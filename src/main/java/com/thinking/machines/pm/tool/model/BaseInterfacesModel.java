package com.thinking.machines.pm.tool.model;
import com.thinking.machines.pm.tool.model.exceptions.*;
import java.util.*;
import javax.swing.table.*;
public class BaseInterfacesModel extends AbstractTableModel
{
public ArrayList<String> names=new ArrayList<String>();
private String [] title={"S.No.","Name"};
public BaseInterfacesModel()
{}
public int getRowCount()
{
return names.size();
}
public int getColumnCount()
{
return title.length;
}
public Object getValueAt(int rowIndex,int columnIndex)
{ if(columnIndex==0) return new Integer(rowIndex+1);
return names.get(rowIndex);
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{ if(columnIndex==0) c=Class.forName("java.lang.Integer");
if(columnIndex==1) c=Class.forName("java.lang.String");
}catch(ClassNotFoundException cnfe)
{}
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
public void addBaseInterface(String name)
{ int x=0;
while(x<names.size())
{ if(names.get(x).equals(name))
{
return;
}
x++;
}
names.add(name);
}
public void updateBaseInterface(int rowIndex,String name)
{
names.remove(rowIndex);
names.add(rowIndex,name);
}
public void removeBaseInterface(int rowIndex)
{
names.remove(rowIndex);
}
public int getRowIndex(String partialName)
{ int x=0;
while(x<names.size())
{ if(names.get(x).startsWith(partialName)) return x;
x++;
}
return -1;
}}