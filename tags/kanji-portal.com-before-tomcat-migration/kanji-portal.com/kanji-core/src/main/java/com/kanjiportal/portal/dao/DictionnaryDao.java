package com.kanjiportal.portal.dao;

import com.kanjiportal.portal.model.Dictionnary;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 5, 2009
 * Time: 10:08:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface DictionnaryDao {
    List<Dictionnary> findDictionnaryEntriesByPatternWithPaging(String pattern, int page, int pageSize);
}
