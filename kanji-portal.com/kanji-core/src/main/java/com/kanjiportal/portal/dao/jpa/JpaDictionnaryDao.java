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

import com.kanjiportal.portal.dao.DictionnaryDao;
import com.kanjiportal.portal.model.Dictionnary;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 5, 2009
 * Time: 10:08:29 PM
 * To change this template use File | Settings | File Templates.
 */
@AutoCreate
@Name("dictionnaryDao")
public class JpaDictionnaryDao implements DictionnaryDao {

    @In
    private EntityManager entityManager;

    public List<Dictionnary> findDictionnaryEntriesByPatternWithPaging(String pattern, int page, int pageSize) {
        List<Dictionnary> des = entityManager.createQuery("select d from Dictionnary d where d.description like :pattern or d.romaji like :pattern or d.kana like :pattern or d.kanji like :pattern")
                .setParameter("pattern", pattern)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
        return des;
    }
}
