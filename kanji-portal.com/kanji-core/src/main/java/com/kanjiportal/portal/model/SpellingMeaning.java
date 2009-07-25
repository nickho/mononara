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

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 13, 2009
 * Time: 5:26:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(
        name = "tbsplmng",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"idspl", "idmng"})}
)
public class SpellingMeaning extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idspl")
    private Spelling spelling;

    @ManyToOne
    @JoinColumn(name = "idmng")
    private Meaning meaning;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Spelling getSpelling() {
        return spelling;
    }

    public void setSpelling(Spelling spelling) {
        this.spelling = spelling;
    }

    public Meaning getMeaning() {
        return meaning;
    }

    public void setMeaning(Meaning meaning) {
        this.meaning = meaning;
    }
}
