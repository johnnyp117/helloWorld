package lab2;
import java.util.Random;

/**
 * @author johnn
 * Date : 16.9.18
 * 
 * Base level requirement for lab2 quiz class
 */
public class AminoAcidTester
{
	public static void main(String[] args) 
	{	 
// Variables for game
	boolean timer = false;
	int scoreVar, questVar;
	scoreVar = questVar = 0;
	final String[] SHORT_NAMES = { "A","R", "N", "D", "C", "Q", "E", 
			"G",  "H", "I", "L", "K", "M", "F", 
			"P", "S", "T", "W", "Y", "V" };
	final String[] FULL_NAMES = {"alanine","arginine", "asparagine", 
			"aspartic acid", "cysteine","glutamine",  "glutamic acid",
			"glycine" ,"histidine","isoleucine","leucine",  "lysine", "methionine", 
			"phenylalanine", "proline","serine","threonine","tryptophan","tyrosine", "valine"};
// Opening of game
	System.out.println("Do you want to play a game?");
	String inString = System.console().readLine().toUpperCase();
		if( inString.equals("N"))
		{
			System.out.println("too bad, dance puppet.");
		}
	long startTime = System.currentTimeMillis();
// Start the actual game loop	
	while(timer == false)
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
			else
			{
			System.out.println(ansString+" was wrong :p. You needed "+SHORT_NAMES[idx]);
			break;
			}
		timer = evalTime(startTime);
	}
// End actual game loop, prints out results
	System.out.println("Game over");
	System.out.println("Your score is "+ scoreVar +" out of possible "+ questVar);
	}
// Method to evaluate time
	public static Boolean evalTime(long timeVar)
	{
		long startTime = System.currentTimeMillis();
		if((startTime-timeVar)>30000)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}