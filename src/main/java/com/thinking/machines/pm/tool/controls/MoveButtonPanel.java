package com.thinking.machines.pm.tool.controls;
import com.thinking.machines.pm.tool.controls.interfaces.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class MoveButtonPanel extends JPanel implements ActionListener
{
private MoveButtonPanelListener target;
private JButton upButton;
private JButton downButton;
public MoveButtonPanel()
{
initializeComponents();
addListeners();
}
public void initializeComponents()
{
upButton=new JButton("U");
downButton=new JButton("D");
setLayout(new GridLayout(1,2));
add(upButton);
add(downButton);
}
public void addListeners()
{
upButton.addActionListener(this);
downButton.addActionListener(this);
}
public void addMoveButtonPanelListener(MoveButtonPanelListener target)
{
this.target=target;
}
public void actionPerformed(ActionEvent event)
{
if(target==null) return;
if(event.getSource()==upButton)
{
target.upButtonClicked(this);
}
if(event.getSource()==downButton)
{
target.downButtonClicked(this);
}
}
}