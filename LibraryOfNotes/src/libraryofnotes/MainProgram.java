package libraryofnotes;

import libraryofnotes.model.SimpleNote;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import libraryofnotes.db.SimpleCRUD;
import libraryofnotes.utils.MyLogger;

/**
 *
 * @author YuriPilshikov
 */
public class MainProgram implements ActionListener {
    
    private MyLogger logger = MyLogger.getLogger();    
    private DefaultMutableTreeNode selectedNode = null;
    private JFrame jfrm;
    private JLabel jlab;
    private DynamicTree treePanel;
    private JToolBar jtb;
    private JTabbedPane jtab;
    private JScrollPane jscr;
    private JEditorPane viewPane, helpPane;
    private EditPanel editPane;
    private SimpleCRUD crud;
    private SimpleNote notes;

    public MainProgram() {

        crud = new SimpleCRUD();
        notes = crud.getNoteTree();

        jfrm = new JFrame("Library of notes");
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
            SimpleNote parent = getNoteFromSelectedNode();   
            
            crud.createNote(parent.getId());
            logger.info("Created child node at " + parent.getId() + ". " + parent.getName());
            treePanel.clear();
            notes = crud.getNoteTree();
            populateTree(treePanel);
        });

        jtb.add(jbtnCreateNode);

        ImageIcon saveIcon = new ImageIcon("save.gif");
        JButton jbtnSaveNode = new JButton(saveIcon);
        jbtnSaveNode.setActionCommand("SaveNote");
        jbtnSaveNode.setToolTipText("Save edited note");

        jbtnSaveNode.addActionListener((ActionEvent e) -> {
            if (selectedNode == null) {
                return;
            }
            SimpleNote n = getNoteFromSelectedNode();
            n.setName(editPane.getCaption().getText());
            n.setContent(editPane.getContent().getText());

            crud.updateNote(n);
            logger.info("Node " + n.getName() + " saved.");

            treePanel.clear();
            notes = crud.getNoteTree();
            populateTree(treePanel);
        });

        jtb.add(jbtnSaveNode);

        ImageIcon deleteIcon = new ImageIcon("delete.gif");
        JButton jbtnDeleteNode = new JButton(deleteIcon);
        jbtnDeleteNode.setActionCommand("DeleteNode");
        jbtnDeleteNode.setToolTipText("Delete selected note");

        jbtnDeleteNode.addActionListener((ActionEvent e) -> {
            if (selectedNode == null) {
                return;
            }
            SimpleNote n = getNoteFromSelectedNode();
            if (n.getChildren().size() > 0) {
                JOptionPane.showMessageDialog(jfrm, "Deleting notes with children is not supported yet");
                return;
            }
            int id = n.getId();

            int confirmDelete = JOptionPane.showConfirmDialog(jfrm, "Are you sure you want to delete the note?", "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (confirmDelete == JOptionPane.YES_OPTION) {
                crud.DeleteNoteByID(id);

                treePanel.clear();
                notes = crud.getNoteTree();
                populateTree(treePanel);
            }
        });

        jtb.add(jbtnDeleteNode);

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
        jscr = new JScrollPane(jtab, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
    private SimpleNote getNoteFromSelectedNode() {
        Object nodeInfo = selectedNode.getUserObject();
        SimpleNote t = null;
        try {
            t = (SimpleNote) nodeInfo;
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
        } else if (comStr.equals("Close")) {
            populateTree(treePanel);
        }
        jlab.setText(comStr + " selected");
    }

    public void populateTree(DynamicTree tree) { 
        tree.clear();
        DefaultMutableTreeNode currentNode = tree.addObject(null, notes);
        traverseNodeChildrenAndAdd(tree, currentNode, notes);
    }

    public void traverseNodeChildrenAndAdd(DynamicTree tree, DefaultMutableTreeNode parent, SimpleNote note) {
        for (SimpleNote n : note.getChildren()) {
            DefaultMutableTreeNode currentNode1 = tree.addObject(parent, n);
            if (n.getChildren() != null) {
                traverseNodeChildrenAndAdd(tree, currentNode1, n);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainProgram();
        });
    }

    public DefaultMutableTreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(DefaultMutableTreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public JLabel getJlab() {
        return jlab;
    }

    public void setJlab(JLabel jlab) {
        this.jlab = jlab;
    }

    public JEditorPane getViewPane() {
        return viewPane;
    }

    public void setViewPane(JEditorPane viewPane) {
        this.viewPane = viewPane;
    }

    public EditPanel getEditPane() {
        return editPane;
    }

    public void setEditPane(EditPanel editPane) {
        this.editPane = editPane;
    }
    
    
    
}
