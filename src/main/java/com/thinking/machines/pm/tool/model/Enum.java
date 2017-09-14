package com.thinking.machines.pm.tool.model;
import java.util.*;
public class Enum
{
public String name;
public ArrayList<String> enumAttributes=new ArrayList<String>();
public String getDeclaration()
{
String declaration="public enum ";
declaration+=name;
declaration+="{";
int x=0;
String enumAttribute;
while(x<enumAttributes.size())
{
enumAttribute=enumAttributes.get(x);
declaration+=enumAttribute;
x++;
if(x<enumAttributes.size())
{
declaration+=",";
}
}
declaration+="};";
return declaration;
}
}