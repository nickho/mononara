/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal;

import javax.ejb.Local;

/**
 * com.kanjiportal.portal.Authenticator
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement com.kanjiportal.portal.Authenticator
 */

@Local
public interface Authenticator {
    boolean authenticate();
}
