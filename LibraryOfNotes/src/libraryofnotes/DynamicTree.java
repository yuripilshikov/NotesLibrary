/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryofnotes;

import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author YuriPilshikov
 */
public class DynamicTree extends JPanel{
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public DynamicTree() {
        super(new GridLayout(1, 0)); // ???
        rootNode = new DefaultMutableTreeNode("root node");
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());
        
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }
    
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }
    
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if(currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if(parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }                
        }        
    }
    
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();
        
        if(parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
        }
        return addObject(parentNode, child, true);        
    }
    
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
        return addObject(parent, child, false);
    }
    
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        if(parent == null) {
            parent = rootNode;
        }
        
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        if(shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }   
}
