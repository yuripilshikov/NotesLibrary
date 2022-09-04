package libraryofnotes;

import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import libraryofnotes.db.CRUD;
import libraryofnotes.model.Note;

public class MainPanel extends JPanel{
    private JTextArea note;
    private JTree notesTree;
    private JScrollPane treeView, noteView;
    private DefaultMutableTreeNode top;

    public MainPanel() {
        this.setLayout(new BoxLayout(this, 1));

        note = new JTextArea();      
        noteView = new JScrollPane(note);
        
        // tree
        top = new DefaultMutableTreeNode("The tree");
        createNodes(top);
        notesTree = new JTree(top);
        treeView = new JScrollPane(notesTree);

        notesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        notesTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)notesTree.getLastSelectedPathComponent();
                if(node == null) {
                    return;
                }
                Object nodeInfo = node.getUserObject();
                if(node.isLeaf()) {
                    Note n = (Note)nodeInfo;
                    note.setText(n.getName());
                }
            }
        });
        
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, noteView);
        jsp.setDividerLocation(200);
        this.add(jsp);
   
    }
    
    public void recalculateTree() {
        top = new DefaultMutableTreeNode("The tree");
        createNodes(top);
        notesTree = new JTree(top);
    }
    
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;
        
        category = new DefaultMutableTreeNode("Notes");
        top.add(category);
        
        CRUD crud = new CRUD();
        List<Note> notesList = crud.getAllNotes();
        
        for(Note n : notesList) {
            book = new DefaultMutableTreeNode(n);
            category.add(book);
        }
    }

    public DefaultMutableTreeNode getTop() {
        return top;
    }

    public JTree getNotesTree() {
        return notesTree;
    }
    
    
    
}
