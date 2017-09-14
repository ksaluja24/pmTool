package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.ui.event.*;
import java.io.*;
import com.thinking.machines.pm.tool.model.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class DAOInterfaceCreationDialog extends JDialog
{
private DAOInterfaceListener target=null;
private JTextArea previewTextArea;
private JScrollPane previewTextAreaScrollPane;
private JTabbedPane tabbedPane;
private Container container;
private Project project;
private JLabel javaFileName;
private JLabel javaFileNameCaption;
private AdditionalMethodsPanel additionalMethodsPanel;
private JButton createButton;
private String dtoInterfaceName;
private String daoInterfaceName;
private Table table;
public DAOInterfaceCreationDialog(Project project,String dtoInterfaceName,String
daoInterfaceName,Table table)
{ this.table=table;
this.dtoInterfaceName=dtoInterfaceName;
this.daoInterfaceName=daoInterfaceName;
this.project=project;
initComponents();
addListeners();
}
public void populateInterfacesPanelWithDefaultData()
{
ArrayList<AdditionalMethod> additionalMethods=new ArrayList<AdditionalMethod>();
ArrayList<AdditionalMethodParameter> additionalMethodParameters;
AdditionalMethod additionalMethod;
// add method starts
additionalMethod=new AdditionalMethod();
additionalMethod.name="add";
additionalMethod.returnType="void";
additionalMethodParameters=new ArrayList<AdditionalMethodParameter>();
AdditionalMethodParameter additionalMethodParameter;
additionalMethodParameter=new AdditionalMethodParameter();
additionalMethodParameter.dataType=dtoInterfaceName;
additionalMethodParameter.parameterName=Character.toString(dtoInterfaceName.charAt(0)).toLowerCase()+dtoInterfaceName.substring(1);
additionalMethodParameters.add(additionalMethodParameter);
additionalMethod.additionalMethodParameters=additionalMethodParameters;
additionalMethods.add(additionalMethod);
// update method
additionalMethod=new AdditionalMethod();
additionalMethod.name="update";
additionalMethod.returnType="void";
additionalMethodParameters=new ArrayList<AdditionalMethodParameter>();
additionalMethodParameter=new AdditionalMethodParameter();
additionalMethodParameter.dataType=dtoInterfaceName;
additionalMethodParameter.parameterName=Character.toString(dtoInterfaceName.charAt(0)).toLowerCase()+dtoInterfaceName.substring(1);
additionalMethodParameters.add(additionalMethodParameter);
additionalMethod.additionalMethodParameters=additionalMethodParameters;
additionalMethods.add(additionalMethod);
int x;
if(table.primaryKeys.size()>0)
{ // remove method
additionalMethod=new AdditionalMethod();
additionalMethod.name="remove";
additionalMethod.returnType="void";
additionalMethodParameters=new ArrayList<AdditionalMethodParameter>();
x=0;
while(x<table.primaryKeys.size())
{ additionalMethodParameter=new AdditionalMethodParameter();
additionalMethodParameter.dataType=table.primaryKeys.get(x).getJavaType();
additionalMethodParameter.parameterName=table.primaryKeys.get(x).getJavaName();
additionalMethodParameters.add(additionalMethodParameter);
x++;
} additionalMethod.additionalMethodParameters=additionalMethodParameters;
additionalMethods.add(additionalMethod);
} // getAll method
additionalMethod=new AdditionalMethod();
additionalMethod.name="getAll";
additionalMethod.returnType="List<"+dtoInterfaceName+">";
additionalMethodParameters=new ArrayList<AdditionalMethodParameter>();
additionalMethod.additionalMethodParameters=additionalMethodParameters;
additionalMethods.add(additionalMethod);
Field field;
String methodName;
if(table.primaryKeys.size()>0)
{ // getByPrimaryKey method
additionalMethod=new AdditionalMethod();
x=0;
methodName="getBy";
while(x<table.primaryKeys.size())
{
field=table.primaryKeys.get(x);
methodName=methodName+(Character.toString(field.getJavaName().charAt(0)).toUpperCase()+field.getJavaName().substring(1));
x++;
if(x<table.primaryKeys.size())
{
methodName+="And";
}} additionalMethod.name=methodName;
additionalMethod.returnType="List<"+dtoInterfaceName+">";
additionalMethodParameters=new ArrayList<AdditionalMethodParameter>();
x=0;
while(x<table.primaryKeys.size())
{
field=table.primaryKeys.get(x);
additionalMethodParameter=new AdditionalMethodParameter();
additionalMethodParameter.dataType=field.getJavaType();
additionalMethodParameter.parameterName=field.getJavaName();
additionalMethodParameters.add(additionalMethodParameter);
x++;
}
additionalMethod.additionalMethodParameters=additionalMethodParameters;
additionalMethods.add(additionalMethod);
} // getCount starts
additionalMethod=new AdditionalMethod();
additionalMethod.name="getCount";
additionalMethod.returnType="long";
additionalMethodParameters=new ArrayList<AdditionalMethodParameter>();
additionalMethod.additionalMethodParameters=additionalMethodParameters;
additionalMethods.add(additionalMethod);
if(table.primaryKeys.size()>0)
{ // existsByPrimaryKey starts
additionalMethod=new AdditionalMethod();
x=0;
methodName="";
while(x<table.primaryKeys.size())
{
field=table.primaryKeys.get(x);
if(x==0)
{
methodName=methodName+(Character.toString(field.getJavaName().charAt(0)).toLowerCase()+field.getJavaName().substring(1));
} else
{
methodName=methodName+(Character.toString(field.getJavaName().charAt(0)).toUpperCase()+field.getJavaName().substring(1));
}
x++;
if(x<table.primaryKeys.size())
{
methodName+="And";
}}
methodName+="Exists";
additionalMethod.name=methodName;
additionalMethod.returnType="boolean";
additionalMethodParameters=new ArrayList<AdditionalMethodParameter>();
x=0;
while(x<table.primaryKeys.size())
{
field=table.primaryKeys.get(x);
additionalMethodParameter=new AdditionalMethodParameter();
additionalMethodParameter.dataType=field.getJavaType();
additionalMethodParameter.parameterName=field.getJavaName();
additionalMethodParameters.add(additionalMethodParameter);
x++;
} additionalMethod.additionalMethodParameters=additionalMethodParameters;
additionalMethods.add(additionalMethod);
} additionalMethodsPanel.setAdditionalMethods(additionalMethods);
}
public void initComponents()
{ tabbedPane=new JTabbedPane();
previewTextArea=new JTextArea();
previewTextArea.setFont(new Font("Verdana",Font.PLAIN,20));
previewTextArea.setEditable(false);
previewTextAreaScrollPane=new
JScrollPane(previewTextArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
javaFileName=new JLabel(project.packageName+"."+daoInterfaceName);
javaFileNameCaption=new JLabel("Java class :");
additionalMethodsPanel=new AdditionalMethodsPanel();
createButton=new JButton("Create");
JPanel p00=new JPanel();
p00.setLayout(new FlowLayout(FlowLayout.LEFT));
p00.add(javaFileNameCaption);
p00.add(javaFileName);
createButton=new JButton("Create Java Class");
JPanel p4=new JPanel();
p4.setLayout(new GridLayout(1,5));
p4.add(new JLabel(" "));
p4.add(new JLabel(" "));
p4.add(createButton);
p4.add(new JLabel(" "));
p4.add(new JLabel(" "));
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(p00,BorderLayout.NORTH);
tabbedPane.addTab("Interface",additionalMethodsPanel);
JPanel p650=new JPanel();
p650.setLayout(new BorderLayout());
p650.add(previewTextAreaScrollPane);
tabbedPane.addTab("Preview",p650);
container.add(tabbedPane,BorderLayout.CENTER);
container.add(p4,BorderLayout.SOUTH);
populateInterfacesPanelWithDefaultData();
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize(dimension.width-150,dimension.height-175);
setLocation(75,75);
setVisible(true);
setModal(true);
}
public void addDAOInterfaceListener(DAOInterfaceListener target)
{ this.target=target;
}
public void addListeners()
{ tabbedPane.addChangeListener(new ChangeListener(){
public void stateChanged(ChangeEvent changeEvent)
{ if(tabbedPane.getSelectedIndex()==1) updatePreview();
}
});
createButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{ createDAOInterface();
}}
);
}
public void createDAOInterface()
{ try
{
String filePath="";
if(project.folder.endsWith("\\"))
{
filePath=project.folder+project.packageName.replace(".","\\")+"\\dl\\dao\\interfaces\\";
} else
{
filePath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dao\\interfaces\\";
}
File folder=new File(filePath);
if(folder.exists()==false)
{
folder.mkdirs();
} else
{ if(folder.isDirectory()==false)
{JOptionPane.showMessageDialog(this,filePath+" is a file, please remove that file to proceed");
return;
}}
filePath=filePath+daoInterfaceName+".java";
File file=new File(filePath);
if(file.exists())
{
file.delete();
}
String line;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
line="package "+project.packageName+".dl.dao.interfaces;";
raf.writeBytes(line+"\r\n");
line="import java.util.*;";
raf.writeBytes(line+"\r\n");
line="import "+project.packageName+".dl.dto.interfaces.*;";
raf.writeBytes(line+"\r\n");
line="import "+project.packageName+".dl.exceptions.*;";
raf.writeBytes(line+"\r\n");
line="public interface "+daoInterfaceName;
raf.writeBytes(line+"\r\n");
raf.writeBytes("{\r\n");
ArrayList<AdditionalMethod> additionalMethods=additionalMethodsPanel.getAdditionalMethods();
AdditionalMethod additionalMethod;
int x=0;
while(x<additionalMethods.size())
{ additionalMethod=additionalMethods.get(x);
line=additionalMethod.getDeclarationOfDAOInterface();
raf.writeBytes(line+"\r\n");
x++;
}
raf.writeBytes("}\r\n");
raf.close();
if(this.target!=null)
{ target.daoInterfaceCreated(daoInterfaceName);
} this.dispose();
}catch(Exception ioException)
{
System.out.println(ioException); // remove after testing and add log entry
}}
public void updatePreview()
{
String line;
line="package "+project.packageName+".dl.dao.interfaces;";
previewTextArea.append(line+"\r\n");
line="import java.util.*;";
previewTextArea.append(line+"\r\n");
line="import "+project.packageName+".dl.dto.interfaces.*;";
previewTextArea.append(line+"\r\n");
line="import "+project.packageName+".dl.exceptions.*;";
previewTextArea.append(line+"\r\n");
line="public interface "+daoInterfaceName;
previewTextArea.append(line+"\r\n");
previewTextArea.append("{\r\n");
ArrayList<AdditionalMethod> additionalMethods=additionalMethodsPanel.getAdditionalMethods();
AdditionalMethod additionalMethod;
int x=0;
while(x<additionalMethods.size())
{ additionalMethod=additionalMethods.get(x);
line=additionalMethod.getDeclarationOfDAOInterface();
previewTextArea.append(line+"\r\n");
x++;
}
previewTextArea.append("}\r\n");

}}