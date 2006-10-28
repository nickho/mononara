/*****************************************
 *                                       
 *   Mononara                     
 *                                       
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *                                       
 *****************************************/
package com.miragedev.mononara.gui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.prefs.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.miragedev.mononara.core.Mononara;


/**
 * @todo Add a description for the class MononaraWindow.java
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class MononaraWindow 
{
	Display display;
	Shell shell;
	TabFolder tabFolder;
	Vector<StudyListView> studyListViews;
	Preferences preferences;

	public MononaraWindow()
	{
		/* Init the Attributes */
		studyListViews = new Vector<StudyListView>();
		
		/* Define the main prop */
		display = new Display();
		shell = new Shell(display);
		shell.setText("Mononara: The learning engine");
		int max_width = display.getPrimaryMonitor().getClientArea().width;
		int max_height = display.getPrimaryMonitor().getClientArea().height;
		shell.setSize(max_width, max_height);
		shell.setLocation(0,0);
		
		/* Define the layout */
    RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
    layout.fill = true;
		shell.setLayout(layout);
		
		/* Define the menu */
		Menu menu = new Menu(shell, SWT.BAR);
		MenuItem menuItemFile = new MenuItem(menu, SWT.CASCADE, 0);
		menuItemFile.setText("File");
		Menu menuFile = new Menu(shell, SWT.DROP_DOWN);
		menuItemFile.setMenu(menuFile);
		MenuItem menuItemLoadDefinition = new MenuItem(menuFile, SWT.PUSH, 0);
		menuItemLoadDefinition.setText("Load definition file");
    menuItemLoadDefinition.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent event)
      {
        FileDialog dialog = new FileDialog(shell, SWT.OPEN);
        String file = dialog.open();
        try
        {
          loadDefinition(new File(file).toURL());
        }
        catch (MalformedURLException e)
        {
          MessageBox message = new MessageBox(shell, SWT.ICON_ERROR & SWT.OK);
          message.setText("Definition file error");
          message.setMessage("The file \""+file+"\" is not a valid URL");
          message.open();
        }
      }
    });
		new MenuItem(menuFile, SWT.SEPARATOR, 1);
		MenuItem menuItemExit = new MenuItem(menuFile, SWT.PUSH, 2);
		menuItemExit.setText("Exit");
		menuItemExit.setAccelerator(SWT.ALT | SWT.F4);
    menuItemExit.addSelectionListener(new SelectionAdapter() 
    {
      @Override
      public void widgetSelected(SelectionEvent arg0)
      {
        shell.dispose();
      } 
    }
    );
        	
		shell.setMenuBar(menu);
		
    /* define the toolbar */
    CoolBar coolBar = new CoolBar(shell, SWT.NONE);
    CoolItem coolItem1 = new CoolItem(coolBar, SWT.DROP_DOWN);
    CoolItem coolItem2 = new CoolItem(coolBar, SWT.DROP_DOWN);
    
    Combo viewCombo = new Combo(coolBar, SWT.READ_ONLY);
    viewCombo.add("button");
    viewCombo.add("list");
    viewCombo.select(0);
    Composite testComposite = new Composite(coolBar, SWT.NONE);
    testComposite.setLayout(new RowLayout());
    Button testButton = new Button(testComposite, SWT.PUSH);
    testButton.setText("Start test");
    testButton.addSelectionListener(new SelectionAdapter() 
        {
          @Override
          public void widgetSelected(SelectionEvent event)
          {
            studyListViews.get(tabFolder.getSelectionIndex()).launchTest();
          }    
        }    
    );
    Button buttonAutoBasket = new Button(testComposite, SWT.PUSH);
    buttonAutoBasket.setText("Auto start");
    
    coolItem1.setControl(viewCombo);
    coolItem2.setControl(testComposite);
    
    coolItem1.setSize(10, 25);
    coolItem1.setSize(200, 25);
    coolBar.pack();
    
		/* define the content */
		tabFolder = new TabFolder(shell, SWT.TOP);
		
		
		/* @todo remove this when the testing is done */
	    try{loadDefinition(new URL("file:///C:/dev/mononara/src/definitions/jlpt4.md.xml"));}
	    catch (MalformedURLException e){e.printStackTrace();}
		
		/* Launch the main loop */
		shell.open();
		//shell.pack();
		while (!shell.isDisposed()) {
		  if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();  
	}

	/**
	 * 
	 * @param file
	 */
	private void loadDefinition(URL file)
	{
	      Mononara mononara = new Mononara();
	      mononara.loadStudyList(file);
	      StudyListView studyListView = new StudyListView(tabFolder, mononara);
	      studyListViews.add(studyListView);
	      TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
	      tabItem.setText(file.getFile());
	      tabItem.setControl(studyListView.getControls());
	}

	public static void main(String[] args)
	{
	    new MononaraWindow();
	}

}
