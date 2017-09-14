package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.model.*;
import com.thinking.machines.pm.tool.controls.*;
import com.thinking.machines.pm.tool.controls.interfaces.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class FinalPropertiesPanel extends JPanel implements
AddUpdateDeleteButtonPanelListener,DocumentListener,ListSelectionListener,MoveButtonPanelListener
{
private MoveButtonPanel moveButtonsPanel;
private JLabel titleLabel;
private JTextField searchTextField;
private JTable table;
private JScrollPane jsp;
private FinalPropertiesModel model;
private JLabel nameLabel;
private JLabel typeLabel;
private JLabel valueLabel;
private JTextField nameTextField;
private JTextField typeTextField;
private JTextField valueTextField;
private AddUpdateDeleteButtonPanel buttonsPanel;
private ArrayList<FinalProperty> finalProperties=new ArrayList();
public FinalPropertiesPanel()
{
initializeComponents();
addListeners();
}
public void initializeComponents()
{
titleLabel=new JLabel("Final properties");
searchTextField=new JTextField();
model=new FinalPropertiesModel();
table=new JTable(model);
table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getColumnModel().getColumn(0).setPreferredWidth(50);
table.getColumnModel().getColumn(1).setPreferredWidth(250);
table.getColumnModel().getColumn(2).setPreferredWidth(250);
table.getColumnModel().getColumn(3).setPreferredWidth(250);
jsp=new
JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
nameLabel=new JLabel("Name");
nameTextField=new JTextField();
typeLabel=new JLabel("Type");
typeTextField=new JTextField();
valueLabel=new JLabel("Value");
valueTextField=new JTextField();
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
p4.setLayout(new GridLayout(2,3));
p4.add(typeLabel);
p4.add(nameLabel);
p4.add(valueLabel);
p4.add(typeTextField);
p4.add(nameTextField);
p4.add(valueTextField);
p3.add(p4);
p3.add(buttonsPanel);
setLayout(new BorderLayout());
add(p1,BorderLayout.NORTH); // titile/search part/updown
add(jsp,BorderLayout.CENTER); // table
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
ArrayList<FinalProperty> tmp=new ArrayList<FinalProperty>();
int x=0;
String vName,vType,vValue;
FinalProperty finalProperty;
while(x<model.getRowCount())
{
vType=(String)model.getValueAt(x,1);
vName=(String)model.getValueAt(x,2);
vValue=(String)model.getValueAt(x,3);
finalProperty=new FinalProperty();
finalProperty.type=vType;
finalProperty.name=vName;
finalProperty.value=vValue;
tmp.add(finalProperty);
x++;
}
finalProperty=tmp.get(row);
tmp.remove(row);
tmp.add(row-1,finalProperty);
while(model.getRowCount()>0)
{
model.removeFinalProperty(0);
}
x=0;
while(x<tmp.size())
{
model.addFinalProperty(tmp.get(x));
x++;
}
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
FinalProperty current=(FinalProperty)model.getValueAt(row,1);
FinalProperty next=(FinalProperty)model.getValueAt(row+1,1);
model.updateFinalProperty(row+1,current);
model.updateFinalProperty(row,next);
model.fireTableDataChanged();
table.setRowSelectionInterval(row+1,row+1);
table.scrollRectToVisible(table.getCellRect(row+1,row+1,false));
}
public void addButtonClicked(AddUpdateDeleteButtonPanel source)
{
String name=nameTextField.getText().trim();
String type=typeTextField.getText().trim();
String value=valueTextField.getText().trim();
if(name.length()==0 || type.length()==0 || value.length()==0)
{
JOptionPane.showMessageDialog(this,"Insufficient data");
return;
}
FinalProperty finalProperty=new FinalProperty();
finalProperty.name=name;
finalProperty.type=type;
finalProperty.value=value;
model.addFinalProperty(finalProperty);
model.fireTableDataChanged();
nameTextField.setText("");
valueTextField.setText("");
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
String value=valueTextField.getText().trim();
if(name.length()==0 || type.length()==0 || value.length()==0)
{
JOptionPane.showMessageDialog(this,"Insufficient data");
return;
}
FinalProperty finalProperty=new FinalProperty();
finalProperty.name=name;
finalProperty.type=type;
finalProperty.value=value;
model.updateFinalProperty(row,finalProperty);
model.fireTableDataChanged();
nameTextField.setText("");
valueTextField.setText("");
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
model.removeFinalProperty(row);
model.fireTableDataChanged();
nameTextField.setText("");
valueTextField.setText("");
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
valueTextField.setText((String)model.getValueAt(row,3));
}


public ArrayList<FinalProperty> getFinalProperties()
{
	int i=0;
	FinalProperty finalProperty;
	while(i<model.getRowCount())
	{	
		finalProperty=new FinalProperty();
		finalProperty.name=((String)model.getValueAt(i,2));	
		finalProperty.type=((String)model.getValueAt(i,1));
		finalProperty.value=((String)model.getValueAt(i,3));
	
	
		finalProperties.add(finalProperty);
		
		i++;
	}
	return finalProperties;
	
	
}
public void setFinalProperties(ArrayList<FinalProperty> finalProperties)
{
	this.finalProperties=finalProperties;
	
}


}