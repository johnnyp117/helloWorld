package lab5;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
/**
 * @author John Patterson 
 * Date : 12.10.18
 * 
 * GUI to demo basic swing functionality
 * 
 */
public class TeleporterGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel panel = new JPanel(); // the panel is not visible in output
	private JTextField tf = new JTextField(10); // accepts up to 10 characters
    private JTextArea ta = new JTextArea(); // Text Area at the Center
    private JLabel imageLabel = new JLabel();

	public TeleporterGUI(String stationName) throws Exception
	{
		super(stationName);
		System.out.println("beeb boot doop");
//Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        m1.setFont(new Font("Microgramma", Font.BOLD,25));
        m2.setFont(new Font("Microgramma", Font.BOLD,25));
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
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
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);
        panel.setBorder(new LineBorder(Color.BLUE, 4));
     // add the image label
        String current = new java.io.File( "." ).getCanonicalPath() +"\\src\\lab5\\teleporting.gif";
        ImageIcon ii = new ImageIcon(current);
        imageLabel.setIcon(ii);
        JLabel picur = new JLabel(ii);
//Adding Components to the frame.
        getContentPane().add(BorderLayout.WEST, picur);
        getContentPane().add(BorderLayout.SOUTH, panel);
        getContentPane().add(BorderLayout.NORTH, mb);
        getContentPane().add(BorderLayout.CENTER, ta);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1200);
        setVisible(true);
//Settings for pretty
        panel.setBackground(Color.BLACK);
        ta.setBackground(Color.BLACK);
        setBackground(Color.BLACK);
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
	private class ResetActionListener implements ActionListener
    {
        @Override
		public void actionPerformed(ActionEvent arg0)
		{
        	ta.setText(""); 
		}
    }
    private class saveToFile implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent arg0)
    	{
    		saveToFile();
    	}
    }
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
    public static void main(String args[]) throws Exception
    {
        new TeleporterGUI("station1");
    }
	

}
