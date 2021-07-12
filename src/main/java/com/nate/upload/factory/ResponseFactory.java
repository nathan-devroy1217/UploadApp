package com.nate.upload.factory;

import com.nate.upload.constants.ProjectConstants;
import com.nate.upload.model.ApiResponse;
import com.nate.upload.util.ProjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public class ResponseFactory {

    public static ApiResponse buildApiSuccessResponse(List<MultipartFile> files) {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .fileName(ProjectUtils.getFileNames(files))
                .message(ProjectConstants.FILE_UPLOAD_SUCCESS)
                .timestamp(new Date())
                .build();
    }
}
