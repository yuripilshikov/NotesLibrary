package libraryofnotes;

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
    JEditorPane editPane, viewPane, helpPane;
    
    
    // for tests
    List<TestNoteClass> notes;

    public MainProgram() {
        //for tests
        createTestNoteList();
        
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
            treePanel.addObject("New node " + newNodeSuffix++);
        });

        jtb.add(jbtnCreateNode);
        
        ImageIcon saveIcon = new ImageIcon("save.gif");
        JButton jbtnSaveNode = new JButton(saveIcon);
        jbtnSaveNode.setActionCommand("SaveNote");
        jbtnSaveNode.setToolTipText("Save edited note");
        
        jbtnSaveNode.addActionListener((ActionEvent e) -> {
            if(selectedNode == null) return;
            TestNoteClass n = (TestNoteClass)selectedNode.getUserObject();
            n.setContent(editPane.getText());            
        });
        
        jtb.add(jbtnSaveNode);

        jfrm.add(jtb, BorderLayout.NORTH);

        treePanel = new DynamicTree(this);
        populateTree(treePanel);
        
        editPane = new JEditorPane();
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
        
        TestNoteClass parent = new TestNoteClass("Notes", "...");
        DefaultMutableTreeNode p1;
        p1 = tree.addObject(null, parent);
        for(TestNoteClass n : notes) {
            DefaultMutableTreeNode currentNode = tree.addObject(p1, n);
            if(n.getChildren() != null) {
                traverseNodeChildrenAndAdd(tree, currentNode, n);
            }
        }        
    }
    
    public void traverseNodeChildrenAndAdd(DynamicTree tree, DefaultMutableTreeNode parent, TestNoteClass note) {        
        for(TestNoteClass n : note.getChildren()) {
            DefaultMutableTreeNode currentNode = tree.addObject(parent, n);
            if(n.getChildren() != null) {
                traverseNodeChildrenAndAdd(tree, currentNode, n);
            }
        }
    }

    public static void main(String[] args) {        
        SwingUtilities.invokeLater(() -> {
            new MainProgram();
        });
    }
    
    
    // This is for testing purpose
    public void createTestNoteList() {
        notes = new ArrayList<>();
        notes.add(new TestNoteClass("first", "this is some text"));
        notes.add(new TestNoteClass("2", "this is some text"));
        notes.add(new TestNoteClass("3", "this is some text"));
        notes.add(new TestNoteClass("4", "this is some text"));
        notes.add(new TestNoteClass("5", "this is some text"));
        
        TestNoteClass p = notes.get(2);
        p.getChildren().add(new TestNoteClass("child 1", "this is some text"));        
    }
    

}
