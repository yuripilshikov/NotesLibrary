package libraryofnotes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import libraryofnotes.db.CRUD;
import libraryofnotes.model.Note;
import libraryofnotes.utils.MyLogger;

public class SwingProgram implements ActionListener {

    MyLogger logger;
    JFrame jfrm;
    CRUD crud;
    List<Note> notesList;
    JSplitPane jsp;

    DefaultMutableTreeNode notesNode;
    DefaultMutableTreeNode noteNode;

    private JTextArea note;
    private JTree notesTree;
    private JScrollPane treeView, noteView;
    private DefaultMutableTreeNode top;
    private DefaultTreeModel treeModel;

    public SwingProgram() {
        logger = MyLogger.getLogger();
        logger.info("Program started");
        crud = new CRUD();
        notesList = crud.getAllNotes();
        initializeUI();

        jfrm.setVisible(true);
    }

    private void createNodes(DefaultMutableTreeNode top) {

        notesNode = new DefaultMutableTreeNode("Notes");
        top.add(notesNode);

        initNoteChildren(notesNode, notesList);

    }

    private void initNoteChildren(DefaultMutableTreeNode parentNode, List<Note> source) {
        DefaultMutableTreeNode temp;
        for (Note n : source) {
            temp = new DefaultMutableTreeNode(n);
            parentNode.add(temp);
        }
    }

    private void initializeUI() {
        jfrm = new JFrame("Notes storage");
        jfrm.setSize(800, 600);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        note = new JTextArea();
        noteView = new JScrollPane(note);

        // tree
        top = new DefaultMutableTreeNode("The tree");
        createNodes(top);
        treeModel = new DefaultTreeModel(top);
        notesTree = new JTree(treeModel);
        treeView = new JScrollPane(notesTree);

        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(new ImageIcon("connectToDB.gif"));
        renderer.setOpenIcon(new ImageIcon("createNew.gif"));
        renderer.setClosedIcon(new ImageIcon("connectToDB.gif"));
        notesTree.setCellRenderer(renderer);

        notesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        notesTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) notesTree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                Object nodeInfo = node.getUserObject();
                if (node.isLeaf()) {
                    Note n = (Note) nodeInfo;
                    note.setText(n.getName());
                }
            }
        });

        jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, noteView);
        jsp.setDividerLocation(200);
        jfrm.add(jsp, BorderLayout.CENTER);

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
                crud.insertNote(null);

                // for tests only
                notesList.add(new Note(98, "something", "test2", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, null));
                System.out.println(notesList);
                notesNode = new DefaultMutableTreeNode("Notes");
                initNoteChildren(notesNode, notesList);
                treeModel.reload(notesNode);
                notesTree.repaint();
                

            }
        });

        jtb.add(jbtnCreate);

        jfrm.add(jtb, BorderLayout.NORTH);

        jfrm.setJMenuBar(jmb);

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

        if (comStr.equals("Exit")) {
            System.exit(0);
        }

    }
}
