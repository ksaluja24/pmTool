package com.thinking.machines.pm.tool.model;
import java.util.*;
public class Database
{
public String name;
public String databaseProductName;
public String databaseVersion;
public ArrayList<Table> tables=new ArrayList<Table>();
public int size()
{
return tables.size();
}
}