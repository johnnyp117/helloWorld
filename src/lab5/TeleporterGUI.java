package lab5;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * @author John Patterson 
 * Date : 12.10.18
 * 
 * GUI to demo basic swing functionality
 * 
 * Class methods 
 */


public class TeleporterGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel panel = new JPanel(); // the panel is not visible in output
	private JTextField tf = new JTextField(10); // accepts up to 10 characters
    private JTextArea ta = new JTextArea(); // Text Area at the Center
    
	public TeleporterGUI(String stationName) 
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
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);
//Button set up
    	JLabel label = new JLabel("Enter Text");
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
//Adding Components to the frame.
        getContentPane().add(BorderLayout.SOUTH, panel);
        getContentPane().add(BorderLayout.NORTH, mb);
        getContentPane().add(BorderLayout.CENTER, ta);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1200);
        setVisible(true);
//Setting overlay for pretty
        try
    	{
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    	}
    	catch(Exception ex)
    	{
    		System.out.println("Could not set look and feel");
    	}
	}
    public static void main(String args[]) throws Exception
    {
        new TeleporterGUI("station1");
    }
	private class StringActionListener implements ActionListener
    {
        @Override
		public void actionPerformed(ActionEvent arg0)
		{
			updateTextArea(tf.getText()); 
		}
    }
	private class ResetActionListener implements ActionListener
    {
        @Override
		public void actionPerformed(ActionEvent arg0)
		{
        	ta.setText(""); 
		}
    }
    public void updateTextArea(String input)
	{
    	input = input + "\n";
    	ta.setText(ta.getText() + input); 
    	tf.setText("");
		validate();
	}
	

}
