/**
 * Copyright (C) 2012 TVH Group NV. <kalman.tiboldi@tvh.com>
 *
 * This file is part of the tvhgooglemapi project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tvh.gmaildrafter.ui;

import com.tvh.gmaildrafter.Authenticater;
import com.tvh.gmaildrafter.Credentials;
import com.tvh.gmaildrafter.PasswordStoreManager;
import com.tvh.gmaildrafter.PasswordStore;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class GetCredentials extends javax.swing.JDialog {
    private PasswordStoreManager PWMan;

    /**
     * Creates new form GetCredentials
     */
    public GetCredentials(java.awt.Frame parent, boolean modal, String username) {
        super(parent, modal);
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); //This is a bit late, but we don't want to do it if the window isn't shown anyway and we're sure this is the only dialog in the application
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GetCredentials.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GetCredentials.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GetCredentials.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GetCredentials.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        
        initComponents();
        this.getRootPane().setDefaultButton(jBtnOk);
        
        centerScreen();
        
        setAlwaysOnTop(true);
        
        if (username != null) {
            jTxtUsername.setText(username);
        }
        if (username != null && username != "") {
            jPassword.requestFocusInWindow();
        }
        /* TODO: have a look at http://stackoverflow.com/a/596141/1389704 */
        
        PWMan = new PasswordStoreManager();
        jStoreList.removeAllItems();
        for (int i=0; i< PWMan.get_store_num(); i++) {
            jStoreList.addItem(PWMan.get_store(i).get_nick());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLblUsername = new javax.swing.JLabel();
        jTxtUsername = new javax.swing.JTextField();
        jLblPassword = new javax.swing.JLabel();
        jPassword = new javax.swing.JPasswordField();
        jBtnOk = new javax.swing.JButton();
        jBtnCancel = new javax.swing.JButton();
        jLblHash = new javax.swing.JLabel();
        jTxtHash = new javax.swing.JTextField();
        jButtonSave = new javax.swing.JButton();
        jCheckBoxAddAccount = new javax.swing.JCheckBox();
        jLblNick = new javax.swing.JLabel();
        jTxtNick = new javax.swing.JTextField();
        jStoreList = new javax.swing.JComboBox<>();
        jButtonModify = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Provide Google login credentials");

        jLblUsername.setLabelFor(jTxtUsername);
        jLblUsername.setText("Email Address");

        jTxtUsername.setEnabled(false);

        jLblPassword.setLabelFor(jPassword);
        jLblPassword.setText("Password");

        jPassword.setEnabled(false);

        jBtnOk.setText("Ok");
        jBtnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnOkActionPerformed(evt);
            }
        });

        jBtnCancel.setText("Cancel");
        jBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelActionPerformed(evt);
            }
        });

        jLblHash.setLabelFor(jTxtHash);
        jLblHash.setText("Hash");

        jTxtHash.setEnabled(false);

        jButtonSave.setText("Save");
        jButtonSave.setEnabled(false);
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jCheckBoxAddAccount.setText("Add new account");
        jCheckBoxAddAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAddAccountActionPerformed(evt);
            }
        });

        jLblNick.setLabelFor(jTxtUsername);
        jLblNick.setText("Nickname/reminder");

        jTxtNick.setEnabled(false);

        jStoreList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButtonModify.setText("Modify");

        jButtonDelete.setText("Delete");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtUsername)
                            .addComponent(jPassword)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblHash)
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTxtHash)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblNick, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jTxtNick))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBoxAddAccount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonModify, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jStoreList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jStoreList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonModify)
                    .addComponent(jCheckBoxAddAccount)
                    .addComponent(jButtonDelete))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblNick)
                    .addComponent(jTxtNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblUsername)
                    .addComponent(jTxtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblPassword)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblHash)
                    .addComponent(jTxtHash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSave)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnOk)
                    .addComponent(jBtnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private Credentials cred = null;

    public Credentials getCredentials() {
        return cred;
    }
    private void jBtnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnOkActionPerformed
        
        String username = jTxtUsername.getText();
        String password = new String(jPassword.getPassword());
        String hash = new String(jTxtHash.getText());
        
        if ("".equals(jTxtUsername.getText()) || "".equals(new String(jPassword.getPassword()))
            || "".equals(jTxtHash.getText())) {
            JOptionPane.showMessageDialog(null, "Provide email address, password and hash!");
            return;
        }
            
        Credentials cred = new Credentials(username, password, hash);
        if (Authenticater.testCredentials(cred)) {
            this.cred = cred;
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Login Failed!");
        }

    }//GEN-LAST:event_jBtnOkActionPerformed

    private void jBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelActionPerformed
        cred = null;
        setVisible(false);
    }//GEN-LAST:event_jBtnCancelActionPerformed

    private void jCheckBoxAddAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAddAccountActionPerformed
        if (jCheckBoxAddAccount.isSelected()) {
            jTxtNick.setEnabled(true);
            jTxtUsername.setEnabled(true);
            jPassword.setEnabled(true);
            jTxtHash.setEnabled(true);
            jButtonSave.setEnabled(true);
        
        } else {
            jTxtNick.setEnabled(false);
            jTxtUsername.setEnabled(false);
            jPassword.setEnabled(false);
            jTxtHash.setEnabled(false);
            jButtonSave.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxAddAccountActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        String username = jTxtUsername.getText();
        String password = new String(jPassword.getPassword());
        String hash = new String(jTxtHash.getText());
        
        if ("".equals(jTxtUsername.getText()) 
            || "".equals(new String(jPassword.getPassword()))
            || "".equals(jTxtNick.getText())
            || "".equals(jTxtHash.getText())) {
            JOptionPane.showMessageDialog(null, "Provide nickname, email address, password and hash!");
            return;
        }
            
        Credentials credential = new Credentials(username, password, hash);
        PasswordStore store = PWMan.add(jTxtNick.getText());
        store.storeLogin(credential);
        JOptionPane.showMessageDialog(null, "Saved!");
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void centerScreen() {
        Dimension dim = getToolkit().getScreenSize();
        Rectangle abounds = getBounds();
        setLocation((dim.width - abounds.width) / 2,
                (dim.height - abounds.height) / 2);       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancel;
    private javax.swing.JButton jBtnOk;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonModify;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JCheckBox jCheckBoxAddAccount;
    private javax.swing.JLabel jLblHash;
    private javax.swing.JLabel jLblNick;
    private javax.swing.JLabel jLblPassword;
    private javax.swing.JLabel jLblUsername;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JComboBox<String> jStoreList;
    private javax.swing.JTextField jTxtHash;
    private javax.swing.JTextField jTxtNick;
    private javax.swing.JTextField jTxtUsername;
    // End of variables declaration//GEN-END:variables
}
