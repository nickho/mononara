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
