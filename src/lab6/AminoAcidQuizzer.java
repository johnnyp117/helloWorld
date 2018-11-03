package lab6;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
 * GUI multi threaded amino acid quiz game. 
 * 
 */
public class AminoAcidQuizzer extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel panel = new JPanel(); 
	private JTextField tf = new JTextField(20); 
    private JTextArea ta1 = new JTextArea();
    private JScrollPane ta = new JScrollPane(ta1);
	private volatile JLabel label = new JLabel("Enter Command");
//    private JLabel imageLabel = new JLabel(); // Creates area for image
    private volatile static String lastTFinput;
    private boolean inputInital;
    private volatile boolean timer = false;
    private volatile boolean inputGame = false;

// All GUI settings occur in this constructor
    public AminoAcidQuizzer(String stationName) throws Exception
	{
		super(stationName);
		updateTextArea("beeb boot doop");
//Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        m1.setFont(new Font("Microgramma", Font.BOLD,25)); 
        m2.setFont(new Font("Microgramma", Font.BOLD,25));
        ta1
        .setFont(new Font("Microgramma", Font.BOLD,25));
        mb.add(m1); mb.add(m2);
        JMenuItem m12 = new JMenuItem("Save as");
        m12.addActionListener(new saveToFile());
        m12.setFont(new Font("Microgramma", Font.BOLD,25));
        JMenuItem m21 = new JMenuItem("No Help For You");
        m1.add(m12);m2.add(m21);
//Button set up
        JButton send = new JButton("Enter");
        JButton reset = new JButton("Reset");
        JButton  start = new JButton("Start Game");
        label.setFont(new Font("Microgramma", Font.BOLD,25));
        send.setFont(new Font("Microgramma", Font.BOLD,25));
        reset.setFont(new Font("Microgramma", Font.BOLD,25));
        tf.setFont(new Font("Microgramma", Font.BOLD,25));
        start.setFont(new Font("Microgramma", Font.BOLD,25));
        send.addActionListener(new StringActionListener());
//        send.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "COPY");
//        send.getActionMap().put("COPY", send);
        tf.addKeyListener(new StringActionListener());
        reset.addActionListener(new ResetActionListener());
        panel.add(start);
        panel.add(label); // Components Added to the JPanel, which is then set to the bottom of GUI
        panel.add(tf);
        panel.add(send);
//        panel.getRootPane().setDefaultButton(send);
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
        ta1.setBackground(Color.BLACK);
        ta1.setForeground(Color.green);
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
    		writer.write(ta1.getText());
    		writer.close();
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not write file", JOptionPane.ERROR_MESSAGE);
    	}
    }
 // method for the send button
 	private class StringActionListener extends KeyAdapter implements ActionListener 
     {
		 @Override
		public void actionPerformed(ActionEvent arg0)
		{
			lastTFinput = tf.getText();
			updateTextArea(tf.getText()); 
			inputInital = true;
			inputGame = true;
		}
		public synchronized void keyPressed(KeyEvent evt) 
		{
		    if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
//		    	btn_build.doClick();
		    	lastTFinput = tf.getText();
				updateTextArea(tf.getText()); 
				inputInital = true;
				inputGame = true;
		     }
		 }
     }
 	public void updateTextArea(String input)
 	{
     	input = input + "\n";
     	ta1.setText(ta1.getText() + input); 
     	tf.setText("");
 		validate();
 	}
 // method for the send button
  	private class GameActionListener implements ActionListener
      {
          @Override
  		public void actionPerformed(ActionEvent arg0)
  		{
   			lastTFinput = tf.getText();
  			updateGame(tf.getText()); 
  			inputInital = true;
  			inputGame = true;
  			
  			
  		}
      }
  	public void updateGame(String input)
  	{
      	input = input + "\n";
      	ta1.setText(ta1.getText() + input); 
      	tf.setText("");
  		validate();
  	}
 // method for the reset button
 	private class ResetActionListener implements ActionListener
     {
         @Override
 		public void actionPerformed(ActionEvent arg0)
 		{
         	ta1.setText(""); 
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
    public static void main(String[] args) throws Exception
	{	 
// Variables for game
    AminoAcidQuizzer gamePane = new AminoAcidQuizzer("Quiz");
	boolean timer, gameEnd, timerFlag, surviveFlag; 
	timer = gameEnd = timerFlag = surviveFlag = false;
	int scoreVar, questVar;
	scoreVar = questVar = 0;
	
	final String[] SHORT_NAMES = { "A","R", "N", "D", "C", "Q", "E", 
			"G",  "H", "I", "L", "K", "M", "F", 
			"P", "S", "T", "W", "Y", "V", "U", "O", "X"};
	final String[] FULL_NAMES = {"alanine","arginine","asparagine", 
			"aspartic acid","cysteine","glutamine","glutamic acid",
			"glycine","histidine","isoleucine","leucine","lysine","methionine", 
			"phenylalanine","proline","serine","threonine","tryptophan","tyrosine", 
			"valine","selenocysteine","pyrrolysine","unknown"};
	int[] errorArray = new int[SHORT_NAMES.length]; 
// Opening of game
	gamePane.updateTextArea("You know why you're here. Do you want to play (t)imed or (s)urvival?");
	//System.out.println("You know why you're here. Do you want to play (t)imed or (s)urvival?");
	System.out.println(gamePane.inputInital);
	while(gamePane.inputInital == false)
	{try {Thread.sleep(100);}catch(InterruptedException e) {}}
	System.out.println(gamePane.inputInital);
	String inString = lastTFinput;
		if( inString.equals("timed")||inString.equals("t"))
		{
			gamePane.updateTextArea("30 seconds to play");
//			timerVar = System.console().readLine();
			timerFlag = true;
			new Thread(gamePane.new Timer()).start();
		}
		else if ( inString.equals("survival")||inString.equals("s"))
		{
			gamePane.updateTextArea("Get ready");
			surviveFlag = true;
		}
		else
		{
			gamePane.updateTextArea("Well looks like you didn't choose, so survival it is :D");
			surviveFlag = true;
		}
// Start the actual game loop with flag controls for versions	
	gamePane.inputGame = false;
	while(gameEnd == false)
	{
		questVar = questVar+1;
		int idx = new Random().nextInt(FULL_NAMES.length);
		gamePane.updateTextArea("What is the 1 letter abbreviation for "+ FULL_NAMES[idx]);
		while(gamePane.inputGame == false)
		{try {Thread.sleep(100);}catch(InterruptedException e) {}}
//		String ansString = System.console().readLine().toUpperCase();
		String ansString = lastTFinput.toUpperCase();
		System.out.println(ansString);
			if( ansString.equals(SHORT_NAMES[idx]))
			{
			gamePane.updateTextArea("Ok, "+ ansString +" was correct. Next...");
			scoreVar++;
			}
			else if(surviveFlag == true)
			{
			gamePane.updateTextArea(ansString+" was wrong :p. You needed "+SHORT_NAMES[idx]);
			gameEnd = true;
			}
			else if(ansString.equals("quit")||ansString.equals("i yield"))
			{
			break;
			}
			else if(timerFlag == true)
			{
			System.out.println(timer);
			gameEnd = gamePane.timer;
			}
			else
			{
			gamePane.updateTextArea("You needed "+SHORT_NAMES[idx]);
			errorArray[idx]++;
			}
		gamePane.inputGame = false;
	}
// End actual game loop, prints out results
	gamePane.updateTextArea("Game over");
	if(timerFlag == true)
	{
	gamePane.updateTextArea("30 seconds is up knave!");
	gamePane.updateTextArea("Your score is "+ scoreVar +" out of possible "+ questVar);
	gamePane.updateTextArea("Your worst residue was "+ FULL_NAMES[getIndexOfLargest(errorArray)]);
	}
	else
	{
	gamePane.updateTextArea("Your score is "+ scoreVar);
	}
	}
// multi threaded class for timing and ending the quiz without latency
	private class Timer implements Runnable
	{
		public void run()
		{
			for(int i=1;i<30;i=i+1)
			{
				try
				{
					label.setText( Integer.toString(30-i));
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					ta1.setText(e.getMessage());
					e.printStackTrace();
				}
			}
			updateTextArea("Your time is over");
			timer = true;
			validate();
		}
	}

}
