package com.thinking.machines.pm.tool.main;
import java.sql.*;
import java.util.*;
import com.thinking.machines.pm.tool.model.*;
import com.thinking.machines.pm.tool.ui.*;
import javax.swing.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import com.thinking.machines.pm.tool.network.server.*;
public class Main
{
public Main()
{
	

try {
	UIManager.setLookAndFeel ( "com.alee.laf.WebLookAndFeel" );
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
      UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon. 
HashMap<String,String> nameValuePairs=null;
String xmlContents="Configuration file (project.xml) is missing,\n";
xmlContents=xmlContents+"it should be in the following format\n\n";
xmlContents=xmlContents+"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n";
xmlContents=xmlContents+"<project>\n";
xmlContents=xmlContents+"<title>Salary processing system</title>\n";
xmlContents=xmlContents+"<folder>c:\\it2015tooltest\\src\\main\\java</folder>\n";
xmlContents=xmlContents+"<package>com.thinking.machines.salary</package>\n";
xmlContents=xmlContents+"<database-driver>com.mysql.jdbc.Driver</database-driver>\n";
xmlContents=xmlContents+"<connection-string>jdbc:mysql://localhost:3306/bookdb</connection-string>\n";
xmlContents=xmlContents+"<database-username>booku</database-username>\n";
xmlContents=xmlContents+"<database-password>bookp</database-password>\n";
xmlContents=xmlContents+"<project-home>c:\\project_folder_path</project-home>\n";
xmlContents=xmlContents+"<gradle-home>c:\\gradle_folder_path</gradle-home>\n";
xmlContents=xmlContents+"</project>\n";
try
{
File file=new File("project.xml");
if(file.exists()==false)
{
JOptionPane.showMessageDialog(null,xmlContents);
System.exit(0); // application ends
}
DocumentBuilderFactory documentBuilderFactory;
documentBuilderFactory=DocumentBuilderFactory.newInstance();
DocumentBuilder documentBuilder;
documentBuilder=documentBuilderFactory.newDocumentBuilder();
Document document;
document=documentBuilder.parse("project.xml");
String rootNodeName=document.getDocumentElement().getNodeName();
if(rootNodeName.toLowerCase().equals("project")==false)
{
JOptionPane.showMessageDialog(null,xmlContents);
System.exit(0); // application ends
}
NodeList nodes=document.getElementsByTagName(rootNodeName);
NodeList childNodes=nodes.item(0).getChildNodes();
nameValuePairs=new HashMap<String,String>();
String nodeName;
String nodeValue;
int x=0;
while(x<childNodes.getLength())
{
nodeName=childNodes.item(x).getNodeName();
nodeValue=childNodes.item(x).getTextContent();
if(nodeName.toLowerCase().equals("title"))
{
nameValuePairs.put("title",nodeValue);
}
if(nodeName.toLowerCase().equals("folder"))
{
nameValuePairs.put("folder",nodeValue);
}
if(nodeName.toLowerCase().equals("package"))
{
nameValuePairs.put("package",nodeValue);
}
if(nodeName.toLowerCase().equals("database-driver"))
{
nameValuePairs.put("database-driver",nodeValue);
}
if(nodeName.toLowerCase().equals("connection-string"))
{
nameValuePairs.put("connection-string",nodeValue);
}
if(nodeName.toLowerCase().equals("database-username"))
{
nameValuePairs.put("database-username",nodeValue);
}
if(nodeName.toLowerCase().equals("database-password"))
{
nameValuePairs.put("database-password",nodeValue);
}
if(nodeName.toLowerCase().equals("project-home"))
{
nameValuePairs.put("project-home",nodeValue);
}
if(nodeName.toLowerCase().equals("gradle-home"))
{
nameValuePairs.put("gradle-home",nodeValue);
}
x++;
}
if(nameValuePairs.size()!=9)
{
JOptionPane.showMessageDialog(null,xmlContents);
System.exit(0); // application ends
}
}catch(Exception exception)
{
System.out.println(exception); // remove after testing
JOptionPane.showMessageDialog(null,xmlContents);
System.exit(0); // application ends
}
// parsing complete
Project project=new Project();
project.title=nameValuePairs.get("title");
project.folder=nameValuePairs.get("folder");
project.packageName=nameValuePairs.get("package");
project.databaseDriver=nameValuePairs.get("database-driver");
project.connectionString=nameValuePairs.get("connection-string");
project.databaseUsername=nameValuePairs.get("database-username");
project.databasePassword=nameValuePairs.get("database-password");
project.projectHome=nameValuePairs.get("project-home");
project.gradleHome=nameValuePairs.get("gradle-home");
try
{
Class.forName(project.databaseDriver);
Connection
connection=DriverManager.getConnection(project.connectionString,project.databaseUsername,project.databasePassword);
project.database=DatabaseAnalyzer.analyze(connection);
connection.close();
}catch(ClassNotFoundException classNotFoundException)
{
JOptionPane.showMessageDialog(null,"Database driver : "+project.databaseDriver+" not in classpath");
System.exit(0);
}
catch(SQLException sqlException)
{
JOptionPane.showMessageDialog(null,"Invalid database connection details : "+project.connectionString+", username : "+project.databaseUsername+", password: "+project.databasePassword);
System.exit(0);
}
catch(Exception exception)
{
System.out.println(exception); // remove after testing
}
// database connection check complete
try
{
File folder=new File(project.folder);
if(folder.exists())
{
if(folder.isDirectory()==false)
{
JOptionPane.showMessageDialog(null,project.folder+" is not a folder");
System.exit(0);
}
}
else // folder doesn't exists
{
int i=JOptionPane.showConfirmDialog(null,project.folder+" does not exist,create ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(i==JOptionPane.YES_OPTION)
{
folder.mkdirs();
}
else
{
JOptionPane.showMessageDialog(null,"Please create folder : "+project.folder+" to proceed");
System.exit(0);
}
}
// folder check complete and now folder exists
String packageFolderName=project.packageName.replace(".","\\");
String fullPath=project.folder;
if(fullPath.endsWith("\\")) fullPath=fullPath+packageFolderName;
else fullPath=fullPath+"\\"+packageFolderName;
File packageFolder=new File(fullPath);
if(packageFolder.exists())
{
if(packageFolder.isDirectory()==false)
{
JOptionPane.showMessageDialog(null,packageFolderName+" is not a folder");
System.exit(0);
}
}
else // folder doesn't exists
{
int i=JOptionPane.showConfirmDialog(null,packageFolderName+" does not exist in "+project.folder+", create ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(i==JOptionPane.YES_OPTION)
{
packageFolder.mkdirs();
}
else
{
JOptionPane.showMessageDialog(null,"Please create package folder : "+packageFolderName+"in "+project.folder+" to proceed");
System.exit(0);
}
}
// package folder check complete and now folder exists
}catch(Exception exception)
{
System.out.println(exception); // remove after testing
}
// Parsing member xml files starts
Team team=new Team();
ArrayList<String> memberXMLFiles=new ArrayList<String>();
try
{
File membersDataFile=new File("members.data");
if(membersDataFile.exists())
{
RandomAccessFile randomAccessFile=new RandomAccessFile(membersDataFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
memberXMLFiles.add(randomAccessFile.readLine());
}
randomAccessFile.close();
}
}catch(Exception exception)
{
// do nothing
}
String xmlFileName;
Member member;
String id;
int r=0;
while(r<memberXMLFiles.size())
{
try
{
id=memberXMLFiles.get(r);
xmlFileName=memberXMLFiles.get(r)+".xml";
DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
Document document=documentBuilder.parse(xmlFileName);
String rootNodeName=document.getDocumentElement().getNodeName();
if(rootNodeName.toLowerCase().equals("member")==false)
{
r++;
continue;
}
NodeList nodes=document.getElementsByTagName(rootNodeName);
NodeList childNodes=nodes.item(0).getChildNodes();
nameValuePairs=new HashMap<String,String>();
int x=0;
while(x<childNodes.getLength())
{
String nodeName=childNodes.item(x).getNodeName();
String nodeValue=childNodes.item(x).getTextContent();
if(nodeName.toLowerCase().equals("name"))
{
nameValuePairs.put("name",nodeValue);
}
if(nodeName.toLowerCase().equals("machine"))
{
nameValuePairs.put("machine",nodeValue);
}
if(nodeName.toLowerCase().equals("port"))
{
nameValuePairs.put("port",nodeValue);
}
x++;
}
if(nameValuePairs.size()==3)
{
member=new Member();
member.id=id;
member.name=nameValuePairs.get("name");
member.machine=nameValuePairs.get("machine");
member.port=Integer.parseInt(nameValuePairs.get("port"));
team.members.add(member);
}
}catch(Exception exception)
{
// do nothing
}
r++;
}
// parsing member xml file ends
// create a new object of class that inherits JDialog
if(team.members.size()>0)
{
project.team=team;
}
else
{
project.team=null; // just for clearity
}
//extra
try
{
createFilesNeeded(project);
TrayIconDemo tid=new TrayIconDemo();
}catch(Exception e)
{
System.out.println(e);	
}
System.out.println("Starting the server..");
Thread serverThread=new Thread(new Runnable(){
	public void run()
	{
new AdminServer(5000);	
	}
});
serverThread.start();
Login login=new Login(project);

}

private void createFilesNeeded(Project project) throws FileNotFoundException,IOException
{
	
String filePath="";
if(project.folder.endsWith("\\"))
{
filePath=project.folder+project.packageName.replace(".","\\")+"\\dl\\dao\\connection\\";
} else
{
filePath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dao\\connection\\";
}
File folder=new File(filePath);
if(folder.exists()==false)
{
folder.mkdirs();
} else
{ if(folder.isDirectory()==false)
{JOptionPane.showMessageDialog(null,filePath+" is a file, please remove that file to proceed");
return;
}}
System.out.println("File Path: "+filePath);
filePath=filePath+"DAOConnection.java";
File file=new File(filePath);
if(file.exists()==false)
{
String line;
RandomAccessFile raf=new RandomAccessFile(file,"rw");

line="package "+project.packageName+".dl.dao.connection;";
line+="\r\n";
line+="import "+project.packageName+".dl.exceptions.*;";
line+="\r\n";
line+="import java.sql.*;";
line+="\r\n";
line+="public class DAOConnection";
line+="\r\n";
line+="{";
line+="\r\n";
line+="public static Connection getConnection() throws DAOException";
line+="\r\n";
line+="{";
line+="\r\n";
line+="Connection connection=null;";
line+="\r\n";
line+="try";
line+="\r\n";
line+="{";
line+="\r\n";
line+="Class.forName("+"\"com.mysql.jdbc.Driver\""+");";
line+="\r\n";
line+="connection=DriverManager.getConnection("+"\""+project.connectionString+"\""+","+"\""+project.databaseUsername+"\""+","+"\""+project.databasePassword+"\""+");";
line+="\r\n";
line+="}catch(Exception exception)";
line+="\r\n";
line+="{";
line+="\r\n";
line+="throw new DAOException(exception.getMessage());";
line+="\r\n";
line+="}";
line+="\r\n";
line+="return connection;";
line+="\r\n";
line+="}";
line+="\r\n";
line+="}";
line+="\r\n";
raf.writeBytes(line);
System.out.println(line);
}


	
 filePath="";
if(project.folder.endsWith("\\"))
{
filePath=project.folder+project.packageName.replace(".","\\")+"\\dl\\exceptions";
} else
{
filePath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\exceptions\\";
}
 folder=new File(filePath);
if(folder.exists()==false)
{
folder.mkdirs();
} else
{ if(folder.isDirectory()==false)
{JOptionPane.showMessageDialog(null,filePath+" is a file, please remove that file to proceed");
return;
}}
System.out.println("File Path: "+filePath);
filePath=filePath+"DAOException.java";
 file=new File(filePath);
if(file.exists()==false)
{
String line;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
line="package "+project.packageName+".dl.exceptions;";
line+="\r\n";
line+="public class DAOException extends Exception";
line+="\r\n";
line+="{";
line+="\r\n";
line+="public DAOException(String message)";
line+="\r\n";
line+="{";
line+="\r\n";
line+="super(message);";
line+="\r\n";
line+="}";
line+="\r\n";
line+="}";
line+="\r\n";
raf.writeBytes(line);
System.out.println(line);
}

 filePath="";
if(project.folder.endsWith("\\"))
{
filePath=project.projectHome;
} else
{
filePath=project.projectHome+"\\";
}
 folder=new File(filePath);
System.out.println("File Path: "+filePath);
filePath=filePath+"build.gradle";
 file=new File(filePath);
if(file.exists()==false)
{
String line;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
line="apply plugin: 'java'";
raf.writeBytes(line);
System.out.println(line);
}

}
}