package com.thinking.machines.pm.tool.ui;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import com.thinking.machines.pm.tool.model.*;
public class TableSelectionDialog extends JDialog
{
private static TableSelectionDialog tableSelectionDialog=null;
private Project project;
private PMTool pmTool;
private JComboBox tablesComboBox;
private JButton proceedButton;
private JLabel tablesCaption;
private Container container;
private String tablesToIgnore[];
private TableSelectionDialog(Project project,PMTool pmTool,String [] tablesToIgnore)
{
this.tablesToIgnore=tablesToIgnore;
this.pmTool=pmTool;
this.project=project;
setTitle("Table selection for (DTO)");
initComponents();
addListeners();
}
public void setTablesToIgnore(String tablesToIgnore[])
{
this.tablesToIgnore=tablesToIgnore;
}
public void initComponents()
{
tablesCaption=new JLabel("Select table");
proceedButton=new JButton("Proceed");
tablesComboBox=new JComboBox();
updateTablesComboBox();
container=getContentPane();
container.setLayout(null);
int lm,tm;
lm=10;
tm=10;
tablesCaption.setBounds(lm+10,tm+20,110,30);
tablesComboBox.setBounds(lm+10+110+10,tm+20,200,30);
proceedButton.setBounds(lm+140,tm+80,100,40);
container.add(tablesCaption);
container.add(tablesComboBox);
container.add(proceedButton);
setSize(400,200);
setLocationRelativeTo(pmTool);
setModal(true);
setResizable(false);
}
public void updateTablesComboBox()
{
tablesComboBox.removeAllItems();
tablesComboBox.addItem("<Select>");
tablesComboBox.addItem("<New DTO>");
int x=0;
int y=0;
while(x<project.database.tables.size())
{
if(tablesToIgnore!=null)
{
y=0;
while(y<tablesToIgnore.length)
{
if(tablesToIgnore[y].equals(project.database.tables.get(x).getName()))
{
break;
}
y++;
}
if(y==tablesToIgnore.length)
{
tablesComboBox.addItem(project.database.tables.get(x).getName());
}
}
else
{
tablesComboBox.addItem(project.database.tables.get(x).getName());
}
x++;
}
}
public void addListeners()
{
proceedButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
TableSelectionDialog.this.setVisible(false);
}
});
}
public Table getSelectedTable()
{
if(tablesComboBox.getSelectedIndex()==0) return null;
if(tablesComboBox.getSelectedIndex()==1) return new Table();
String selectedTableName=tablesComboBox.getSelectedItem().toString();
Table table=null;
int x=0;
while(x<project.database.tables.size())
{
table=project.database.tables.get(x);
if(table.getName().equals(selectedTableName))
{
break;
}
x++;
}
return table;
}
public static Table showTableSelectionDialog(Project project,PMTool pmTool,String tablesToIgnore[])
{
if(tableSelectionDialog==null)
{
tableSelectionDialog=new TableSelectionDialog(project,pmTool,tablesToIgnore);
}
else
{
tableSelectionDialog.setTablesToIgnore(tablesToIgnore);
tableSelectionDialog.updateTablesComboBox();
}
tableSelectionDialog.tablesComboBox.setSelectedIndex(0);
tableSelectionDialog.setVisible(true);
Table table=tableSelectionDialog.getSelectedTable();
return table;
}
}