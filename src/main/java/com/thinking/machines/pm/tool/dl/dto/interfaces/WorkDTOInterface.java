

package com.thinking.machines.pm.tool.dl.dto.interfaces;
import java.io.*;
public interface WorkDTOInterface extends Serializable,Comparable<WorkDTOInterface>
{
public enum STATUS{SENT,NOT_SENT,RECEIVED,ACCEPTED};
public void setCode(long code);
public long getCode();
public void setMemberId(String memberId);
public String getMemberId();
public void setMemberName(String memberName);
public String getMemberName();
public void setFileName(String fileName);
public String getFileName();
public void setDate(java.util.Date date);
public java.util.Date getDate();
public void setStatus(STATUS status);
public STATUS getStatus();
public boolean equals(Object object);
public int compareTo(WorkDTOInterface workDTOInterface);
}