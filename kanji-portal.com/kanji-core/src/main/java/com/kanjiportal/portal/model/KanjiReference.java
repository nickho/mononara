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
 * Date: Feb 16, 2009
 * Time: 9:24:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(
        name = "tbkanref",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"idkan", "idref"})
        }
)
public class KanjiReference extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idkan")
    private Kanji kanji;

    @ManyToOne
    @JoinColumn(name = "idref")
    private Reference reference;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
