/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.miragedev.mononara.core.io;

import org.xml.sax.helpers.DefaultHandler;

/**
 * DictionnaryHandler
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnaryHandler
 */
public abstract class DictionnaryHandler extends DefaultHandler {

    public abstract void addDictionnaryEntryAddedListener(DictionaryEntryAddedListener listener);

}
