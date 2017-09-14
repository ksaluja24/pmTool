import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import java.net.URL;
import java.io.*;
import java.util.*;
import java.net.*;
class Seperators
{
public static final String OPERATION_SEPARATOR="acaafc36e9e143d4ef38d616cede48d9c5a534";
public static final String OBJECT_SEPARATOR="68daf551f3ef644ce083d55d6da22caba67f";
public static final String PROPERTY_SEPARATOR="a4a86adff0ff64547349c94ccc08eda4d8830";
public static final String RESPONSE_SEPARATOR="448cf33cb05da4cbd8e9e69c8051f03572b";
public static final String EXCEPTION_SEPARATOR="d844964632ee43f50be745d9980g4d4a99";
public static final String STREAM_TERMINATOR="87ccf9d8b770f4g5d5a6c7e4d3f0ctd7965b";
public static final String RESPONSE_OK="OK";
public static final String RESPONSE_EXCEPTION="EXCEPTION";
}

 class TrayIconDemo {
	  final TrayIcon  trayIcon =new TrayIcon(createImage("bulb.gif", "tray icon"));;
    public TrayIconDemo()
	{

                createAndShowGUI();

    }
    
    private  void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
       
        final SystemTray tray = SystemTray.getSystemTray();
        
        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");
        
        //Add components to popup menu
		//popup.add(aboutItem);
       // popup.addSeparator();
        //popup.add(cb1);
        //popup.add(cb2);
        //popup.addSeparator();
        //popup.add(displayMenu);
        //displayMenu.add(errorItem);
        //displayMenu.add(warningItem);
        //displayMenu.add(infoItem);
        //displayMenu.add(noneItem);
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Thanks ..!! Project Management Tool");
            }
        });
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from the About menu item");
            }
        });
        
        cb1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                    trayIcon.setImageAutoSize(true);
                } else {
                    trayIcon.setImageAutoSize(false);
                }
            }
        });
        
        cb2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb2Id = e.getStateChange();
                if (cb2Id == ItemEvent.SELECTED){
                    trayIcon.setToolTip("Sun TrayIcon");
                } else {
                    trayIcon.setToolTip(null);
                }
            }
        });
        
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem)e.getSource();
                //TrayIcon.MessageType type = null;
                System.out.println(item.getLabel());
                if ("Error".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.ERROR;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an error message", TrayIcon.MessageType.ERROR);
                    
                } else if ("Warning".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);
                    
                } else if ("Info".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.INFO;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);
                    
                } else if ("None".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.NONE;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an ordinary message", TrayIcon.MessageType.NONE);
                }
            }
        };
        
        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
    
    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = TrayIconDemo.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
	public  void showMessage(String title,String message)
	{
	trayIcon.displayMessage(title,message, TrayIcon.MessageType.INFO);	
	}
}

class RequestProcessor extends Thread
{
	
