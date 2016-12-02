package com.data.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.data.processor.core.Initiator;
import com.data.processor.util.ProcessingQueueImpl;
import com.data.processor.watcherService.WatcherService;

public abstract class AbstractDataProcessor implements DataProcessor {

    private static final Logger log = LoggerFactory.getLogger(AbstractDataProcessor.class);
    private ProcessingQueueImpl queue = new ProcessingQueueImpl();
    private int threads;
    private List<Initiator> taskList;

    private ExecutorService executorService;

    public AbstractDataProcessor(int threadCount) {
        this.threads = threadCount;
        taskList = addExecTasks(executorService, threads, queue);
    }

    public AbstractDataProcessor() {
        // Default thread count
        this(2);
    }

    public abstract List<Initiator> addExecTasks(ExecutorService service, int threads, ProcessingQueueImpl queue);

    @Override
    public void startProcess(String inputDir, String outDir) {
        try {
            WatcherService watcherService = new WatcherService(inputDir, true);
            while (true) {
                Map<Kind<?>, String> map = watcherService.getChangedFilePaths();
                for (Map.Entry<Kind<?>, String> entry : map.entrySet()) {
                    File file = new File(entry.getValue());
                    eventTrigger(entry.getKey(), file, inputDir, outDir);
                }
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error occured due to:{} ", e.getCause());
        }
    }

    @Override
    public void stopProcess() {
        for (Initiator initiator : taskList) {
            initiator.stopTask();
        }
        executorService.shutdown();
    }

    private void eventTrigger(Kind<?> watchEventKind, File file, String inDir, String outDir) throws IOException {
        String filePath = file.getPath();
        String ext = FilenameUtils.getExtension(filePath);
        Files.createDirectories(Paths.get(outDir));
        String outPutFilePath = filePath.replace(inDir, outDir).replace("." + ext, ".json");
        File outfile = new File(outPutFilePath);
        outfile.getParentFile().mkdirs();
        boolean bol = (StringUtils.isNoneBlank(ext));
        if (watchEventKind == StandardWatchEventKinds.OVERFLOW) {
            log.warn("Overflow of the file: {}", filePath);
        } else if (watchEventKind == StandardWatchEventKinds.ENTRY_CREATE) {
            log.info("Created a new file :{}", filePath);
        } else if (watchEventKind == StandardWatchEventKinds.ENTRY_DELETE) {
            log.debug("Deletion of the file: {}", filePath);
        } else if (watchEventKind == StandardWatchEventKinds.ENTRY_MODIFY) {
            if (bol) {
                log.info("Modification of the file :{}", filePath, " has happened");
                startProcessingSingleFile(filePath, outPutFilePath);
            }
        }
    }

    public ProcessingQueueImpl getQueue() {
        return queue;
    }

    /**
     * 
     * @param filePathLocation
     * @param outPutFileLocation
     */
    private void startProcessingSingleFile(String filePath, String outPutFileLocation) {
        DefaultProcessingDetails factSlidesDetails = new DefaultProcessingDetails(filePath, outPutFileLocation);
        String rawContent = null;
        try {
            rawContent = FileUtils.readFileToString(new File(filePath));
        } catch (IOException e) {
            // TODO: collect all the files which are not able to be read
            log.error("Error in processing the file: {} due to:{}", filePath, e.getMessage());
        }
        if (rawContent != null) {
            factSlidesDetails.setRawContent(rawContent);
            queue.enqueue(factSlidesDetails);
        }
    }
}
