package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.utils.*;
import java.io.*;
import com.thinking.machines.pm.tool.dl.dto.interfaces.*;
import com.thinking.machines.pm.tool.dl.dao.interfaces.*;
import com.thinking.machines.pm.tool.dl.dao.*;
import com.thinking.machines.pm.tool.dl.dto.*;
import java.util.*;
import com.thinking.machines.pm.tool.network.client.*;
import static com.thinking.machines.pm.tool.network.client.Separators.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import com.thinking.machines.pm.tool.dl.dto.interfaces.*;
import com.thinking.machines.pm.tool.dl.dto.*;
import com.thinking.machines.pm.tool.ui.event.*;
import com.thinking.machines.pm.tool.model.*;
import com.thinking.machines.pm.tool.model.exceptions.*;
import com.thinking.machines.pm.tool.network.client.event.*;
import com.thinking.machines.pm.tool.network.client.*;
import com.thinking.machines.pm.tool.network.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class WorkCentrePanel extends JPanel implements ListSelectionListener,DropTargetListener,ActionListener,MemberAvailabilityStateChangedListener
{
private DropTarget dropTarget;
private MemberWorkModel memberWorkModel;
private JTable table;
private JScrollPane tableScrollPane;
private JLabel memberNameLabel;
private JLabel memberNameCaptionLabel;
private JButton distributeButton;
private JButton acceptButton;
private JButton removeButton;
private JPanel distributionPanel;
private String memberId;
private MemberAvailabilityChecker memberAvailabilityChecker;
private JList membersList;
private JScrollPane membersListScrollPane;
private Vector<String> membersVector;
private ClosePanelListener target;
private JButton closeButton;
private Project project;
private ArrayList<String> filesToExclude=new ArrayList();
public WorkCentrePanel(Project project)
{
	initComponents(project);
	addListeners();
}
public void initComponents(Project project)
{
this.project=project;
memberAvailabilityChecker=new MemberAvailabilityChecker(this,project.team);
closeButton=new JButton("Close");
setLayout(new BorderLayout());
JPanel p2=new JPanel();
p2.setLayout(new BorderLayout());
p2.add(closeButton,BorderLayout.EAST);
add(p2,BorderLayout.NORTH);
membersVector=new Vector<String>();
int x=0;
while(x<project.team.members.size())
{
membersVector.add(project.team.members.get(x).id);
x++;
}
membersList=new JList();
membersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
membersList.setListData(membersVector);
JPanel p3=new JPanel();
p3.setLayout(new BorderLayout());
membersListScrollPane=new JScrollPane(membersList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
membersListScrollPane.setPreferredSize(new Dimension(250,100));
membersListScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Online Members List"));
p3.add(membersListScrollPane,BorderLayout.CENTER);
add(p3,BorderLayout.EAST);
	distributionPanel=new JPanel();
memberNameLabel=new JLabel(" ");
memberNameCaptionLabel=new JLabel("Member : ");
distributeButton=new JButton("Distribute");
acceptButton=new JButton("Accept");
removeButton=new JButton("Remove");
distributeButton.setEnabled(false);
acceptButton.setEnabled(false);
removeButton.setEnabled(false);
JPanel p100=new JPanel();
FlowLayout f=new FlowLayout(FlowLayout.LEFT);
p100.setLayout(f);
p100.add(memberNameCaptionLabel);
p100.add(memberNameLabel);
JPanel p300=new JPanel();
p300.setLayout(new BorderLayout());
p300.add(p100,BorderLayout.CENTER);
JPanel p400=new JPanel();
p400.setLayout(new GridLayout(1,2));
p400.add(distributeButton);
p400.add(acceptButton);
p300.add(p400,BorderLayout.EAST);
distributionPanel.setLayout(new BorderLayout());
distributionPanel.add(p300,BorderLayout.NORTH);
JPanel p200=new JPanel();
p200.setLayout(new FlowLayout(FlowLayout.CENTER));
p200.add(removeButton);
distributionPanel.add(p200,BorderLayout.SOUTH);
memberWorkModel=new MemberWorkModel(memberId);
table=new JTable(memberWorkModel);
tableScrollPane=new
JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
table.setEnabled(false);
distributionPanel.add(tableScrollPane,BorderLayout.CENTER);
this.add(distributionPanel,BorderLayout.CENTER);
}
public void addListeners()
{
membersList.addListSelectionListener(this);
membersList.setCellRenderer(new CustomListCellRenderer());

closeButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(target!=null)
{
target.closeButtonClicked(WorkCentrePanel.this);
}
}
});

distributeButton.addActionListener(this);
acceptButton.addActionListener(this);
removeButton.addActionListener(this);
table.setFillsViewportHeight(true);	
dropTarget=new DropTarget(table,DnDConstants.ACTION_MOVE,this);

}

public void addClosePanelListener(ClosePanelListener target)
{
this.target=target;
}



