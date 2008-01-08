package com.miragedev.mononara.gui;

import com.miragedev.mononara.core.business.*;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;
import com.miragedev.mononara.core.service.KanjiService;
import com.miragedev.mononara.core.service.MononaraService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 29, 2007
 * Time: 1:47:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class MononaraFrame {
    private JTabbedPane panelMain;
    private JButton startButton;
    private JButton propertiesButton;
    private JPanel studyListPane;
    private JComboBox tagsComboBox;
    private JTextField userSpellingTestTextField;
    private JButton nextTestButton;
    private JEditorPane commentTestTextPane;
    private JLabel contextTestLabel;
    private JButton refreshButton;
    private JList basketList;
    private JButton goTestButton;
    private JLabel pageLabel;
    private JFrame frame;

    private Basket basket;
    private Exam test;
    private LearningMethod learningMethod;

    private KanjiService kanjiService;
    private MononaraService mononaraService;

    private MononaraMenuFactory mononaraMenuFactory;

    private Log log = LogFactory.getLog(MononaraFrame.class);

    public MononaraFrame() {
    }

    public void setLearningMethod(LearningMethod learningMethod) {
        this.learningMethod = learningMethod;
    }

    public void setKanjiService(KanjiService kanjiService) {
        this.kanjiService = kanjiService;
    }

    public void setMononaraService(MononaraService mononaraService) {
        this.mononaraService = mononaraService;
    }

    public void setMononaraMenuFactory(MononaraMenuFactory mononaraMenuFactory) {
        this.mononaraMenuFactory = mononaraMenuFactory;
    }

    public void startMononara() {
        // Create the window
        frame = new JFrame();
        frame.getContentPane().add(panelMain);

        //create the menu
        frame.setJMenuBar(mononaraMenuFactory.createMenuBar());

        //some wires
        //ImageIcon imageScroll = new ImageIcon("images/scroll.jpg");
        //imageLeft.add(new JLabel(imageScroll));
        //imageRight.add(new JLabel(imageScroll));
        basket = new Basket(40);
        basket.addContentChangeListener(new ContentChangeListener() {
            public void contentChange(ContentChangeEvent e) {
                if (e.getType() == ContentChangeEvent.Type.ADD) {
                    DefaultListModel listModel = (DefaultListModel) basketList.getModel();
                    listModel.addElement(e.getKnowledge().getKanji().getCharacter());
                } else if (e.getType() == ContentChangeEvent.Type.REMOVE) {
                    DefaultListModel listModel = (DefaultListModel) basketList.getModel();
                    listModel.removeElement(e.getKnowledge().getKanji().getCharacter());
                }
            }
        });


        tagsComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tagsComboBox_ActionPerformed();
            }
        });
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButton_ActionPerformed();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshButton_ActionPerformed();
            }
        });
        nextTestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextTestButton_ActionPerformed();
            }
        });
        goTestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goTestButton_ActionPerformed();
            }
        });

        //Showing time
        refreshTagList();
        frame.setSize(640, 480);
        frame.setLocation(540, 280);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    private void tagsComboBox_ActionPerformed() {
        refreshStudyList();
    }

    private void refreshStudyList() {
        List<Knowledge> listKnowledge = kanjiService.findKnowledgesByTag((String) tagsComboBox.getSelectedItem());
        log.info("Loading study list with " + listKnowledge.size() + " kanjis");
        studyListPane.removeAll();
        for (final Knowledge knowledge : listKnowledge) {
            JToggleButton toggleButton = new JToggleButton(knowledge.getKanji().getCharacter());
            toggleButton.setFont(new Font(toggleButton.getFont().getName(), toggleButton.getFont().getStyle(), 26));
            int fadingLvl = (int) learningMethod.computeFadingLvl(knowledge);
            int red = 0;
            int green = 0;
            int blue = 0;
            if (knowledge.getLastTestSuccess() > 0.9999) {
                green = 100;
            } else if (knowledge.getLastTimeSuccess() == null) {
                //ohhh first time huh!?
            } else {
                red = 200 - (int) (100 * knowledge.getLastTestSuccess());
                blue = 50 + (int) (150 * knowledge.getLastTestSuccess());
                green = 100;
            }
            if (fadingLvl > 1) {
                log.info("knowledge " + knowledge.getKanji() + " : (" + red + "," + green + "," + blue + ")");
                green = Math.min(255, green * fadingLvl);
                red = Math.min(255, (red + 50) * fadingLvl);
                blue = Math.min(255, (blue + 50) * fadingLvl);
            }

            //int transparency = 255 * Math.round(backgroundColor.getAlpha() / 255);
            //Color foregroundColor = new Color(transparency, transparency, transparency);
            if (learningMethod.isTested(knowledge)) {
                toggleButton.setBackground(new Color(red, green, blue));
            }
            //toggleButton.setForeground(foregroundColor);


            toggleButton.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent event) {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        basket.add(knowledge);
                    } else if (event.getStateChange() == ItemEvent.DESELECTED) {
                        basket.remove(knowledge);
                    }
                }
            });
            studyListPane.add(toggleButton);
        }
        studyListPane.revalidate();
        studyListPane.repaint();
    }

    private void refreshButton_ActionPerformed() {
        refreshTagList();
    }

    private void refreshTagList() {
        List<Tag> listTag = kanjiService.findAllTags();
        log.info("Found " + listTag.size() + " tags in the database.");
        tagsComboBox.removeAllItems();
        for (Tag tag : listTag) {
            tagsComboBox.addItem(tag.getCode());
        }
    }

    private void goTestButton_ActionPerformed() {
        if (test.test(userSpellingTestTextField.getText())) {
            commentTestTextPane.setText("<html><p align=center>OK!!!</p></html>");
        } else {
            int knowledgePos = test.getContextTest().getKnowledgePos();
            String spellings = test.getContextTest().getDictionnaryEntry().getSpellingInKana();
            commentTestTextPane.setText("<html><p align=center>Faux!!!</p><br/><br><br><p align=center><b><font size=26>" + spellings.split("\\.")[knowledgePos] + "</font></b></p></html>");
        }
        if (test.next()) {
            goTestButton.setVisible(false);
            nextTestButton.setVisible(true);
        } else {
            mononaraService.saveExamResults(test.getResults());
            commentTestTextPane.setText(commentTestTextPane.getText());
            goTestButton.setVisible(false);
            nextTestButton.setVisible(false);
            basket.empty();
            refreshStudyList();
            panelMain.setSelectedIndex(0);
        }


    }

    private void nextTestButton_ActionPerformed() {
        commentTestTextPane.setText("");
        userSpellingTestTextField.setText("");
        ExamContext examContext = test.getContextTest();
        int pos = examContext.getKnowledgePos();
        StringBuffer contextText = new StringBuffer();
        contextText.append("<html><p align=center>");
        contextText.append("<font size=26>");
        contextText.append(examContext.getDictionnaryEntry().getSpellingInKanji().substring(0, pos));
        contextText.append("</font>");
        contextText.append("<font size=26 color=red>");
        contextText.append(examContext.getKnowledge().getKanji().getCharacter());
        contextText.append("</font>");
        contextText.append("<font size=26>");
        contextText.append(examContext.getDictionnaryEntry().getSpellingInKanji().substring(pos + 1));
        contextText.append("</font></p>");
        contextText.append("</html>");
        contextTestLabel.setText(contextText.toString());
        pageLabel.setText("<html><p align=right><font size=+1>" + (test.getPosition() + 1) + "/" + test.size() + "</font></p></html>");

        goTestButton.setVisible(true);
        nextTestButton.setVisible(false);
    }

    private void startButton_ActionPerformed() {
        test = mononaraService.startExam(basket);
        if (test.size() != 0) {
            panelMain.setSelectedIndex(2);
            nextTestButton_ActionPerformed();
        }
    }

    public static void main(String[] args) {
        AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("META-INF/spring-config.xml");
        ctx.registerShutdownHook();
        MononaraFrame mform = (MononaraFrame) ctx.getBean("mononaraFrame");
        mform.startMononara();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panelMain = new JTabbedPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panelMain, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panelMain.addTab("Study list", panel2);
        studyListPane = new JPanel();
        studyListPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(studyListPane, gbc);
        studyListPane.setBorder(BorderFactory.createTitledBorder("Choix des kaniji a tester"));
        tagsComboBox = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(tagsComboBox, gbc);
        startButton = new JButton();
        startButton.setText("Start");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel2.add(startButton, gbc);
        propertiesButton = new JButton();
        propertiesButton.setText("Properties");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        panel2.add(propertiesButton, gbc);
        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(refreshButton, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panelMain.addTab("Basket", panel3);
        basketList = new JList();
        basketList.setFont(new Font(basketList.getFont().getName(), basketList.getFont().getStyle(), 26));
        basketList.setLayoutOrientation(2);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        basketList.setModel(defaultListModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(basketList, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panelMain.addTab("Test", panel4);
        userSpellingTestTextField = new JTextField();
        userSpellingTestTextField.setColumns(10);
        userSpellingTestTextField.setFont(new Font(userSpellingTestTextField.getFont().getName(), userSpellingTestTextField.getFont().getStyle(), 18));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        panel4.add(userSpellingTestTextField, gbc);
        commentTestTextPane = new JEditorPane();
        commentTestTextPane.setContentType("text/html");
        commentTestTextPane.setEditable(false);
        commentTestTextPane.setFont(new Font(commentTestTextPane.getFont().getName(), commentTestTextPane.getFont().getStyle(), commentTestTextPane.getFont().getSize()));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(commentTestTextPane, gbc);
        contextTestLabel = new JLabel();
        contextTestLabel.setFont(new Font(contextTestLabel.getFont().getName(), contextTestLabel.getFont().getStyle(), contextTestLabel.getFont().getSize()));
        contextTestLabel.setText("No test started");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        panel4.add(contextTestLabel, gbc);
        nextTestButton = new JButton();
        nextTestButton.setText("Next");
        nextTestButton.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel4.add(nextTestButton, gbc);
        goTestButton = new JButton();
        goTestButton.setText("Go");
        goTestButton.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        panel4.add(goTestButton, gbc);
        pageLabel = new JLabel();
        pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), pageLabel.getFont().getSize()));
        pageLabel.setText("?/?");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel4.add(pageLabel, gbc);
    }
}