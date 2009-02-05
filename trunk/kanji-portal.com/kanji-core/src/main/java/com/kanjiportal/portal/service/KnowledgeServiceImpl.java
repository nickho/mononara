package com.kanjiportal.portal.service;

import com.kanjiportal.portal.Authenticator;
import com.kanjiportal.portal.course.KnowledgeImporting;
import com.kanjiportal.portal.dao.KnowledgeDao;
import com.kanjiportal.portal.dao.TagDao;
import com.kanjiportal.portal.dao.UserDao;
import com.kanjiportal.portal.model.Knowledge;
import com.kanjiportal.portal.model.Tag;
import com.kanjiportal.portal.model.User;
import com.kanjiportal.portal.model.service.KnowledgeParam;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 10:37:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@WebService
@WebContext(contextRoot = "/kanji-portal/services/soap", urlPattern = "/knowledge")
@Name("knowledgeService")
public class KnowledgeServiceImpl implements KnowledgeService {

    private static final int ITEM_PER_PAGE = 100;

    @In(create = true)
    private Authenticator authenticator;

    @In(create = true)
    private KnowledgeImporting knowledgeImporting;

    @In
    private UserDao userDao;

    @In
    private KnowledgeDao knowledgeDao;

    @In
    private TagDao tagDao;

    @Logger
    private Log logger;

    @WebMethod
    public boolean login(String username, String password) {
        logger.info("Logging by WebService user : #{username}");
        Identity.instance().getCredentials().setUsername(username);
        Identity.instance().getCredentials().setPassword(password);
        return authenticator.authenticate();
    }

    @WebMethod
    public int update(List<KnowledgeParam> list) {
        logger.info("update #0 Knowledges", list.size());
        int updated = 0;
        knowledgeImporting.startImport();
        User user = userDao.findByUsername(Identity.instance().getCredentials().getUsername());
        updated = knowledgeImporting.importKnowledges(list, user);
        knowledgeImporting.destroy();
        return updated;
    }

    @WebMethod
    public List<Knowledge> getKnowledgesByTag(String tagName) {
        return getKnowledgesByTagWithPaging(tagName, 0, ITEM_PER_PAGE);
    }

    @WebMethod
    public List<Knowledge> getKnowledgesByTagWithPaging(String tagName, int page, int pageSize) {
        User user = userDao.findByUsername(Identity.instance().getCredentials().getUsername());
        Tag tag = tagDao.findByName(tagName);
        List<Knowledge> knowledges = knowledgeDao.findByTagForUser(tag, user, page, pageSize);
        return knowledges;
    }


    @WebMethod
    public void logout() {
        knowledgeImporting.destroy();
    }
}