package com.thinking.machines.pm.tool.dl.dao.interfaces;
import com.thinking.machines.pm.tool.dl.dto.interfaces.*;
import com.thinking.machines.pm.tool.dl.exceptions.*;
import java.util.*;
public interface WorkDAOInterface
{
public void add(WorkDTOInterface workDTOInterface) throws DAOException;
public void update(WorkDTOInterface workDTOInterface) throws DAOException;
public void remove(long code) throws DAOException;
public List<WorkDTOInterface> getAll() throws DAOException;
public WorkDTOInterface getByCode(long code) throws DAOException;
public List<WorkDTOInterface> getByMemberId(String memberId) throws DAOException;
public List<WorkDTOInterface> getByMemberIdAndStatus(String memberId,WorkDTOInterface.STATUS status) throws DAOException;
public List<WorkDTOInterface> getByStatus(WorkDTOInterface.STATUS status) throws DAOException;
public long getCount() throws DAOException;
public long getCountByMemberId(String memberId) throws DAOException;
public long getCountByStatus(WorkDTOInterface.STATUS status) throws DAOException;
public long getCountByMemberIdAndStatus(String memberId,WorkDTOInterface.STATUS status)throws DAOException;
}