package com.example.fileuploaddownload.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class DataBaseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;

    public DataBaseFile() {
    }

    public DataBaseFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataBaseFile)) return false;
        DataBaseFile file = (DataBaseFile) o;
        return Objects.equals(id, file.id) &&
                Objects.equals(fileName, file.fileName) &&
                Objects.equals(fileType, file.fileType) &&
                Arrays.equals(data, file.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fileName, fileType);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
