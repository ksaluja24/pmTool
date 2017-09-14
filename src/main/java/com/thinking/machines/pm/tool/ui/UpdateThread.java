package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.network.client.event.*;
import com.thinking.machines.pm.tool.model.*;
import java.net.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class UpdateThread extends Thread
{
 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
public UpdateThread()
{
start();
}
public void run()
{
while(true)
{
PMTool.updateDateAndTime(dateFormat.format(new Date()).toString());
try
{
Thread.sleep(1000);
}catch(InterruptedException interruptedException)
{
}
} // infinite loop
} // run
} // class