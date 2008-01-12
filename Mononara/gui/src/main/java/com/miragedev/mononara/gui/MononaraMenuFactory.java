package com.miragedev.mononara.gui;

import com.miragedev.mononara.core.service.MononaraService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 2, 2008
 * Time: 7:41:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MononaraMenuFactory {

    private Log log = LogFactory.getLog(MononaraMenuFactory.class);

    String kanjiUri;
    String dictionnaryUri;
    MononaraService mononaraService;

    public void setKanjiUri(String kanjiUri) {
        this.kanjiUri = kanjiUri;
    }

    public void setDictionnaryUri(String dictionnaryUri) {
        this.dictionnaryUri = dictionnaryUri;
    }

    public void setMononaraService(MononaraService mononaraService) {
        this.mononaraService = mononaraService;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        menubar.add(menuFile);
        JMenuItem menuItemSync = new JMenuItem("Online sync");
        menuFile.add(menuItemSync);
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuFile.add(new JSeparator());
        menuFile.add(menuItemExit);
        JMenu menuAide = new JMenu("?");
        menubar.add(menuAide);
        JMenuItem menuItemAbout = new JMenuItem("About");
        menuAide.add(menuItemAbout);


        menuItemSync.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                URI kanjiURI = null;
                try {
                    kanjiURI = new URI(kanjiUri);
                    URI dicURI = new URI(dictionnaryUri);
                    mononaraService.onlineSync(kanjiURI, dicURI);

                } catch (URISyntaxException e1) {
                    log.error("Uri non valide", e1);
                    e1.printStackTrace();
                }

            }
        });

        menuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AboutFrame aboutFrame = new AboutFrame();
                aboutFrame.pack();
                aboutFrame.setVisible(true);
            }
        });

        return menubar;
    }
}
