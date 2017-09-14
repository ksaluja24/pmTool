package com.thinking.machines.pm.tool.network.client.event;
public interface MemberAvailabilityStateChangedListener
{
public void memberStateChanged(String machine,int port,boolean state);
}