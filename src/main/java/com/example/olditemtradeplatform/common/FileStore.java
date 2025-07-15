package com.example.olditemtradeplatform.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.upload-dir:uploads/}")
    private String uploadPath;

    public String saveFile(MultipartFile file) throws IOException {
        // 🔥 절대 경로로 변환
        File dir = new File(uploadPath).getAbsoluteFile();
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) throw new RuntimeException("업로드 폴더 생성 실패");
        }

        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String savedName = uuid + "_" + originalFilename;

        File target = new File(dir, savedName);
        file.transferTo(target);

        return "/uploads/" + savedName;  // 브라우저 접근용 URL
    }
}
