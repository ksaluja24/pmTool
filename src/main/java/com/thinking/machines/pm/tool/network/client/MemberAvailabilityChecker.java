package com.thinking.machines.pm.tool.network.client;
import com.thinking.machines.pm.tool.network.client.event.*;
import com.thinking.machines.pm.tool.model.*;
import java.net.*;
public class MemberAvailabilityChecker extends Thread
{
private MemberAvailabilityStateChangedListener memberAvailabilityStateChangedListener;
private Team team;
public MemberAvailabilityChecker(MemberAvailabilityStateChangedListener memberAvailabilityStateChangedListener,Team team)
{
this.memberAvailabilityStateChangedListener=memberAvailabilityStateChangedListener;
this.team=team;
start();
}
public void run()
{
boolean isAvailable;
Socket socket;
String machine;
int port;
Member member;
int x;
while(true)
{
x=0;
while(x<team.members.size())
{
member=team.members.get(x);
machine=member.machine;
port=member.port;
try
{
socket=new Socket(machine,port);
socket.close();
isAvailable=true;
}catch(Exception exception)
{
isAvailable=false;
}
if(member.isAvailable!=isAvailable)
{
// state changed
memberAvailabilityStateChangedListener.memberStateChanged(member.machine,member.port,isAvailable);
}
x++;
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