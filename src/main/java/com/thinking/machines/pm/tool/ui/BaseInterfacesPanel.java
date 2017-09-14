package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.model.*;
import com.thinking.machines.pm.tool.controls.*;
import com.thinking.machines.pm.tool.controls.interfaces.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class BaseInterfacesPanel extends JPanel implements
AddUpdateDeleteButtonPanelListener,DocumentListener,ListSelectionListener,MoveButtonPanelListener
{
private MoveButtonPanel moveButtonsPanel;
private JLabel titleLabel;
private JTextField searchTextField;
private JTable table;
private JScrollPane jsp;
private BaseInterfacesModel model;
private JTextField nameTextField;
private AddUpdateDeleteButtonPanel buttonsPanel;
public BaseInterfacesPanel()
{ initializeComponents();
addListeners();
}
public void initializeComponents()
{ titleLabel=new JLabel("Base interfaces");
searchTextField=new JTextField();
model=new BaseInterfacesModel();
table=new JTable(model);
table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getColumnModel().getColumn(0).setPreferredWidth(50);
table.getColumnModel().getColumn(1).setPreferredWidth(250);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
nameTextField=new JTextField();;
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
p3.add(nameTextField);
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
String current=(String)model.getValueAt(row,1);
String previous=(String)model.getValueAt(row-1,1);
model.updateBaseInterface(row-1,current);
model.updateBaseInterface(row,previous);
model.fireTableDataChanged();
table.setRowSelectionInterval(row-1,row-1);
table.scrollRectToVisible(table.getCellRect(row-1,row-1,false));
}
public void downButtonClicked(MoveButtonPanel source)
{ int row=table.getSelectedRow();
if(row<0 || row==model.getRowCount()-1)
{
return;
}
String current=(String)model.getValueAt(row,1);
String next=(String)model.getValueAt(row+1,1);
model.updateBaseInterface(row+1,current);
model.updateBaseInterface(row,next);
model.fireTableDataChanged();
table.setRowSelectionInterval(row+1,row+1);
table.scrollRectToVisible(table.getCellRect(row+1,row+1,false));
}
public void addButtonClicked(AddUpdateDeleteButtonPanel source)
{
String name=nameTextField.getText().trim();
if(name.length()==0) return;
model.addBaseInterface(name);
model.fireTableDataChanged();
nameTextField.setText("");
}
public void updateButtonClicked(AddUpdateDeleteButtonPanel source)
{ int row=table.getSelectedRow();
if(row<0)
{JOptionPane.showMessageDialog(this,"No row selected");
return;
}
String name=nameTextField.getText().trim();
if(name.length()==0) return;
model.updateBaseInterface(row,name);
model.fireTableDataChanged();
nameTextField.setText("");
}
public void deleteButtonClicked(AddUpdateDeleteButtonPanel source)
{ int row=table.getSelectedRow();
if(row<0)
{JOptionPane.showMessageDialog(this,"No row selected");
return;
}
model.removeBaseInterface(row);
model.fireTableDataChanged();
nameTextField.setText("");
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
{ int row=table.getSelectedRow();
if(row<0) return;
nameTextField.setText((String)model.getValueAt(row,1));
}


public void setBaseInterfaces(ArrayList<String> baseInterfaces)
{
int x=0;
while(x<baseInterfaces.size())
{
model.addBaseInterface(baseInterfaces.get(x));
x++;
}
model.fireTableDataChanged();
}

public ArrayList<String> getBaseInterfaces()
{
ArrayList<String> baseInterfaces=new ArrayList<String>();
int x=0;
while(x<model.names.size())
{
baseInterfaces.add((String)model.getValueAt(x,1));
x++;
}
return baseInterfaces;
}
}