package com.data.processor.didUKnow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.data.processor.AbstractDataProcessor;
import com.data.processor.core.Initiator;
import com.data.processor.util.ProcessingQueueImpl;

public class DidYouKnowDataProcessor extends AbstractDataProcessor {

    @Override
    public List<Initiator> addExecTasks(ExecutorService service, int threads, ProcessingQueueImpl queue) {
        service = Executors.newFixedThreadPool(threads);
        List<Initiator> taskList = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            Initiator initiator = new Initiator(new DidYouKnowProcessor(), queue);
            service.submit(initiator);
            taskList.add(initiator);
        }
        return taskList;
    }
}
