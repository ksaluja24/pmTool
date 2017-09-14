package com.thinking.machines.pm.tool.ui;
import java.io.*;
import java.net.*;
import java.lang.reflect.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import com.thinking.machines.pm.tool.compiler.*;
import java.util.*;
import java.io.*;
import com.thinking.machines.pm.tool.ui.event.*;
import com.thinking.machines.pm.tool.model.*;
import com.thinking.machines.pm.tool.controls.*;
import com.thinking.machines.pm.tool.controls.interfaces.*;
import javax.swing.tree.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class PMTool extends JFrame implements DTOInterfaceListener,ClosePanelListener,DAOInterfaceListener,DragGestureListener,DragSourceListener
{
private boolean isWorkCentrePanelVisible;
private DragSource dragSource;
private JTabbedPane tabbedPane;
private WorkCentrePanel workCentrePanel;
private CompilerResultPanel compilerResultPanel;
private JToolBar toolbar;
private JButton compileButton;
private JButton workCentreButton;
private ProjectCompiler projectCompiler;
private ArrayList<String> dtoInterfaceNames=new ArrayList<String>();
private ArrayList<String> dtoImplementationNames=new ArrayList<String>();
private ArrayList<String> daoInterfaceNames=new ArrayList<String>();
private ArrayList<String> daoImplementationNames=new ArrayList<String>();
private Container container;
private Project project;
private JTree projectTree;
private JScrollPane treeScrollPane;
private DefaultMutableTreeNode rootNode;
private DefaultMutableTreeNode dataLayerNode;
private DefaultMutableTreeNode dtoNode;
private DefaultMutableTreeNode dtoInterfaceNode;
private DefaultMutableTreeNode dtoImplementationNode;
private DefaultMutableTreeNode daoNode;
private DefaultMutableTreeNode daoInterfaceNode;
private DefaultMutableTreeNode daoImplementationNode;
private DTOInterfaceCreationDialog dtoInterfaceCreationDialog;
private EditorPanel editorPanel;
private static JLabel dateAndTimeLabel=new JLabel();
public PMTool(Project project)
{
this.project=project;
this.setTitle("PMTool - ("+project.title+")");
projectCompiler=new ProjectCompiler(project);
initComponents();
addListeners();
UpdateThread ut=new UpdateThread();
}
public void initComponents()
{

tabbedPane=new JTabbedPane();
toolbar = new JToolBar();
toolbar.setRollover(true);
compileButton= new JButton("Compile");
workCentreButton=new JButton("WorkCentre");
toolbar.add(compileButton);
toolbar.add(workCentreButton);
toolbar.addSeparator();
rootNode=new DefaultMutableTreeNode("Project");
dataLayerNode=new DefaultMutableTreeNode("Data Layer");
dtoNode=new DefaultMutableTreeNode("DTO");
dtoInterfaceNode=new DefaultMutableTreeNode("Interface");
dtoImplementationNode=new DefaultMutableTreeNode("Implementation");
dtoNode.add(dtoInterfaceNode);
dtoNode.add(dtoImplementationNode);
daoNode=new DefaultMutableTreeNode("DAO");
daoInterfaceNode=new DefaultMutableTreeNode("Interface");
daoImplementationNode=new DefaultMutableTreeNode("Implementation");
daoNode.add(daoInterfaceNode);
daoNode.add(daoImplementationNode);
dataLayerNode.add(dtoNode);
dataLayerNode.add(daoNode);
rootNode.add(dataLayerNode);
projectTree=new JTree(rootNode);
populateTree();
treeScrollPane=new JScrollPane(projectTree,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
treeScrollPane.setPreferredSize(new Dimension(350,50));
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(toolbar,BorderLayout.NORTH);
container.add(treeScrollPane,BorderLayout.WEST);
container.add(tabbedPane,BorderLayout.CENTER);

JPanel px=new JPanel();
FlowLayout fl=new FlowLayout(FlowLayout.RIGHT);
px.setLayout(fl);
px.add(dateAndTimeLabel);
//JPanel pz=new JPanel();
///pz.setLayout(new BorderLayout());
//pz.add(px,BorderLayout.SOUTH);
//pz.add(tabbedPane,BorderLayout.NORTH);
container.add(px,BorderLayout.SOUTH);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize(dimension.width-50,dimension.height-75);
setLocation(25,25);
setVisible(true);
}
//drag drop events for project tree
public void dragGestureRecognized(DragGestureEvent e)
{
if(isWorkCentrePanelVisible==false) return;
if(workCentrePanel.isMemberSelected()==false) return;
TreePath path=projectTree.getSelectionPath();
DefaultMutableTreeNode draggedNode=(DefaultMutableTreeNode)path.getLastPathComponent();
if(draggedNode!=null && draggedNode.isLeaf())
{
if(draggedNode.getParent()==dtoInterfaceNode || draggedNode.getParent()==dtoImplementationNode || draggedNode.getParent()==daoInterfaceNode || draggedNode.getParent()==daoImplementationNode)
{
String nodeName=(String)draggedNode.getUserObject();
if(workCentrePanel.fileExists(nodeName)) return;
if(draggedNode.getParent()==dtoInterfaceNode)
{
nodeName=project.packageName+".dl.dto.interfaces."+nodeName;
}
if(draggedNode.getParent()==dtoImplementationNode)
{
nodeName=project.packageName+".dl.dto."+nodeName;
}
if(draggedNode.getParent()==daoInterfaceNode)
{
nodeName=project.packageName+".dl.dao.interfaces."+nodeName;
}
if(draggedNode.getParent()==daoImplementationNode)
{
nodeName=project.packageName+".dl.dao."+nodeName;
}
Transferable transferable=new StringSelection(nodeName);
Cursor cursor=DragSource.DefaultMoveDrop;
dragSource.startDrag(e,cursor,transferable,this);
System.out.println(nodeName);
}
}
}
public void dragEnter(java.awt.dnd.DragSourceDragEvent ev)
{
}
public void dragOver(java.awt.dnd.DragSourceDragEvent ev)
{
}
public void dropActionChanged(java.awt.dnd.DragSourceDragEvent ev)
{
}
public void dragExit(java.awt.dnd.DragSourceEvent ev)
{
}
public void dragDropEnd(java.awt.dnd.DragSourceDropEvent ev)
{
}
public void addListeners()
{
dragSource=new DragSource();
dragSource.createDefaultDragGestureRecognizer(projectTree,DnDConstants.ACTION_MOVE,this);
this.workCentreButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(project.team==null || project.team.members.size()==0)
{
JOptionPane.showMessageDialog(PMTool.this,"Members data not set in members.data");
return;
}
container.remove(tabbedPane);
if(workCentrePanel==null)
{
workCentrePanel=new WorkCentrePanel(project);
workCentrePanel.addClosePanelListener(PMTool.this);
}
container.add(workCentrePanel,BorderLayout.CENTER);
container.revalidate();
container.repaint();
isWorkCentrePanelVisible=true;
}
});
this.compileButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
compileProject();
}
});



