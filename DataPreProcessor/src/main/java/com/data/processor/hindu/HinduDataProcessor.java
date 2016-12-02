package com.data.processor.hindu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.data.processor.AbstractDataProcessor;
import com.data.processor.core.Initiator;
import com.data.processor.util.ProcessingQueueImpl;

public class HinduDataProcessor extends AbstractDataProcessor {

    public HinduDataProcessor() {
        super(3);
    }

    @Override
    public List<Initiator> addExecTasks(ExecutorService service, int threads, ProcessingQueueImpl queue) {
        List<Initiator> taskList = new ArrayList<>();
        service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            Initiator initiator = new Initiator(new HinduProcessor(), queue);
            service.submit(initiator);
        }
        return taskList;
    }
}
