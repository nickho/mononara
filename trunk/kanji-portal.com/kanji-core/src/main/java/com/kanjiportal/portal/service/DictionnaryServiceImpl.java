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
package com.kanjiportal.portal.service;

import com.kanjiportal.portal.dao.DictionnaryDao;
import com.kanjiportal.portal.model.Dictionnary;
import com.kanjiportal.portal.model.service.DictionnaryList;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * DictionnarySoapService
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnarySoapService
 */
@WebService
@WebContext(contextRoot = "/kanji-portal/services/soap", urlPattern = "/dictionnary")
@Name("dictionnaryService")
public class DictionnaryServiceImpl implements DictionnaryService {

    private static final int ITEM_PER_PAGE = 100;

    @In
    private DictionnaryDao dictionnaryDao;

    @WebMethod
    public DictionnaryList getDictionnaryEntriesByPattern(String pattern) {
        return getDictionnaryEntriesByPatternWithPaging(pattern, 0, ITEM_PER_PAGE);
    }

    @WebMethod
    public DictionnaryList getDictionnaryEntriesByPatternWithPaging(String pattern, int page, int pageSize) {
        List<Dictionnary> list = dictionnaryDao.findDictionnaryEntriesByPatternWithPaging(pattern, page, pageSize);
        return new DictionnaryList(list);
    }
}
