package com.AmazingDevOpsLMS.model;
import javax.persistence.Entity;

/**
 *
 * @author samuel
 */
@Entity
public class Administrator extends User{
    private boolean happy;

    public boolean isHappy() {
        return happy;
    }

    public void setHappy(boolean happy) {
        this.happy = happy;
    }
    
    
    
}
