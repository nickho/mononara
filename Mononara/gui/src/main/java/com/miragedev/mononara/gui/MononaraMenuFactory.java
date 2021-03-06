package com.miragedev.mononara.gui;

import com.miragedev.mononara.core.service.MononaraService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 2, 2008
 * Time: 7:41:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MononaraMenuFactory {

    private Log log = LogFactory.getLog(MononaraMenuFactory.class);
    private ResourceBundle res = ResourceBundle.getBundle("InterfaceResources");

    MononaraService mononaraService;
    MononaraFrame mononaraFrame;

    public void setMononaraService(MononaraService mononaraService) {
        this.mononaraService = mononaraService;
    }

    public void setMononaraFrame(MononaraFrame mononaraFrame) {
        this.mononaraFrame = mononaraFrame;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu menuFile = new JMenu(res.getString("mononaraframe.menu.file.title"));
        menubar.add(menuFile);
        JMenuItem menuItemSync = new JMenuItem(res.getString("mononaraframe.menu.file.sync"));
        menuFile.add(menuItemSync);
        JMenuItem menuItemExit = new JMenuItem(res.getString("mononaraframe.menu.file.exit"));
        menuFile.add(new JSeparator());
        menuFile.add(menuItemExit);
        JMenu menuAide = new JMenu("?");
        menubar.add(menuAide);
        JMenuItem menuItemAbout = new JMenuItem(res.getString("mononaraframe.menu.help.about"));
        menuAide.add(menuItemAbout);


        menuItemSync.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final SyncFrame syncFrame = new SyncFrame(mononaraService);
                syncFrame.pack();
                syncFrame.setVisible(true);
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
