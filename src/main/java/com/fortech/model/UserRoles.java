package com.fortech.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by iosifvarga on 07.07.2017.
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "role")
    private String role;

    public UserRoles() {
    }

    public UserRoles(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", role=\"" + role + '\"' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
