package com.petcorner.profileservice.upload;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();

    public void save(MultipartFile file, String name);

    public Resource load(String filename);

    public void delete(String filename) throws IOException;

    public void deleteAll();

    public Stream<Path> loadAll();
}
