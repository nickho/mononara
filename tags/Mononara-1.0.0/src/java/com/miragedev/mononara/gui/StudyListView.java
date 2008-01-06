/*****************************************
 *                                       
 *   Mononara                     
 *                                       
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *                                       
 *****************************************/
package com.miragedev.mononara.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;

import com.miragedev.mononara.core.Knowledge;
import com.miragedev.mononara.core.Mononara;
import com.miragedev.mononara.core.Test;


/**
 * 
 * @todo Add description
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 */
public class StudyListView
{
  private Mononara mononara;

  private Composite mainComposite;
  private TestWindow testWindow;

  public StudyListView(TabFolder tabFolder, Mononara mono)
  {
    mononara = mono;
    /*
     * Compute the size of the table to have a 1.25 ratio width/height
     */
    int studyListSize = mononara.getStudyList().size();
    int height = (int) (2 * Math.round(Math.sqrt(studyListSize / 5)));
    //TODO put this in property
    //int width = (5 * height / 4);
    int width = (5 * height / 3);
    if (width * height < studyListSize)
    {
      height = height + 1;
    }
    /* Try to compute the button size */
    int buttonSize = tabFolder.getDisplay().getPrimaryMonitor().getClientArea().width / width;
    System.out.println("size: "+buttonSize);
    //Color buttonColor = new Color(tabFolder.getDisplay(), 0, 255, 255);
    Font buttonFont = new Font(tabFolder.getDisplay(), "Arial", buttonSize/2-10, SWT.NONE);
    
    
    testWindow = new TestWindow(tabFolder.getShell());
    
    /* The study list view */
    mainComposite = new Composite(tabFolder, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = width;
    mainComposite.setLayout(layout);
    
    Button button;
    for (int i = 0; i < studyListSize; i++)
    {
        button = new Button(mainComposite, SWT.TOGGLE);
        button.setSize(buttonSize, buttonSize);
        button.setFont(buttonFont);
        button.setText(mononara.getStudyList().get(i+1).getName());
        button.setData(mononara.getStudyList().get(i+1));
        button.addSelectionListener(new SelectionAdapter() 
            {

              @Override
              public void widgetSelected(SelectionEvent event)
              {
                if (((Button)event.widget).getSelection())
                {
                  mononara.addToBasket((Knowledge)((Button)event.widget).getData());
                  System.out.println("Basket +:"+((Knowledge)((Button)event.widget).getData()).getName());
                }
                else
                {
                  mononara.removeFromBasket((Knowledge)((Button)event.widget).getData());
                  System.out.println("Basket -:"+((Knowledge)((Button)event.widget).getData()).getName());
                }
              }
              
            }
        );
    }
  }

  public Control getControls()
  {
    return mainComposite;
  }

  /**
   * Launch the test on this study list view
   */
  public void launchTest()
  {
    Test test = mononara.startTest();
    if (test != null)
    {
      testWindow.setTest(test);
      testWindow.open();
      mononara.endTest();
    }
    else
    {
      MessageBox messageBox = new MessageBox(mainComposite.getShell(), SWT.ICON_WARNING & SWT.OK);
      messageBox.setText("Information");
      messageBox.setMessage("Votre Panier est vide.\nAjouter des elements dedans avant de lancer le test");
      messageBox.open();
    }
  }
}
