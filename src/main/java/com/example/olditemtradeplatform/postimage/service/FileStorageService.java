package com.example.olditemtradeplatform.postimage.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String save(MultipartFile file);
}
