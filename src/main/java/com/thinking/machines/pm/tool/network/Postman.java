package com.thinking.machines.pm.tool.network;
import com.thinking.machines.pm.tool.network.client.*;
import com.thinking.machines.pm.tool.model.*;
import java.util.*;
import static com.thinking.machines.pm.tool.network.client.Separators.*;
import com.thinking.machines.pm.tool.dl.dto.interfaces.*;
import com.thinking.machines.pm.tool.dl.dto.*;
import java.io.*;
public class Postman 
{
	public Postman()
	{
		
		
	}
	public static void post(FileParcel fileParcel,Member member)
	{
	
FileParcelIndexEntry fileParcelIndexEntry;
String request;
request="FILE_PARCEL_INDEX"+ENTITY_SEPARATOR;
request+="ACCEPT_FILE_PARCEL_INDEX";
request+=OPERATION_SEPARATOR;
int x=0;
while(x<fileParcel.fileParcelIndexEntries.size())
{
fileParcelIndexEntry=fileParcel.fileParcelIndexEntries.get(x);
request+=fileParcelIndexEntry.fileName;
request+=PROPERTY_SEPARATOR;
request+=fileParcelIndexEntry.fileType.name();
request+=PROPERTY_SEPARATOR;
request+=fileParcelIndexEntry.size;
request+=PROPERTY_SEPARATOR;
request+=new String(fileParcelIndexEntry.fileData);
x++;
if(x<fileParcel.fileParcelIndexEntries.size())
{
request+=OBJECT_SEPARATOR;
}
}

ServerConfiguration.SERVER_IP=member.machine;
ServerConfiguration.PORT_NUMBER=member.port;
String response=AdminClient.sendRequest(request);
System.out.println(response);
	
		
	}
	
	
}
