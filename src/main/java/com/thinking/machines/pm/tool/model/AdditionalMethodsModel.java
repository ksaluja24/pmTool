package com.thinking.machines.pm.tool.model;
import java.util.*;
import javax.swing.table.*;
public class AdditionalMethodsModel extends AbstractTableModel
{
private ArrayList<AdditionalMethod> additionalMethods=new ArrayList();
private String [] title={"S.No.","Return Type","Name"};
public AdditionalMethodParametersModel additionalMethodParametersModel;
public AdditionalMethodsModel()
{
}
public int getRowCount()
{
return additionalMethods.size();
}
public int getColumnCount()
{
return title.length;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return new Integer(rowIndex+1);
AdditionalMethod additionalMethod=additionalMethods.get(rowIndex);
if(columnIndex==1) return additionalMethod.returnType;
return additionalMethod.name;
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0) c=Class.forName("java.lang.Integer");
else c=Class.forName("java.lang.String");
}catch(ClassNotFoundException cnfe) {}
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
public void addAdditionalMethod(AdditionalMethod additionalMethod)
{
AdditionalMethod tmp;
int x=0;
while(x<additionalMethods.size())
{
tmp=additionalMethods.get(x);
if(tmp.name.equals(additionalMethod.name)) return;
x++;
}
additionalMethods.add(additionalMethod);
}
public void updateAdditionalMethod(int rowIndex,AdditionalMethod additionalMethod)
{
additionalMethods.remove(rowIndex);
additionalMethods.add(rowIndex,additionalMethod);
}
public void removeAdditionalMethod(int rowIndex)
{
additionalMethods.remove(rowIndex);
}
public int getRowIndex(String partialName)
{
int x=0;
while(x<additionalMethods.size())
{
if(additionalMethods.get(x).name.startsWith(partialName)) return x;
x++;
}
return -1;
}
public AdditionalMethod getAdditionalMethodAt(int index)
{
return additionalMethods.get(index);
}
public void setAdditionalMethodAt(int index,AdditionalMethod additionalMethod)
{
additionalMethods.set(index,additionalMethod);
}
}