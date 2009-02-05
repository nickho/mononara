package com.kanjiportal.portal.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 1:24:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class Audit {

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updateDate;


    @PrePersist
    public void updateCreationDate() {
        if (creationDate == null) {
            creationDate = Calendar.getInstance();
        }
        creationDate.setTimeInMillis(System.currentTimeMillis());
    }

    @PreUpdate
    public void updateUpdateDate() {
        if (updateDate == null) {
            updateDate = Calendar.getInstance();
        }
        updateDate.setTimeInMillis(System.currentTimeMillis());
    }

}
