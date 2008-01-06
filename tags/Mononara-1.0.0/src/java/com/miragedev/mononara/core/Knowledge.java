/*****************************************
 *                                       
 *   Mononara                     
 *                                       
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *                                       
 *****************************************/
package com.miragedev.mononara.core;

import java.util.Calendar;

/**
 * A Knowledge is an entity you wish to learn.
 * 
 * @todo Seriously review this class. Add listener when the sucess time change.
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class Knowledge
{
   private Calendar firstTimeSuccess;
   private Calendar lastTest;
   private String name;

   public Knowledge(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   /**
    * Return the fading level.
    * 
    * @todo Review the fading lvl method algorythm
    * @return the fading lvl
    */
   public float getFadingLevel()
   {
	   if (lastTest == null && firstTimeSuccess == null)
	   {
		   return 0f;
	   }
	   else if (firstTimeSuccess != null && lastTest == null)
	   {
		   return -1f;
	   }
	   else
	   {
		   return 1f + (System.currentTimeMillis()-lastTest.getTimeInMillis())/(lastTest.getTimeInMillis()-firstTimeSuccess.getTimeInMillis()+21600000);
	   }
   }
   
   /**
    * Set the result of a test.
    * 
    * @todo Review the fading lvl method algorythm
    * @param result is <code>true</code> if the test was ok and <code>false</code> otherwise
    */
   public void setTestResult(boolean result)
   {
	   if (!result)
	   {
		   lastTest = null;
	   }
	   else
	   {
		   if (lastTest == null)
		   {
			   lastTest = Calendar.getInstance();
		   }
		   lastTest.setTimeInMillis(System.currentTimeMillis());
		   if (firstTimeSuccess == null)
		   {
			   firstTimeSuccess = Calendar.getInstance();
			   firstTimeSuccess.setTimeInMillis(System.currentTimeMillis());
		   }
	   }
   }
}
