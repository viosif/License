package com.fortech.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by iosifvarga on 07.07.2017.
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_roles_id")
    private UserRoles userRoles;

    public User(){}
    public User(String username, String password,  UserRoles userRoles) {
        this.username = username;
        this.password = password;
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", username=\"" + username + '\"' +
                ", password=\"" + password + '\"' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }
}
