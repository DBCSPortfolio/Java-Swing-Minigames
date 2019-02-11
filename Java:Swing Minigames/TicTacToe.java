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

public class TicTacToe extends JFrame implements ActionListener
{
   public static final int GRID_SIZE = 3;
   
   private JButton[][] buttonArray;
   private JLabel[][] labelArray;
   private Container container = getContentPane();
   private GridLayout layout = new GridLayout(GRID_SIZE, GRID_SIZE);
   private int boxesLeft = GRID_SIZE * GRID_SIZE;
   private int currentOpenSpotPos = 0;
   private int randomCompSpot;
   private boolean compTurnDoneFlag;
   private Random random = new Random();
   
   public TicTacToe()
   {
      super("Tic-Tac-Toe");
      
      buttonArray = new JButton[GRID_SIZE][GRID_SIZE];
      labelArray = new JLabel[GRID_SIZE][GRID_SIZE];
      
      // setting up buttons and default label values
      for (int h = 0; h < GRID_SIZE; h++)
      {
         for (int i = 0; i < GRID_SIZE; i++)
         {
            buttonArray[h][i] = 
                  new JButton(Integer.toString((h * GRID_SIZE) + i + 1));
            buttonArray[h][i].addActionListener(this);
            labelArray[h][i] = new JLabel("X");
         }
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
   }
   
   @Override
   public void actionPerformed(ActionEvent e)
   {
      JButton clickedButton = (JButton) e.getSource();
      int rowNumber = -1;
      int columnNumber = -1;
      
      // determines which cell was clicked
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
      // reveals X in clicked cell for player's turn
      labelArray[rowNumber][columnNumber].setVisible(true);
      buttonArray[rowNumber][columnNumber].setVisible(false);
      boxesLeft--;
      checkForWinner();
      
      // calculates random cell number for computer's cell choice
      randomCompSpot = random.nextInt(boxesLeft);
      compTurnDoneFlag = false;
      
      for (int m = 0; m < GRID_SIZE; m++)
      {
         if (compTurnDoneFlag)
         {
            break;
         }
         for (int n = 0; n < GRID_SIZE; n++)
         {
            if (compTurnDoneFlag)
            {
               break;
            }
            // goes through cells until randomly selected position is found
            else if (!labelArray[m][n].isVisible())
            {
               currentOpenSpotPos++;
               // reveals an O for computer cell choice
               if (currentOpenSpotPos == randomCompSpot)
               {
                  labelArray[m][n].setText("O");
                  labelArray[m][n].setVisible(true);
                  buttonArray[m][n].setVisible(false);
                  boxesLeft--;
                  compTurnDoneFlag = true; // ends computer's turn
                  checkForWinner();
                  break;
               }
            }
            else
            {
               continue;
            }
         }
      }
      currentOpenSpotPos = -1;
   }
   
   // checks potential winning sequences of three cells
   private void checkForWinner()
   {
      for (int i = 0; i < GRID_SIZE; i++)
      {
         // winning row
         if (labelArray[i][0].isVisible() &&
               labelArray[i][1].isVisible() &&
               labelArray[i][2].isVisible())
         {
            if (labelArray[i][0].getText().equals("X")
                  && labelArray[i][1].getText().equals("X")
                  && labelArray[i][2].getText().equals("X"))
            {
               playerWins();
            }
            else if (labelArray[i][0].getText().equals("O")
                  && labelArray[i][1].getText().equals("O")
                  && labelArray[i][2].getText().equals("O"))
            {
               computerWins();
            }
         }
         // winning column
         if (labelArray[0][i].isVisible() &&
               labelArray[1][i].isVisible() &&
               labelArray[2][i].isVisible())
         {
            if (labelArray[0][i].getText().equals("X")
                  && labelArray[1][i].getText().equals("X")
                  && labelArray[2][i].getText().equals("X"))
            {
               playerWins();
            }
            else if (labelArray[0][i].getText().equals("O")
                  && labelArray[1][i].getText().equals("O")
                  && labelArray[2][i].getText().equals("O"))
            {
               computerWins();
            }
         }
      }
      
      // winning diagonals
      if (labelArray[0][0].isVisible() &&
            labelArray[1][1].isVisible() &&
            labelArray[2][2].isVisible())
      {
         if (labelArray[0][0].getText().equals("X")
               && labelArray[1][1].getText().equals("X")
               && labelArray[2][2].getText().equals("X"))
         {
            playerWins();
         }
         else if (labelArray[0][0].getText().equals("O")
               && labelArray[1][1].getText().equals("O")
               && labelArray[2][2].getText().equals("O"))
         {
            computerWins();
         }
      }
      
      if (labelArray[0][2].isVisible() &&
            labelArray[1][1].isVisible() &&
            labelArray[2][0].isVisible())
      {
         if (labelArray[0][2].getText().equals("X")
               && labelArray[1][1].getText().equals("X")
               && labelArray[2][0].getText().equals("X"))
         {
            playerWins();
         }
         else if (labelArray[0][2].getText().equals("O")
               && labelArray[1][1].getText().equals("O")
               && labelArray[2][0].getText().equals("O"))
         {
            computerWins();
         }
      }
      
      // create option for all being visible, with no winner
      boolean boardFull = true;
      for (int y = 0; y < GRID_SIZE; y++)
      {
         if (boardFull == false)
         {
            break;
         }
         for (int z = 0; z < GRID_SIZE; z++)
         {
            if (labelArray[y][z].isVisible())
            {
               continue;
            }
            else if (!labelArray[y][z].isVisible())
            {
               boardFull = false;
               break;
            }
         }
      }
      if (boardFull == true)
      {
         noWinner();
      }
   }
   
   // alert for player win
   private void playerWins()
   {
      JOptionPane.showMessageDialog(null, "You win!", "Game Over!", 
            JOptionPane.WARNING_MESSAGE);
      System.exit(0);
   }
   
   // alert for computer win
   private void computerWins()
   {
      JOptionPane.showMessageDialog(null, "Computer wins!", "Game Over!",
            JOptionPane.WARNING_MESSAGE);
      System.exit(0);
   }
   
   // alert for no winner with full board
   private void noWinner()
   {
      JOptionPane.showMessageDialog(null, "You both lose!", "Game Over!",
            JOptionPane.WARNING_MESSAGE);
      System.exit(0);
   }
   
   public static void main(String[] args)
   {
      TicTacToe newGame = new TicTacToe();
      newGame.setVisible(true);
   }
}