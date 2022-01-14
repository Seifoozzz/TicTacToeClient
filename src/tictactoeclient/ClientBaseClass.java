/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author asus
 */
public class ClientBaseClass {
    public static final int draw = 0;
    public static final int youWin = 1;
    public static final int youLose = 2;
    public static final int playing = 3;
    public static final String X= "X";
    public static final String O = "O";
    public static final String TIE= "tie";
    public static final String  move = "move";
    public static final String separator = ",";
    public static final String iWantToPlay = "iWantToPlay";
    public static final String  letsPlay= "letsPlay";
    public static final String  yourSymbole= "yourSymbole";
    public String Name;
    
    
    public void onSelect(JButton button,String symbole){
        button.setText(symbole);
    }
    
    public void onWin(){
        winnerSymbole = mySymbole;
        onFinsh();
       System.out.println("you win ");
    }
    
    public void onLose(){
        winnerSymbole = mySymbole.equals(X)?O:X;
        onFinsh();
        System.out.println("you lose ");
    }
    
    public void onDraw(){
        winnerSymbole = "TIE";
        onFinsh();
        System.out.println(" draw ");
    }
    
    public void onLetsPlay(){
        
    }
    
    public void onFinsh(){
        
    }
    
    
    
    
    public ClientBaseClass(ArrayList<JButton> buttons,String myName) {
        try {
            this.buttons = buttons;
            Name=myName;
            buttons.forEach(button->{
                button.addActionListener(this::onButtonClicked);
            });
            
            s = new Socket("127.0.0.1",5004);
            dis= new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(iWantToPlay+separator+myName);
            System.out.println("hellooooo"+iWantToPlay+separator+Name);
            new requestRecever().start();
        } catch (IOException ex) {
            Logger.getLogger(ClientBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    public int getGameState(){
        System.out.println("getGameState");
        System.out.println(currentTurn==yourSymbole);
        System.out.println(currentTurn);
        System.out.println(mySymbole);
        System.out.println("end getGameState");
            for (int i =0;i<=2;i++) {
            if (
                buttons.get(i * 3).getText().equals(currentTurn) &&
                buttons.get((i * 3) + 1).getText().equals(currentTurn)&&
                buttons.get((i * 3) + 2).getText().equals(currentTurn)||

                buttons.get(i).getText().equals(currentTurn)  &&
                buttons.get(i + 3).getText().equals(currentTurn) &&
                buttons.get(i + 6).getText().equals(currentTurn)
                ) 
            {
                
                return  currentTurn.equals(mySymbole)?youWin:youLose;
            }
        } 

        if (
            buttons.get(0).getText().equals(currentTurn) &&
            buttons.get(4).getText().equals(currentTurn) &&
            buttons.get(8).getText().equals(currentTurn) ||
                
            buttons.get(2).getText().equals(currentTurn) &&
            buttons.get(4).getText().equals(currentTurn) &&
            buttons.get(6).getText().equals(currentTurn)
            )
        {
             
            return currentTurn.equals(mySymbole)?youWin:youLose;
        }
        
        for (JButton button : buttons) {
            if(button.getText().equals(""))
               return playing;
        }
        
        return draw;
    }
    
    private void onButtonClicked(ActionEvent evt){
            JButton button = (JButton)evt.getSource();
            System.out.println(currentTurn.equals(mySymbole) && button.getText().equals("") && gameState==playing && !witingServer);
            if(currentTurn.equals(mySymbole) && button.getText().equals("") && gameState==playing && !witingServer){
                try {
                    int pos = buttons.indexOf(button);
                    dos.writeUTF(move+separator+pos+separator+currentTurn);
                    witingServer = true;
                } catch (IOException ex) {
                    Logger.getLogger(ClientBaseClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
       
    }
    
    class requestRecever extends Thread{
        public void run(){
            while(true){
                try {
                    String message = dis.readUTF();
                    String[] request = message.split(separator);
                    
                    
                    
                    if(request[0].equals(yourSymbole)){
                        // request[1] == my simbole
                       mySymbole = request[1];
                       otherPlayerName=request[2];
                    }else if(request[0].equals(letsPlay)){
                        // game.setVisible(true);
                        onLetsPlay();
                        currentTurn = X;
                    }else if(request[0].equals(move)){
                        // request[1] == position
                        // request[2] == symbole
                        // request[3] == nextSymbole
                        
                        System.out.println(request);
                        Integer pos = Integer.valueOf(request[1]);
                        //  buttons.get(pos).setText(request[2]);
                        onSelect(buttons.get(pos),currentTurn);
                        gameState = getGameState();

                        if(gameState == youWin)
                            onWin();
                        if(gameState == youLose)
                            onLose();
                        if(gameState == draw)
                            onDraw();
                        if(gameState == playing)
                           currentTurn = request[3];

                        witingServer = false;
                    }
                    
                    System.out.println(currentTurn);
                } catch (IOException ex) {
                    Logger.getLogger(ClientBaseClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                    
                    
                
                
            }
        }
    }

    
    
    public Socket s;
    public DataInputStream dis;
    public DataOutputStream dos;
    public   ArrayList<JButton> buttons;
    public boolean witingServer = false;
    public String mySymbole;
    public  int gameState = playing;
    public String currentTurn = X;
    public String winnerSymbole;
    public String otherPlayerName;
    
    
}
