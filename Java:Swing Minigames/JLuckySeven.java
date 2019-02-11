/**************************************************
 * Damon Black
 * CIS 263AA (Section 28878)
 * Final Project
 * 2/15/2018
 **************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JLuckySeven extends JFrame implements ActionListener
{
   public static final int NUM_BUTTONS = 7;
   
   // private member data
   private JPanel gamePanel = new JPanel();
   private JButton[] buttonArray = new JButton[NUM_BUTTONS];
   private JLabel[] labelArray = new JLabel[NUM_BUTTONS];
   private BorderLayout layout = new BorderLayout();
   private boolean gameStarted;
   private String clickableButton;
   private int[] randomArray = new int[NUM_BUTTONS];
   private int gamesWon = 0;
   private int gamesLost = 0;
   private JLabel scoreboard;
   //private NamePanel namePanel = new NamePanel();
   
   public JLuckySeven()
   {
      super("Lucky Sevens!");
      setSize(750, 500);
      setLayout(layout);
      gameStarted = false;
      scoreboard = new JLabel();
      updateScoreboard();
      
      randomize();
      
      // sets 1-7 values for buttons, random values for labels
      for (int i = 0; i < NUM_BUTTONS; i++)
      {
         buttonArray[i] = new JButton(Integer.toString(i + 1));
         buttonArray[i].addActionListener(this);
         labelArray[i] = new JLabel(Integer.toString(randomArray[i]));
         labelArray[i].setVisible(false); // hides labels until buttons clicked
         gamePanel.add(buttonArray[i]);
         gamePanel.add(labelArray[i]);
      }
      
      scoreboard.setHorizontalAlignment(SwingConstants.CENTER);
      add(gamePanel, BorderLayout.NORTH);
      add(scoreboard, BorderLayout.CENTER);
      //add(namePanel, BorderLayout.SOUTH);
   }
   
   public void actionPerformed(ActionEvent e)
   {
      JButton clickSource = (JButton) e.getSource();
      String buttonValue = clickSource.getText();
      if (!gameStarted)
      {
         for (int i = 0; i < NUM_BUTTONS; i++)
         {
            if ((Integer.parseInt(buttonValue)) ==
                  Integer.parseInt(buttonArray[i].getText()))
            {
               buttonArray[i].setVisible(false); // hides button
               labelArray[i].setVisible(true); // uncovers hidden number
               if (!buttonArray[Integer.parseInt(labelArray[i]
                     .getText()) - 1].isVisible())
               {
                  losingMessage(); // player loses if button already clicked
               }
               else
               {
                  checkForWinner();
                  clickableButton = labelArray[i].getText();
               }
            }
            gameStarted = true; // enforces clicking on particular buttons
         }
      }
      else
      {
         // action if player clicks the correct button
         if (buttonValue.equals(clickableButton))
         {
            int temp = Integer.parseInt(buttonValue) - 1;
            buttonArray[temp].setVisible(false); // hides button
            labelArray[temp].setVisible(true); // uncovers hidden number
            if (!(buttonArray[Integer.parseInt(
                  labelArray[temp].getText()) - 1]).isVisible())
            {
               checkForWinner();
               if (gameStarted)
               {
                  losingMessage(); // player loses if button already clicked
               }
            }
            else
            {
               clickableButton = labelArray[temp].getText();
            }
         }
         // forces player to clicked a particularly-numbered button
         else
         {
            JOptionPane.showMessageDialog(null, "Please click the button "
                  + "corresponding to the last revealed number.",
                  "Error", JOptionPane.WARNING_MESSAGE);
         }
      }
   }
   
   // randomizes numbers stored in labels
   private void randomize()
   {
      // sets number values 1-7 in an auxiliary array
      for (int g = 0; g < NUM_BUTTONS; g++)
      {
         randomArray[g] = g + 1;
      }
      
      // shuffles number values in the auxiliary array
      for (int h = 0; h < NUM_BUTTONS; h++)
      {
         int r = (int) (Math.random() * (h + 1));
         int swap = randomArray[r];
         randomArray[r] = randomArray[h];
         randomArray[h] = swap;
      }
   }
   
   // if all buttons are hidden, the player has won
   private void checkForWinner()
   {
      for (int n = 0; n < NUM_BUTTONS; n++)
      {
         if (!buttonArray[n].isVisible())
         {
            continue;
         }
         else
         {
            return;
         }
      }
      winningMessage();
   }
   
   private void losingMessage()
   {
      gamesLost++;
      updateScoreboard();
      JOptionPane.showMessageDialog(null, "Sorry, you lose. Please try again!",
            "Game Over!", JOptionPane.WARNING_MESSAGE);
      reset();
   }
   
   private void winningMessage()
   {
      gamesWon++;
      updateScoreboard();
      JOptionPane.showMessageDialog(null, "Congratulations, you win!",
            "Game Over!", JOptionPane.WARNING_MESSAGE);
      reset();
   }
   
   // keeps tally of games won and lost in a session
   private void updateScoreboard()
   {
      scoreboard.setText("Games Won: " + gamesWon + "; " + "Games Lost: "
            + gamesLost + ".");
   }
   
   // restarts game
   private void reset()
   {
      gameStarted = false;
      randomize();
      for (int i = 0; i < NUM_BUTTONS; i++)
      {
         buttonArray[i].setText(Integer.toString(i + 1));
         labelArray[i].setText(Integer.toString(randomArray[i]));
         buttonArray[i].setVisible(true);
         labelArray[i].setVisible(false);
      }
   }
   
   /*
   // separate class for drawing string onto a panel
   private class NamePanel extends JPanel
   {
      public void paintComponent(Graphics g)
      {
         super.paintComponent(g);
         g.drawString("Damon Black\n" + 
               "CIS 263AA (Section 28878)", 10, 10);
      }
   }
   */
   
   public static void main(String[] args)
   {
      JLuckySeven newGame = new JLuckySeven();
      newGame.setVisible(true);
   }
}