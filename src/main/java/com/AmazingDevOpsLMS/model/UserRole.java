package com.AmazingDevOpsLMS.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Defines user and role relation/assignment
 * 
 * UserRole.java
 * 
 * @author Samuel Maina
 * 
 * 12-10-2021
 * 
 * @version 1.0
 */
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"role", "user"})})
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // unique user-role assignment identifier
    //@JsonIgnore
    @JoinColumn(name="user")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private User user; // user object/instance
@JoinColumn(name="role")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Role role; // role object/instance

    /**
     *
     * @return userId
     */
    public String getUser() {
        return user.getEmail();
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return roleTypes
     */
    public Roles getUserRole() {
        return role.getRoleType();

    }

    /**
     *
     * @param roles
     */
    public void setRoles(Role role) {
        this.role = role;
    }

    /**
     *
     * @return id
     */
    public long getId() {
        return id;
    }
    




}
