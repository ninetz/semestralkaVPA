package com.semestralka.semestralkaVPA.entities;

import com.google.gson.Gson;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class FilesModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    byte[] file;
    String filename;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    @JoinColumn(name = "user.id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
