/*****************************************
 *
 *   MononaraService
 *
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *
 *****************************************/
package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.business.ExamContext;
import com.miragedev.mononara.core.dao.DictionnaryEntryDao;
import com.miragedev.mononara.core.model.DictionnaryEntry;
import com.miragedev.mononara.core.model.Knowledge;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 * @todo Add a description for the class DictionnaryService.java
 */
public class DictionnaryServiceImpl implements DictionnaryService {

    private Log log = LogFactory.getLog(DictionnaryServiceImpl.class);

    DictionnaryEntryDao dictionnaryEntryDao;

    public void setDictionnaryEntryDao(DictionnaryEntryDao dictionnaryEntryDao) {
        this.dictionnaryEntryDao = dictionnaryEntryDao;
    }

    public DictionnaryServiceImpl() {
    }

    /**
     * Return the Context and offset inside the context
     * where this knowledge element occur.
     *
     * @param knowledge the knowledge to look for
     * @return All the Context which contain the knowledge
     * @todo Modify to get just a subset of the matching context
     */
    public Collection<ExamContext> getContexts(Knowledge knowledge) {
        Vector<ExamContext> res = new Vector<ExamContext>();

        List<DictionnaryEntry> list = dictionnaryEntryDao.findBySpelling(knowledge.getKanji().getCharacter());


        for (DictionnaryEntry entry : list) {
            int index = -1;//fake one
            do {
                index = entry.indexOf(knowledge.getKanji().getCharacter(), index + 1);
                if (index >= 0) {
                    res.add(new ExamContext(knowledge, entry, index));
                }
            } while (index >= 0);
        }
        log.debug(knowledge.getKanji().getCharacter() + " => " + res.size() + " compounds");
        return res;
    }

    public int getDictionnarySize() {
        return dictionnaryEntryDao.size();
    }

    public DictionnaryEntry getEntryByNumber(long number) {
        return dictionnaryEntryDao.findById(number);
    }
}
