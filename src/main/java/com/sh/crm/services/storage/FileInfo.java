package com.sh.crm.services.storage;

public class FileInfo {
    private String originalFileName;
    private String newFileName;
    private String fileExt;

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "originalFileName='" + originalFileName + '\'' +
                ", newFileName='" + newFileName + '\'' +
                ", fileExt='" + fileExt + '\'' +
                '}';
    }
}
