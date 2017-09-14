package com.thinking.machines.pm.tool.ui;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class CompilerOutputPanel extends JPanel
{
private JScrollPane jsp;
private JTextArea textArea;
private JLabel outputLabel;
public CompilerOutputPanel(String output)
{
textArea=new JTextArea();
textArea.setEditable(false);
textArea.setText(output);
textArea.setFont(new Font("Verdana",Font.PLAIN,16));
outputLabel=new JLabel("Output");
outputLabel.setFont(new Font("Verdana",Font.PLAIN,18));
jsp=new
JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
setLayout(new BorderLayout());
add(jsp,BorderLayout.CENTER);
add(outputLabel,BorderLayout.NORTH);
}
public void setOutput(String output)
{
textArea.setText(output);
}
}