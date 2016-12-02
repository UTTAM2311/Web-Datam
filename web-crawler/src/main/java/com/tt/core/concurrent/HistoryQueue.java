package com.tt.core.concurrent;

import java.util.List;

/**
 * Simple queue implementation, which keeps track of what's been in the queue(history). Otherwise
 * it's just a queue.
 * 
 * @param <E> Type of the Element in the Queue.
 */
public interface HistoryQueue<E> {


    /**
     * Add Element to the Queue.
     * 
     * @param e element to add to Queue.
     * @see #enqueueIfNew(Object)
     */
    public void enqueue(E element);

    /**
     * Add Elements to the Queue.
     * 
     * @param elements List of elements to add to Queue.
     * @see #enqueueIfNew(List)
     */
    public void enqueue(List<E> elements);



    /**
     * Add Element to the Queue Only if it wasn't added earlier, i.e., verify history and enqueue
     * only if it isn't in history.
     * 
     * @param e element to add to Queue.
     * @see #enqueue(Object)
     */
    public void enqueueIfNew(E element);

    /**
     * Add Elements to the Queue Only if they weren't added earlier, i.e., verify history and
     * enqueue.
     * 
     * @param elements List of elements to add to Queue.
     * @see #enqueue(List)
     */
    public void enqueueIfNew(List<E> elements);



    /**
     * Take the element out of the Queue.
     * 
     * @return the first element in the Queue.
     */
    public E dequeue();



    /* --- History Related --- */

    /**
     * Check if the Queue is empty.
     * 
     * @return true if the Queue is empty.
     */
    public boolean isEmpty();

    /**
     * Check if an element was in the Queue.
     * 
     * @param e element to be verified
     * @return true if the element was in the Queue earlier.
     */
    public boolean wasInQueue(E e);


}
