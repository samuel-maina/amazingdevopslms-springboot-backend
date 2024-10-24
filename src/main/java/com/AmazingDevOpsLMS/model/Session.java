package com.AmazingDevOpsLMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author samuel
 */
@Entity
//@Table(uniqueConstraints = {
//@UniqueConstraint(columnNames = {"course", "sessionStart"})})
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JoinColumn(name = "program")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Program program;

    @OneToMany(mappedBy = "session", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Course> courses;

    public Program getProgram() {
        return program;
    }

    public String getProgramName() {
        return program.getName();
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "Session{" + "id=" + id + ", program=" + program + ", courses=" + courses + '}';
    }

    public Long getSessionId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }


    

}
