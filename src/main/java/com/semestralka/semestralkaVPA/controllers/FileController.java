package com.semestralka.semestralkaVPA.controllers;

import com.google.gson.Gson;
import com.semestralka.semestralkaVPA.entities.FilesModel;
import com.semestralka.semestralkaVPA.models.UploadFileResult;
import com.semestralka.semestralkaVPA.security.UserPrincipal;
import com.semestralka.semestralkaVPA.services.FileService;
import com.semestralka.semestralkaVPA.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@RestController()
@RequestMapping("/api")
public class FileController {
    @Autowired
    FileService fileService;
    private final Gson gson = new Gson();


    @RequestMapping(method = RequestMethod.GET, value = "/filejson")
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

    @RequestMapping(method = RequestMethod.GET, value = "/deletefile")
    public ResponseEntity<Object> deleteFile(@RequestParam("id") long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated() || SecurityUtils.isUserAnonymous(auth)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            if (fileService.deleteFile(id, (UserPrincipal) auth.getPrincipal())) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/file/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UploadFileResult uploadFileResult;
            if (!auth.isAuthenticated() || SecurityUtils.isUserAnonymous(auth)) {
                UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
                uploadFileResult = fileService.uploadFile(file.getBytes(), file.getOriginalFilename(), principal);
            } else {
                uploadFileResult = fileService.uploadFile(file.getBytes(), file.getOriginalFilename(), null);
            }
            if (uploadFileResult.isSuccess()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/home");
                return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload file.");
        }
        throw new RuntimeException();

    }
}
