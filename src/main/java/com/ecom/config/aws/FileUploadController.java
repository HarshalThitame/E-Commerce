package com.ecom.config.aws;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUploadController {

    private final S3Service s3Service;

    public FileUploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

//    @PostMapping("/auth/upload")
//    public String uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
//            Files.write(tempFile, file.getBytes());
//            String eTag = s3Service.uploadFile(tempFile.toString());
//            Files.delete(tempFile);
//            return "File uploaded successfully with ETag: " + eTag;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload file", e);
//        }
//    }
}
