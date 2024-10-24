package com.AmazingDevOpsLMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 *
 * @author samuel
 */
@Entity
public class Chat {

    @Id
    private String id;
    private Date date;
    private String about;
    private String fromm;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @OrderBy(value = "date asc ")
    private List<Message> message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public String getFrom() {
        return fromm;
    }

    public void setFrom(String fromm) {
        this.fromm = fromm;
    }
    
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Chat{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", about=").append(about);
        sb.append(", message=").append(message);
        sb.append('}');
        return sb.toString();
    }
    
}
