package libraryofnotes;

import libraryofnotes.model.TestNoteClass;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import libraryofnotes.db.TestCRUD;

/**
 *
 * @author YuriPilshikov
 */
public class MainProgram implements ActionListener {
    
    private int newNodeSuffix = 1;
    
    DefaultMutableTreeNode selectedNode = null;

    JFrame jfrm;
    JLabel jlab;
    DynamicTree treePanel;
    JToolBar jtb;
    JTabbedPane jtab;
    JScrollPane jscr;
    JEditorPane viewPane, helpPane;
    EditPanel editPane;
    
    TestCRUD crud;
    
    
    // for tests
    TestNoteClass notes;

    public MainProgram() {
        //replace with CRUD later
        //notes = TestNoteClass.testNotes; 
        crud = new TestCRUD();
        notes = crud.getNoteTree();
        
        JFrame jfrm = new JFrame("Library of notes");
        // set layout
        jfrm.setSize(800, 600);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menu
        JMenuBar jmb = new JMenuBar();

        JMenu jmFile = new JMenu("File");
        JMenuItem jmiOpen = new JMenuItem("Open");
        JMenuItem jmiClose = new JMenuItem("Close");
        JMenuItem jmiSave = new JMenuItem("Save");
        JMenuItem jmiExit = new JMenuItem("Exit");

        jmFile.add(jmiOpen);
        jmFile.add(jmiClose);
        jmFile.add(jmiSave);
        jmFile.addSeparator();
        jmFile.add(jmiExit);

        jmb.add(jmFile);

        jmiOpen.addActionListener(this);
        jmiClose.addActionListener(this);
        jmiSave.addActionListener(this);
        jmiExit.addActionListener(this);

        jfrm.setJMenuBar(jmb);

        jfrm.setVisible(true);

        // Toolbar
        jtb = new JToolBar("Actions");        
        ImageIcon createIcon = new ImageIcon("createNew.gif");
        JButton jbtnCreateNode = new JButton(createIcon);
        jbtnCreateNode.setActionCommand("CreateNew");
        jbtnCreateNode.setToolTipText("Create new node");

        jbtnCreateNode.addActionListener((ActionEvent e) -> {            
            
            TestNoteClass n = getNoteFromSelectedNode();
            n.getChildren().add(new TestNoteClass(9, "new note", "")); ////////////////////// TODO: write directly into DB and update root note
            treePanel.clear();
            populateTree(treePanel);
        });

        jtb.add(jbtnCreateNode);
        
        ImageIcon saveIcon = new ImageIcon("save.gif");
        JButton jbtnSaveNode = new JButton(saveIcon);
        jbtnSaveNode.setActionCommand("SaveNote");
        jbtnSaveNode.setToolTipText("Save edited note");
        
        jbtnSaveNode.addActionListener((ActionEvent e) -> {
            if(selectedNode == null) return;
            TestNoteClass n = getNoteFromSelectedNode();
            n.setName(editPane.getCaption().getText());            
            n.setContent(editPane.getContent().getText());        
            
            treePanel.clear();
            populateTree(treePanel);
        });
        
        jtb.add(jbtnSaveNode);

        jfrm.add(jtb, BorderLayout.NORTH);

        treePanel = new DynamicTree(this);
        populateTree(treePanel);
        
        //editPane = new JEditorPane();
        editPane = new EditPanel(this);
        viewPane = new JEditorPane();
        helpPane = new JEditorPane();
        viewPane.setContentType("text/html");
        helpPane.setContentType("text/html");      
        
        viewPane.setText("<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<h1>Test HTML page</h1>\n"
                + "<p>Lorem ipsum...</p><img src=\"https://www.w3schools.com/html/img_girl.jpg\">\n"
                + "\n"
                + "</body>\n"
                + "</html>");
        
        helpPane.setText("<html>\n"
                + "<head></head>\n"
                + "<body>\n"
                + "<h1>Help</h1>\n"
                + "<p>To rename a node, right click on it.</p>\n"
                + "\n"
                + "</body>\n"
                + "</html>");

        jtab = new JTabbedPane();
        jscr = new JScrollPane(jtab);

        jtab.add("Edit", editPane);
        jtab.add("View", viewPane);
        jtab.add("Help", helpPane);
        
        // split pane
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, jscr);
        jsp.setDividerLocation(200);
        jfrm.add(jsp, BorderLayout.CENTER);

        jlab = new JLabel("Status");
        jfrm.add(jlab, BorderLayout.SOUTH);

    }
    
    // get note from selected node
    private TestNoteClass getNoteFromSelectedNode() {        
        Object nodeInfo = selectedNode.getUserObject();
        TestNoteClass t = null;
        try {
            t = (TestNoteClass) nodeInfo;
        } catch (ClassCastException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
        return t;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comStr = e.getActionCommand();
        if (comStr.equals("Exit")) {
            System.exit(0);
        } else if(comStr.equals("Close")) {
            populateTree(treePanel);
        }
        jlab.setText(comStr + " selected");
    }
    
    // Move somewhere
    public void populateTree(DynamicTree tree) {
        // remove this string later
        tree.clear();
        
        TestNoteClass parent = new TestNoteClass(0, "Notes", "..."); /////////////////// TODO: write directly into DB and update root note
        //DefaultMutableTreeNode p1;
        //p1 = tree.addObject(null, parent);        
        DefaultMutableTreeNode currentNode = tree.addObject(null, notes);
        traverseNodeChildrenAndAdd(tree, currentNode, notes);      
    }
    
    public void traverseNodeChildrenAndAdd(DynamicTree tree, DefaultMutableTreeNode parent, TestNoteClass note) {        
        for(TestNoteClass n : note.getChildren()) {
            DefaultMutableTreeNode currentNode1 = tree.addObject(parent, n);
            if(n.getChildren() != null) {
                traverseNodeChildrenAndAdd(tree, currentNode1, n);
            }
        }
    }

    public static void main(String[] args) {        
        SwingUtilities.invokeLater(() -> {
            new MainProgram();
        });
    }
}
