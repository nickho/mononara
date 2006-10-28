/*****************************************
 *                                       
 *   Mononara                     
 *                                       
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *                                       
 *****************************************/
package com.miragedev.mononara.core;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @todo Add a description for the class Mononara.java
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class Mononara {
	
	StudyList studyList;
	Dictionnary dictionnary;
	Basket basket;
	Test test;
    
	public Mononara()
	{
		studyList = new StudyList();
    dictionnary = new Dictionnary();
    basket = new Basket();
    test = null;
    
	}
	
	/**
	 * Return the study list
	 * 
	 * @return the study list
	 */
	public StudyList getStudyList() {
		return studyList;
	}

	/**
	 * Return the dictionnary
	 * 
	 * @return the dictionnary
	 */
	public Dictionnary getDictionnary() {
		return dictionnary;
	}
	
	/**
	 * Load the result of a user inside the system.
	 * 
	 * @todo implement this
	 * @param url the url of the file to load
	 */
	public void loadResultList(URL url)
	{
		//load the study list first then fill it
	}
	
	/**
	 * Load the study list into the system.
	 * The process discard the old one.
	 * 
	 * @todo implement this
	 * @param url the url of the file to load
	 */
	public void loadStudyList(URL url)
	{
    Logger.getLogger(this.getClass()).info("Loading def file (" + url.toString() + ")");
    DefaultHandler handler = new DefinitionHandler(studyList, dictionnary);

    try
    {
       SAXParserFactory.newInstance().newSAXParser().parse(new File(url.toURI()), handler);
    }
    catch (ParserConfigurationException e)
    {
       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    catch (SAXException e)
    {
       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    catch (URISyntaxException e)
    {
       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    catch (IOException e)
    {
       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
	}
	
	/**
	 * Add the knowledge to the basket
	 * 
	 * @param knowledge is the knowledge to add to the next test
	 */
	public void addToBasket(Knowledge knowledge)
	{
		basket.add(knowledge);
	}
	
	/**
	 * Remove a knowledge from the basket.
	 * 
	 * @param knowledge 
	 */
	public void removeFromBasket(Knowledge knowledge)
	{
		basket.remove(knowledge);
	}
	
	/**
	 * Add all the knowledge which need to be checked to the basket.
	 * 
	 * @todo implement this
	 */
	public void autoAddToBasket()
	{
		
	}
	
	
	/**
	 * Start the test with the basket elements.
	 * 
	 * @return the Test to start.
	 */
	public Test startTest()
	{
    if (basket.size() == 0)
    {
      Logger.getLogger(this.getClass()).info("Start test with an empty basket");
      return null;
    }
    test = new Test();
    for (int i=0 ; i < basket.size() ; i++)
    {
       Collection<ContextTest> cts = dictionnary.getContexts(basket.getKnowledge(i));
       for (ContextTest contextTest : cts)
       {
         test.add(contextTest);
       }
    }
    Logger.getLogger(this.getClass()).info("Start test: "+test.size()+" questions.");
    return test;
	}
	
	
	/**
	 * End a test and report the results on the study list.
	 * 
	 * @todo The storage of the score results can be better
	 * @return the Results
	 */
	public void endTest()
	{
    TestResults res = test.getResults();
    for (int i=0 ; i < res.size() ; i++)
    {
      res.getKnowledge(i).setTestResult(res.getScore(i) == res.getMaxScore(i));
    }
    Logger.getLogger(this.getClass()).info("End test: "+res.size()+" questions.");
	}
}
