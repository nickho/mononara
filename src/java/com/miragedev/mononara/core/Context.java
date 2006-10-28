/*****************************************
 *                                       
 *   Mononara                     
 *                                       
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *                                       
 *****************************************/
package com.miragedev.mononara.core;

import java.util.Vector;

/**
 * A Context is a context around a knowledge to help you guess what is the knowledge.
 * 
 * @todo Add accessors and fill constructor.
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class Context
{
	private Vector<String> context;
	private Vector<String> contextSpelling;
	private Vector<String> hints;
	private String description; 

  public Context()
  {
    context = new Vector<String>();
    contextSpelling = new Vector<String>();
    hints = new Vector<String>();
  }
  
  public int getHintMax()
  {
    return hints.size();
  }
  
  public String getHint(int index)
  {
    return hints.get(index);
  }
  
  public void addKnowledge(String knowledge)
  {
    context.add(knowledge);
  }
  
  public void addSpelling(String spelling)
  {
    contextSpelling.add(spelling);
  }
  
  public void addHint(String hint)
  {
    hints.add(hint);
  }
  
  public int indexOf(String knowledge, int start)
  {
    int index = -1;
    for (int i=start ; i < context.size() && index <= 0 ; i++)
    {
      if (knowledge.equalsIgnoreCase(context.get(i)))
      {
        index = i;
      }
    }
    
    return index;
  }
  
  public int size()
  {
    return context.size();
  }
  
  public boolean isTheSame(int index, String spelling)
  {
    return contextSpelling.get(index).equalsIgnoreCase(spelling);
  }

  public String getKnowledge(int index)
  {
    return context.get(index);
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }
}