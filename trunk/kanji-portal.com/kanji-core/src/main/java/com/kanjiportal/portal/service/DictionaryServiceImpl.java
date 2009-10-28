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

import com.kanjiportal.portal.dao.DictionaryDao;
import com.kanjiportal.portal.dao.SearchTooGenericException;
import com.kanjiportal.portal.model.Dictionary;
import com.kanjiportal.portal.model.service.DictionaryList;
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

/**
 * DictionnarySoapService
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnarySoapService
 */
@WebService
@WebContext(contextRoot = "/kanji-portal/services/soap", urlPattern = "/dictionary")
@Path("/dictionary")
@Name("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

    private static final int ITEM_PER_PAGE = 100;

    @In
    private DictionaryDao dictionaryDao;

    @Logger
    private Log log;

    @WebMethod
    @GET
    @Path("/pattern/{pattern}")
    @ProduceMime({"application/xml", "application/json"})
    public DictionaryList searchDictionnaryByPattern(@PathParam("pattern") @WebParam String pattern) throws SearchTooGenericException {
        return searchDictionnaryByPatternWithPaging(pattern, 0, ITEM_PER_PAGE);
    }

    @WebMethod
    @GET
    @Path("/pattern/{pattern}/{page}/{item}")
    @ProduceMime({"application/xml", "application/json"})
    public DictionaryList searchDictionnaryByPatternWithPaging(
            @PathParam("pattern") @WebParam String pattern,
            @PathParam("page") @WebParam int page,
            @PathParam("item") @WebParam int itemPerPage) throws SearchTooGenericException {
        String search = pattern == null ? "%" : '%' + pattern.toLowerCase().replace('*', '%') + '%';
        List<Dictionary> list = dictionaryDao.searchDictionaryByPattern(search, page, itemPerPage);
        return new DictionaryList(list, -1);
    }

    @WebMethod
    @GET
    @Path("/since/{since}/{page}/{item}")
    @ProduceMime({"application/xml", "application/json"})
    public DictionaryList searchDictionarySinceWithPaging(@PathParam("since") @WebParam String since,
                                                          @PathParam("page") @WebParam int page,
                                                          @PathParam("item") @WebParam int itemPerPage) throws InvalidArgumentsException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        Date date = null;
        try {
            date = format.parse(since);
        } catch (ParseException e) {
            throw new BadDateFormatingException(e);
        }
        log.debug("loading dictionary since #0 : #1/#2", since, page, itemPerPage);
        List<Dictionary> dictionary = dictionaryDao.searchDictionnaryBySinceDate(date, page, itemPerPage);
        log.debug("loaded dictionary since #0 : #1 entry", since, dictionary.size());

        return new DictionaryList(dictionary, -1);
    }

    @WebMethod
    @GET
    @Path("/since/{since}")
    @ProduceMime({"application/xml", "application/json"})
    public DictionaryList searchDictionarySince(@PathParam("since") @WebParam String since) throws InvalidArgumentsException {
        DictionaryList list = searchDictionarySinceWithPaging(since, 0, ITEM_PER_PAGE);
        return list;
    }

    @WebMethod
    @GET
    @Path("/tag/{tag}/{page}/{item}")
    @ProduceMime("application/xml")
    public DictionaryList searchDictionaryByTagWithPaging(@PathParam("tag") @WebParam String tag,
                                                          @PathParam("page") @WebParam int page,
                                                          @PathParam("item") @WebParam int itemPerPage) throws InvalidArgumentsException {

        List<Dictionary> dictionary = dictionaryDao.searchDictionnaryByTag(tag, page, itemPerPage);

        return new DictionaryList(dictionary, -1);
    }

    @WebMethod
    @GET
    @Path("/tag/{tag}")
    @ProduceMime("application/xml")
    public DictionaryList searchDictionaryByTag(@PathParam("tag") @WebParam String tag) throws InvalidArgumentsException {
        DictionaryList list = searchDictionaryByTagWithPaging(tag, 0, ITEM_PER_PAGE);
        return list;
    }


}
