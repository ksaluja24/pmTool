package com.thinking.machines.pm.tool.compiler;
import com.thinking.machines.pm.tool.model.*;
import java.io.*;
import java.util.*;
public class ProjectCompiler
{
private String output;
private String error;
private Project project;
private boolean errorsFlag=false;
public ProjectCompiler(Project project)
{
this.project=project;
}
public boolean hasErrors()
{
return errorsFlag;
}
public void compileProject()
{
try
{
ProcessBuilder processBuilder=new ProcessBuilder(project.gradleHome+"\\bin\\gradle.bat","build");
processBuilder.directory(new File(project.projectHome));
Process process = processBuilder.start();
output=processOutput(process.getInputStream());
error=processError(process.getErrorStream());
errorsFlag=(error.length()>0);
}catch(Exception e)
{
System.out.println(e);
}
}
public String getOutput()
{
if(this.output==null) this.output="";
return this.output;
}
public String getError()
{
if(this.error==null) this.error="";
return this.error;
}
private String processOutput(InputStream inputStream)
{
StringBuffer stringBuffer=new StringBuffer();
try
{
InputStreamReader isr=new InputStreamReader(inputStream);
BufferedReader br = new BufferedReader(isr);
String line;
while(true)
{
line=br.readLine();
if(line==null) break;
stringBuffer.append(line+"\n");
}
}catch(Exception e)
{
System.out.println(e);
}
return stringBuffer.toString();
}
private String processError(InputStream inputStream)
{
StringBuffer stringBuffer=new StringBuffer();
try
{
InputStreamReader isr=new InputStreamReader(inputStream);
BufferedReader br = new BufferedReader(isr);
String line;
while(true)
{
line=br.readLine();
if(line==null) break;
stringBuffer.append(line+"\n");
}
}catch(Exception e)
{
System.out.println(e);
}
return stringBuffer.toString();
}
}