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

import javax.ejb.Local;

/**
 * DictionnaryService
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnaryService
 */
@Local
public interface DictionnaryViewing {

    public Dictionnary getDictionnaryEntry();

}
