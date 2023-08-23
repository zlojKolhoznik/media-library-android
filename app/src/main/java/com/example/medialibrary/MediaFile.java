package com.example.medialibrary;

public class MediaFile {
    private int id;
    private String filename;
    private String type;

    public MediaFile(int id, String filename, String type) {
        this.id = id;
        this.filename = filename;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
