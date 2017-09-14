package com.thinking.machines.pm.tool.model;
import javax.swing.table.*;
import com.thinking.machines.pm.tool.model.exceptions.*;
import com.thinking.machines.pm.tool.dl.exceptions.*;
import com.thinking.machines.pm.tool.dl.dto.interfaces.*;
import com.thinking.machines.pm.tool.dl.dao.interfaces.*;
import com.thinking.machines.pm.tool.dl.dao.*;
import com.thinking.machines.pm.tool.dl.dto.*;
import java.util.*;
public class MemberWorkModel extends AbstractTableModel
{
private  List<WorkDTOInterface> works;
private String memberId;
private String[] title={"S.No.","File","Time","Days","Status"};
private java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd-MM-yyyy,hh:mm:ss");
private WorkDAOInterface workDAOInterface;
private ArrayList<String> members=new ArrayList();
public MemberWorkModel(String memberId)
{
this.memberId=memberId;
loadDataStructure();
 workDAOInterface=new WorkDAO();
}
private void loadDataStructure()
{
if(memberId==null)
{
works=new ArrayList<WorkDTOInterface>();
return;
}
try
{
works=new WorkDAO().getByMemberId(memberId);
}catch(DAOException daoException)
{
works=new ArrayList<WorkDTOInterface>();
}
}
public int getColumnCount()
{
return title.length;
}
public int getRowCount()
{
return works.size();
}
public String getColumnName(int column)
{
return title[column];
}
public boolean isCellEditable(int row,int column)
{
return false;
}
public Object getValueAt(int row,int column)
{
if(column==0) return new Integer(row+1);
WorkDTOInterface workDTOInterface=works.get(row);
if(column==1) return
workDTOInterface.getFileName().substring(workDTOInterface.getFileName().lastIndexOf(".")+1);
java.util.Date obtainedDate=workDTOInterface.getDate();
if(column==2) return sdf.format(obtainedDate);

if(column==3) 
{
java.util.Date currentDate=new java.util.Date();
 int diff =currentDate.getDate() - obtainedDate.getDate()+1;
return diff;
}

if(column==4) return workDTOInterface.getStatus().name();
return null; // will never arise
}
public Class getColumnClass(int column)
{
Class c=null;
try
{
if(column==0 || column==3) c=Class.forName("java.lang.Integer");
else c=Class.forName("java.lang.String");
}catch(ClassNotFoundException cnfe)
{
System.out.println(cnfe); // will never arise
}
return c;
}
public WorkDTOInterface getWorkDTOAt(int i)
{
return works.get(i);
}
public void add(WorkDTOInterface workDTOInterface) throws ModelException
{
try
{
workDAOInterface.add(workDTOInterface);
works.add(workDTOInterface);
}catch(DAOException daoException)
{
throw new ModelException(daoException.getMessage());
}
}

public void remove(int index) throws ModelException
{
try
{

WorkDTOInterface workDTOInterface=works.get(index);
if(workDTOInterface.getStatus()==WorkDTOInterface.STATUS.SENT)
{
throw new ModelException("Can't remove ...File Already SENT");
}
workDAOInterface.remove(workDTOInterface.getCode());
works.remove(index);


}catch(DAOException daoException)
{
throw new ModelException(daoException.getMessage());
}
}

public void update(int index,WorkDTOInterface workDTOInterface) throws ModelException
{
try
{

works.remove(index);
works.add(index,workDTOInterface);
workDAOInterface.update(workDTOInterface);
}catch(DAOException daoException)
{
throw new ModelException(daoException.getMessage());
}
}

public void setMemberId(String memberId)
{
this.memberId=memberId;
loadDataStructure();


int x=0;
while(x<members.size())
{
	if(memberId.equals(members.get(x)))
	return;
x++;
}
this.members.add(this.memberId);
}
public boolean fileExists(String fileName)
{
int y=0;
while(y<members.size())
{

setMemberId(members.get(y));
int x=0;
String temp;
while(x<works.size())
{
	temp=works.get(x).getFileName();
temp=temp.substring(temp.lastIndexOf(".")+1);
	if(temp.equals(fileName)) return true;
x++;
}


y++;
}

return false;
}

}