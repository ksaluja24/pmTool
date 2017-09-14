package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.model.*;
import com.thinking.machines.pm.tool.controls.*;
import com.thinking.machines.pm.tool.controls.interfaces.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class PropertiesPanel extends JPanel implements
AddUpdateDeleteButtonPanelListener,DocumentListener,ListSelectionListener,MoveButtonPanelListener
{
private MoveButtonPanel moveButtonsPanel;
private JLabel titleLabel;
private JTextField searchTextField;
private JTable table;
private JScrollPane jsp;
private PropertiesModel model;
private JLabel nameLabel;
private JLabel typeLabel;
private JTextField nameTextField;
private JTextField typeTextField;
private AddUpdateDeleteButtonPanel buttonsPanel;
public PropertiesPanel()
{
initializeComponents();
addListeners();
}
public void initializeComponents()
{
titleLabel=new JLabel("Final properties");
searchTextField=new JTextField();
model=new PropertiesModel();
table=new JTable(model);
table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getColumnModel().getColumn(0).setPreferredWidth(50);
table.getColumnModel().getColumn(1).setPreferredWidth(250);
table.getColumnModel().getColumn(2).setPreferredWidth(250);
jsp=new
JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
nameLabel=new JLabel("Name");
nameTextField=new JTextField();
typeLabel=new JLabel("Type");
typeTextField=new JTextField();
buttonsPanel=new AddUpdateDeleteButtonPanel();
JPanel p1=new JPanel();
p1.setLayout(new GridLayout(2,1));
p1.add(titleLabel);
JPanel p2=new JPanel();
p2.setLayout(new BorderLayout());
p2.add(searchTextField,BorderLayout.CENTER);
moveButtonsPanel=new MoveButtonPanel();
p2.add(moveButtonsPanel,BorderLayout.EAST);
p1.add(p2);
JPanel p3=new JPanel();
p3.setLayout(new GridLayout(2,1));
JPanel p4=new JPanel();
p4.setLayout(new GridLayout(2,2));
p4.add(typeLabel);
p4.add(nameLabel);
p4.add(typeTextField);
p4.add(nameTextField);
p3.add(p4);
p3.add(buttonsPanel);
setLayout(new BorderLayout());
add(p1,BorderLayout.NORTH);
add(jsp,BorderLayout.CENTER);
add(p3,BorderLayout.SOUTH);
}
public void addListeners()
{
buttonsPanel.addAddUpdateDeleteButtonPanelListener(this);
searchTextField.getDocument().addDocumentListener(this);
table.getSelectionModel().addListSelectionListener(this);
moveButtonsPanel.addMoveButtonPanelListener(this);
}
public void upButtonClicked(MoveButtonPanel source)
{
int row=table.getSelectedRow();
if(row<=0)
{
return;
}
ArrayList<Property> tmp=new ArrayList<Property>();
Property property;
int x=0;
while(x<model.getRowCount())
{
property= model.getPropertyAt(x);
tmp.add(property);
x++;
}
property=tmp.get(row);
tmp.remove(row);
tmp.add(row-1,property);
model.resetPropertiesList();
model.setPropertiesList(tmp);
model.fireTableDataChanged();
table.setRowSelectionInterval(row-1,row-1);
table.scrollRectToVisible(table.getCellRect(row-1,row-1,false));
}
public void downButtonClicked(MoveButtonPanel source)
{
int row=table.getSelectedRow();
if(row<0 || row==model.getRowCount()-1)
{
return;
}
ArrayList<Property> tmp=new ArrayList<Property>();
Property property;
int x=0;
while(x<model.getRowCount())
{
property= model.getPropertyAt(x);
tmp.add(property);
x++;
}
property=tmp.get(row);
tmp.remove(row);
tmp.add(row+1,property);
model.resetPropertiesList();
model.setPropertiesList(tmp);
model.fireTableDataChanged();
table.setRowSelectionInterval(row+1,row+1);
table.scrollRectToVisible(table.getCellRect(row+1,row+1,false));
}
public void addButtonClicked(AddUpdateDeleteButtonPanel source)
{
String name=nameTextField.getText().trim();
String type=typeTextField.getText().trim();
if(name.length()==0 || type.length()==0)
{
JOptionPane.showMessageDialog(this,"Insufficient data");
return;
}
Property property=new Property();
property.name=name;
property.type=type;
model.addProperty(property);
model.fireTableDataChanged();
nameTextField.setText("");
typeTextField.setText("");
}
public void updateButtonClicked(AddUpdateDeleteButtonPanel source)
{
int row=table.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
String name=nameTextField.getText().trim();
String type=typeTextField.getText().trim();
if(name.length()==0 || type.length()==0)
{
JOptionPane.showMessageDialog(this,"Insufficient data");
return;
}
Property property=new Property();
property.name=name;
property.type=type;
model.updateProperty(row,property);
model.fireTableDataChanged();
nameTextField.setText("");
typeTextField.setText("");
}
public void deleteButtonClicked(AddUpdateDeleteButtonPanel source)
{
int row=table.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
model.removeProperty(row);
model.fireTableDataChanged();
nameTextField.setText("");
typeTextField.setText("");
}
public void insertUpdate(DocumentEvent ev)
{
performPartialSearch(searchTextField.getText());
}
public void removeUpdate(DocumentEvent ev)
{
performPartialSearch(searchTextField.getText());
}
public void changedUpdate(DocumentEvent ev)
{
performPartialSearch(searchTextField.getText());
}
public void performPartialSearch(String partialName)
{
partialName=partialName.trim();
if(partialName.length()==0) return;
int index=model.getRowIndex(partialName);
if(index==-1) return;
table.setRowSelectionInterval(index,index);
table.scrollRectToVisible(table.getCellRect(index,index,false));
}
public void valueChanged(ListSelectionEvent event)
{
int row=table.getSelectedRow();
if(row<0) return;
typeTextField.setText((String)model.getValueAt(row,1));
nameTextField.setText((String)model.getValueAt(row,2));
}
public ArrayList<Property> getProperties()
{
int x;
ArrayList<Property> properties = new ArrayList<Property>();
x=0;
while(x<model.getRowCount())
{
properties.add(model.getPropertyAt(x));
x++;
}
return properties;
}
public void setProperties(ArrayList<Property> properties)
{
int x=0;
while(x<properties.size())
{
model.addProperty(properties.get(x));
x++;
}
model.fireTableDataChanged();
}
}