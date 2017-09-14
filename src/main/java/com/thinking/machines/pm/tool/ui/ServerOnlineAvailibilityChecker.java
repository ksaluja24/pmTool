package com.thinking.machines.pm.tool.ui;
import java.net.*;
public class ServerOnlineAvailibilityChecker extends Thread
{
public ServerOnlineAvailibilityChecker()
{

start();
}
public void run()
{

Socket socket;

while(true)
{

try
{
socket=new Socket("localhost",5000);
Login.updateServerStatus(true);

socket.close();
}catch(Exception exception)
{
    Login.updateServerStatus(false); 
}

try
{
Thread.sleep(1000);
}catch(InterruptedException interruptedException)
{
}
} // infinite loop
} // run
} // class