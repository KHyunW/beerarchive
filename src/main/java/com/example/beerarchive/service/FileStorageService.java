package com.example.beerarchive.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.Value;

@Service
public class FileStorageService {
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String store(MultipartFile file){
        if(file == null || file.isEmpty()){
            return null;
        }
        try {
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String savedFilename = UUID.randomUUID() + extension;

            Path targePath = uploadPath.resolve(savedFilename);
            Files.copy(file.getInputStream(), targePath);

            return "/images/" + savedFilename;
        } catch (IOException e){
            throw new RuntimeException("파일 저장 실패 : " + e.getMessage());
        }
    }
}
