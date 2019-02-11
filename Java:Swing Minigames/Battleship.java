/**************************************************
 * Damon Black
 * CIS 263AA (Section 28878)
 * Lesson 7 Project
 * 1/9/2018
 **************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Battleship extends JFrame implements ActionListener
{
   public static final int GRID_SIZE = 6;
   
   private JButton[][] buttonArray;
   private JLabel[][] labelArray;
   private String buttonString;
   private char cellChar;
   private int randomOnes;
   private Container container = getContentPane();
   private GridLayout layout = new GridLayout(GRID_SIZE, GRID_SIZE);
   
   public Battleship()
   {
      super("Battleship!");
      
      buttonArray = new JButton[GRID_SIZE][GRID_SIZE];
      labelArray = new JLabel[GRID_SIZE][GRID_SIZE];
      cellChar = 'A';
      
      // sets up cell positions and empty ("0") cell labels
      for (int h = 0; h < GRID_SIZE; h++)
      {
         for (int i = 0; i < GRID_SIZE; i++)
         {
            buttonString = Character.toString(cellChar) + 
                  Integer.toString(i + 1);
            buttonArray[h][i] = new JButton(buttonString);
            buttonArray[h][i].addActionListener(this);
            labelArray[h][i] = new JLabel("0");
         }
         cellChar++;
      }
      
      container.setLayout(layout);
      
      for (int j = 0; j < GRID_SIZE; j++)
      {
         for (int k = 0; k < GRID_SIZE; k++)
         {
            container.add(buttonArray[j][k]);
            container.add(labelArray[j][k]);
            labelArray[j][k].setVisible(false);
         }
      }
      
      setSize(500, 500);
      
      // sets a random row or column of labels to "1" for present ships
      int numRowsColumns = GRID_SIZE + GRID_SIZE;
      Random random = new Random();
      randomOnes = random.nextInt(numRowsColumns);
      if (randomOnes >= GRID_SIZE)
      {
         for (int m = 0; m < GRID_SIZE; m++)
         {
            labelArray[m][randomOnes - GRID_SIZE].setText("1");
         }
      }
      else if (randomOnes < GRID_SIZE)
      {
         for (int n = 0; n < GRID_SIZE; n++)
         {
            labelArray[randomOnes][n].setText("1");
         }
      }
   }
   
   @Override
   public void actionPerformed(ActionEvent e)
   {
      JButton clickedButton = (JButton) e.getSource();
      int rowNumber = -1;
      int columnNumber = -1;
      
      for (int i = 0; i < GRID_SIZE; i++)
      {
         for (int j = 0; j < GRID_SIZE; j++)
         {
            if (clickedButton.getText().equals(buttonArray[i][j].getText()))
            {
               rowNumber = i;
               columnNumber = j;
            }
         }
      }
      labelArray[rowNumber][columnNumber].setVisible(true);
      buttonArray[rowNumber][columnNumber].setVisible(false);
      
      // checks to see if all cells containing a "1" are visible
      boolean winnerFlag = true;
      for (int k = 0; k < GRID_SIZE; k++)
      {
         
         if (randomOnes >= GRID_SIZE)
         {
            if (labelArray[k][randomOnes - GRID_SIZE].isVisible())
            {
               continue;
            }
            else
            {
               winnerFlag = false;
               break;
            }
         }
         else if (randomOnes < GRID_SIZE)
         {
            if (labelArray[randomOnes][k].isVisible())
            {
               continue;
            }
            else
            {
               winnerFlag = false;
               break;
            }
         }
      }
      // alert for win, exits application upon action
      if (winnerFlag)
      {
         JOptionPane.showMessageDialog(null, "You win!");
         System.exit(0);
      }
   }
   
   public static void main(String[] args)
   {
      Battleship newGame = new Battleship();
      newGame.setVisible(true);
   }
}