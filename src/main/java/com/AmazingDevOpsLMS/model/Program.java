package com.AmazingDevOpsLMS.model;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
public class Program {

    @Id
    private String Id;
    @Column(unique = true)
    private String programId;
    @Column(unique = true)
    private String name;
    @Column(length = 1000)
    private String description;

    private int cost;

    //@JsonIgnore
    @OneToMany(mappedBy = "program", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Session> sessions;

    @ManyToMany
    @JoinTable(name = "student_programs", joinColumns = @JoinColumn(name = "student"), inverseJoinColumns = @JoinColumn(name = "program"))
    private List<Student> student;

    @OneToMany(mappedBy = "program", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Payments> payments;
    @OneToOne
    private Image image;
    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public List<Student> getStudent() {
        return student;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    

}
