/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryofnotes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

/**
 *
 * @author YuriPilshikov
 */
public class LibraryOfNotesSwingGui extends JPanel {
    
    private int newNodeSuffix = 1;
    private static String ADD_COMMAND = "add";
    private static String REMOVE_COMMAND = "remove";
    private static String CLEAR_COMMAND = "clear";

    private DynamicTree treePanel;

    public LibraryOfNotesSwingGui() {
        super(new BorderLayout());
        treePanel = new DynamicTree();
                
        // Toolbar
        JToolBar jtb = new JToolBar("Debug");
        ImageIcon connectIcon = new ImageIcon("connectToDB.gif");
        JButton jbtnConnect = new JButton(connectIcon);
        jbtnConnect.setActionCommand("ConnectToDB");
        jbtnConnect.setToolTipText("Connect to database");

        jbtnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("You pressed a button!");
            }
        });

        jtb.add(jbtnConnect);

        ImageIcon createIcon = new ImageIcon("createNew.gif");
        JButton jbtnCreate = new JButton(createIcon);
        jbtnCreate.setActionCommand("CreateNewNote");
        jbtnCreate.setToolTipText("Create new note");
        
        jtb.add(jbtnCreate);

        add(jtb, BorderLayout.NORTH);
        
        ///
        
        JTextArea note = new JTextArea();
        JScrollPane noteView = new JScrollPane(note);
        
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, noteView);
        add(jsp, BorderLayout.CENTER);
        
        add(new JLabel("..."), BorderLayout.SOUTH);
        
    }  

    private static void createAndShowGUI() {
        JFrame mainFrame = new JFrame("Library of notes");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    

        LibraryOfNotesSwingGui newContentPane = new LibraryOfNotesSwingGui();
        newContentPane.setOpaque(true); 
        mainFrame.setContentPane(newContentPane);
        
        // Main menu
        JMenuBar jmb = new JMenuBar();
        JMenu jmFile = new JMenu("File");
        JMenuItem jmiOpen = new JMenuItem("Open");
        JMenuItem jmiExit = new JMenuItem("Exit");
        jmFile.add(jmiOpen);
        jmFile.addSeparator();
        jmFile.add(jmiExit);
        jmb.add(jmFile);
        mainFrame.setJMenuBar(jmb);
        
        //Display the window.
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
