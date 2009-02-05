package com.kanjiportal.portal.service; /**
 * ${NAME}
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement ${NAME}
 */

import com.kanjiportal.portal.dao.KanjiDao;
import com.kanjiportal.portal.model.Kanji;
import com.kanjiportal.portal.model.service.KanjiList;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;


@Stateless
@WebService
@WebContext(contextRoot = "/kanji-portal/services/soap", urlPattern = "/kanji")
//@Path("/kanji")
@Name("kanjiService")
public class KanjiServiceImpl implements KanjiService {

    @In
    private KanjiDao kanjiDao;
    private static final int ITEM_PER_PAGE = 100;

    //@Path(value = "/{search}")
    //@GET
    @WebMethod
    public KanjiList getKanjisByPatternWithPaging(@WebParam String pattern, @WebParam int page, @WebParam int itemPerPage) {
        List<Kanji> kanjis = kanjiDao.findByPattern(pattern, page, itemPerPage);
        return new KanjiList(kanjis);
    }

    @WebMethod
    public KanjiList getKanjisByPattern(@WebParam String pattern) {
        return getKanjisByPatternWithPaging(pattern, 0, ITEM_PER_PAGE);
    }


}