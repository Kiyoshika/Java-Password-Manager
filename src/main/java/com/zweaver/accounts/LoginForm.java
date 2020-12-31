package com.zweaver.accounts;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.zweaver.core.CoreGUI;
import com.zweaver.dialogue.OKDialogue;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.UIManager;
import org.jasypt.util.text.AES256TextEncryptor;

public class LoginForm extends javax.swing.JFrame {

    public LoginForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        createUserButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple Password Manager");

        usernameField.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Username:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Password:");

        passwordField.setForeground(new java.awt.Color(255, 255, 255));

        createUserButton.setBackground(new java.awt.Color(153, 255, 153));
        createUserButton.setForeground(new java.awt.Color(0, 0, 0));
        createUserButton.setText("Create User");
        createUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createUserButtonActionPerformed(evt);
            }
        });

        loginButton.setBackground(new java.awt.Color(102, 153, 255));
        loginButton.setForeground(new java.awt.Color(0, 0, 0));
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameField)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createUserButton)
                    .addComponent(loginButton))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createUserButtonActionPerformed
            Window activeWindow = javax.swing.FocusManager.getCurrentManager().getActiveWindow();
            ConfirmPassword cp = new ConfirmPassword(activeWindow, this, usernameField.getText(), passwordField.getText());
    }//GEN-LAST:event_createUserButtonActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // check if user folder exists
        String path = System.getProperty("user.home") + File.separator + "passwordmanager" + File.separator + usernameField.getText();
        File userFolder = new File(path);
        if (userFolder.exists()) {
            // get encrypted password from folder
            AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
            textEncryptor.setPassword(usernameField.getText());
            String decryptedPassword = "";
            String encryptedPassword = "";
            
            try {
                BufferedReader configFile = new BufferedReader(new FileReader(path + File.separator + usernameField.getText() + ".cfg"));
                String currentLine;
                while ( (currentLine = configFile.readLine()) != null) {
                    encryptedPassword = currentLine;
                    decryptedPassword = textEncryptor.decrypt(currentLine);
                }
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (decryptedPassword.equals(passwordField.getText())) {
                // login successful
                Window activeWindow = javax.swing.FocusManager.getCurrentManager().getActiveWindow();
                CoreGUI core = new CoreGUI(activeWindow, usernameField.getText(), encryptedPassword);
                core.importConfigOnLogin();
                this.dispose();
            } else {
                System.out.println(decryptedPassword);
                System.out.println(passwordField.getText());
                Window activeWindow = javax.swing.FocusManager.getCurrentManager().getActiveWindow();
                OKDialogue okd = new OKDialogue(activeWindow, "Invalid password, please try again.");
            }
                
        } else {
            Window activeWindow = javax.swing.FocusManager.getCurrentManager().getActiveWindow();
            OKDialogue okd = new OKDialogue(activeWindow, "That user does not exist.");
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createUserButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
