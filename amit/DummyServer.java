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
	public static final String ENTITY_SEPARATOR="a54ddfe71239c1944f236b7d01bd41dca550a95";
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
	  final TrayIcon  trayIcon =new TrayIcon(createImage("bulb.gif", "tray icon"));
	  	private String dirToOpen=null;
    public TrayIconDemo()
	{

                createAndShowGUI();

    }
	public void update(String dirToOpen)
	{
	this.dirToOpen=dirToOpen;	
		
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
		
		
        MenuItem serverStatusItem = new MenuItem("Server is Running");
        MenuItem exitItem = new MenuItem("Exit");
        MenuItem openMenuItem = new MenuItem("Open your project folder");
        MenuItem submitMenuItem = new MenuItem("Submit");
        
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
		
        popup.add(serverStatusItem);
		popup.addSeparator();
		popup.add(openMenuItem);
		popup.add(submitMenuItem);
		popup.addSeparator();
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
       	

	String message=null;
		message="Wanna up for a challenge  ..?";
		message+="\nYour Work is about to arrive...";
		int response = JOptionPane.showConfirmDialog(null, message, "Recieve...",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
  if (response == JOptionPane.YES_OPTION) {
		if(ServerConfiguration.SERVER_IP.equals("localhost")||ServerConfiguration.SERVER_IP.equals("127.0.0.1"))
		{
			dirToOpen=".";
		}
		else
		{
			dirToOpen="C:\\";
		}
		try
		{
		Desktop.getDesktop().open(new File(dirToOpen));
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
    } else if (response == JOptionPane.CLOSED_OPTION) {
		
    }
			}
        });
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Regards...!! Project Management Tool");
            }
        });
		openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              try
		{
		Desktop.getDesktop().open(new File(dirToOpen));
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
            }
        }); 
		submitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from the Submit menu item");
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
                    trayIcon.setToolTip("Project Management Tool");
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
TrayIconDemo tid=null;
int x,y;
	public RequestProcessor(Socket socket,TrayIconDemo tid)
	{
		this.socket=socket;
		this.tid=tid;
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
		System.out.println(requestString.substring(34));
//		File folder=new File(requestString.substring(32));
		
	}
	else
	{

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
if(separatedByPropertySeparator[1].equals("DEPENDENCY"))
{
System.out.println("File Name ["+separatedByPropertySeparator[0]+"]\n\n");
	
}
System.out.println("File Name ["+separatedByPropertySeparator[0].substring(separatedByPropertySeparator[0].lastIndexOf(".")+1)+"]\n\n");
System.out.println("Type of file ["+separatedByPropertySeparator[1]+"]\n\n");
System.out.println("File Size ["+separatedByPropertySeparator[2]+"]\n\n");
System.out.println("File Contents ["+separatedByPropertySeparator[3]+"]\n\n");
System.out.println("File Contents ["+separatedByPropertySeparator[3].getBytes()+"]\n\n");
System.out.println("File Received [SuccessFully]\n\n");
//creating a file

File file;
if(separatedByPropertySeparator[1].equals("DEPENDENCY"))
{
String filePath=separatedByPropertySeparator[0];
file =new File(filePath);	
}
else
{
String filePath=separatedByPropertySeparator[0].substring(separatedByPropertySeparator[0].lastIndexOf(".")+1);
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
public static TrayIconDemo tid=new TrayIconDemo();
public static ServerConfiguration serverConfiguration=new ServerConfiguration();
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
new RequestProcessor(socket,tid);
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

class ServerConfiguration
{
public static int PORT_NUMBER;
public static String SERVER_IP;	
ServerConfiguration()
{
	try
	{
	File file=new File("server.conf");
	RandomAccessFile raf=new RandomAccessFile(file,"rw");
	raf.seek(0);
	SERVER_IP=raf.readLine();
	PORT_NUMBER=Integer.parseInt(raf.readLine());
	System.out.println("SERVER_IP: "+SERVER_IP);
	System.out.println("Port Number: "+PORT_NUMBER);
	}catch(Exception ek)
	{
		System.out.println(ek);
	}
	}
}