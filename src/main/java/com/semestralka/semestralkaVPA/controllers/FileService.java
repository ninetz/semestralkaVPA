package com.semestralka.semestralkaVPA.controllers;

import com.semestralka.semestralkaVPA.entities.FilesModel;
import com.semestralka.semestralkaVPA.models.UploadFileResult;
import com.semestralka.semestralkaVPA.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    public Optional<FilesModel> getFile(long id) {
        return fileRepository.findById(id);
    }

    public UploadFileResult uploadFile(byte[] file, String filename) {
        FilesModel filesModel = new FilesModel();
        filesModel.setFile(file);
        filesModel.setFilename(filename);
        FilesModel resultModel = fileRepository.save(filesModel);
        return new UploadFileResult(true, resultModel.getId());
    }
}
