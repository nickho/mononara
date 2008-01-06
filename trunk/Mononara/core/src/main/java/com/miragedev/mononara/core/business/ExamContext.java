/*****************************************
 *
 *   MononaraService
 *
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *
 *****************************************/
package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.DictionnaryEntry;
import com.miragedev.mononara.core.model.Knowledge;


/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 * @todo Add a description for the class ExamContext.java
 */
public class ExamContext {

    DictionnaryEntry dictionnaryEntry;
    Knowledge knowledge;
    int knowledgePos;

    public ExamContext(Knowledge knowledge, DictionnaryEntry dictionnaryEntry, int knowledgePos) {
        this.knowledge = knowledge;
        this.dictionnaryEntry = dictionnaryEntry;
        this.knowledgePos = knowledgePos;
    }

    public DictionnaryEntry getDictionnaryEntry() {
        return dictionnaryEntry;
    }

    public int getKnowledgePos() {
        return knowledgePos;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }
}
