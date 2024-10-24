package com.AmazingDevOpsLMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Class defines a user role object
 *
 * @author Samuel Maina
 *
 * Role.java
 *
 * 10-10-2021
 *
 * @version 1.0
 */
@Table(name = "roles_t")
@Entity
public class Role {

    @Id
    private int id;

    @Column(name = "role_type")
    private Roles roleType; // defines type of role
    @Column
    private String description; //short description of the role

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<UserRole> userRoles; //list of userRoles

    /**
     *
     * @return roleType
     */
    public Roles getRoleType() {
        return roleType;
    }

    /**
     *
     * @param roleType
     */
    public void setRoleType(Roles roleType) {
        this.roleType = roleType;
    }

    /**
     *
     * @return #undefined
     */
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    /**
     *
     * @param userRoles
     */
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
}
