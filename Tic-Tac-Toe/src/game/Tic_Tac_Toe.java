package game;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class Tic_Tac_Toe implements ActionListener { 
    private JFrame frame; 
    private JPanel panel; 
    private JButton[] buttons = new JButton[9]; 
    private boolean xTurn = true;
    static String temp = "";
 
    public Tic_Tac_Toe() { 
        frame = new JFrame("Tic-Tac-Toe"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
 
        panel = new JPanel(); 
        panel.setLayout(new GridLayout(3, 3)); 
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
 
        for (int i = 0; i < 9; i++) { 
            buttons[i] = new JButton(); 
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40)); 
            buttons[i].addActionListener(this); 
            panel.add(buttons[i]); 
        } 
        frame.add(panel, BorderLayout.CENTER); 
        frame.setSize(400, 400); 
        frame.setVisible(true); 
    } 
 
    public void actionPerformed(ActionEvent e) { 
        JButton button = (JButton) e.getSource(); 
        if (xTurn) { 
            button.setText("X"); 
        } else { 
            button.setText("O"); 
        } 
        button.setEnabled(false); 
        xTurn = !xTurn; 
 
        checkForWinner(); 
    } 
 
    public void checkForWinner() { 
        for (int i = 0; i < 9; i += 3) { 
            if (buttons[i].getText().equals(buttons[i + 1].getText()) && buttons[i] 
                    .getText().equals(buttons[i + 2].getText()) && !buttons[i].isEnabled()) { 
                JOptionPane.showMessageDialog(frame, buttons[i].getText() + " wins!");
                StoreValue(buttons[i].getText());
                resetGame(); 
                return; 
            } 
        } 
        for (int i = 0; i < 3; i++) { 
            if (buttons[i].getText().equals(buttons[i + 3].getText()) 
                    && buttons[i].getText().equals(buttons[i + 6].getText()) 
                    && !buttons[i].isEnabled()) { 
                JOptionPane.showMessageDialog(frame, buttons[i] 
                        .getText() + " wins!");
                StoreValue(buttons[i].getText());
                resetGame(); 
                return; 
            } 
        } 
        // Check diagonals 
        if (buttons[0].getText().equals(buttons[4].getText()) && 
                buttons[0].getText().equals(buttons[8].getText()) && 
                !buttons[0].isEnabled()) { 
            JOptionPane.showMessageDialog(frame, buttons[0] 
                    .getText() + " wins!");
            StoreValue(buttons[0].getText());
            resetGame(); 
            return; 
        } 
 
        if (buttons[2].getText().equals(buttons[4].getText()) && 
                buttons[2].getText().equals(buttons[6].getText()) && 
                !buttons[2].isEnabled()) { 
            JOptionPane.showMessageDialog(frame, buttons[2] 
                    .getText() + " wins!"); 
            StoreValue(buttons[2].getText());
            resetGame(); 
            return; 
        } 
 
        // Check for tie 
        boolean tie = true; 
        for (int i = 0; i < 9; i++) { 
            if (buttons[i].isEnabled()) { 
                tie = false; 
                break; 
            } 
        } 
        if (tie) { 
            JOptionPane.showMessageDialog(frame, "Tie game!"); 
            StoreValue("Tie");
            resetGame(); 
        } 
    } 
 
    public void resetGame() { 
        for (int i = 0; i < 9; i++) { 
            buttons[i].setText(""); 
            buttons[i].setEnabled(true); 
        } 
        xTurn = true; 
    }
    
    public void StoreValue(String str) {
    	if(str.equals("Tie")) {
    		str+=" game!";
    	}else {
    		str+=" wins";
    	}
    	System.out.println(str);
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	Date date = new Date();
    	String date_value = formatter.format(date);
    	System.out.println(date_value);
    	try {
       	 Class.forName("com.mysql.cj.jdbc.Driver");
           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "Deva@2002");
           //Keela irukara winners table name
           String query = "INSERT INTO winners values('" + str + "','" + date_value + "')";
           Statement sta = connection.createStatement();
           sta.executeUpdate(query);
           connection.close();
       } catch (Exception exception) {
           exception.printStackTrace();
       }
    }
 
    public static void main(String[] args) { 
        new Tic_Tac_Toe(); 
      //FOR SEEING VALUES ADDED IN TABLE
        
        /*
        System.out.println();
        System.out.println("Data updated in database");
        try {
 		   Class.forName("com.mysql.cj.jdbc.Driver");
 		   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/game","root","Deva@2002");
 		   Statement stmt=con.createStatement();
 		   ResultSet rs=stmt.executeQuery("Select * from winners");
 	   while(rs.next())
 	   {
 		   System.out.println(rs.getString(1)+"  "+rs.getString(2));
 	   }
 	   }
 	   catch(Exception e)
 	   {
 		   System.out.println(e.toString());
 	   }
        */
    } 
}
12345678901