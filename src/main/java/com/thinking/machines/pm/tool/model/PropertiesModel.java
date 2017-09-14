package com.thinking.machines.pm.tool.model;
import java.util.*;
import javax.swing.table.*;
public class PropertiesModel extends AbstractTableModel
{
private ArrayList<Property> properties=new ArrayList<Property>();
private String [] title={"S.No.","Type","Name"};
public PropertiesModel()
{
}
public int getRowCount()
{
return properties.size();
}
public int getColumnCount()
{
return title.length;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return new Integer(rowIndex+1);
Property property=properties.get(rowIndex);
if(columnIndex==1) return property.type;
return property.name;
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0) c=Class.forName("java.lang.Integer");
else c=Class.forName("java.lang.String");
}
catch(ClassNotFoundException cnfe) {}
return c;
}
public String getColumnName(int columnIndex)
{
return title[columnIndex];
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public void addProperty(Property property)
{
Property tmp;
int x=0;
while(x<properties.size())
{
tmp=properties.get(x);
if(tmp.name.equals(property.name) && tmp.type.equals(property.type))
{
return;
}
x++;
}
properties.add(property);
}
public void updateProperty(int rowIndex,Property property)
{
properties.remove(rowIndex);
properties.add(rowIndex,property);
}
public void removeProperty(int rowIndex)
{
properties.remove(rowIndex);
}
public int getRowIndex(String partialName)
{
int x=0;
while(x<properties.size())
{
if(properties.get(x).name.startsWith(partialName)) return x;
x++;
}
return -1;
}
public void setPropertiesList(ArrayList<Property> properties)
{
this.properties=properties;
}
public Property getPropertyAt(int rowIndex)
{
return properties.get(rowIndex);
}
public void resetPropertiesList()
{
while(properties.size()>0)
{
properties.remove(0);
}
}
}