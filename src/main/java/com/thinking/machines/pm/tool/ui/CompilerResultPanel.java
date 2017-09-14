package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.ui.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class CompilerResultPanel extends JPanel
{
private ClosePanelListener target;
private CompilerErrorsPanel compilerErrorsPanel;
private CompilerOutputPanel compilerOutputPanel;
private JButton closeButton;
public CompilerResultPanel(String output,String error)
{
closeButton=new JButton("Close");
JPanel p1=new JPanel();
p1.setLayout(new GridLayout(2,1));
compilerErrorsPanel=new CompilerErrorsPanel(error);
compilerOutputPanel=new CompilerOutputPanel(output);
p1.add(compilerOutputPanel);
p1.add(compilerErrorsPanel);
setLayout(new BorderLayout());
add(p1,BorderLayout.CENTER);
JPanel p2=new JPanel();
p2.setLayout(new BorderLayout());
p2.add(closeButton,BorderLayout.EAST);
add(p2,BorderLayout.NORTH);
closeButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(target!=null)
{
target.closeButtonClicked(CompilerResultPanel.this);
}
}
});
}
public void addClosePanelListener(ClosePanelListener target)
{
this.target=target;
}
public void setOutput(String output)
{
compilerOutputPanel.setOutput(output);
}
public void setError(String error)
{
compilerErrorsPanel.setError(error);
}
}