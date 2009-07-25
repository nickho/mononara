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
