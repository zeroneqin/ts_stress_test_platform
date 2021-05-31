package io.zeroneqin.base.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileContent implements Serializable {
    private String fileId;

    private byte[] file;

    private static final long serialVersionUID = 1L;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}