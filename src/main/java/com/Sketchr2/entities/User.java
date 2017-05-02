package com.Sketchr2.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by ericweidman on 5/2/17.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;


    @Column(nullable = false, unique = true)
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String passwordHash;

    public User() {
    }

    public User(int id, String userName, String passwordHash) {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
