package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.model.*;
import com.thinking.machines.pm.tool.controls.*;
import com.thinking.machines.pm.tool.controls.interfaces.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
public class EnumsPanel extends JPanel implements
AddUpdateDeleteButtonPanelListener,DocumentListener,ListSelectionListener,MoveButtonPanelListener
{
//Enum Panel properties
private MoveButtonPanel enumMoveButtonsPanel;
private JLabel enumTitleLabel;
private JTextField enumSearchTextField;
private JTable enumTable;
private JScrollPane enumJsp;
private EnumsModel enumsModel;
private JTextField enumNameTextField;
private AddUpdateDeleteButtonPanel enumButtonsPanel;
//Enum Attributes Panel properties
private MoveButtonPanel enumAttributeMoveButtonsPanel;
private JLabel enumAttributeTitleLabel;
private JTextField enumAttributeSearchTextField;
private JTable enumAttributeTable;
private JScrollPane enumAttributeJsp;
private EnumAttributesModel enumAttributesModel;
private JTextField enumAttributeNameTextField;
private AddUpdateDeleteButtonPanel enumAttributeButtonsPanel;
public EnumsPanel()
{
initializeComponents();
addListeners();
}
public void initializeComponents()
{
//Enum components
enumTitleLabel=new JLabel("Enum");
enumSearchTextField=new JTextField();
enumsModel=new EnumsModel();
enumTable=new JTable(enumsModel);
enumTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
enumTable.getColumnModel().getColumn(0).setPreferredWidth(50);
enumTable.getColumnModel().getColumn(1).setPreferredWidth(250);
enumJsp=new
JScrollPane(enumTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
enumNameTextField=new JTextField();;
enumButtonsPanel=new AddUpdateDeleteButtonPanel();
JPanel p1=new JPanel();
p1.setLayout(new GridLayout(2,1));
p1.add(enumTitleLabel);
JPanel p2=new JPanel();
p2.setLayout(new BorderLayout());
p2.add(enumSearchTextField,BorderLayout.CENTER);
enumMoveButtonsPanel=new MoveButtonPanel();
p2.add(enumMoveButtonsPanel,BorderLayout.EAST);
p1.add(p2);
JPanel p3=new JPanel();
p3.setLayout(new GridLayout(2,1));
p3.add(enumNameTextField);
p3.add(enumButtonsPanel);
JPanel p50=new JPanel();
p50.setLayout(new BorderLayout());
p50.add(p1,BorderLayout.NORTH);
p50.add(enumJsp,BorderLayout.CENTER);
p50.add(p3,BorderLayout.SOUTH);
//Enum Attribute Components
enumAttributeTitleLabel=new JLabel("Enum attributes");
enumAttributeSearchTextField=new JTextField();
enumAttributesModel=new EnumAttributesModel();
enumAttributeTable=new JTable(enumAttributesModel);
enumAttributeTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
enumAttributeTable.getColumnModel().getColumn(0).setPreferredWidth(50);
enumAttributeTable.getColumnModel().getColumn(1).setPreferredWidth(250);
enumAttributeJsp=new
JScrollPane(enumAttributeTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
enumAttributeNameTextField=new JTextField();;
enumAttributeButtonsPanel=new AddUpdateDeleteButtonPanel();
JPanel p11=new JPanel();
p11.setLayout(new GridLayout(2,1));
p11.add(enumAttributeTitleLabel);
JPanel p22=new JPanel();
p22.setLayout(new BorderLayout());
p22.add(enumAttributeSearchTextField,BorderLayout.CENTER);
enumAttributeMoveButtonsPanel=new MoveButtonPanel();
p22.add(enumAttributeMoveButtonsPanel,BorderLayout.EAST);
p11.add(p22);
JPanel p33=new JPanel();
p33.setLayout(new GridLayout(2,1));
p33.add(enumAttributeNameTextField);
p33.add(enumAttributeButtonsPanel);
setLayout(new BorderLayout());
JPanel p60=new JPanel();
p60.setLayout(new BorderLayout());
p60.add(p11,BorderLayout.NORTH);
p60.add(enumAttributeJsp,BorderLayout.CENTER);
p60.add(p33,BorderLayout.SOUTH);
setLayout(new GridLayout(1,2));
add(p50);
add(p60);
}
public void addListeners()
{
//Enum components
enumButtonsPanel.addAddUpdateDeleteButtonPanelListener(this);
enumSearchTextField.getDocument().addDocumentListener(this);
enumTable.getSelectionModel().addListSelectionListener(this);
enumMoveButtonsPanel.addMoveButtonPanelListener(this);
//Enum Attribute Components
enumAttributeButtonsPanel.addAddUpdateDeleteButtonPanelListener(this);
enumAttributeSearchTextField.getDocument().addDocumentListener(this);
enumAttributeTable.getSelectionModel().addListSelectionListener(this);
enumAttributeMoveButtonsPanel.addMoveButtonPanelListener(this);
}
public void upButtonClicked(MoveButtonPanel source)
{
if(source==enumMoveButtonsPanel)
{
int row=enumTable.getSelectedRow();
if(row<=0)
{
return;
}
com.thinking.machines.pm.tool.model.Enum
current=(com.thinking.machines.pm.tool.model.Enum)enumsModel.getEnumAt(row);
com.thinking.machines.pm.tool.model.Enum
previous=(com.thinking.machines.pm.tool.model.Enum)enumsModel.getEnumAt(row-1);
enumsModel.updateEnum(row-1,current);
enumsModel.updateEnum(row,previous);
enumsModel.fireTableDataChanged();
enumTable.setRowSelectionInterval(row-1,row-1);
enumTable.scrollRectToVisible(enumTable.getCellRect(row-1,row-1,false));
}
if(source==enumAttributeMoveButtonsPanel)
{
int row=enumAttributeTable.getSelectedRow();
if(row<=0)
{
return;
}
String current=(String)enumAttributesModel.getValueAt(row,1);
String previous=(String)enumAttributesModel.getValueAt(row-1,1);
enumAttributesModel.updateEnumAttribute(row-1,current);
enumAttributesModel.updateEnumAttribute(row,previous);
enumAttributesModel.fireTableDataChanged();
enumAttributeTable.setRowSelectionInterval(row-1,row-1);
enumAttributeTable.scrollRectToVisible(enumAttributeTable.getCellRect(row-1,row-1,false));
}
}
public void downButtonClicked(MoveButtonPanel source)
{
if(source==enumMoveButtonsPanel)
{
int row=enumTable.getSelectedRow();
if(row<0 || row==enumsModel.getRowCount()-1)
{
return;
}
com.thinking.machines.pm.tool.model.Enum
current=(com.thinking.machines.pm.tool.model.Enum)enumsModel.getEnumAt(row);
com.thinking.machines.pm.tool.model.Enum
next=(com.thinking.machines.pm.tool.model.Enum)enumsModel.getEnumAt(row+1);
enumsModel.updateEnum(row+1,current);
enumsModel.updateEnum(row,next);
enumsModel.fireTableDataChanged();
enumTable.setRowSelectionInterval(row+1,row+1);
enumTable.scrollRectToVisible(enumTable.getCellRect(row+1,row+1,false));
}
if(source==enumAttributeMoveButtonsPanel)
{
int row=enumAttributeTable.getSelectedRow();
if(row<0 || row==enumAttributesModel.getRowCount()-1)
{
return;
}
String current=(String)enumAttributesModel.getValueAt(row,1);
String next=(String)enumAttributesModel.getValueAt(row+1,1);
enumAttributesModel.updateEnumAttribute(row+1,current);
enumAttributesModel.updateEnumAttribute(row,next);
enumAttributesModel.fireTableDataChanged();
enumAttributeTable.setRowSelectionInterval(row+1,row+1);
enumAttributeTable.scrollRectToVisible(enumAttributeTable.getCellRect(row+1,row+1,false));
}
}
public void addButtonClicked(AddUpdateDeleteButtonPanel source)
{
if(source==enumButtonsPanel)
{
String enumName=enumNameTextField.getText().trim();
if(enumName.length()==0) return;
com.thinking.machines.pm.tool.model.Enum vEnum=new
com.thinking.machines.pm.tool.model.Enum();
vEnum.name=enumName;
enumsModel.addEnum(vEnum);
enumsModel.fireTableDataChanged();
enumNameTextField.setText("");
int index=enumsModel.getRowCount()-1;
enumTable.setRowSelectionInterval(index,index);
enumTable.scrollRectToVisible(enumTable.getCellRect(index,index,false));
}
if(source==enumAttributeButtonsPanel)
{
String enumAttributeName=enumAttributeNameTextField.getText().trim();
if(enumAttributeName.length()==0) return;
enumAttributesModel.addEnumAttribute(enumAttributeName);
enumAttributesModel.fireTableDataChanged();
enumAttributeNameTextField.setText("");
}
}
public void updateButtonClicked(AddUpdateDeleteButtonPanel source)
{
if(source==enumButtonsPanel)
{
int row=enumTable.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
String enumName=enumNameTextField.getText().trim();
if(enumName.length()==0) return;
com.thinking.machines.pm.tool.model.Enum vEnum=enumsModel.getEnumAt(row);
vEnum.name=enumName;
enumsModel.fireTableDataChanged();
enumNameTextField.setText("");
}
if(source==enumAttributeButtonsPanel)
{
int row=enumAttributeTable.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
String enumAttributeName=enumAttributeNameTextField.getText().trim();
if(enumAttributeName.length()==0) return;
enumAttributesModel.updateEnumAttribute(row,enumAttributeName);
enumAttributesModel.fireTableDataChanged();
enumAttributeNameTextField.setText("");
}
}
public void deleteButtonClicked(AddUpdateDeleteButtonPanel source)
{
if(source==enumButtonsPanel)
{
int row=enumTable.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
enumsModel.removeEnum(row);
enumsModel.fireTableDataChanged();
enumNameTextField.setText("");
enumAttributesModel.clear();
enumAttributesModel.fireTableDataChanged();
}
if(source==enumAttributeButtonsPanel)
{
int row=enumAttributeTable.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
enumAttributesModel.removeEnumAttribute(row);
enumAttributesModel.fireTableDataChanged();
enumAttributeNameTextField.setText("");
}
}
public void insertUpdate(DocumentEvent ev)
{
// if condition required, later on
if(ev.getDocument()==enumSearchTextField.getDocument())
{
enumPerformPartialSearch(enumSearchTextField.getText());
}
if(ev.getDocument()==enumAttributeSearchTextField.getDocument())
{
enumAttributePerformPartialSearch(enumAttributeSearchTextField.getText());
}
}
public void removeUpdate(DocumentEvent ev)
{
if(ev.getDocument()==enumSearchTextField.getDocument())
{
enumPerformPartialSearch(enumSearchTextField.getText());
}
if(ev.getDocument()==enumAttributeSearchTextField.getDocument())
{
enumAttributePerformPartialSearch(enumAttributeSearchTextField.getText());
}
}
public void changedUpdate(DocumentEvent ev)
{
// if condition required, later on
if(ev.getDocument()==enumSearchTextField.getDocument())
{
enumPerformPartialSearch(enumSearchTextField.getText());
}
if(ev.getDocument()==enumAttributeSearchTextField.getDocument())
{
enumAttributePerformPartialSearch(enumAttributeSearchTextField.getText());
}
}
public void enumPerformPartialSearch(String partialName)
{
partialName=partialName.trim();
if(partialName.length()==0) return;
int index=enumsModel.getRowIndex(partialName);
if(index==-1) return;
enumTable.setRowSelectionInterval(index,index);
enumTable.scrollRectToVisible(enumTable.getCellRect(index,index,false));
}
public void enumAttributePerformPartialSearch(String partialName)
{
partialName=partialName.trim();
if(partialName.length()==0) return;
int index=enumAttributesModel.getRowIndex(partialName);
if(index==-1) return;
enumAttributeTable.setRowSelectionInterval(index,index);
enumAttributeTable.scrollRectToVisible(enumAttributeTable.getCellRect(index,index,false));
}
public void valueChanged(ListSelectionEvent event)
{
if(event.getSource()==enumTable.getSelectionModel())
{
int row=enumTable.getSelectedRow();
if(row<0) return;
enumNameTextField.setText((String)enumsModel.getValueAt(row,1));
com.thinking.machines.pm.tool.model.Enum vEnum=enumsModel.getEnumAt(row);
enumAttributesModel.setEnumAttributes(vEnum.enumAttributes);
enumAttributesModel.fireTableDataChanged();
}
if(event.getSource()==enumAttributeTable.getSelectionModel())
{
int row=enumAttributeTable.getSelectedRow();
if(row<0) return;
enumAttributeNameTextField.setText((String)enumAttributesModel.getValueAt(row,1));
}
}
public ArrayList<com.thinking.machines.pm.tool.model.Enum> getEnums()
{
ArrayList<com.thinking.machines.pm.tool.model.Enum> enums=new ArrayList();
int i=0;
com.thinking.machines.pm.tool.model.Enum vEnum=new com.thinking.machines.pm.tool.model.Enum();
while(i<enumsModel.getRowCount())
{
	vEnum=enumsModel.getEnumAt(i);
	enums.add(vEnum);
	
	
	i++;
}
return enums;
}

}