import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author likhily2k
 */
public class mframe extends javax.swing.JFrame {
	//auto beta
	autocomplete inputauto;
	//auto beta
	
int f;

public insDB insdb=new insDB();


ArrayList<Integer> labeljmp= new ArrayList<Integer>();    
String mem[][]=new String[16384][2];
    String IO[][]=new String[256][2];
    int iparraylen=0;
    String iparray[]=new String[4000];
    linklist instruction=new linklist();
    String hex[]=new String[4000];
    labellist symtab=new labellist();
    String jmplabels[]=new String[1000];
    boolean STOPRUN=false;
    Timer timer;
    int speed=0;
    boolean RES=true;
    String APSW;
    //regs
    String A,B,C,D,E,H,L,SP,PC,M,HL,BC,DE,S,P;
    int iA,iB,iC,iD,iE,iH,iL,iSP,iPC,iM,iHL,iBC,iDE,iS,iP;
    int cyf,acf,pf,sf,zf;
    ImageIcon image,pie;
    int caladdr[]=new int[100];
	int calno=0;
	String fg;
	//arj db
	//DataBase Variables and Routines START {
	
static Connection con=null;
static Statement stm=null;
static String con_url="jdbc:derby:ins_db;create=true";
	

public void setupConn(){		//to setup a connection
try {
con=DriverManager.getConnection(con_url);
stm=con.createStatement();
}
catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}


public void viewIns(String key){
String sql="select * from inspool where name='"+key+"'";
ResultSet rst;
try {
rst=stm.executeQuery(sql);
while(rst.next()){
searchresultTextArea.setText(rst.getString("name"));
searchresultTextArea.append("\n--------------\n\n");
String str=rst.getString("details");
String[] sub=str.split(";");
int i=0;
	for(String dup: sub)
		searchresultTextArea.append(dup+"\n");
}

} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

}




public void listAll(){		//list all the instructions
String sql="select * from inspool";
ResultSet rst;

try {
rst=stm.executeQuery(sql);
while(rst.next()){
listmodel.addElement(rst.getString("name"));
}

} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}


public void listSome(String key){		//list all the instructions

key=key.toUpperCase();
String sql="select * from inspool";
ResultSet rst;

try {
rst=stm.executeQuery(sql);
while(rst.next()){

if(rst.getString("name").startsWith(key)){
	listmodel.addElement((rst.getString("name")));
}
}

} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}



	//	 } END DataBase Variables and Routines 



    
    //ARJ DBEND
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Creates new form mframe
     */
    public mframe() {  
       windowslook();
       initins();
       meminit();
       IOinit();
       
       insdb.createDB();
       
       
       
       //some minuks
       
       //
       
       
      // setACFLAG();
       initComponents();
       //auto beta
       inputauto=new autocomplete(input);
       
       
       //fc
        timer=new Timer(speed,runbut);
       //instruction.showins();
    }
    public void initall()
    {
    	iA=iB=iC=iD=iE=iH=iL=iSP=iPC=iM=iHL=iBC=iDE=0;
    	A=B=C=D=E=H=L=M="00";
    	cyf=acf=pf=sf=zf=0;
    	PC="4000";
    	iPC=16385;
    	resetreg();
    	consoleTextArea.setText("");
        
    }
   //actin listeners.. 
   /* ActionListener searchins=new ActionListener()
  	{
  		public void actionPerformed(ActionEvent ep)
  		{
  			isearch h=new isearch();
      	//saveAndOpen xxx=new saveAndOpen();
      	//String ip=xxx.openFile();
      	//input.setText("");
      	searchresultTextArea.setText("bitch please");
  			
      }};
    
    */
    
    
    ActionListener open=new ActionListener()
  	{
  		public void actionPerformed(ActionEvent ep)
  		{
      	saveAndOpen xxx=new saveAndOpen();
      	String ip=xxx.openFile();
      	//input.setText("");
      	input.setText(ip);
  			
      }};
      
      ActionListener runbut=new ActionListener()
    	{
    		public void actionPerformed(ActionEvent ep)
    		{	timer.start();
    		iPC=16385+f;//mofification needed.
    		pcTextField.setText(Integer.toHexString(iPC));	
    		if(f==0)
    			{
    				
    				initall();
    				
    				iSP=32768;
    				SP=make4b(Integer.toHexString(iSP));
    				spTextField.setText(SP);
    				caladdr=new int[100];
    				calno=0;
    		
    			}
    			
    		if((hex[f].equalsIgnoreCase("76"))||(hex[f].equalsIgnoreCase("C7"))||(hex[f].equalsIgnoreCase("CF"))||(hex[f].equalsIgnoreCase("D7"))||(hex[f].equalsIgnoreCase("DF"))||(hex[f].equalsIgnoreCase("E7"))||(hex[f].equalsIgnoreCase("EF"))||(hex[f].equalsIgnoreCase("F7"))||(hex[f].equalsIgnoreCase("FF")))//rst
    			{
    				System.out.println("halt");
    				timer.stop();
    				f=-1;
    			}
    			if(f!=-1)
    			{RUN();}
    			f++;
    			System.out.println(f);
    			
    			
        }};
    
    
    ActionListener resetreg=new ActionListener()
	{
		public void actionPerformed(ActionEvent ep)
		{
    	resetreg();
		}};
    ActionListener resetmem=new ActionListener()
   	{
   		public void actionPerformed(ActionEvent ep)
   		{
   			resetmem();
       	}
       };
 public void resetreg()
 {
	 for(int i=0;i<7;i++)
 	{
 		for(int j=1;j<9;j++)
 		{
 			registerTable.getModel().setValueAt(0, i, j);
 		}
 	}
 	aTextField.setText("00");bTextField.setText("00");cTextField.setText("00");
 	dTextField.setText("00");eTextField.setText("00");hTextField.setText("00");
 	lTextField.setText("00");spTextField.setText("0000");pcTextField.setText("0000");

 }
       
       
public void resetmem()
{
	for(int j=0;j<16384;j++)
		{
			memorytable.getModel().setValueAt("00",j,1);
			//memorytable.getModel().setValueAt(aValue, rowIndex, columnIndex)
		}
}
public void resetio()
{
	for(int j=0;j<256;j++)
		{
			iotable.getModel().setValueAt("00",j,1);
			//memorytable.getModel().setValueAt(aValue, rowIndex, columnIndex)
		}
}
     ActionListener reseti=new ActionListener()
      	{
      		public void actionPerformed(ActionEvent ep)
      		{
      			resetio();
          	}
          };
     ActionListener assm=new ActionListener()
        	{
        		public void actionPerformed(ActionEvent ep)
        		{	resetmem();
        			resetreg();
        			restflag();
        			initall();//bugfix
        			labeljmp.clear();//serious edit
        			//latest edit
        			resetio();//latest edit-error remov
        			//IOinit();
        			readip();
        			toHex();
            	}
            };
    //end
    public void restflag()
    {
    	cyf=0;
    	sf=0;
    	pf=0;
    	acf=0;
    	zf=0;
    	setFLAG();
    }
            
