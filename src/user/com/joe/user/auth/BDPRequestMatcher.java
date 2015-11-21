package com.joe.user.auth;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 
 * 一个比较器。。
 * @author Joe
 *
 */
public final class BDPRequestMatcher implements RequestMatcher {
    private static final Logger logger = Logger.getLogger(BDPRequestMatcher.class);
    private static final String MATCH_ALL = "/**";

    private final Matcher matcher;
    private final String pattern;
    private final HttpMethod httpMethod;
    private final boolean caseSensitive;

    /**
     * Creates a matcher with the specific pattern which will match all HTTP
     * methods in a case insensitive manner.
     *
     * @param pattern
     *            the ant pattern to use for matching
     */
    public BDPRequestMatcher(String pattern) {
        this(pattern, null);
    }

    /**
     * 
     * @param pattern
     * @param caseSensitive
     */
    public BDPRequestMatcher(String pattern,boolean caseSensitive){
    	this(pattern,null,caseSensitive);
    }
    
    /**
     * Creates a matcher with the supplied pattern and HTTP method in a case
     * insensitive manner.
     *
     * @param pattern
     *            the ant pattern to use for matching
     * @param httpMethod
     *            the HTTP method. The {@code matches} method will return false
     *            if the incoming request doesn't have the same method.
     */
    public BDPRequestMatcher(String pattern, String httpMethod) {
        this(pattern,httpMethod,false);
    }

    /**
     * Creates a matcher with the supplied pattern which will match the
     * specified Http method
     *
     * @param pattern
     *            the ant pattern to use for matching
     * @param httpMethod
     *            the HTTP method. The {@code matches} method will return false
     *            if the incoming request doesn't doesn't have the same method.
     * @param caseSensitive
     *            true if the matcher should consider case, else false
     */
    public BDPRequestMatcher(String pattern, String httpMethod, boolean caseSensitive) {
        Assert.hasText(pattern, "Pattern cannot be null or empty");
        this.caseSensitive = caseSensitive;

        if (pattern.equals(MATCH_ALL) || pattern.equals("**")) {
            pattern = MATCH_ALL;
            matcher = null;
        } else {
            if(!caseSensitive) {
                pattern = pattern.toLowerCase();
            }

            // If the pattern ends with {@code /**} and has no other wildcards, then optimize to a sub-path match
            if (pattern.endsWith(MATCH_ALL) && pattern.indexOf('?') == -1 &&
                    pattern.indexOf("*") == pattern.length() - 2) {
                matcher = new SubpathMatcher(pattern.substring(0, pattern.length() - 3));
            } else {
            	matcher = new RegexMatcher(pattern,caseSensitive);
//                matcher = new SpringAntMatcher(pattern);
            }
        }

        this.pattern = pattern;
        this.httpMethod = StringUtils.hasText(httpMethod) ? HttpMethod.valueOf(httpMethod) : null;
    }

    /**
     * Returns true if the configured pattern (and HTTP-Method) match those of the supplied request.
     *
     * @param request the request to match against. The ant pattern will be matched against the
     *    {@code servletPath} + {@code pathInfo} of the request.
     */
    public boolean matches(HttpServletRequest request) {
        if (httpMethod != null && request.getMethod() != null && httpMethod != HttpMethod.valueOf(request.getMethod())) {
            if (logger.isDebugEnabled()) {
                logger.debug("Request '" + request.getMethod() + " " + getRequestPath(request,caseSensitive) + "'"
                        + " doesn't match '" + httpMethod  + " " + pattern);
            }
            return false;
        }

        if (pattern.equals(MATCH_ALL)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Request '" + getRequestPath(request,caseSensitive) + "' matched by universal pattern '/**'");
            }
            return true;
        }

        String url = getRequestPath(request,caseSensitive);

        if (logger.isDebugEnabled()) {
            logger.debug("Checking match of request : '" + url + "'; against '" + pattern + "'");
        }

        return matcher.matches(url);
    }

    public static final String getRequestPath(HttpServletRequest request,boolean caseSensitive) {
        String url = request.getServletPath();

        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }

        if(!caseSensitive) {
            url = url.toLowerCase();
        }

        return url;
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BDPRequestMatcher)) {
            return false;
        }

        BDPRequestMatcher other = (BDPRequestMatcher) obj;
        return this.pattern.equals(other.pattern) &&
                this.httpMethod == other.httpMethod &&
                this.caseSensitive == other.caseSensitive;
    }

    @Override
    public int hashCode() {
        int code = 31 ^ pattern.hashCode();
        if (httpMethod != null) {
            code ^= httpMethod.hashCode();
        }
        return code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ant [pattern='").append(pattern).append("'");

        if (httpMethod != null) {
            sb.append(", ").append(httpMethod);
        }

        sb.append("]");

        return sb.toString();
    }

    private static interface Matcher {
        boolean matches(String path);
    }

    private static class RegexMatcher implements Matcher {

		private static final String VALEUE_REGEX = "(\\{[a-z|A-Z|0-9|-|_]+\\})";
		private static final Pattern VALUE_PATTERN = Pattern.compile(VALEUE_REGEX);
		
    	private final String restPath;
    	private Pattern pattern;
    	private RegexMatcher(String restPath,boolean caseSensitive){
    		this.restPath = restPath;
    		if(caseSensitive){
    			pattern = Pattern.compile(replaceValue(restPath),Pattern.CASE_INSENSITIVE);	
    		}else{
    			pattern = Pattern.compile(replaceValue(restPath));
    		}
    	}
    	
		@Override
		public boolean matches(String path) {
			return pattern.matcher(path).matches();
		}
    	

		/**
		 * 替换value值。。/hosts/{0}
		 * @param value
		 * @return
		 */
		private static final String replaceValue(String value){
			java.util.regex.Matcher matcher = VALUE_PATTERN.matcher(value);
			StringBuffer buffer = new StringBuffer();
			while(matcher.find()){
				String matcherKey = matcher.group(1);
	            String matchervalue = ".+";
	            matcher.appendReplacement(buffer, matchervalue);
			}
			matcher.appendTail(buffer);
			return buffer.toString();
		}
    }
    
//    private static class SpringAntMatcher implements Matcher {
//        private static final AntPathMatcher antMatcher = new AntPathMatcher();
//
//        private final String pattern;
//
//        private SpringAntMatcher(String pattern) {
//            this.pattern = pattern;
//        }
//
//        public boolean matches(String path) {
//            return antMatcher.match(pattern, path);
//        }
//    }

    /**
     * Optimized matcher for trailing wildcards
     */
    private static class SubpathMatcher implements Matcher {
        private final String subpath;
        private final int length;

        private SubpathMatcher(String subpath) {
            assert !subpath.contains("*");
            this.subpath = subpath;
            this.length = subpath.length();
        }

        public boolean matches(String path) {
            return path.startsWith(subpath) && (path.length() == length || path.charAt(length) == '/');
        }
    }
}