public void memberStateChanged(String machine,int port,boolean isAvailable)
{
if(membersList==null) return;
// lot of diff code should really go here
int x=0;
Member member;
while(x<project.team.members.size())
{
member=project.team.members.get(x);
if(member.machine.equals(machine) && member.port==port)
{
member.isAvailable=isAvailable;
membersList.revalidate();
membersList.repaint();
System.out.println("Member : is available : "+member.name+" --->"+member.isAvailable+"("+member.port+")");
break;
}
x++;
}

}

public void valueChanged(ListSelectionEvent ev)
{
memberId=membersList.getSelectedValue().toString();
memberWorkModel.setMemberId(memberId);
memberWorkModel.fireTableDataChanged();
memberNameLabel.setText(memberId);
distributeButton.setEnabled(true);
acceptButton.setEnabled(true);
removeButton.setEnabled(true);
table.setEnabled(true);

}
public void dragEnter(java.awt.dnd.DropTargetDragEvent ev)
{
}
public void dragOver(java.awt.dnd.DropTargetDragEvent ev)
{
}
public void dropActionChanged(java.awt.dnd.DropTargetDragEvent ev)
{
}
public void dragExit(java.awt.dnd.DropTargetEvent ev)
{
}
public void drop(java.awt.dnd.DropTargetDropEvent ev)
{
System.out.println("Drop chala");
if(this.memberId==null || this.memberId.length()==0)
{
ev.acceptDrop(DnDConstants.ACTION_MOVE);
ev.dropComplete(true);
return;
}
try
{
DataFlavor dataFlavor=DataFlavor.stringFlavor;
Transferable transferable=ev.getTransferable();
if(ev.isDataFlavorSupported(dataFlavor))
{
String fileName=(String)transferable.getTransferData(dataFlavor);
ev.acceptDrop(DnDConstants.ACTION_MOVE);
ev.dropComplete(true);
System.out.println(fileName+" dropped");
WorkDTOInterface workDTOInterface=new WorkDTO();
workDTOInterface.setMemberId(memberId);
int x=0;
while(x<project.team.members.size())
{
if(project.team.members.get(x).id.equals(memberId))
{
break;
}
x++;
}
workDTOInterface.setMemberName(project.team.members.get(x).name);
workDTOInterface.setFileName(fileName);
workDTOInterface.setDate(new java.util.Date());
workDTOInterface.setStatus(WorkDTOInterface.STATUS.NOT_SENT);
try
{
memberWorkModel.add(workDTOInterface);
memberWorkModel.fireTableDataChanged();
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(this,modelException.getMessage());
}
}
}catch(Exception exception)
{
System.out.println(exception);
}
}

public boolean isMemberSelected()
{
return this.memberId!=null && this.memberId.length()>0;
}
public boolean fileExists(String fileName)
{
return memberWorkModel.fileExists(fileName);
}
public void actionPerformed(ActionEvent ev)
{
if(ev.getSource()==distributeButton)
{

	Member member=null;
int x=0;
while(x<project.team.members.size())
{
	System.out.println("inside while");
member=project.team.members.get(x);
if(member.id.equals(memberId))
{
break;
}
x++;
}

	WorkDTOInterface workDTOInterface;
	FileParcelIndexEntry fileParcelIndexEntry;
	FileParcel fileParcel=new FileParcel();


	x=0;
while(x<memberWorkModel.getRowCount())
{
workDTOInterface=memberWorkModel.getWorkDTOAt(x);
if(workDTOInterface.getStatus()==WorkDTOInterface.STATUS.NOT_SENT)
{
String filePath=project.folder+"\\"+workDTOInterface.getFileName().replace(".","\\");
String filePath1=project.projectHome+"\\"+"build\\temp\\"+workDTOInterface.getFileName().replace(".","\\")+".class";
fileParcelIndexEntry=new FileParcelIndexEntry();
filesToExclude.add(filePath1);
fileParcelIndexEntry.fileName=workDTOInterface.getFileName();
fileParcelIndexEntry.fileType=FileParcelIndexEntry.FileType.WORK;
fileParcelIndexEntry.file=new File(filePath+".java");
fileParcelIndexEntry.size=fileParcelIndexEntry.file.length();
try{
DataInputStream diStream = new DataInputStream(new FileInputStream(fileParcelIndexEntry.file));
                long len = (int) fileParcelIndexEntry.file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                        fileBytes.length - read)) >= 0) {
                    read = read + numRead;

                }

                fileParcelIndexEntry.fileData=fileBytes;
            
}catch(Exception e)
{
	System.out.println(e);
}

fileParcel.fileParcelIndexEntries.add(fileParcelIndexEntry);
workDTOInterface.setStatus(WorkDTOInterface.STATUS.SENT);

try
{
memberWorkModel.update(x,workDTOInterface);
}catch(Exception e)
{
	System.out.println(e);
}
memberWorkModel.fireTableDataChanged();
}
x++;
}
if(fileParcel.fileParcelIndexEntries.size()==0)
{
JOptionPane.showMessageDialog(null,"No files to distribute");
return;
}
	ServerConfiguration.SERVER_IP=member.machine;
