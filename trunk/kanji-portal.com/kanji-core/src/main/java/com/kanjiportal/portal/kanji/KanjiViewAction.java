/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.kanji;

import com.kanjiportal.portal.Reference;
import com.kanjiportal.portal.ReferenceType;
import com.kanjiportal.portal.Tag;
import com.kanjiportal.portal.dictionnary.DictionnaryEntry;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public class KanjiViewAction implements KanjiView {

    private final static long ON_SPELLING_TYPE_ID = 1;
    private final static long KUN_SPELLING_TYPE_ID = 2;

    @In
    private EntityManager entityManager;

    @RequestParameter
    private long kanjiId;

    @DataModel
    private Set<Spelling> otherSpellings;

    @DataModel
    private Set<String> japaneeseSpellings;

    @DataModel
    private Map<ReferenceType, Reference> references;

    @DataModel
    private Set<Tag> tags;

    @DataModel
    private Set<DictionnaryEntry> compounds;

    @Out
    private Kanji kanji;


    public void view() {
        kanji = (Kanji) entityManager.createQuery("select k from Kanji k where k.id = :id")
                .setParameter("id", kanjiId)
                .getSingleResult();
        System.out.println("Kanji set to : " + kanji.getId());

        //References
        references = kanji.getReferences();
        System.out.println("Nb References : " + references.size());

        //Spellings
        //others than japaneese
        otherSpellings = new HashSet<Spelling>();
        HashSet<String> tempSpellings = new HashSet<String>();
        Spelling tempSpelling = null;
        Set<Meaning> tempMeaning = null;
        for (Spelling spelling : kanji.getSpellings()) {
            if (spelling.getType().getId() != ON_SPELLING_TYPE_ID || spelling.getType().getId() != KUN_SPELLING_TYPE_ID) {
                otherSpellings.add(spelling);
            } else {
                if (tempSpelling != null) {

                }
                tempSpelling = spelling;
            }
        }
        //japaneeses (On and Kun)
        japaneeseSpellings = new HashSet<String>();
        //Set<Spelling> spellings = (Set<Spelling>) entityManager.createQuery("select s from Kanji k where k.spellings. ").getResultList();

        //Tags
        tags = kanji.getTags();
        System.out.println("Nb Tags : " + tags.size());


    }


}
