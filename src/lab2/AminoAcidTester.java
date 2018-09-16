package lab2;
import java.util.Random;
import java.io.Console;
/**
 * @author johnn
 * Date : 16.9.18
 * Base level requirement for lab2 quiz class
 */
public class AminoAcidTester
{
	public static void main(String[] args) 
	{	 
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
	System.out.println("Do you want to play a game?");
	String aString = System.console().readLine().toUpperCase();
		if( aString.equals("N"))
		{
			System.out.println("too bad");
		}
	long startTime = System.currentTimeMillis();
// Start the actual game loop	
	while(timer == false)
	{
		questVar++;
		int idx = new Random().nextInt(FULL_NAMES.length);
		System.out.println("What is the 1 letter abbreviation for "+ FULL_NAMES[idx] +" >.<");
		String bString = System.console().readLine().toUpperCase();
			if( bString.equals(SHORT_NAMES[idx]))
			{
			System.out.println("Ok, "+ bString +" was correct. Next...");
			scoreVar++;
			}
			else
			{
			System.out.println(bString+" was wrong :p.");
			break;
			}
		timer = evalTime(startTime);
	}
// End actual game loop
	System.out.println("Game over");
	System.out.println("Your score is "+ scoreVar +" out of possible "+ questVar);
	}
	
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