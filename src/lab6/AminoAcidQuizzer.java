package lab6;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * @author John Patterson 
 * Date : 1.11.18
 * 
 * GUI to demo basic multi thread functionality
 * 
 */
public class AminoAcidQuizzer extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel panel = new JPanel(); 
	private JTextField tf = new JTextField(20); // length up to 20 characters 
    private JTextArea ta = new JTextArea(); // Text Area at the Center
    private JLabel imageLabel = new JLabel(); // Creates area for image
// All GUI settings occur in this constructor
    public AminoAcidQuizzer(String stationName) throws Exception
	{
		super(stationName);
		System.out.println("beeb boot doop");
//Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        m1.setFont(new Font("Microgramma", Font.BOLD,25)); 
        m2.setFont(new Font("Microgramma", Font.BOLD,25));
        mb.add(m1); mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
//        m11.addActionListener(new loadFromFile());
        JMenuItem m12 = new JMenuItem("Save as");
        m12.addActionListener(new saveToFile());
        m11.setFont(new Font("Microgramma", Font.BOLD,25));
        m12.setFont(new Font("Microgramma", Font.BOLD,25));
        JMenuItem m21 = new JMenuItem("No Help For You");
        m1.add(m11);
        m1.add(m12);
        m2.add(m21);
//Button set up
    	JLabel label = new JLabel("Enter Command");
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        label.setFont(new Font("Microgramma", Font.BOLD,25));
        send.setFont(new Font("Microgramma", Font.BOLD,25));
        reset.setFont(new Font("Microgramma", Font.BOLD,25));
        tf.setFont(new Font("Microgramma", Font.BOLD,25));
        send.addActionListener(new StringActionListener());
        reset.addActionListener(new ResetActionListener());
        panel.add(label); // Components Added to the JPanel, which is then set to the bottom of GUI
        panel.add(tf);
        panel.add(send);
        panel.add(reset);
        panel.setBorder(new LineBorder(Color.BLUE, 4));
//// add the image , this could be actually super cool to add AA pic.....
//        String current = new java.io.File( "." ).getCanonicalPath() +"\\src\\lab5\\teleporting.gif";
//        ImageIcon ii = new ImageIcon(current);
//        imageLabel.setIcon(ii);
//        JLabel picur = new JLabel(ii);
//Adding Components to the frame.
//        getContentPane().add(BorderLayout.WEST, picur);
        getContentPane().add(BorderLayout.SOUTH, panel);
        getContentPane().add(BorderLayout.NORTH, mb);
        getContentPane().add(BorderLayout.CENTER, ta);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1200);
        setVisible(true);
//Settings for pretty
        panel.setBackground(Color.BLACK);
        ta.setBackground(Color.BLACK);
        ta.setForeground(Color.green);
        label.setForeground(Color.green);  
        panel.setForeground(Color.green);
        try
    	{
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    	}
    	catch(Exception ex)
    	{
    		System.out.println("Could not set look and feel");
    	}
	}
 // method for the menu save button
    private class saveToFile implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent arg0)
    	{
    		saveToFile();
    	}
    }
