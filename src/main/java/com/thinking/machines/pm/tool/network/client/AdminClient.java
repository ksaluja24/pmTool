package com.thinking.machines.pm.tool.network.client;
import java.net.*;
import java.io.*;
import static com.thinking.machines.pm.tool.network.client.Separators.*;
public class AdminClient
{
public static String sendRequest(String request)
{
InputStream is;
InputStreamReader isr;
OutputStream os;
OutputStreamWriter osw;
StringBuffer mainBuffer,tempBuffer;
int x,y;
String response="";
String server=ServerConfiguration.SERVER_IP;
int portNumber=ServerConfiguration.PORT_NUMBER;
try
{
Socket socket=new Socket(server,portNumber);
os=socket.getOutputStream();
osw=new OutputStreamWriter(os);
osw.write(request+STREAM_TERMINATOR);
osw.flush();
System.out.println("Request sent "+request);
is=socket.getInputStream();
isr=new InputStreamReader(is);
mainBuffer=new StringBuffer();
tempBuffer=new StringBuffer();
while(true)
{
x=isr.read();
if(x==-1) break;
tempBuffer.append((char)x);
if(tempBuffer.length()==STREAM_TERMINATOR.length())
{
y=0;
while(y<tempBuffer.length())
{
if(tempBuffer.charAt(y)!=STREAM_TERMINATOR.charAt(y)) break;
y++;
}
if(y==tempBuffer.length()) break;
mainBuffer.append(tempBuffer.charAt(0));
tempBuffer.deleteCharAt(0);
}
}
response=mainBuffer.toString();
System.out.println("Response arrived : "+response);
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
return response;
}
}