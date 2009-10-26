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
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProduceMime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


//@Stateless
@WebService
@Path("/kanji")
@WebContext(contextRoot = "/kanji-portal/services/soap", urlPattern = "/kanji")
@Name("kanjiService")
public class KanjiServiceImpl implements KanjiService {

    @Logger
    private Log log;

    @In
    private KanjiDao kanjiDao;
    private static final int ITEM_PER_PAGE = 100;

    @WebMethod
    @GET
    @Path("/pattern/{pattern}/{page}/{item}")
    @ProduceMime("application/xml")
    public KanjiList getKanjisByPatternWithPaging(@PathParam("pattern") @WebParam String pattern,
                                                  @PathParam("page") @WebParam int page,
                                                  @PathParam("item") @WebParam int itemPerPage) {
        String search = pattern == null ? "%" : '%' + pattern.toLowerCase().replace('*', '%') + '%';
        List<Kanji> kanjis = kanjiDao.findByPatternForcingFullFetch(search, page, itemPerPage);
        return new KanjiList(kanjis, -1);
    }

    @WebMethod
    @GET
    @Path("/pattern/{pattern}")
    @ProduceMime("application/xml")
    public KanjiList getKanjisByPattern(@PathParam("pattern") @WebParam String pattern) {
        KanjiList list = getKanjisByPatternWithPaging(pattern, 0, ITEM_PER_PAGE);
        return list;
    }

    @WebMethod
    @GET
    @Path("/since/{since}/{page}/{item}")
    @ProduceMime("application/xml")
    public KanjiList getKanjisBySinceWithPaging(@PathParam("since") @WebParam String since,
                                                @PathParam("page") @WebParam int page,
                                                @PathParam("item") @WebParam int itemPerPage) throws InvalidArgumentsException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");


        Date date = null;
        try {
            date = format.parse(since);
        } catch (ParseException e) {
            throw new BadDateFormatingException(e);
        }

        List<Kanji> kanjis = kanjiDao.findBySinceDateForcingFullFetch(date, page, itemPerPage);

        return new KanjiList(kanjis, -1);
    }

    @WebMethod
    @GET
    @Path("/since/{since}")
    @ProduceMime("application/xml")
    public KanjiList getKanjisBySince(@PathParam("since") @WebParam String since) throws InvalidArgumentsException {
        KanjiList list = getKanjisBySinceWithPaging(since, 0, ITEM_PER_PAGE);
        return list;
    }

    @WebMethod
    @GET
    @Path("/tag/{tag}/{page}/{item}")
    @ProduceMime("application/xml")
    public KanjiList getKanjisByTagWithPaging(@PathParam("tag") @WebParam String tag,
                                              @PathParam("page") @WebParam int page,
                                              @PathParam("item") @WebParam int itemPerPage) throws InvalidArgumentsException {

        List<Kanji> kanjis = kanjiDao.findByTagForcingFullFetch(tag, page, itemPerPage);

        return new KanjiList(kanjis, -1);
    }

    @WebMethod
    @GET
    @Path("/tag/{tag}")
    @ProduceMime("application/xml")
    public KanjiList getKanjisByTag(@PathParam("tag") @WebParam String tag) throws InvalidArgumentsException {
        KanjiList list = getKanjisByTagWithPaging(tag, 0, ITEM_PER_PAGE);
        return list;
    }

    @WebMethod
    @GET
    @Path("/reference/{reference}/{value}/{page}/{item}")
    @ProduceMime("application/xml")
    public KanjiList getKanjisByRefWithPaging(@PathParam("reference") @WebParam String reference,
                                              @PathParam("value") @WebParam String value,
                                              @PathParam("page") @WebParam int page,
                                              @PathParam("item") @WebParam int itemPerPage) throws InvalidArgumentsException {
        long totalCount = kanjiDao.countByRef(reference, value);
        List<Kanji> kanjis = kanjiDao.findByRefForcingFullFetch(reference, value, page, itemPerPage);
        return new KanjiList(kanjis, totalCount);
    }

    @WebMethod
    @GET
    @Path("/reference/{reference}/{value}")
    @ProduceMime("application/xml")
    public KanjiList getKanjisByRef(@PathParam("reference") @WebParam String reference, @PathParam("value") @WebParam String value) throws InvalidArgumentsException {
        KanjiList list = getKanjisByRefWithPaging(reference, value, 0, ITEM_PER_PAGE);
        return list;
    }


}