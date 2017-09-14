package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.ui.event.*;
import java.io.*;
import com.thinking.machines.pm.tool.model.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class DTOInterfaceCreationDialog extends JDialog
{
private DTOInterfaceListener target=null;
private JTextArea previewTextArea;
private JScrollPane previewTextAreaScrollPane;
private JTabbedPane tabbedPane;
private Container container;
private Project project;
private Table table;
private JLabel tableName;
private JLabel tableNameCaption;
private JLabel javaFileName;
private JLabel javaFileNameCaption;
private BaseInterfacesPanel baseInterfacesPanel;
private FinalPropertiesPanel finalPropertiesPanel;
private PropertiesPanel propertiesPanel;
private EnumsPanel enumsPanel;
private AdditionalMethodsPanel additionalMethodsPanel;
private JButton createButton;
public DTOInterfaceCreationDialog(Project project,Table table)
{ this.project=project;
this.table=table;
initComponents();
addListeners();
}
public void populateInterfacesPanelWithDefaultData()
{
Field field;
Property property;
ArrayList<Property> properties=new ArrayList<Property>();
int x=0;
while(x<table.fields.size())
{
field=table.fields.get(x);
property=new Property();
property.name=field.getJavaName();
property.type=field.getJavaType();
properties.add(property);
x++;
}
propertiesPanel.setProperties(properties);
ArrayList<String> baseInterfaces=new ArrayList<String>();
baseInterfaces.add("java.io.Serializable");
baseInterfaces.add("java.lang.Comparable<"+table.getJavaName()+"DTOInterface>");
baseInterfacesPanel.setBaseInterfaces(baseInterfaces);
}
public void initComponents()
{ tabbedPane=new JTabbedPane();
previewTextArea=new JTextArea();
previewTextArea.setFont(new Font("Verdana",Font.PLAIN,20));
previewTextArea.setEditable(false);
previewTextAreaScrollPane=new JScrollPane(previewTextArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
tableName=new JLabel(table.getName());
tableNameCaption=new JLabel("Table name : ");
javaFileName=new JLabel(project.packageName+"."+table.getJavaName());
javaFileNameCaption=new JLabel("Java class :");
baseInterfacesPanel=new BaseInterfacesPanel();
finalPropertiesPanel=new FinalPropertiesPanel();
propertiesPanel=new PropertiesPanel();
enumsPanel=new EnumsPanel();
additionalMethodsPanel=new AdditionalMethodsPanel();
JPanel p0=new JPanel();
p0.setLayout(new FlowLayout(FlowLayout.LEFT));
p0.add(tableNameCaption);
p0.add(tableName);
JPanel p00=new JPanel();
p00.setLayout(new FlowLayout(FlowLayout.LEFT));
p00.add(javaFileNameCaption);
p00.add(javaFileName);
JPanel p1000=new JPanel();
p1000.setLayout(new GridLayout(2,1));
p1000.add(p0);
p1000.add(p00);

tabbedPane.add("BaseInterfaces",baseInterfacesPanel);
tabbedPane.add("Properties",propertiesPanel);
tabbedPane.add("Final Properties",finalPropertiesPanel);
tabbedPane.add("Enums ",enumsPanel);
tabbedPane.add("AdditionalMethods",additionalMethodsPanel);
tabbedPane.addTab("Preview",previewTextAreaScrollPane);

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
container.add(p1000,BorderLayout.NORTH);
container.add(tabbedPane,BorderLayout.CENTER);
container.add(p4,BorderLayout.SOUTH);


populateInterfacesPanelWithDefaultData();
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize(dimension.width-675,dimension.height-175);
setLocation(75,75);
setVisible(true);
setModal(true);
}
public void addDTOInterfaceListener(DTOInterfaceListener target)
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
{ createDTOInterface();
}}
);
}
public void createDTOInterface()
{ try
{
String filePath="";
if(project.folder.endsWith("\\"))
{
filePath=project.folder+project.packageName.replace(".","\\")+"\\dl\\dto\\interfaces\\";
} else
{
filePath=project.folder+"\\"+project.packageName.replace(".","\\")+"\\dl\\dto\\interfaces\\";
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
filePath=filePath+table.getJavaName()+"DTOInterface.java";
File file=new File(filePath);
if(file.exists())
{
file.delete();
}
String line;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
raf.writeBytes("package "+project.packageName+".dl.dto.interfaces;\r\n");
line="public interface "+table.getJavaName()+"DTOInterface extends ";
ArrayList<String> baseInterfaces=baseInterfacesPanel.getBaseInterfaces();
int x;
x=0;
String baseInterfaceName;
while(x<baseInterfaces.size())
{
baseInterfaceName=baseInterfaces.get(x);
line+=baseInterfaceName;
x++;
if(x<baseInterfaces.size())
{ line+=",";
}}
raf.writeBytes(line+"\r\n");
raf.writeBytes("{\r\n");
ArrayList<FinalProperty> finalProperties=finalPropertiesPanel.getFinalProperties();
FinalProperty finalProperty;
if(finalProperties.size()>0)
{ 
raf.writeBytes("//Final properties\r\n");
}
x=0;
while(x<finalProperties.size())
{
finalProperty=finalProperties.get(x);
if(finalProperty.type.equals("java.lang.String") || finalProperty.type.equals("String"))
{ line="public final java.lang.String "+finalProperty.name+"=\""+finalProperty.value+"\";";
} else
{ line="public final "+finalProperty.type+" "+finalProperty.name+"="+finalProperty.value+";";
}
raf.writeBytes(line+"\r\n");
x++;
}

ArrayList<com.thinking.machines.pm.tool.model.Enum> enums=enumsPanel.getEnums();
com.thinking.machines.pm.tool.model.Enum vEnum;

if(enums.size()>0)
{
raf.writeBytes("//enum's\r\n");
}

x=0;
while(x<enums.size())
{
vEnum=enums.get(x);
line=vEnum.getDeclaration();
raf.writeBytes(line+"\r\n");
x++;
}
ArrayList<Property> properties=propertiesPanel.getProperties();
Property property;
raf.writeBytes("//Methods\r\n");
x=0;
while(x<properties.size())
{
property=properties.get(x);
line="public void "+property.getSetterName()+"("+property.type+" "+property.name+");";
raf.writeBytes(line+"\r\n");
line="public "+property.type+" "+property.getGetterName()+"();";
raf.writeBytes(line+"\r\n");
x++;
}
ArrayList<AdditionalMethod> additionalMethods=additionalMethodsPanel.getAdditionalMethods();
AdditionalMethod additionalMethod;
x=0;
while(x<additionalMethods.size())
{ additionalMethod=additionalMethods.get(x);
line=additionalMethod.getDeclaration();
raf.writeBytes(line+"\r\n");
x++;
}
raf.writeBytes("}\r\n");
raf.close();
if(this.target!=null)
{ target.dtoInterfaceCreated(table.getJavaName()+"DTOInterface");
} this.dispose();
}catch(Exception ioException)
{
System.out.println(ioException); // remove after testing and add log entry
}}
public void updatePreview()
{
String line;
previewTextArea.setText("");// my logic
previewTextArea.append("package "+project.packageName+".dl.dto.interfaces;\r\n");
line="public interface "+table.getJavaName()+"DTOInterface extends ";
ArrayList<String> baseInterfaces=baseInterfacesPanel.getBaseInterfaces();
int x;
x=0;
String baseInterfaceName;
while(x<baseInterfaces.size())
{
baseInterfaceName=baseInterfaces.get(x);
line+=baseInterfaceName;
x++;
if(x<baseInterfaces.size())
{ line+=",";
}}
previewTextArea.append(line+"\r\n");
previewTextArea.append("{\r\n");
ArrayList<FinalProperty> finalProperties=finalPropertiesPanel.getFinalProperties();
FinalProperty finalProperty;
if(finalProperties.size()>0)
{
previewTextArea.append("//Final properties\r\n");
}
x=0;
while(x<finalProperties.size())
{
finalProperty=finalProperties.get(x);
if(finalProperty.type.equals("java.lang.String") || finalProperty.type.equals("String"))
{ line="public final java.lang.String "+finalProperty.name+"=\""+finalProperty.value+"\";";
} else
{ line="public final "+finalProperty.type+" "+finalProperty.name+"="+finalProperty.value+";";
}
previewTextArea.append(line+"\r\n");
x++;
}

ArrayList<com.thinking.machines.pm.tool.model.Enum> enums=enumsPanel.getEnums();

if(enums.size()>0)
{
previewTextArea.append("//enum's\r\n");
}

com.thinking.machines.pm.tool.model.Enum vEnum;
x=0;
while(x<enums.size())
{
vEnum=enums.get(x);
line=vEnum.getDeclaration();
previewTextArea.append(line+"\r\n");
x++;
}
ArrayList<Property> properties=propertiesPanel.getProperties();
Property property;
if(properties.size()>0)
{
previewTextArea.append("//Methods\r\n");
}
x=0;
while(x<properties.size())
{
property=properties.get(x);
line="public void "+property.getSetterName()+"("+property.type+" "+property.name+");";
previewTextArea.append(line+"\r\n");
line="public "+property.type+" "+property.getGetterName()+"();";
previewTextArea.append(line+"\r\n");
x++;
}
ArrayList<AdditionalMethod> additionalMethods=additionalMethodsPanel.getAdditionalMethods();
AdditionalMethod additionalMethod;
x=0;
while(x<additionalMethods.size())
{ additionalMethod=additionalMethods.get(x);
line=additionalMethod.getDeclaration();
previewTextArea.append(line+"\r\n");
x++;
}
previewTextArea.append("}\r\n");
}}