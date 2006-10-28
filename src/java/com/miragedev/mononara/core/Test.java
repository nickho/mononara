/*****************************************
 *                                       *
 *  JBoss Portal: The OpenSource Portal  *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.miragedev.mononara.core;

import java.util.Vector;

/**
 * @author <a href="mailto:julien@jboss.org">Julien Viet</a>
 * @version $Revision: 1.1 $
 */
public class Test
{
  Vector<ContextTest> data;
  Vector<Boolean> results;
  int pos;

  /**
   * Init the Test
   */
  public Test()
  {
    pos = 0;
  }

  /**
   * Return the number of question inside the test
   * 
   * @return the number of question
   */
  public int size()
  {
    return data.size();
  }
  
  public boolean getResult(int index)
  {
    return results.get(index);
  }
  
  public ContextTest getContextTest(int index)
  {
    return data.get(index);
  }

  public void add(ContextTest contextTest)
  {
    data.add(contextTest);
  }
  
  /**
   * Compute the results of the test
   * 
   * @return the test results
   */
  public TestResults getResults()
  {
    TestResults res = new TestResults();
    for (int i=0 ; i < data.size() ; i++)
    {
      res.add(data.get(i).getKnowledges(), results.get(i));
    }
    return res;
  }
  
  public boolean test(String input)
  {
    ContextTest cont = data.get(pos);
    results.set(pos, cont.getContext().isTheSame(cont.getContextInd(), input));
    return results.get(pos);
  }
  
  public boolean next()
  {
    pos++;
    return (pos >= size());
  }

  public int getPosition()
  {
    return pos;
  }
   
}
