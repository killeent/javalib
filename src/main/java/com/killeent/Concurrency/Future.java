package com.killeent.Concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A simple implementation of a Future -> an object that represents the result of an asynchronous
 * operation. We provide a simple static method to run a task asynchronously, which generates
 * the Future object.
 *
 * The future object is meant to match the functionality of {@link java.util.concurrent.Future}
 * except for cancelling the task.
 *
 * @author Trevor Killeen (2015)
 */
public class Future<T> {

    /**
     * Executes the specified task asynchronously in a background thread.
     * @param task The task to execute.
     * @return A Future Object that represents the result of this operation.
     */
    public static <T> Future<T> execute(Callable<T> task) {
        return new Future<T>(task);
    }

    private final Lock lock;          // lock to guard the finished field
    private final Condition cv;       // cv associated with the lock
    private final Thread asyncThread; // thread executing the background task
    private boolean finished;         // tracks whether or not the async task has finished
    private T result;                 // the result of this computation
    private boolean threwException;   // if the computation threw an exception
    private Throwable executionCause; // cause of the exception, if one occurs

    private Future(Callable<T> task) {
        lock = new ReentrantLock();
        cv = lock.newCondition();
        finished = false;
        result = null;
        threwException = false;

        asyncThread = new Thread(new AsyncTask(task));
        asyncThread.run();
    }

    // Runnable to executable the callable in a separate thread
    private class AsyncTask implements Runnable {

        private final Callable<T> task;
        private T atResult;
        private boolean atException;

        public AsyncTask(Callable<T> task) {
            this.task = task;
            atResult = null;
            atException = false;
        }

        @Override
        public void run() {
            try {
                atResult = task.call();
            } catch (Exception e) {
                executionCause = e;
                threwException = true;
            } finally {
                synchronized (lock) {
                    finished = true;
                    result = atResult;
                    threwException = atException;
                    cv.notifyAll();
                }
            }
        }
    }

    /**
     * @return True if the task is finished, otherwise false.
     */
    public boolean isFinished() {
        synchronized (lock) {
            return finished;
        }
    }

    /**
     * Waits for the asynchronous task to complete, then returns the result.
     *
     * @throws java.util.concurrent.ExecutionException if the computation threw an exception.
     * @throws java.lang.InterruptedException if we are interrupted while waiting.
     *
     * @return The result of the computation.
     */
    public T get() throws ExecutionException, InterruptedException {
        synchronized (lock) {
            while (!finished) {
                cv.await();
            }

            if (threwException) {
                throw new ExecutionException(executionCause);
            } else {
                return result;
            }
        }
    }

    /**
     * Waits for the asynchronous task to complete, then returns the result.
     *
     * @param timeout the maximum timeout to wait.
     * @param timeUnit the unit of the timeout argument.
     *
     * @throws java.util.concurrent.ExecutionException if the computation threw an exception.
     * @throws java.lang.InterruptedException if we are interrupted while waiting.
     * @throws java.util.concurrent.TimeoutException if the timeout occurs.
     *
     * @return The result of the computation.
     */
    public T get(long timeout, TimeUnit timeUnit)
            throws ExecutionException, InterruptedException, TimeoutException {
        synchronized (lock) {
            while (!finished) {
                if (!cv.await(timeout, timeUnit)) {
                    throw new TimeoutException();
                }
            }

            if (threwException) {
                throw new ExecutionException(executionCause);
            } else {
                return result;
            }
        }
    }

}
