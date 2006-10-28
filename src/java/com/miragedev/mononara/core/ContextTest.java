/*****************************************
 *                                       
 *   Mononara                     
 *                                       
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *                                       
 *****************************************/
package com.miragedev.mononara.core;


/**
 * @todo Add a description for the class ContextTest.java
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class ContextTest {

	Context context;
  Knowledge knowledges;
	int contextInd;
	
	public ContextTest(Knowledge knowledge, Context context, int contextInd)
	{
    this.knowledges = knowledge;
		this.context = context;
		this.contextInd = contextInd;
	}
	
	public Context getContext()
	{
		return context;
	}
	
	public int getContextInd()
	{
		return contextInd;
	}

  public Knowledge getKnowledges()
  {
    return knowledges;
  }
}
