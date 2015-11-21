package com.joe.host.ssh;

import java.io.Serializable;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.joe.host.dao.HostCheck;
import com.joe.host.vo.FindResult;
import com.joe.host.vo.Host;

/**
 * 检查多个主机处理线程池
 * 
 * @author user
 * 
 */
public class SSHCheckPool {
    private static final Log log = LogFactory.getLog(SSHCheckPool.class);
    private final ExecutorService threadPool;
    private FindResult result;
    private HostCheck check;

    public SSHCheckPool(HostCheck check, int poolSize) {
	this.check = check;
	threadPool = Executors.newFixedThreadPool(poolSize);
	result = new FindResult();
	result.setExistHosts(new ArrayList<Host>());
	result.setNoExistHosts(new ArrayList<Host>());
    }

    public void destroy() {
	log.info("开始关闭异步处理线程池!");
	if (threadPool != null)
	    shutdownAndAwaitTermination(threadPool);
	log.info("关闭异步处理线程池结束!");
    }

    /**
     * 根据list参数在线程池中启动size大小的线程，并用CountDownLatch来控制该批线程结束后的处理。
     * 
     * @param list
     */
    public FindResult process(List<Host> list) {
	final CountDownLatch doneSignal = new CountDownLatch(list.size());

	try {
	    for (Host host : list) {
		TPParseRunnable checkRun = new TPParseRunnable(doneSignal, host);
		threadPool.execute(checkRun);
	    }
	    try {
		doneSignal.await();
		log.info("检查完成!");
	    } catch (InterruptedException e) {
		Thread.currentThread().interrupt();
		log.error(e.getMessage(), e);
	    }
	} catch (Exception e) {
	    log.error("检查发现异常", e);
	}
	return result;
    }

    public class TPParseRunnable implements Runnable, Serializable {
	private static final long serialVersionUID = 6800676323086501763L;
	private CountDownLatch doneSignal;
	private Host host;

	public TPParseRunnable(CountDownLatch doneSignal, Host host) {
	    this.doneSignal = doneSignal;
	    this.host = host;
	    Thread.currentThread().setName(host.getIp());
	    Thread.currentThread().setUncaughtExceptionHandler(
		    new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
			    t.interrupt();
			    log.error("任务" + t.getName() + "查询异常", e);
			}
		    });
	}

	@Override
	public void run() {
	    try {
		if (check.checkExist(host)) {
		    result.getExistHosts().add(host);
		} else {
		    result.getNoExistHosts().add(host);
		}
	    } catch (Exception e) {
		log.error(e.getMessage(), e);
	    } finally {
		doneSignal.countDown();
	    }
	}
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
	pool.shutdown(); // Disable new tasks from being submitted
	try {
	    // Wait a while for existing tasks to terminate
	    if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
		pool.shutdownNow(); // Cancel currently executing tasks
		// Wait a while for tasks to respond to being cancelled
		if (!pool.awaitTermination(60, TimeUnit.SECONDS))
		    log.debug("Pool did not terminate");
	    }
	} catch (InterruptedException ie) {
	    // (Re-)Cancel if current thread also interrupted
	    pool.shutdownNow();
	    // Preserve interrupt status
	    Thread.currentThread().interrupt();
	}
    }
}