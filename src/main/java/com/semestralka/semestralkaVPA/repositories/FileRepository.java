package com.semestralka.semestralkaVPA.repositories;

import com.semestralka.semestralkaVPA.entities.FilesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FilesModel, Long> {
}
