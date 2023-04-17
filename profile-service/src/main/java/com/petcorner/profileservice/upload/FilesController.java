package com.petcorner.profileservice.upload;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("/profile/v2/upload")
public class FilesController {

    @Autowired
    FilesStorageService storageService;
    private static String ANIMAL_THERAPY="therapy";
    private static String ANIMAL_ADOPT="adopt";
    private static String USER="user";
    @PostMapping("/add/{id}/{type}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String id, @PathVariable String type) {
        String message = "";
        String type_selected="";
        switch (type){
            case "therapy":
                type_selected=ANIMAL_THERAPY;
                break;
            case "adopt":
                type_selected=ANIMAL_ADOPT;
                break;
            default:
                type_selected=USER;
        }
        try {

            storageService.save(file, id, type_selected);

            message = "Uploaded the file successfully for user: " + id;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file for user: " + id + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/{id}/{type}")
    @ResponseBody
    public ResponseEntity<List<FileInfo>> getFilesFor(@PathVariable String id, @PathVariable String type) throws IOException {
        List<FileInfo> fileInfos = storageService.loadFilesFor(id,type).map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }



    @DeleteMapping("/delete/{filename:.+}")
    @ResponseBody
    public ResponseEntity deleteFile(@PathVariable String filename) throws IOException {
        storageService.delete(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body("delete "+filename);
    }



}