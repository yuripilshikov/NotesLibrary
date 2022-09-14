package libraryofnotes;

import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author YuriPilshikov
 */
public class EditPanel extends JPanel {
    private JTextField caption;
    private JEditorPane content;
    private JLabel children;
    private MainProgram mp;

    public EditPanel(MainProgram mp) {
        super(new BorderLayout());
        this.mp = mp;
        caption = new JTextField();
        content = new JEditorPane();
        children = new JLabel("Some text");
        
        add(caption, BorderLayout.NORTH);        

        JScrollPane contentPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(contentPane, BorderLayout.CENTER);
        add(children, BorderLayout.SOUTH);
    }

    public void setCaption(JTextField caption) {
        this.caption = caption;
    }

    public void setContent(JEditorPane content) {
        this.content = content;
    }

    public void setChildren(JLabel parentNote) {
        this.children = parentNote;
    }

    public void setMp(MainProgram mp) {
        this.mp = mp;
    }

    public JTextField getCaption() {
        return caption;
    }

    public JEditorPane getContent() {
        return content;
    }

    public JLabel getChildren() {
        return children;
    }

    public MainProgram getMp() {
        return mp;
    }
}
