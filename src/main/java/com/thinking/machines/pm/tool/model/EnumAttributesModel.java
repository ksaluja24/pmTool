package com.thinking.machines.pm.tool.model;
import java.util.*;
import javax.swing.table.*;
public class EnumAttributesModel extends AbstractTableModel
{
private ArrayList<String> enumAttributes=new ArrayList<String>();
private String [] title={"S.No.","Attribute"};
public EnumAttributesModel()
{
}
public int getRowCount()
{
return enumAttributes.size();
}
public int getColumnCount()
{
return title.length;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return new Integer(rowIndex+1);
return enumAttributes.get(rowIndex);
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0) c=Class.forName("java.lang.Integer");
if(columnIndex==1) c=Class.forName("java.lang.String");
}catch(ClassNotFoundException cnfe)
{
}
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
public void addEnumAttribute(String name)
{
int x=0;
while(x<enumAttributes.size())
{
if(enumAttributes.get(x).equals(name))
{
return;
}
x++;
}
enumAttributes.add(name);
}
public void updateEnumAttribute(int rowIndex,String name)
{
enumAttributes.remove(rowIndex);
enumAttributes.add(rowIndex,name);
}
public void removeEnumAttribute(int rowIndex)
{
enumAttributes.remove(rowIndex);
}
public int getRowIndex(String partialName)
{
int x=0;
while(x<enumAttributes.size())
{
if(enumAttributes.get(x).startsWith(partialName)) return x;
x++;
}
return -1;
}
public void setEnumAttributes(ArrayList<String> enumAttributes)
{
this.enumAttributes=enumAttributes;
}
public void clear()
{
this.enumAttributes=new ArrayList<String>();
}
}
