package com.nate.upload.service.impl;

import com.nate.upload.config.properties.LocalStorageProperties;
import com.nate.upload.constants.ProjectConstants;
import com.nate.upload.exception.CustomFileException;
import com.nate.upload.factory.ExceptionFactory;
import com.nate.upload.service.StandardFileUploadService;
import com.nate.upload.util.ProjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.nate.upload.constants.ProjectConstants.FILE_NOT_EXISTS_ERROR;
import static com.nate.upload.constants.ProjectConstants.FILE_PROCESSING_ERROR;

@Service
@Slf4j
public class StandardFileUploadServiceImpl implements StandardFileUploadService {

    private final LocalStorageProperties properties;

    @Autowired
    public StandardFileUploadServiceImpl(LocalStorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public void uploadFiles(List<MultipartFile> files) throws IOException, CustomFileException {
        log.info("Inside FileUploadServiceImpl.uploadFiles() --> files: {}", ProjectUtils.getFileNames(files));

        List<CustomFileException> exceptions = new ArrayList<>();
        Path fileStorageLocation = Paths.get(properties.getFileLocation())
                .toAbsolutePath().normalize();

        createDirectory(fileStorageLocation);

        files.forEach(file -> processFile(exceptions, fileStorageLocation, file));

        handleExceptions(exceptions);
    }


    private void createDirectory(Path fileStorageLocation) throws IOException {
        if(!Files.exists(fileStorageLocation)) {
            Files.createDirectory(fileStorageLocation);
        }
    }

    private void processFile(List<CustomFileException> exceptions, Path fileStorageLocation, MultipartFile file) {
        String fileName = deriveFileName(file, exceptions);
        log.info("Processing file: {}", fileName);

        Path targetLocation = fileStorageLocation.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("File processed successfully --> {}", fileName);
        } catch (IOException e) {
            log.error(ProjectConstants.ERROR_PREFIX + e.getMessage());
            exceptions.add(ExceptionFactory.createCustomFileException(FILE_PROCESSING_ERROR));
        }
    }

    private String deriveFileName(MultipartFile file, List<CustomFileException> exceptions) {
        if(Objects.isNull(file.getOriginalFilename())) {
            exceptions.add(ExceptionFactory.createCustomFileException(FILE_NOT_EXISTS_ERROR));
        }
        return StringUtils.cleanPath(file.getOriginalFilename());
    }

    private void handleExceptions(List<CustomFileException> exceptions) throws CustomFileException {
        if(!CollectionUtils.isEmpty(exceptions)) {
            throw exceptions.get(0);
        }
    }
}
