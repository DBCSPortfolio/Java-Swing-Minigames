/**************************************************
 * Damon Black
 * CIS 263AA (Section 28878)
 * Final Project
 * 2/15/2018
 **************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JLottery2 extends JFrame implements ItemListener, ActionListener
{
   // static constants
   private static final int NUMBER_OF_OPTIONS = 31;
   private static final int MAX_BOXES = 6;
   private static final String LOSER = "0";
   private static final String THREE_BOXES = "100";
   private static final String FOUR_BOXES = "10000";
   private static final String FIVE_BOXES = "50000";
   private static final String ALL_SIX = "1000000";
   private static final String FINAL_MESSAGE =
         "Congratulations! You won $ ";
   
   // private member data
   // menu construction
   private JMenuBar menu = new JMenuBar();
   private JMenu file = new JMenu("File");
   private JMenuItem about = new JMenuItem("About");
   
   // UI setup
   private GridLayout grid = new GridLayout(2, 3);
   private JLabel header = new JLabel("Please select 6 lottery numbers.");
   
   private JCheckBox[] checkboxArray = new JCheckBox[NUMBER_OF_OPTIONS];
   private JPanel checkboxPanel = new JPanel();
   private int numBoxesChecked = 0;
   private String[] winningNumbers = new String[MAX_BOXES];
   private String[] selectedNumbers = new String[MAX_BOXES];
   private int numberMatched = 0;
   private String winningsMessage = "";
   private String selectedString = "";
   private String numberString = "";
   
   public JLottery2()
   {
      super("Lottery");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(grid);
      setSize(500, 500);
      
      // creates menu with about section
      setJMenuBar(menu);
      menu.add(file);
      file.add(about);
      
      file.addActionListener(this);
      about.addActionListener(this);
      
      // creates checkboxes for each possible number
      for (int i = 0; i < NUMBER_OF_OPTIONS; i++)
      {
         checkboxArray[i] = new JCheckBox(Integer.toString(i), false);
         checkboxArray[i].addItemListener(this);
         checkboxPanel.add(checkboxArray[i]);
      }
      
      // rest of UI setup
      header.setHorizontalAlignment(SwingConstants.CENTER);
      add(header);
      add(checkboxPanel);
   }
   
   // shows "about" message in header menu
   public void actionPerformed(ActionEvent e)
   {
      Object choice = e.getSource();
      if (choice.equals(about))
      {
         JOptionPane.showMessageDialog(null,
               "Damon Black\n" + 
               "CIS 263AA (Section 28878)", "About", 
               JOptionPane.WARNING_MESSAGE);
      }
   }
   
   // actions for checkbox selection
   public void itemStateChanged(ItemEvent check)
   {
      numBoxesChecked++;
      JCheckBox checkedBox = (JCheckBox) check.getSource();
      selectedNumbers[numBoxesChecked - 1] = checkedBox.getText();
      // actions for when user has selected 6 numbers
      if (numBoxesChecked == MAX_BOXES)
      {
         generateNumbers(); // generates winning numbers
         determineWinnings(); // checks to see if user won and how much
         displayWinnings(); // displays results to user
      }
   }
   
   // randomly generates 6 winning numbers
   private void generateNumbers()
   {
      // shuffle array of all possible values
      int[] randomArray = new int[NUMBER_OF_OPTIONS];
      for (int g = 0; g < NUMBER_OF_OPTIONS; g++)
      {
         randomArray[g] = g;
      }
      
      for (int h = 0; h < NUMBER_OF_OPTIONS; h++)
      {
         int r = (int) (Math.random() * (h + 1));
         int swap = randomArray[r];
         randomArray[r] = randomArray[h];
         randomArray[h] = swap;
      }
      
      for (int i = 0; i < MAX_BOXES; i++)
      {
         // choose first six shuffled values as winning numbers
         winningNumbers[i] = Integer.toString(randomArray[i]);
         // generate strings of winning and user numbers for later output
         if (i < MAX_BOXES - 1)
         {
            numberString += winningNumbers[i] + ", ";
            selectedString += selectedNumbers[i] + ", ";
         }
         else if (i == MAX_BOXES - 1)
         {
            numberString += "and " + winningNumbers[i] + ".";
            selectedString += "and " + selectedNumbers[i] + ".";
         }
      }
   }
   
   // finds number of matched numbers and calculates winnings
   private void determineWinnings()
   {
      for (int i = 0; i < MAX_BOXES; i++)
      {
         for (int j = 0; j < MAX_BOXES; j++)
         {
            if (selectedNumbers[i].equals(winningNumbers[j]))
            {
               numberMatched++;
            }
         }
      }
      
      if (numberMatched < 3)
      {
         winningsMessage = FINAL_MESSAGE + LOSER + ".";
      }
      else if (numberMatched == 3)
      {
         winningsMessage = FINAL_MESSAGE + THREE_BOXES + ".";
      }
      else if (numberMatched == 4)
      {
         winningsMessage = FINAL_MESSAGE + FOUR_BOXES + ".";
      }
      else if (numberMatched == 5)
      {
         winningsMessage = FINAL_MESSAGE + FIVE_BOXES + ".";
      }
      else if (numberMatched == 6)
      {
         winningsMessage = FINAL_MESSAGE + ALL_SIX + ".";
      }
   }
   
   // displays money won, selected numbers, and winning numbers
   private void displayWinnings()
   {
      JOptionPane.showMessageDialog(null, winningsMessage, "Game Over!", 
            JOptionPane.WARNING_MESSAGE);
      JOptionPane.showMessageDialog(null, "You selected the numbers: "
            + selectedString, "Your Numbers", JOptionPane.WARNING_MESSAGE);
      JOptionPane.showMessageDialog(null, "The winning numbers were: "
            + numberString, "Winning Numbers", JOptionPane.WARNING_MESSAGE);
      System.exit(0);
   }
   
   public static void main(String[] args)
   {
      JLottery2 newGame = new JLottery2();
      newGame.setVisible(true);
   }
}