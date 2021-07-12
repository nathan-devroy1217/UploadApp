package com.nate.upload.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectUtils {
    public static String getFileNames(List<MultipartFile> files) {
        return files.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
    }
}
