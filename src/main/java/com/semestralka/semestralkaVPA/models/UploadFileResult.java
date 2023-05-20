package com.semestralka.semestralkaVPA.models;

public class UploadFileResult {
    private final boolean success;
    private final long fileId;

    public boolean isSuccess() {
        return success;
    }

    public long getFileId() {
        return fileId;
    }

    public UploadFileResult(boolean success, long fileId) {
        this.success = success;
        this.fileId = fileId;
    }
}