	public void meminit()
    {	int adr=16384;
       	for(int i=0;i<16384;i++)
    	{
    		mem[i][0]=Integer.toHexString(adr);
    		mem[i][1]="00";
    		adr++;
    	}
    } 
    public void IOinit()
    {	int ioadr=0;
    	for(int j=0;j<256;j++)
    	{
    		IO[j][0]=Integer.toHexString(ioadr);
    		IO[j][1]="00";
    		ioadr++;
    	}
    }  
//read input
public void readip()
   {
    	int curpos=0,si=0,ei=0;
    	iparraylen=0;//i=length of mnemon;
    	//fc
    	
    	String ipcode="nop\n";
    	ipcode=ipcode+input.getText();
        	//Arrays.fill(iparray,null);
    	do
    		{	curpos=ipcode.indexOf("\n",si);
    			if(curpos!=-1)
    			{
    				ei=curpos;
    				iparray[iparraylen]=ipcode.substring(si, ei).trim().toUpperCase();
    				iparraylen++;
    				si=ei+1;
    			}
    			else
    			{
    				iparray[iparraylen]=ipcode.substring(si).trim().toUpperCase();
    				iparraylen++;//?????
    			}
    			
    		}while(curpos!=-1);
    for(int x=0;x<iparraylen;x++)
    	System.out.println(iparray[x]);
    }
//add instructin
public void initins()
{
	//instruction.addinstruction(name, hex, tstate, impos, oplength, REGF, IMDTF, CALLF, JUMPF, ZEROOPF)
	instruction.addinstruction("ACI ","CE",7,1,2,false,true,false,false,false);
	instruction.addinstruction("ADC A","8F",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADC B","88",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADC C","89",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADC D","8A",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADC E","8B",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADC H","8C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADC L","8D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADC M","8E",7,0,1,true,false,false,false,false);
	instruction.addinstruction("ADD A","87",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADD B","80",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADD C","81",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADD D","82",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADD E","83",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADD H","84",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADD L","85",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ADD M","86",7,0,1,true,false,false,false,false);
	instruction.addinstruction("ADI ","C6",7,1,2,false,true,false,false,false);
	instruction.addinstruction("ANA A","A7",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ANA B","A0",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ANA C","A1",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ANA D","A2",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ANA E","A3",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ANA H","A4",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ANA L","A5",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ANA M","A6",7,0,1,true,false,false,false,false);
	instruction.addinstruction("ANI ","E6",7,1,2,false,true,false,false,false);
	instruction.addinstruction("CALL ","CD",18,1,4,false,false,true,false,false);
	instruction.addinstruction("CC ","DC",18,1,4,false,false,true,false,false);
	instruction.addinstruction("CNC ","D4",18,1,4,false,false,true,false,false);
	instruction.addinstruction("CP ","F4",18,1,4,false,false,true,false,false);
	instruction.addinstruction("CM ","FC",18,1,4,false,false,true,false,false);
	instruction.addinstruction("CPE ","EC",18,1,4,false,false,true,false,false);
	instruction.addinstruction("CPO ","E4",18,1,4,false,false,true,false,false);
	instruction.addinstruction("CZ ","CC",18,1,4,false,false,true,false,false);
	instruction.addinstruction("CNZ ","C4",18,1,4,false,false,true,false,false);
	instruction.addinstruction("CMA","2F",4,0,0,false,false,false,false,true);
	instruction.addinstruction("CMC","3F",4,0,0,false,false,false,false,true);
	instruction.addinstruction("CMP A","BF",4,0,1,true,false,false,false,false);
	instruction.addinstruction("CMP B","B8",4,0,1,true,false,false,false,false);
	instruction.addinstruction("CMP C","B9",4,0,1,true,false,false,false,false);
	instruction.addinstruction("CMP D","BA",4,0,1,true,false,false,false,false);
	instruction.addinstruction("CMP E","BB",4,0,1,true,false,false,false,false);
	instruction.addinstruction("CMP H","BC",4,0,1,true,false,false,false,false);
	instruction.addinstruction("CMP L","BD",4,0,1,true,false,false,false,false);
	instruction.addinstruction("CMP M","BE",7,0,1,true,false,false,false,false);
	instruction.addinstruction("CPI ","FE",7,1,2,false,true,false,false,false);
	instruction.addinstruction("DAA","27",4,0,0,false,false,false,false,true);
	instruction.addinstruction("DAD BC","09",10,0,2,true,false,false,false,false);
	instruction.addinstruction("DAD DE","19",10,0,2,true,false,false,false,false);
	instruction.addinstruction("DAD HL","29",10,0,2,true,false,false,false,false);
	instruction.addinstruction("DAD SP","39",10,0,2,true,false,false,false,false);
	instruction.addinstruction("DCR A","3D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("DCR B","05",4,0,1,true,false,false,false,false);
	instruction.addinstruction("DCR C","0D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("DCR D","15",4,0,1,true,false,false,false,false);
	instruction.addinstruction("DCR E","1D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("DCR H","25",4,0,1,true,false,false,false,false);
	instruction.addinstruction("DCR L","2D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("DCR M","35",10,0,1,true,false,false,false,false);
	instruction.addinstruction("DCX BC","0B",6,0,2,true,false,false,false,false);
	instruction.addinstruction("DCX DE","1B",6,0,2,true,false,false,false,false);
	instruction.addinstruction("DCX HL","2B",6,0,2,true,false,false,false,false);
	instruction.addinstruction("DCX SP","3B",6,0,2,true,false,false,false,false);
	instruction.addinstruction("DI","F3",4,0,0,false,false,false,false,true);
	instruction.addinstruction("EI","FB",4,0,0,false,false,false,false,true);
	instruction.addinstruction("HLT","76",7,0,0,false,false,false,false,true);
	instruction.addinstruction("IN ","DB",10,1,2,false,true,false,false,false);
	instruction.addinstruction("INR A","3C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("INR B","04",4,0,1,true,false,false,false,false);
	instruction.addinstruction("INR C","0C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("INR D","14",4,0,1,true,false,false,false,false);
	instruction.addinstruction("INR E","1C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("INR H","24",4,0,1,true,false,false,false,false);
	instruction.addinstruction("INR L","2C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("INR M","34",10,0,1,true,false,false,false,false);
	instruction.addinstruction("INX BC","03",6,0,2,true,false,false,false,false);
	instruction.addinstruction("INX DE","13",6,0,2,true,false,false,false,false);
	instruction.addinstruction("INX HL","23",6,0,2,true,false,false,false,false);
	instruction.addinstruction("INX SP","33",6,0,2,true,false,false,false,false);
	instruction.addinstruction("JMP ","C3",10,1,4,false,false,false,true,false);
	instruction.addinstruction("JC ","DA",10,1,4,false,false,false,true,false);
	instruction.addinstruction("JNC ","D2",10,1,4,false,false,false,true,false);
	instruction.addinstruction("JP ","F2",10,1,4,false,false,false,true,false);
	instruction.addinstruction("JM ","FA",10,1,4,false,false,false,true,false);
	instruction.addinstruction("JPE ","EA",10,1,4,false,false,false,true,false);
	instruction.addinstruction("JPO ","E2",10,1,4,false,false,false,true,false);
	instruction.addinstruction("JZ ","CA",10,1,4,false,false,false,true,false);
	instruction.addinstruction("JNZ ","C2",10,1,4,false,false,false,true,false);
	instruction.addinstruction("LDA ","3A",13,1,4,false,true,false,false,false);
	instruction.addinstruction("LDAX BC","0A",7,0,2,true,false,false,false,false);
	instruction.addinstruction("LDAX DE","1A",7,0,2,true,false,false,false,false);
	instruction.addinstruction("LHLD ","2A",16,1,4,false,true,false,false,false);
	
	instruction.addinstruction("LXI BC,","01",10,2,4,false,true,false,false,false);
	instruction.addinstruction("LXI DE,","11",10,2,4,false,true,false,false,false);
	instruction.addinstruction("LXI HL,","21",10,2,4,false,true,false,false,false);
	instruction.addinstruction("LXI SP,","31",10,2,4,false,true,false,false,false);
	
	instruction.addinstruction("MOV A,A","7F",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV A,B","78",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV A,C","79",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV A,D","7A",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV A,E","7B",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV A,H","7C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV A,L","7D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV B,A","47",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV B,B","40",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV B,C","41",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV B,D","42",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV B,E","43",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV B,H","44",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV B,L","45",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV C,A","4F",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV C,B","48",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV C,C","49",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV C,D","4A",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV C,E","4B",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV C,H","4C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV C,L","4D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV D,A","57",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV D,B","50",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV D,C","51",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV D,D","52",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV D,E","53",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV D,H","54",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV D,L","55",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV E,A","5F",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV E,B","58",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV E,C","59",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV E,D","5A",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV E,E","5B",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV E,H","5C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV E,L","5D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV H,A","67",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV H,B","60",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV H,C","61",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV H,D","62",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV H,E","63",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV H,H","64",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV H,L","65",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV L,A","6F",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV L,B","68",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV L,C","69",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV L,D","6A",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV L,E","6B",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV L,H","6C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV L,L","6D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV M,A","77",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV M,B","70",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV M,C","71",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV M,D","72",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV M,E","73",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV M,H","74",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV M,L","75",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV A,M","7E",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV B,M","46",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV C,M","4E",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV D,M","56",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV E,M","5E",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV H,M","66",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MOV L,M","6E",7,0,1,true,false,false,false,false);
	instruction.addinstruction("MVI A,","3E",7,2,2,false,true,false,false,false);
	instruction.addinstruction("MVI B,","06",7,2,2,false,true,false,false,false);
	instruction.addinstruction("MVI C,","0E",7,2,2,false,true,false,false,false);
	instruction.addinstruction("MVI D,","16",7,2,2,false,true,false,false,false);
	instruction.addinstruction("MVI E,","1E",7,2,2,false,true,false,false,false);
	instruction.addinstruction("MVI H,","26",7,2,2,false,true,false,false,false);
	instruction.addinstruction("MVI L,","2E",7,2,2,false,true,false,false,false);
	instruction.addinstruction("MVI M,","36",10,2,2,false,true,false,false,false);
	instruction.addinstruction("NOP","00",4,0,0,false,false,false,false,true);
	instruction.addinstruction("ORA A","B7",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ORA B","B0",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ORA C","B1",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ORA D","B2",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ORA E","B3",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ORA H","B4",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ORA L","B5",4,0,1,true,false,false,false,false);
	instruction.addinstruction("ORA M","B6",7,0,1,true,false,false,false,false);
	instruction.addinstruction("ORI ","F6",7,1,2,false,true,false,false,false);
	instruction.addinstruction("OUT ","D3",10,1,2,false,true,false,false,false);
	instruction.addinstruction("PCHL","E9",6,0,0,false,false,false,false,true);
	instruction.addinstruction("POP BC","C1",10,0,2,true,false,false,false,false);
	instruction.addinstruction("POP DE","D1",10,0,2,true,false,false,false,false);
	instruction.addinstruction("POP HL","E1",10,0,2,true,false,false,false,false);
	instruction.addinstruction("POP PSW","F1",10,0,3,true,false,false,false,false);
	instruction.addinstruction("PUSH BC","C5",12,0,2,true,false,false,false,false);
	instruction.addinstruction("PUSH DE","D5",12,0,2,true,false,false,false,false);
	instruction.addinstruction("PUSH HL","E5",12,0,2,true,false,false,false,false);
	instruction.addinstruction("PUSH PSW","F5",12,0,3,true,false,false,false,false);
	instruction.addinstruction("RAL","17",4,0,0,false,false,false,false,true);
	instruction.addinstruction("RAR","1F",4,0,0,false,false,false,false,true);
	instruction.addinstruction("RLC","07",4,0,0,false,false,false,false,true);
	instruction.addinstruction("RRC","0F",4,0,0,false,false,false,false,true);
	instruction.addinstruction("RET","C9",10,0,0,false,false,false,false,true);
	instruction.addinstruction("RC","D8",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RNC","D0",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RP","F0",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RM","F8",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RPE","E8",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RPO","E0",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RZ","C8",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RNZ","C0",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RIM","20",4,0,0,false,false,false,false,true);
	instruction.addinstruction("RST0","C7",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RST1","CF",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RST2","D7",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RST3","DF",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RST4","E7",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RST5","EF",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RST6","F7",12,0,0,false,false,false,false,true);
	instruction.addinstruction("RST7","FF",12,0,0,false,false,false,false,true);
	instruction.addinstruction("SBB A","9F",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SBB B","98",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SBB C","99",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SBB D","9A",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SBB E","9B",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SBB H","9C",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SBB L","9D",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SBB M","9E",7,0,1,true,false,false,false,false);
	instruction.addinstruction("SBI ","DE",7,1,2,false,true,false,false,false);
	instruction.addinstruction("SHLD ","22",16,1,4,false,true,false,false,false);
	instruction.addinstruction("SIM","30",4,0,0,false,false,false,false,true);
	instruction.addinstruction("SPHL","F9",6,0,0,false,false,false,false,true);
	instruction.addinstruction("STA ","32",13,1,4,false,true,false,false,false);
	instruction.addinstruction("STAX BC","02",7,0,2,true,false,false,false,false);
	instruction.addinstruction("STAX DE","12",7,0,2,true,false,false,false,false);
	instruction.addinstruction("STC","37",4,0,0,false,false,false,false,true);
	instruction.addinstruction("SUB A","97",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SUB B","90",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SUB C","91",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SUB D","92",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SUB E","93",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SUB H","94",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SUB L","95",4,0,1,true,false,false,false,false);
	instruction.addinstruction("SUB M","96",7,0,1,true,false,false,false,false);
	instruction.addinstruction("SUI ","D6",7,1,2,false,true,false,false,false);
	instruction.addinstruction("XCHG","EB",4,0,0,false,false,false,false,true);
	instruction.addinstruction("XRA A","AF",4,0,1,true,false,false,false,false);
	instruction.addinstruction("XRA B","A8",4,0,1,true,false,false,false,false);
	instruction.addinstruction("XRA C","A9",4,0,1,true,false,false,false,false);
	instruction.addinstruction("XRA D","AA",4,0,1,true,false,false,false,false);
	instruction.addinstruction("XRA E","AB",4,0,1,true,false,false,false,false);
	instruction.addinstruction("XRA H","AC",4,0,1,true,false,false,false,false);
	instruction.addinstruction("XRA L","AD",4,0,1,true,false,false,false,false);
	instruction.addinstruction("XRA M","AE",7,0,1,true,false,false,false,false);
	instruction.addinstruction("XRI ","EE",7,1,2,false,true,false,false,false);
	instruction.addinstruction("XTHL","E3",16,0,0,false,false,false,false,true);

}




public boolean errorimd(int size,String op,int index)
{	boolean ret=false;
	int len;
	op.trim();
	len=op.length();
	if(len!=size)
	{
		consoleTextArea.append("Error at line : "+(index)+"\nOperand size doesn't match ("+(size/2)+" byte expected)\n");
		ret=true;
	
	}
	else
	{
	boolean valchar=false;
	char singlechar[]=op.toCharArray();
		for(int opl=0;opl<size;opl++)
		{	valchar=false;
			for(int chari=48;chari<71;chari++)
			{
				if(singlechar[opl]==((char)chari))
				{
					valchar=true;
				}
				
				
				if(chari==57)chari=64;
			}
			if(!valchar)break;
		}
		if(!valchar)
		{
			consoleTextArea.append("Error at line : "+(index)+"\nInvalid operand '"+op+"'");
			ret=true;
		}
	}
	
	

return ret;
}




public void toHex()
{int mem=0,cp=0;

boolean printop=true,match=false;
//reset hex
for(int hexi=0;hexi<4000;hexi++)hex[hexi]="00";
consoleTextArea.setText("");
String sub;
opcode.setText("");
symtab.first=null;
for(int xx=0;xx<iparraylen;xx++)//pass 1
{	
	link cur=instruction.first;
	match=false;
	while(cur!=null)//instr cmpr
	{
		if(iparray[xx].equalsIgnoreCase(cur.name)&&cur.ZEROOPF)
		{	match=true;
			hex[mem]=cur.hexcode;
			memorytable.getModel().setValueAt(hex[mem],mem,1);
			mem++;
			
			
		}
		else if(iparray[xx].equalsIgnoreCase(cur.name)&&cur.REGF)
		{	match=true;
			hex[mem]=cur.hexcode;
			memorytable.getModel().setValueAt(hex[mem],mem,1);
			mem++;
		}
		else if(iparray[xx].startsWith(cur.name)&&cur.IMDTF)
		{	match=true;
			hex[mem]=cur.hexcode;
			memorytable.getModel().setValueAt(hex[mem],mem,1);
			mem++;
			if(cur.impos==2)
			{
				cp=iparray[xx].indexOf(',');
				cp++;
				sub=iparray[xx].substring(cp);
				
				if(cur.oplength==2)
				{	if(errorimd(2,sub,xx)){printop=false;}
					
					hex[mem]=sub;
					memorytable.getModel().setValueAt(hex[mem],mem,1);

					mem++;
				}
				else if(cur.oplength==4)
				{
					String sub2=sub.substring(0,2);
					String sub1=sub.substring(2);
					if(errorimd(4,sub,xx)) {printop=false;}
					hex[mem]=sub1;
					memorytable.getModel().setValueAt(hex[mem],mem,1);

					mem++;
					hex[mem]=sub2;
					memorytable.getModel().setValueAt(hex[mem],mem,1);
					mem++;
				}
			}
			else if(cur.impos==1)
			{
				cp=iparray[xx].indexOf(" ");
				cp++;
				sub=iparray[xx].substring(cp);
				if(cur.oplength==2)
				{
					hex[mem]=sub;
					if(errorimd(2,sub,xx)) {printop=false;}
					memorytable.getModel().setValueAt(hex[mem],mem,1);

					mem++;
				}
				else if(cur.oplength==4)
				{
					String sub2=sub.substring(0,2);
					String sub1=sub.substring(2);
					if(errorimd(4,sub,xx)){printop=false;}
					hex[mem]=sub1;
					memorytable.getModel().setValueAt(hex[mem],mem,1);

					mem++;
					hex[mem]=sub2;
					memorytable.getModel().setValueAt(hex[mem],mem,1);
					mem++;
				}

			}
			
		}
		else if(iparray[xx].startsWith(cur.name)&&(cur.JUMPF||cur.CALLF))
		{	match=true;
		
		
		hex[mem]=cur.hexcode;
		
				
		memorytable.getModel().setValueAt(hex[mem],mem,1);
		mem++;
	
		//code for inserting the label(L) into the hex codes
		
		hex[mem]=iparray[xx].substring(iparray[xx].indexOf(" ")+1).trim();

		labeljmp.add(mem);
		
		mem++;
		mem++;
			
		}
		else if(iparray[xx].indexOf(":")!=-1)
		{	match=true;
		String lab;
		boolean dup;
	
		lab=iparray[xx].substring(0,iparray[xx].indexOf(":")).trim();
		

		//label modification	check whther already defined, labl s der or not label has a adress or not.		
					
		if(symtab.search(lab))
		{
			symtab.add(lab, mem);
			
				
		}
		//multiple defintion
		else{
			consoleTextArea.append("Multiple Definition for Label "+lab+" at Line "+(xx+1));
			printop=false;
		}
		
		String temp=iparray[xx].substring(iparray[xx].indexOf(":")+1).trim();
		iparray[xx]=temp;
		xx--;
		break;

		}
		
		
		
		
		
	cur=cur.next;
	}
	if(!match)
	{	if(!iparray[xx].equals(""))
	{
		printop=false;
		consoleTextArea.append("Invalid instruction/register at line "+(xx)+"\n");
	}
	}
	
}
 //pass1 end
for(int txi=0;txi<mem;txi++)
System.out.print(hex[txi]+"\n");
 
 //pass2 start
 labellink curlab=symtab.first;
 //int p2=0;
 int testflag=0;
//label modification already defined label too.
 
for(int p2: labeljmp)
	{
		testflag=0;
		curlab=symtab.first;
		while(curlab!=null)
		{	
		  if(hex[p2].equalsIgnoreCase(curlab.label))
			{
			testflag=1;
			String jadr=Integer.toHexString(curlab.taddr+16384);
			hex[p2]=jadr.substring(2);
			memorytable.getModel().setValueAt(hex[p2],p2,1);
			p2++;
			hex[p2]=jadr.substring(0,2);
			memorytable.getModel().setValueAt(hex[p2],p2,1);
			break;
		    }
		curlab=curlab.next;
		}
if(testflag==0) {printop=false; consoleTextArea.append("Undefined label "+hex[p2]);}
}		
for(int txi=1;txi<mem;txi++)
opcode.append(hex[txi]+"\n");
if(!printop) {opcode.setText(""); resetmem();
resetio();
Toolkit.getDefaultToolkit().beep();
}
}
 
//RUN
public void RUN()
{ try{

	
	
	//initall();
	STOPRUN=false;
	int instrbyte=0;
	//for(int f=0;f<hex.length;f++)
	//{	//LXI 
		if(hex[f].equalsIgnoreCase("01"))
		{
			instrbyte=3;
			iC=Integer.valueOf(hex[f+1], 16).intValue();
			iB=Integer.valueOf(hex[f+2], 16).intValue();
			tobin(iB, 1);
			tobin(iC, 2);
			C=hex[f+1];
			B=hex[f+2];
			BC=B+C;
			iBC=Integer.valueOf(BC, 16).intValue();
			f=f+2;
			
			//Update UI
			cTextField.setText(C);
	        bTextField.setText(B);
		}
		else if(hex[f].equalsIgnoreCase("11"))
		{
			instrbyte=3;
			iE=Integer.valueOf(hex[f+1], 16).intValue();
			iD=Integer.valueOf(hex[f+2], 16).intValue();
			E=hex[f+1];
			D=hex[f+2];
			tobin(iD,3);
			tobin(iE,4);
			
			DE=D+E;
			iDE=Integer.valueOf(DE, 16).intValue();
			f=f+2;
			//Update UI
			eTextField.setText(E);
	        dTextField.setText(D);
		}
		else if(hex[f].equalsIgnoreCase("21"))
		{
			instrbyte=3;
			iL=Integer.valueOf(hex[f+1], 16).intValue();
			iH=Integer.valueOf(hex[f+2], 16).intValue();

			tobin(iH,5);
			tobin(iL,6);
			
			L=hex[f+1];
			H=hex[f+2];
			HL=H+L;
			iHL=Integer.valueOf(HL, 16).intValue();
			f=f+2;
			//Update UI
			lTextField.setText(L);
	        hTextField.setText(H);
		}
		else if(hex[f].equalsIgnoreCase("31"))
		{
			instrbyte=3;
			String t1=hex[f+1];
			String t2=hex[f+2];
			SP=t2+t1;
			iSP=Integer.valueOf(SP, 16).intValue();
			f=f+2;
			//Update UI
			spTextField.setText(SP);
		}
		//MVI R,DATA
		else if(hex[f].equalsIgnoreCase("3E"))
		{
			instrbyte=2;
			A=hex[f+1];
			iA=Integer.valueOf(hex[f+1], 16).intValue();
			f++;
			aTextField.setText(A);
			tobin(iA, 0);
		}
		else if(hex[f].equalsIgnoreCase("06"))
		{
			instrbyte=2;
			B=hex[f+1];
			iB=Integer.valueOf(hex[f+1], 16).intValue();
			tobin(iB, 1);
			f++;
			bTextField.setText(B);
		}
		else if(hex[f].equalsIgnoreCase("0E"))
		{
			instrbyte=2;
			C=hex[f+1];
			iC=Integer.valueOf(hex[f+1], 16).intValue();
			tobin(iC, 2);
			f++;
			cTextField.setText(C);
		}
		
		else if(hex[f].equalsIgnoreCase("16"))
		{
			instrbyte=2;
			D=hex[f+1];
			iD=Integer.valueOf(hex[f+1], 16).intValue();

			tobin(iD, 3);
			f++;
			dTextField.setText(D);
		}
		
		else if(hex[f].equalsIgnoreCase("1E"))
		{
			instrbyte=2;
			E=hex[f+1];;
			iE=Integer.valueOf(hex[f+1], 16).intValue();
			tobin(iE, 4);
			f++;
			eTextField.setText(E);
		}
		else if(hex[f].equalsIgnoreCase("26"))
		{
			instrbyte=2;
			H=hex[f+1];;
			iH=Integer.valueOf(hex[f+1], 16).intValue();
			tobin(iH, 5);
			f++;
			hTextField.setText(H);
		}
		else if(hex[f].equalsIgnoreCase("2E"))
		{
			instrbyte=2;
			L=hex[f+1];
			iL=Integer.valueOf(hex[f+1], 16).intValue();
			tobin(iL, 6);
			f++;
			lTextField.setText(L);
		}
		//MOV R,M
		else if(hex[f].equalsIgnoreCase("7E"))//mov a,m
		{
			instrbyte=1;
			A=ValAtLoc(loc());
			iA=Integer.valueOf(A, 16).intValue();

			tobin(iA, 0);
			aTextField.setText(A);
		}
		else if(hex[f].equalsIgnoreCase("46"))//mov a,m
		{
			instrbyte=1;
			B=ValAtLoc(loc());
			iB=Integer.valueOf(B, 16).intValue();

			tobin(iB, 1);
			bTextField.setText(B);
		}
		else if(hex[f].equalsIgnoreCase("4E"))//mov a,m
		{
			instrbyte=1;
			C=ValAtLoc(loc());
			iC=Integer.valueOf(C, 16).intValue();

			tobin(iC, 2);
			cTextField.setText(C);
		}
		else if(hex[f].equalsIgnoreCase("56"))//mov a,m
		{
			instrbyte=1;
			D=ValAtLoc(loc());
			iD=Integer.valueOf(D, 16).intValue();

			tobin(iD, 3);
			dTextField.setText(D);
		}
		else if(hex[f].equalsIgnoreCase("5E"))//mov a,m
		{
			instrbyte=1;
			E=ValAtLoc(loc());
			iE=Integer.valueOf(E, 16).intValue();

			tobin(iE, 4);
			eTextField.setText(E);
		}
		else if(hex[f].equalsIgnoreCase("66"))//mov a,m
		{
			instrbyte=1;
			H=ValAtLoc(loc());
			iH=Integer.valueOf(H, 16).intValue();

			tobin(iH, 5);
			hTextField.setText(H);
		}
		else if(hex[f].equalsIgnoreCase("6E"))//mov a,m
		{
			instrbyte=1;
			L=ValAtLoc(loc());
			iL=Integer.valueOf(L, 16).intValue();

			tobin(iL, 6);
			lTextField.setText(L);
		}
		//MVi M,8
		else if(hex[f].equalsIgnoreCase("36"))//mov m,8
		{
			instrbyte=2;
			
			int tiHL=loc();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(hex[f+1],tLoc,1);
			f++;
		}
		//MOV M,R
		else if(hex[f].equalsIgnoreCase("77"))//mov m,a
		{
			instrbyte=1;
			int tiHL=loc();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(A,tLoc,1);
		}
		else if(hex[f].equalsIgnoreCase("70"))//mov m,a
		{
			instrbyte=1;
			int tiHL=loc();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(B,tLoc,1);
		}
		else if(hex[f].equalsIgnoreCase("71"))//mov m,a
		{
			instrbyte=1;
			int tiHL=loc();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(C,tLoc,1);
		}
		else if(hex[f].equalsIgnoreCase("72"))//mov m,a
		{
			instrbyte=1;
			int tiHL=loc();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(D,tLoc,1);
		}
		else if(hex[f].equalsIgnoreCase("73"))//mov m,a
		{
			instrbyte=1;
			int tiHL=loc();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(E,tLoc,1);
		}
		else if(hex[f].equalsIgnoreCase("74"))//mov m,a
		{
			instrbyte=1;
			int tiHL=loc();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(H,tLoc,1);
		}
		else if(hex[f].equalsIgnoreCase("75"))//mov m,a
		{
			instrbyte=1;
			int tiHL=loc();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(L,tLoc,1);
		}
		//STA 16
		else if(hex[f].equalsIgnoreCase("32"))//sta 16
		{
			instrbyte=3;
			String stLoc=hex[f+2]+hex[f+1];
			int tiHL=Integer.valueOf(stLoc, 16).intValue();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(A,tLoc,1);
			f=f+2;
		}
		//STAX
		else if(hex[f].equalsIgnoreCase("02"))//stax bc
		{
			instrbyte=1;
			String t1=Integer.toHexString(iB)+Integer.toHexString(iC);
			int tiHL=Integer.valueOf(t1, 16).intValue();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(A,tLoc,1);
		}
		else if(hex[f].equalsIgnoreCase("12"))//stax de
		{
			instrbyte=1;
			String t1=Integer.toHexString(iD)+Integer.toHexString(iE);
			int tiHL=Integer.valueOf(t1, 16).intValue();
			int tLoc=tiHL-16384;
			memorytable.getModel().setValueAt(A,tLoc,1);
		}
		//mov r1,r2
		  else if(hex[f].equalsIgnoreCase("7F"))
		  {//a,a
	      A=A;
	      iA=iA;
			tobin(iA, 0);
	      aTextField.setText(A);
	      }
		  else if(hex[f].equalsIgnoreCase("78")){
	            A=B;
	            iA=iB;
				tobin(iA, 0);
	            aTextField.setText(A);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("79")){
	            A=C;
	            iA=iC;
				tobin(iA, 0);
	            aTextField.setText(A);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("7A")){
	            A=D;
	            iA=iD;
				tobin(iA, 0);
	            aTextField.setText(A);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("7B")){
	            A=E;
	            iA=iE;
				tobin(iA, 0);
	            aTextField.setText(A);
	        }
	        else if(hex[f].equalsIgnoreCase("7C")){
	            A=H;
	            iA=iH;
				tobin(iA, 0);
	            aTextField.setText(A);
	        }
	        else if(hex[f].equalsIgnoreCase("7D")){
	            A=L;
	            iA=iL;
				tobin(iA, 0);
	            aTextField.setText(A);
	        }
		
	        else if(hex[f].equalsIgnoreCase("47")){
	            B=A;
	            iB=iA;
				tobin(iB, 1);
	            bTextField.setText(B);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("40")){
	            B=B;
	            iB=iB;
				tobin(iB, 1);
	            bTextField.setText(B);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("41")){
	            B=C;
	            iB=iC;
				tobin(iB, 1);
	            bTextField.setText(B);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("42")){
	            B=D;
	            iB=iD;
				tobin(iB, 1);
	            bTextField.setText(B);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("43")){
	            B=E;
	            iB=iE;
				tobin(iB, 1);
	            bTextField.setText(B);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("44")){
	            B=H;
	            iB=iH;
				tobin(iB, 1);
	            bTextField.setText(B);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("45")){
	            B=L;
	            iB=iL;
				tobin(iB, 1);
	             
	            bTextField.setText(B);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("4F")){
	            C=A;
	            iC=iA;

				tobin(iC, 2);
	            cTextField.setText(C);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("48")){
	            C=B;
	            iC=iB;
				tobin(iC, 2);
	             
	            cTextField.setText(C);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("49")){
	            C=C;
	            iC=iC;
				tobin(iC, 2);
	             
	            cTextField.setText(C);
	            }
	        
	        else if(hex[f].equalsIgnoreCase("4A")){
	            C=D;
	            iC=iD;
				tobin(iC, 2);
	             
	            cTextField.setText(C);
	            }
	        
	        else if(hex[f].equalsIgnoreCase("4B")){
	            C=E;
	            iC=iE;
				tobin(iC, 2);
	             
	            cTextField.setText(C);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("4C")){
	            C=H;
	            iC=iH;
				tobin(iC, 2);
	             
	            cTextField.setText(C);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("4D")){
	            C=L;
	            iC=iL;
				tobin(iC, 2);
	             
	            cTextField.setText(C);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("57")){
	            D=A;
	            iD=iA;
				tobin(iD, 3);
	             
	            dTextField.setText(D);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("50")){
	            D=B;
	            iD=iB;
				tobin(iD, 3);
	             
	            dTextField.setText(D);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("51")){
	            D=C;
	            iD=iC;
				tobin(iD, 3);
	             
	            dTextField.setText(D);
	        }
	        else if(hex[f].equalsIgnoreCase("52")){
	            D=D;
	            iD=iD;
				tobin(iD, 3);
	             
	            dTextField.setText(D);
	        }
	        else if(hex[f].equalsIgnoreCase("53")){
	            D=E;
	            iD=iE;
				tobin(iD, 3);
	             
	            dTextField.setText(D);
	        }
	        else if(hex[f].equalsIgnoreCase("54")){
	            D=H;
	            iD=iH;
				tobin(iD, 3);
	             
	            dTextField.setText(D);
	        }
	        else if(hex[f].equalsIgnoreCase("55")){
	            D=L;
	            iD=iL;
				tobin(iD, 3);
	             
	            dTextField.setText(D);
	        }
	        else if(hex[f].equalsIgnoreCase("5F")){
	            E=A;
	            iE=iA;
				tobin(iE, 4);
	             
	            eTextField.setText(E);
	        }
	        else if(hex[f].equalsIgnoreCase("58")){
	            E=B;
	            iE=iB;
				tobin(iE, 4);
	             
	            eTextField.setText(E);
	        }
	        else if(hex[f].equalsIgnoreCase("59")){
	            E=C;
	            iE=iC;
				tobin(iE, 4);
	             
	            eTextField.setText(E);
	        }
	        else if(hex[f].equalsIgnoreCase("5A")){
	            E=D;
	            iE=iD;
				tobin(iE, 4);
	             
	            eTextField.setText(E);
	        }
	        else if(hex[f].equalsIgnoreCase("5B")){
	            E=E;
	            iE=iE;
				tobin(iE, 4);
	             
	            eTextField.setText(E);
	        }
	        else if(hex[f].equalsIgnoreCase("5C")){
	            E=H;
	            iE=iH;
				tobin(iE, 4);
	             
	            eTextField.setText(E);
	        }
	        else if(hex[f].equalsIgnoreCase("5D")){
	            E=L;
	            iE=iL;
				tobin(iE, 4);
	             
	            eTextField.setText(E);
	        }
	        else if(hex[f].equalsIgnoreCase("67")){
	            H=A;
	            iH=iA;
				tobin(iH, 5);
	             
	            hTextField.setText(H);
	        }

	        else if(hex[f].equalsIgnoreCase("60")){
	            H=B;
	            iH=iB;
				tobin(iH, 5);
	             
	            hTextField.setText(H);
	        }
	            

	        else if(hex[f].equalsIgnoreCase("61")){
	            H=C;
	            iH=iC;
				tobin(iH, 5);
	             
	            hTextField.setText(H);
	        }
	        

	        else if(hex[f].equalsIgnoreCase("62")){
	            H=D;
	            iH=iD;
				tobin(iH, 5);
	             
	            hTextField.setText(H);
	        }

	        else if(hex[f].equalsIgnoreCase("63")){
	            H=E;
	            iH=iE;
				tobin(iH, 5);
	             
	            hTextField.setText(H);
	        }

	        else if(hex[f].equalsIgnoreCase("64")){
	            H=H;
	            iH=iH;
				tobin(iH, 5);
	             
	            hTextField.setText(H);
	        }

	        else if(hex[f].equalsIgnoreCase("65")){
	            H=L;
	            iH=iL;
				tobin(iH, 5);
	             
	            hTextField.setText(H);
	        }
	        

	        else if(hex[f].equalsIgnoreCase("6F")){
	            L=A;
	            iL=iA;
				tobin(iL, 6);
	             
	            lTextField.setText(L);
	        }
	        
	        else if(hex[f].equalsIgnoreCase("68")){
	            L=B;
	            iL=iB;
				tobin(iL, 6);
	             
	            lTextField.setText(L);
	        }
	        else if(hex[f].equalsIgnoreCase("69")){
	            L=C;
	            iL=iC;
				tobin(iL, 6);
	             
	            lTextField.setText(L);
	        }    
	        else if(hex[f].equalsIgnoreCase("6A")){
	            L=D;
	            iL=iD;
				tobin(iL, 6);
	             
	            lTextField.setText(L);
	        }
	        else if(hex[f].equalsIgnoreCase("6B")){
	            L=E;
	            iL=iE;
				tobin(iL, 6);
	             
	            lTextField.setText(L);
	        }
	        else if(hex[f].equalsIgnoreCase("6C")){
	            L=H;
	            iL=iH;
				tobin(iL, 6);
	             
	            lTextField.setText(L);
	        }
	        else if(hex[f].equalsIgnoreCase("6D")){
	            L=L;
	            iL=iL;
				tobin(iL, 6);
	             
	            lTextField.setText(L);
	        }
		
		//end Mov R1,R2
		//LDA 16 by ABV
	        else if(hex[f].equalsIgnoreCase("3A"))
	        {
	            String loc=hex[f+2]+hex[f+1];
	            int iloc=Integer.valueOf(loc, 16).intValue();
	            String mip=ValAtLoc(iloc);
	            //hex[iloc-16384]=mip;//fc..venda enn thonnunnu
	            A=mip;    
	            aTextField.setText(A);
	            iA=Integer.valueOf(A,16).intValue();

				tobin(iA, 0);
	            f+=2;
	        }
		//LDAX abv
	        else if(hex[f].equalsIgnoreCase("0A")){
                String mip;
                BC=B+C;
                iBC=Integer.valueOf(BC, 16).intValue();
                mip=ValAtLoc(iBC);
                //hex[iBC-16384]=mip;
                A=mip;    
                aTextField.setText(A);
                iA=Integer.valueOf(A,16).intValue();

				tobin(iA, 0);
            }
    
            else if(hex[f].equalsIgnoreCase("1A")){
                String mip;
                DE=D+E;
                iDE=Integer.valueOf(DE, 16).intValue();
                mip=ValAtLoc(iDE);
                //hex[iDE-16384]=mip;
                A=mip;    
                aTextField.setText(A);
                iA=Integer.valueOf(A,16).intValue();

				tobin(iA, 0);
            }
		  //LHLD ABV
        
            else if(hex[f].equalsIgnoreCase("2A")){
                
               
                
                String loc=hex[f+2]+hex[f+1];
                int iloc=Integer.valueOf(loc, 16).intValue();
                String mip=ValAtLoc(iloc);
                //hex[iloc-16384]=mip;
                L=mip;    
                lTextField.setText(L);
                iL=Integer.valueOf(L,16).intValue();

				tobin(iL, 6);
                iloc++;
                mip=ValAtLoc(iloc);
                //hex[iloc-16384]=mip;
                H=mip;    
                hTextField.setText(H);
                iH=Integer.valueOf(H,16).intValue();

				tobin(iH, 5);
                f+=2;
            } 
		//SHLD abc
            else if(hex[f].equalsIgnoreCase("22"))
            {
                String loc=hex[f+2]+hex[f+1];
                int iloc=Integer.valueOf(loc, 16).intValue();
                int m=iloc-16384;
                //hex[m]=L;
                memorytable.getModel().setValueAt(L,m,1);
                m++;
                iloc++;
                m=iloc-16384;
                //hex[m]=H;
                memorytable.getModel().setValueAt(H,m,1);
                f+=2;
            }
		
		   //XCHG abv
            else if(hex[f].equalsIgnoreCase("EB")){
                String T;
                int iT;
                iT=iH;
                T=H;
                iH=iD;
                H=D;
                iD=iT;
                D=T;
                iT=iL;
                T=L;
                iL=iE;
                L=E;
                iE=iT;
                E=T;
                hTextField.setText(H);
                lTextField.setText(L);
                dTextField.setText(D);
                eTextField.setText(E);

				tobin(iD, 3);
				tobin(iE, 4);
				tobin(iH, 5);
				tobin(iL, 6);
                
                
            }
		
		  //SPHL abv unchecked
        
            else if(hex[f].equalsIgnoreCase("F9"))
            {    
                SP=make2b(H)+make2b(L);
                iSP=Integer.valueOf(SP,16).intValue();
                spTextField.setText(SP);
            }
            
    //XTHL
            else if(hex[f].equalsIgnoreCase("E3")){
                String T;
                int iT;
                
                
                String mip;
                int m=iSP;
                
                mip=ValAtLoc(m);            
                T=mip;
                iT=Integer.valueOf(T,16).intValue();
                
                
               // hex[m-16384]=L;
                memorytable.getModel().setValueAt(hex[m-16384],m-16384,1);
                
                L=make2b(T);
                iL=iT;

				tobin(iL, 6);
                lTextField.setText(L);
                
                        
                m++;
                
                mip=ValAtLoc(m);            
                T=mip;
                iT=Integer.valueOf(T,16).intValue();
             //   hex[m-16384]=H;
                memorytable.getModel().setValueAt(hex[m-16384],m-16384,1);
                H=make2b(T);
                iH=iT;

				tobin(iH, 5);
                hTextField.setText(H);
                
            }
            
        //POP Rp//binary not set
        
            else if(hex[f].equalsIgnoreCase("C1")){
                String mip;
                mip=ValAtLoc(iSP);            
                C=mip;
         
                iC=Integer.valueOf(C,16);
                cTextField.setText(C);
                memorytable.getModel().setValueAt("00", iSP-16384, 1);
                iSP++;
                mip=ValAtLoc(iSP);
                B=mip;

                iB=Integer.valueOf(B,16);

                memorytable.getModel().setValueAt("00", iSP-16384, 1);
                bTextField.setText(B);
                iSP++;
                spTextField.setText(SP=make4b(Integer.toHexString(iSP)));
             }
        
            else if(hex[f].equalsIgnoreCase("D1")){
                String mip;
                mip=ValAtLoc(iSP);            
                E=mip;
                iE=Integer.valueOf(E,16);
                
                eTextField.setText(E);
                memorytable.getModel().setValueAt("00", iSP-16384, 1);
                iSP++;
                mip=ValAtLoc(iSP);
                D=mip;
                iD=Integer.valueOf(D,16);
                
                dTextField.setText(D);
                memorytable.getModel().setValueAt("00", iSP-16384, 1);
                iSP++;

                spTextField.setText(SP=make4b(Integer.toHexString(iSP)));
            }
            
            else if(hex[f].equalsIgnoreCase("E1")){
                String mip;
                mip=ValAtLoc(iSP);            
                L=mip;
                iL=Integer.valueOf(L,16);
                
                lTextField.setText(L);
                memorytable.getModel().setValueAt("00", iSP-16384, 1);
                iSP++;
                mip=ValAtLoc(iSP);
                H=mip;
                iH=Integer.valueOf(H,16);
                
                hTextField.setText(H);
                memorytable.getModel().setValueAt("00", iSP-16384, 1);
                iSP++;
                spTextField.setText(SP=make4b(Integer.toHexString(iSP)));
                
            }
            
            else if(hex[f].equalsIgnoreCase("F1")){
            	String mip;
                mip=ValAtLoc(iSP);            
               // PSW=mip;
                //pswTextField.setText(PSW);
                String hfg=ValAtLoc(iSP);
                int ifg=Integer.valueOf(hfg, 16);
                char[] cfg=make8bit(ifg);
                sf=Integer.parseInt(String.valueOf(cfg[0]));
                zf=Integer.parseInt(String.valueOf(cfg[1]));
                acf=Integer.parseInt(String.valueOf(cfg[3]));
                pf=Integer.parseInt(String.valueOf(cfg[5]));
                cyf=Integer.parseInt(String.valueOf(cfg[7]));
               // System.out.println("ggggggg"+sf);
                memorytable.getModel().setValueAt("00",iSP-16384,1);
                
                iSP++;
                A=ValAtLoc(iSP);
                iA=Integer.valueOf(A, 16);
                aTextField.setText(A);
                memorytable.getModel().setValueAt("00",iSP-16384,1);
                
                iSP++;
                setFLAG();
                spTextField.setText(SP=make4b(Integer.toHexString(iSP)));
                    
            }
		
		   //PUSH Rp
        
            else if(hex[f].equalsIgnoreCase("C5")){
                int m=iSP-16384;
                m--;
                iSP--;
                //hex[m]=B;
                memorytable.getModel().setValueAt(B,m,1);
                m--;
                iSP--;
                //hex[m]=C;
                memorytable.getModel().setValueAt(C,m,1);
                spTextField.setText(SP=make4b(Integer.toHexString(iSP)));
            }
            
            else if(hex[f].equalsIgnoreCase("D5")){
                int m=iSP-16384;
                m--;
                iSP--;
                //hex[m]=D;
                memorytable.getModel().setValueAt(D,m,1);
                m--;
                iSP--;
                //hex[m]=E;
                memorytable.getModel().setValueAt(E,m,1);
                spTextField.setText(SP=make4b(Integer.toHexString(iSP)));
            }
            
            else if(hex[f].equalsIgnoreCase("E5")){
            int    m=iSP-16384;
                m--;
                iSP--;
                //hex[m]=H;
                memorytable.getModel().setValueAt(H,m,1);
                m--;
                iSP--;
                //hex[m]=L;
                memorytable.getModel().setValueAt(L,m,1);
                spTextField.setText(SP=make4b(Integer.toHexString(iSP)));
            }
            
            else if(hex[f].equalsIgnoreCase("F5")){
            	 int m=iSP-16384;
                 m--;
                 iSP--;
                 
                 //hex[m]=PSW.substring(0, 2);a
                 memorytable.getModel().setValueAt(A,m,1);
                 m--;
                 iSP--;
                 fg=Integer.toString(sf)+Integer.toString(zf)+'0'+Integer.toString(acf)+'0'+Integer.toString(pf)+'0'+Integer.toString(cyf);
                 int ifg=Integer.valueOf(fg, 2);
                 String hfg=make2b(Integer.toHexString(ifg));
   
                 //hex[m]=PSW.substring(2);fg
                 memorytable.getModel().setValueAt(hfg,m,1);
                 spTextField.setText(SP=make4b(Integer.toHexString(iSP)));

            }
		
		//Y2K arithmtc strt...
		
		//AdI 8bit
            else if(hex[f].equalsIgnoreCase("C6"))
            {
            	String op1=hex[f+1]; 
            	setACFLAG(A,op1);
            	add8bit(A,op1);
            	f=f+1;
            }
		//aci
            else if(hex[f].equalsIgnoreCase("CE"))
            {
            	String op1=hex[f+1]; 
            	adc8bit(A,op1);
            	f=f+1;
            }
	
		//add r
            else if(hex[f].equalsIgnoreCase("87"))
            {//add a
            	setACFLAG(A, A);
            	add8bit(A, A);
            }
            else if(hex[f].equalsIgnoreCase("80"))
            {//add b
            	setACFLAG(A, B);
            	add8bit(A, B);
            }
            else if(hex[f].equalsIgnoreCase("81"))
            {//add c
            	setACFLAG(A, C);
            	add8bit(A, C);
            }
            else if(hex[f].equalsIgnoreCase("82"))
            {//add d
            	setACFLAG(A, D);
            	add8bit(A, D);
            }
            else if(hex[f].equalsIgnoreCase("83"))
            {//add e
            	setACFLAG(A, E);
            	add8bit(A, E);
            }
            else if(hex[f].equalsIgnoreCase("84"))
            {//add h
            	setACFLAG(A, H);
            	add8bit(A, H);
            }
            else if(hex[f].equalsIgnoreCase("85"))
            {//add l
            	setACFLAG(A, L);
            	add8bit(A, L);
            }
		//add m
            else if(hex[f].equalsIgnoreCase("86"))
            {//add m
            	String addm=ValAtLoc(loc());
            	setACFLAG(A, addm);
            	add8bit(A, addm);
            }
		//end of add r0000 a
	//adc star
            else if(hex[f].equalsIgnoreCase("8F"))
            {//adc r
            	adc8bit(A,A);
            }
            else if(hex[f].equalsIgnoreCase("88"))
            {//adc r
            	adc8bit(A,B);
            }
            else if(hex[f].equalsIgnoreCase("89"))
            {//adc r
            	adc8bit(A,C);
            }
            else if(hex[f].equalsIgnoreCase("8A"))
            {//adc r
            	adc8bit(A,D);
            }
            else if(hex[f].equalsIgnoreCase("8B"))
            {//adc r
            	adc8bit(A,E);
            }
            else if(hex[f].equalsIgnoreCase("8C"))
            {//adc r
            	adc8bit(A,H);
            }
            else if(hex[f].equalsIgnoreCase("8D"))
            {//adc r
            	adc8bit(A,L);
            }
            else if(hex[f].equalsIgnoreCase("8E"))
            {//adc m
            	String addm=ValAtLoc(loc());
            	adc8bit(A, addm);
            }
		//endofadc fuck
            else if(hex[f].equalsIgnoreCase("A7"))
            {//ana b
            	logicalAnd(A,A);
            }
            else if(hex[f].equalsIgnoreCase("A0"))
            {//ana b
            	logicalAnd(A,B);
            }
            else if(hex[f].equalsIgnoreCase("A1"))
            {//ana b
            	logicalAnd(A,C);
            }
            else if(hex[f].equalsIgnoreCase("A2"))
            {//ana b
            	logicalAnd(A,D);
            }
            else if(hex[f].equalsIgnoreCase("A3"))
            {//ana b
            	logicalAnd(A,E);
            }
            else if(hex[f].equalsIgnoreCase("A4"))
            {//ana b
            	logicalAnd(A,H);
            }
            else if(hex[f].equalsIgnoreCase("A5"))
            {//ana b
            	logicalAnd(A,L);
            }
            else if(hex[f].equalsIgnoreCase("A6"))
            {//anc m
            	String valm=ValAtLoc(loc());
            	logicalAnd(A, valm);
            }
		//ANI
            else if(hex[f].equalsIgnoreCase("E6"))
            {//ani 8            	
            	String imval=hex[f+1];
            	logicalAnd(A, imval);
            	f=f+1;
            }
            else if(hex[f].equalsIgnoreCase("27"))
            {//daa
            	DAA();
            }
		
		//DAD Rp//DAD sp oomb-probs
            else if(hex[f].equals("09"))
            {
            	dad(B,C);
            }
            else if(hex[f].equals("19"))
            {
            	dad(D,E);
            }
            else if(hex[f].equals("29"))
            {
            	dad(H,L);
            }
			else if (hex[f].equals("39"))
			{	SP=make4b(SP);
			String sp1=SP.substring(0,2),sp2=SP.substring(2);
				
				dad(sp1,sp2);//incomplete Stackptr-
			}
		//DCR
			else if(hex[f].equalsIgnoreCase("3D"))//dcr r
			{
				iA=dcr(A,0);
				A=Integer.toHexString(iA);
				A=make2b(A);
				aTextField.setText(A);
			}
			else if(hex[f].equalsIgnoreCase("05"))//dcr r
			{
				iB=dcr(B,1);
				B=Integer.toHexString(iB);
				B=make2b(B);
				bTextField.setText(B);
			}
			else if(hex[f].equalsIgnoreCase("0D"))//dcr r
			{
				iC=dcr(C,2);
				C=Integer.toHexString(iC);
				C=make2b(C);
				cTextField.setText(C);
			}
			else if(hex[f].equalsIgnoreCase("15"))//dcr r
			{
				iD=dcr(D,3);
				D=Integer.toHexString(iD);
				D=make2b(D);
				dTextField.setText(D);
			}
			else if(hex[f].equalsIgnoreCase("1D"))//dcr r
			{
				iE=dcr(E,4);
				E=Integer.toHexString(iE);
				E=make2b(E);
				eTextField.setText(E);
			}
			else if(hex[f].equalsIgnoreCase("25"))//dcr r
			{
				iH=dcr(H,5);
				H=Integer.toHexString(iH);
				H=make2b(H);
				hTextField.setText(H);
			}
			else if(hex[f].equalsIgnoreCase("2D"))//dcr r
			{
				iL=dcr(L,6);
				L=Integer.toHexString(iL);
				L=make2b(L);
				lTextField.setText(L);
			}
			else if(hex[f].equalsIgnoreCase("35"))//dcr M
			{
				String srcm=ValAtLoc(loc());
				int isrcm=dcr(srcm,-1);
				srcm=Integer.toHexString(isrcm);
				srcm=make2b(srcm);
				memorytable.getModel().setValueAt(srcm,loc()-16384,1);
				
			}//end of dcr
		
		//DCX
			else if(hex[f].equalsIgnoreCase("0B"))//dcx 
			{
				String bc=dcx(B,C,1);
				B=bc.substring(0,2);
				C=bc.substring(2);
				iB=Integer.valueOf(B,16);
				iC=Integer.valueOf(C,16);
				bTextField.setText(B);
				cTextField.setText(C);
			}	
			else if(hex[f].equalsIgnoreCase("1B"))//dcx 
			{
				String bc=dcx(D,E,3);
				D=bc.substring(0,2);
				E=bc.substring(2);
				iD=Integer.valueOf(D,16);
				iE=Integer.valueOf(E,16);
				dTextField.setText(D);
				eTextField.setText(E);
			}
			else if(hex[f].equalsIgnoreCase("2B"))//dcx 
			{
				String bc=dcx(H,L,5);
				H=bc.substring(0,2);
				L=bc.substring(2);
				iH=Integer.valueOf(H,16);
				iL=Integer.valueOf(L,16);
				hTextField.setText(H);
				lTextField.setText(L);
			}
			else if(hex[f].equalsIgnoreCase("3B"))//dcx SP
			{
				SP=dcx(SP,"",-1);
				iSP=Integer.valueOf(SP,16);
				spTextField.setText(SP);
			}
		//end dsx
		//INR
			else if(hex[f].equalsIgnoreCase("3C"))//dcr r
			{
				iA=inr(A,0);
				A=Integer.toHexString(iA);
				A=make2b(A);
				aTextField.setText(A);
			}
			else if(hex[f].equalsIgnoreCase("04"))//inr r
			{
				iB=inr(B,1);
				B=Integer.toHexString(iB);
				B=make2b(B);
				bTextField.setText(B);
			}
			else if(hex[f].equalsIgnoreCase("0C"))//inr r
			{
				iC=inr(C,2);
				C=Integer.toHexString(iC);
				C=make2b(C);
				cTextField.setText(C);
			}
			else if(hex[f].equalsIgnoreCase("14"))//inr r
			{
				iD=inr(D,3);
				D=Integer.toHexString(iD);
				D=make2b(D);
				dTextField.setText(D);
			}
			else if(hex[f].equalsIgnoreCase("1C"))//inr r
			{
				iE=inr(E,4);
				E=Integer.toHexString(iE);
				E=make2b(E);
				eTextField.setText(E);
			}
			else if(hex[f].equalsIgnoreCase("24"))//inr r
			{
				iH=inr(H,5);
				H=Integer.toHexString(iH);
				H=make2b(H);
				hTextField.setText(H);
			}
			else if(hex[f].equalsIgnoreCase("2C"))//inr r
			{
				iL=inr(L,6);
				L=Integer.toHexString(iL);
				L=make2b(L);
				lTextField.setText(L);
			}
			else if(hex[f].equalsIgnoreCase("34"))//inr M
			{
				String srcm=ValAtLoc(loc());
				int isrcm=inr(srcm,-1);
				srcm=Integer.toHexString(isrcm);
				srcm=make2b(srcm);
				memorytable.getModel().setValueAt(srcm,loc()-16384,1);
				
			}//end of inr

		//INX RP
		
			else if(hex[f].equalsIgnoreCase("03"))//inx 
			{
				String bc=inx(B,C,1);
				B=bc.substring(0,2);
				C=bc.substring(2);
				iB=Integer.valueOf(B,16);
				iC=Integer.valueOf(C,16);
				bTextField.setText(B);
				cTextField.setText(C);
			}	
			else if(hex[f].equalsIgnoreCase("13"))//inx 
			{
				String bc=inx(D,E,3);
				D=bc.substring(0,2);
				E=bc.substring(2);
				iD=Integer.valueOf(D,16);
				iE=Integer.valueOf(E,16);
				dTextField.setText(D);
				eTextField.setText(E);
			}
			else if(hex[f].equalsIgnoreCase("23"))//inx 
			{
				String bc=inx(H,L,5);
				H=bc.substring(0,2);
				L=bc.substring(2);
				iH=Integer.valueOf(H,16);
				iL=Integer.valueOf(L,16);
				hTextField.setText(H);
				lTextField.setText(L);
			}
			else if(hex[f].equalsIgnoreCase("33"))//inx SP
			{
				SP=inx(SP,"",-1);
				iSP=Integer.valueOf(SP,16);
				spTextField.setText(SP);
			}
		
		//end INX RP
		//SUB r
			else if(hex[f].equalsIgnoreCase("97"))
			{
				sub(iA);
			}
			else if(hex[f].equalsIgnoreCase("90"))
			{
				sub(iB);
			}
			else if(hex[f].equalsIgnoreCase("91"))
			{
				sub(iC);
			}
			else if(hex[f].equalsIgnoreCase("92"))
			{
				sub(iD);
			}
			else if(hex[f].equalsIgnoreCase("93"))
			{
				sub(iE);
			}
			else if(hex[f].equalsIgnoreCase("94"))
			{
				sub(iH);
			}
			else if(hex[f].equalsIgnoreCase("95"))
			{
				sub(iL);
			}
			//sub m
			else if(hex[f].equalsIgnoreCase("96"))
			{	
				
				String sm=ValAtLoc(loc());
				System.out.println("sm-"+sm);
				sub(Integer.valueOf(sm, 16));
			}
		//SUI Im
		    else if(hex[f].equalsIgnoreCase("D6"))
            {
            	String op1=hex[f+1];
            	int op1i=Integer.valueOf(op1, 16);
            	//setACFLAG(A,op1);
            	sub(op1i);
            	f=f+1;
            }
		//SBB R
		    else if(hex[f].equalsIgnoreCase("9F"))
		    {
		    	sub(cyf);
		    	sub(iA);
		   
		    }
		    else if(hex[f].equalsIgnoreCase("98"))
		    {
		    	sub(cyf);
		    	sub(iB);
		    
		    }
		    else if(hex[f].equalsIgnoreCase("99"))
		    {
		    	sub(cyf);
		    	sub(iC);
		  
		    }
		    else if(hex[f].equalsIgnoreCase("9A"))
		    {sub(cyf);
		    sub(iD);
		    }
		    else if(hex[f].equalsIgnoreCase("9B"))
		    {sub(cyf);
		    sub(iE);
		    }
		    else if(hex[f].equalsIgnoreCase("9C"))
		    {sub(cyf);
		    sub(iH);
		    }
		    else if(hex[f].equalsIgnoreCase("9D"))
		    {sub(cyf);
		    sub(iL);
		    }
		    else if(hex[f].equalsIgnoreCase("9E"))
		    {sub(cyf);
		    int su=Integer.parseInt(ValAtLoc(loc()), 16);
		    sub(su);
		    }
		
		//end of sbbr,sbbm
		//SBI
		    else if(hex[f].equalsIgnoreCase("DE"))
            {
            	String op1=hex[f+1];
            	sub(cyf);
            	int op1i=Integer.valueOf(op1, 16);
            	//setACFLAG(A,op1);
            	sub(op1i);
            	f=f+1;
            }
		//eosbi
		//ORA
		    else if(hex[f].equalsIgnoreCase("B7"))
		    {
		    	ora(iA);
		    }
		    else if(hex[f].equalsIgnoreCase("B0"))
		    {
		    	ora(iB);
		    }
		    else if(hex[f].equalsIgnoreCase("B1"))
		    {
		    	ora(iC);
		    }
		    else if(hex[f].equalsIgnoreCase("B2"))
		    {
		    	ora(iD);
		    }
		    else if(hex[f].equalsIgnoreCase("B3"))
		    {
		    	ora(iE);
		    }
		    else if(hex[f].equalsIgnoreCase("B4"))
		    {
		    	ora(iH);
		    }
		    else if(hex[f].equalsIgnoreCase("B5"))
		    {
		    	ora(iL);
		    }
		    else if(hex[f].equalsIgnoreCase("B6"))
		    {	
		    	int orai=Integer.valueOf(ValAtLoc(loc()),16);
		    	ora(orai);
		    }
		//end of ora
		//ori
		    else if(hex[f].equalsIgnoreCase("F6"))
            {
            	String op1=hex[f+1];
            	int op1i=Integer.valueOf(op1, 16);
            	ora(op1i);
            	f=f+1;
            }
		//ori end
		//xra
		    else if(hex[f].equalsIgnoreCase("AF"))
		    {
		    	xra(iA);
		    }
		    else if(hex[f].equalsIgnoreCase("A8"))
		    {
		    	xra(iB);
		    }
		    else if(hex[f].equalsIgnoreCase("A9"))
		    {
		    	xra(iC);
		    }
		    else if(hex[f].equalsIgnoreCase("AA"))
		    {
		    	xra(iD);
		    }
		    else if(hex[f].equalsIgnoreCase("AB"))
		    {
		    	xra(iE);
		    }
		    else if(hex[f].equalsIgnoreCase("AC"))
		    {
		    	xra(iH);
		    }
		    else if(hex[f].equalsIgnoreCase("AD"))
		    {
		    	xra(iL);
		    }
		    else if(hex[f].equalsIgnoreCase("AE"))
		    {	
		    	int xrai=Integer.valueOf(ValAtLoc(loc()),16);
		    	xra(xrai);
		    }
		//end of xra
		//xri
		    else if(hex[f].equalsIgnoreCase("EE"))
            {
            	String op1=hex[f+1];
            	int op1i=Integer.valueOf(op1, 16);
            	xra(op1i);
            	f=f+1;
            }
		//xri end
		//From ellipro
		//cma
		    else if(hex[f].equalsIgnoreCase("2F"))
            {
		    	iA=255-iA;
				A=Integer.toHexString(iA);
            	A=make2b(A);
            	aTextField.setText(A);
            	tobin(iA, 0);
            }
		
		//cma ebd
		//rrc
		    else if(hex[f].equalsIgnoreCase("0F"))  ///rrc
			{
				instrbyte=0;
				int q=iA%2;
				cyf=q;
				iA=iA>>1;
		        iA=iA+(q<<7);
		        A=make2b(Integer.toHexString(iA));
		        aTextField.setText(A);
		        tobin(iA, 0);
		        setFLAG();
		
			}
		//end rrc,strt rlc
		    else if(hex[f].equalsIgnoreCase("07"))  ///rlc
			{
				instrbyte=0;
				int q=iA/128;
				cyf=q;
				iA=iA<<1;
				iA=iA%256;
		        iA=iA+q;
		        A=make2b(Integer.toHexString(iA));
		        aTextField.setText(A);
		        tobin(iA,0);
		        setFLAG();
			}//end rlc
/////////////CMP START
		    else if(hex[f].equalsIgnoreCase("BF"))//cmp a
		    {cmp(iA);}
		    else if(hex[f].equalsIgnoreCase("B8"))//cmp b
			{cmp(iB);}
		    else if(hex[f].equalsIgnoreCase("B9"))//cmp c
			{cmp(iC);}
		    else if(hex[f].equalsIgnoreCase("BA"))//cmp d
			{cmp(iD);}
		    else if(hex[f].equalsIgnoreCase("BB"))//cmp e
			{cmp(iE);}
		    else if(hex[f].equalsIgnoreCase("BC"))//cmp h
			{cmp(iH);}
		    else if(hex[f].equalsIgnoreCase("BD"))//cmp l
			{cmp(iL);}
		    else if(hex[f].equalsIgnoreCase("BE"))//cmp m
			{cmp(Integer.valueOf(ValAtLoc(loc()),16).intValue());}
		   //cpi 8bit
		    else if(hex[f].equalsIgnoreCase("FE"))         //cpi 8bit
			{cmp(Integer.valueOf(hex[f+1],16).intValue());f=f+1;}
			
		  //rameez jmp
		    else if(hex[f].equalsIgnoreCase("C3"))
            {jmp();}
		    else if(hex[f].equalsIgnoreCase("DA"))//jc
            {
		    	if(cyf==1)
		    		jmp();
		    	else
		    		f=f+2;
		    }  
		    else if(hex[f].equalsIgnoreCase("D2"))//jnc
            {
		    	if(cyf==0)
		    		jmp();
		    	else
		    		f=f+2;
		    }
		    else if(hex[f].equalsIgnoreCase("F2"))//jp
            {
		    	if(sf==0)
		    		jmp();
		    	else
		    		f=f+2;
		    }  
		    else if(hex[f].equalsIgnoreCase("FA"))//jm
            {
		    	if(sf==1)
		    		jmp();
		    	else
		    		f=f+2;
		    }
		    else if(hex[f].equalsIgnoreCase("EA"))//jpe
            {
		    	if(pf==1)
		    		jmp();
		    	else
		    		f=f+2;
		    }
		    else if(hex[f].equalsIgnoreCase("E2"))//jpo
            {
		    	if(pf==0)
		    		jmp();
		    	else
		    		f=f+2;
		    }
		
		    else if(hex[f].equalsIgnoreCase("CA"))//jz
            {
		    	if(zf==1)
		    		jmp();
		    	else
		    		f=f+2;
		    }
		    else if(hex[f].equalsIgnoreCase("C2"))//jnz
            {
		    	if(zf==0)
		    		jmp();
		    	else
		    		f=f+2;
		    }
		//eojmp
		//stc
		    else if(hex[f].equalsIgnoreCase("37"))
            {
		    cyf=1;
		    setFLAG();
		    }
		
		//end ellipro

		if(hex[f].equalsIgnoreCase("CD"))      /////////CALLL
		{
			fcall();
			/*instrbyte=3;
			String temp=hex[f+2]+hex[f+1];
			int tem=Integer.valueOf(temp,16).intValue()-16384;
			caladdr[calno]=f+3;
			calno++;
			f=tem;
			f--;*/
		}
		if(hex[f].equalsIgnoreCase("DC"))      /////////cc
		{	if(cyf==1)
			fcall();
			else
			f=f+2;
		}
		if(hex[f].equalsIgnoreCase("D4"))      /////////cnc
		{	if(cyf==0)
			fcall();
			else
			f=f+2;
		}
		if(hex[f].equalsIgnoreCase("F4"))      /////////cnc
		{	if(sf==0)
			fcall();
			else
			f=f+2;
		}
		if(hex[f].equalsIgnoreCase("FC"))      /////////cnc
		{	if(sf==1)
			fcall();
			else
			f=f+2;
		}
		if(hex[f].equalsIgnoreCase("EC"))      /////////cnc
		{	if(pf==1)
			fcall();
			else
			f=f+2;
		}
		if(hex[f].equalsIgnoreCase("E4"))      /////////cnc
		{	if(pf==0)
			fcall();
			else
			f=f+2;
		}
		if(hex[f].equalsIgnoreCase("CC"))      /////////cnc
		{	if(zf==1)
			fcall();
			else
			f=f+2;
		}
		if(hex[f].equalsIgnoreCase("C4"))      /////////cnc
		{	if(zf==0)
			fcall();
			else
			f=f+2;
		}
		//cmc
		if(hex[f].equalsIgnoreCase("3F"))      /////////cnc
		{	if(cyf==1)
			cyf=0;
			else
			cyf=1;
		}
		
		else if(hex[f].equalsIgnoreCase("C9"))       //////////RETTT
		{
			fret();
			/*instrbyte=1;
			calno--;
			f=caladdr[calno];
			f--;*/
		}
		else if(hex[f].equalsIgnoreCase("D8"))       //////////RETTT
		{	if(cyf==1)
			fret();
		}
		else if(hex[f].equalsIgnoreCase("D0"))       //////////RETTT
		{	if(cyf==0)
			fret();
		}
		else if(hex[f].equalsIgnoreCase("F0"))       //////////RETTT
		{	if(sf==0)
			fret();
		}
		else if(hex[f].equalsIgnoreCase("F8"))       //////////RETTT
		{	if(sf==1)
			fret();
		}
		else if(hex[f].equalsIgnoreCase("E8"))       //////////RETTT
		{	if(pf==1)
			fret();
		}
		else if(hex[f].equalsIgnoreCase("E0"))       //////////RETTT
		{	if(pf==0)
			fret();
		}
		else if(hex[f].equalsIgnoreCase("C8"))       //////////RETTT
		{	if(zf==1)
			fret();
		}
		else if(hex[f].equalsIgnoreCase("C0"))       //////////RETTT
		{	if(zf==0)
			fret();
		}
		
		else if(hex[f].equalsIgnoreCase("E9"))       /////////PCHL
		{
			
			iPC=Integer.valueOf((H+L),16);
			PC=make2b(H)+make2b(L);
			pcTextField.setText(PC);
			int test1=iPC-16384;
			f=test1-1;
		}
		//ramex RLC
		else if(hex[f].equalsIgnoreCase("07"))  ///rlc
		{
			instrbyte=0;
			int q=iA/128;
			cyf=q;
			setFLAG();
			iA=iA<<1;
			iA=iA%256;
	        iA=iA+q;
	        A=Integer.toHexString(iA);
	        aTextField.setText(A);
	        tobin(iA,0);
	
		}
		//ral
		else if(hex[f].equalsIgnoreCase("17"))  ///ral
		{
			instrbyte=0;
			int q=iA/128;
			int tem=cyf;
			cyf=q;
			setFLAG();
			iA=iA<<1;
			iA=iA%256;
	        iA=iA+tem;
	        A=Integer.toHexString(iA);
	        aTextField.setText(A);
	        tobin(iA,0);
	
		}
		//nop
		else if(hex[f].equalsIgnoreCase("00"))  ///ral
		{
			//nop oombikkooooooooooooooooooo
		}
		
		
		//IN 
		
		else if(hex[f].equalsIgnoreCase("DB"))
		{
			int iloc;
			instrbyte=2;
			String loc=hex[f+1];
			iloc=Integer.valueOf(loc,16).intValue();
			Object ival=iotable.getModel().getValueAt((iloc),1);
			String val=ival.toString();
			System.out.println("------------------------------"+val);
			A=val;
			iA=Integer.valueOf(A,16).intValue();
			tobin(iA, 0);
			aTextField.setText(A);
		}
		
		
		else if(hex[f].equalsIgnoreCase("D3"))
		{
			int iloc;
			instrbyte=2;
			String loc=hex[f+1];
			iloc=Integer.valueOf(loc,16).intValue();
			iotable.getModel().setValueAt(A,iloc,1);
		}
		
		
		

		
		
	//}//end main for
}catch(NullPointerException npe)
{
	//System.out.print(npe.toString());
}
}

public void fret()
{
	calno--;
	f=caladdr[calno];
	f--;
}
public void fcall()
{

	String temp=hex[f+2]+hex[f+1];
	int tem=Integer.valueOf(temp,16).intValue()-16384;
	caladdr[calno]=f+3;
	calno++;
	f=tem;
	f--;
}


public void jmp()
{
	String op1=hex[f+1];
    String op2=hex[f+2];
    String tem=op2+op1;
    f=Integer.valueOf(tem, 16).intValue()-16384;
    f--;
}
public void cmp(int R)
{
		if(iA<R)
		{	cyf=1;zf=0;}
		else if(iA==R)
		{cyf=0;zf=1;}
		else
		{cyf=0;zf=0;}
		setFLAG();
}

void xra(int r)
{
	System.out.println("started xra");
	//int a1=Integer.valueOf(a, 16),x1=Integer.valueOf(x, 16);
	char ca1[]=make8bit(iA);
	char cx1[]=make8bit(r);
	char lres[] ={'0','0','0','0','0','0','0','0'};
	for(int lai=0;lai<8;lai++)
	{
		if((ca1[lai]=='1' && cx1[lai]=='0')||(ca1[lai]=='0' && cx1[lai]=='1')) 
		{lres[lai]='1'; }
		else 
		{ lres[lai]='0';}
	}
	String tem=String.valueOf(lres);
	iA=Integer.parseInt(tem, 2);
	A=make2b(Integer.toHexString(iA));
	aTextField.setText(A);
	tobin(iA,0);
	cyf=0;acf=0;
	zf=zero(iA);
	pf=parity(A);	
	sf=lres[0];
	setFLAG();
	
}
	

void ora(int r)
{
	System.out.println("started ORA");
	//int a1=Integer.valueOf(a, 16),x1=Integer.valueOf(x, 16);
	char ca1[]=make8bit(iA);
	char cx1[]=make8bit(r);
	char lres[] ={'0','0','0','0','0','0','0','0'};
	for(int lai=0;lai<8;lai++)
	{
		if(ca1[lai]=='1' || cx1[lai]=='1') 
		{lres[lai]='1'; }
		else 
		{ lres[lai]='0';}
	}
	String tem=String.valueOf(lres);
	iA=Integer.parseInt(tem, 2);
	A=make2b(Integer.toHexString(iA));
	aTextField.setText(A);
	tobin(iA,0);
	cyf=0;acf=0;
	zf=zero(iA);
	pf=parity(A);	
	sf=lres[0];
	setFLAG();
	
}



void sub(int r)
{	String hexr="";
	int res=iA-r;
	if(res<0)
	{	res=256+res;
		hexr=make2b(Integer.toHexString(res));
		cyf=1;
	}
	else
	{
		hexr=make2b(Integer.toHexString(res));
		cyf=0;
	}
	aTextField.setText(make2b(hexr));
	A=make2b(hexr);
	iA=Integer.valueOf(hexr, 16);
	zf=zero(res);
	pf=parity(hexr);
	char ss[]=make8bit(Integer.valueOf(hexr, 16));
	sf=Integer.parseInt(String.valueOf(ss[0]));
	setFLAG();
	tobin(iA, 0);
}

String make4b(String a2)
{
	while(a2.length()<4)
		a2="0"+a2;
	return a2;
}
private String make2b(String a2) {
	// TODO Auto-generated method stub
	while(a2.length()<2)
		a2="0"+a2;
	return a2;
}
//end run
//for
//inx
String inx(String r1,String r2,int rowindex)
{
	String a1=r1+r2;
	int a2=Integer.valueOf(a1, 16);
	a2=a2+1;
	if(a2>65535) {a2=0;}
	a1=Integer.toHexString(a2);
	a1=make4b(a1);
	if(rowindex>0){
	tobin(Integer.valueOf(a1.substring(0, 2),16),rowindex);
	tobin(Integer.valueOf(a1.substring(2),16),rowindex+1);
	}return a1;
}
//dcx
String dcx(String r1,String r2, int rowindex)
{
	String a1=r1+r2;
	int a2=Integer.valueOf(a1, 16);
	a2=a2-1;
	if(a2<0) {a2=65535;}
	a1=Integer.toHexString(a2);
	a1=make4b(a1);
	if(rowindex>0){
	tobin(Integer.valueOf(a1.substring(0, 2),16),rowindex);
	tobin(Integer.valueOf(a1.substring(2),16),rowindex+1);
	}return a1;
}

int inr(String r,int regindex)
{
	setACFLAG(r, "01");
	int src=Integer.valueOf(r,16);
	src=src+1;
	if(src>255)
	{
		src=0;
	}
	
	zf=zero(src);
	pf=parity(Integer.toHexString(src));
	char ss[];
	ss=make8bit(src);
	sf=Integer.parseInt(String.valueOf(ss[0]));
	
	setFLAG();
	if(regindex>=0)
	tobin(src, regindex);
	return src;
}

int dcr(String r,int regindex)
{
	acf=0;
	int src=Integer.valueOf(r,16);
	src=src-1;
	if(src<0)
	{
		src=255;
	}
	
	zf=zero(src);
	pf=parity(Integer.toHexString(src));
	char ss[];
	ss=make8bit(src);
	sf=Integer.parseInt(String.valueOf(ss[0]));
	
	setFLAG();
	if(regindex>=0)
	tobin(src, regindex);
	return src;
}



//for DAD
void dad(String h,String l)
{
	setACFLAG(l,L);
	L=add8HL(l, L);
	setACFLAG(H, String.valueOf(cyf));
	H=add8HL(H,String.valueOf(cyf));
	
	setACFLAG(h,H);
	H=add8HL(h, H);
	
	iH=Integer.valueOf(H, 16);
	iL=Integer.valueOf(L, 16);
	hTextField.setText(H);
	lTextField.setText(L);
	int res=Integer.valueOf(H, 16);char ss[];
	ss=make8bit(res);
	sf=Integer.parseInt(String.valueOf(ss[0]));
	setFLAG();
	tobin(iH,5);//tobin and set reg tabs
	tobin(iL,6);
	
}
public String add8HL(String a,String x)
{
	int a1=Integer.valueOf(a, 16),x1=Integer.valueOf(x, 16);
	int res=a1+x1;
	String hexresult;
	if(res>255)
	{cyf=1;
	 hexresult=Integer.toHexString(res).substring(1, 3);
	}
	else
	{cyf=0;
	
	hexresult=Integer.toHexString(res);
	if(hexresult.length()<2){hexresult='0'+hexresult;}
	}
	zf=zero(res);
	pf=parity(hexresult);
	return hexresult;
	
	/*A=hexresult;
	iA=Integer.valueOf(A,16);
	aTextField.setText(A);
	tobin(iA, 0);
	System.out.println("inva"+iA);
	
	res=Integer.valueOf(hexresult, 16);char ss[];
	ss=make8bit(res);
	sf=Integer.parseInt(String.valueOf(ss[0]));
	setFLAG();
	
	System.out.println("parity="+pf+"\nzero="+zf);
	*/
	
}







//DAA incomplete
public void DAA()
{	int daacary=0,daaf=0;
	
	A=make2b(A);
	int high=Integer.valueOf(A.substring(0,1),16).intValue();
	int low=Integer.valueOf(A.substring(1),16).intValue();
	String lw =Integer.toBinaryString(low),hi =Integer.toBinaryString(high),daares;
	
	if(low>9||acf==1)
	{	daaf=1;
		low=low+6;
		if(low>15)
		{	lw=Integer.toBinaryString(low).substring(1);
			high+=1;
			if(high>15) cyf=1;
			hi =Integer.toBinaryString(high);
			acf=1;
		}
		else{lw=Integer.toBinaryString(low);}
	}	
	if(high>9||cyf==1)
	{daaf=1;
		high=high+6;
		if(high>15)
		{
			hi=Integer.toBinaryString(high).substring(1);
			cyf=1;
		}
		else
		{hi=Integer.toBinaryString(high);}
	
	}
	if(daaf==5)
	{
		return;
	}
	
	while(hi.length()<4)
	{hi='0'+hi;}
	while(lw.length()<4)
	{lw='0'+lw;}
	
	daares=hi+lw;
	iA=Integer.parseInt(daares, 2);
	
	A=make2b(Integer.toHexString(iA));
	
	aTextField.setText(A);
	tobin(iA, 0);
	char sft[]=make8bit(iA);
	pf=parity(A);
	sf=Integer.parseInt(String.valueOf(sft[0]));
	setFLAG();
}



//ANA R
public void logicalAnd(String a,String x)
{	System.out.println("started ana");
	int a1=Integer.valueOf(a, 16),x1=Integer.valueOf(x, 16);
	char ca1[]=make8bit(a1);
	char cx1[]=make8bit(x1);
	char lres[] ={'0','0','0','0','0','0','0','0'};
	for(int lai=0;lai<8;lai++)
	{
		if(ca1[lai]=='1' && cx1[lai]=='1') 
		{lres[lai]='1'; }
		else 
		{ lres[lai]='0';}
	}
	String tem=String.valueOf(lres);
	iA=Integer.parseInt(tem, 2);
	A=Integer.toHexString(iA);
	if(A.length()<2){A='0'+A;}
	aTextField.setText(A);
	tobin(iA,0);
	//flag
	cyf=0;
	acf=1;
	pf=parity(tem);
	sf=Integer.parseInt(String.valueOf(lres[0]));
	if(iA==0){zf=1;}
	else{zf=0;}
	setFLAG();
	
	
	
	System.out.println("asshole  "+iA+"  \n hol "+A);
	//int kkk=Integer.parseInt(String.valueOf(ca1));
}

public void setFLAG()
{
	
	flagTable.setValueAt(sf,0, 1);
	flagTable.setValueAt(zf,0, 2);
	flagTable.setValueAt(acf,0, 4);
	flagTable.setValueAt(pf,0, 6);
	flagTable.setValueAt(cyf,0, 8);
}


// 
public char[] make8bit(int ivalue)
{
	int ar1i=7;
	char ar[];
	char ar1[]={'0','0','0','0','0','0','0','0'};
	ar=Integer.toBinaryString(ivalue).toCharArray();
	
	for(int ari=ar.length-1;ari>=0;ari--)
	{ar1[ar1i]=ar[ari];
	if(ar1i==0)
	{break;}
	ar1i--;
	}
	return ar1;
}


public void setACFLAG(String a,String x)
{	//String a="28",x="08";
	String a1=a.substring(a.length()-1),x1=x.substring(x.length()-1);
	
	int ah1=Integer.valueOf(a1,16),xh1=Integer.valueOf(x1,16);
	int res=ah1+xh1;
	if(res>15)
	{//set ac
		acf=1;
	}
	else
	{
		acf=0;
	}
	System.out.println("accarry"+acf);
	
}


//adc
public void adc8bit(String a,String x)
{	String scyf=Integer.toString(cyf);
	System.out.println("cyf= "+scyf);
	setACFLAG(a, scyf);
	add8bit(a, scyf);
	setACFLAG(A, x);
	add8bit(A, x);	
}

//end adc
public void add8bit(String a,String x)
{
	int a1=Integer.valueOf(a, 16),x1=Integer.valueOf(x, 16);
	int res=a1+x1;
	String hexresult;
	if(res>255)
	{cyf=1;
	 hexresult=Integer.toHexString(res).substring(1, 3);
	}
	else
	{cyf=0;
	
	hexresult=Integer.toHexString(res);
	if(hexresult.length()<2){hexresult='0'+hexresult;}
	}
	zf=zero(res);
	pf=parity(hexresult);
	
	
	A=make2b(hexresult);
	iA=Integer.valueOf(A,16);
	aTextField.setText(A);
	tobin(iA, 0);
	System.out.println("inva"+iA);
	
	res=Integer.valueOf(hexresult, 16);char ss[];
	ss=make8bit(res);
	sf=Integer.parseInt(String.valueOf(ss[0]));
	setFLAG();
	
	System.out.println("parity="+pf+"\nzero="+zf);
}

//parity check
public int parity(String r)
{
	int par=0,num=Integer.valueOf(r, 16);
	
	char parc[]=Integer.toBinaryString(num).toCharArray();
	for(int pa=0;pa<parc.length;pa++)
	{
	if(parc[pa]=='1')
	{
		par++;
		System.out.println("fcjjjj");
	}
	}
	if((par%2)==0)
	{
		return 1;
	}
	else
	{
	
	return 0;

	}
}
//zero flg chk
public int zero(int r)
{
	if(r==0)
	{
		return 1;
	}
	else
	{
		return 0;
	}	
}
//tobit..protsetresgs....set regtablefc
public void tobin(int ivalue,int rowindex)
{	
	int ar1i=7,i8;
	char ar[];
	Object ar1[]={'0','0','0','0','0','0','0','0'};
	ar=Integer.toBinaryString(ivalue).toCharArray();
	
	for(int ari=ar.length-1;ari>=0;ari--)
	{ar1[ar1i]=ar[ari];
	if(ar1i==0)
	{break;}
	ar1i--;
	}
	for(i8=0;i8<8;i8++)
	registerTable.setValueAt(ar1[i8],rowindex, i8+1);
	System.out.println(ar);
	
}




public int loc()//need modification
{	
	String t1=make2b(Integer.toHexString(iH))+make2b(Integer.toHexString(iL));
	return(Integer.valueOf(t1, 16).intValue());
	
}


public String ValAtLoc(int loc)
{
	Object val=memorytable.getModel().getValueAt((loc-16384), 1);
	//bound check.
	boolean valchar=true;
	char singlechar[]=val.toString().toUpperCase().toCharArray();
	if(singlechar.length>2){valchar=false;}
	else{
	for(int opl=0;opl<2;opl++)
	{	valchar=false;
		for(int chari=48;chari<71;chari++)
		{
			if(singlechar[opl]==((char)chari))
			{
				valchar=true;
			}
			
			
			if(chari==57)chari=64; 
		}
		if(!valchar)break;
	}}
	if(!valchar){consoleTextArea.append("invalid operand \n"); return "00";}
	
	else{return make2b(val.toString());}
}






    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * eTextFieldnerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    public void windowslook() {
		// TODO Auto-generated method stub
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			}catch(UnsupportedLookAndFeelException ex){}
			catch(ClassNotFoundException ex2){}
			catch (InstantiationException ex3) {
				System.err.println("Could not load LookAndFeel");
				}
				catch (IllegalAccessException ex4) {
				System.err.println("Cannot use LookAndFeel");
				}	
	}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    @SuppressWarnings("serial")
	private void initComponents() {
    	
    	
    	inputScrollPane = new javax.swing.JScrollPane();
        input = new javax.swing.JTextArea();
        outputScrollPane = new javax.swing.JScrollPane();
        opcode = new javax.swing.JTextArea();
        memoryScrollPane = new javax.swing.JScrollPane();
        memorytable = new javax.swing.JTable();
        ioScrollPane = new javax.swing.JScrollPane();
        iotable = new javax.swing.JTable();
        registerScrollPane = new javax.swing.JScrollPane();
        registerTable = new javax.swing.JTable();
        flagScrollPane = new javax.swing.JScrollPane();
        flagTable = new javax.swing.JTable();
        assemble = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        run = new javax.swing.JButton();
        resetm = new javax.swing.JButton();
        resetio = new javax.swing.JButton();
        resetr = new javax.swing.JButton();
        consoleScrollPane = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
        aTextField = new javax.swing.JTextField();
        dTextField = new javax.swing.JTextField();
        cTextField = new javax.swing.JTextField();
        bTextField = new javax.swing.JTextField();
        eTextField = new javax.swing.JTextField();
        hTextField = new javax.swing.JTextField();
        lTextField = new javax.swing.JTextField();
        spTextField = new javax.swing.JTextField();
        pcTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        searchScrollPane = new javax.swing.JScrollPane();
        searchresultTextArea = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        expg = new javax.swing.JMenuItem();
        hextool=new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        aboutus=new javax.swing.JMenuItem();
        simspeedms=new JMenuItem("1ms");
        simspeed10ms=new JMenuItem("10ms");
        simspeed1s=new JMenuItem("100ms");
        simspeed10s=new JMenuItem("1s");


        
        //sarch compi
        searchTextField = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        search.setText("Search");
        searchScrollPane = new javax.swing.JScrollPane();
        searchresultTextArea = new javax.swing.JTextArea();
        searchList=new javax.swing.JList(listmodel);
        
        searchScrollPane.setViewportView(searchList);
        
       //minuks
        image=new ImageIcon("DMR_120.png");
        pie=new ImageIcon("pie8.png");
        run.setIcon(image);
        
        
        //minuksend
        
        
        
        
        
        
        
        
        clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			input.setText(" ");
			opcode.setText(" ");
			
			}
		});
        
        
        
        
        
        
        
        
        
        
        setupConn();  
        listAll();
        
        search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				searchList.setVisible(true);
            	searchScrollPane.setViewportView(searchList);
            	listmodel.removeAllElements();
            	String key=searchTextField.getText();
          	listSome(key);
			}
		});
        
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyReleased(java.awt.event.KeyEvent evt) {
            	searchList.setVisible(true);
            	searchScrollPane.setViewportView(searchList);
            	listmodel.removeAllElements();
            	String key=searchTextField.getText();
            listSome(key);
            }
        });

        
        searchList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
           	//scroll.remove(list);
           
            	String key=searchList.getSelectedValue().toString();
            	
            	viewIns(key);
            	
            	searchList.setVisible(false);
                
            	searchScrollPane.setViewportView(searchresultTextArea);
            
            }
        });
        
        //by arj
        
        
        
        
        
        
        
        //button actiopn
        run.addActionListener(runbut);
        resetr.addActionListener(resetreg);
        resetm.addActionListener(resetmem);
        resetio.addActionListener(reseti);
        assemble.addActionListener(assm);
        jMenuItem1.addActionListener(open);
        //search.addActionListener(searchins);
        //
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Intel 8085 simulator IDE");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(1280, 720));

        inputScrollPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 3, true));
        inputScrollPane.setForeground(new java.awt.Color(255, 255, 255));
        inputScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        input.setColumns(20);
        input.setRows(5);
        input.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N
        inputScrollPane.setViewportView(input);

        outputScrollPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 3, true));
        outputScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        opcode.setColumns(20);
        opcode.setRows(5);
        opcode.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opcode", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N
        outputScrollPane.setViewportView(opcode);

        memoryScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 3, true), "Memory", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), java.awt.Color.black)); // NOI18N
        memoryScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        memorytable.setModel(new javax.swing.table.DefaultTableModel(
            mem,new String [] {"Location", "Value"}
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
       // memorytable.setPreferredSize(new java.awt.Dimension(150, 16384));
        memoryScrollPane.setViewportView(memorytable);

        ioScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 3, true), "IO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N

        iotable.setModel(new javax.swing.table.DefaultTableModel(
        		IO,new String [] {"Location", "Value"}
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        //iotable.setPreferredSize(new java.awt.Dimension(150, 400));
        ioScrollPane.setViewportView(iotable);

        registerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"     A",  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0)},
                {"     B",  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0)},
                {"     C",  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0)},
                {"     D",  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0)},
                {"     E",  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0)},
                {"     H",  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0)},
                {"     L",  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0),  new Integer(0), new Integer(0)}
            },
            new String [] {
                "Reg.", "7","6","5","4","3","2","1","0"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        registerScrollPane.setViewportView(registerTable);

        flagTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"FLAG", null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Reg", "S", "Z", "*", "AC", "*", "P", "*", "CY"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        flagScrollPane.setViewportView(flagTable);

        assemble.setText("Assemble");

        clear.setText("Clear");

        run.setText("Run");
        
          
        resetm.setText("Memory Reset");

        resetio.setText("I/O Reset");

        resetr.setText("Register Reset");

        consoleTextArea.setColumns(20);
        consoleTextArea.setEditable(false);
        consoleTextArea.setFont(new java.awt.Font("Monospaced", 2, 13)); // NOI18N
        consoleTextArea.setForeground(new java.awt.Color(255, 0, 0));
        consoleTextArea.setRows(5);
        consoleTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "console", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N
        consoleScrollPane.setViewportView(consoleTextArea);

        aTextField.setText("00");
        aTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aTextFieldActionPerformed(evt);
            }
        });

        dTextField.setText("00");
        dTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dTextFieldActionPerformed(evt);
            }
        });

        cTextField.setText("00");

        bTextField.setText("00");
        bTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTextFieldActionPerformed(evt);
            }
        });

        eTextField.setText("00");

        hTextField.setText("00");

        lTextField.setText("00");
        lTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lTextFieldActionPerformed(evt);
            }
        });

        spTextField.setText("0000");

        pcTextField.setText("0000");

        jLabel1.setText("A");

        jLabel2.setText("B");

        jLabel3.setText("C");

        jLabel4.setText("D");

        jLabel5.setText("E");

        jLabel6.setText("H");

        jLabel7.setText("L");

        jLabel8.setText("SP");

        jLabel9.setText("PC");

        jLabel10.setText("<html><b>HEX values</b><html>");

        search.setText("Search");

        
      /*/fucklist change {
        jList1 = new javax.swing.JList();
        
        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = {"test 1","test2","test3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        //efl
        
        
        
        searchresultTextArea.setColumns(20);
        searchresultTextArea.setRows(18);
        //searchScrollPane.setViewportView(searchresultTextArea);
       
        JPanel pContainer = new JPanel();
        
        pContainer.setLayout(new BorderLayout());
        pContainer.add(jList1,BorderLayout.NORTH);
        pContainer.add(searchresultTextArea,BorderLayout.SOUTH);
        // searchScrollPane.setLayout(null);
       
        searchScrollPane.setViewportView(pContainer);
        
        
        //change }
        */
        
        
        
        
        
        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Open");
        
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Save");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            	saveFile savefile=new saveFile();
            	savefile.SaveL(input.getText().toString());
            	
            }
        });
        jMenu1.add(jMenuItem2);
        
        //jmenu3 example programs
        expg.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E,InputEvent.CTRL_MASK));
        expg.setText("Example programs");
        expg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				examplepgms b=new examplepgms();
				b.setVisible(true);
				 b.setDefaultCloseOperation(b.HIDE_ON_CLOSE);
				
			}
		});

        jMenu1.add(expg);
        
        hextool.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE,InputEvent.CTRL_MASK));
        hextool.setText("hex-bin-dec");
        hextool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				hext b = null;
				try {
					b = new hext();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				b.setVisible(true);
				 b.setDefaultCloseOperation(b.HIDE_ON_CLOSE);
				
			}
		});
        
        jMenu1.add(hextool);

        jMenuBar1.add(jMenu1);

               
        jMenu2.setText("Simulation speed");
        jMenuBar1.add(jMenu2);
        
        simspeedms.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
				speed=1;
				timer.setDelay(speed);
				simspeedms.setIcon(pie);

				simspeed10ms.setIcon(null);
				simspeed1s.setIcon(null);
				simspeed10s.setIcon(null);
			}
		});
        
        
        
        
        
