package com.data.processor.watcherService;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WatcherService {

    private WatchService watchService;
    private String path;
    private boolean enableSubWatcher;


    public WatcherService(String pathToWatch, boolean enableWatherForSubFolders) throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        this.path = pathToWatch;
        this.enableSubWatcher = enableWatherForSubFolders;
    }

    public Map<Kind<?>, String> getChangedFilePaths() throws InterruptedException, IOException {
        Path watchDir = Paths.get(path);
        if (enableSubWatcher) {
            registerRecursive(watchDir);
        }
        Map<Kind<?>, String> map = new HashMap<>();
        WatchKey key = watchService.take();
        Path dir = (Path) key.watchable();
        List<WatchEvent<?>> keys = key.pollEvents();
        for (WatchEvent<?> watchEvent : keys) {

            Kind<?> watchEventKind = watchEvent.kind();
            Path path = dir.resolve((Path) watchEvent.context());
            map.put(watchEventKind, path.toFile().getPath());
        }
        key.reset();
        return map;
    }

    public Map<Kind<?>, File> getChangedFile() throws InterruptedException, IOException {
        Path watchDir = Paths.get(path);
        if (enableSubWatcher) {
            registerRecursive(watchDir);
        }
        Map<Kind<?>, File> map = new HashMap<>();
        WatchKey key = watchService.take();
        Path dir = (Path) key.watchable();
        List<WatchEvent<?>> keys = key.pollEvents();
        for (WatchEvent<?> watchEvent : keys) {

            Kind<?> watchEventKind = watchEvent.kind();
            Path path = dir.resolve((Path) watchEvent.context());
            map.put(watchEventKind, path.toFile());
        }
        key.reset();
        return map;
    }

    /**
     * register all subfolders in the root
     * 
     * @param root
     * @throws IOException
     */
    private void registerRecursive(Path root) throws IOException {
        // register all subfolders
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
