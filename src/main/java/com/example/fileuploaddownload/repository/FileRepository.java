package com.example.fileuploaddownload.repository;

import com.example.fileuploaddownload.model.DataBaseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<DataBaseFile, Long> {
}
