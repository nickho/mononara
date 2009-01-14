/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.kanji;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Set;

import com.kanjiportal.portal.Tag;
import com.kanjiportal.portal.Reference;
import com.kanjiportal.portal.ReferenceType;

/**
 * KanjiViewAction
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement KanjiViewAction
 */
@Name("kanjiView")
@Scope(ScopeType.SESSION)
@Stateless
public class KanjiViewAction implements KanjiView
{

   @In
   private EntityManager entityManager;

   @RequestParameter
   private long kanjiId;

  @DataModel
   private Set<Spelling> spellings;

   @DataModel
   private Map<ReferenceType, Reference> references;

   @DataModel
   private Set<Tag> tags;

   @Out
   private Kanji kanji;


   public void view()
   {
      kanji = (Kanji) entityManager.createQuery("select distinct k from Kanji k where k.id = :id")
            .setParameter("id", kanjiId)
            .getSingleResult();
       System.out.println("Kanji set to : "+kanji.getId());
       references = kanji.getReferences();
       System.out.println("Nb References : "+references.size());
       spellings = kanji.getSpellings();
       System.out.println("Nb Spellings : "+spellings.size());
       tags = kanji.getTags();
       System.out.println("Nb Tags : "+tags.size());

   }






}
