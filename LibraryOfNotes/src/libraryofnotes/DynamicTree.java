package libraryofnotes;

import libraryofnotes.model.SimpleNote;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author YuriPilshikov
 */
public class DynamicTree extends JPanel implements TreeSelectionListener {

    private MainProgram mp;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public DynamicTree(MainProgram mp) {
        super(new GridLayout(1, 0));
        this.mp = mp;
        rootNode = new DefaultMutableTreeNode(new SimpleNote(1, "THE VERY ROOT NODE", "Here be notes"));
        mp.setSelectedNode(rootNode);
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());

        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.addTreeSelectionListener(this);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }
    }

    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }
        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
        return addObject(parent, child, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        if (parent == null) {
            parent = rootNode;
        }

        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }     
        return childNode;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (tree.getLastSelectedPathComponent());
        if (node == null) {
            return;
        }
        Object nodeInfo = node.getUserObject();
        SimpleNote t = null;
        try {
            t = (SimpleNote) nodeInfo;
        } catch (ClassCastException ex) {
            System.err.println(ex.getMessage());
            return;
        }
        mp.setSelectedNode(node);
        mp.getJlab().setText(t.getName() + " selected.");
        mp.getViewPane().setText("<html><body><h1>" + t.getName() + "</h1><div>" + t.getContent() + "</div></body></html>");
        mp.getEditPane().getContent().setText(t.getContent());
        mp.getEditPane().getCaption().setText(t.getName());
        mp.getEditPane().getChildren().setText("Child notes count: " + t.getChildren().size());
    }

    class MyTreeModelListener implements TreeModelListener {

        @Override
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode) (node.getChildAt(index));
            Object nodeInfo = node.getUserObject();
            SimpleNote t = null;
            try {
                t = (SimpleNote) nodeInfo;
            } catch (ClassCastException ex) {
                System.err.println(ex.getMessage());
                return;
            }
            t.setName(node.toString()); // not working... wonder why
        }

        @Override
        public void treeNodesInserted(TreeModelEvent e) {
        }

        @Override
        public void treeNodesRemoved(TreeModelEvent e) {
        }

        @Override
        public void treeStructureChanged(TreeModelEvent e) {
        }
    }
}
