package libraryofnotes;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class MyTreeModelListener implements TreeModelListener {

    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode) (node.getChildAt(index));

        System.out.println("The user has finished editing the node.");
        System.out.println("New value: " + node.getUserObject());

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
