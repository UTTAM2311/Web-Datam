package com.data.processor.util;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.data.processor.ProcessingDetails;
import com.data.processor.ProcessingQueue;

public class ProcessingQueueImpl implements ProcessingQueue<ProcessingDetails> {

    private static final Logger log = LoggerFactory.getLogger(ProcessingQueueImpl.class);

    private BlockingQueue<ProcessingDetails> fileQueue;

    public ProcessingQueueImpl() {
        this.fileQueue = new LinkedBlockingQueue<ProcessingDetails>();
    }

    @Override
    public void enqueue(ProcessingDetails element) {
        Verify.notNull(element);

        fileQueue.add(element);
        log.debug("Enqueue filePath : {}", element);

    }

    @Override
    public void enqueue(List<ProcessingDetails> elements) {
        Verify.notNull(elements);

        fileQueue.addAll(elements);
        log.debug("Enqueue multiple processing files, in total : {}", elements.size());

    }

    @Override
    public ProcessingDetails dequeue() {
        try {
            ProcessingDetails element = fileQueue.take();
            log.debug("Dequeue  element : {}", element);
            return element;
        } catch (InterruptedException e) {
            log.error(e.getMessage() + e);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return fileQueue.isEmpty();
    }
}
