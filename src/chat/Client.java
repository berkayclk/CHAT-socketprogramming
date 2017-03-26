/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.*;
import java.net.*;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Berkay
 */
class Client {

        private ObjectInputStream sInput;		
	private ObjectOutputStream sOutput;		
	private Socket socket;

	private ClientGUI cg;
	
	private String server, username;
	private int port;
        
        Client(String server, int port, String username, ClientGUI cg) {
		this.server = server;
		this.port = port;
		this.username = username;
		
		this.cg = cg;
	}
        
        public boolean start() {
		try {
			socket = new Socket(server, port);
		}catch(IOException ec) {
			display("[SİSTEM MESAJI] Server'a bağlanırken hata!  " + ec +"  [SİSTEM MESAJI]");
			return false;
		}
		
		String msg = "[SİSTEM MESAJI]     BAĞLANDI!           [SİSTEM MESAJI]";
		display(msg);
	
		try
		{    
                     sOutput = new ObjectOutputStream(socket.getOutputStream());
                     sOutput.flush();
                     sInput  = new ObjectInputStream(socket.getInputStream());
                   
		   
		}catch (IOException eIO) {
			display("[SİSTEM MESAJI] Input/Output kanalı oluştururken hata oluştu. " + eIO +" [SİSTEM MESAJI]");
			return false;
		}
                
		new ListenFromServer().start();
		
		try
		{
			sOutput.writeObject(new ChatMessage(ChatMessage.LOGIN, username));
		}
		catch (IOException eIO) {
			display("[SİSTEM MESAJI] Giriş sırasında hata oluştu : " + eIO +"  [SİSTEM MESAJI]");
			disconnect();
			return false;
		}
		return true;
	}
       
        private void display(String msg) {
            cg.append(msg + "\n");		
	}
	
        void sendMessage(ChatMessage msg) throws IOException {
            
            sOutput.writeObject(msg);
            if(msg.getType() == ChatMessage.LOGOUT)
                disconnect();
	}

	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(IOException e) {} 
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(IOException e) {} 
              try{
			if(socket != null) socket.close();
		}
		catch(IOException e) {} 
                
                cg.connectionFailed();
                
                
			
	}
        
        public String getUsername(){return this.username;}

	class ListenFromServer extends Thread {

                @Override
		public void run() {
			while(true) {
				try {
                                    ChatMessage msg = (ChatMessage) sInput.readObject();
					
                                    switch(msg.getType()){
                                        case ChatMessage.MESSAGE :
                                            cg.append(msg.getMessage());
                                            break;
                                        case ChatMessage.LOGIN :
                                            cg.appendOnlineUsers(msg.getMessage());
                                            break;
                                        case ChatMessage.LOGOUT :
                                           cg.removeOnlineUsers(msg.getMessage());
                                            break;
                                        case ChatMessage.WHOISIN :
                                            msg.getList().remove(username);
                                            cg.displayOnlineUsers(msg.getList());
                                            break;
                                        case ChatMessage.ERRORUN: 
                                            cg.connectionFailed();
                                            JOptionPane.showMessageDialog(cg, msg.getMessage());
                                            disconnect();
                                            break;
                                    }
					
				}
				catch(IOException e) {
					display("[SİSTEM MESAJI] Server bağlantısına ulaşılanmıyor : " + e+ " [SİSTEM MESAJI]");
					if(cg != null) 
						cg.connectionFailed();
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}
