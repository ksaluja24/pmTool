package com.thinking.machines.pm.tool.model;
import java.util.*;
import javax.swing.table.*;
public class AdditionalMethodParametersModel extends AbstractTableModel
{
public ArrayList<AdditionalMethodParameter> additionalMethodParameters=new
ArrayList<AdditionalMethodParameter>();
private String [] title={"S.No.","Data Type","Name"};
public AdditionalMethodParametersModel()
{
}
public int getRowCount()
{
return additionalMethodParameters.size();
}
public int getColumnCount()
{
return title.length;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
AdditionalMethodParameter additionalMethodParameter=additionalMethodParameters.get(rowIndex);
if(columnIndex==0) return new Integer(rowIndex+1);
if(columnIndex==1)return additionalMethodParameter.dataType;
return additionalMethodParameter.parameterName;
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
public void addAdditionalMethodParameter(AdditionalMethodParameter additionalMethodParameter)
{
int x=0;
AdditionalMethodParameter tmp;
while(x<additionalMethodParameters.size())
{
tmp=additionalMethodParameters.get(x);
if(tmp.parameterName.equals(additionalMethodParameter.parameterName)) return;
x++;
}
additionalMethodParameters.add(additionalMethodParameter);
}
public void updateAdditionalMethodParameter(int rowIndex,AdditionalMethodParameter
additionalMethodParameter)
{
additionalMethodParameters.remove(rowIndex);
additionalMethodParameters.add(rowIndex,additionalMethodParameter);
}
public void removeAdditionalMethodParameter(int rowIndex)
{
additionalMethodParameters.remove(rowIndex);
}
public int getRowIndex(String partialName)
{
int x=0;
while(x<additionalMethodParameters.size())
{
if(additionalMethodParameters.get(x).parameterName.startsWith(partialName)) return x;
x++;
}
return -1;
}
public AdditionalMethodParameter getAdditionalMethodParameterAt(int index)
{
return additionalMethodParameters.get(index);
}
public void set(int index,AdditionalMethodParameter additionalMethodParameter)
{
additionalMethodParameters.set(index,additionalMethodParameter);
}
public void setParametersList(ArrayList<AdditionalMethodParameter> additionalMethodParameters)
{
this.additionalMethodParameters=additionalMethodParameters;
}
public void clear()
{
this.additionalMethodParameters.clear();
}
}