this.addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent ev)
{
// later on add a dialog that appears for 10 seconds
// with some bye,bye, thank you messages
System.exit(0);
}
});
this.projectTree.addMouseListener(new MouseAdapter(){
public void mousePressed(MouseEvent mouseEvent)
{
	
int clickCount=mouseEvent.getClickCount();
TreePath treePath=projectTree.getPathForLocation(mouseEvent.getX(),mouseEvent.getY());
if(treePath==null) return;
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
if(clickCount==2)
{
	
TreePath path=projectTree.getSelectionPath();
DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode)path.getLastPathComponent();
if(selectedNode!=null && selectedNode.isLeaf())
{
if(selectedNode.getParent()==dtoInterfaceNode || selectedNode.getParent()==dtoImplementationNode || selectedNode.getParent()==daoInterfaceNode || selectedNode.getParent()==daoImplementationNode)
{
String nodeName=(String)selectedNode.getUserObject();
String nodePath="";
if(selectedNode.getParent()==dtoInterfaceNode)
{
nodePath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dto\\interfaces\\"+nodeName+".java";

}
if(selectedNode.getParent()==dtoImplementationNode)
{
nodePath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dto\\"+nodeName+".java";
}
if(selectedNode.getParent()==daoInterfaceNode)
{
nodePath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dao\\interfaces\\"+nodeName+".java";
}
if(selectedNode.getParent()==daoImplementationNode)
{
nodePath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dao\\"+nodeName+".java";
}
createTab(nodeName,nodePath);

	
}
}
}

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

if(mouseEvent.getButton()==MouseEvent.BUTTON3 &&
((DefaultMutableTreeNode)treePath.getLastPathComponent())==dtoInterfaceNode)
{
projectTree.setSelectionPath(treePath);
JPopupMenu dtoPopupMenu=new JPopupMenu();
JMenuItem newDTOMenuItem=new JMenuItem("New");
newDTOMenuItem.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
String tablesToIgnore[]=new String[dtoInterfaceNames.size()];
String dtoInterfaceName;
int x=0;
int y;
while(x<dtoInterfaceNames.size())
{
dtoInterfaceName=dtoInterfaceNames.get(x);
y=0;
while(y<project.database.tables.size())
{
if((project.database.tables.get(y).getJavaName()+"DTOInterface").equals(dtoInterfaceName))
{
tablesToIgnore[x]=project.database.tables.get(y).getName();
break;
}
y++;
}
x++;
}
Table table=TableSelectionDialog.showTableSelectionDialog(project,PMTool.this,tablesToIgnore);
if(table!=null)
{

dtoInterfaceCreationDialog=new DTOInterfaceCreationDialog(project,table);	

dtoInterfaceCreationDialog.addDTOInterfaceListener(PMTool.this);
}
else
{
JOptionPane.showMessageDialog(PMTool.this,"Cannot proceed without selecting a table");
}
}
});
dtoPopupMenu.add(newDTOMenuItem);
Rectangle pathBounds=projectTree.getUI().getPathBounds(projectTree,treePath);
if(pathBounds!=null && pathBounds.contains(mouseEvent.getX(),mouseEvent.getY()))
{
dtoPopupMenu.show(projectTree,pathBounds.x+pathBounds.width-5,pathBounds.y+pathBounds.height-5);
}
} // if condition for rightclick on dtoInterfaceNode ends
if(mouseEvent.getButton()==MouseEvent.BUTTON3 &&
((DefaultMutableTreeNode)treePath.getLastPathComponent()).getParent()==dtoInterfaceNode)
{
DefaultMutableTreeNode
selectedInterfaceNode=(DefaultMutableTreeNode)treePath.getLastPathComponent();
String interfaceName=selectedInterfaceNode.toString();
String implementationName=interfaceName.substring(0,interfaceName.length()-9);
boolean dtoImplementationExists=false;
int x=0;
while(x<dtoImplementationNames.size())
{
if(dtoImplementationNames.get(x).equals(implementationName))
{
dtoImplementationExists=true;
break;
}
x++;
}
// one more loop required for daoInterfaceExists
boolean daoInterfaceExists=false;
String daoInterfaceName=interfaceName.substring(0,interfaceName.length()-12)+"DAOInterface";
x=0;
while(x<daoInterfaceNames.size())
{
if(daoInterfaceNames.get(x).equals(daoInterfaceName))
{
daoInterfaceExists=true;
break;
}
x++;
}
projectTree.setSelectionPath(treePath);
JPopupMenu dtoPopupMenu=new JPopupMenu();
if(dtoImplementationExists==false)
{
JMenuItem newDTOMenuItem1=new JMenuItem("Create implementation");
newDTOMenuItem1.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
try
{
createDTOImplementation(interfaceName,implementationName);
}catch(RuntimeException runtimeException)
{
JOptionPane.showMessageDialog(PMTool.this,runtimeException.getMessage());
}
}
});
dtoPopupMenu.add(newDTOMenuItem1);
}
if(daoInterfaceExists==false)
{
JMenuItem newDTOMenuItem2=new JMenuItem("Create DAO interface");
newDTOMenuItem2.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
try
{
String dtoInterfaceName=interfaceName;
Table table=null;
int x=0;
while(x<project.database.tables.size())
{
if((project.database.tables.get(x).getJavaName()+"DTOInterface").equals(interfaceName))
{
table=project.database.tables.get(x);
break;
}
x++;
}
String daoInterfaceName=table.getJavaName()+"DAOInterface";
DAOInterfaceCreationDialog daoInterfaceCreationDialog;
daoInterfaceCreationDialog=new
DAOInterfaceCreationDialog(project,dtoInterfaceName,daoInterfaceName,table);
daoInterfaceCreationDialog.addDAOInterfaceListener(PMTool.this);
}catch(RuntimeException runtimeException)
{
JOptionPane.showMessageDialog(PMTool.this,runtimeException.getMessage()
+runtimeException.toString());
}
}
});
dtoPopupMenu.add(newDTOMenuItem2);
}
Rectangle pathBounds=projectTree.getUI().getPathBounds(projectTree,treePath);
if(pathBounds!=null && pathBounds.contains(mouseEvent.getX(),mouseEvent.getY()))
{
dtoPopupMenu.show(projectTree,pathBounds.x+pathBounds.width-5,pathBounds.y+pathBounds.height-5);
}
} // if condition for right click on a DtoInterface.java file ends
if(mouseEvent.getButton()==MouseEvent.BUTTON3 &&
((DefaultMutableTreeNode)treePath.getLastPathComponent()).getParent()==daoInterfaceNode)
{
DefaultMutableTreeNode
selectedInterfaceNode=(DefaultMutableTreeNode)treePath.getLastPathComponent();
String interfaceName=selectedInterfaceNode.toString();
String implementationName=interfaceName.substring(0,interfaceName.length()-9);
boolean daoImplementationExists=false;
int x=0;
while(x<daoImplementationNames.size())
{
if(daoImplementationNames.get(x).equals(implementationName))
{
daoImplementationExists=true;
return;
}
x++;
}
projectTree.setSelectionPath(treePath);
JPopupMenu dtoPopupMenu=new JPopupMenu();
JMenuItem newDTOMenuItem1=new JMenuItem("Create implementation");
newDTOMenuItem1.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
try
{
createDAOImplementation(interfaceName,implementationName);
}catch(RuntimeException runtimeException)
{
JOptionPane.showMessageDialog(PMTool.this,runtimeException.getMessage()
+runtimeException.toString());
}
}
});
dtoPopupMenu.add(newDTOMenuItem1);
Rectangle pathBounds=projectTree.getUI().getPathBounds(projectTree,treePath);
if(pathBounds!=null && pathBounds.contains(mouseEvent.getX(),mouseEvent.getY()))
{
dtoPopupMenu.show(projectTree,pathBounds.x+pathBounds.width-
5,pathBounds.y+pathBounds.height-5);
}
} // if condition for right click on a DaoInterface.java file ends

}
});
}
public void daoInterfaceCreated(String daoInterfaceName)
{
DefaultMutableTreeNode node=new DefaultMutableTreeNode(daoInterfaceName);
daoInterfaceNode.add(node);
projectTree.expandPath(new TreePath(daoInterfaceNode.getPath()));
daoInterfaceNames.add(daoInterfaceName);
reloadDAOInterfaceNode();
}
public void dtoInterfaceCreated(String dtoInterfaceName)
{
DefaultMutableTreeNode node=new DefaultMutableTreeNode(dtoInterfaceName);
dtoInterfaceNode.add(node);
projectTree.expandPath(new TreePath(dtoInterfaceNode.getPath()));
dtoInterfaceNames.add(dtoInterfaceName);
reloadDTOInterfaceNode();
}
public void reloadDTOInterfaceNode()
{
DefaultTreeModel defaultTreeModel=(DefaultTreeModel)projectTree.getModel();
defaultTreeModel.reload(dtoInterfaceNode);
}
public void reloadDTOImplementationNode()
{
DefaultTreeModel defaultTreeModel=(DefaultTreeModel)projectTree.getModel();
defaultTreeModel.reload(dtoImplementationNode);
}
public void reloadDAOInterfaceNode()
{
DefaultTreeModel defaultTreeModel=(DefaultTreeModel)projectTree.getModel();
defaultTreeModel.reload(daoInterfaceNode);
}
public void reloadDAOImplementationNode()
{
DefaultTreeModel defaultTreeModel=(DefaultTreeModel)projectTree.getModel();
defaultTreeModel.reload(daoImplementationNode);
}
public void populateTree()
{
try

{
// populating dto interfaces
DefaultMutableTreeNode node;
String dtoInterfaceFolderPath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dto\\interfaces\\";
File dtoInterfaceFolder=new File(dtoInterfaceFolderPath);
if(dtoInterfaceFolder.exists() && dtoInterfaceFolder.isDirectory())
{
String dtoInterfaceFileNames[]=dtoInterfaceFolder.list();
String dtoInterfaceName;
int x=0;
while(x<dtoInterfaceFileNames.length)
{
if(dtoInterfaceFileNames[x].toLowerCase().endsWith(".java"))
{
dtoInterfaceName=dtoInterfaceFileNames[x].substring(0,dtoInterfaceFileNames[x].length()-5);
node=new DefaultMutableTreeNode(dtoInterfaceName);
dtoInterfaceNode.add(node);
dtoInterfaceNames.add(dtoInterfaceName);
}
x++;
}
reloadDTOInterfaceNode();
}
// populating dao interfaces
String daoInterfaceFolderPath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dao\\interfaces\\";
File daoInterfaceFolder=new File(daoInterfaceFolderPath);
if(daoInterfaceFolder.exists() && daoInterfaceFolder.isDirectory())
{
String daoInterfaceFileNames[]=daoInterfaceFolder.list();
String daoInterfaceName;
int x=0;
while(x<daoInterfaceFileNames.length)
{
if(daoInterfaceFileNames[x].toLowerCase().endsWith(".java"))
{
daoInterfaceName=daoInterfaceFileNames[x].substring(0,daoInterfaceFileNames[x].length()-5);
node=new DefaultMutableTreeNode(daoInterfaceName);
daoInterfaceNode.add(node);
daoInterfaceNames.add(daoInterfaceName);
}
x++;
}
reloadDAOInterfaceNode();
}

// populating dto implementations
String dtoImplementationFolderPath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dto\\";
File dtoImplementationFolder=new File(dtoImplementationFolderPath);
if(dtoImplementationFolder.exists() && dtoImplementationFolder.isDirectory())
{
String dtoImplementationFileNames[]=dtoImplementationFolder.list();
String dtoImplementationName;
int x=0;
while(x<dtoImplementationFileNames.length)
{
if(dtoImplementationFileNames[x].toLowerCase().endsWith(".java"))
{
dtoImplementationName=dtoImplementationFileNames[x].substring(0,dtoImplementationFileNames[x].length()-5);
node=new DefaultMutableTreeNode(dtoImplementationName);
dtoImplementationNode.add(node);
dtoImplementationNames.add(dtoImplementationName);
}
x++;
}
reloadDTOImplementationNode();
}
// populating dao implementations
String daoImplementationFolderPath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dao\\";
File daoImplementationFolder=new File(daoImplementationFolderPath);
if(daoImplementationFolder.exists() && daoImplementationFolder.isDirectory())
{
String daoImplementationFileNames[]=daoImplementationFolder.list();
String daoImplementationName;
int x=0;
while(x<daoImplementationFileNames.length)
{
if(daoImplementationFileNames[x].toLowerCase().endsWith(".java"))
{
daoImplementationName=daoImplementationFileNames[x].substring(0,daoImplementationFileNames[x].length()-5);
node=new DefaultMutableTreeNode(daoImplementationName);
daoImplementationNode.add(node);
daoImplementationNames.add(daoImplementationName);
}
x++;
}

reloadDTOImplementationNode();
}
}catch(Exception exception)
{
System.out.println(exception); // remove after testing
}
}
public void closeButtonClicked(JPanel sourcePanel)
{
if(sourcePanel==workCentrePanel)
{
container.remove(workCentrePanel);
container.add(tabbedPane,BorderLayout.CENTER);
container.revalidate();
container.repaint();
isWorkCentrePanelVisible=false;
}
else
{
tabbedPane.remove(sourcePanel);
}
}
public void createDAOImplementation(String interfaceName,String implementationName)
{
String interfaceClassFile=project.packageName+".dl.dao.interfaces."+interfaceName;
String implementationJavaFileName=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dao\\"+implementationName+".java";
Class c=null;
ClassLoader cl=null;
try
{	String jarName=project.projectHome.substring(project.projectHome.lastIndexOf("\\")+1)+".jar";
String jarPath=project.projectHome+"\\build\\libs\\"+jarName;
File file  = new File(jarPath);
URL url = file.toURI().toURL();  
URL[] urls = new URL[]{url};
cl = new URLClassLoader(urls);
c = cl.loadClass(interfaceClassFile);

//c=Class.forName(interfaceClassFile);
}catch(ClassNotFoundException exception)
{	
String message=null;
message="Seems you have not compiled your project";
message+="\n";
message+="Kindly Compile the project";

JOptionPane.showMessageDialog(this,message);
return;
    /*int response = JOptionPane.showConfirmDialog(this, message, "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (response == JOptionPane.NO_OPTION) {
      JOptionPane.showMessageDialog(this,"Cannot Proceed without compiling");
    } else if (response == JOptionPane.YES_OPTION) {
		compileProject();
		try
		{
		c = cl.loadClass(interfaceClassFile);
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
    } else if (response == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(this,"Cannot Proceed without compiling");
    }
*/
//throw new RuntimeException("DAO Interface class : "+interfaceClassFile);
}catch(Exception exception)
{
throw new RuntimeException("DAO Interface class : "+interfaceClassFile);
}
try
{
File f=new File(implementationJavaFileName);
if(f.exists())
{
throw new RuntimeException(implementationJavaFileName+" exists.");
}
System.out.println(" implementation java : "+implementationJavaFileName);
System.out.println("implementation "+implementationJavaFileName);
createDAOImplementationFile(c,implementationName,implementationJavaFileName);
DefaultMutableTreeNode defaultMutableTreeNode=new DefaultMutableTreeNode(implementationName);
daoImplementationNode.add(defaultMutableTreeNode);
daoImplementationNames.add(implementationName);
reloadDAOImplementationNode();
}catch(Exception exception)
{
throw new RuntimeException("Cannot create implementation file : "+implementationJavaFileName);
}
}
private void createDAOImplementationFile(Class c,String classToGenerate,String javaFileName)
{
try
{
File file=new File(javaFileName);
if(file.exists())
{
throw new RuntimeException(javaFileName+" exists.");
}
RandomAccessFile raf=new RandomAccessFile(file,"rw");
System.out.println("raf");
String line;
line="package "+project.packageName+".dl.dao;";
raf.writeBytes(line+"\r\n");
line="import java.util.*;";
raf.writeBytes(line+"\r\n");
line="import "+project.packageName+".dl.exceptions.*;";
raf.writeBytes(line+"\r\n");
line="import "+project.packageName+".dl.connection.*;";
raf.writeBytes(line+"\r\n");
line="import "+project.packageName+".dl.dto.interfaces.*;";
raf.writeBytes(line+"\r\n");
line="import "+project.packageName+".dl.dao.interfaces.*;";
raf.writeBytes(line+"\r\n");
System.out.println("dl-dao--interfaces");
System.out.println(classToGenerate);
line="class "+classToGenerate+" implements "+c.getSimpleName();
System.out.println("after class to generate");
raf.writeBytes(line+"\r\n");
line="{";
raf.writeBytes(line+"\r\n");
Method [] methods=c.getDeclaredMethods();
Method method;
String methodName;
String returnType;
String genericReturnType;
String parameterTypeName;
String returnTypeName;
Class parameterTypes[];
java.lang.reflect.Type genericParameterTypes[];
int y;
int x=0;
while(x<methods.length)
{

method=methods[x];
methodName=method.getName();
returnType=method.getReturnType().getName();
genericReturnType=method.getGenericReturnType().getTypeName();
parameterTypes=method.getParameterTypes();
genericParameterTypes=method.getGenericParameterTypes();
if(returnType.equals(genericReturnType)==false)
{
returnTypeName=genericReturnType;
}
else
{
returnTypeName=returnType;
}
if(returnTypeName.startsWith(project.packageName+".dl.dto.interfaces."))
{
returnTypeName=returnTypeName.substring((project.packageName+".dl.dto.interfaces.").length());
}
if(returnTypeName.startsWith(project.packageName+".dl.dao.interfaces."))
{
returnTypeName=returnTypeName.substring((project.packageName+".dl.dao.interfaces.").length());
}
line="public "+returnTypeName+" "+methodName+"(";
y=0;
while(y<parameterTypes.length)
{
if(parameterTypes[y].getName().equals(genericParameterTypes[y].getTypeName())==false)
{
parameterTypeName=genericParameterTypes[y].getTypeName();
}
else
{
parameterTypeName=parameterTypes[y].getName();
}
if(parameterTypeName.startsWith(project.packageName+".dl.dto.interfaces."))
{
parameterTypeName=parameterTypeName.substring((project.packageName+".dl.dto.interfaces.").length());
}
if(parameterTypeName.startsWith(project.packageName+".dl.dao.interfaces."))
{
parameterTypeName=parameterTypeName.substring((project.packageName+".dl.dao.interfaces.").length());
}
line=line+parameterTypeName+" arg"+(y+1);
y++;

if(y<parameterTypes.length)
{
line=line+",";
}
}
line=line+") throws DAOException";
raf.writeBytes(line+"\r\n");
line="{";
raf.writeBytes(line+"\r\n");
line="throw new DAOException(\"Not yet implemented\");";
raf.writeBytes(line+"\r\n");
line="}";
raf.writeBytes(line+"\r\n");
x++;
}
line="}";
raf.writeBytes(line+"\r\n");
raf.close();
}catch(IOException ioException)
{
throw new RuntimeException("Cannot create implementation file1 : "+javaFileName);
}
}
public void createDTOImplementation(String interfaceName,String implementationName)
{
String interfaceClassFile=project.packageName+".dl.dto.interfaces."+interfaceName;
String implementationJavaFileName=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dto\\"+implementationName+".java";
Class c=null;
//..
try
{
	String jarName=project.projectHome.substring(project.projectHome.lastIndexOf("\\")+1)+".jar";
String jarPath=project.projectHome+"\\"+"build\\libs\\"+jarName;
File file  = new File(jarPath);
URL url = file.toURI().toURL();  
URL[] urls = new URL[]{url};
ClassLoader cl = new URLClassLoader(urls);
c = cl.loadClass(interfaceClassFile);

}catch(ClassNotFoundException exception)
{

String message=null;
message="Seems you have not compiled your project";
message+="\n";
message+="Should we complile it for you...";
    int response = JOptionPane.showConfirmDialog(this, message, "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (response == JOptionPane.NO_OPTION) {
      JOptionPane.showMessageDialog(this,"Cannot Proceed without compiling");
    } else if (response == JOptionPane.YES_OPTION) {
		compileProject();
		
    } else if (response == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(this,"Cannot Proceed without compiling");
    }

//throw new RuntimeException("DTO Interface class : "+interfaceClassFile);
}
catch(Exception exception)
{
throw new RuntimeException(exception.getMessage());
}
try
{
File f=new File(implementationJavaFileName);
if(f.exists())
{
throw new RuntimeException(implementationJavaFileName+" exists.");

}
createDTOImplementationFile(c,implementationName,implementationJavaFileName);
DefaultMutableTreeNode defaultMutableTreeNode=new
DefaultMutableTreeNode(implementationName);
dtoImplementationNode.add(defaultMutableTreeNode);
dtoImplementationNames.add(implementationName);
reloadDTOImplementationNode();
}catch(IOException ioException)
{
throw new RuntimeException("Cannot create implementation file2 : "+implementationJavaFileName);
}
}
public void createDTOImplementationFile(Class c,String classToGenerate,String javaFileName) throws IOException
{
// parameter (c) contains the ref of object with definition of DTOInterface whose Implementation needs to be created
try
{
HashMap<String,String> propertyNames=getPropertiesOfClass(c);
HashMap<String,Method> propertySetters=getPropertySettersOfClass(c);
HashMap<String,Method> propertyGetters=getPropertyGettersOfClass(c,propertySetters);
ArrayList<Method> otherMethods=getOtherMethodsOfClass(c,propertySetters);
Method method;
String propertyName,propertyType;
Iterator iterator = propertyNames.entrySet().iterator();
while (iterator.hasNext())
{
Map.Entry entry = (Map.Entry) iterator.next();
propertyName = (String)entry.getKey();
propertyType = (String)entry.getValue();
}
iterator = propertySetters.entrySet().iterator();
while (iterator.hasNext())
{
Map.Entry entry = (Map.Entry) iterator.next();
propertyName = (String)entry.getKey();
method= (Method)entry.getValue();
}
iterator = propertyGetters.entrySet().iterator();
while (iterator.hasNext())

{
Map.Entry entry = (Map.Entry) iterator.next();
propertyName = (String)entry.getKey();
method= (Method)entry.getValue();
}
int x=0;
while(x<otherMethods.size())
{
method=otherMethods.get(x);
x++;
}
// code to create java file starts
createClass(c,classToGenerate,javaFileName,propertyNames,propertySetters,propertyGetters,otherMethods);
}catch(Exception e)
{
throw new IOException(e.getMessage().toString());
}
}
public HashMap<String,Method> getPropertySetters(Method methods[])
{
Class [] parameterTypes;
Method method;
String methodName;
String propertyName;
String returnTypeName;
int x;
int y;
HashMap<String,Method> propertySetters=new HashMap<String,Method>();
x=0;
while(x<methods.length)
{
method=methods[x];
methodName=method.getName();
returnTypeName=method.getReturnType().getName();
parameterTypes=method.getParameterTypes();
if(methodName.length()>3 && methodName.startsWith("set") && parameterTypes.length==1 && returnTypeName.equals("void"))
{
propertyName=methodName.substring(3);
propertyName=Character.toString(propertyName.charAt(0)).toLowerCase()
+propertyName.substring(1);
propertySetters.put(propertyName,method);

}
x++;
}
return propertySetters;
}
public HashMap<String,Method> getPropertyGetters(Method methods[],HashMap<String,Method> propertySetters)
{
Class [] parameterTypes;
Method method;
String methodName;
String propertyName;
String returnTypeName;
int x;
int y;
HashMap<String,Method> propertyGetters=new HashMap<String,Method>();
x=0;
while(x<methods.length)
{
method=methods[x];
methodName=method.getName();
returnTypeName=method.getReturnType().getName();
parameterTypes=method.getParameterTypes();
if(methodName.length()>3 && methodName.startsWith("get") && parameterTypes.length==0 && returnTypeName.equals("void")==false)
{
propertyName=methodName.substring(3);
propertyName=Character.toString(propertyName.charAt(0)).toLowerCase()+propertyName.substring(1);
if(propertySetters.get(propertyName)!=null)
{
propertyGetters.put(propertyName,method);
}
}
x++;
}
return propertyGetters;
}
public HashMap<String,String> getProperties(Method methods[])
{
Class [] parameterTypes;
Method method;
String methodName;
String returnTypeName;

String propertyName;
String propertyTypeName;
int x;
int y;
HashMap<String,String> propertyNames=new HashMap<String,String>();
x=0;
while(x<methods.length)
{
method=methods[x];
methodName=method.getName();
returnTypeName=method.getReturnType().getName();
parameterTypes=method.getParameterTypes();
java.lang.reflect.Type types[];
if(methodName.length()>3 && methodName.startsWith("set") && parameterTypes.length==1 && returnTypeName.equals("void"))
{
types=method.getGenericParameterTypes();
if(types.length==1)
{
if(types[0] instanceof ParameterizedType)
{
propertyTypeName=types[0].getTypeName();
}
else
{
propertyTypeName=parameterTypes[0].getName();
}
}
else
{
propertyTypeName=parameterTypes[0].getName();
}
propertyName=methodName.substring(3);
propertyName=Character.toString(propertyName.charAt(0)).toLowerCase()
+propertyName.substring(1);
propertyNames.put(propertyName,propertyTypeName);
}
x++;
}
return propertyNames;
}
public String getTypeOf(Class c,Class declaringClass,String genericTypeName)
{
String dataType="Object";
String s=declaringClass.toGenericString();
int x,y,z;
int p1=s.indexOf("<");

int p2=s.lastIndexOf(">");
int p11,p22;
String ss;
if(p1!=-1 && p2!=-1)
{
s=s.substring(p1+1,p2);
String [] dataTypes=s.split(",");
Class interfaces[];
java.lang.reflect.Type genericInterfaces[];
Class m=c;
while(true)
{
interfaces=m.getInterfaces();
genericInterfaces=m.getGenericInterfaces();
if(interfaces.length==0) break;
x=0;
while(x<interfaces.length)
{
if(interfaces[x].getName().equals(declaringClass.getName()))
{
ss=genericInterfaces[x].getTypeName();
p11=ss.indexOf("<");
p22=ss.lastIndexOf(">");
if(p11!=-1 && p22!=-1)
{
ss=ss.substring(p11+1,p22);
String [] actualDataTypes=ss.split(",");
z=0;
while(z<dataTypes.length)
{
if(dataTypes[z].equals(genericTypeName))
{
dataType=actualDataTypes[z];
break;
}
z++;
}
}
break;
}
x++;
}
if(x<interfaces.length) break;
}
}
return dataType;

}
public ArrayList<Method> getOtherMethods(Method methods[],HashMap<String,Method> propertySetters)
{
Class [] parameterTypes;
Method method;
String methodName;
String propertyName;
String returnTypeName;
int x;
int y;
ArrayList<Method> otherMethods=new ArrayList<Method>();
x=0;
while(x<methods.length)
{
method=methods[x];
methodName=method.getName();
returnTypeName=method.getReturnType().getName();
parameterTypes=method.getParameterTypes();
if(methodName.length()>3 && methodName.startsWith("set") && parameterTypes.length==1 && returnTypeName.equals("void"))
{
x++;
continue;
}
if(methodName.length()>3 && methodName.startsWith("get") && parameterTypes.length==0 && returnTypeName.equals("void")==false)
{
propertyName=methodName.substring(3);
propertyName=Character.toString(propertyName.charAt(0)).toLowerCase()
+propertyName.substring(1);
if(propertySetters.get(propertyName)!=null)
{
x++;
continue;
}
}
propertyName=methodName.substring(3);
propertyName=Character.toString(propertyName.charAt(0)).toLowerCase()
+propertyName.substring(1);
otherMethods.add(method);
x++;
}
return otherMethods;
}

