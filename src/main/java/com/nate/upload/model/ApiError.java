package com.nate.upload.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ApiError {
    public Date timestamp;
    public int status;
    public String error;
}