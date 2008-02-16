package com.miragedev.mononara.gui;

import com.miragedev.mononara.core.business.Basket;
import com.miragedev.mononara.core.business.Exam;
import com.miragedev.mononara.core.business.ExamContext;
import com.miragedev.mononara.core.business.LearningMethod;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.service.DictionnaryService;
import com.miragedev.mononara.core.service.KanjiService;
import com.miragedev.mononara.core.service.MononaraService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;


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
    private JPanel studyListPane;
    private JComboBox tagsComboBox;
    private JTextField userSpellingTestTextField;
    private JButton testButton;
    private JEditorPane commentTestTextPane;
    private JLabel contextTestLabel;
    private JButton refreshButton;
    private JList basketList;
    private JLabel pageLabel;
    private JTable tableDictionnary;
    private JComboBox comboBoxTagsDictionnary;
    private JPanel testPane;
    private JFrame frame;

    private Basket basket;
    private Exam test;
    private LearningMethod learningMethod;

    private KanjiService kanjiService;
    private DictionnaryService dictionnaryService;
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

    public void setDictionnaryService(DictionnaryService dictionnaryService) {
        this.dictionnaryService = dictionnaryService;
    }

    public void startMononara() {
        log.info("Mononara starting");
        // Create the window
        frame = new JFrame();
        frame.getContentPane().add(panelMain);

        //create the menu
        frame.setJMenuBar(mononaraMenuFactory.createMenuBar());

        //The DictionnaryList
        DictionnaryTableModel tableModel = new DictionnaryTableModel(dictionnaryService);
        tableDictionnary.setModel(tableModel);
        TableRowSorter<DictionnaryTableModel> sorter = new TableRowSorter<DictionnaryTableModel>(tableModel);
        RowFilter<DictionnaryTableModel, Object> rf;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter("NULL", 0);
            rf = RowFilter.notFilter(rf);
        } catch (PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
        tableDictionnary.setRowSorter(sorter);
        comboBoxTagsDictionnary.setModel(new TagsListModel(kanjiService));
        comboBoxTagsDictionnary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                System.out.println(combo.getSelectedItem());
                TableRowSorter<DictionnaryTableModel> sorter = (TableRowSorter<DictionnaryTableModel>) tableDictionnary.getRowSorter();

                Vector<RowFilter<DictionnaryTableModel, Object>> filters = new Vector<RowFilter<DictionnaryTableModel, Object>>();
                RowFilter<DictionnaryTableModel, Object> rfNotNull = RowFilter.regexFilter("NULL");
                rfNotNull = RowFilter.notFilter(rfNotNull);
                filters.add(rfNotNull);
                RowFilter<DictionnaryTableModel, Object> rfCombo = RowFilter.regexFilter("^.*" + combo.getSelectedItem() + ".*$");
                filters.add(rfCombo);
                RowFilter<DictionnaryTableModel, Object> rfTotal = RowFilter.andFilter(filters);
                sorter.setRowFilter(rfTotal);
            }
        });

        //some wires
        //ImageIcon imageScroll = new ImageIcon("images/scroll.jpg");
        //imageLeft.add(new JLabel(imageScroll));
        //imageRight.add(new JLabel(imageScroll));
        basket = new Basket(40);
        basketList.setModel(new BasketListModel(basket));
        /*
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
        */

        //set the default button for the tests
        frame.getRootPane().setDefaultButton(testButton);


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
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testButton_ActionPerformed();
            }
        });

        //Showing time
        refreshTagList();
        //Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        frame.setSize((int) bounds.getWidth(), (int) bounds.getHeight());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //frame.setLocation((int) ((bounds.getWidth() - frame.getWidth()) / 2), (int) ((bounds.getHeight() - frame.getHeight()) / 2));
        frame.setTitle("Mononara");
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/ai-icon.jpg"));
        frame.setIconImage(icon.getImage());
        refreshStudyList();
        log.info("Mononara started");
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
            //toggleButton.setFont(new Font(toggleButton.getFont().getName(), toggleButton.getFont().getStyle(), 26));
            Dimension sizePanel = studyListPane.getSize();
            //(x*nbh)= sizePanel.width;
            //(x*nbv)= sizePanel.height;
            //nbv*nbh = listKnowledge.size();
            //int nbv = (int) (listKnowledge.size() / nbh) + 1;
            //x2*listKnowledge.size()  + sizePanel.width * x - sizePanel.height * sizePanel.width = 0;
            long delta = (long) sizePanel.width * (long) sizePanel.width + (long) 4 * (long) listKnowledge.size() * (long) sizePanel.height * (long) sizePanel.width;
            //log.debug("delta : " + delta + ", racine delta : " + Math.sqrt(delta));
            int buttonSize = (int) (-sizePanel.width + Math.sqrt(delta)) / (2 * listKnowledge.size());
            toggleButton.setPreferredSize(new Dimension(buttonSize - 6, buttonSize - 6));
            toggleButton.setFont(new Font(toggleButton.getFont().getName(), toggleButton.getFont().getStyle(), (buttonSize - 6) / 2));
            //toggleButton.setMargin(new Insets(1, 1, 1, 1));
            //log.debug("size : " + buttonSize);
            int fadingLvl = (int) learningMethod.computeFadingLvl(knowledge);
            int red = 0;
            int green = 0;
            int blue = 0;
            Color kanjiColor = null;
            if (knowledge.getLastTestSuccess() > 0.9999) {
                log.info("fading lvl correct " + fadingLvl);
                if (fadingLvl < 1) {
                    kanjiColor = new Color(67, 173, 67);
                } else {
                    kanjiColor = new Color(67, 173, 67, 255 / fadingLvl);
                }
            } else if (knowledge.getLastTimeSuccess() == null) {
                //ohhh first time huh!?
            } else {
                red = 200 - (int) (100 * knowledge.getLastTestSuccess());
                blue = 50 + (int) (150 * knowledge.getLastTestSuccess());
                green = 100;
                kanjiColor = new Color(red, green, blue);
            }

            if (learningMethod.isTested(knowledge)) {
                toggleButton.setBackground(kanjiColor);
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
        //List<Tag> listTag = kanjiService.findAllTags();
        //log.info("Found " + listTag.size() + " tags in the database.");
        //tagsComboBox.removeAllItems();
        //for (Tag tag : listTag) {
        //    tagsComboBox.addItem(tag.getCode());
        //}
        tagsComboBox.setSelectedIndex(0);
    }

    private void testButton_ActionPerformed() {
        if (testButton.getText().equals("Next")) {

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
            if (examContext.isHintNeeded()) {
                commentTestTextPane.setText("<html></br></br></br><p align=center><b>" + examContext.getDictionnaryEntry().getDescription() + "</b></p></html>");
            }
            pageLabel.setText("<html><p align=right><font size=+1>" + (test.getPosition() + 1) + "/" + test.size() + "</font></p></html>");
            testButton.setEnabled(true);
            testButton.setText("Go");
            testButton.setVisible(true);
        } else {

            String testResultText;
            if (test.test(userSpellingTestTextField.getText())) {
                testResultText = "OK!!!";
            } else {
                testResultText = "Faux!!!";
            }

            int knowledgePos = test.getContextTest().getKnowledgePos();
            String spellings = test.getContextTest().getDictionnaryEntry().getSpellingInKana();
            String spelling = spellings.split("\\.")[knowledgePos];
            String description = test.getContextTest().getDictionnaryEntry().getDescription();
            userSpellingTestTextField.setText(spelling);
            StringBuffer comment = new StringBuffer();
            comment.append("<html><p align=center>");
            comment.append(testResultText);
            comment.append("</p><br/><br/><br/>");
            comment.append("<p align=center><b><font size=26>");
            comment.append(spellings.replace(".", ""));
            comment.append("</font></b></p><br/>");
            comment.append("<p align=center>");
            comment.append(description);
            comment.append("</p><br/>");
            comment.append("</html>");
            commentTestTextPane.setText(comment.toString());

            if (test.next()) {
                //testButton.setText("Next");
            } else {
                mononaraService.saveExamResults(test.getResults());
                commentTestTextPane.setText(commentTestTextPane.getText());
                testButton.setVisible(false);
                testButton.setEnabled(false);
                basket.empty();
                refreshStudyList();
                //panelMain.setSelectedIndex(0);
            }
            testButton.setText("Next");
        }
    }

    private void startButton_ActionPerformed() {
        test = mononaraService.startExam(basket);
        if (test.size() != 0) {
            panelMain.setSelectedIndex(2);
            testButton_ActionPerformed();
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.FRENCH);
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(args[0]);
        //AbstractApplicationContext ctx = new FileSystemXmlApplicationContext();
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
        panelMain.addTab(ResourceBundle.getBundle("InterfaceResources").getString("mononaraframe.studylist.tab.title"), panel2);
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
        studyListPane.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getBundle("InterfaceResources").getString("mononaraframe.studylist.panel.title")));
        tagsComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("jlpt_4");
        defaultComboBoxModel1.addElement("jlpt_3");
        defaultComboBoxModel1.addElement("jlpt_2");
        defaultComboBoxModel1.addElement("jlpt_1");
        tagsComboBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(tagsComboBox, gbc);
        startButton = new JButton();
        this.$$$loadButtonText$$$(startButton, ResourceBundle.getBundle("InterfaceResources").getString("mononaraframe.studylist.button.start"));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel2.add(startButton, gbc);
        refreshButton = new JButton();
        this.$$$loadButtonText$$$(refreshButton, ResourceBundle.getBundle("InterfaceResources").getString("mononaraframe.studylist.button.refresh"));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(refreshButton, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panelMain.addTab(ResourceBundle.getBundle("InterfaceResources").getString("mononaraframe.basket.tab.title"), panel3);
        basketList = new JList();
        basketList.setFont(new Font(basketList.getFont().getName(), basketList.getFont().getStyle(), 26));
        basketList.setLayoutOrientation(2);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        basketList.setModel(defaultListModel1);
        basketList.setValueIsAdjusting(true);
        basketList.setVisibleRowCount(1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(basketList, gbc);
        testPane = new JPanel();
        testPane.setLayout(new GridBagLayout());
        panelMain.addTab(ResourceBundle.getBundle("InterfaceResources").getString("mononaraframe.test.tab.title"), testPane);
        userSpellingTestTextField = new JTextField();
        userSpellingTestTextField.setColumns(10);
        userSpellingTestTextField.setFont(new Font(userSpellingTestTextField.getFont().getName(), userSpellingTestTextField.getFont().getStyle(), 18));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        testPane.add(userSpellingTestTextField, gbc);
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
        testPane.add(commentTestTextPane, gbc);
        contextTestLabel = new JLabel();
        contextTestLabel.setFont(new Font(contextTestLabel.getFont().getName(), contextTestLabel.getFont().getStyle(), contextTestLabel.getFont().getSize()));
        contextTestLabel.setText("No test started");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        testPane.add(contextTestLabel, gbc);
        testButton = new JButton();
        testButton.setDefaultCapable(true);
        testButton.setText("Next");
        testButton.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        testPane.add(testButton, gbc);
        pageLabel = new JLabel();
        pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), pageLabel.getFont().getSize()));
        pageLabel.setText("?/?");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        testPane.add(pageLabel, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panelMain.addTab(ResourceBundle.getBundle("InterfaceResources").getString("mononaraframe.dictionnary.tab.title"), panel4);
        comboBoxTagsDictionnary = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(comboBoxTagsDictionnary, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(scrollPane1, gbc);
        tableDictionnary = new JTable();
        tableDictionnary.setAutoCreateRowSorter(false);
        tableDictionnary.setFillsViewportHeight(true);
        scrollPane1.setViewportView(tableDictionnary);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }
}