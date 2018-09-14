package com.sh.crm.services.storage;

public class UploadFileResponse {
    private String fileName;
    private String fileType;
    private long size;
    private String orignalFileName;

    public UploadFileResponse() {
    }

    public UploadFileResponse(String fileName, String fileType, long size, String orignalFileName) {
        this.fileName = fileName;

        this.fileType = fileType;
        this.size = size;
        this.orignalFileName = orignalFileName;

    }

    public String getOrignalFileName() {
        return orignalFileName;
    }

    public void setOrignalFileName(String orignalFileName) {
        this.orignalFileName = orignalFileName;
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


}