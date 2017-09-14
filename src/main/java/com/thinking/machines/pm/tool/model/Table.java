package com.thinking.machines.pm.tool.model;
import java.util.*;
public class Table
{
private String name;
private String javaName;
public ArrayList<Field> fields=new ArrayList<Field>();
public ArrayList<Field> primaryKeys=new ArrayList<Field>();
public int getSize()
{
return fields.size();
}
public void setName(String name)
{
this.name=name;
this.javaName=getDTOName(this.name);
}
public String getJavaName()
{
return this.javaName;
}
public String getName()
{
return this.name;
}
public String getDTOName(String tableName)
{
int x=0;
char g;
StringBuilder stringBuilder=new StringBuilder();
while(x<tableName.length())
{
if(tableName.charAt(x)=='_')
{
while(tableName.charAt(x)=='_')
{
x++;
}
if(x==tableName.length()) break;
g=tableName.charAt(x);
if(g>=97 && g<=122) g-=32;
stringBuilder.append((char)g);
x++;
}
else
{
g=tableName.charAt(x);
if(x==0) { if(g>=97 && g<=122) g-=32; }
stringBuilder.append((char)g);
x++;
}
}
return stringBuilder.toString();
}
}