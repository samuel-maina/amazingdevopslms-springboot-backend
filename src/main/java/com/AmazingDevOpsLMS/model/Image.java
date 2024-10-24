package com.AmazingDevOpsLMS.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author samuel
 */
@Entity
public class Image {

    @Id
    public String Id;
    public String bigImage;
    public String smallImage;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

}
