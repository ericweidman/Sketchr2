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
    private byte[] drawingAsString;

    @ManyToOne
    private User user;

    public Drawing() {
    }

    public Drawing(String title, byte[] drawingAsString, User user) {
        this.title = title;
        this.drawingAsString = drawingAsString;
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

    public byte[] getDrawingAsString() {
        return drawingAsString;
    }

    public void setDrawingAsString(byte[] drawingAsString) {
        this.drawingAsString = drawingAsString;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
