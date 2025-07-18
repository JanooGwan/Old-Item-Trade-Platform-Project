package com.example.olditemtradeplatform.postimage.service;

import com.example.olditemtradeplatform.global.exception.CustomException;
import com.example.olditemtradeplatform.postimage.exception.PostImageErrorCode;
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
            throw new CustomException(PostImageErrorCode.FILE_SAVE_FAILED);
        }
    }
}
