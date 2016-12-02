package com.data.processor;

import java.util.List;

public interface ProcessingQueue<E> {

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
     * Take the element out of the Queue.
     * 
     * @return the first element in the Queue.
     */
    public E dequeue();


    /**
     * Check if the Queue is empty.
     * 
     * @return true if the Queue is empty.
     */
    public boolean isEmpty();

}
