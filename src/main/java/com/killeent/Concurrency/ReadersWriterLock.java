package com.killeent.Concurrency;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of a readers-writer lock with fairness -> both readers and
 * writers will not starve.
 *
 * Note: if a thread wants to acquire a lock for writing, it must first release
 * its hold on the read lock.
 *
 * @author Trevor Killeen (2015)
 */
public class ReadersWriterLock {

    // We use a simple lock, condition variable and counter based approach
    // to implement the RWLock

    // Lock guarding the internal state
    private final Lock lock;

    // Condition Variable for readers to wait on
    private final Condition readerWaitCV;

    // Condition Variable for writers to wait on
    private final Condition writerWaitCV;

    // counts of active, waiting readers and writer. activeReaders and
    // activeWriters may be greater than the number of threads currently
    // holding the read or write lock, as it is incremented again on
    // re-entrance
    private int waitingReaders = 0;
    private int activeReaders = 0;
    private int waitingWriters = 0;
    private int activeWriters = 0;

    // We use a naive (and thus inefficient) mechanism to implement fairness.
    // When trying to acquire a lock for reading, if there are waiting writers
    // AND the last operation was a read, then we wait. Similarly, when trying
    // to acquire a lock for writing, if there are waiting readers AND the last
    // operation was a write, then we wait. This is in addition to the mechanisms
    // to ensure that writes are exclusive and multiple reads can proceed at a
    // time.
    //
    // Accordingly, when releasing a read lock, we check to see if there are
    // waiting writers and notify them. When releasing the write lock, we check
    // and see if there are waiting readers and notify them.

    private boolean lastOpWasRead = false;

    // In order to prevent thread starvation, we queue waiting writers in the
    // order they request access. Waiting writers should only be allowed
    // write access if they are at the front of their queue when awoken. We
    // choose not to implement similar functionality for readers - that way
    // if readers are awoken they all read in tandem.

    private final Queue<Thread> waitingWriterQueue;

    // Because this class is built on top of a re-entrant lock, we want to
    // support re-entrant behavior for clients of the lock. As a result, we
    // need to keep track of the threads that hold the lock and allow them
    // to bypass the normal wait conditions for acquireRead and acquireWrite.

    private final Map<Thread, Integer> readers;
    private Thread writer = null;

    /**
     * Constructs a new ReadersWriterLock.
     */
    public ReadersWriterLock() {
        this.lock = new ReentrantLock();
        this.readerWaitCV = lock.newCondition();
        this.writerWaitCV = lock.newCondition();

        this.waitingWriterQueue = new LinkedList<Thread>();

        this.readers = new HashMap<Thread, Integer>();
    }

    /**
     * Acquires the lock for reading.
     */
    public void acquireRead() {
        synchronized (lock) {
            waitingReaders++;
            Thread curr = Thread.currentThread();
            // check and see if we already hold the lock
            if (!readers.containsKey(curr) && !curr.equals(writer)) {
                // wait on the following conditions:
                // 1. There is an active writer
                // 2. There is a waiting writer, and the last operation was a read
                while (activeWriters > 0 || (waitingWriters > 0 && lastOpWasRead)) {
                    readerWaitCV.awaitUninterruptibly();
                }
            }
            lastOpWasRead = true;
            if (!readers.containsKey(curr)) {
                readers.put(curr, 1);
            } else {
                readers.put(curr, readers.get(curr) + 1);
            }
            waitingReaders--;
            activeReaders++;
        }
    }

    /**
     * @return True if the current thread holds the lock for reading, otherwise false.
     */
    private boolean doIHoldReadLock() {
        synchronized (lock) {
            return readers.containsKey(Thread.currentThread());
        }
    }

    /**
     * Releases the lock for reading.
     *
     * @throws IllegalMonitorStateException if the current thread does not hold
     * the lock.
     */
    public void releaseRead() {
        synchronized (lock) {
            Thread curr = Thread.currentThread();
            if (!readers.containsKey(curr)) {
                throw new IllegalMonitorStateException("Thread does not hold lock");
            }

            activeReaders--;
            if (readers.get(curr) == 0) {
                readers.remove(curr);
            } else {
                readers.put(curr, readers.get(curr) - 1);
            }

            if (activeReaders == 0) {
                // if there are any waiting writers, notify them
                if (waitingWriters > 0) {
                    writerWaitCV.notifyAll();
                } else if (waitingReaders > 0) {
                    // sanity check -> i don't think this should ever happen
                    readerWaitCV.notifyAll();
                }
            }
        }
    }

    /**
     * Acquires the lock for writing.
     *
     * @throws java.lang.IllegalStateException if the lock is currently held for
     * reading.
     */
    public void acquireWrite() {
        synchronized (lock) {
            waitingWriters++;
            Thread curr = Thread.currentThread();

            if (readers.containsKey(curr)) {
                throw new IllegalStateException("read lock trying to acquire lock for writing");
            }

            // check and see if we already hold the lock
            if (!curr.equals(writer)) {
                // wait on the following conditions:
                // 1. there are active readers
                // 2. there is an active writer
                // 3. there are waiting readers and the last op was a write
                // 4. we are not the first writer in the queue
                waitingWriterQueue.add(curr);
                while (activeReaders > 0
                        || activeWriters > 0
                        || (waitingReaders > 0 && !lastOpWasRead)
                        || !curr.equals(waitingWriterQueue.peek())) {
                    writerWaitCV.awaitUninterruptibly();
                }
                waitingWriterQueue.remove();
            }
            lastOpWasRead = false;
            writer = curr;
            waitingWriters--;
            activeWriters++;
        }
    }

    /**
     * @return True if the current thread holds the lock for writing, otherwise false.
     */
    private boolean doIHoldWriteLock() {
        synchronized (lock) {
            return Thread.currentThread().equals(writer);
        }
    }

    /**
     * Releases the lock for reading.
     *
     * @throws IllegalMonitorStateException if the current thread does not hold
     * the lock.
     */
    public void releaseWrite() {
        synchronized (lock) {
            Thread curr = Thread.currentThread();
            if (!curr.equals(writer)) {
                throw new IllegalMonitorStateException("thread does not hold lock");
            }
            activeWriters--;
            if (activeWriters == 0) {
                writer = null;
                // if there are any waiting readers, notify them
                if (waitingReaders > 0) {
                    readerWaitCV.notifyAll();
                } else if (waitingWriters > 0) {
                    // otherwise, notify any waiting writers
                    writerWaitCV.notifyAll();
                }
            }
        }
    }

}
