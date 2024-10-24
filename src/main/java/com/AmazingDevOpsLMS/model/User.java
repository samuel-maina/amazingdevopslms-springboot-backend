/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AmazingDevOpsLMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author samuel
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Id
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String address;
    @Column(name = "enabled")
    private boolean enabled;  // is account enabled 
    @Column(name = "locked")
    private boolean locked; // is account locked

    private String password;

    private int activationCode;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<UserRole> userRoles; //list of userRoles assigned to te user
    @JsonIgnore
    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Message> senderMessages;
    @JsonIgnore
    @OneToMany(mappedBy = "receiver", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Message> receiverMessages;

    @OneToOne
    private Image image;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public int getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(int activationCode) {
        this.activationCode = activationCode;
    }

    public Set<Message> getSenderMessages() {
        return senderMessages;
    }

    public void setSenderMessages(Set<Message> senderMessages) {
        this.senderMessages = senderMessages;
    }

    public Set<Message> getReceiverMessages() {
        return receiverMessages;
    }

    public void setReceiverMessages(Set<Message> receiverMessages) {
        this.receiverMessages = receiverMessages;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
