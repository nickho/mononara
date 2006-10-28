/*****************************************
 *                                       
 *   Mononara                     
 *                                       
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *                                       
 *****************************************/
package com.miragedev.mononara.core;

import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * @todo Add a description for the class Dictionnary.java
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class Dictionnary {
	Vector<Context> contexts;
	
	public Dictionnary()
	{
		contexts = new Vector<Context>();
	}
	
	public void add(Context context)
	{
		contexts.add(context);
	}
	
	/**
	 * Return the Context and offset inside the context 
	 * where this knowledge element occur.
	 * 
	 * @todo Modify to get just a subset of the matching context
	 * @param knowledge the knowledge to look for
	 * @return All the Context which contain the knowledge
	 */
	public Collection<ContextTest> getContexts(Knowledge knowledge)
	{
		Vector<ContextTest> res = new Vector<ContextTest>(); 
    
    for (int i = 0 ; i < contexts.size() ; i++)
    {
      int index = 1;//fake one
      while (index > 0)
      {
        index = contexts.get(i).indexOf(knowledge.getName(), index+1);
        if (index > 0)
        {
          res.add(new ContextTest(knowledge, contexts.get(i), index));
        }
      }
    }
    Logger.getLogger(this.getClass()).debug(knowledge.getName()+" => "+res.size()+" compounds");
    return res;
	}

}
