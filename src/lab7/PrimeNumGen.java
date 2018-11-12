package lab7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
// i7 6600U, 4 logical processors
// for 10,000 = 0.024 sec AF: 0.022 sec JP
// for 100,000 = 1.265 sec Af: 0.772 sec JP
// for 1,000,000 = ? sec AF: 55.41 sec JP
public class PrimeNumGen extends JFrame
{
	
	private static final long serialVersionUID = 1L;
	private final JTextArea aTextField = new JTextArea();
	private final JButton primeButton = new JButton("Start");
	private final JButton cancelButton = new JButton("Cancel");
	private volatile boolean cancel = false;
	private final PrimeNumGen thisFrame;
	private static final int NUMCORES = Runtime.getRuntime().availableProcessors();
//	private final Semaphore semaphore = new Semaphore(NUMCORES);
	private List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

	public static void main(String[] args)
	{
		System.out.println("Your number of cores is "+ NUMCORES);
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
		private Semaphore semaphore = new Semaphore(NUMCORES);
		private UserInput(int num)
		{
			this.max = num;
			this.startTime = System.currentTimeMillis();
		}
		public void run()
		{
			if(max%NUMCORES == 0)
			{
				System.out.println("Check 1 even");
				int gradient = max/NUMCORES;
				for (int i = 0; i < NUMCORES; i++) 
				{
					try {semaphore.tryAcquire(1, TimeUnit.MILLISECONDS);} 
					catch (InterruptedException e) {e.printStackTrace();}
					numIterator currentBin = new numIterator((gradient*i), (gradient*(i+1))-1, semaphore);
					new Thread(currentBin).start();
				}
			}
			else if(max%(NUMCORES+1) == 0)
			{
				System.out.println("Check 1 odd");
				int gradient = max/(NUMCORES+1);
				for (int i = 0; i < (NUMCORES+1); i++) 
				{
					try {semaphore.tryAcquire(1, TimeUnit.MILLISECONDS);} 
					catch (InterruptedException e) {e.printStackTrace();}
					numIterator currentBin = new numIterator((gradient*i), (gradient*(i+1))-1, semaphore);
					new Thread(currentBin).start();
				}
			}
			else
			{
				System.out.println("Your number is stupid, input a new one");
				return;
			}
//			the stuff in numIterator run() went here.
			int numAcquired = 0;
			while(numAcquired < NUMCORES)
			{
				try{semaphore.acquire();} 
				catch (InterruptedException e) {e.printStackTrace();}
				numAcquired++;
			}
			final StringBuffer buff = new StringBuffer();
			synchronized(list)
			{
			for( Integer i2 : list)
				buff.append(i2 + "\n");
			
			if( cancel)
				buff.append("cancelled\n");
			
			float time = (System.currentTimeMillis() - startTime )/1000f;
			buff.append("Time = " + time + " seconds " );
			}
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
	private class numIterator implements Runnable
	{
		private final int endNum;
		private final int startNum;
		private final long startTime;
		private final Semaphore semaphore;
		private numIterator(int num1, int num2, Semaphore currentThread)
		{
			this.endNum = num2;
			this.startNum = num1;
			this.startTime = System.currentTimeMillis();
			this.semaphore = currentThread;
		}
		public void run()
		{
			System.out.println(this.startNum+" Bin has started");
			long lastUpdate = System.currentTimeMillis();
			for (int i = startNum; i < endNum && ! cancel; i++) 
			{
				if(isPrime(i))
				{
					list.add(i);
						
					if( System.currentTimeMillis() - lastUpdate > 500)
					{
						float time = (System.currentTimeMillis() -startTime )/1000f;
						final String outString= "Found " + list.size() + " in " + i + " of " + endNum + " " 
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
			semaphore.release();
			System.out.println(this.startNum+" Bin has finished");
		}// end run
	}//end numIterator
}