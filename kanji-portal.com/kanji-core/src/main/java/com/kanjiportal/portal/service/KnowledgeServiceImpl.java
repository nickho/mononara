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

import com.kanjiportal.portal.course.KnowledgeImporting;
import com.kanjiportal.portal.dao.KnowledgeDao;
import com.kanjiportal.portal.dao.TagDao;
import com.kanjiportal.portal.model.Knowledge;
import com.kanjiportal.portal.model.Tag;
import com.kanjiportal.portal.model.service.KnowledgeParam;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.management.IdentityManager;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 10:37:36 PM
 * To change this template use File | Settings | File Templates.
 */
@WebService
@WebContext(contextRoot = "/kanji-portal/services/soap", urlPattern = "/knowledge")
@Path("/knowledge")
@ProduceMime({"application/xml;charset=utf-8", "application/json;charset=utf-8"})
@Name("knowledgeService")
public class KnowledgeServiceImpl implements KnowledgeService {

    private static final int ITEM_PER_PAGE = 100;

    @In(create = true)
    private KnowledgeImporting knowledgeImporting;

    @In
    private KnowledgeDao knowledgeDao;

    @In
    private IdentityManager identityManager;

    @In
    private TagDao tagDao;

    @Logger
    private Log logger;

    @WebMethod
    @PUT
    @ConsumeMime({"application/xml;charset=utf-8", "application/json;charset=utf-8"})
    @Path("/login")
    public boolean login(String username, String password) {
        logger.info("Logging by WebService user : #{username}");
        Identity.instance().getCredentials().setUsername(username);
        Identity.instance().getCredentials().setPassword(password);
        return identityManager.authenticate(username, password);
    }

    @WebMethod
    @PUT
    @ConsumeMime({"application/xml;charset=utf-8", "application/json;charset=utf-8"})
    @Path("/update")
    @Restrict("#{identity.loggedIn}")
    public int update(List<KnowledgeParam> list) {
        String username = Identity.instance().getCredentials().getUsername();
        logger.info("update #0 Knowledges for #1", list.size(), username);
        int updated = 0;
        knowledgeImporting.startImport();
        updated = knowledgeImporting.importKnowledges(list, username);
        return updated;
    }

    @WebMethod
    @GET
    @Path("/tag/{tag}")
    @Restrict("#{identity.loggedIn}")
    public List<Knowledge> getKnowledgesByTag(@PathParam("tag") String tagName) {
        return getKnowledgesByTagWithPaging(tagName, 0, ITEM_PER_PAGE);
    }

    @WebMethod
    @GET
    @Path("/tag/{tag}/{page}/{item}")
    @Restrict("#{identity.loggedIn}")
    public List<Knowledge> getKnowledgesByTagWithPaging(@PathParam("tag") String tagName,
                                                        @PathParam("page") int page,
                                                        @PathParam("item") int pageSize) {
        String user = Identity.instance().getCredentials().getUsername();
        Tag tag = tagDao.findByCode(tagName);
        List<Knowledge> knowledges = knowledgeDao.findByTagForUser(tag, user, page, pageSize);
        return knowledges;
    }


}
