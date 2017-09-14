package com.thinking.machines.pm.tool.controls;
import com.thinking.machines.pm.tool.controls.interfaces.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class AddUpdateDeleteButtonPanel extends JPanel implements ActionListener
{
private AddUpdateDeleteButtonPanelListener target;
private JButton addButton;
private JButton updateButton;
private JButton deleteButton;
public AddUpdateDeleteButtonPanel()
{
initializeComponents();
addListeners();
}
public void initializeComponents()
{
addButton=new JButton("A");
updateButton=new JButton("U");
deleteButton=new JButton("D");
setLayout(new GridLayout(1,3));
add(addButton);
add(updateButton);
add(deleteButton);
}
public void addListeners()
{
addButton.addActionListener(this);
updateButton.addActionListener(this);
deleteButton.addActionListener(this);
}
public void addAddUpdateDeleteButtonPanelListener(AddUpdateDeleteButtonPanelListener target)
{
this.target=target;
}
public void actionPerformed(ActionEvent event)
{
if(target==null) return;
if(event.getSource()==addButton)
{
target.addButtonClicked(this);
}
if(event.getSource()==updateButton)
{
target.updateButtonClicked(this);
}
if(event.getSource()==deleteButton)
{
target.deleteButtonClicked(this);
}
}
}