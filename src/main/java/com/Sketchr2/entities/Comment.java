package com.Sketchr2.entities;

import javax.persistence.*;

/**
 * Created by ericweidman on 5/2/17.
 */

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    String comment;

    @ManyToOne
    Drawing drawing;

    public Comment() {
    }

    public Comment(int id, String comment, Drawing drawing) {
        this.id = id;
        this.comment = comment;
        this.drawing = drawing;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Drawing getDrawing() {
        return drawing;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }
}