ServerConfiguration.PORT_NUMBER=member.port;
String l=createJar();
System.out.println("l: "+l);

String jarName=(l.substring(0,l.indexOf("....")));
String absolouteJarPath=(l.substring(l.indexOf("....")+4));
System.out.println("jarName: "+jarName);
System.out.println("absolouteJarPath: "+absolouteJarPath);

try
{
	Thread.sleep(3000);
}catch(Exception w)
{
	System.out.println(w);
}
fileParcelIndexEntry=new FileParcelIndexEntry();
String folderStructure="*-*-*-*-*FolderLocation*-*-*-*-*"+"--"+project.title+"--"+project.folder+"--"+project.packageName+"--"+project.projectHome;
String response=AdminClient.sendRequest(folderStructure);
System.out.println(response);

fileParcelIndexEntry.fileName=jarName;
fileParcelIndexEntry.fileType=FileParcelIndexEntry.FileType.DEPENDENCY;
fileParcelIndexEntry.file=new File(absolouteJarPath);
fileParcelIndexEntry.size=fileParcelIndexEntry.file.length();
try{
DataInputStream diStream = new DataInputStream(new FileInputStream(fileParcelIndexEntry.file));
                long len = (int) fileParcelIndexEntry.file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                        fileBytes.length - read)) >= 0) {
                    read = read + numRead;

                }
                fileParcelIndexEntry.fileData=fileBytes;
            
}catch(Exception e)
{
	System.out.println(e);
}

fileParcel.fileParcelIndexEntries.add(fileParcelIndexEntry);

Postman.post(fileParcel,member);
}


if(ev.getSource()==removeButton)
{
	
int selectedRowIndex=table.getSelectedRow();
try
{
memberWorkModel.remove(selectedRowIndex);
memberWorkModel.fireTableDataChanged();

}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(this,modelException.getMessage());
}
	
}



}

class CustomListCellRenderer implements ListCellRenderer {
	 DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

public Component getListCellRendererComponent(JList list, Object value,int index, boolean isSelected, boolean cellHasFocus)
{

String memberName=(String)value;
JLabel label=new JLabel(memberName);

label.setFont(new Font("Verdana",Font.PLAIN,22));
if (isSelected) {
	  label.setBackground(Color.BLUE);
	  label.setForeground(Color.WHITE);
	  label.setOpaque(true);
    }
int x=0;
Member member;
while(x<project.team.members.size())
{
member=project.team.members.get(x);
if(member.name.substring(0,member.name.indexOf(" ")).equalsIgnoreCase(memberName))
{

if(member.isAvailable==true)
{

label.setForeground(Color.GREEN);
}
else
{
label.setForeground(Color.RED);
}
}
x++;
}
return label;

}	
	
}
private String createJar()
{

String jarName=project.projectHome.substring(project.projectHome.lastIndexOf("\\")+1)+".jar";
System.out.println("JarName: "+jarName);

String packageFolder=project.packageName.substring(0,project.packageName.indexOf("."));
System.out.println("Package Folder: "+packageFolder);
int i=0;

try{

	if(new File(project.projectHome+"\\build\\temp").exists()==true)
		Delete.delete(new File(project.projectHome+"\\build\\temp"));
		new File(project.projectHome+"\\build\\temp").mkdirs();
	Copy.copyFolder(new File(project.projectHome+"\\build\\classes\\main"),new File(project.projectHome+"\\build\\temp"));
String t;
while(i<filesToExclude.size())
{
	t=filesToExclude.get(i);
	System.out.println("t: "+t+ " -->Deleted");
	System.out.println(new File(t).delete());
	
	i++;
}
}catch(Exception e)
{
	System.out.println(e);
	
}

try
{
	
ProcessBuilder processBuilder=new ProcessBuilder("C:\\jdk1.8\\bin\\jar","-cf",jarName,packageFolder);
System.out.println("C:\\jdk1.8\\bin\\jar"+" -cf "+jarName+" "+packageFolder);
System.out.println("heyyyy");
processBuilder.directory(new File(project.projectHome+"\\build\\temp"));
/*File jarFile=new File(project.projectHome+"\\build\\temp\\"+jarName);
if(jarFile.exists())
{
jarFile.delete();
}*/
Process process = processBuilder.start();
System.out.println("Process completed");
}catch(Exception e)
{
System.out.println(e);
}
System.out.println(jarName+"...."+project.projectHome+"\\build\\temp\\"+jarName);
return jarName+"...."+project.projectHome+"\\build\\temp\\"+jarName;
}

}