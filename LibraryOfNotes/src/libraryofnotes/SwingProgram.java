package libraryofnotes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import libraryofnotes.db.CRUD;
import libraryofnotes.utils.MyLogger;

public class SwingProgram implements ActionListener{
    MyLogger logger;
    JFrame jfrm;
    MainPanel mp;
    CRUD crud;

    public SwingProgram() {
        logger = MyLogger.getLogger();
        logger.info("Program started");
        crud = new CRUD();
        
        // GUI
        jfrm = new JFrame("Notes storage");
        jfrm.setSize(800, 600);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mp = new MainPanel();
        jfrm.add(mp, BorderLayout.CENTER);
        
        jfrm.add(new JLabel("..."), BorderLayout.SOUTH);
        
        
        // Main menu
        JMenuBar jmb = new JMenuBar();
        
        JMenu jmFile = new JMenu("File");
        JMenuItem jmiOpen = new JMenuItem("Open");
        JMenuItem jmiExit = new JMenuItem("Exit");
        
        jmFile.add(jmiOpen);
        jmFile.addSeparator();
        jmFile.add(jmiExit);
        jmb.add(jmFile);
        
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
        
        jbtnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create new empty note
                crud.insert(null);
                mp.recalculateTree();
            }
        });
        
        jtb.add(jbtnCreate);
        
        jfrm.add(jtb, BorderLayout.NORTH);
        
        jfrm.setJMenuBar(jmb);
        
        jfrm.setVisible(true);
    }
      
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SwingProgram();
            }
        });
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String comStr = e.getActionCommand();
        
        if(comStr.equals("Exit")) System.exit(0);
        
    }
}
