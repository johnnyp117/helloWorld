package lab7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

// for 10,000, single thread = 0.024 sec
// for 100,000 single thread = 1.265 sec
public class PrimeNumGen extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final JTextArea aTextField = new JTextArea();
	private final JButton primeButton = new JButton("Start");
	private final JButton cancelButton = new JButton("Cancel");
	private volatile boolean cancel = false;
	private final PrimeNumGen thisFrame;
	private final Semaphore semaphore = new Semaphore(4);
	private List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

	
	public static void main(String[] args)
	{
		PrimeNumGen png = new PrimeNumGen("Primer Number Generator");
		
		// don't add the action listener from the constructor
		png.addActionListeners();
		png.setVisible(true);
	}
	
	private PrimeNumGen(String title)
	{
		super(title);
		this.thisFrame = this;
		cancelButton.setEnabled(false);
		aTextField.setEditable(false);
		setSize(400, 200);
		setLocationRelativeTo(null);
		//kill java VM on exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(primeButton,  BorderLayout.SOUTH);
		getContentPane().add(cancelButton,  BorderLayout.EAST);
		getContentPane().add( new JScrollPane(aTextField),  BorderLayout.CENTER);
	}
	
	private class CancelOption implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			cancel = true;
		}
	}
	
	private void addActionListeners()
	{
		cancelButton.addActionListener(new CancelOption());
	
		primeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					
					String num = JOptionPane.showInputDialog("Enter a large integer");
					Integer max =null;
					
					try
					{
						max = Integer.parseInt(num);
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(
								thisFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
					
					if( max != null)
					{
						aTextField.setText("");
						primeButton.setEnabled(false);
						cancelButton.setEnabled(true);
						cancel = false;
						
						new Thread(new UserInput(max)).start();

					}
				}});
		}
	
	private boolean isPrime( int i) 
	{
		for( int x=2; x < i -1; x++)
			if( i % x == 0  )
				return false;
		
		return true;
	}
	
	private class UserInput implements Runnable
	{
		private final int max;
		private final long startTime;
		
		private UserInput(int num)
		{
			this.max = num;
			this.startTime = System.currentTimeMillis();
		}
		
		public void run()
		{
			try 
			{
				long lastUpdate = System.currentTimeMillis();
				for (int i = 1; i < max && ! cancel; i++) 
				{
					semaphore.acquire();
					if( isPrime(i))
					{
						list.add(i);
							
						if( System.currentTimeMillis() - lastUpdate > 500)
						{
							float time = (System.currentTimeMillis() -startTime )/1000f;
							final String outString= "Found " + list.size() + " in " + i + " of " + max + " " 
										+ time + " seconds ";
							
							SwingUtilities.invokeLater( new Runnable()
							{
								@Override
								public void run()
								{
									aTextField.setText(outString);
								}
							});
							
							lastUpdate = System.currentTimeMillis();	
						}
					}
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println("exit");
				System.exit(1);				
			}
			finally
			{
				semaphore.release();
			}
			final StringBuffer buff = new StringBuffer();
			
			for( Integer i2 : list)
				buff.append(i2 + "\n");
			
			if( cancel)
				buff.append("cancelled\n");
			
			float time = (System.currentTimeMillis() - startTime )/1000f;
			buff.append("Time = " + time + " seconds " );
			
			SwingUtilities.invokeLater( new Runnable()
			{
				@Override
				public void run()
				{
					
					cancel = false;
					primeButton.setEnabled(true);
					cancelButton.setEnabled(false);
					aTextField.setText( (cancel ? "cancelled " : "") +  buff.toString());
					
				}
			});
			
			
			
		}// end run
		
	}  // end UserInput
}