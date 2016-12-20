import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class saveFile extends JFrame{
	private JTextField filename = new JTextField(), dir = new JTextField();
public saveFile()
{
	//setVisible(true);
	//setSize(400, 300);
}


 void SaveL(String savedata) {
  
      JFileChooser c = new JFileChooser();
      // Demonstrate "Save" dialog:
      int rVal = c.showSaveDialog(saveFile.this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
        filename.setText(c.getSelectedFile().getName());
        dir.setText(c.getCurrentDirectory().toString());
        
        String d=c.getCurrentDirectory().toString();
        String fn=filename.getText();
        
        
        File savefile=new File(d+"/"+fn);
       try {
		FileOutputStream fout = new FileOutputStream(savefile);
		byte[] b=savedata.getBytes();
		
		fout.write(b);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        
        
        
      }
      if (rVal == JFileChooser.CANCEL_OPTION) {
        filename.setText("You pressed cancel");
        dir.setText("");
      }
    
  }


}