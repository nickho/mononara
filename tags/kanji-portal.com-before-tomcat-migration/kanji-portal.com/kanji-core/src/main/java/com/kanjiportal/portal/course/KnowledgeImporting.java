package com.kanjiportal.portal.course;

import com.kanjiportal.portal.model.User;
import com.kanjiportal.portal.model.service.KnowledgeParam;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 1, 2009
 * Time: 7:20:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface KnowledgeImporting {
    public int importKnowledges(List<KnowledgeParam> list, User user);

    public void destroy();

    public void startImport();
}