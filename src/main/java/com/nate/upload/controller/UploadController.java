package com.nate.upload.controller;

import com.jcraft.jsch.JSchException;
import com.nate.upload.exception.CustomFileException;
import com.nate.upload.factory.ResponseFactory;
import com.nate.upload.model.ApiResponse;
import com.nate.upload.service.SftpFileUploadService;
import com.nate.upload.service.StandardFileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("data/v1")
@Slf4j
public class UploadController {

    private final SftpFileUploadService fileUploadService;
    private final StandardFileUploadService standardFileUploadService;

    @Autowired
    public UploadController(SftpFileUploadService fileUploadService, StandardFileUploadService standardFileUploadService) {
        this.fileUploadService = fileUploadService;
        this.standardFileUploadService = standardFileUploadService;
    }

    @PostMapping(value = "/sftp/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadFileSftp(@RequestPart("file") List<MultipartFile> files) throws JSchException, CustomFileException {
        log.info("Inside UploadController.uploadFileSftp()");
        Instant start = Instant.now();

        fileUploadService.uploadFiles(files);

        Instant finish = Instant.now();
        log.info("Time elapsed for API call: {}ms", Duration.between(start, finish).toMillis());

        return ResponseEntity.ok(
                ResponseFactory.buildApiSuccessResponse(files)
        );
    }

    @PostMapping(value = "/standard/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadFileStandard(@RequestPart("file") List<MultipartFile> files) throws JSchException, CustomFileException, IOException {
        log.info("Inside UploadController.uploadFileStandard()");
        Instant start = Instant.now();

        standardFileUploadService.uploadFiles(files);

        Instant finish = Instant.now();
        log.info("Time elapsed for API call: {}ms", Duration.between(start, finish).toMillis());

        return ResponseEntity.ok(
                ResponseFactory.buildApiSuccessResponse(files)
        );
    }
}
