package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.ui.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import jsyntaxpane.*;
import jsyntaxpane.actions.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class EditorPanel extends JPanel implements CaretListener
{
private ClosePanelListener target;
private JButton closeButton;
private JButton saveButton;
private JEditorPane editorPane;
private JScrollPane editorScrollPane;
private JPanel editorPanel;
private String fileName;
private String filePath;
private JToolBar editorToolBar;
private JLabel caretPosLabel;
public EditorPanel(String fileName,String filePath)
{
	jsyntaxpane.DefaultSyntaxKit.initKit();
	this.fileName=fileName;
	this.filePath=filePath;
  
  caretPosLabel=new JLabel();
editorToolBar=new JToolBar();
 editorToolBar.setBorder(null);
      editorToolBar.setFloatable(false);
        editorToolBar.setRollover(true);
closeButton=new JButton("Close");
saveButton=new JButton("Save");
JPanel panel=new JPanel();
panel.setLayout(new GridLayout(1,2));
panel.add(closeButton);
panel.add(saveButton);


JPanel p1=new JPanel();
p1.setLayout(new BorderLayout());
p1.add(panel,BorderLayout.EAST);

JPanel p2=new JPanel();
p2.setLayout(new BorderLayout());
p2.add(p1,BorderLayout.NORTH);
p2.add(editorToolBar,BorderLayout.SOUTH);

editorPane=new JEditorPane();
  editorScrollPane=new JScrollPane(editorPane);
	 editorPane.setContentType("text/java");

JPanel p3=new JPanel();
p3.setLayout(new BorderLayout());
p3.add(caretPosLabel,BorderLayout.EAST);
setLayout(new BorderLayout());
add(editorScrollPane,BorderLayout.CENTER);
add(p2,BorderLayout.NORTH);
add(p3,BorderLayout.SOUTH)	;

	 
  openFile(filePath);
	 
	 
	closeButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(target!=null)
{
target.closeButtonClicked(EditorPanel.this);
}
}
});
	saveButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ev)
		{
			

			try 
			{
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            file.seek(0);
			String data=editorPane.getText();
			file.writeBytes(data); 
			file.setLength(file.getFilePointer());
			file.close(); 
			 JOptionPane.showMessageDialog (null, "File Saved at path: \n"+filePath);
			} catch (IOException e)
			{
			JOptionPane.showMessageDialog (null, "Could not save file");				
			e.printStackTrace(); 
			}

			
		}
		
		
	});
	
    new CaretMonitor(editorPane, caretPosLabel);	
     editorPane.addCaretListener(this);
  setToolbar();
}

public void addClosePanelListener(ClosePanelListener target)
{
this.target=target;
}

private void openFile(String filePath)
{ 
 String contents = null;
       try {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            file.seek(0);
			while(file.getFilePointer()<file.length())
			{

			editorPane.setText(editorPane.getText()+file.readLine()+"\n"); 
			}
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

	

/*
	try {

       Scanner scan=null;
		scan = new Scanner(new FileReader(filePath));
		while (scan.hasNext()||scan.hasNextLine()) 
		{
			editorPane.setText(editorPane.getText()+scan.nextLine()+ "\r\n");    
		}
		} catch (Exception ex) 
		{ 
		System.out.println(ex.getMessage());
		}
		
		/*
		try{
		editorPane.setPage(new File(filePath).toURI().toURL());
		}catch(Exception e)
		{}
			*/	
}


    private void setToolbar() {
      editorToolBar.removeAll();
       EditorKit kit = editorPane.getEditorKit();
			if (kit instanceof DefaultSyntaxKit) {
				DefaultSyntaxKit defaultSyntaxKit = (DefaultSyntaxKit) kit;
				defaultSyntaxKit.addToolBarActions(editorPane,editorToolBar);
			}
			editorToolBar.validate();
    }

	public void caretUpdate(CaretEvent ce) {
           
         SyntaxDocument sDoc = ActionUtils.getSyntaxDocument(editorPane);
         if (sDoc != null) {
             Token t = sDoc.getTokenAt(ce.getDot());
             if (t != null) {
                 CharSequence tData = t.getText(sDoc);
                 if (t.length > 40) {
                     tData = tData.subSequence(0, 40);
                 }
                 caretPosLabel.setText(t.toString() + ": " + tData);
             } else {
                 // null token, remove the status
//				lblToken.setText(java.util.ResourceBundle.getBundle("jsyntaxpane/Bundle").getString("NO_TOKEN_AT_CURSOR"));
             }
         }
              }
	}