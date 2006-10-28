/*****************************************
 *                                       
 *   Mononara                     
 *                                       
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *                                       
 *****************************************/
package com.miragedev.mononara.core;

import java.util.HashMap;

/**
 * @todo Add a description for the class StudyList.java
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class StudyList
{
   private HashMap<Integer, Knowledge> studyList;

   public StudyList()
   {
	   studyList = new HashMap<Integer, Knowledge>();
   }

   public void add(int index, Knowledge knowledge)
   {
      studyList.put(index, knowledge);
   }

   public Knowledge get(int i)
   {
      return studyList.get(i);
   }
   
   /**
    * Return the number of element in the study list.
    * 
    * @return the study list size
    */
   public int size()
   {
      return studyList.size();
   }
   
   /**
    * @todo Delete this or not ?
    * @deprecated
    * @param index
    * @return
    */
   public float getKnowledgeFadingLevel(int index)
   {
     return studyList.get(index).getFadingLevel();
   }

   
}