simspeed1s.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
				speed=100;

				timer.setDelay(speed);
				simspeed1s.setIcon(pie);

				simspeed10ms.setIcon(null);
				simspeedms.setIcon(null);
				simspeed10s.setIcon(null);
			}
		});
simspeed10s.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
				speed=1000;

				timer.setDelay(speed);
				simspeed10s.setIcon(pie);

				simspeed10ms.setIcon(null);
				simspeed1s.setIcon(null);
				simspeedms.setIcon(null);
			}
		});
simspeed10ms.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	
		speed=10;

		timer.setDelay(speed);
		simspeed10ms.setIcon(pie);

		simspeedms.setIcon(null);
		simspeed1s.setIcon(null);
		simspeed10s.setIcon(null);
	}
});
jMenu2.add(simspeedms);
jMenu2.add(simspeed10ms);
jMenu2.add(simspeed1s);
jMenu2.add(simspeed10s);

jMenu3.setText("Help");
aboutus.setText("About IDE");

aboutus.addActionListener(new ActionListener() {
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		 JDialog f = new SimpleAboutDialog(new JFrame());
		    f.show();
				
	}
});

jMenu3.add(aboutus);
jMenuBar1.add(jMenu3);


        setJMenuBar(jMenuBar1);

        
        
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(assemble)
                        .addGap(18, 18, 18)
                        .addComponent(clear)
                        .addGap(137, 137, 137)
                        .addComponent(run)
                        .addGap(150, 150, 150)
                        .addComponent(resetm)
                        .addGap(38, 38, 38)
                        .addComponent(resetr)
                        .addGap(27, 27, 27)
                        .addComponent(resetio)
                        .addGap(190, 190, 190))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(inputScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(outputScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(consoleScrollPane))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(registerScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(flagScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(memoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ioScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(55, 55, 55)
                                        .addComponent(search))
                                    .addComponent(searchScrollPane))
                                .addGap(0, 576, Short.MAX_VALUE))//edit
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(aTextField))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pcTextField))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(spTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(hTextField))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(bTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(47, 47, 47)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(eTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap())))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(outputScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(memoryScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ioScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(search))
                        .addGap(9, 9, 9)
                        .addComponent(searchScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)))//fc,size
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(assemble)
                        .addComponent(clear))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(resetm)
                        .addComponent(resetio)
                        .addComponent(resetr))
                    .addComponent(run))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(aTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(cTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(eTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(lTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(registerScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(flagScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                        .addComponent(consoleScrollPane)))
                .addContainerGap(205, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void bTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void aTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void dTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void lTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new mframe().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JDialog about;
    private javax.swing.JTextField aTextField;
    private javax.swing.JButton assemble;
    private javax.swing.JTextField bTextField;
    private javax.swing.JTextField cTextField;
    private javax.swing.JButton clear;
    private javax.swing.JScrollPane consoleScrollPane;
    private javax.swing.JTextArea consoleTextArea;
    private javax.swing.JTextField dTextField;
    private javax.swing.JTextField eTextField;
    private javax.swing.JScrollPane flagScrollPane;
    private javax.swing.JTable flagTable;
    private javax.swing.JTextField hTextField;
    private javax.swing.JTextArea input;
    private javax.swing.JScrollPane inputScrollPane;
    private javax.swing.JScrollPane ioScrollPane;
    private javax.swing.JTable iotable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem expg,simspeedms,simspeed1s,simspeed10s,simspeed10ms;
    private javax.swing.JMenuItem hextool;
    private javax.swing.JMenuItem aboutus;
    private javax.swing.JTextField lTextField;
    private javax.swing.JScrollPane memoryScrollPane;
    private javax.swing.JTable memorytable;
    private javax.swing.JTextArea opcode;
    private javax.swing.JScrollPane outputScrollPane;
    private javax.swing.JTextField pcTextField;
    private javax.swing.JScrollPane registerScrollPane;
    private javax.swing.JTable registerTable;
    private javax.swing.JButton resetio;
    private javax.swing.JButton resetm;
    private javax.swing.JButton resetr;
    private javax.swing.JButton run;
    private javax.swing.JButton search;
    private javax.swing.JScrollPane searchScrollPane;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTextArea searchresultTextArea;
    private javax.swing.JTextField spTextField;
    // End of variables declaration
    private DefaultListModel listmodel=new DefaultListModel();
    private javax.swing.JList searchList;
    //private javax.swing.JList jList1;
    





}







//linklist
class link
{ 
	public link next;
	public String name,hexcode;
	int tstate,impos,oplength;
	boolean REGF,IMDTF,CALLF,JUMPF,ZEROOPF;
	public link(String name,String hexcode,int tstate,int impos,int oplength,boolean REGF,boolean IMDTF,boolean CALLF,boolean JUMPF,boolean ZEROOPF)
	{ this.name=name;
	  this.hexcode=hexcode;
	  this.tstate=tstate;
	  this.impos=impos;
	  this.oplength=oplength;
	  this.REGF=REGF;
	  this.IMDTF=IMDTF;
	  this.CALLF=CALLF;
	  this.JUMPF=JUMPF;
	  this.ZEROOPF=ZEROOPF;
	  
	}
}
class linklist
{
	public link first;
	public linklist(){first=null;}
	
	public void addinstruction(String name,String hex,int tstate,int impos,int oplength,boolean REGF,boolean IMDTF,boolean CALLF,boolean JUMPF,boolean ZEROOPF)
	{
		link cur=first;
		link newinstruction=new link(name,hex,tstate,impos,oplength,REGF,IMDTF,CALLF,JUMPF,ZEROOPF);
		if(first==null){first=newinstruction;}
		else{
			while(cur.next!=null)
			{cur=cur.next;}
			cur.next=newinstruction;
			newinstruction.next=null;
		}
		
	}
	public void showins()
	{
		link cur=first;
		while(cur!=null)
		{
			System.out.println(cur.name+"-"+cur.hexcode+"\n");
			cur=cur.next;
		}
		
	}
}

//Symtable
class labellink
{ 
	public labellink next;
	public String label;
	int taddr;
	public labellink(String label,int taddr)
	{
		this.label=label;
		this.taddr=taddr;
	}
}
class labellist
{
	public labellink first;
	public labellist(){first=null;}
	
	public void add(String label,int taddr)
	{
		labellink cur=first;
		labellink newlabel=new labellink(label,taddr);
		if(first==null){first=newlabel;}
		else{
			while(cur.next!=null)
			{cur=cur.next;}
			cur.next=newlabel;
			newlabel.next=null;
		}
		
	}
	
	//search
	public boolean search(String lab)
	{
		labellink cur=first;
		boolean flag=true;
		while(cur!=null)
		{
			if(cur.label.equalsIgnoreCase(lab))
			{flag=false;
			break;
			}
			else
			{cur=cur.next;}	
		}
		return(flag);	
	}
	
	
	
	
	
	
	/*public void addtaddr(String label,String taddr)
	{	labellink curnt=first;
		while(curnt!=null)
		{
			if(curnt.label.equalsIgnoreCase(label))
			{
				curnt.taddr=taddr;
				break;
			}
		}
	}
	
	
	*/
	public void showins()
	{
		labellink cur=first;
		while(cur!=null)
		{
			System.out.println(cur.label+"-"+cur.taddr+"\n");
			cur=cur.next;
		}
		
	}
}
























