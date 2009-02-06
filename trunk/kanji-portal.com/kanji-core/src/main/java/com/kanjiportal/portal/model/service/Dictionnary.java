/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.model.service;

import com.kanjiportal.portal.dictionnary.DictionnaryEntry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Dictionnary
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement Dictionnary
 */

@XmlRootElement(name = "dictionnary")
@XmlAccessorType(XmlAccessType.NONE)
public class Dictionnary {

    @SuppressWarnings("unused")
    @XmlElement(name = "entry")
    private List<DictionnaryEntry> dictionnary;

    public Dictionnary() {
    }

    public Dictionnary(final List<DictionnaryEntry> dictionnary) {
        this.dictionnary = dictionnary;
    }

}
