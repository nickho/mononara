/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.kanji;


import java.util.Map;

/**
 * KanjiEditing
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 */

public interface KanjiEditing {

    public void edit();

    public void saveMainInfos();

    public void addTag();

    public void removeTag(long tagId);

    public void addReference();

    public void removeReference(long refId);

    public void addSpelling();

    public void removeSpelling(long spellingId);


    public void exit();


    public void setTagId(long tagId);

    public long getTagId();

    public Map<String, Long> getTagsAvailables();

    public long getReferenceTypeId();

    public void setReferenceTypeId(long referenceTypeId);

    public String getReferenceValue();

    public void setReferenceValue(String referenceValue);

    public Map<String, Long> getReferencesTypesAvailables();

    public long getSpellingTypeId();

    public String getSpellingKana();

    public void setSpellingTypeId(long spellingTypeId);

    public void setSpellingKana(String spellingKana);

    public String getSpellingRomaji();

    public void setSpellingRomaji(String spellingRomaji);

    public String getSpellingMeanings();

    public void setSpellingMeanings(String spellingMeanings);

    public Map<String, Long> getSpellingTypesAvailables();
}
