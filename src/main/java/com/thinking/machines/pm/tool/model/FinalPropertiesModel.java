package com.thinking.machines.pm.tool.model;
import java.util.*;
import javax.swing.table.*;
public class FinalPropertiesModel extends AbstractTableModel
{
private ArrayList<FinalProperty> finalProperties=new ArrayList();
private String [] title={"S.No.","Type","Name","Value"};
public FinalPropertiesModel()
{
}
public int getRowCount()
{
return finalProperties.size();
}
public int getColumnCount()
{
return title.length;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return new Integer(rowIndex+1);
FinalProperty finalProperty=finalProperties.get(rowIndex);
if(columnIndex==1) return finalProperty.type;
if(columnIndex==2) return finalProperty.name;
return finalProperty.value;
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0) c=Class.forName("java.lang.Integer");
else c=Class.forName("java.lang.String");
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
public void addFinalProperty(FinalProperty finalProperty)
{
FinalProperty tmp;
int x=0;
while(x<finalProperties.size())
{
tmp=finalProperties.get(x);
if(tmp.name.equals(finalProperty.name))
{
return;
}
x++;
}
finalProperties.add(finalProperty);
}
public void updateFinalProperty(int rowIndex,FinalProperty finalProperty)
{
finalProperties.remove(rowIndex);
finalProperties.add(rowIndex,finalProperty);
}
public void removeFinalProperty(int rowIndex)
{
finalProperties.remove(rowIndex);
}
public int getRowIndex(String partialName)
{
int x=0;
while(x<finalProperties.size())
{
if(finalProperties.get(x).name.startsWith(partialName)) return x;
x++;
}
return -1;
}
}