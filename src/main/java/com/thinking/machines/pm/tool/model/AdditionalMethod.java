package com.thinking.machines.pm.tool.model;
import java.util.*;
public class AdditionalMethod
{
public String name;
public String returnType;
public ArrayList<AdditionalMethodParameter> additionalMethodParameters=new
ArrayList<AdditionalMethodParameter>();
public String getDeclaration()
{
String declaration;
declaration="public "+returnType+" "+name+"(";
AdditionalMethodParameter additionalMethodParameter;
int x=0;
while(x<additionalMethodParameters.size())
{
additionalMethodParameter=additionalMethodParameters.get(x);
declaration+=additionalMethodParameter.dataType;
declaration+=" ";
declaration+=additionalMethodParameter.parameterName;
x++;
if(x<additionalMethodParameters.size())
{
declaration+=",";
}
}
declaration+=");";
return declaration;
}
public String getDeclarationOfDAOInterface()
{
String declaration;
declaration="public "+returnType+" "+name+"(";

AdditionalMethodParameter additionalMethodParameter;
int x=0;
while(x<additionalMethodParameters.size())
{
additionalMethodParameter=additionalMethodParameters.get(x);
declaration+=additionalMethodParameter.dataType;
declaration+=" ";
declaration+=additionalMethodParameter.parameterName;
x++;
if(x<additionalMethodParameters.size())
{
declaration+=",";
}
}
declaration+=") throws DAOException;";
return declaration;
}
}