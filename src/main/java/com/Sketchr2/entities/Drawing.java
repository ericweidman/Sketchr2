package com.Sketchr2.entities;

import javax.persistence.*;

/**
 * Created by ericweidman on 5/2/17.
 */

@Entity
@Table(name = "drawings")
public class Drawing {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10485760)
    private String fileName;

    @ManyToOne
    private User user;

    public Drawing() {
    }

    public Drawing(String title, String fileName, User user) {
        this.title = title;
        this.fileName = fileName;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
