package com.example.fileuploaddownload.controller.web;

import com.example.fileuploaddownload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Controller
public class FileController {

    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("files", fileService.getAllFilesList());
        return "index";
    }

    @DeleteMapping("/{fileId}")
    public String deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return "redirect:/";
    }

    @PostMapping()
    public String addFiles(@RequestParam("files") MultipartFile[] files) {
        Arrays.stream(files).forEach(file -> {
            fileService.uploadFile(file);
        });
        return "redirect:/";
    }
}
