package com.semestralka.semestralkaVPA.controllers;

import com.google.gson.Gson;
import com.semestralka.semestralkaVPA.entities.FilesModel;
import com.semestralka.semestralkaVPA.models.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@RestController
public class FileController {
    @Autowired
    FileService fileService;
    private final Gson gson = new Gson();


    @RequestMapping(method = RequestMethod.GET, value = "/file")
    public String getFileForm(@RequestParam("id") long id) {
        Optional<FilesModel> optionalFilesModel = fileService.getFile(id);
        if (optionalFilesModel.isEmpty()) {
            throw new RuntimeException();
        }
        return gson.toJson(optionalFilesModel.get());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/file/{id}")
    public ResponseEntity getFile(@PathVariable("id") long id) {
        Optional<FilesModel> optionalFilesModel = fileService.getFile(id);
        if (optionalFilesModel.isEmpty()) {
            throw new RuntimeException();
        }
        FilesModel filesModel = optionalFilesModel.get();
        ContentDisposition contentDisposition = ContentDisposition.attachment().filename(filesModel.getFilename()).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(headers).body(filesModel.getFile());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/file/generatelink")
    public String generateLink(@RequestParam("id") long id, UriComponentsBuilder uriComponentsBuilder) {
        return uriComponentsBuilder.path("/file/" + id).build().toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/file/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("filename") String filename,
                                             UriComponentsBuilder uriComponentsBuilder) {
        try {
            UploadFileResult uploadFileResult = fileService.uploadFile(file.getBytes(), filename);
            if (uploadFileResult.isSuccess()) {
                return ResponseEntity.ok().body(uriComponentsBuilder.path("/file/" + uploadFileResult.getFileId()).build().toString());
            }
        } catch (IOException e) {
            ResponseEntity.badRequest().body("Failed to upload file.");
        }
        throw new RuntimeException();

    }
}