// not entirely sure if it's safe to give helper method and class same name, but was easy
    public void saveToFile()
    {
    	JFileChooser jfc = new JFileChooser();
    	if(jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
    		return;
    	if(jfc.getSelectedFile() == null)
    		return;
    	File chosenFile = jfc.getSelectedFile();
    	if(jfc.getSelectedFile().exists())
    	{
    		String message = "File" + jfc.getSelectedFile().getName() + " exists. Overwrite?";
    		if(JOptionPane.showConfirmDialog(this, message) != JOptionPane.YES_OPTION)
				return;
    	}
    	try
    	{
    		BufferedWriter writer = new BufferedWriter(new FileWriter(chosenFile));
    		writer.write(ta.getText());
    		writer.close();
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not write file", JOptionPane.ERROR_MESSAGE);
    	}
    }
 // method for the send button
 	private class StringActionListener implements ActionListener
     {
         @Override
 		public void actionPerformed(ActionEvent arg0)
 		{
 			updateTextArea(tf.getText()); 
 		}
     }
 	public void updateTextArea(String input)
 	{
     	input = input + "\n";
     	ta.setText(ta.getText() + input); 
     	tf.setText("");
 		validate();
 	}
 // method for the reset button
 	private class ResetActionListener implements ActionListener
     {
         @Override
 		public void actionPerformed(ActionEvent arg0)
 		{
         	ta.setText(""); 
 		}
     }

    public static void main(String[] args) throws Exception
	{	 
// Variables for game
    // From the gui teleporter:  new TeleporterGUI("station1");	
    new AminoAcidQuizzer("station1");
    
	boolean timer, gameEnd, timerFlag, surviveFlag; 
	timer = gameEnd = timerFlag = surviveFlag = false;
	int scoreVar, questVar;
	String timerVar = null;
	scoreVar = questVar = 0;
	final String[] SHORT_NAMES = { "A","R", "N", "D", "C", "Q", "E", 
			"G",  "H", "I", "L", "K", "M", "F", 
			"P", "S", "T", "W", "Y", "V", "U", "O", "X"};
	final String[] FULL_NAMES = {"alanine","arginine", "asparagine", 
			"aspartic acid", "cysteine","glutamine",  "glutamic acid",
			"glycine" ,"histidine","isoleucine","leucine",  "lysine", "methionine", 
			"phenylalanine", "proline","serine","threonine","tryptophan","tyrosine", 
			"valine","selenocysteine","pyrrolysine","unknown"};
	int[] errorArray = new int[SHORT_NAMES.length]; 
// Opening of game
	System.out.println("You know why you're here. Do you want to play (t)imed or (s)urvival?");
	String inString = System.console().readLine();
		if( inString.equals("timed")||inString.equals("t"))
		{
			System.out.println("Enter seconds desired to play");
			timerVar = System.console().readLine();
			timerFlag = true;
		}
		else if ( inString.equals("survival")||inString.equals("s"))
		{
			System.out.println("Get ready");
			surviveFlag = true;
		}
		else
		{
			System.out.println("Well looks like you didn't choose, so survival it is :D");
			surviveFlag = true;
		}
	long startTime = System.currentTimeMillis();
// 
// Start the actual game loop with flag controls for versions	
	while(gameEnd == false)
	{
		questVar++;
		int idx = new Random().nextInt(FULL_NAMES.length);
		System.out.println("What is the 1 letter abbreviation for "+ FULL_NAMES[idx] +" >.<");
		String ansString = System.console().readLine().toUpperCase();
			if( ansString.equals(SHORT_NAMES[idx]))
			{
			System.out.println("Ok, "+ ansString +" was correct. Next...");
			scoreVar++;
			}
			else if(surviveFlag == true)
			{
			System.out.println(ansString+" was wrong :p. You needed "+SHORT_NAMES[idx]);
			gameEnd = true;
			}
			else if(ansString.equals("quit")||ansString.equals("i yield"))
			{
			break;
			}
			else
			{
			System.out.println("You needed "+SHORT_NAMES[idx]);
			errorArray[idx]++;
			}
		if(timerFlag == true)
		{
		timer = evalTime(startTime, timerVar);
		gameEnd = timer;
		}
	}
// End actual game loop, prints out results
//
	System.out.println("Game over");
	if(timerFlag == true)
	{
	System.out.println(timerVar + " seconds is up knave!");
	System.out.println("Your score is "+ scoreVar +" out of possible "+ questVar);
	System.out.println("Your worst residue was "+ FULL_NAMES[getIndexOfLargest(errorArray)]);
	}
	else
	{
	System.out.println("Your score is "+ scoreVar);
	}
	}
	
// Method to evaluate time
	public static Boolean evalTime(long startTimeVar, String timeLengthVar)  
	{
		long currentTime = System.currentTimeMillis();
		if((currentTime-startTimeVar)>(Integer.parseInt(timeLengthVar)*1000))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
// Method to get index in int array of largest value
	public static int getIndexOfLargest( int[] array )
	{
	  if ( array == null || array.length == 0 ) return -1; // null or empty
	  int largest = 0;
	  for ( int i = 1; i < array.length; i++ )
	  {
	      if ( array[i] > array[largest] ) largest = i;
	  }
	  return largest; // position of the first largest found
	}
// This will be the multi threaded class for timing and ending the quiz without latency
	private class TimerOrSomething implements Runnable
	{
		public void run()
		{
		try
		{
			Thread.sleep(30000);
			
		}
		catch(InterruptedException e)
		{
			textArea.setText(e.getMessage());
			e.printStackTrace()
		}
		slowButton.setEnabled(true);
		cancelButton.setEnable(false);
		}
	}
}
