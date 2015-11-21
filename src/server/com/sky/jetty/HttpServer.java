/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sky.jetty;

import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ShutdownHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import com.sky.config.ConfigAble;
import com.sky.config.Configed;


/**
 * Create a Jetty embedded server to answer http requests. The primary goal
 * is to serve up status information for the server.
 * There are three contexts:
 *   "/logs/" -> points to the log directory
 *   "/static/" -> points to common static files (src/webapps/static)
 *   "/" -> the jsp server code from (src/webapps/<name>)
 */
public class HttpServer extends Configed implements FilterContainer,ConfigAble {
  public static final Logger LOG = Logger.getLogger(HttpServer.class);

  // The ServletContext attribute where the daemon Configuration
  // gets stored.
  private static final String MIME_XML = "mime.xml";
  private static final String DEFAULT_WEB_XML = "webdefault.xml";
  private static final String WEB_XML = "web.xml";
  private static final String WEB_INFO = "WEB-INF";
  private static final String STOP_KEY_CONF = "com.sky.server.stop";
  private static final String EXIT_JVM_CONF = "com.sky.server.exitjvm";
  
  protected final Server webServer;
  protected final Connector listener;
  protected final WebAppContext webAppContext;
  protected final boolean findPort;
  protected final Map<ServletContextHandler, Boolean> defaultContexts = new HashMap<ServletContextHandler, Boolean>();
  protected final List<String> filterNames = new ArrayList<String>();
  private static final int MAX_RETRIES = 10;
  private Configuration conf;
  private boolean listenerStartedExternally = false;


  public HttpServer(Configuration conf,String name, String bindAddress, int port,
      boolean findPort) throws Exception {
    this(conf,name, bindAddress, port, findPort, null);
  }

  /**
   * Add a servlet in the server.
   * @param name The name of the servlet (can be passed as null)
   * @param pathSpec The path spec for the servlet
   * @param clazz The servlet class
   */
  public void addServlet(String name, String pathSpec,
      Class<? extends HttpServlet> clazz) {
    addInternalServlet(name, pathSpec, clazz);
    addFilterPathMapping(pathSpec, webAppContext);
  }
  
  /**
   * Add an internal servlet in the server, specifying whether or not to
   * protect with Kerberos authentication. 
   * Note: This method is to be used for adding servlets that facilitate
   * internal communication and not for user facing functionality. For
   * servlets added using this method, filters (except internal Kerberized
   * filters) are not enabled. 
   * 
   * @param name The name of the servlet (can be passed as null)
   * @param pathSpec The path spec for the servlet
   * @param clazz The servlet class
   */
  public void addInternalServlet(String name, String pathSpec, 
      Class<? extends HttpServlet> clazz) {
    ServletHolder holder = new ServletHolder(clazz);
    if (name != null) {
      holder.setName(name);
    }
    webAppContext.addServlet(holder, pathSpec);
  }
  
  public HttpServer(Configuration conf,String name, String bindAddress, int port,
      boolean findPort, Connector connector) throws Exception{
    webServer = new Server();
    this.findPort = findPort;
    this.conf = conf;
    String stopToken = conf.getString(STOP_KEY_CONF, "stop");
    boolean exitJvm = Boolean.valueOf(conf.getString(EXIT_JVM_CONF, "true"));
    HandlerList handlerList = new HandlerList();
    addShutDownHandler(handlerList, stopToken, exitJvm);
    
    if(connector == null) {
      listenerStartedExternally = false;
      listener = createBaseListener(conf);
      listener.setHost(bindAddress);
      listener.setPort(port);
    } else {
      listenerStartedExternally = true;
      listener = connector;
    }
    
    webServer.addConnector(listener);

    webServer.setThreadPool(new QueuedThreadPool());

    final String appDir = getWebAppsPath();
    ContextHandlerCollection contexts = new ContextHandlerCollection();
    
    handlerList.addHandler(contexts);
    
    webServer.setStopAtShutdown(true);

    webAppContext = new WebAppContext();
    webAppContext.setDisplayName("WepAppsContext");
    webAppContext.setContextPath("/");
    webAppContext.setWar(appDir + "/" + name);
    
    String mimeXml = appDir + "/" + name + "/" + WEB_INFO + "/" + MIME_XML;
    MimeTypes mimes = MimeUtil.loadResouse(new File(new URI(mimeXml)));
    webAppContext.setMimeTypes(mimes);
    
    String defWebXml = appDir + "/" + name + "/" + WEB_INFO + "/" + DEFAULT_WEB_XML;
    webAppContext.setDefaultsDescriptor(defWebXml);
    
    String webXml = appDir + "/" + name + "/" + WEB_INFO + "/" + WEB_XML;
    webAppContext.setDescriptor(webXml);
    
    handlerList.addHandler(webAppContext);
    webServer.setHandler(handlerList);
  }
  
