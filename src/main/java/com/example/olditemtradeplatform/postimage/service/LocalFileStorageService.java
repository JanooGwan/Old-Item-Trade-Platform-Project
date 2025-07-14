package com.example.olditemtradeplatform.postimage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    private final String uploadDir = "/your/path/to/uploads";

    @Override
    public String save(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String newFileName = uuid + "_" + originalFileName;
            Path path = Paths.get(uploadDir, newFileName);

            Files.createDirectories(path.getParent());
            file.transferTo(path.toFile());

            return "/images/" + newFileName;

        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
}
