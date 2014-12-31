package com.killeent.Concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A thread pool is a fixed collection of running threads that can execute tasks. This is a simple
 * thread pool that takes a specified number of threads, and a specified maximum number of tasks in
 * the queue. It makes no effort to support more complex thread pool tasks like blocking while
 * adding things to the queue if the queue is full, or more dangerous shutdown requests.
 *
 * Code inspired by https://github.com/mbrossard/threadpool.
 *
 * @author Trevor Killeen (2014)
 */
public class ThreadPool {


    // Queue (FIFO) of tasks posted to this thread pool; inherently tracks the number of
    // tasks currently in the queue
    private final Queue<Runnable> tasks;

    // Client-specified maximum number of tasks that can be queued
    private final int maxTasks;

    // Coarse Lock/CV for the thread pool
    private final Lock poolLock;
    private final Condition poolCV;

    // CV for termination
    private final Condition terminationCV;

    // True if the client has initiated a shutdown request, otherwise false
    private boolean shutdown;

    // Number of active threads in the pool; the client specifies the initial amount
    private int active;

    /**
     * Constructs a new ThreadPool with the specified number of threads.
     *
     * @param threads Threads to create in the pool.
     * @param maxTasks The maximum number of tasks that can be queued (but not running)
     *                 at a time.
     */
    public ThreadPool(int threads, int maxTasks) {
        tasks = new LinkedList<Runnable>();
        this.maxTasks = maxTasks;
        poolLock = new ReentrantLock();
        poolCV = poolLock.newCondition();
        terminationCV = poolLock.newCondition();

        shutdown = false;
        active = 0;

        // Spin up worker threads
        for (int i = 0; i < threads; i++) {
            Thread worker = new Thread(new ThreadPoolWorker());
            worker.start();
            active++;
        }
    }

    /**
     * Adds a task to the thread pool.
     *
     * @param task The task to add
     * @return True if the task has successfully been scheduled to run. False if the queue
     * is full, or if a shutdown request has occurred.
     */
    public boolean add(Runnable task) {
        synchronized (poolLock) {
            // If we already have the maximum number of tasks queued, simply return
            if (tasks.size() == maxTasks) {
                return false;
            }

            // If we are shutting down, then return false
            if (shutdown) {
                return false;
            }

            // Add task to the queue and signal
            tasks.add(task);
            poolCV.signal();
            return true;
        }
    }

    /**
     * Shutdown the thread pool. All currently executing tasks will run to completion, but no
     * queued tasks will run, and future requests to run a task will be rejected.
     */
    public void shutdown() {
        synchronized (poolLock) {
            shutdown = true;
            poolCV.signalAll();
        }
    }

    /**
     * Wait for all threads in the pool to terminate, i.e. finish their tasks.
     *
     * @throws java.lang.InterruptedException if the wait is interrupted.
     */
    public void awaitTermination() throws InterruptedException {
        synchronized (poolLock) {
            while (active > 0) {
                terminationCV.await();
            }
        }
    }

    /**
     * Wait for all threads in the pool to terminate, i.e. finish their tasks, or
     * for timeout to expire, whichever comes first.
     *
     * @throws java.lang.InterruptedException if the wait is interrupted.
     */
    public void awaitTermination(long time, TimeUnit timeUnit) throws InterruptedException {
        synchronized (poolLock) {
            while (active > 0) {
                terminationCV.await(time, timeUnit);
            }
        }
    }

    /**
     * A ThreadPoolWorker simply waits for a task and then executes it when one becomes
     * available.
     */
    private class ThreadPoolWorker implements Runnable {

        @Override
        public void run() {
            while(true) {
                Runnable task;
                synchronized (poolLock) {
                    // Wait for something to enter the task queue or for there to be a shutdown
                    // request from the user
                    while (!shutdown && tasks.isEmpty()) {
                        poolCV.awaitUninterruptibly();
                    }

                    // If there is a shutdown request, exit
                    if (shutdown) {
                        active--;
                        if (active == 0) {
                            terminationCV.signalAll();
                        }
                        return;
                    }

                    // Otherwise, get the next task from the task queue
                    task = tasks.remove();
                }

                // Release the lock and run the task
                task.run();
            }
        }
    }

}