public HashMap<String,String> getPropertiesOfClass(Class c)
{
HashMap<String,String> badaHashMap=new HashMap<String,String>();
Method methods[]=c.getDeclaredMethods();
HashMap<String,String> propertyNames=getProperties(methods);
Iterator iterator = propertyNames.entrySet().iterator();
String propertyName,propertyType;
while (iterator.hasNext())
{
Map.Entry entry = (Map.Entry) iterator.next();
propertyName = (String)entry.getKey();
propertyType = (String)entry.getValue();
badaHashMap.put(propertyName,propertyType);
}
Class interfaces[]=c.getInterfaces();
int x=0;
while(x<interfaces.length)
{
propertyNames=getPropertiesOfClass(interfaces[x]);
iterator = propertyNames.entrySet().iterator();
while (iterator.hasNext())
{
Map.Entry entry = (Map.Entry) iterator.next();
propertyName = (String)entry.getKey();
propertyType = (String)entry.getValue();
badaHashMap.put(propertyName,propertyType);
}
x++;
}
return badaHashMap;
}
public HashMap<String,Method> getPropertySettersOfClass(Class c)
{
HashMap<String,Method> badaHashMap=new HashMap<String,Method>();
Method methods[]=c.getDeclaredMethods();
HashMap<String,Method> propertySetters=getPropertySetters(methods);
Iterator iterator = propertySetters.entrySet().iterator();
String propertyName;
Method method;
while (iterator.hasNext())
{
Map.Entry entry = (Map.Entry) iterator.next();
propertyName = (String)entry.getKey();
method = (Method)entry.getValue();
badaHashMap.put(propertyName,method);

}
Class interfaces[]=c.getInterfaces();
int x=0;
while(x<interfaces.length)
{
propertySetters=getPropertySettersOfClass(interfaces[x]);
iterator = propertySetters.entrySet().iterator();
while (iterator.hasNext())
{
Map.Entry entry = (Map.Entry) iterator.next();
propertyName = (String)entry.getKey();
method = (Method)entry.getValue();
badaHashMap.put(propertyName,method);
}
x++;
}
return badaHashMap;
}
public HashMap<String,Method> getPropertyGettersOfClass(Class c,HashMap<String,Method> propertySetters)
{
HashMap<String,Method> badaHashMap=new HashMap<String,Method>();
Method methods[]=c.getDeclaredMethods();
HashMap<String,Method> propertyGetters=getPropertyGetters(methods,propertySetters);
Iterator iterator = propertyGetters.entrySet().iterator();
String propertyName;
Method method;
while (iterator.hasNext())
{
Map.Entry entry = (Map.Entry) iterator.next();
propertyName = (String)entry.getKey();
method = (Method)entry.getValue();
badaHashMap.put(propertyName,method);
}
Class interfaces[]=c.getInterfaces();
int x=0;
while(x<interfaces.length)
{
propertyGetters=getPropertyGettersOfClass(interfaces[x],propertySetters);
iterator = propertyGetters.entrySet().iterator();
while (iterator.hasNext())
{
Map.Entry entry = (Map.Entry) iterator.next();
propertyName = (String)entry.getKey();
method = (Method)entry.getValue();

badaHashMap.put(propertyName,method);
}
x++;
}
return badaHashMap;
}
public ArrayList<Method> getOtherMethodsOfClass(Class c,HashMap<String,Method> propertySetters)
{
ArrayList<Method> badiArrayList=new ArrayList<Method>();
Method methods[]=c.getDeclaredMethods();
ArrayList<Method> otherMethods=getOtherMethods(methods,propertySetters);
int x=0;
Method method;
while (x<otherMethods.size())
{
method=otherMethods.get(x);
badiArrayList.add(method);
x++;
}
int y;
Class interfaces[]=c.getInterfaces();
x=0;
while(x<interfaces.length)
{
otherMethods=getOtherMethodsOfClass(interfaces[x],propertySetters);
y=0;
while(y<otherMethods.size())
{
badiArrayList.add(otherMethods.get(y));
y++;
}
x++;
}
return badiArrayList;
}
public void createClass(Class c,String classToGenerate,String javaFileName,HashMap<String,String>
propertyNames,HashMap<String,Method> propertySetters,HashMap<String,Method>
propertyGetters,ArrayList<Method> otherMethods) throws IOException
{
File file=new File(javaFileName);
if(file.exists())
{
throw new RuntimeException(javaFileName+" exists.");

}
RandomAccessFile raf=new RandomAccessFile(file,"rw");
String line;
line="package "+project.packageName+".dl.dto;";
raf.writeBytes(line+"\r\n");
line="import "+project.packageName+".dl.dto.interfaces.*;";
raf.writeBytes(line+"\r\n");
line="class "+classToGenerate+" implements "+c.getSimpleName();
raf.writeBytes(line+"\r\n");
line="{";
raf.writeBytes(line+"\r\n");
Iterator iterator;
Method method;
String propertyName,propertyTypeName;
java.lang.reflect.Type types[];
Class parameterTypes[];
// create properties
if(propertyNames.size()>0)
{
line="//properties";
raf.writeBytes(line+"\r\n");
}
iterator=propertyNames.entrySet().iterator();
while(iterator.hasNext())
{
Map.Entry entry=(Map.Entry)iterator.next();
propertyName=(String)entry.getKey();
propertyTypeName=(String)entry.getValue();
line="private "+propertyTypeName+" "+propertyName+";";
raf.writeBytes(line+"\r\n");
}
// create setter methods
if(propertySetters.size()>0)
{
line="//setter methods";
raf.writeBytes(line+"\r\n");
}
iterator=propertySetters.entrySet().iterator();
while(iterator.hasNext())
{
Map.Entry entry=(Map.Entry)iterator.next();
propertyName=(String)entry.getKey();
method=(Method)entry.getValue();
line="public void "+method.getName()+"(";
parameterTypes=method.getParameterTypes();

types=method.getGenericParameterTypes();
if(types.length==1)
{
if(types[0] instanceof ParameterizedType)
{
propertyTypeName=types[0].getTypeName();
}
else
{
propertyTypeName=parameterTypes[0].getName();
}
}
else
{
propertyTypeName=parameterTypes[0].getName();
}
line=line+propertyTypeName+" "+propertyName+")";
raf.writeBytes(line+"\r\n");
line="{";
raf.writeBytes(line+"\r\n");
line="this."+propertyName+"="+propertyName+";";
raf.writeBytes(line+"\r\n");
line="}";
raf.writeBytes(line+"\r\n");
}
// create getter methods
if(propertyGetters.size()>0)
{
line="//getter methods";
raf.writeBytes(line+"\r\n");
}
iterator=propertyGetters.entrySet().iterator();
while(iterator.hasNext())
{
Map.Entry entry=(Map.Entry)iterator.next();
propertyName=(String)entry.getKey();
method=(Method)entry.getValue();
line="public "+propertyNames.get(propertyName)+" "+method.getName()+"()";
raf.writeBytes(line+"\r\n");
line="{";
raf.writeBytes(line+"\r\n");
line="return this."+propertyName+";";
raf.writeBytes(line+"\r\n");
line="}";
raf.writeBytes(line+"\r\n");
}

// create other methods
if(otherMethods.size()>0)
{
line="//other methods";
raf.writeBytes(line+"\r\n");
}
String returnTypeName;
Class returnType;
java.lang.reflect.Type genericReturnType;
String parameterTypeName;
int x,y;
x=0;
while(x<otherMethods.size())
{
method=otherMethods.get(x);
returnType=method.getReturnType();
genericReturnType=method.getGenericReturnType();
if(genericReturnType instanceof ParameterizedType)
{
returnTypeName=genericReturnType.getTypeName();
}
else
{
returnTypeName=returnType.getName();
}
line="public "+returnTypeName+" "+method.getName()+"(";
types=method.getGenericParameterTypes();
parameterTypes=method.getParameterTypes();
y=0;
while(y<types.length)
{
parameterTypeName=parameterTypes[y].getName();
if(types[y].getTypeName().equals(parameterTypes[y].getName())==false)
{
parameterTypeName=getTypeOf(c,method.getDeclaringClass(),types[y].getTypeName());
}
line=line+parameterTypeName+" arg"+(y+1);
y++;
if(y<types.length)
{
line=line+",";
}
}
line=line+")";
raf.writeBytes(line+"\r\n");
line="{";
raf.writeBytes(line+"\r\n");

if(returnTypeName.equals("void")==false)
{
line="return null;";
if(returnTypeName.equals("long")) line="return 0;";
if(returnTypeName.equals("int")) line="return 0;";
if(returnTypeName.equals("short")) line="return 0;";
if(returnTypeName.equals("byte")) line="return 0;";
if(returnTypeName.equals("double")) line="return 0.0;";
if(returnTypeName.equals("float")) line="return 0.0f;";
if(returnTypeName.equals("char")) line="return (char)0;";
if(returnTypeName.equals("boolean")) line="return false;";
raf.writeBytes(line+"\r\n");
}
else
{
raf.writeBytes("\r\n");
}
line="}";
raf.writeBytes(line+"\r\n");
x++;
}
line="}";
raf.writeBytes(line+"\r\n");
raf.close();
}

