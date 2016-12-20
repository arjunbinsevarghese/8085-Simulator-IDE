import java.io.File;
import java.io.FilenameFilter;


public class isearch {
String result;
File f1;
public isearch()
{	

	String dir=System.getProperty("user.dir")+"/src/instruction";
	File f1=new File(dir);
}
public String getdata(String qry)
{
	FilenameFilter a=new OnlyExt(qry);
	String items[]=f1.list(a);
	//result;
	for(int i=0;i<items.length;i++)
	System.out.println(items[i]);
	
	
	
	return result;
}
	
}
