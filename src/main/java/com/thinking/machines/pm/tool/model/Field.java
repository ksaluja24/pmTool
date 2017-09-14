package com.thinking.machines.pm.tool.model;
import java.util.*;
public class Field
{
private String name;
private String javaName;
private String fieldType;
private String javaType;
public String width;
public boolean isPrimaryKey;
public void setName(String name)
{
this.name=name;
javaName=getJavaPropertyName(this.name);
}
public String getName()
{
return this.name;
}
public String getJavaName()
{
return this.javaName;
}
public void setFieldType(String fieldType)
{
this.fieldType=fieldType;
this.javaType=getJavaDataType(this.fieldType);
}
public String getFieldType()
{
return this.fieldType;
}
public String getJavaType()
{
return this.javaType;
}
public String getJavaPropertyName(String fieldName)
{
int x=0;
char g;
StringBuilder stringBuilder=new StringBuilder();
while(x<fieldName.length())
{
if(fieldName.charAt(x)=='_')
{
while(fieldName.charAt(x)=='_')
{
x++;
}
if(x==fieldName.length()) break;
g=fieldName.charAt(x);
if(stringBuilder.length()==0)
{
if(g>=65 && g<=90) g+=32;
}
else
{
if(g>=97 && g<=122) g-=32;
}
stringBuilder.append((char)g);
x++;
}
else
{
g=fieldName.charAt(x);
if(x==0) { if(g>=65 && g<=90) g+=32; }
stringBuilder.append((char)g);
x++;
}
}
return stringBuilder.toString();
}
public String getJavaDataType(String fieldType)
{
if(fieldType.toUpperCase().equals("CHAR"))
{
return "java.lang.String";
}
if(fieldType.toUpperCase().equals("VARCHAR"))
{
return "java.lang.String";
}
if(fieldType.toUpperCase().equals("INT"))
{
return "int";
}
if(fieldType.toUpperCase().equals("BIGINT"))
{
return "long";
}
if(fieldType.toUpperCase().equals("DOUBLE"))
{
return "double";
}
if(fieldType.toUpperCase().equals("FLOAT"))
{
return "float";
}
if(fieldType.toUpperCase().equals("BOOL"))
{
return "boolean";
}
if(fieldType.toUpperCase().equals("DATE"))
{
return "java.sql.Date";
}
if(fieldType.toUpperCase().equals("TIME"))
{
return "java.sql.Time";
}
// you can add more as per your requirement
return "java.lang.String";
}
}