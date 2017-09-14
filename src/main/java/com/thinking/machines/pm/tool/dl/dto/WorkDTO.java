package com.thinking.machines.pm.tool.dl.dto;
import com.thinking.machines.pm.tool.dl.dto.interfaces.*;
public class WorkDTO implements WorkDTOInterface
{
private long code;
private String memberId;
private String memberName;
private String fileName;
private java.util.Date date;
private WorkDTOInterface.STATUS status;
public void setCode(long code)
{
this.code=code;
}
public long getCode()
{
return this.code;
}
public void setMemberId(String memberId)
{
this.memberId=memberId;
}
public String getMemberId()
{
return this.memberId;
}
public void setMemberName(String memberName)
{
this.memberName=memberName;
}
public String getMemberName()
{
return this.memberName;
}
public void setFileName(String fileName)
{
this.fileName=fileName;
}
public String getFileName()
{
return this.fileName;
}
public void setDate(java.util.Date date)
{
this.date=date;
}
public java.util.Date getDate()
{
return this.date;
}
public void setStatus(WorkDTOInterface.STATUS status)
{
this.status=status;
}
public STATUS getStatus()
{
return this.status;
}
public boolean equals(Object object)
{
if(!(object instanceof WorkDTOInterface)) return false;
return this.memberName.equals(((WorkDTOInterface)object).getMemberName());
}
public int compareTo(WorkDTOInterface workDTOInterface)
{
return this.memberName.compareTo(workDTOInterface.getMemberName());
}
}