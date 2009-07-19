/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.dictionnary;

import com.kanjiportal.portal.model.Dictionnary;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.web.RequestParameter;

import javax.persistence.EntityManager;

/**
 * DictionnaryServiceAction
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnaryServiceAction
 */
@Name("dictionnaryViewing")
@Scope(ScopeType.STATELESS)
@AutoCreate
public class DictionnaryViewingAction implements DictionnaryViewing {

    @In
    private EntityManager entityManager;

    @RequestParameter
    private long entryId;

    @Unwrap
    public Dictionnary getDictionnaryEntry() {
        return (Dictionnary) entityManager.createQuery("select distinct e from Dictionnary e where e.id = :id")
                .setParameter("id", entryId)
                .getSingleResult();
    }

}
