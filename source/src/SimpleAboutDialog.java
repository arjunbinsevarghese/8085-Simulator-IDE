import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SimpleAboutDialog extends JDialog {
  public SimpleAboutDialog(JFrame parent) {
    super(parent, "About 8085 IDE", true);

    Box b = Box.createVerticalBox();
    b.add(Box.createGlue());
    b.add(new JLabel("                       8085 Simulator IDE                 "));
    b.add(new JLabel(" "));
    b.add(new JLabel("     created by  RABBITFOOT developers"));
    b.add(new JLabel(" "));
    b.add(new JLabel(" web: www.rabbitfoot.tk"));
    b.add(new JLabel(" "));
    b.add(new JLabel(" download: www.8085ide.tk"));
    b.add(Box.createGlue());
    getContentPane().add(b, "Center");

    JPanel p2 = new JPanel();
    JButton ok = new JButton("OK");
    p2.add(ok);
    getContentPane().add(p2, "South");

    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        setVisible(false);
      }
    });

    setSize(250, 200);
  }

}