  private void addShutDownHandler(HandlerList handers,String token,boolean exitJvm) throws Exception{
      ShutdownHandler shutdown = new ShutdownHandler(webServer, token);
      shutdown.setExitJvm(exitJvm);
      handers.addHandler(shutdown);
      shutdown.start();
  }

//  public static void attemptShutdown(int port) throws IOException, ConfigurationException {
//	  Configuration conf = ConfigUtil.create();
//      String shutdownCookie = conf.getString(STOP_KEY_CONF, "stop");
//      try {
//          URL url = new URL("http://127.0.0.1:" + port + "/shutdown?cookie=" + shutdownCookie);
//          HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//          connection.setRequestMethod("POST");
//          connection.getResponseCode();
//          LOG.info("Shutting down " + url + ": " + connection.getResponseMessage());
//      } catch (SocketException e) {
//          LOG.debug("Not running");
//      } catch (IOException e) {
//          throw new RuntimeException(e);
//      }
//  }
  
  /**
   * Create a required listener for the Jetty instance listening on the port
   * provided. This wrapper and all subclasses must create at least one
   * listener.
   */
  public Connector createBaseListener(Configuration conf)
      throws IOException {
    return HttpServer.createDefaultChannelConnector();
  }
  
  // LimitedPrivate for creating secure datanodes
  public static Connector createDefaultChannelConnector() {
    SelectChannelConnector ret = new SelectChannelConnector();
    ret.setLowResourcesMaxIdleTime(10000);
    ret.setAcceptQueueSize(128);
    ret.setResolveNames(false);
    ret.setUseDirectBuffers(false);
    return ret;   
  }
  

  /**
   * Set a value in the webapp context. These values are available to the jsp
   * pages as "application.getAttribute(name)".
   * @param name The name of the attribute
   * @param value The value of the attribute
   */
  public void setAttribute(String name, Object value) {
    setAttribute(webAppContext, name, value);
  }

  /**
   * Set a value in the webapp context. These values are available to the jsp
   * pages as "application.getAttribute(name)".
   * @param context Context to add attribute
   * @param name The name of the attribute
   * @param value The value of the attribute
   */
  public void setAttribute(ServletContextHandler context, String name, Object value) {
    context.setAttribute(name, value);
  }

  /** {@inheritDoc} */
  public void addFilter(String name, String classname,
      Map<String, String> parameters) {

    final String[] USER_FACING_URLS = { "*.html", "*.jsp" };
    defineFilter(webAppContext, name, classname, parameters, USER_FACING_URLS);
    LOG.info("Added filter " + name + " (class=" + classname
        + ") to context " + webAppContext.getDisplayName());
    final String[] ALL_URLS = { "/*" };
    for (Map.Entry<ServletContextHandler, Boolean> e : defaultContexts.entrySet()) {
      if (e.getValue()) {
	 ServletContextHandler ctx = e.getKey();
        defineFilter(ctx, name, classname, parameters, ALL_URLS);
        LOG.info("Added filter " + name + " (class=" + classname
            + ") to context " + ctx.getDisplayName());
      }
    }
    filterNames.add(name);
  }

