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
    private String filelocation;

    @ManyToOne
    private User user;

    public Drawing() {
    }

    public Drawing(String title, String filelocation, User user) {
        this.title = title;
        this.filelocation = filelocation;
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

    public String getFilelocation() {
        return filelocation;
    }

    public void setFilelocation(String filelocation) {
        this.filelocation = filelocation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
