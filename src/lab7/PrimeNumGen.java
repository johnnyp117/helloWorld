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
// i7 6600U, 4 logical processors
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
	private List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
	private final Semaphore semaphore = new Semaphore(4);
	private volatile boolean endFlag = false;

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
	
	private synchronized void addActionListeners()
	{
		cancelButton.addActionListener(new CancelOption());
	
		primeButton.addActionListener(new ActionListener() 
		{
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
						int start =1;
//						boolean endFlag = false;
// implement a for loop with bins that throw to threads.....
						long startTime = System.currentTimeMillis();
						while(!endFlag) 
						{
							if(start+100<max)
							{
							new Thread(new UserInput(start,start+100)).start();
							System.out.println("new thread with start "+start);
							start=start+100;
							}
							else
							{
							start=(max-start)-max;
							UserInput finale = new UserInput(start,max);
							new Thread(finale).start();
							endFlag = true;
//							try {
//								finale.wait();
//							} catch (InterruptedException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
							}
						
						final StringBuffer buff = new StringBuffer();
						//list.sort(null);
						for( Integer i2 : list)
							buff.append(i2 + "\n");
						if( cancel)
							buff.append("cancelled\n");
//						startTime = getStartTime();
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
						}// end while loop for binning
					}//the > if max != null
			}});
	}
	
	private boolean isPrime( int i) 
	{
		for( int x=2; x < i -1; x++)
			if( i % x == 0  )
				return false;
		System.out.println("I ran yo");
		return true;
	}
	
	private class UserInput implements Runnable
	{
		private final int end;
		private final int start;
		private final long startTime;

		private UserInput(int num, int num1)
		{
			this.end = num;
			this.start = num1;
			this.startTime = System.currentTimeMillis();
		}
		public void run()
		{
			try 
			{
				semaphore.acquire();
				long lastUpdate = System.currentTimeMillis();
				for (int i = start; i < end && ! cancel; i++) 
				{
					if( isPrime(i))
					{
						list.add(i);
							
						if( System.currentTimeMillis() - lastUpdate > 500)
						{
							float time = (System.currentTimeMillis() -startTime )/1000f;
							final String outString= "Found " + list.size() + " in " + i + " of " + end + " " 
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
//				final StringBuffer buff = new StringBuffer();
//				//list.sort(null);
//				for( Integer i2 : list)
//					buff.append(i2 + "\n");
//				
//				if( cancel)
//					buff.append("cancelled\n");
//				
//				float time = (System.currentTimeMillis() - startTime )/1000f;
//				buff.append("Time = " + time + " seconds " );
//				
//				SwingUtilities.invokeLater( new Runnable()
//				{
//					@Override
//					public void run()
//					{
//						cancel = false;
//						primeButton.setEnabled(true);
//						cancelButton.setEnabled(false);
//						aTextField.setText( (cancel ? "cancelled " : "") +  buff.toString());	
//					}
//				});				
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
//				this.notifyAll();
			}
		}// end run
		
	}  // end UserInput
}