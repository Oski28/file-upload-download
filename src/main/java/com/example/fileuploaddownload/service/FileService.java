package com.example.fileuploaddownload.service;


import com.example.fileuploaddownload.exception.FileNotFoundException;
import com.example.fileuploaddownload.exception.FileStorageException;
import com.example.fileuploaddownload.model.DataBaseFile;
import com.example.fileuploaddownload.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public DataBaseFile saveFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Invalid filename.");
            }

            DataBaseFile dataBaseFile = new DataBaseFile(fileName, file.getContentType(), file.getBytes());

            return fileRepository.save(dataBaseFile);
        } catch (IOException e) {
            throw new FileStorageException("Save file error.", e);
        }
    }

    public DataBaseFile getFile(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
}
