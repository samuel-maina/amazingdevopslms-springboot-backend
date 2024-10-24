package com.AmazingDevOpsLMS.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Nationalized;

/**
 *
 * @author samuel
 */
@Entity
public class Blog {

    @Id
    private String id;
    private String title;
    private String about;
    private LocalDate date;
    private String contributor;
    @Nationalized
    @Lob
    private String body;
    
    @OneToOne(mappedBy = "blog")
    private ArticleHeaderImage headerImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArticleHeaderImage getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(ArticleHeaderImage headerImage) {
        this.headerImage = headerImage;
    }

}
