package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.model.*;
import com.thinking.machines.pm.tool.controls.*;
import com.thinking.machines.pm.tool.controls.interfaces.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
public class AdditionalMethodsPanel extends JPanel implements
AddUpdateDeleteButtonPanelListener,DocumentListener,ListSelectionListener,MoveButtonPanelListener
{
private MoveButtonPanel additionalMethodMoveButtonsPanel;
private JLabel additionalMethodTitleLabel;
private JTextField additionalMethodSearchTextField;
private JTable additionalMethodTable;
private JScrollPane additionalMethodJsp;
private AdditionalMethodsModel additionalMethodsModel;
private JTextField additionalMethodNameTextField;
private JTextField additionalMethodReturnTypeTextField;
private AddUpdateDeleteButtonPanel additionalMethodButtonsPanel;
private MoveButtonPanel additionalMethodParameterMoveButtonsPanel;
private JLabel additionalMethodParameterTitleLabel;
private JTextField additionalMethodParameterSearchTextField;
private JTable additionalMethodParameterTable;
private JScrollPane additionalMethodParameterJsp;
private AdditionalMethodParametersModel additionalMethodParametersModel;
private JTextField additionalMethodParameterNameTextField;
private JTextField additionalMethodParameterDataTypeTextField;
private AddUpdateDeleteButtonPanel additionalMethodParameterButtonsPanel;
private JTextArea textArea;
private JScrollPane textAreaJSP;
public AdditionalMethodsPanel()
{
initializeComponents();
addListeners();
}
public void initializeComponents()
{
textArea=new JTextArea(2,5);
textAreaJSP=new
JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
textArea.setEditable(false);
additionalMethodTitleLabel=new JLabel("Additional Methods");
additionalMethodSearchTextField=new JTextField();
additionalMethodsModel=new AdditionalMethodsModel();
additionalMethodTable=new JTable(additionalMethodsModel);
additionalMethodTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
additionalMethodTable.getColumnModel().getColumn(0).setPreferredWidth(50);
additionalMethodTable.getColumnModel().getColumn(1).setPreferredWidth(250);
additionalMethodTable.getColumnModel().getColumn(1).setPreferredWidth(250);
additionalMethodJsp=new
JScrollPane(additionalMethodTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
additionalMethodNameTextField=new JTextField();
additionalMethodReturnTypeTextField=new JTextField();
additionalMethodButtonsPanel=new AddUpdateDeleteButtonPanel();
JPanel p1=new JPanel();
p1.setLayout(new GridLayout(2,1));
p1.add(additionalMethodTitleLabel);
JPanel p2=new JPanel();
p2.setLayout(new BorderLayout());
p2.add(additionalMethodSearchTextField,BorderLayout.CENTER);
additionalMethodMoveButtonsPanel=new MoveButtonPanel();
p2.add(additionalMethodMoveButtonsPanel,BorderLayout.EAST);
p1.add(p2);
JPanel px=new JPanel();
px.setLayout(new GridLayout(2,2));
px.add(new JLabel("Return Type"));
px.add(new JLabel("Name"));
px.add(additionalMethodReturnTypeTextField);
px.add(additionalMethodNameTextField);
JPanel p3=new JPanel();
p3.setLayout(new GridLayout(2,1));
p3.add(px);
p3.add(additionalMethodButtonsPanel);
JPanel p50=new JPanel();
p50.setLayout(new BorderLayout());
p50.add(p1,BorderLayout.NORTH);
p50.add(additionalMethodJsp,BorderLayout.CENTER);
p50.add(p3,BorderLayout.SOUTH);
additionalMethodParameterTitleLabel=new JLabel("Additional Method Parameters");
additionalMethodParameterSearchTextField=new JTextField();
additionalMethodParametersModel=new AdditionalMethodParametersModel();
additionalMethodParameterTable=new JTable(additionalMethodParametersModel);
additionalMethodParameterTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
additionalMethodParameterTable.getColumnModel().getColumn(0).setPreferredWidth(50);
additionalMethodParameterTable.getColumnModel().getColumn(1).setPreferredWidth(250);
additionalMethodParameterTable.getColumnModel().getColumn(1).setPreferredWidth(250);
additionalMethodParameterJsp=new
JScrollPane(additionalMethodParameterTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
additionalMethodParameterNameTextField=new JTextField();
additionalMethodParameterDataTypeTextField=new JTextField();
additionalMethodParameterButtonsPanel=new AddUpdateDeleteButtonPanel();
JPanel p11=new JPanel();
p11.setLayout(new GridLayout(2,1));
p11.add(additionalMethodParameterTitleLabel);
JPanel p22=new JPanel();
p22.setLayout(new BorderLayout());
p22.add(additionalMethodParameterSearchTextField,BorderLayout.CENTER);
additionalMethodParameterMoveButtonsPanel=new MoveButtonPanel();
p22.add(additionalMethodParameterMoveButtonsPanel,BorderLayout.EAST);
p11.add(p22);
JPanel px1=new JPanel();
px1.setLayout(new GridLayout(2,2));
px1.add(new JLabel("Data Type"));
px1.add(new JLabel("Parameter Name"));
px1.add(additionalMethodParameterDataTypeTextField);
px1.add(additionalMethodParameterNameTextField);
JPanel p33=new JPanel();
p33.setLayout(new GridLayout(2,1));
p33.add(px1);
p33.add(additionalMethodParameterButtonsPanel);
setLayout(new BorderLayout());
JPanel p60=new JPanel();
p60.setLayout(new BorderLayout());
p60.add(p11,BorderLayout.NORTH);
p60.add(additionalMethodParameterJsp,BorderLayout.CENTER);
p60.add(p33,BorderLayout.SOUTH);
JPanel finalPanel=new JPanel();
finalPanel.setLayout(new GridLayout(1,2));
finalPanel.add(p50);
finalPanel.add(p60);
setLayout(new BorderLayout());
add(finalPanel,BorderLayout.CENTER);
add(textAreaJSP,BorderLayout.SOUTH);
}
public void addListeners()
{
additionalMethodButtonsPanel.addAddUpdateDeleteButtonPanelListener(this);
additionalMethodSearchTextField.getDocument().addDocumentListener(this);
additionalMethodTable.getSelectionModel().addListSelectionListener(this);
additionalMethodMoveButtonsPanel.addMoveButtonPanelListener(this);
additionalMethodParameterButtonsPanel.addAddUpdateDeleteButtonPanelListener(this);
additionalMethodParameterSearchTextField.getDocument().addDocumentListener(this);
additionalMethodParameterTable.getSelectionModel().addListSelectionListener(this);
additionalMethodParameterMoveButtonsPanel.addMoveButtonPanelListener(this);
}
public void upButtonClicked(MoveButtonPanel source)
{
if(source==additionalMethodMoveButtonsPanel)
{
int row=additionalMethodTable.getSelectedRow();
if(row<=0)
{
return;
}
AdditionalMethod
current=(AdditionalMethod)additionalMethodsModel.getAdditionalMethodAt(row);
AdditionalMethod
previous=(AdditionalMethod)additionalMethodsModel.getAdditionalMethodAt(row-1);
additionalMethodsModel.updateAdditionalMethod(row-1,current);
additionalMethodsModel.updateAdditionalMethod(row,previous);
additionalMethodsModel.fireTableDataChanged();
additionalMethodTable.setRowSelectionInterval(row-1,row-1);
additionalMethodTable.scrollRectToVisible(additionalMethodTable.getCellRect(row-1,row-1,false));
}
if(source==additionalMethodParameterMoveButtonsPanel)
{
int row=additionalMethodParameterTable.getSelectedRow();
if(row<=0)
{
return;
}
AdditionalMethodParameter current=(AdditionalMethodParameter)additionalMethodParametersModel.getAdditionalMethodParameterAt(row);
AdditionalMethodParameter
previous=(AdditionalMethodParameter)additionalMethodParametersModel.getAdditionalMethodParameterAt(row-1);
additionalMethodParametersModel.updateAdditionalMethodParameter(row-1,current);
additionalMethodParametersModel.updateAdditionalMethodParameter(row,previous);
additionalMethodParametersModel.fireTableDataChanged();
additionalMethodParameterTable.setRowSelectionInterval(row-1,row-1);
additionalMethodParameterTable.scrollRectToVisible(additionalMethodParameterTable.getCellRect(row-1,row-1,false));
}
}
public void downButtonClicked(MoveButtonPanel source)
{
if(source==additionalMethodMoveButtonsPanel)
{
int row=additionalMethodTable.getSelectedRow();
if(row<0 || row==additionalMethodsModel.getRowCount()-1)
{
return;
}
AdditionalMethod current=(AdditionalMethod)additionalMethodsModel.getAdditionalMethodAt(row);
AdditionalMethod next=(AdditionalMethod)additionalMethodsModel.getAdditionalMethodAt(row+1);
additionalMethodsModel.updateAdditionalMethod(row+1,current);
additionalMethodsModel.updateAdditionalMethod(row,next);
additionalMethodsModel.fireTableDataChanged();
additionalMethodTable.setRowSelectionInterval(row+1,row+1);
additionalMethodTable.scrollRectToVisible(additionalMethodTable.getCellRect(row+1,row+1,false));
}
if(source==additionalMethodParameterMoveButtonsPanel)
{
int row=additionalMethodParameterTable.getSelectedRow();
if(row<0 || row==additionalMethodParametersModel.getRowCount()-1)
{
return;
}
AdditionalMethodParameter current=(AdditionalMethodParameter)additionalMethodParametersModel.getAdditionalMethodParameterAt(row);
AdditionalMethodParameter next=(AdditionalMethodParameter)additionalMethodParametersModel.getAdditionalMethodParameterAt(row+1);
additionalMethodParametersModel.updateAdditionalMethodParameter(row+1,current);
additionalMethodParametersModel.updateAdditionalMethodParameter(row,next);
additionalMethodParametersModel.fireTableDataChanged();
additionalMethodParameterTable.setRowSelectionInterval(row+1,row+1);
additionalMethodParameterTable.scrollRectToVisible(additionalMethodParameterTable.getCellRect(row+1,row+1,false));
}
}
public void addButtonClicked(AddUpdateDeleteButtonPanel source)
{
if(source==additionalMethodButtonsPanel)
{
String additionalMethodName=additionalMethodNameTextField.getText().trim();
String additionalMethodReturnType=additionalMethodReturnTypeTextField.getText().trim();
if(additionalMethodName.length()==0) return;
if(additionalMethodReturnType.length()==0) return;
AdditionalMethod additionalMethod=new AdditionalMethod();
additionalMethod.name=additionalMethodName;
additionalMethod.returnType=additionalMethodReturnType;
additionalMethodsModel.addAdditionalMethod(additionalMethod);
additionalMethodsModel.fireTableDataChanged();
int index=additionalMethodsModel.getRowCount()-1;
additionalMethodTable.setRowSelectionInterval(index,index);
additionalMethodTable.scrollRectToVisible(additionalMethodTable.getCellRect(index,index,false));
additionalMethodNameTextField.setText(" ");
additionalMethodReturnTypeTextField.setText(" ");
}
if(source==additionalMethodParameterButtonsPanel)
{
String additionalMethodParameterName=additionalMethodParameterNameTextField.getText().trim();
String additionalMethodParameterDataType=additionalMethodParameterDataTypeTextField.getText().trim();
if(additionalMethodParameterName.length()==0) return;
if(additionalMethodParameterDataType.length()==0) return;
AdditionalMethodParameter additionalMethodParameter=new AdditionalMethodParameter();
additionalMethodParameter.dataType=additionalMethodParameterDataType;
additionalMethodParameter.parameterName=additionalMethodParameterName;
additionalMethodParametersModel.addAdditionalMethodParameter(additionalMethodParameter);
additionalMethodParametersModel.fireTableDataChanged();
additionalMethodParameterNameTextField.setText(" ");
additionalMethodParameterDataTypeTextField.setText(" ");
createAdditionalMethodName();
}
}
public void updateButtonClicked(AddUpdateDeleteButtonPanel source)
{
if(source==additionalMethodButtonsPanel)
{
int row=additionalMethodTable.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
String additionalMethodName=additionalMethodNameTextField.getText().trim();
String additionalMethodReturnType=additionalMethodReturnTypeTextField.getText().trim();
if(additionalMethodName.length()==0) return;
if(additionalMethodReturnType.length()==0) return;
AdditionalMethod additionalMethod=additionalMethodsModel.getAdditionalMethodAt(row);
additionalMethod.name=additionalMethodName;
additionalMethod.returnType=additionalMethodReturnType;
additionalMethodsModel.fireTableDataChanged();
additionalMethodNameTextField.setText("");
additionalMethodReturnTypeTextField.setText("");
}
if(source==additionalMethodParameterButtonsPanel)
{
int row=additionalMethodParameterTable.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
String additionalMethodParameterName=additionalMethodParameterNameTextField.getText().trim();
String
additionalMethodParameterDataType=additionalMethodParameterDataTypeTextField.getText().trim();
if(additionalMethodParameterName.length()==0) return;
if(additionalMethodParameterDataType.length()==0) return;
AdditionalMethodParameter
additionalMethodParameter=additionalMethodParametersModel.getAdditionalMethodParameterAt(row);
additionalMethodParameter.parameterName=additionalMethodParameterName;
additionalMethodParameter.dataType=additionalMethodParameterDataType;
additionalMethodParametersModel.updateAdditionalMethodParameter(row,additionalMethodParameter);
additionalMethodParametersModel.fireTableDataChanged();
additionalMethodParameterNameTextField.setText("");
additionalMethodParameterDataTypeTextField.setText("");
createAdditionalMethodName();
}
}
public void deleteButtonClicked(AddUpdateDeleteButtonPanel source)
{
if(source==additionalMethodButtonsPanel)
{
int row=additionalMethodTable.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
additionalMethodsModel.removeAdditionalMethod(row);
additionalMethodsModel.fireTableDataChanged();
additionalMethodNameTextField.setText("");
additionalMethodReturnTypeTextField.setText("");
additionalMethodParametersModel.clear();
additionalMethodsModel.fireTableDataChanged();
}
if(source==additionalMethodParameterButtonsPanel)
{
int row=additionalMethodParameterTable.getSelectedRow();
if(row<0)
{
JOptionPane.showMessageDialog(this,"No row selected");
return;
}
additionalMethodParametersModel.removeAdditionalMethodParameter(row);
additionalMethodParametersModel.fireTableDataChanged();
additionalMethodParameterNameTextField.setText("");
additionalMethodParameterDataTypeTextField.setText("");
createAdditionalMethodName();
}
}
public void insertUpdate(DocumentEvent ev)
{
if(ev.getDocument()==additionalMethodSearchTextField.getDocument())
{
additionalMethodPerformPartialSearch(additionalMethodSearchTextField.getText());
}
if(ev.getDocument()==additionalMethodParameterSearchTextField.getDocument())
{
additionalMethodParameterPerformPartialSearch(additionalMethodParameterSearchTextField.getText());
}
}
public void removeUpdate(DocumentEvent ev)
{
if(ev.getDocument()==additionalMethodSearchTextField.getDocument())
{
additionalMethodPerformPartialSearch(additionalMethodSearchTextField.getText());
}
if(ev.getDocument()==additionalMethodParameterSearchTextField.getDocument())
{
additionalMethodParameterPerformPartialSearch(additionalMethodParameterSearchTextField.getText());
}
}
public void changedUpdate(DocumentEvent ev)
{
if(ev.getDocument()==additionalMethodSearchTextField.getDocument())
{
additionalMethodPerformPartialSearch(additionalMethodSearchTextField.getText());
}
if(ev.getDocument()==additionalMethodParameterSearchTextField.getDocument())
{
additionalMethodParameterPerformPartialSearch(additionalMethodParameterSearchTextField.getText());
}
}
public void additionalMethodPerformPartialSearch(String partialName)
{
partialName=partialName.trim();
if(partialName.length()==0) return;
int index=additionalMethodsModel.getRowIndex(partialName);
if(index==-1) return;
additionalMethodTable.setRowSelectionInterval(index,index);
additionalMethodTable.scrollRectToVisible(additionalMethodTable.getCellRect(index,index,false));
}
public void additionalMethodParameterPerformPartialSearch(String partialName)
{
partialName=partialName.trim();
if(partialName.length()==0) return;
int index=additionalMethodParametersModel.getRowIndex(partialName);
if(index==-1) return;
additionalMethodParameterTable.setRowSelectionInterval(index,index);
additionalMethodParameterTable.scrollRectToVisible(additionalMethodParameterTable.getCellRect(index,index,false));
}
public void valueChanged(ListSelectionEvent event)
{
if(event.getSource()==additionalMethodTable.getSelectionModel())
{
int row=additionalMethodTable.getSelectedRow();
if(row<0) return;
additionalMethodNameTextField.setText((String)additionalMethodsModel.getValueAt(row,2));
additionalMethodReturnTypeTextField.setText((String)additionalMethodsModel.getValueAt(row,1));
AdditionalMethod additionalMethod=additionalMethodsModel.getAdditionalMethodAt(row);
additionalMethodParametersModel.setParametersList(additionalMethod.additionalMethodParameters);
additionalMethodParametersModel.fireTableDataChanged();
createAdditionalMethodName();
}
if(event.getSource()==additionalMethodParameterTable.getSelectionModel())
{
int row=additionalMethodParameterTable.getSelectedRow();
if(row<0) return;
additionalMethodParameterNameTextField.setText((String)additionalMethodParametersModel.getValueAt(row,2));
additionalMethodParameterDataTypeTextField.setText((String)additionalMethodParametersModel.getValueAt(row,1));
createAdditionalMethodName();
}
}
public void createAdditionalMethodName()
{
int row=additionalMethodTable.getSelectedRow();
AdditionalMethod additionalMethod=additionalMethodsModel.getAdditionalMethodAt(row);
int x=0;
StringBuilder para=new StringBuilder();
while(x<additionalMethod.additionalMethodParameters.size())
{
para.append(additionalMethod.additionalMethodParameters.get(x).dataType+" "+additionalMethod.additionalMethodParameters.get(x).parameterName+",");
x++;
}
if(additionalMethod.additionalMethodParameters.size()>0)
para.setCharAt(para.length()-1,' ');
textArea.setText("public"+" "+additionalMethod.returnType+" "+additionalMethod.name+"("+para+")"+";");
}
public void setAdditionalMethods(ArrayList<AdditionalMethod> additionalMethods)
{
int x=0;
while(x<additionalMethods.size())
{
additionalMethodsModel.addAdditionalMethod(additionalMethods.get(x));
x++;
}
additionalMethodsModel.fireTableDataChanged();
}
public ArrayList<AdditionalMethod> getAdditionalMethods()
{
int x;
ArrayList<AdditionalMethod> additionalMethods = new ArrayList<AdditionalMethod>();
x=0;
while(x<additionalMethodsModel.getRowCount())
{
additionalMethods.add(additionalMethodsModel.getAdditionalMethodAt(x));
x++;
}
return additionalMethods;
}

}