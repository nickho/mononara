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
 * KanjiHandler
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement KanjiHandler
 */
public abstract class KanjiHandler extends DefaultHandler {

    public abstract void addKanjiAddedListener(KanjiAddedListener listener);

}
