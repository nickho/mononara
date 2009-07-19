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

import javax.persistence.*;

/**
 * Spelling
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement Spelling
 */
@Entity
@Table(name = "tbspltyp")
public class SpellingType extends Audit {

    private long id;
    private String code;
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
    @Column(name = "lbnme")
    public String getName() {
        return name;
    }

    @NotNull
    @Length(max = 50)
    @Column(name = "cdcde")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Length(max = 255)
    @Column(name = "lbdsc")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
