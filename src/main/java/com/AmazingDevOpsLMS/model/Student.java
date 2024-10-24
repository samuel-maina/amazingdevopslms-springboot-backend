package com.AmazingDevOpsLMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author samuel
 */
@Entity
public class Student extends User {
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Payments> payments;
    @JsonIgnore
    @ManyToMany(mappedBy="student")
    private List<Program> programs;
    

     @OneToOne
    private VerificationToken token;

    public VerificationToken getToken() {
        return token;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }
    public Set<Payments> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payments> payments) {
        this.payments = payments;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public List<Program> getPrograms() {
        return programs;
    }

 

   

}
