package com.example.fileuploaddownload.service;


import com.example.fileuploaddownload.exception.FileNotFoundException;
import com.example.fileuploaddownload.exception.FileStorageException;
import com.example.fileuploaddownload.model.DataBaseFile;
import com.example.fileuploaddownload.payload.Response;
import com.example.fileuploaddownload.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    private DataBaseFile saveFile(MultipartFile file) {
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

    public Response uploadFile(@RequestParam("file") MultipartFile file) {
        DataBaseFile dataBaseFile = saveFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/")
                .path(dataBaseFile.getId().toString())
                .toUriString();

        return new Response(dataBaseFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    public DataBaseFile getFile(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }

    public void deleteFile(Long fileId) {
        fileRepository.deleteById(fileId);
    }

    public List<Response> getAllFilesList() {
        List<DataBaseFile> dataBaseFiles = fileRepository.findAll();
        List<Response> responses = new ArrayList<>();
        dataBaseFiles.forEach(dataBaseFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dataBaseFile.getId().toString())
                    .toUriString();
            responses.add(new Response(dataBaseFile.getId(), dataBaseFile.getFileName(), fileDownloadUri, dataBaseFile.getFileType(), dataBaseFile.getData().length));
        });
        return responses;
    }
}
