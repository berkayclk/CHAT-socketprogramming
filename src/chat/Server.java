/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import chat.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
        private static int uniqueId;
        
	private ArrayList<ClientThread> al;
	private ServerGUI sg;
	private SimpleDateFormat sdf;
	private int port;
	private boolean keepGoing;
        
        public Server(int port, ServerGUI sg) {
		this.sg = sg;
		this.port = port;
                
		sdf = new SimpleDateFormat("HH:mm:ss");
		al = new ArrayList<ClientThread>();
	}
        
        
        public void start() {
		keepGoing = true;
		try 
		{
			ServerSocket serverSocket = new ServerSocket(port);
                        display("Server başarılı bir şekilde aktif edildi. Clientlarını bekliyor... " );
                        
			while(keepGoing) 
			{
                           
				final Socket socket = serverSocket.accept();  	
                                
				if(!keepGoing)
					break;
                                
                            try {
                                acceptOperation(socket);
                            } catch (ClassNotFoundException ex) {
                                
                            }
				
			}
                        
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
                                            tc.sInput.close();
                                            tc.sOutput.close();
                                            tc.socket.close();

                                            sg.removeOnlineUsers(tc.username);
					}
					catch(IOException ioE) {
					}
				}
                                
			}
			catch(Exception e) {
				display("Server ve Client kapatma hatası : " + e);
			}
		}
		// something went bad
		catch (IOException e) {
                          String msg = sdf.format(new Date()) + " Server Oluşturulamadı : " + e + "\n";
			display(msg);
		}
	}	
        
        protected void stop() {
		keepGoing = false;
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
		}
	}

        private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		sg.appendEvent(time + "\n");
	}
        public void sendMessageToAll(String msg){
            broadcast("[ADMİN] : "+ msg);
        }
        
        private synchronized List<String> getOnlineUsers(){
            List<String> users = new ArrayList<String>();
            
            for(int i = al.size(); --i >= 0;) {
                            ClientThread ct = al.get(i);
                            users.add(ct.username);
            } return users;
        }
        
        public void sendLoginEvent(String username){
            eventBroadcast(username, ChatMessage.LOGIN);
        }
        public void sendLogoutEvent(String username){
            eventBroadcast(username, ChatMessage.LOGOUT);
        }
         
    
        
        private synchronized void eventBroadcast(String message, int messageType) {
		 
		for(int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
                        if(!ct.username.equals(message)){
                            if(!ct.writeMsg(message,messageType)) {
				al.remove(i);
				display("Client ile bağlantı koptu :  " + ct.username );
                            }
                        }
			
		}
	}
         
        private synchronized void broadcast(String message) {
		String time = sdf.format(new Date());
		String messageLf = time + " " + message + "\n";
		
                sg.appendRoom(messageLf);     
		
		for(int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
                        
			if(!ct.writeMsg(messageLf,ChatMessage.MESSAGE)) {
				al.remove(i);
				display("Client ile bağlantı koptu :  " + ct.username );
			}
		}
	}

	synchronized void remove(int id) {
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			if(ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}
        
        private void acceptOperation(Socket socket) throws IOException, ClassNotFoundException{
           try{
                ObjectOutputStream sOutput = new ObjectOutputStream(socket.getOutputStream());
                sOutput.flush();
                ObjectInputStream sInput  = new ObjectInputStream(socket.getInputStream());

                ChatMessage msg = (ChatMessage) sInput.readObject();
                if(msg.getType() == ChatMessage.LOGIN){
                    for(int i = 0; i < al.size(); ++i) {
                        ClientThread ct = al.get(i);
                        if(ct.username.equals(msg.getMessage())){
                            sOutput.writeObject(new ChatMessage(ChatMessage.ERRORUN,"Kullanıcı adı kullanımda. Başka birşey deneyiniz."));
                            sInput.close(); sOutput.close(); socket.close();
                            return ;
                        }
                    }
                }
                ClientThread t = new ClientThread(socket,sInput,sOutput,msg.getMessage()); 
                sendLoginEvent(msg.getMessage());
                t.writeList();
                al.add(t);									
                t.start();
           }catch (IOException e) {
		display("Input/Output kanallarında sıkıntı yaşanıyor : " + e);
		return;
            }catch (ClassNotFoundException e) {
		}
                   
            return;            
				
        }

        
        
        class ClientThread extends Thread {
		
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput; 
		
                
		int id;
		String username;
		ChatMessage cm;
		String date;

		
                ClientThread(Socket socket,ObjectInputStream sInput,ObjectOutputStream sOutput,String msg) {
			
                    	id = ++uniqueId;
			this.socket = socket;
			this.sInput = sInput;
                        this.sOutput = sOutput;
                        this.username =  msg;
                                   
                        sg.appendOnlineUsers(username, id);
                        display(username + " bağlandı.");
			
                        date = new Date().toString() + "\n";
		}

		public void run() {
			boolean keepGoing = true;
			while(keepGoing) {
				try {
					cm = (ChatMessage) sInput.readObject();
				}
				catch (IOException e) {
					display(username + "ile olan bağlantıda hata oluştu : " + e);
                                        keepGoing = false;
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
                                
				String message = cm.getMessage();

				switch(cm.getType()) {

				case ChatMessage.MESSAGE:
					broadcast(username + ": " + message);
					break;
				case ChatMessage.LOGOUT:
					display(username + " çıkış yaptı");
                                        sg.removeOnlineUsers(username);
                                        sendLogoutEvent(username);
					keepGoing = false;
					break;
				
				}
			}
                        
			remove(id);
                        
			close();
		}
		
		private void close() {
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
                         sg.removeOnlineUsers(username);
		}

		private boolean writeMsg(String msg,int messageType) {
			if(!socket.isConnected()) {
				close();
				return false;
			}
			try {
				sOutput.writeObject(new ChatMessage(messageType, msg));
			}
			catch(IOException e) {
				display(username+ " adlı kullanıcıya mesaj gönderilirken hata oluştu.");
				display(e.toString());
			}
			return true;
		}
                private boolean writeList() {
			if(!socket.isConnected()) {
				close();
				return false;
			}
			try {
                             List<String> users =  getOnlineUsers();
                             
                             sOutput.writeObject(new ChatMessage(ChatMessage.WHOISIN, users));
			}
			catch(IOException e) {
				display(username+ " adlı kullanıcıya mesaj gönderilirken hata oluştu.");
				display(e.toString());
			}
			return true;
		}
                
              
	}

}
