/**
 * This file is part of kanji-portal.com.
 *
 * kanji-portal.com is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * kanji-portal.com is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with kanji-portal.com.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2008-2009 Nicolas Radde <nicolas@radde.org>
 */
package com.kanjiportal.portal.model;

import org.hibernate.validator.NotNull;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 13, 2009
 * Time: 6:02:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "tblan")
public class Language extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "lblan")
    private String name;

    @NotNull
    @Column(name = "cdiso63901")
    private String codeIso63901;

    @NotNull
    @Column(name = "cdiso63902")
    private String codeIso63902;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeIso63901() {
        return codeIso63901;
    }

    public void setCodeIso63901(String codeIso63901) {
        this.codeIso63901 = codeIso63901;
    }

    public String getCodeIso63902() {
        return codeIso63902;
    }

    public void setCodeIso63902(String codeIso63902) {
        this.codeIso63902 = codeIso63902;
    }
}
