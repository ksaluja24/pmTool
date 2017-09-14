package com.thinking.machines.pm.tool.model;
import java.util.*;
import java.sql.*;
public class DatabaseAnalyzer
{
public static Database analyze(Connection connection)
{
Database database=null;
try
{
database=new Database();
DatabaseMetaData databaseMetaData=connection.getMetaData();
database.databaseProductName=databaseMetaData.getDatabaseProductName();
database.databaseVersion=databaseMetaData.getDatabaseProductVersion();
ResultSet tablers=databaseMetaData.getTables(null,null,null,new String[]{"TABLE"});
Table table;
Field field;
while(tablers.next())
{
table=new Table();
table.setName(tablers.getString("TABLE_NAME").trim());
database.tables.add(table);
}
tablers.close();
ResultSet fieldrs;
ResultSet primaryKeysResultSet;
int x=0;
while(x<database.tables.size())
{
table=database.tables.get(x);
fieldrs=databaseMetaData.getColumns(null,null,table.getName(),null);
while(fieldrs.next())
{
field=new Field();
field.setName(fieldrs.getString("COLUMN_NAME").trim());
field.setFieldType(fieldrs.getString("TYPE_NAME").trim());
try
{
field.width=fieldrs.getString("COLUMN_SIZE").trim();
}catch(NullPointerException nullPointerException)
{
field.width="0";
}
table.fields.add(field);
}
fieldrs.close();
primaryKeysResultSet=databaseMetaData.getPrimaryKeys(null,null,table.getName());
int y;
String primaryKeyFieldName;
while(primaryKeysResultSet.next())
{
primaryKeyFieldName=primaryKeysResultSet.getString(4).trim();
y=0;
while(y<table.fields.size())
{
if(table.fields.get(y).getName().equals(primaryKeyFieldName))
{
table.primaryKeys.add(table.fields.get(y));
break;
}
y++;
}
}
x++;
}
}catch(Exception exception)
{System.out.println(exception);
}
return database;
}
}