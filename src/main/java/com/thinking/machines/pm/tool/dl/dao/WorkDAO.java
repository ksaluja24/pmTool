package com.thinking.machines.pm.tool.dl.dao;
import com.thinking.machines.pm.tool.dl.dao.interfaces.*;
import com.thinking.machines.pm.tool.dl.dto.interfaces.*;
import com.thinking.machines.pm.tool.dl.dto.*;
import com.thinking.machines.pm.tool.dl.exceptions.*;
import com.thinking.machines.pm.tool.dl.dao.*;
import com.thinking.machines.pm.tool.logger.*;
import java.io.*;
import java.util.*;
public class WorkDAO implements WorkDAOInterface
{
private java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd-MM-yyyy,hh:mm:ss");
public void add(WorkDTOInterface workDTOInterface) throws DAOException
{
try
{
File aiFile=new File("workpk.data");
RandomAccessFile raf2=new RandomAccessFile(aiFile,"rw");
int existingCode=0;
if(raf2.length()>0)
{
existingCode=Integer.parseInt(raf2.readLine());
}
int newCode=existingCode+1;
raf2.seek(0);
raf2.writeBytes(newCode+"\n");
raf2.close();
File file=new File("work.data");
RandomAccessFile raf=new RandomAccessFile(file,"rw");
raf.seek(raf.length());
workDTOInterface.setCode(newCode);
raf.writeBytes(newCode+"\n");
raf.writeBytes(workDTOInterface.getMemberId()+"\n");
raf.writeBytes(workDTOInterface.getMemberName()+"\n");
raf.writeBytes(workDTOInterface.getFileName()+"\n");
raf.writeBytes(sdf.format(workDTOInterface.getDate())+"\n");
raf.writeBytes(workDTOInterface.getStatus().name()+"\n");
raf.close();
}catch(IOException ioException)
{
TMLogger.addToLog("WorkDAO : public void add(WorkDTOInterface workDTOInterface) throws DAOException -->"+ioException.getMessage()+" -->"+ioException.toString());
}
}
public void update(WorkDTOInterface workDTOInterface) throws DAOException
{
try
{
	System.out.println("1");
	File file=new File("work.data");
	RandomAccessFile raf2=new RandomAccessFile(file,"rw");
raf2.seek(0);
while(raf2.getFilePointer()<raf2.length())
{
	System.out.println("inside while outer waala");
int v_code=Integer.parseInt(raf2.readLine());
if(v_code==workDTOInterface.getCode())
{
long new1=raf2.getFilePointer();
	System.out.println("new 1"+new1);
raf2.readLine();
raf2.readLine();
raf2.readLine();
raf2.readLine();
long temp=raf2.getFilePointer();
	System.out.println("temp"+temp);
raf2.readLine();
StringBuffer sb=new StringBuffer();
while(raf2.getFilePointer()<raf2.length())
{
	sb.append(raf2.readLine()+"\n");
}
	System.out.println("pura padh liya"+raf2.getFilePointer());
System.out.println(sb.toString());
raf2.seek(temp);
raf2.writeBytes(workDTOInterface.getStatus().name()+"\n");
	System.out.println("status change karne k baad"+raf2.getFilePointer());
raf2.writeBytes(sb.toString());
raf2.setLength(raf2.getFilePointer());
return;
}
raf2.readLine();
raf2.readLine();
raf2.readLine();
raf2.readLine();
raf2.readLine();
}
raf2.close();
}catch(IOException ioException)
{
TMLogger.addToLog("public void update(WorkDTOInterface workDTOInterface) throws DAOException-->"+ioException.getMessage()+" -->"+ioException.toString());
}
}
public void remove(long code) throws DAOException
{
try
{
	File file=new File("work.data");
	RandomAccessFile raf2=new RandomAccessFile(file,"rw");
raf2.seek(0);
if(raf2.length()==0)
{
raf2.close();
throw new DAOException("No records");
}
while(raf2.getFilePointer()<raf2.length())
{
int v_code=Integer.parseInt(raf2.readLine());
if(v_code==code)
{
long new1=raf2.getFilePointer();
raf2.readLine();
raf2.readLine();
raf2.readLine();
raf2.readLine();
raf2.readLine();
StringBuffer sb=new StringBuffer();
while(raf2.getFilePointer()<raf2.length())
{
	sb.append(raf2.readLine()+"\n");
}
raf2.seek((new1-getNumberOfDigits(v_code)));
raf2.setLength(raf2.getFilePointer()+sb.length());
while(raf2.getFilePointer()<raf2.length())
{
	raf2.writeBytes(sb.toString());

}

raf2.close();
return;
}
raf2.readLine();
raf2.readLine();
raf2.readLine();
raf2.readLine();
raf2.readLine();
}
raf2.close();
}catch(IOException ioException)
{
TMLogger.addToLog("WorkDAO :public void remove(long code) throws DAOException -->"+ioException.getMessage()+" -->"+ioException.toString());
}



}
public List<WorkDTOInterface> getAll() throws DAOException
{
ArrayList<WorkDTOInterface> works=new ArrayList<WorkDTOInterface>();
try
{
File file=new File("work.data");
RandomAccessFile raf=new RandomAccessFile(file,"rw");
WorkDTOInterface workDTOInterface;
String status;
String vMemberId;
long code;
while(raf.getFilePointer()<raf.length())
{
code=Long.parseLong(raf.readLine());
vMemberId=raf.readLine();
workDTOInterface=new WorkDTO();
workDTOInterface.setCode(code);
workDTOInterface.setMemberId(vMemberId);
workDTOInterface.setMemberName(raf.readLine());
workDTOInterface.setFileName(raf.readLine());
try
{
workDTOInterface.setDate(sdf.parse(raf.readLine()));
}catch(java.text.ParseException parseException)
{
TMLogger.addToLog("WorkDAO : public List<WorkDTOInterface> getByMemberId(String memberId) throws DAOException -->"+parseException.getMessage()+" -->"+parseException.toString());
}
status=raf.readLine();
if(status.equals("SENT"))
{
workDTOInterface.setStatus(WorkDTOInterface.STATUS.SENT);
}
if(status.equals("NOT_SENT"))
{
workDTOInterface.setStatus(WorkDTOInterface.STATUS.NOT_SENT);
}
if(status.equals("RECEIVED"))
{
workDTOInterface.setStatus(WorkDTOInterface.STATUS.RECEIVED);
}
if(status.equals("ACCEPTED"))
{
workDTOInterface.setStatus(WorkDTOInterface.STATUS.ACCEPTED);
}
works.add(workDTOInterface);

}
raf.close();
if(works.size()==0)
{
throw new DAOException("No data");
}
}catch(IOException ioException)
{
TMLogger.addToLog("WorkDAO : public List<WorkDTOInterface> getByMemberId(String memberId) throws DAOException -->"+ioException.getMessage()+" -->"+ioException.toString());
}
return works;
}
public WorkDTOInterface getByCode(long code) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public List<WorkDTOInterface> getByMemberId(String memberId) throws DAOException
{
ArrayList<WorkDTOInterface> works=new ArrayList<WorkDTOInterface>();
try
{
File file=new File("work.data");
RandomAccessFile raf=new RandomAccessFile(file,"rw");
WorkDTOInterface workDTOInterface;
String status;
String vMemberId;
long code;
while(raf.getFilePointer()<raf.length())
{
code=Long.parseLong(raf.readLine());
vMemberId=raf.readLine();
if(memberId.equals(vMemberId))
{
workDTOInterface=new WorkDTO();
workDTOInterface.setCode(code);
workDTOInterface.setMemberId(vMemberId);
workDTOInterface.setMemberName(raf.readLine());
workDTOInterface.setFileName(raf.readLine());
try
{
workDTOInterface.setDate(sdf.parse(raf.readLine()));
}catch(java.text.ParseException parseException)
{
TMLogger.addToLog("WorkDAO : public List<WorkDTOInterface> getByMemberId(String memberId) throws DAOException -->"+parseException.getMessage()+" -->"+parseException.toString());
}
status=raf.readLine();
if(status.equals("SENT"))
{
workDTOInterface.setStatus(WorkDTOInterface.STATUS.SENT);
}
if(status.equals("NOT_SENT"))
{
workDTOInterface.setStatus(WorkDTOInterface.STATUS.NOT_SENT);
}
if(status.equals("RECEIVED"))
{
workDTOInterface.setStatus(WorkDTOInterface.STATUS.RECEIVED);
}
if(status.equals("ACCEPTED"))
{
workDTOInterface.setStatus(WorkDTOInterface.STATUS.ACCEPTED);
}
works.add(workDTOInterface);
}
else
{
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
}
}
raf.close();
if(works.size()==0)
{
throw new DAOException("No data");
}
}catch(IOException ioException)
{
TMLogger.addToLog("WorkDAO : public List<WorkDTOInterface> getByMemberId(String memberId) throws DAOException -->"+ioException.getMessage()+" -->"+ioException.toString());
}
return works;
}
public List<WorkDTOInterface> getByMemberIdAndStatus(String memberId,WorkDTOInterface.STATUS status) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public List<WorkDTOInterface> getByStatus(WorkDTOInterface.STATUS status) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public long getCount() throws DAOException
{
throw new DAOException("Not yet implemented");
}
public long getCountByMemberId(String memberId) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public long getCountByStatus(WorkDTOInterface.STATUS status) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public long getCountByMemberIdAndStatus(String memberId,WorkDTOInterface.STATUS status) throws DAOException
{
throw new DAOException("Not yet implemented");
}

private long getNumberOfDigits(long n)
{
	
	long x=0;
	while(n>0)
	{
		n=n/10;
		x++;
	}
	return x+1;
	
}
}