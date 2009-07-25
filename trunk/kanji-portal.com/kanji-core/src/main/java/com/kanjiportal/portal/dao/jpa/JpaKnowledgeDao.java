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

import com.kanjiportal.portal.dao.KnowledgeDao;
import com.kanjiportal.portal.model.Knowledge;
import com.kanjiportal.portal.model.Tag;
import com.kanjiportal.portal.model.User;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 10:58:39 PM
 * To change this template use File | Settings | File Templates.
 */
@AutoCreate
@Name("knowledgeDao")
public class JpaKnowledgeDao implements KnowledgeDao {

    @In
    private EntityManager entityManager;

    @Logger
    private Log logger;

    public Knowledge findByKanjiAndTagForUser(String kanji, String tag, User user) {
        List<Knowledge> list = entityManager.createQuery("select kw from Knowledge kw where kw.kanji.kanji like :kanji and kw.tag.name like :tag and kw.user.id = :username")
                .setParameter("kanji", kanji)
                .setParameter("tag", tag)
                .setParameter("username", user.getUsername())
                .getResultList();
        if (list.size() == 0) {
            return null;
        } else if (list.size() > 1) {
            logger.warn("Search by kanji (#0) and tag (#1) should return only one Knowledge, but #2 were found", kanji, tag, list.size());
            return list.get(0);
        } else {
            return list.get(0);
        }
    }

    public List<Knowledge> findByTagForUser(Tag tag, User user, int page, int pageSize) {
        List<Knowledge> list = entityManager.createQuery("select kw from Knowledge kw where kw.tag.name like :tag and kw.user.id = :username")
                .setParameter("tag", tag.getName())
                .setParameter("username", user.getUsername())
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
        return list;
    }


    public Knowledge update(Knowledge knowledge) {
        return entityManager.merge(knowledge);
    }

    public void create(Knowledge knowledge) {
        entityManager.persist(knowledge);
    }

    public void delete(Knowledge knowledge) {
        entityManager.remove(knowledge);
    }
}
