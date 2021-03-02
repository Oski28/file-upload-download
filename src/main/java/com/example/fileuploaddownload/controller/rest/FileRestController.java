package com.example.fileuploaddownload.controller.rest;

import com.example.fileuploaddownload.model.DataBaseFile;
import com.example.fileuploaddownload.payload.Response;
import com.example.fileuploaddownload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileRestController {

    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping()
    public List<Response> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files).stream().map(file -> fileService.uploadFile(file)).collect(Collectors.toList());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Response> getAllDataBaseFiles() {
        return fileService.getAllFilesList();
    }

    @GetMapping(path = "/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId, HttpServletRequest request) {

        DataBaseFile dataBaseFile = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dataBaseFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dataBaseFile.getFileName() + "\"")
                .body(new ByteArrayResource(dataBaseFile.getData()));
    }

    @DeleteMapping("/{fileId}")
    public void deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
    }
}
