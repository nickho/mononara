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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.miragedev.mononara.core.Context;
import com.miragedev.mononara.core.Test;
import com.miragedev.mononara.core.TestResults;

/**
 * @todo Add a description for the class TestWindow
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class TestWindow extends Dialog 
{
  Test test;
  TestResults results;
  
  Shell shell;
  List hintsList;
  Label contextLabel;
  Text inputText;
  Button goNextButton;
  Label resultLabel;
  Label descriptionLabel;
  Label positionLabel;
  Color backColor;
  Font contextFont;
  Font descriptionFont;
  Font posFont;
  Font inputFont;
  Font resultFont;
  
  public TestWindow(Shell parent, int style) 
  {
    super (parent, style);
    backColor = new Color(parent.getDisplay(), 192, 0, 0);
    contextFont = new Font(parent.getDisplay(), "Arial", 30, SWT.BOLD);
    descriptionFont = new Font(parent.getDisplay(), "Arial", 20, SWT.NONE);
    inputFont = new Font(parent.getDisplay(), "Arial", 20, SWT.NONE);
    posFont = new Font(parent.getDisplay(), "Arial", 15, SWT.BOLD);
    resultFont = new Font(parent.getDisplay(), "Arial", 30, SWT.BOLD);
  }
  
  public TestWindow (Shell parent) 
  {
    this (parent, 0); // your default style bits go here (not the Shell's style bits)
  }
  
  public void setTest(Test test)
  {
    this.test = test;
  }
  
  public Test open() 
  {
    Shell parent = getParent();
    shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    shell.setText(getText());
    shell.setBackground(backColor); // Red
    shell.setSize(700, 500);
    FormLayout layout = new FormLayout();
    shell.setLayout(layout);
    
    hintsList = new List(shell, SWT.NONE);
    FormData data1 = new FormData();
    data1.left = new FormAttachment(0, 10);
    data1.top = new FormAttachment(0, 10);
    data1.bottom = new FormAttachment(100, -10);
    hintsList.setLayoutData(data1);
    
    contextLabel = new Label(shell, SWT.NONE);
    contextLabel.setText("Context (Word, Sentence, ....)");
    contextLabel.setFont(contextFont);
    contextLabel.setBackground(backColor);
    FormData dataContext = new FormData();
    dataContext.left = new FormAttachment(hintsList, 10);
    dataContext.top = new FormAttachment(0, 10);
    //dataContext.right = new FormAttachment(positionLabel, 10);
    contextLabel.setLayoutData(dataContext);

    inputText = new Text(shell, SWT.NONE);
    inputText.setFont(inputFont);
    FormData dataInput = new FormData();
    dataInput.left = new FormAttachment(50, 0);
    dataInput.top = new FormAttachment(contextLabel, 10);
    //dataContext.right = new FormAttachment(positionLabel, 10);
    inputText.setLayoutData(dataInput);
    
    goNextButton = new Button(shell, SWT.PUSH);
    goNextButton.setText("Go/Next");
    goNextButton.addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent arg0)
          {
            if (goNextButton.getText().equals("&Go"))
            {
              testInput();
            }
            else if (goNextButton.getText().equals("&Next"))
            {
              readData();
            }
            else if (goNextButton.getText().equals("&Finish"))
            {
              shell.dispose();
            }
          }
          
        });
    FormData dataGoNext = new FormData();
    dataGoNext.left = new FormAttachment(inputText, 10);
    dataGoNext.top = new FormAttachment(contextLabel, 10);
    goNextButton.setLayoutData(dataGoNext);
    
    resultLabel = new Label(shell, SWT.NONE);
    resultLabel.setBackground(backColor);
    resultLabel.setFont(resultFont);
    FormData dataResult = new FormData();
    dataResult.left = new FormAttachment(hintsList, 10);
    dataResult.top = new FormAttachment(inputText, 10);
    resultLabel.setLayoutData(dataResult);
    
    descriptionLabel = new Label(shell, SWT.NONE);
    descriptionLabel.setText("Description come here");
    descriptionLabel.setBackground(backColor);
    descriptionLabel.setFont(descriptionFont);
    FormData dataDescription = new FormData();
    dataDescription.left = new FormAttachment(hintsList, 10);
    dataDescription.top = new FormAttachment(resultLabel, 10);
    //dataDescription.right = new FormAttachment(positionLabel, 10);
    //dataDescription.bottom = new FormAttachment(positionLabel, 100, 0);
    descriptionLabel.setLayoutData(dataDescription);
    
    positionLabel = new Label(shell, SWT.NONE);
    positionLabel.setText("x/y");
    positionLabel.setFont(posFont);
    positionLabel.setBackground(backColor);
    FormData dataPosition = new FormData();
    dataPosition.right = new FormAttachment(100, -20);
    dataPosition.bottom = new FormAttachment(100, -20);
    positionLabel.setLayoutData(dataPosition);
    
    
    /* Main loop */
    readData();
    shell.open();
    Display display = parent.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) display.sleep();
    }
    
    /* Dispose */
    backColor.dispose();
    contextFont.dispose();
    descriptionFont.dispose();
    posFont.dispose();
    inputFont.dispose();
    resultFont.dispose();
    
    return test;
  }
  
  private void readData()
  {
    /* setup the hints */
    hintsList.removeAll();
    Context cont = test.getContextTest(test.getPosition()).getContext();
    for (int i=0 ; i < cont.getHintMax(); i++)
    {
      hintsList.add(cont.getHint(i));
    }
    
    /* context */
    StringBuffer buffs = new StringBuffer();
    for (int i=0 ; i < cont.size(); i++)
    {
      buffs.append(cont.getKnowledge(i));
    }
    contextLabel.setText(buffs.toString());
    
    /* description, result, button and input */
    descriptionLabel.setText("");
    inputText.setText("");
    goNextButton.setText("&Go");
    resultLabel.setText("");
  }
  
  private void testInput()
  {
    if (test.test(inputText.getText()))
    {
      resultLabel.setText("Correcte");
    }
    else
    {
      resultLabel.setText("Faux");
    }
    descriptionLabel.setText(test.getContextTest(test.getPosition()).getContext().getDescription());
    if (test.next())
    {
      goNextButton.setText("&Next");
    }
    else
    {
      goNextButton.setText("&Finish");
    }
  }
  
}
