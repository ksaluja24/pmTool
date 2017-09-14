package com.thinking.machines.pm.tool.ui;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class CompilerErrorsPanel extends JPanel
{
private JScrollPane jsp;
private JTextArea textArea;
private JLabel errorsLabel;
public CompilerErrorsPanel(String error)
{
textArea=new JTextArea();
textArea.setEditable(false);
textArea.setText(error);
textArea.setFont(new Font("Verdana",Font.PLAIN,16));
errorsLabel=new JLabel("Errors");
errorsLabel.setFont(new Font("Verdana",Font.PLAIN,18));
jsp=new
JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
setLayout(new BorderLayout());
add(jsp,BorderLayout.CENTER);
add(errorsLabel,BorderLayout.NORTH);
}
public void setError(String error)
{
textArea.setText(error);
}
}