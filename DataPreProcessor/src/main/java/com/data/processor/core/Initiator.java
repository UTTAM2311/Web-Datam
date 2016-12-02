package com.data.processor.core;

import com.data.processor.ProcessingDetails;
import com.data.processor.Processor;
import com.data.processor.util.ProcessingQueueImpl;

public class Initiator implements Runnable {

    private Processor processor;
    private ProcessingQueueImpl queue;
    boolean start = true;

    public Initiator(Processor processor, ProcessingQueueImpl queue) {
        this.processor = processor;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (start) {
            ProcessingDetails processingDetails = queue.dequeue();
            processor.process(processingDetails);
        }

    }

    public void stopTask() {
        start = false;
    }

}
