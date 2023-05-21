package com.semestralka.semestralkaVPA.services;

import com.semestralka.semestralkaVPA.entities.FilesModel;
import com.semestralka.semestralkaVPA.entities.User;
import com.semestralka.semestralkaVPA.models.UploadFileResult;
import com.semestralka.semestralkaVPA.repositories.FileRepository;
import com.semestralka.semestralkaVPA.repositories.UserRepository;
import com.semestralka.semestralkaVPA.security.UserPrincipal;
import com.semestralka.semestralkaVPA.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    public boolean deleteFile(long id, Authentication auth) {
        Optional<FilesModel> filesModel = fileRepository.findById(id);
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        if (SecurityUtils.isUserInRole(auth, SecurityUtils.ADMINISTRATOR_ROLE)) {
            fileRepository.deleteById(id);
            return true;
        }
        if (filesModel.isPresent() && filesModel.get().getUser() != null) {
            long userId = filesModel.get().getUser().getId();
            if (userId == userPrincipal.getId()) {
                fileRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}
