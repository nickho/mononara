/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.model.service;

import com.kanjiportal.portal.model.Dictionnary;

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
public class DictionnaryList {

    private List<Dictionnary> dictionnary;

    public DictionnaryList() {
    }

    public DictionnaryList(final List<Dictionnary> dictionnary) {
        this.dictionnary = dictionnary;
    }

    @XmlElement(name = "entry")
    public List<Dictionnary> getDictionnary() {
        return dictionnary;
    }

    public void setDictionnary(List<Dictionnary> dictionnary) {
        this.dictionnary = dictionnary;
    }
}
