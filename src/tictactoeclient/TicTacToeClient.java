/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class TicTacToeClient extends javax.swing.JFrame {

    /**
     * Creates new form TicTacToeClient
     */
    public static File localFile;
    public static  String dataLocl;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    LocalDataBase base = new LocalDataBase();
    String flag = "X"; //string to swap between x , o
    int xCounter = 0;  // int value to count the number of winning game to player x
    int oCounter =0;   //int value to count the number of winning game to player o
    int draw =0;
    static int drawCount=0;
    boolean cx = false;
    boolean co = false;
    String playerX00;
    String playerY00;
    String winner ="";
    boolean record = false;
    Boolean success=false;
    boolean end=false;
    public static String playerName;
    public static String playerEmail;
    String playingMode="";
    String easy ="";
    String hard ="";
    String medium="";
    int computerco=0;
  static  int playerGames ;
   static int playerWins;
   static int playerLoses;
    String secPlayer;
    public static int pWins;
     public static int pLose;
      public static int pGames;
    LinkedHashMap<Integer, String> moves = new LinkedHashMap<>();
    JButton [] arr = new JButton[9];
    public TicTacToeClient() {
        initComponents();
        setResizable(true);
        setSize(1200, 800);
        arr[0] = jButton0;
        arr[1] = jButton1;
        arr[2] = jButton2;
        arr[3] = jButton3;
        arr[4] = jButton4;
        arr[5] = jButton5;
        arr[6] = jButton6;
        arr[7] = jButton7;
        arr[8] = jButton8;
        for(int x = 0; x <9; x++ )
        {
            
            arr[x].setBackground(Color.LIGHT_GRAY);
        }
        
        
        restButtons();
              try {
            
            localFile = new File("local.txt");
            if(localFile.createNewFile())
            {
                System.out.println("file created "+ localFile.getName()+ localFile.getPath());
            }
            else
            {
                System.out.println("the file is already existed");
            }
            
            System.out.println("length: "+ localFile.length());
            } catch (IOException ex) {
            Logger.getLogger(LocalDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
              secondPlayer.setText("Computer");
    }
    public void restButtons(){
    
    for(int x = 0; x <9; x++ )
        {
            arr[x].setText("");
        }
    
    }
    // method to reset the score of two players
    public void gameScore()
    {
        playerX.setText(""+xCounter);
        playerO.setText(""+oCounter);
        
    }// end method gameScore 
    public void recordGame()
    {
           
            System.out.println("inside");
            LocalDataBase.fillMap(moves, arr);
           jButton9.setText("record game");
           jButton9.setBackground(Color.LIGHT_GRAY);
            record = false;
           dataLocl = LocalDataBase.readLocalFile(localFile);
            System.out.println(dataLocl+"the line inside recordGame");
        
        
    }
     public void recordGameHard()
    {
           
            System.out.println("inside");
           
           jButton9.setText("record game");
           jButton9.setBackground(Color.LIGHT_GRAY);
            record = false;
           dataLocl = LocalDataBase.readLocalFile(localFile);
            System.out.println(dataLocl+"the line inside recordGame");
        
        
    }
    
    public boolean isDraw()
    {
        if(drawCount<8)
        {
            drawCount++;
            System.out.print(""+drawCount);
            return false;
        }else
        {
            System.out.println(""+cx+""+co);
            if(cx&&co)
            {
                closeButtons();
                JOptionPane.showMessageDialog(this, "the game is draw try again ");
                if(record)
                {
                    
                    recordGame();
                    LocalDataBase.writeLocalGameSteps(localFile, dataLocl ,playerX00, xCounter, playerY00, oCounter, moves, winner);
                    
                }
                return true;
            }
        }
        return false;
        
    }
    public  boolean isDrawComputer()
    {
        if(drawCount<4)
        {
            drawCount++;
            System.out.print(""+drawCount);
            return false;
        } if(cx&&co)
            {
                closeButtons();
                JOptionPane.showMessageDialog(this, "the game is draw try again ");
                if(record)
                {
                    
                    recordGame();
                    LocalDataBase.writeLocalGameSteps(localFile, dataLocl ,playerX00, xCounter, playerY00, oCounter, moves, winner);
                    
                }
                return true;
            }
        return false;
        
    }
  
    
    
     public void choose_player()
    {
        
        playerX00 = firstPlayer.getText();
        playerY00 = secondPlayer.getText();
        System.out.println("\n first "+playerX00+"  "+playerY00);
        if(flag.equalsIgnoreCase("X"))
        {
            flag = "O";
           // jLabel46.setText(" O turn");
        }else
        {
            flag = "X";
            //jLabel46.setText(" X turn");
        }
            
        
    }// end choose_player
      public boolean xWin()
    {
        for(int x =0, r1=0, r2=1 ,r3=2; x <3; x++, r1=r1+3, r2=r2+3, r3=r3+3)
        {
            String b1 = arr[r1].getText();
            String b2 = arr[r2].getText();
            String b3 = arr[r3].getText();
            if(b1.equalsIgnoreCase("X") && b2.equalsIgnoreCase("X") && b3.equalsIgnoreCase("X"))
            {
                arr[r1].setBackground(Color.green);
                arr[r2].setBackground(Color.green);
                arr[r3].setBackground(Color.green);
                JOptionPane.showMessageDialog(this, "Player x is win ");
                xCounter++;
                winner =firstPlayer.getText();
                gameScore();
                closeButtons();
                cx = false;
                NewJFrame framVidoe = new NewJFrame();
                framVidoe.setVisible(true);
                framVidoe.createScane(playingMode,"x");
                framVidoe.setDefaultCloseOperation(2);
                if(record)
                {
                    
                    recordGame();
                    LocalDataBase.writeLocalGameSteps(localFile, dataLocl ,playerX00, xCounter, playerY00, oCounter, moves, winner);
                    
                }
                return true;
                
            }// end if
            
        }//end for loop to check win in rows
        for(int x =0, c1=0, c2=3 ,c3=6; x <3; x++, c1++, c2++, c3++)
        {
            String b1 = arr[c1].getText();
            String b2 = arr[c2].getText();
            String b3 = arr[c3].getText();
            if(b1.equalsIgnoreCase("X") && b2.equalsIgnoreCase("X") && b3.equalsIgnoreCase("X"))
            {
                arr[c1].setBackground(Color.green);
                arr[c2].setBackground(Color.green);
                arr[c3].setBackground(Color.green);
                JOptionPane.showMessageDialog(this, "Player x is win ");
                xCounter++;
                gameScore();
                closeButtons();
                cx = false;
                winner =firstPlayer.getText();
                NewJFrame framVidoe = new NewJFrame();
                framVidoe.setVisible(true);
                framVidoe.createScane(playingMode,"x");
                framVidoe.setDefaultCloseOperation(2);
                if(record)
                {
                    
                    recordGame();
                    LocalDataBase.writeLocalGameSteps(localFile, dataLocl ,playerX00, xCounter, playerY00, oCounter, moves, winner);
                    
                }
                return true;
            }// end if
            
        }//end for loop to check win in columes
        for(int x =0, d1=0, d2=4 , d3=8  ; x <2; x++ , d1=d1+2, d3=d3-2)
        {
            String b1 = arr[d1].getText();
            String b2 = arr[d2].getText();
            String b3 = arr[d3].getText();
            if(b1.equalsIgnoreCase("X") && b2.equalsIgnoreCase("X") && b3.equalsIgnoreCase("X"))
            {
                arr[d1].setBackground(Color.green);
                arr[d2].setBackground(Color.green);
                arr[d3].setBackground(Color.green);
                JOptionPane.showMessageDialog(this, "Player x is win ");
                xCounter++;
                gameScore();
                closeButtons();
                winner =firstPlayer.getText();
                cx = false;
                NewJFrame framVidoe = new NewJFrame();
                framVidoe.setVisible(true);
                framVidoe.createScane(playingMode,"x");
                framVidoe.setDefaultCloseOperation(2);
                if(record)
                {
                    
                    recordGame();
                    LocalDataBase.writeLocalGameSteps(localFile, dataLocl ,playerX00, xCounter, playerY00, oCounter, moves, winner);
                    
                }
              return true;
            }// end if
            
        }//end for loop to check win in diagonal
        cx = true;
        return false;
    }// end xwin method
    
    public boolean  oWin()
    {
        for (int x = 0, r1 = 0, r2 = 1, r3 = 2; x < 3; x++, r1 = r1 + 3, r2 = r2 + 3, r3 = r3 + 3) {
            String b1 = arr[r1].getText();
            String b2 = arr[r2].getText();
            String b3 = arr[r3].getText();
            if (b1.equalsIgnoreCase("O") && b2.equalsIgnoreCase("O") && b3.equalsIgnoreCase("O")) {
                arr[r1].setBackground(Color.green);
                arr[r2].setBackground(Color.green);
                arr[r3].setBackground(Color.green);
                JOptionPane.showMessageDialog(this, "Player O is win ");
                oCounter++;
                gameScore();
                closeButtons();
                co = false;
                winner =secondPlayer.getText();
                NewJFrame framVidoe = new NewJFrame();
                framVidoe.setVisible(true);
                framVidoe.createScane(playingMode,"o");
                framVidoe.setDefaultCloseOperation(2);
                if(record)
                {
                    
                    recordGame();
                    LocalDataBase.writeLocalGameSteps(localFile, dataLocl ,playerX00, xCounter, playerY00, oCounter, moves, winner);
                    
                }
                return true;
                 
            }// end if
            
        }//end for loop to check win in rows
        for (int x = 0, c1 = 0, c2 = 3, c3 = 6; x < 3; x++, c1++, c2++, c3++) {
            String b1 = arr[c1].getText();
            String b2 = arr[c2].getText();
            String b3 = arr[c3].getText();
            if (b1.equalsIgnoreCase("O") && b2.equalsIgnoreCase("O") && b3.equalsIgnoreCase("O")) {
                arr[c1].setBackground(Color.green);
                arr[c2].setBackground(Color.green);
                arr[c3].setBackground(Color.green);
                JOptionPane.showMessageDialog(this, "Player O is win ");
                oCounter++;
                gameScore();
                closeButtons();
                winner =secondPlayer.getText();
                co = false;
                NewJFrame framVidoe = new NewJFrame();
                framVidoe.setVisible(true);
                framVidoe.createScane(playingMode,"o");
                framVidoe.setDefaultCloseOperation(2);
                if(record)
                {
                    
                    recordGame();
                    LocalDataBase.writeLocalGameSteps(localFile, dataLocl ,playerX00, xCounter, playerY00, oCounter, moves, winner);
                    
                }
                return true;
            }// end if
            
        }//end for loop to check win in columes
        for (int x = 0, d1 = 0, d2 = 4, d3 = 8; x < 2; x++, d1 = d1 + 2, d3 = d3 - 2) {
            String b1 = arr[d1].getText();
            String b2 = arr[d2].getText();
            String b3 = arr[d3].getText();
            if (b1.equalsIgnoreCase("O") && b2.equalsIgnoreCase("O") && b3.equalsIgnoreCase("O")) {
                arr[d1].setBackground(Color.green);
                arr[d2].setBackground(Color.green);
                arr[d3].setBackground(Color.green);
                JOptionPane.showMessageDialog(this, "Player O is win ");
                oCounter++;
                gameScore();
                closeButtons();
                winner =secondPlayer.getText();
                co = false;
                NewJFrame framVidoe = new NewJFrame();
                framVidoe.setVisible(true);
                framVidoe.createScane(playingMode,"o");
                framVidoe.setDefaultCloseOperation(2);
                if(record)
                {
                    
                    recordGame();
                    LocalDataBase.writeLocalGameSteps(localFile, dataLocl ,playerX00, xCounter, playerY00, oCounter, moves, winner);
                    
                }
                return true;
                
            }// end if
           System.out.println();
        }//end for loop to check win in diagonal
        co = true;
     return false;
    }// end xwin method
    
    public void closeButtons()
    {
        for(int x = 0; x <9; x++ )
        {
            arr[x].setEnabled(false);
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

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        mycards = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        passwordFeild = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();
        signUpLabel = new javax.swing.JLabel();
        signinLabel = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        signUserName = new javax.swing.JTextField();
        signEmail = new javax.swing.JTextField();
        signPass = new javax.swing.JPasswordField();
        confPass = new javax.swing.JPasswordField();
        signUpScreenLabel = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        onlineLabel = new javax.swing.JLabel();
        offlineLabel = new javax.swing.JLabel();
        computerLabel = new javax.swing.JLabel();
        Profile = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        easyLabel = new javax.swing.JLabel();
        mediumLabel = new javax.swing.JLabel();
        hardLabel = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        gamesLabel = new javax.swing.JLabel();
        winLabel = new javax.swing.JLabel();
        loseLabel = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        ProfileUserName = new javax.swing.JTextField();
        profileUserEmail = new javax.swing.JTextField();
        profileGames = new javax.swing.JLabel();
        profileWins = new javax.swing.JLabel();
        profileLose = new javax.swing.JLabel();
        btnProfileBack = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton0 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        newGameBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();
        firstPlayer = new javax.swing.JLabel();
        secondPlayer = new javax.swing.JLabel();
        drawcont = new javax.swing.JLabel();
        playerX = new javax.swing.JLabel();
        playerO = new javax.swing.JLabel();
        drawlable = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Source Sans Pro Black", 3, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 153));
        jLabel1.setText("Tic Tac Toe ");

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 3, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setText("X");

        jLabel5.setFont(new java.awt.Font("Chiller", 3, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 255));
        jLabel5.setText("O");

        jLabel6.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Name ");

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 0, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Password");

        jLabel8.setFont(new java.awt.Font("SimSun", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("don't have an account");

        jLabel9.setFont(new java.awt.Font("Tempus Sans ITC", 2, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 153));
        jLabel9.setText("Sign up");

        jLabel10.setFont(new java.awt.Font("Tempus Sans ITC", 2, 48)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sign in");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel10MousePressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 153, 51));
        jLabel11.setText("==================");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(234, 234, 234))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(193, 193, 193))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 762, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(217, 217, 217))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)))))
                .addGap(42, 42, 42)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(136, 136, 136))
        );

        jPanel1.add(jPanel2, "card2");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mycards.setLayout(new java.awt.CardLayout());

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));

        jLabel2.setFont(new java.awt.Font("Source Sans Pro Black", 3, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 153));
        jLabel2.setText("Tic Tac Toe ");

        jLabel3.setFont(new java.awt.Font("Tempus Sans ITC", 3, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("X");

        jLabel12.setFont(new java.awt.Font("Chiller", 3, 48)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 255));
        jLabel12.setText("O");

        jLabel13.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("E-mail");

        jLabel14.setFont(new java.awt.Font("Tempus Sans ITC", 0, 48)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Password");

        passwordFeild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFeildActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("SimSun", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("don't have an account");

        signUpLabel.setFont(new java.awt.Font("Tempus Sans ITC", 2, 36)); // NOI18N
        signUpLabel.setForeground(new java.awt.Color(255, 255, 153));
        signUpLabel.setText("Sign up");
        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                signUpLabelMousePressed(evt);
            }
        });

        signinLabel.setFont(new java.awt.Font("Tempus Sans ITC", 2, 48)); // NOI18N
        signinLabel.setForeground(new java.awt.Color(255, 255, 255));
        signinLabel.setText("Login");
        signinLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                signinLabelMousePressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 153, 51));
        jLabel18.setText("========================");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(jLabel14))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordFeild, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(signinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(385, 385, 385)
                        .addComponent(jLabel15)
                        .addGap(62, 62, 62)
                        .addComponent(signUpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel18)))
                .addContainerGap(137, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(357, 357, 357))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(passwordFeild, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addComponent(signinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(signUpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(221, Short.MAX_VALUE))
        );

        mycards.add(jPanel6, "card2");

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setForeground(new java.awt.Color(204, 204, 255));

        jLabel17.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Name");

        jLabel20.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Email");

        jLabel21.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Password");

        jLabel22.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Confirm Password");

        signEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signEmailActionPerformed(evt);
            }
        });

        signPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signPassActionPerformed(evt);
            }
        });

        confPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confPassActionPerformed(evt);
            }
        });

        signUpScreenLabel.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        signUpScreenLabel.setForeground(new java.awt.Color(255, 255, 102));
        signUpScreenLabel.setText("Sign Up");
        signUpScreenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                signUpScreenLabelMousePressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 2, 48)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 102, 0));
        jLabel24.setText("o");

        jLabel25.setFont(new java.awt.Font("Chiller", 3, 48)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(204, 204, 255));
        jLabel25.setText("X");

        jLabel26.setFont(new java.awt.Font("Tahoma", 2, 36)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(204, 255, 204));
        jLabel26.setText("o");

        jLabel27.setFont(new java.awt.Font("Tahoma", 2, 36)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(204, 255, 204));
        jLabel27.setText("X");

        jLabel28.setFont(new java.awt.Font("Tempus Sans ITC", 3, 36)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(204, 204, 0));
        jLabel28.setText("o");

        jLabel29.setFont(new java.awt.Font("Tempus Sans ITC", 3, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 204, 255));
        jLabel29.setText("X");

        jLabel30.setFont(new java.awt.Font("Chiller", 2, 48)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(204, 255, 204));
        jLabel30.setText("x");

        jLabel31.setFont(new java.awt.Font("Chiller", 3, 48)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(102, 255, 204));
        jLabel31.setText("o");

        jLabel32.setFont(new java.awt.Font("Tahoma", 2, 48)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(204, 0, 255));
        jLabel32.setText("o");

        jLabel33.setFont(new java.awt.Font("Tahoma", 2, 48)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 204, 0));
        jLabel33.setText("X");

        jLabel34.setFont(new java.awt.Font("Tahoma", 2, 48)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 204, 255));
        jLabel34.setText("X");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(442, 442, 442)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(223, 223, 223)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(202, 202, 202)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)
                        .addGap(58, 58, 58)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(confPass, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addGap(46, 46, 46)
                                        .addComponent(signEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addGap(18, 18, 18)
                                        .addComponent(signPass, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(signUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(319, 319, 319)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(271, 271, 271)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(533, 533, 533)
                        .addComponent(signUpScreenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27)
                        .addComponent(jLabel26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel30))
                                .addGap(37, 37, 37))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(signUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(signEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(53, 53, 53))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(signPass, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(61, 61, 61)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confPass, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)))
                    .addComponent(jLabel29))
                .addGap(31, 31, 31)
                .addComponent(signUpScreenLabel)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        mycards.add(jPanel3, "card3");

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 153, 51));
        jLabel19.setText("========================");

        jLabel35.setFont(new java.awt.Font("Source Sans Pro Black", 2, 48)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 153));
        jLabel35.setText("Tic Tac Toe ");

        jLabel36.setFont(new java.awt.Font("Tempus Sans ITC", 2, 48)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Select mode ");

        onlineLabel.setFont(new java.awt.Font("Chiller", 2, 48)); // NOI18N
        onlineLabel.setForeground(new java.awt.Color(102, 204, 255));
        onlineLabel.setText("Online");
        onlineLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                onlineLabelMousePressed(evt);
            }
        });

        offlineLabel.setFont(new java.awt.Font("Chiller", 2, 48)); // NOI18N
        offlineLabel.setForeground(new java.awt.Color(204, 204, 255));
        offlineLabel.setText("Offline");
        offlineLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                offlineLabelMousePressed(evt);
            }
        });

        computerLabel.setFont(new java.awt.Font("Chiller", 2, 48)); // NOI18N
        computerLabel.setForeground(new java.awt.Color(204, 255, 204));
        computerLabel.setText("Computer");
        computerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                computerLabelMousePressed(evt);
            }
        });

        Profile.setBackground(new java.awt.Color(204, 204, 255));
        Profile.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Profile.setForeground(new java.awt.Color(255, 255, 0));
        Profile.setText("Your Profile  ");
        Profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ProfileMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(479, 479, 479)
                                    .addComponent(computerLabel))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(492, 492, 492)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(onlineLabel)
                                        .addComponent(offlineLabel)))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(436, 436, 436)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(124, 124, 124))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addGap(119, 119, 119)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(62, 62, 62)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(334, 334, 334)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(Profile)))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(Profile)))
                .addGap(37, 37, 37)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(onlineLabel)
                .addGap(40, 40, 40)
                .addComponent(offlineLabel)
                .addGap(38, 38, 38)
                .addComponent(computerLabel)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        mycards.add(jPanel4, "card4");

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));

        easyLabel.setFont(new java.awt.Font("Tempus Sans ITC", 3, 48)); // NOI18N
        easyLabel.setForeground(new java.awt.Color(255, 255, 255));
        easyLabel.setText("easy");
        easyLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                easyLabelMousePressed(evt);
            }
        });

        mediumLabel.setFont(new java.awt.Font("Tempus Sans ITC", 3, 48)); // NOI18N
        mediumLabel.setForeground(new java.awt.Color(153, 153, 255));
        mediumLabel.setText("medium");
        mediumLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mediumLabelMousePressed(evt);
            }
        });

        hardLabel.setFont(new java.awt.Font("Tempus Sans ITC", 3, 48)); // NOI18N
        hardLabel.setForeground(new java.awt.Color(0, 204, 204));
        hardLabel.setText("hard");
        hardLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                hardLabelMousePressed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Source Sans Pro Black", 3, 48)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 153));
        jLabel43.setText("Tic Tac Toe ");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 153, 51));
        jLabel44.setText("========================");

        jLabel45.setFont(new java.awt.Font("Snap ITC", 2, 48)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 51));
        jLabel45.setText("Select Level");

        jLabel23.setFont(new java.awt.Font("Tempus Sans ITC", 3, 48)); // NOI18N
        jLabel23.setText("back");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel23MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(easyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(368, 368, 368))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(501, 501, 501)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hardLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(422, 422, 422)
                        .addComponent(jLabel43))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel44))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(456, 456, 456)
                        .addComponent(mediumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel44)
                .addGap(51, 51, 51)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(easyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(mediumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(hardLabel)
                .addGap(18, 18, 18)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        mycards.add(jPanel5, "card5");

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));

        jLabel37.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Player Name ");

        jLabel38.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Player Email");

        gamesLabel.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        gamesLabel.setForeground(new java.awt.Color(255, 255, 255));
        gamesLabel.setText("Games ");

        winLabel.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        winLabel.setForeground(new java.awt.Color(255, 255, 255));
        winLabel.setText("Win");

        loseLabel.setFont(new java.awt.Font("Tempus Sans ITC", 1, 48)); // NOI18N
        loseLabel.setForeground(new java.awt.Color(255, 255, 255));
        loseLabel.setText("Lose");

        jLabel42.setFont(new java.awt.Font("Ravie", 0, 48)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 153));
        jLabel42.setText("Player Profile");

        profileGames.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        profileGames.setForeground(new java.awt.Color(51, 255, 0));
        profileGames.setText("0");

        profileWins.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        profileWins.setForeground(new java.awt.Color(0, 255, 0));
        profileWins.setText("0");

        profileLose.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        profileLose.setForeground(new java.awt.Color(51, 255, 0));
        profileLose.setText("0");

        btnProfileBack.setFont(new java.awt.Font("Tempus Sans ITC", 3, 48)); // NOI18N
        btnProfileBack.setText("back");
        btnProfileBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnProfileBackMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ProfileUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(profileUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 235, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnProfileBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(gamesLabel)
                                .addGap(192, 192, 192)
                                .addComponent(winLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(167, 167, 167))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(profileGames)
                .addGap(319, 319, 319)
                .addComponent(profileWins)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(profileLose)
                .addGap(226, 226, 226))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProfileUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(profileUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel38)
                        .addGap(45, 45, 45)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gamesLabel)
                    .addComponent(winLabel)
                    .addComponent(loseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profileGames)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(profileWins)
                        .addComponent(profileLose)))
                .addGap(59, 59, 59)
                .addComponent(btnProfileBack, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        mycards.add(jPanel7, "card7");

        jPanel8.setBackground(new java.awt.Color(0, 153, 153));

        jLabel46.setFont(new java.awt.Font("Source Sans Pro Black", 3, 48)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 153));
        jLabel46.setText("Tic Tac Toe");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 153, 51));
        jLabel47.setText("=========================");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("MV Boli", 1, 48)); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("MV Boli", 1, 48)); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("MV Boli", 1, 48)); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("MV Boli", 1, 48)); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton0.setBackground(new java.awt.Color(255, 255, 255));
        jButton0.setFont(new java.awt.Font("MV Boli", 1, 48)); // NOI18N
        jButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton0ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("MV Boli", 1, 48)); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("MV Boli", 1, 48)); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("MV Boli", 1, 48)); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("MV Boli", 1, 48)); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        newGameBtn.setBackground(new java.awt.Color(255, 255, 153));
        newGameBtn.setFont(new java.awt.Font("Snap ITC", 0, 48)); // NOI18N
        newGameBtn.setForeground(new java.awt.Color(102, 102, 255));
        newGameBtn.setText("New Game");
        newGameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameBtnActionPerformed(evt);
            }
        });

        resetBtn.setBackground(new java.awt.Color(255, 255, 153));
        resetBtn.setFont(new java.awt.Font("Snap ITC", 0, 48)); // NOI18N
        resetBtn.setForeground(new java.awt.Color(0, 204, 204));
        resetBtn.setText("Reset");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        exitBtn.setBackground(new java.awt.Color(255, 255, 153));
        exitBtn.setFont(new java.awt.Font("Snap ITC", 0, 48)); // NOI18N
        exitBtn.setText("Exit");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        firstPlayer.setFont(new java.awt.Font("Tempus Sans ITC", 2, 36)); // NOI18N
        firstPlayer.setForeground(new java.awt.Color(255, 255, 255));
        firstPlayer.setText("Player X");

        secondPlayer.setFont(new java.awt.Font("Tempus Sans ITC", 2, 36)); // NOI18N
        secondPlayer.setForeground(new java.awt.Color(255, 255, 255));
        secondPlayer.setText("Player O");

        drawcont.setFont(new java.awt.Font("Tempus Sans ITC", 2, 36)); // NOI18N
        drawcont.setForeground(new java.awt.Color(204, 255, 204));
        drawcont.setText("Draw ");

        playerX.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        playerX.setForeground(new java.awt.Color(255, 255, 0));
        playerX.setText("0");

        playerO.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        playerO.setForeground(new java.awt.Color(255, 255, 51));
        playerO.setText("0");

        drawlable.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        drawlable.setForeground(new java.awt.Color(255, 255, 102));
        drawlable.setText("0");

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton9.setText("Record");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton10.setText("History");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 120, Short.MAX_VALUE)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 983, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton0, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(132, 132, 132)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(resetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(newGameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(447, 447, 447)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(586, 586, 586)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(drawcont)
                                    .addComponent(secondPlayer)
                                    .addComponent(firstPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(playerX)
                                    .addComponent(playerO)
                                    .addComponent(drawlable)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton0, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(firstPlayer)
                                    .addComponent(playerX))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(secondPlayer)
                                    .addComponent(playerO))))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(drawcont)
                            .addComponent(drawlable))
                        .addGap(18, 18, 18)
                        .addComponent(newGameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(162, Short.MAX_VALUE))
        );

        mycards.add(jPanel8, "card6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mycards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mycards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MousePressed
       
    }//GEN-LAST:event_jLabel10MousePressed

    private void signinLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signinLabelMousePressed
        // TODO add your handling code here:

       try {
            String email = emailField.getText().trim();
            String password = passwordFeild.getText().trim();
            String key=signinLabel.getText();
            dataOutputStream.writeUTF(key);
            System.out.println(key);
            dataOutputStream.writeUTF(email);
            dataOutputStream.writeUTF(password);        // TODO add your handling code here:
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
          try {
            // TODO add your handling code here:
            success=dataInputStream.readBoolean();
           
            System.out.println(success);
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(success){
          /* try {
               playerEmail=dataInputStream.readUTF();
               playerName= dataInputStream.readUTF();
               playerGames=dataInputStream.readInt();
               playerWins=dataInputStream.readInt();
               playerLoses=dataInputStream.readInt();
               ProfileUserName.setText(playerName);
               profileUserEmail.setText(playerEmail);
               profileGames.setText(""+playerGames);
               profileWins.setText(""+playerWins);
               profileLose.setText(""+playerLoses);
           } catch (IOException ex) {
               Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
           }
          */
            JOptionPane.showMessageDialog(null, "Logged in Succcessfully");
         CardLayout card=(CardLayout)mycards.getLayout();
        for(int i=0;i<2;i++){
          card.next(mycards);
          
        }
       
        
        }else JOptionPane.showMessageDialog(null, "Incorrect user name or password");


       
    }//GEN-LAST:event_signinLabelMousePressed

    private void signPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_signPassActionPerformed

    private void confPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confPassActionPerformed

    private void signUpLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpLabelMousePressed
        // TODO add your handling code here:
        CardLayout card=(CardLayout)mycards.getLayout();
        card.next(mycards);
    }//GEN-LAST:event_signUpLabelMousePressed

    private void passwordFeildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFeildActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFeildActionPerformed

    private void computerLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_computerLabelMousePressed
        // TODO add your handling code here:
        CardLayout card=(CardLayout)mycards.getLayout();
        card.next(mycards);
    }//GEN-LAST:event_computerLabelMousePressed

    private void onlineLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onlineLabelMousePressed
        //try {
            // TODO add your handling code here:
         /*  String label = onlineLabel.getText();
        try {
            //dataOutputStream.writeUTF("ready");
            dataOutputStream.writeUTF("ready");
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
            CardLayout card=(CardLayout)mycards.getLayout();
            card.last(mycards);*/
            
          new online().setVisible(true);
            
        //} catch (IOException ex) {
           // Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
       // }
       
    }//GEN-LAST:event_onlineLabelMousePressed

    private void offlineLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineLabelMousePressed
        // TODO add your handling code here:
      /* this.setVisible(false);
       JFrame j = new NewJFrame();
       j.setVisible(true);
       
       j.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosed(WindowEvent e) {
               TicTacToeClient.this.setVisible(true);
           }

       });*/
       
       secPlayer = JOptionPane.showInputDialog("please enter the second player name ?");
         CardLayout card=(CardLayout)mycards.getLayout();
         card.last(mycards);
         playingMode=offlineLabel.getText();
         secondPlayer.setText(secPlayer);
         firstPlayer.setText(playerName);
        
    }//GEN-LAST:event_offlineLabelMousePressed

    private void signUpScreenLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpScreenLabelMousePressed
            
            // TODO add your handling code here:
            
            // TODO add your handling code here:
           String email = signEmail.getText().trim();
         String userName = signUserName.getText().trim();
           String password = signPass.getText().trim();
           String confirmPass = confPass.getText().trim();
            String key =signUpScreenLabel.getText();
           
            if(password.equals(confirmPass)){
                
                try {
                    dataOutputStream.writeUTF(key);
                     System.out.println(key);
                    dataOutputStream.writeUTF(userName);
                    dataOutputStream.writeUTF(email);
                   dataOutputStream.writeUTF(password);
                    success=dataInputStream.readBoolean();
                    if(success){
                   /*   playerEmail=dataInputStream.readUTF();
                      playerName= dataInputStream.readUTF();
                      playerGames=dataInputStream.readInt();
                      playerWins=dataInputStream.readInt();
                      playerLoses=dataInputStream.readInt();
                      ProfileUserName.setText(playerName);
                      profileUserEmail.setText(playerEmail);
                      profileGames.setText(""+playerGames);
                      profileWins.setText(""+playerWins);
                      profileLose.setText(""+playerLoses);*/
                        JOptionPane.showMessageDialog(null, "registered successfully");
                        CardLayout card=(CardLayout)mycards.getLayout();
                        card.next(mycards);
                          
                    } else  JOptionPane.showMessageDialog(null, "Unsuccessfull Regestration Review Your Data and try again");
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
                }
              
       } else  JOptionPane.showMessageDialog(null, "password didn't match try again");

                
            
            
    }//GEN-LAST:event_signUpScreenLabelMousePressed
        
                                         

    private void signEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_signEmailActionPerformed

    private void easyLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_easyLabelMousePressed
        // TODO add your handling code here:
        CardLayout card=(CardLayout)mycards.getLayout();
        card.last(mycards);
        secondPlayer.setText("Computer");
        firstPlayer.setText(playerName);
        easy = easyLabel.getText();
    }//GEN-LAST:event_easyLabelMousePressed

    private void mediumLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mediumLabelMousePressed
        // TODO add your handling code here:
        CardLayout card=(CardLayout)mycards.getLayout();
        card.last(mycards);
        secondPlayer.setText("Computer");
        firstPlayer.setText(playerName);
        medium = mediumLabel.getText();
    }//GEN-LAST:event_mediumLabelMousePressed

    private void hardLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hardLabelMousePressed
        // TODO add your handling code here:
        CardLayout card=(CardLayout)mycards.getLayout();
        card.last(mycards);
        secondPlayer.setText("Computer");
        firstPlayer.setText(playerName);
        hard = hardLabel.getText();
    }//GEN-LAST:event_hardLabelMousePressed

    private void jLabel23MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MousePressed
        // TODO add your handling code here:
         CardLayout card=(CardLayout)mycards.getLayout();
        card.previous(mycards);
        easy ="";
        hard ="";
        medium="";
    }//GEN-LAST:event_jLabel23MousePressed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        try {
            // TODO add your handling code here:
            pWins=Integer.parseInt(profileWins.getText());
            pWins+=xCounter;
            profileWins.setText(""+pWins);
            pLose=Integer.parseInt(profileLose.getText());
            pLose+=oCounter;
            profileLose.setText(""+pLose);
            pGames=pWins+pLose;
            profileGames.setText(""+pGames);
            String key = exitBtn.getText();
            String email=profileUserEmail.getText();
            dataOutputStream.writeUTF(key);
            dataOutputStream.writeUTF(email);
            dataOutputStream.writeInt(pGames);
            dataOutputStream.writeInt(pWins);
            dataOutputStream.writeInt(pLose);
            
            
                   
            
            xCounter =0;
            oCounter = 0;
            draw = 0;
            gameScore();
            restButtons();
            for(int x = 0; x <9; x++ )
            {
                arr[x].setText("");
                arr[x].setEnabled(true);
                arr[x].setBackground(Color.LIGHT_GRAY);
            }
            secondPlayer.setText("player O");
            firstPlayer.setText("Player X");
            CardLayout card=(CardLayout)mycards.getLayout();
            for(int i=0;i<3;i++){
                card.next(mycards);
            }
            playingMode="";
            flag="X";
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
        // TODO add your handling code here:
      xCounter =0;
        oCounter = 0;
        draw = 0;
        drawCount = 0;
        gameScore();
        base.comInd = -1;

        base.is_loss = false;
        base.is_win = false;
        base.is_full = false;
        
        for (int i = 0; i < 9; i++) {
            base.mnmx.xo[i] = "0";

        }
        
        flag = "X";
        computerco =0;
        end = false;
       
         
        for(int x = 0; x <9; x++ )
        {
            arr[x].setText("");
            arr[x].setEnabled(true);
            arr[x].setBackground(Color.LIGHT_GRAY);
        }

        }   
    }//GEN-LAST:event_exitBtnActionPerformed

    private void jButton0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton0ActionPerformed
        // TODO add your handling code here:
        int n = 0;
        if(playingMode.equals("Offline")){
        String s = jButton0.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton0.setText(flag);
            moves.put(0, flag);
            choose_player();
            xWin();
            oWin();
            isDraw();
        }
         draw = 0;
        }
        else{
            if(easy.equalsIgnoreCase("easy")||medium.equalsIgnoreCase("medium"))
            {
                String s = jButton0.getText();
            
        if(s.equalsIgnoreCase(""))
        {
            
            jButton0.setText(flag);
            moves.put(0, flag);
             end  =xWin();
            if((computerco<4)&&!end)
            {
                computerco++;
                ComputerMode.generateRand(arr, flag, moves);
            }
            
            
            end =oWin();
            isDrawComputer();
           
        }
            }else if(hard.equalsIgnoreCase("hard")){
        
            base.playclick(jButton0, arr, n, record, moves );
            
            if(base.is_win)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                 playerO.setText(""+base.mnmx.oppScore);
                if(record)
            {
                recordGameHard();
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
            }
            }
            if(base.is_loss)
            {
                String s =firstPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerX.setText(""+base.mnmx.Score);
                if(record)
            {
                recordGameHard();
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
            }
            }
            if(base.is_full)
            {
                 JOptionPane.showMessageDialog(this, "the game is Draw please try again");
                 drawlable.setText(""+base.mnmx.tieScore);
                 if(record)
            {
                recordGameHard();
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
            }
            }
        }
            
         draw = 0;
        }
    }//GEN-LAST:event_jButton0ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int n = 3;
        if(playingMode.equals("Offline")){
        String s = jButton3.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton3.setText(flag);
            moves.put(3, flag);
            choose_player();
            xWin();
            oWin();
            isDraw();
        }
        }
        else 
        {
            if(easy.equalsIgnoreCase("easy")||medium.equalsIgnoreCase("medium"))
            {
                String s = jButton3.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton3.setText(flag);
            moves.put(3, flag);
            end  =xWin();
            if((computerco<4)&&!end)
            {
                computerco++;
                ComputerMode.generateRand(arr, flag, moves);
            }
             
            end =oWin();
            isDrawComputer();
            
        }
            }else if(hard.equalsIgnoreCase("hard")){
            
                base.playclick(jButton3, arr,n, record, moves );
                
             if(base.is_win)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                 playerO.setText(""+base.mnmx.oppScore);
                if(record)
            {
                recordGameHard();
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
            }
            }
            if(base.is_loss)
            {
                String s =firstPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerX.setText(""+base.mnmx.Score);
                if(record)
            {
                recordGameHard();
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
            }
            }
            if(base.is_full)
            {
                 JOptionPane.showMessageDialog(this, "the game is Draw please try again");
                 drawlable.setText(""+base.mnmx.tieScore);
                 if(record)
            {
                recordGameHard();
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
            }
            }
            }
            
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int n = 1;
         if(playingMode.equals("Offline")){
        String s = jButton1.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton1.setText(flag);
            moves.put(1, flag);
            choose_player();
            xWin();
            oWin();
            isDraw();
        }
        }
        else
         {
             if(easy.equalsIgnoreCase("easy")||medium.equalsIgnoreCase("medium"))
             {
                 String s = jButton1.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton1.setText(flag);
            moves.put(1, flag);
            end =xWin();
            if((computerco<4)&&!end)
            {
                computerco++;
                ComputerMode.generateRand(arr, flag, moves);
            }
             
            end=oWin();
            isDrawComputer();
         
        }
             }else if(hard.equalsIgnoreCase("hard")){
            base.playclick(jButton1, arr, n, record, moves );
            
            if(base.is_win)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                 playerO.setText(""+base.mnmx.oppScore);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_loss)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerX.setText(""+base.mnmx.Score);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_full)
            {
                 JOptionPane.showMessageDialog(this, "the game is Draw please try again");
                 drawlable.setText(""+base.mnmx.tieScore);
                 if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
        }
             
         }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int n = 2;
        if(playingMode.equals("Offline")){
        String s = jButton2.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton2.setText(flag);
            moves.put(2, flag);
            choose_player();
            xWin();
            oWin();
            isDraw();
        }
        }
        else{
            if(easy.equalsIgnoreCase("easy")||medium.equalsIgnoreCase("medium"))
            {
                String s = jButton2.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton2.setText(flag);
            moves.put(2, flag);
             end =xWin();
            if((computerco<4)&&!end)
            {
                computerco++;
                ComputerMode.generateRand(arr, flag, moves);
            }
           
            end =oWin();
            isDrawComputer();
          
        }
            }else if(hard.equalsIgnoreCase("hard")){
            base.playclick(jButton2, arr, n, record, moves );
            
            if(base.is_win)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerO.setText(""+base.mnmx.oppScore);
                if(record)
                
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_loss)
            {
                String s =firstPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerX.setText(""+base.mnmx.Score);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_full)
            {
                 JOptionPane.showMessageDialog(this, "the game is Draw please try again");
                 drawlable.setText(""+base.mnmx.tieScore);
                 if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            
        }
            
            
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        System.out.print(hard);
        int n = 4;
         if(playingMode.equals("Offline")){
        String s = jButton4.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton4.setText(flag);
            moves.put(4, flag);
            choose_player();
            xWin();
            oWin();
            isDraw();
        }
        }
        else {
             if(easy.equalsIgnoreCase("easy")||medium.equalsIgnoreCase("medium"))
             {
                 String s = jButton4.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton4.setText(flag);
            moves.put(4, flag);
            end =xWin();
            if((computerco<4)&&!end)
            {
                computerco++;
                ComputerMode.generateRand(arr, flag, moves);
            }
             
            end =oWin();
           isDrawComputer();
          
        }
             }else if(hard.equalsIgnoreCase("hard")){
                 System.out.print("inside hard");
                 base.playclick(jButton4, arr, n, record, moves );
            
            if(base.is_win)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                 playerO.setText(""+base.mnmx.oppScore);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_loss)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerX.setText(""+base.mnmx.Score);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_full)
            {
                 JOptionPane.showMessageDialog(this, "the game is Draw please try again");
                 drawlable.setText(""+base.mnmx.tieScore);
                 if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
             }
             
         }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int n = 5;
          if(playingMode.equals("Offline")){
        String s = jButton5.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton5.setText(flag);
            moves.put(5, flag);
            choose_player();
            xWin();
            oWin();
            isDraw();
        }
        }
        else {
              if(easy.equalsIgnoreCase("easy")||medium.equalsIgnoreCase("medium"))
              {
                  String s = jButton5.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton5.setText(flag);
            moves.put(5, flag);
            end =xWin();
            if((computerco<4)&&!end)
            {
                computerco++;
                ComputerMode.generateRand(arr, flag, moves);
            }
            
            end =oWin();
            isDrawComputer();
          
            
            
            
        }
              }else if(hard.equalsIgnoreCase("hard")){
            base.playclick(jButton5, arr, n, record, moves );
           
            if(base.is_win)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                 playerO.setText(""+base.mnmx.oppScore);
                 if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_loss)
            {
                String s =firstPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerX.setText(""+base.mnmx.Score);
                 if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_full)
            {
                 JOptionPane.showMessageDialog(this, "the game is Draw please try again");
                 drawlable.setText(""+base.mnmx.tieScore);
                  if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
               }
              
          }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int n = 6;
         if(playingMode.equals("Offline")){
        String s = jButton6.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton6.setText(flag);
            moves.put(6, flag);
            choose_player();
            xWin();
            oWin();
            isDraw();
        }
        }
        else {
             if(easy.equalsIgnoreCase("easy")||medium.equalsIgnoreCase("medium"))
             {
                 String s = jButton6.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton6.setText(flag);
            moves.put(6, flag);
            end =xWin();
            if((computerco<4)&&!end)
            {
                computerco++;
                ComputerMode.generateRand(arr, flag, moves);
            }
             
            end =oWin();
           isDrawComputer();
           
        }
             }else if(hard.equalsIgnoreCase("hard")){
            base.playclick(jButton6, arr, n, record, moves );
            
            if(base.is_win)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                 playerO.setText(""+base.mnmx.oppScore);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_loss)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerX.setText(""+base.mnmx.Score);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_full)
            {
                 JOptionPane.showMessageDialog(this, "the game is Draw please try again");
                 drawlable.setText(""+base.mnmx.tieScore);
                 if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
                
        }
             
         }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        int n = 7;
        if(playingMode.equals("Offline")){
        String s = jButton7.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton7.setText(flag);
            moves.put(7, flag);
            choose_player();
            xWin();
            oWin();
            isDraw();
        }
        }
        else {
            if(easy.equalsIgnoreCase("easy")||medium.equalsIgnoreCase("medium"))
            {
                 String s = jButton7.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton7.setText(flag);
            moves.put(7, flag);
            end =xWin();
            if((computerco<4)&&!end)
            {
                computerco++;
                ComputerMode.generateRand(arr, flag, moves);
            }
             
            end =oWin();
            isDrawComputer();
            
        }
            }else if(hard.equalsIgnoreCase("hard")){
                base.playclick(jButton7, arr, n, record, moves );
                
            if(base.is_win)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                 playerO.setText(""+base.mnmx.oppScore);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_loss)
            {
                String s =firstPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerX.setText(""+base.mnmx.Score);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_full)
            {
                 JOptionPane.showMessageDialog(this, "the game is Draw please try again");
                 drawlable.setText(""+base.mnmx.tieScore);
                 if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            }
           
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int n = 8;
         if(playingMode.equals("Offline")){
        String s = jButton8.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton8.setText(flag);
            moves.put(8, flag);
            choose_player();
            xWin();
            oWin();
            isDraw();
        }
        }
        else {
             if(easy.equalsIgnoreCase("easy")||medium.equalsIgnoreCase("medium"))
             {
                 String s = jButton8.getText();
        if(s.equalsIgnoreCase(""))
        {
            
            jButton8.setText(flag);
            moves.put(8, flag);
            end =xWin();
            if((computerco<4)&&!end)
            {
                computerco++;
                ComputerMode.generateRand(arr, flag, moves);
            }
             
            end =oWin();
           isDrawComputer();
         
        }
             }else if(hard.equalsIgnoreCase("hard"))
             {
                 base.playclick(jButton8, arr, n, record, moves );
                 
                 if(base.is_win)
            {
                String s =secondPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                 playerO.setText(""+base.mnmx.oppScore);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_loss)
            {
                String s =firstPlayer.getText();
                JOptionPane.showMessageDialog(this, "player "+s+" is win");
                playerX.setText(""+base.mnmx.Score);
                if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
            if(base.is_full)
            {
                 JOptionPane.showMessageDialog(this, "the game is Draw please try again");
                 drawlable.setText(""+base.mnmx.tieScore);
                 if(record)
            {
                
                LocalDataBase.writeLocalGameSteps(localFile, dataLocl, playerX00, base.mnmx.Score, playerX00, base.mnmx.oppScore, moves, winner);
                recordGameHard();
            }
            }
             }
             
         }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        // TODO add your handling code here:
        base.comInd = -1;

        base.is_loss = false;
        base.is_win = false;
        base.is_full = false;
        
        for (int i = 0; i < 9; i++) {
            base.mnmx.xo[i] = "0";

        }
        
        flag = "X";
        computerco =0;
        end = false;
         if(cx && co)
        { 
            
            draw++;
            drawlable.setText(""+draw);
            cx = false;
            co = false;
            
        }
         drawCount =0;
        for(int x = 0; x <9; x++ )
        {
            arr[x].setText("");
            arr[x].setEnabled(true);
            arr[x].setBackground(Color.LIGHT_GRAY);
        }
        moves.clear();
    }//GEN-LAST:event_resetBtnActionPerformed

    private void newGameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameBtnActionPerformed
        // TODO add your handling code here:
        xCounter =0;
        oCounter = 0;
        draw = 0;
        drawCount = 0;
        restButtons();
        gameScore();
            base.comInd = -1;

        base.is_loss = false;
        base.is_win = false;
        base.is_full = false;
        
        for (int i = 0; i < 9; i++) {
            base.mnmx.xo[i] = "0";

        }
        
        flag = "X";
        computerco =0;
        end = false;
       
         
        for(int x = 0; x <9; x++ )
        {
            arr[x].setText("");
            arr[x].setEnabled(true);
            arr[x].setBackground(Color.LIGHT_GRAY);
        }
        moves.clear();
       
    }//GEN-LAST:event_newGameBtnActionPerformed

    private void btnProfileBackMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileBackMousePressed
        // TODO add your handling code here:
        CardLayout card=(CardLayout) mycards.getLayout();
         for (int i=0;i<2;i++){
        card.previous(mycards);}
    }//GEN-LAST:event_btnProfileBackMousePressed

    private void ProfileMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProfileMousePressed
        try {
            // TODO add your handling code here:
            playerEmail=dataInputStream.readUTF();
            playerName= dataInputStream.readUTF();
            playerGames=dataInputStream.readInt();
            playerWins=dataInputStream.readInt();
            playerLoses=dataInputStream.readInt();
            ProfileUserName.setText(playerName);
            profileUserEmail.setText(playerEmail);
            profileGames.setText(""+playerGames);
            profileWins.setText(""+playerWins);
            profileLose.setText(""+playerLoses);
            CardLayout card=(CardLayout) mycards.getLayout();
            for (int i=0;i<2;i++){
                card.next(mycards);
            }
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ProfileMousePressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        record = true;
       jButton9.setText("Recording");
       jButton9.setBackground(Color.RED);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        HistoryTabel s = new HistoryTabel();
        dataLocl = LocalDataBase.readLocalFile(localFile);
        System.out.println("\n inside the history btn"+dataLocl+"\n");
        s.method(localFile, dataLocl);
        s.setVisible(true);
        s.setDefaultCloseOperation(2);
    }//GEN-LAST:event_jButton10ActionPerformed

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
            java.util.logging.Logger.getLogger(TicTacToeClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicTacToeClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicTacToeClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicTacToeClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new TicTacToeClient().setVisible(true);
            }
            
        });
         try {  
             socket = new Socket("127.0.0.1", 5004);
             dataOutputStream = new DataOutputStream(socket.getOutputStream());  
             dataInputStream = new DataInputStream(socket.getInputStream()); 
            
           } catch (Exception e) {} 
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Profile;
    public javax.swing.JTextField ProfileUserName;
    private javax.swing.JLabel btnProfileBack;
    private javax.swing.JLabel computerLabel;
    private javax.swing.JPasswordField confPass;
    private javax.swing.JLabel drawcont;
    private javax.swing.JLabel drawlable;
    private javax.swing.JLabel easyLabel;
    private javax.swing.JTextField emailField;
    private javax.swing.JButton exitBtn;
    private javax.swing.JLabel firstPlayer;
    private javax.swing.JLabel gamesLabel;
    private javax.swing.JLabel hardLabel;
    private javax.swing.JButton jButton0;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel loseLabel;
    private javax.swing.JLabel mediumLabel;
    private javax.swing.JPanel mycards;
    private javax.swing.JButton newGameBtn;
    private javax.swing.JLabel offlineLabel;
    private javax.swing.JLabel onlineLabel;
    private javax.swing.JPasswordField passwordFeild;
    private javax.swing.JLabel playerO;
    private javax.swing.JLabel playerX;
    public static javax.swing.JLabel profileGames;
    public javax.swing.JLabel profileLose;
    private javax.swing.JTextField profileUserEmail;
    public static javax.swing.JLabel profileWins;
    private javax.swing.JButton resetBtn;
    private javax.swing.JLabel secondPlayer;
    private javax.swing.JTextField signEmail;
    private javax.swing.JPasswordField signPass;
    private javax.swing.JLabel signUpLabel;
    private javax.swing.JLabel signUpScreenLabel;
    private javax.swing.JTextField signUserName;
    private javax.swing.JLabel signinLabel;
    private javax.swing.JLabel winLabel;
    // End of variables declaration//GEN-END:variables
}
