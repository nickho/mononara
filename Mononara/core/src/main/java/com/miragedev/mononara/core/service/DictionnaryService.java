package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.business.ExamContext;
import com.miragedev.mononara.core.model.DictionnaryEntry;
import com.miragedev.mononara.core.model.Knowledge;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 5, 2008
 * Time: 7:26:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DictionnaryService {

    Collection<ExamContext> getContexts(Knowledge knowledge);

    int getDictionnarySize();

    DictionnaryEntry getEntryByNumber(long number);
}
