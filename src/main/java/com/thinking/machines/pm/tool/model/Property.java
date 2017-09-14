package com.thinking.machines.pm.tool.model;
public class Property
{
public String name;
public String type;
public String getSetterName()
{
return "set"+Character.toString(name.charAt(0)).toUpperCase()+name.substring(1);
}
public String getGetterName()
{
return "get"+Character.toString(name.charAt(0)).toUpperCase()+name.substring(1);
}
}