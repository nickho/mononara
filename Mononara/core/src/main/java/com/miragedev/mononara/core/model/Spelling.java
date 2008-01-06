package com.miragedev.mononara.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 2, 2008
 * Time: 8:08:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Spelling {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id = -1;

    private String spelling;

    /*
    @ManyToOne(cascade={CascadeType.ALL})
    private DictionnaryEntry dictionnaryEntry;
    */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    /*
    public DictionnaryEntry getDictionnaryEntry() {
        return dictionnaryEntry;
    }

    public void setDictionnaryEntry(DictionnaryEntry dictionnaryEntry) {
        this.dictionnaryEntry = dictionnaryEntry;
    }
    */
}
