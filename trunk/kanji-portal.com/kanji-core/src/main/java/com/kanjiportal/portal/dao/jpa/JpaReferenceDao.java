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

import com.kanjiportal.portal.dao.ReferenceDao;
import com.kanjiportal.portal.model.Kanji;
import com.kanjiportal.portal.model.Reference;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 18, 2009
 * Time: 9:25:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class JpaReferenceDao implements ReferenceDao {

    @In
    private EntityManager entityManager;

    @Logger
    private Log log;

    public Reference addReference(String referenceValue) {
        Reference reference = new Reference();
        reference.setValue(referenceValue);
        entityManager.persist(reference);
        return reference;
    }

    public Reference findReferenceByKanjiAndType(Kanji kanji, long referenceTypeId) {
        List<Reference> listKanjiReference = entityManager.createQuery("select r from KanjiReference kr join kr.reference as r where kr.kanji.id = :kanjiId and kr.reference.referenceType.id = :typeId")
                .setParameter("kanjiId", kanji.getId())
                .setParameter("typeId", referenceTypeId)
                .getResultList();
        if (listKanjiReference.size() > 1) {
            log.warn("No unicity while trying to find a reference(#1) for Kanji(#0). Returning first one", kanji.getId(), referenceTypeId);
            return listKanjiReference.get(0);
        } else if (listKanjiReference.size() == 0) {
            return null;
        } else {
            return listKanjiReference.get(0);
        }
    }

    public void updateReferenceValue(Reference reference, String referenceValue) {
        reference.setValue(referenceValue);
        entityManager.merge(reference);
    }

    public void removeReference(Reference reference) {
        entityManager.remove(reference);
    }
}
