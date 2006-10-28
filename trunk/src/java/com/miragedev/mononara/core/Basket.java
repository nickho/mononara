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
 * A Basket contain the knowledge the user select for the test.
 * 
 * @todo Add listener when a knowledge is removed/add from the basket
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class Basket
{

   private Vector<Knowledge> content;

   public Basket()
   {
     content = new Vector<Knowledge>();
   }

   /**
    * Add a knowledge in the basket.
    * If this one is full <code>false</code> is returned
    * 
    * @todo Implement the BasketSize strategy
    * @param knowledge the knowledge to add
    * @return false if the Basket is full
    */
   public boolean add(Knowledge knowledge)
   {
      content.add(knowledge);
      return true;
   }

   public boolean remove(Knowledge knowledge)
   {
	   return content.remove(knowledge);
   }
   
   public int size() 
   {
	   return content.size();
   }
   
   public Knowledge getKnowledge(int index)
   {
	   return content.get(index);
   }
   
   public void empty()
   {
      content.clear();
   }
}
