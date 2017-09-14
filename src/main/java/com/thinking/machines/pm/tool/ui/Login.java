package com.thinking.machines.pm.tool.ui;
import com.thinking.machines.pm.tool.network.client.*;
import java.io.File;
import javax.swing.JOptionPane;
import com.thinking.machines.pm.tool.model.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KamalPreet Saluja
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    private static boolean isServerOn;
    private Project project;
    public Login(Project project) {
        this.project=project;
		initComponents();
        Thread t1=new Thread(new Runnable(){
        public void run()
        {
            new ServerOnlineAvailibilityChecker();
        }
        });
        t1.start();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        editorPane.setText("Loading.. news");
        String filePath="C:\\k\\kamal.html";
        try{
		editorPane.setPage(new File(filePath).toURI().toURL());
		}catch(Exception e)
		{
                System.out.println(e);
                }
				setVisible(true);
    }
                     
    private void initComponents() {

        editorScrollPane = new javax.swing.JScrollPane();
        editorPane = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        passwordCaption = new javax.swing.JLabel();
        userNameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        userNameCaption = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        editorScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("News"));

        editorPane.setEditable(false);
        editorPane.setContentType("text/html"); // NOI18N
        editorScrollPane.setViewportView(editorPane);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));

        passwordCaption.setText("Password");

        userNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameFieldActionPerformed(evt);
            }
        });

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        userNameCaption.setText("Username");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/final.PNG"))); // NOI18N
        jLabel1.setText("");

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(passwordCaption)
                            .addGap(71, 71, 71)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(userNameCaption)
                            .addGap(71, 71, 71)
                            .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(loginButton)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addGap(190, 190, 190))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameCaption)
                    .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordCaption)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton)
                    .addComponent(jButton1))
                .addGap(93, 93, 93))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(editorScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editorScrollPane)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        pack();
    }// </editor-fold>                        

	
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        login();        // TODO add your handling code here:
    }                                           

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {                                              
        login();        // TODO add your handling code here:
    }                                             

    private void userNameFieldActionPerformed(java.awt.event.ActionEvent evt) {                                              
        login();        // TODO add your handling code here:
    }                                             

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
System.exit(0);        // TODO add your handling code here:
    }                                        
private void login()
{
String pwd=new String(passwordField.getPassword());
System.out.println(pwd);
if(userNameField.getText().trim().length()==0 ||pwd.length()==0)
{
  return;
}
if(this.isServerOn==false)
{
JOptionPane.showMessageDialog(this,"Server is offline");
return;
}
ServerConfiguration.SERVER_IP="localhost";
ServerConfiguration.PORT_NUMBER=5000;
String response=AdminClient.sendRequest("--LOGIN--"+userNameField.getText().trim()+"/"+pwd);
System.out.println(response);
if(response.equals("OK"))
{
	PMTool pmTool=new PMTool(project);
	this.dispose();
}
else
{
	JOptionPane.showMessageDialog(this,"Invalid Credentials..");

}
}

public static void updateServerStatus(boolean value)
    {
    Login.isServerOn=value;
    }
    // Variables declaration - do not modify                     
    private javax.swing.JEditorPane editorPane;
    private javax.swing.JScrollPane editorScrollPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel passwordCaption;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel userNameCaption;
    private javax.swing.JTextField userNameField;
    // End of variables declaration                   
}
