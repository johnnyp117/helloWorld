package Lab1;
import java.util.Random;
import java.lang.StringBuilder;
/**
 * @author johnn
 * Date : 5.9.18
 */
public class DnaEstimater
{
	public static void main(String[] args)  throws Exception
	{
//* Local variables
		Random randyVar = new Random();
		int count = 0;
		int countA, countC, countT, countG ;
		countA = countC = countT = countG = 0;
		StringBuilder Sequence = new StringBuilder();
//* Main functionality 
		for(int i=1;i<3000;i=i+1)
		{
//*  nextFloat gens a num 0-1; which nucNumber correlates to Nucleotides distribution
			float random = randyVar.nextFloat();
			Sequence.append(nucNumber(random));
			switch(Sequence.substring(i-1))
			{
			case "A": countA++;
			break;
			case "C":countC++;
			break;
			case "G":countG++;
			break;
			case "T":countT++;
			break;
			}
			if(i%3 == 0)
				{	
					String Trimers =  Sequence.substring(i-3,i);
					if( Trimers.equals("AAA"))
					{
						count++;
					}
				}
		}
		System.out.printf("AAA trimer was found %s times %n",count);
		System.out.printf("A %s, T %s, C %s, G %s %n",countA, countT, countC, countG);
		double total = countA + countT + countC + countG;
		System.out.printf("p(A) %f, p(T) %f, p(C) %f, p(G) %f",countA/total, countT/total, countC/total, countG/total);
	}
/**
 * 	This method can be modified for another distribution at the conditionals
 * @param randomNum needs to be between 0 and 1
 * @return string representing nucleotide
 */
	public static String nucNumber(float randomNum) 
	{
		String Nucleotides[] = {"A","C","T","G"};
	    if (randomNum < 0.11)
	    {
	    	return Nucleotides[2];
	    }
	    else if(randomNum < 0.23)
	    {
	    	
	    	return Nucleotides[0];
	    }
	    else if(randomNum < 0.61)
	    {
	    	return Nucleotides[1];
	    }
// to lazy to handle errors. 
	    else 
	    {
	    	return Nucleotides[3];
	    } 
	}
}
