package com.thinking.machines.pm.tool.logger;
import java.io.*;
public class TMLogger
{
private static boolean consoleLogging=true;
public static boolean isConsoleLoggingOn()
{
return consoleLogging;
}
public static void setConsoleLogging(boolean cl)
{
consoleLogging=cl;
}
public static void addToLog(String logData)
{
java.util.Date date=new java.util.Date();
String logTime=date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
try
{
int day=date.getDate();
int month=date.getMonth();
int year=date.getYear()+1900;
String fileName;
if(day<=26)
{
fileName=((char)(65+(day-1)))+"__"+day+"_"+(month+1)+"_"+year+".log";
}
else
{
fileName=((char)(97+(day-27)))+"__"+day+"_"+(month+1)+"_"+year+".log";
}
String yearFolderName=String.valueOf(year);
String monthFolderName=((char)(65+month))+"__"+(month+1);
String folderStructureRequired=yearFolderName+"/"+monthFolderName;
File f=new File(folderStructureRequired);
if(f.exists()==false)
{
f.mkdirs();
}
File file=new File(folderStructureRequired+"/"+fileName);
RandomAccessFile raf=new RandomAccessFile(file,"rw");
raf.seek(raf.length());
raf.writeBytes(logTime+"-"+logData+"\r\n");
raf.close();
}catch(Exception e)
{
System.out.println(e);
}
if(consoleLogging)
{
System.out.println(logTime+"-"+logData);
}
}
}