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
package com.kanjiportal.portal.dictionary;

import com.kanjiportal.portal.dao.DictionaryDao;
import com.kanjiportal.portal.model.Dictionary;
import com.kanjiportal.portal.model.DictionaryTag;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;

import java.util.List;

/**
 * DictionnaryServiceAction
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnaryServiceAction
 */
@Name("dictionaryView")
@Scope(ScopeType.SESSION)
@Synchronized(timeout = 10000)
public class DictionaryViewingAction implements DictionaryViewing {

    @RequestParameter
    private long entryId;

    @In
    private DictionaryDao dictionaryDao;

    @DataModel
    private List<DictionaryTag> dictionnaryTags;

    @Out
    private Dictionary entry;

    public void view() {
        entry = dictionaryDao.findById(entryId);
        dictionnaryTags = entry.getTags();
    }

}
