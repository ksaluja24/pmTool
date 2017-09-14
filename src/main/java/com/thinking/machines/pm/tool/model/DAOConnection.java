package com.thinking.machines.tmide.database;
import java.sql.*;
public class DAOConnection
{
public static Connection getConnection() 
{
Connection connection=null;
try
{
Class.forName("com.mysql.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","root","kamal");
}catch(Exception exception)
{
System.out.println(exception);
}
return connection;
}
}