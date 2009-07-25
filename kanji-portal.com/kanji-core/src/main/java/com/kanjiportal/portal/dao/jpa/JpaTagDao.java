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
package com.kanjiportal.portal.dao.jpa;

import com.kanjiportal.portal.dao.TagDao;
import com.kanjiportal.portal.model.Tag;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 11:22:13 PM
 * To change this template use File | Settings | File Templates.
 */
@AutoCreate
@Name("tagDao")
public class JpaTagDao implements TagDao {

    @In
    private EntityManager entityManager;

    public Tag findByName(String tag) {
        return (Tag) entityManager.createQuery("select t from Tag t where t.name = :name")
                .setParameter("name", tag)
                .getSingleResult();
    }

    public Tag findById(long id) {
        return entityManager.find(Tag.class, id);
    }
}
