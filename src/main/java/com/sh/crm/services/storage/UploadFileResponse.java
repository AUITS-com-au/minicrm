package com.sh.crm.services.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

public class UploadFileResponse {
    private String fileName;
    private String fileType;
    private long size;
    private String originalFileName;

    @JsonIgnore
    private byte[] content;

    public UploadFileResponse() {
    }

    public UploadFileResponse(String fileName, String fileType, long size, String orignalFileName) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.originalFileName = orignalFileName;

    }

    public String getOrignalFileName() {
        return originalFileName;
    }

    public void setOrignalFileName(String orignalFileName) {
        this.originalFileName = orignalFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UploadFileResponse{" +
                "fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", size=" + size +
                ", originalFileName='" + originalFileName + '\'' +

                '}';
    }
}