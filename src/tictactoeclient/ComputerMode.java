/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.util.LinkedHashMap;
import java.util.Random;
import javax.swing.JButton;

/**
 *
 * @author 20111
 */
public class ComputerMode {
    
    
    
    
    public static void generateRand(JButton []arr, String flage,  LinkedHashMap<Integer, String> moves) {
        Random rand = new Random(); //instance of random class
        int upperbound = 9;
        //generate random values from 0-9
        int ind = -1;
        ind = rand.nextInt(upperbound);
        while(arr[ind].getText().equalsIgnoreCase("x")||arr[ind].getText().equalsIgnoreCase("o"))
        {
            ind = rand.nextInt(upperbound);
        }
        arr[ind].setText("O");
        moves.put(ind, "O");
        flage = "x";
      
            
        
       
    }
    
}
