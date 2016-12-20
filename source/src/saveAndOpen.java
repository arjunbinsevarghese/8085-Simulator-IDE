

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class saveAndOpen {
	JFileChooser fileChooser;
	FileNameExtensionFilter filter;
	String text="";
  public saveAndOpen() {
	 // JFrame iofiles=new JFrame();
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Choose a file");
    //iofiles.getContentPane().add(fileChooser);
    fileChooser.setVisible(true);
   
    FileNameExtensionFilter filter = new FileNameExtensionFilter("ALP files", "zen");
    fileChooser.addChoosableFileFilter(filter);
    filter = new FileNameExtensionFilter("Text files", "txt");
    fileChooser.addChoosableFileFilter(filter);
    int ret = fileChooser.showDialog(null, "Open file");

    if (ret == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      
      try {
		FileInputStream fstream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(fstream);
		 BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String kkk;
		try {
			while((kkk=br.readLine())!=null)
			text=text+kkk+"\n";
			{	
				
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
      } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      
      
      //iofiles.dispose();
    }
  }
    public String openFile()
    {
    return text;
  }
  public static void main(String[] args) {
    
    
  }
}