//-=-=--=-=-=-=-=-=-=-=-
private void createTab(String fileName,String filePath)
{
	int x=0;
while(x<tabbedPane.getTabCount())
{
	if(tabbedPane.getTitleAt(x).equals(fileName))
	{
		tabbedPane.setSelectedIndex(x);
		return;
	}
	
	 x++;		
}
   editorPanel=new EditorPanel(fileName,filePath);
   editorPanel.addClosePanelListener(this);
  tabbedPane.addTab(fileName,editorPanel);
  tabbedPane.setSelectedIndex(x);
container.add(tabbedPane,BorderLayout.CENTER);
	
}

//-=-=--=-=-=-=-=-=-=-=-

private void compileProject()
{
if(compilerResultPanel==null)
{
compilerResultPanel=new
CompilerResultPanel(projectCompiler.getOutput(),projectCompiler.getError());
compilerResultPanel.addClosePanelListener(this);
tabbedPane.addTab("Compiler results",compilerResultPanel);
}
compilerResultPanel.setOutput("Compiling project, please wait");
compilerResultPanel.setError("");

System.out.println("Compilation starts");
projectCompiler.compileProject();
System.out.println("Compilation ends");
System.out.println(projectCompiler.hasErrors());
compilerResultPanel.setOutput(projectCompiler.getOutput());
compilerResultPanel.setError(projectCompiler.getError());
tabbedPane.addTab("Compiler results",compilerResultPanel);
tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
}
public static void updateDateAndTime(String dateAndTime)
{
	dateAndTimeLabel.setText(dateAndTime);
}
}