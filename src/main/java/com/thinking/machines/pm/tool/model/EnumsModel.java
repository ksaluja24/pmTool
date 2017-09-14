package com.thinking.machines.pm.tool.model;
import java.util.*;
import javax.swing.table.*;
public class EnumsModel extends AbstractTableModel
{
private ArrayList<Enum> enums=new ArrayList();
private String [] title={"S.No.","Name"};
public EnumAttributesModel enumAttributesModel;
public EnumsModel()
{
}
public int getRowCount()
{
return enums.size();
}
public int getColumnCount()
{
return title.length;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return new Integer(rowIndex+1);
Enum vEnum=enums.get(rowIndex);
return vEnum.name;
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
public void addEnum(Enum vEnum)
{
Enum tmp;
int x=0;
while(x<enums.size())
{
tmp=enums.get(x);
if(tmp.name.equals(vEnum.name))
{
return;
}
x++;
}
enums.add(vEnum);
}
public void updateEnum(int rowIndex,Enum vEnum)
{
enums.remove(rowIndex);
enums.add(rowIndex,vEnum);
}
public void removeEnum(int rowIndex)
{
enums.remove(rowIndex);
}
public int getRowIndex(String partialName)
{
int x=0;
while(x<enums.size())
{
if(enums.get(x).name.startsWith(partialName)) return x;
x++;
}
return -1;
}
public com.thinking.machines.pm.tool.model.Enum getEnumAt(int index)
{
return enums.get(index);
} 
public void setEnumAt(int index,com.thinking.machines.pm.tool.model.Enum vEnum)
{
enums.set(index,vEnum);
}
}