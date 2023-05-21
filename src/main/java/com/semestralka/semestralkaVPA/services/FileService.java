package com.semestralka.semestralkaVPA.services;

import com.semestralka.semestralkaVPA.entities.FilesModel;
import com.semestralka.semestralkaVPA.entities.User;
import com.semestralka.semestralkaVPA.models.UploadFileResult;
import com.semestralka.semestralkaVPA.repositories.FileRepository;
import com.semestralka.semestralkaVPA.repositories.UserRepository;
import com.semestralka.semestralkaVPA.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;
    @Autowired
    UserRepository userRepository;

    public Optional<FilesModel> getFile(long id) {
        return fileRepository.findById(id);
    }

    public UploadFileResult uploadFile(byte[] file, String filename, UserPrincipal principal) {
        FilesModel filesModel = new FilesModel();
        if (principal != null) {
            Optional<User> user = userRepository.findById(principal.getId());
            user.ifPresent(filesModel::setUser);
        }
        filesModel.setFile(file);
        filesModel.setFilename(filename);
        FilesModel resultModel = fileRepository.save(filesModel);
        return new UploadFileResult(true, resultModel.getId());
    }

    public boolean deleteFile(long id, UserPrincipal principal) {
        Optional<FilesModel> filesModel = fileRepository.findById(id);
        if (filesModel.isPresent()) {
            if (filesModel.get().getUser().getId() == principal.getId()) {
                fileRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}
