/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Berkay
 */
public class ServerGUI extends javax.swing.JFrame {

   private static final long serialVersionUID = 1L;
    private Server server;
    int PORT = 1500;
    
    public ServerGUI() {
        initComponents();
        server = null;
        try {
            lblIPAdress.setText(Inet4Address.getLocalHost().getHostAddress());
        } catch (UnknownHostException ex) {
           
        }
    }
    void appendRoom(String str) {
		allMessages.append(str);
		allMessages.setCaretPosition(allMessages.getText().length() - 1);
	}

    void appendEvent(String str) {
		event.append(str);
		
	}
    
    void appendOnlineUsers(String username,int id){
        onlineUsers.add(username, id);
    }
    void removeOnlineUsers(String username){
        onlineUsers.remove(username);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        allMessages = new java.awt.TextArea();
        newMessage = new java.awt.TextField();
        senButton = new java.awt.Button();
        onlineUsers = new java.awt.List();
        lblIPAdress = new java.awt.Label();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        stopStart = new javax.swing.JButton();
        event = new java.awt.TextArea();
        label3 = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CHAT SERVER");
        setFocusCycleRoot(false);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        allMessages.setEditable(false);

        senButton.setLabel("GÖNDER");
        senButton.setName("sendButton"); // NOI18N
        senButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                senButtonActionPerformed(evt);
            }
        });

        lblIPAdress.setName("lblIPAdress"); // NOI18N
        lblIPAdress.setText("127.0.0.1");

        label1.setText("SERVER ADRESİ : ");

        label2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label2.setText("AKTİF KULLANICILAR");

        stopStart.setText("ÇALIŞTIR");
        stopStart.setToolTipText("");
        stopStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopStartActionPerformed(evt);
            }
        });

        event.setEditable(false);

        label3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        label3.setText("OLAY KAYITLARI");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(newMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(senButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(allMessages, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblIPAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(364, 364, 364)
                                .addComponent(stopStart)
                                .addGap(9, 9, 9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(onlineUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(event, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIPAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(stopStart, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(allMessages, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(senButton, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addComponent(newMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(onlineUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(event, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblIPAdress.getAccessibleContext().setAccessibleName("lblIPAdress");
        lblIPAdress.getAccessibleContext().setAccessibleDescription("");

        getAccessibleContext().setAccessibleParent(senButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void stopStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopStartActionPerformed
                 if(server != null) {
			server.stop();
			server = null;
			stopStart.setText("ÇALIŞTIR");
                        allMessages.setText("");
			return;
		}
		
		server = new Server(PORT, this);
                stopStart.setText("DURDUR");
                new ServerRunning().start();
                
		//new ServerRunning().start();
		
    }//GEN-LAST:event_stopStartActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
              if(server != null) {
			try {
				server.stop();			
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		dispose();
		System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void senButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_senButtonActionPerformed
        server.sendMessageToAll(newMessage.getText());
        newMessage.setText("");
    }//GEN-LAST:event_senButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerGUI().setVisible(true);
            }
        });
    }
    
    class ServerRunning extends Thread {
		public void run() {
			server.start();         
			// the server failed
			stopStart.setText("ÇALIŞTIR");
			appendEvent("Server Durdu\n");
			server = null;
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.TextArea allMessages;
    private java.awt.TextArea event;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label lblIPAdress;
    private java.awt.TextField newMessage;
    private java.awt.List onlineUsers;
    private java.awt.Button senButton;
    private javax.swing.JButton stopStart;
    // End of variables declaration//GEN-END:variables
}
