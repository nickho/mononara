/*****************************************
 *                                       *
 *  JBoss Portal: The OpenSource Portal  *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.miragedev.mononara.core;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 */
public class DefinitionHandler extends DefaultHandler
{

   private String parent;
   private int parentId;
   boolean inKnowledgeName;
   boolean inContextName;
   boolean inContextYomi;
   boolean inContextDescription;
   
   StudyList studyList;
   Dictionnary dictionnary;
   Context context;

   public DefinitionHandler(StudyList studyList, Dictionnary dictionnary)
   {
      this.studyList = studyList;
      this.dictionnary = dictionnary;
      parent = "";
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
   {
      super.startElement(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
      //System.out.println("Parsing: uri("+uri+") localName("+localName+") qName("+qName+")");
      if (qName.equalsIgnoreCase("knowledge"))
      {
         parent = "knowledge";
         parentId = Integer.parseInt(attributes.getValue("id"));
      }
      else if (qName.equalsIgnoreCase("context"))
      {
         parent = "context";
         parentId = Integer.parseInt(attributes.getValue("id"));
      }
      else if (qName.equalsIgnoreCase("name") && parent.equalsIgnoreCase("knowledge"))
      {
         //System.out.println("In name and knowledge");
         inKnowledgeName = true;
         inContextName = false;
         inContextYomi = false;
         inContextDescription = false;
      }
      else if (qName.equalsIgnoreCase("name") && parent.equalsIgnoreCase("context"))
      {
         //System.out.println("In name and knowledge");
        inKnowledgeName = false;
        inContextName = true;
        inContextYomi = false;
        inContextDescription = false;
      }
      else if (qName.equalsIgnoreCase("yomi") && parent.equalsIgnoreCase("context"))
      {
         //System.out.println("In name and knowledge");
        inKnowledgeName = false;
        inContextName = false;
        inContextYomi = true;
        inContextDescription = false;
      }
      else if (qName.equalsIgnoreCase("description") && parent.equalsIgnoreCase("context"))
      {
         //System.out.println("In name and knowledge");
        inKnowledgeName = false;
        inContextName = false;
        inContextYomi = false;
        inContextDescription = true;
      }
      
   }

   public void endElement(String uri, String localName, String qName) throws SAXException
   {
      super.endElement(uri, localName, qName);    //To change body of overridden methods use File | Settings | File Templates.
      //System.out.println("End of parsing: uri("+uri+") localName("+localName+") qName("+qName+")");
   }

   public void characters(char ch[], int start, int length) throws SAXException
   {
      super.characters(ch, start, length);    //To change body of overridden methods use File | Settings | File Templates.
      //System.out.println("Processing Chars ("+String.valueOf(ch, start, length)+")");
      if (inKnowledgeName)
      {
         inKnowledgeName = false;
         studyList.add(parentId, new Knowledge(String.valueOf(ch, start, length).trim()));
         Logger.getLogger(this.getClass()).info("Knowledge ("+parentId+", "+String.valueOf(ch, start, length)+") imported");
      }
      else if (inContextName)
      {
        inContextName = false;
        context = new Context();
        //String[] knowledges = String.valueOf(ch, start, length).trim().;
        Logger.getLogger(this.getClass()).info("Context name ("+parentId+", "+String.valueOf(ch, start, length)+") imported");
        for (int i=start ; i < length ; i++)
        {
          context.addKnowledge(String.valueOf(ch[i]));
        }
      }
      else if (inContextYomi)
      {
        inContextYomi = false;
        String[] yomis = String.valueOf(ch, start, length).trim().split(".");
        Logger.getLogger(this.getClass()).info("        yomi ("+parentId+", "+String.valueOf(ch, start, length)+") imported");
        for (int i=0 ; i < yomis.length ; i++)
        {
          context.addSpelling(yomis[i]);
        }
      }
      else if (inContextDescription)
      {
        inContextDescription = false;
        context.setDescription(String.valueOf(ch, start, length).trim());
        Logger.getLogger(this.getClass()).info("        desc ("+parentId+", "+String.valueOf(ch, start, length)+") imported");
        dictionnary.add(context);
      }
   }
}