  /**
   * Define a filter for a context and set up default url mappings.
   */
  protected void defineFilter(ServletContextHandler ctx, String name,
      String classname, Map<String,String> parameters, String[] urls) {
      
    FilterHolder holder = new FilterHolder();
    holder.setName(name);
    holder.setClassName(classname);
    holder.setInitParameters(parameters);
    FilterMapping fmap = new FilterMapping();
    fmap.setPathSpecs(urls);
    fmap.setDispatches(FilterMapping.ALL);
    fmap.setFilterName(name);
    ServletHandler handler = ctx.getServletHandler();
    handler.addFilter(holder, fmap);
  }
  /** {@inheritDoc} */
  public void addGlobalFilter(String name, String classname,
      Map<String, String> parameters) {
    final String[] ALL_URLS = { "/*" };
    defineFilter(webAppContext, name, classname, parameters, ALL_URLS);
    for (ServletContextHandler ctx : defaultContexts.keySet()) {
      defineFilter(ctx, name, classname, parameters, ALL_URLS);
    }
    LOG.info("Added global filter" + name + " (class=" + classname + ")");
  }

  /**
   * Add the path spec to the filter path mapping.
   * @param pathSpec The path spec
   * @param webAppCtx The WebApplicationContext to add to
   */
  protected void addFilterPathMapping(String pathSpec,
	  ServletContextHandler webAppCtx) {
    ServletHandler handler = webAppCtx.getServletHandler();
    for(String name : filterNames) {
      FilterMapping fmap = new FilterMapping();
      fmap.setPathSpec(pathSpec);
      fmap.setFilterName(name);
      fmap.setDispatches(FilterMapping.ALL);
      handler.addFilterMapping(fmap);
    }
  }
  
  /**
   * Get the value in the webapp context.
   * @param name The name of the attribute
   * @return The value of the attribute
   */
  public Object getAttribute(String name) {
    return webAppContext.getAttribute(name);
  }

  /**
   * Get the pathname to the webapps files.
   * @return the pathname as a URL
   * @throws IOException if 'webapps' directory cannot be found on CLASSPATH.
   */
  protected String getWebAppsPath() throws IOException {
    URL url = getClass().getClassLoader().getResource("bigdata");
    if (url == null) 
      throw new IOException("webapps not found in CLASSPATH"); 
    return url.toString();
  }

  /**
   * Get the port that the server is on
   * @return the port
   */
  public int getPort() {
    return webServer.getConnectors()[0].getLocalPort();
  }

  /**
   * Set the min, max number of worker threads (simultaneous connections).
   */
  public void setThreads(int min, int max) {
    QueuedThreadPool pool = (QueuedThreadPool) webServer.getThreadPool() ;
    pool.setMinThreads(min);
    pool.setMaxThreads(max);
  }

  /**
   * Start the server. Does not wait for the server to start.
   */
  public void start() throws IOException {
    try {
      if(listenerStartedExternally) { // Expect that listener was started securely
        if(listener.getLocalPort() == -1) // ... and verify
          throw new Exception("Exepected webserver's listener to be started" +
          		"previously but wasn't");
        // And skip all the port rolling issues.
        webServer.start();
      } else {
        int port = 0;
        int oriPort = listener.getPort(); // The original requested port
        while (true) {
          try {
            port = webServer.getConnectors()[0].getLocalPort();
            LOG.info("Port returned by webServer.getConnectors()[0]." +
            		"getLocalPort() before open() is "+ port + 
            		". Opening the listener on " + oriPort);
            listener.open();
            port = listener.getLocalPort();
            LOG.info("listener.getLocalPort() returned " + listener.getLocalPort() + 
                  " webServer.getConnectors()[0].getLocalPort() returned " +
                  webServer.getConnectors()[0].getLocalPort());
            //Workaround to handle the problem reported in HADOOP-4744
            if (port < 0) {
              Thread.sleep(100);
              int numRetries = 1;
              while (port < 0) {
                LOG.warn("listener.getLocalPort returned " + port);
                if (numRetries++ > MAX_RETRIES) {
                  throw new Exception(" listener.getLocalPort is returning " +
                  		"less than 0 even after " +numRetries+" resets");
                }
                for (int i = 0; i < 2; i++) {
                  LOG.info("Retrying listener.getLocalPort()");
                  port = listener.getLocalPort();
                  if (port > 0) {
                    break;
                  }
                  Thread.sleep(200);
                }
                if (port > 0) {
                  break;
                }
                LOG.info("Bouncing the listener");
                listener.close();
                Thread.sleep(1000);
                listener.setPort(oriPort == 0 ? 0 : (oriPort += 1));
                listener.open();
                Thread.sleep(100);
                port = listener.getLocalPort();
              }
            } //Workaround end
            LOG.info("Jetty bound to port " + port);
            webServer.start();
            break;
          } catch (IOException ex) {
            // if this is a bind exception,
            // then try the next port number.
            if (ex instanceof BindException) {
              if (!findPort) {
                throw (BindException) ex;
              }
            } else {
              LOG.info("HttpServer.start() threw a non Bind IOException"); 
              throw ex;
           }
          } catch (MultiException ex) {
            LOG.info("HttpServer.start() threw a MultiException"); 
            throw ex;
          }
          listener.setPort((oriPort += 1));
        }
      }
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw new IOException("Problem starting http server", e);
    }
  }

