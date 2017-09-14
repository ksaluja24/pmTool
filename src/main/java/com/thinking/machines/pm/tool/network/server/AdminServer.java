package com.thinking.machines.pm.tool.network.server;
import java.io.*;
import java.util.*;
import java.sql.*;
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


class RequestProcessor extends Thread
{
	InputStreamReader inputStreamReader;
InputStream inputStream;
OutputStream outputStream;
OutputStreamWriter outputStreamWriter;
String requestString;
String responseString="";
StringBuffer mainBuffer,tempBuffer;
String separatedByEntitySeparator[];
String separatedByOperationSeparator[];
String separatedByObjectSeparator[];
String separatedByPropertySeparator[];
String entityName;
String operationName;
Socket socket;
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
	if(requestString.startsWith("--LOGIN--"))
	{
		String abc=requestString.substring(9);
		String[] abc1=abc.split("/");
		String username=abc1[0];
		String password=abc1[1];
		boolean b=authenticateUser(username,password);
		if (b==true)
		{
		responseString=Seperators.RESPONSE_OK;
		}
		if(b==false)
		{
		responseString=Seperators.RESPONSE_EXCEPTION;	
		}
		}
	if(requestString.split(Seperators.ENTITY_SEPARATOR)[0].equals("FILE_PARCEL_INDEX"))
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
responseString="";
responseString=Seperators.RESPONSE_OK;
}
}
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
	public boolean authenticateUser(String username,String password)
{
try
{
Class.forName("com.mysql.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/admindb","root","kamal");

PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from user_manager where username=? and password=?");
preparedStatement.setString(1,username);
preparedStatement.setString(2,password);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}catch(Exception sqlException)
{
System.out.println(sqlException);
}
return false;
}
}

public class AdminServer
{
private ServerSocket serverSocket;
private int portNumber;
public AdminServer(int portNumber)
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
AdminServer ds=new AdminServer(Integer.parseInt(gg[0]));
}


}

