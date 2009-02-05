/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.model;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Spelling
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement Spelling
 */
@Entity
@Table(name = "spellingtype")
public class SpellingType {

    private long id;
    private String name;
    private String description;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @Length(max = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Length(max = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
