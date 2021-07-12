package com.nate.upload.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApiResponse {
    private Date timestamp;
    private String fileName;
    private int status;
    private String message;
}
