package com.killeent.Concurrency;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A timer provides a mechanism for threads to schedule tasks to run at some point in the
 * future in a single background thread. This is a simple timer that only supports one-off
 * task scheduling (it is not able to schedule tasks to run at fixed intervals).
 *
 * @author Trevor Killeen (2015)
 */
public class Timer {

    // Queue of tasks to perform in the future, ordered by their scheduled time
    private final PriorityQueue<DelayedTask> tasks;

    // Lock to guard the task queue and canceled variable
    private final Lock timerLock;

    // Condition variable for the background thread to wait on when their are no
    // tasks in the queue
    private final Condition emptyQueueCV;

    // Condition variable for the background thread to wait on when the current time
    // is less than the time at which the next task should be executed
    private final Condition taskDelayCV;

    // True if the client has called cancel, otherwise false
    private boolean canceled;

    /**
     * Creates a new timer that is able to accept and execute tasks that are scheduled
     * in the future. In particular, by constructing a timer we are creating a new background
     * thread.
     */
    public Timer() {
        tasks = new PriorityQueue<DelayedTask>();
        timerLock = new ReentrantLock();
        emptyQueueCV = timerLock.newCondition();
        taskDelayCV = timerLock.newCondition();
        canceled = false;

        // spin up background thread
        new Thread(new TimerRunnable()).start();
    }

    /**
     * Cancels any future tasks that have not began executing.
     */
    public void cancel() {
        synchronized (timerLock) {
            canceled = true;
            emptyQueueCV.signal();
            taskDelayCV.signal();
        }
    }

    /**
     * Schedules a task to run after the specified delay.
     *
     * @param task Task to run.
     * @param delay Delay to wait for.
     * @throws java.lang.IllegalArgumentException if delay is negative.
     * @throws java.lang.IllegalArgumentException if task is null.
     * @throws java.lang.IllegalStateException if the Timer is canceled.
     */
    public void schedule(Runnable task, long delay) {
        if (delay < 0) {
            throw new IllegalArgumentException("negative delay");
        }
        if (task == null) {
            throw new IllegalArgumentException("null task");
        }

        synchronized (timerLock) {
            if (canceled) {
                throw new IllegalStateException("Timer has been canceled");
            }
            tasks.add(new DelayedTask(task, System.currentTimeMillis() + delay));
            emptyQueueCV.signal();
            taskDelayCV.signal();
        }
    }

    /**
     * Defines the behavior of the background thread for the timer.
     */
    private class TimerRunnable implements Runnable {

        @Override
        public void run() {
            while(true) {
                synchronized (timerLock) {
                    try {
                        // check to see if we have any threads in the task queue, or
                        // if we have been canceled
                        while (!canceled && tasks.isEmpty()) {
                            emptyQueueCV.await();
                        }

                        // otherwise, wait for the next tasks execution time
                        // to be equal to the current time
                        long diff = tasks.peek().getExecutionTime() - System.currentTimeMillis();
                        while (!canceled &&
                                diff > 0) {
                            taskDelayCV.await(
                                    diff,
                                    TimeUnit.MILLISECONDS);
                        }

                        // check to see if canceled
                        if (canceled) {
                            break;
                        }

                        // otherwise pull the next task off of the task queue and execute it. This
                        // may not be the same task we were waiting for, but it is guaranteed to
                        // have an execution time less than or equal to the current time
                        tasks.remove().run();

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        canceled = true;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Class to encapsulate a task to be ran and the time at which it should be ran.
     */
    private class DelayedTask implements Comparable<DelayedTask> {

        private final Runnable task;
        private final long executionTime;

        public DelayedTask(Runnable task, long executionTime) {
            this.task = task;
            this.executionTime = executionTime;
        }

        public long getExecutionTime() {
            return this.executionTime;
        }

        public void run() {
            task.run();
        }

        @Override
        public int compareTo(DelayedTask o) {
            long diff = executionTime - o.executionTime;
            if (diff < 0) {
                return -1;
            } else if (diff == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }

}
