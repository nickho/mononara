package com.kanjiportal.portal.service;

import com.kanjiportal.portal.model.Knowledge;
import com.kanjiportal.portal.model.service.KnowledgeParam;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 9:42:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface KnowledgeService {

    public boolean login(String user, String password);

    public int update(List<KnowledgeParam> list);

    public void logout();

    public List<Knowledge> getKnowledgesByTag(String tagName);

    public List<Knowledge> getKnowledgesByTagWithPaging(String tagName, int page, int pageSize);
}