  /**
   * stop the server
   */
  public void stop() throws Exception {
    listener.close();
    webServer.stop();
  }

  public void join() throws InterruptedException {
    webServer.join();
  }

  /**
   * A Servlet input filter that quotes all HTML active characters in the
   * parameter names and values. The goal is to quote the characters to make
   * all of the servlets resistant to cross-site scripting attacks.
   */
  public static class QuotingInputFilter implements Filter {

    public static class RequestQuoter extends HttpServletRequestWrapper {
      private final HttpServletRequest rawRequest;
      public RequestQuoter(HttpServletRequest rawRequest) {
        super(rawRequest);
        this.rawRequest = rawRequest;
      }
      
      /**
       * Return the set of parameter names, quoting each name.
       */
      @SuppressWarnings("unchecked")
      @Override
      public Enumeration<String> getParameterNames() {
        return new Enumeration<String>() {
          private Enumeration<String> rawIterator = 
            rawRequest.getParameterNames();
          @Override
          public boolean hasMoreElements() {
            return rawIterator.hasMoreElements();
          }

          @Override
          public String nextElement() {
            return HtmlQuoting.quoteHtmlChars(rawIterator.nextElement());
          }
        };
      }
      
      /**
       * Unquote the name and quote the value.
       */
      @Override
      public String getParameter(String name) {
        return HtmlQuoting.quoteHtmlChars(rawRequest.getParameter
                                     (HtmlQuoting.unquoteHtmlChars(name)));
      }
      
      @Override
      public String[] getParameterValues(String name) {
        String unquoteName = HtmlQuoting.unquoteHtmlChars(name);
        String[] unquoteValue = rawRequest.getParameterValues(unquoteName);
        String[] result = new String[unquoteValue.length];
        for(int i=0; i < result.length; ++i) {
          result[i] = HtmlQuoting.quoteHtmlChars(unquoteValue[i]);
        }
        return result;
      }

      @SuppressWarnings("unchecked")
      @Override
      public Map<String, String[]> getParameterMap() {
        Map<String, String[]> result = new HashMap<String,String[]>();
        Map<String, String[]> raw = rawRequest.getParameterMap();
        for (Map.Entry<String,String[]> item: raw.entrySet()) {
          String[] rawValue = item.getValue();
          String[] cookedValue = new String[rawValue.length];
          for(int i=0; i< rawValue.length; ++i) {
            cookedValue[i] = HtmlQuoting.quoteHtmlChars(rawValue[i]);
          }
          result.put(HtmlQuoting.quoteHtmlChars(item.getKey()), cookedValue);
        }
        return result;
      }
      
      /**
       * Quote the url so that users specifying the HOST HTTP header
       * can't inject attacks.
       */
      @Override
      public StringBuffer getRequestURL(){
        String url = rawRequest.getRequestURL().toString();
        return new StringBuffer(HtmlQuoting.quoteHtmlChars(url));
      }
      
      /**
       * Quote the server name so that users specifying the HOST HTTP header
       * can't inject attacks.
       */
      @Override
      public String getServerName() {
        return HtmlQuoting.quoteHtmlChars(rawRequest.getServerName());
      }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, 
                         ServletResponse response,
                         FilterChain chain
                         ) throws IOException, ServletException {
      HttpServletRequestWrapper quoted = 
        new RequestQuoter((HttpServletRequest) request);
      final HttpServletResponse httpResponse = (HttpServletResponse) response;
      // set the default to UTF-8 so that we don't need to worry about IE7
      // choosing to interpret the special characters as UTF-7
//      httpResponse.setContentType("text/html;charset=utf-8");
      chain.doFilter(quoted, response);
    }

  }
}
