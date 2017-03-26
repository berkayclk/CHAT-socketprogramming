/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.*;
import java.util.*;

class ChatMessage implements Serializable {
     protected static final long serialVersionUID = 1112122200L;
    static final int WHOISIN = 0, MESSAGE = 1,LOGIN=2, LOGOUT = 3,ERRORUN=4;// CHECKUN: ERROR USERNAME
    private final int type;
    private final String message;
    private final List<String> OnlineUsers;

    ChatMessage(int type, String message) {
        this.type = type;
        this.message = message;
        OnlineUsers=null;
    }
     ChatMessage(int type, List<String> OnlineUsers) {
        this.type = type;
        this.OnlineUsers = OnlineUsers;
        message=null;
    }
    int getType() {
       return type;
  }
    List<String> getList(){
        return OnlineUsers;
    }
    String getMessage() {
      return message;
    }
}