	InputStreamReader inputStreamReader;
InputStream inputStream;
OutputStream outputStream;
OutputStreamWriter outputStreamWriter;
String requestString;
String responseString;
StringBuffer mainBuffer,tempBuffer;
String separatedByEntitySeparator[];
String separatedByOperationSeparator[];
String separatedByObjectSeparator[];
String separatedByPropertySeparator[];
String entityName;
String operationName;
Socket socket;
TrayIconDemo tid=new TrayIconDemo();
int x,y;
	public RequestProcessor(Socket socket)
	{
		this.socket=socket;
	start();
	
}
public void run()
{

		try
		{

inputStream=socket.getInputStream();
inputStreamReader=new InputStreamReader(inputStream);
mainBuffer=new StringBuffer();
tempBuffer=new StringBuffer();

while(true)
{
x=inputStreamReader.read();
if(x==-1) break;
tempBuffer.append((char)x);
if(tempBuffer.length()==Seperators.STREAM_TERMINATOR.length())
{
y=0;
while(y<tempBuffer.length())
{
if(tempBuffer.charAt(y)!=Seperators.STREAM_TERMINATOR.charAt(y)) break;
y++;
}
if(y==tempBuffer.length()) break;
mainBuffer.append(tempBuffer.charAt(0));
tempBuffer.deleteCharAt(0);
}
}
requestString=mainBuffer.toString();
System.out.println("Request arrived ["+requestString+"]\n\n");
if(requestString.equals("")==false)
{
	if(requestString.startsWith("*-*-*-*-*FolderLocation*-*-*-*-*"))
	{
		System.out.println(requestString.substring(32));
//		File folder=new File(requestString.substring(32));
		
	}
	else
	{
tid.showMessage("Receiving Files","Here your work arrives");
separatedByEntitySeparator=requestString.split(Seperators.ENTITY_SEPARATOR);
separatedByOperationSeparator=separatedByEntitySeparator[1].split(Seperators.OPERATION_SEPARATOR);
//System.out.println("Separated by Operation separators ["+separatedByOperationSeparator[1]+"]\n\n");
separatedByObjectSeparator=separatedByOperationSeparator[1].split(Seperators.OBJECT_SEPARATOR);
int x=0;
while(x<separatedByObjectSeparator.length)
{
//System.out.println("Separated by Object separators ["+separatedByObjectSeparator[x]+"]\n\n");
separatedByPropertySeparator=separatedByObjectSeparator[x].split(Seperators.PROPERTY_SEPARATOR);
int y=0;
System.out.println("File Name ["+separatedByPropertySeparator[0].substring(separatedByPropertySeparator[0].lastIndexOf(".")+1)+"]\n\n");
System.out.println("Type of file ["+separatedByPropertySeparator[1]+"]\n\n");
System.out.println("File Size ["+separatedByPropertySeparator[2]+"]\n\n");
System.out.println("File Contents ["+separatedByPropertySeparator[3]+"]\n\n");
System.out.println("File Contents ["+separatedByPropertySeparator[3].getBytes()+"]\n\n");
System.out.println("File Received [SuccessFully]\n\n");
//creating a file

String filePath=separatedByPropertySeparator[0].substring(separatedByPropertySeparator[0].lastIndexOf(".")+1);
File file;
if(separatedByPropertySeparator[1].equals("DEPENDENCY"))
{
 file =new File(filePath+".jar");	
}
else
{
String folderPath=separatedByPropertySeparator[0].substring(0,separatedByPropertySeparator[0].lastIndexOf(".")).replace(".","\\")+"\\";
file =new File(folderPath+filePath+".java");

File folder=new File(folderPath);
folder.mkdirs();
}
file.createNewFile();
RandomAccessFile raf=new RandomAccessFile(file,"rw");
raf.write(separatedByPropertySeparator[3].getBytes());

x++;
}
tid.showMessage("Recieved "+x+" Files.","Files are saved at there respective location");
}
}
responseString="";
responseString=Seperators.RESPONSE_OK;
// response is ready
responseString+=Seperators.STREAM_TERMINATOR;
outputStream=socket.getOutputStream();
outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(responseString);
outputStreamWriter.flush();
		}catch(IOException io)
		{
			System.out.println(io);
		}
		
	}	
	
}

public class DummyServer
{
private ServerSocket serverSocket;
private int portNumber;
public DummyServer(int portNumber)
{
this.portNumber=portNumber;
try
{
serverSocket=new ServerSocket(portNumber);
System.out.println("Server instantiated");
startListening();
}catch(Exception exception)
{
System.out.println(exception);
System.exit(0);
}
}
private void startListening()
{

Socket socket;
try
{
while(true)
{
System.out.println("Server is listening at port "+portNumber);
socket=serverSocket.accept();
System.out.println("Request arrived"); 
new RequestProcessor(socket);
//socket.close();
} // infinite loop ends (server)
}catch(Exception e)
{
System.out.println(e);
}
} // startListening ends
public static void main(String gg[])
{
DummyServer ds=new DummyServer(Integer.parseInt(gg[0]));
}
}

