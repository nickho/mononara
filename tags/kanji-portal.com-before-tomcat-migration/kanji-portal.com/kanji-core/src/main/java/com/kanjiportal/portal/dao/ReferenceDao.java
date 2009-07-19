package com.kanjiportal.portal.dao;

import com.kanjiportal.portal.model.Kanji;
import com.kanjiportal.portal.model.Reference;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 18, 2009
 * Time: 9:25:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReferenceDao {

    public Reference addReference(String referenceValue);

    public Reference findReferenceByKanjiAndType(Kanji kanji, long referenceTypeId);

    public void updateReferenceValue(Reference reference, String referenceValue);

    public void removeReference(Reference reference);
}
