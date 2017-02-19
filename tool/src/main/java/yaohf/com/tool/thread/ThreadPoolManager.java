package yaohf.com.tool.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ThreadPoolManager
{
	private static ThreadPoolManager tpm;

	private final static int CORE_POOL_SIZE = 13;

	private final static int MAX_POOL_SIZE = 15;

	private final static int TASK_QOS_PERIOD = 120;
	private boolean isPaused;

	private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();

	
	private PausableThreadPoolExecutor threadPool;

	
	private ThreadPoolManager() {
		taskQueue = new LinkedBlockingQueue<Runnable>();
		threadPool = new PausableThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
				TASK_QOS_PERIOD, TimeUnit.SECONDS, taskQueue);
	}

	
	public static ThreadPoolManager getInstance() {
		if (null == tpm)
			tpm = new ThreadPoolManager();
		return tpm;
	}

	public boolean isPause(){
		return isPaused;
	}
	
	public static boolean isInit() {
		if (null == tpm)
			return false;
		return true;
	}

	public void remove(Runnable task)
	{
		if(task!=null && threadPool != null)
		{
			threadPool.remove(task);
		}
	}

	public void addExecuteTask(Runnable task) {
		if (task != null) {
			threadPool.execute(task);
		}
	}

	public void pauseThreadPool() {
		if (null != tpm && null != threadPool) {
			threadPool.pause();
			isPaused = true;
		}
	}
	
	public void clearThreadPool() {
		if (null != tpm && null != threadPool) {
			taskQueue.clear();
		}
	}

	public void resumeThreadPool() {
		if(null != tpm && null != threadPool) {
			threadPool.resume();
			isPaused = false;
		}
	}

	public void destoryThreadPool() {
		clearThreadPool();
		threadPool.shutdown();
		taskQueue = null;
		threadPool = null;
		tpm = null;
	}
}

class PausableThreadPoolExecutor extends ThreadPoolExecutor {
	public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	private boolean isPaused;
	private ReentrantLock pauseLock = new ReentrantLock();
	private Condition unpaused = pauseLock.newCondition();

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		pauseLock.lock();
		try {
			while (isPaused)
				unpaused.await();
		} catch (InterruptedException ie) {
			t.interrupt();
		} finally {
			pauseLock.unlock();
		}
	}

	public void pause() {
		pauseLock.lock();
		try {
			isPaused = true;
		} finally {
			pauseLock.unlock();
		}
	}

	public void resume() {
		pauseLock.lock();
		try {
			isPaused = false;
			unpaused.signal();
		} finally {
			pauseLock.unlock();
		}
	}
}
