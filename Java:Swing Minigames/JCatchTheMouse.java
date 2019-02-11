/**************************************************
 * Damon Black
 * CIS 263AA (Section 28878)
 * Final Project
 * 2/15/2018
 **************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class JCatchTheMouse extends JFrame implements ActionListener
{
   // static constants
   public static final int NUM_ROWS = 8;
   public static final int NUM_COLUMNS = 6;
   public static final String MARKED_BUTTON = "X";
   
   // private member data
   private JButton[][] buttonArray;
   private Container container = getContentPane();
   private GridLayout layout = new GridLayout(NUM_ROWS, NUM_COLUMNS);
   private Random random = new Random();
   private int randomButton = -1;
   private int successClicks;
   private int totalClicks;
   
   public JCatchTheMouse()
   {
      super("Catch The Mouse!");
      buttonArray = new JButton[NUM_ROWS][NUM_COLUMNS];
      
      // setting up buttons
      for (int h = 0; h < NUM_ROWS; h++)
      {
         for (int i = 0; i < NUM_COLUMNS; i++)
         {
            buttonArray[h][i] = 
                  new JButton("");
            buttonArray[h][i].addActionListener(this);
         }
      }
      
      successClicks = 0;
      totalClicks = 0;
      generateRandom();
      
      container.setLayout(layout);
      
      for (int j = 0; j < NUM_ROWS; j++)
      {
         for (int k = 0; k < NUM_COLUMNS; k++)
         {
            container.add(buttonArray[j][k]);
         }
      }
      
      setSize(500, 500);
   }
   
   // generates random button to be marked with an "X"
   private void generateRandom()
   {
      int oldRandom = randomButton;
      randomButton = random.nextInt(NUM_ROWS * NUM_COLUMNS);
      if (randomButton == oldRandom)
      {
         if (randomButton == 0)
         {
            randomButton++;
         }
         if (randomButton == (NUM_ROWS * NUM_COLUMNS) - 1)
         {
            randomButton--;
         }
      }
      buttonArray[randomButton / NUM_COLUMNS][randomButton % NUM_COLUMNS].
            setText(MARKED_BUTTON);
   }
   
   @Override
   public void actionPerformed(ActionEvent e)
   {
      JButton clickedButton = (JButton) e.getSource();
      
      // events if user clicks correct button
      if (clickedButton.getText().equals(MARKED_BUTTON))
      {
         totalClicks++;
         successClicks++;
         checkForWinner();
         clickedButton.setText("");
         generateRandom();
      }
      
      // events if user does not click correct button
      else if (!clickedButton.getText().equals(MARKED_BUTTON))
      {
         totalClicks++;
         for (int i = 0; i < NUM_ROWS; i++)
         {
            for (int j = 0; j < NUM_COLUMNS; j++)
            {
               if (buttonArray[i][j].getText().equals(MARKED_BUTTON))
               {
                  buttonArray[i][j].setText("");
               }
            }
         }
         generateRandom();
      }
   }
   
   // checks if player has clicked the correct cell 10 times in total
   private void checkForWinner()
   {
      if (successClicks == 10)
      {
         playerWins();
      }
   }
   
   // alert for player win
   private void playerWins()
   {
      int percentage = (successClicks * 100) / totalClicks;
      JOptionPane.showMessageDialog(null, "Congratulations, you win!",
            "You win!", JOptionPane.WARNING_MESSAGE);
      JOptionPane.showMessageDialog(null, "Correct number of clicks: "
            + successClicks + "/" + totalClicks + " (" + percentage + "%)",
            "You win!", JOptionPane.WARNING_MESSAGE);
      System.exit(0);
   }
   
   public static void main(String[] args)
   {
      JCatchTheMouse newGame = new JCatchTheMouse();
      newGame.setVisible(true);
   }
}