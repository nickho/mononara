/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.service;

import com.kanjiportal.portal.dao.DictionnaryDao;
import com.kanjiportal.portal.model.Dictionnary;
import com.kanjiportal.portal.model.service.DictionnaryList;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.ejb.Stateless;
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
@Stateless
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
