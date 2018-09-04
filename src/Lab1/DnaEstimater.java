package Lab1;
import java.util.Random;
import java.lang.StringBuilder;
/**
 * @author johnn
 *
 */
public class DnaEstimater {
	public static void main(String[] args)
	{
		//* Local variables
		Random random = new Random();
		String Nucleotides[] = {"A","C","T","G"};
		int count = 0;
		int countA, countC, countT, countG  ;
		countA = countC = countT = countG = 0;
		StringBuilder Sequence = new StringBuilder();
		//* Runtime
		for(int i=1;i<3000;i=i+1)
		{
			Sequence.append(Nucleotides[random.nextInt(4)]);
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
					//System.out.println(Trimers);
					if( Trimers.equals("AAA"))
					{
						count++;
						//* p(AAA) = n choose k for all trimers assuming even dist.
					}
				}
		}
		System.out.printf("AAA trimer was found %s times %n",count);
		System.out.printf("A %s, T %s, C %s, G %s %n",countA, countT, countC, countG);
		double total = countA + countT + countC + countG;
		System.out.printf("p(A) %f, p(T) %f, p(C) %f, p(G) %f",countA/total, countT/total, countC/total, countG/total);
	}
}
