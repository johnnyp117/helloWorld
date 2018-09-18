package lab2;
import java.util.Random;

/**
 * @author johnn
 * Date : 16.9.18
 * 
 * Some extra credit level requirement for lab2 quiz class
 * EC: Pass length of quiz, in time mode will return worst residue at end
 */
public class AminoAcidTester
{
	public static void main(String[] args) 
	{	 
// Variables for game
	boolean timer, gameEnd, timerFlag, surviveFlag; 
	timer = gameEnd = timerFlag = surviveFlag = false;
	int scoreVar, questVar;
	String timerVar = null;
	scoreVar = questVar = 0;
	final String[] SHORT_NAMES = { "A","R", "N", "D", "C", "Q", "E", 
			"G",  "H", "I", "L", "K", "M", "F", 
			"P", "S", "T", "W", "Y", "V" };
	final String[] FULL_NAMES = {"alanine","arginine", "asparagine", 
			"aspartic acid", "cysteine","glutamine",  "glutamic acid",
			"glycine" ,"histidine","isoleucine","leucine",  "lysine", "methionine", 
			"phenylalanine", "proline","serine","threonine","tryptophan","tyrosine", "valine"};
	int[] errorArray = new int[SHORT_NAMES.length]; 
// Opening of game
	System.out.println("You know why you're here. Do you want to play timed or survival?");
	String inString = System.console().readLine();
		if( inString.equals("timed"))
		{
			System.out.println("Enter seconds desired to play");
			timerVar = System.console().readLine();
			timerFlag = true;
		}
		else if ( inString.equals("survival"))
		{
			System.out.println("Get ready");
			surviveFlag = true;
			
		}
	long startTime = System.currentTimeMillis();
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
			else
			{
			errorArray[idx]++;
			}
		if(timerFlag == true)
		{
		timer = evalTime(startTime, timerVar);
		gameEnd = timer;
		}
	}
// End actual game loop, prints out results
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

}