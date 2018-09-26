package lab3;
import java.io.*;
import java.util.*;
/**
 * @author John Patterson 
 * Date : 25.9.18
 * 
 * Read in FASTA file, spit out tsv file.
 */
public class ReadFASTA 
{
// Functional parts of the main	
	ArrayList<String> sequenceID  = new ArrayList<String>();
	ArrayList<String> sequence  = new ArrayList<String>();
	BufferedReader FASTAfile;
	public ReadFASTA() throws Exception
	{
		this.sequenceID  = new ArrayList<String>();
		this.sequence  = new ArrayList<String>();
		System.out.println("Running FASTA file reader.");
// name requires absolute file name
		String fullFilePath = System.getProperty("user.dir") + "\\src\\lab3\\FASTAinput2.txt";
		System.out.println(fullFilePath);
		this.FASTAfile = new BufferedReader(new FileReader(new File(fullFilePath)));
	}

	public static int countLetter(char ref, String seq) 
	{
		int refCount = 0;
		char[] charSeq = seq.toCharArray();
		for(int i=0;i <= charSeq.length-1;i++)
		{
			if(charSeq[i] == ref)
			{
			refCount++;
			}
		}
		return refCount;
	}
	
	public static void FASTA2tsv(ReadFASTA foo) throws Exception
	{
		String fullFilePath = System.getProperty("user.dir") + "\\src\\lab3";
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fullFilePath+"\\output")));
		writer.write("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\n");
		for(int i =0;i <= foo.sequenceID.size()-1;i++)
		{
		writer.write(foo.sequenceID.get(i) +"\t"+ countLetter('A',foo.sequence.get(i)) +"\t"+ 
		countLetter('C',foo.sequence.get(i)) +"\t"+ countLetter('T',foo.sequence.get(i)) +"\t"+ 
		countLetter('G',foo.sequence.get(i)) +"\t"+ foo.sequence.get(i) +"\n");	
		}
		writer.close();
		System.out.println("It has been printed to "+fullFilePath);
	}
	
// main that operates the logic flow
public static void main(String[] args) throws Exception
{
	ReadFASTA foo = new ReadFASTA();
// used to logically concat. the strings between > containing lines.
	int lineCount = 0;
	String entireSequnce = new String();
	for(String nextLine = foo.FASTAfile.readLine(); nextLine != null; nextLine = foo.FASTAfile.readLine())
	{
// Breaks nextLine into charArray; I go on to recompile this to a String
		System.out.println(nextLine);
		char[] tempLine = nextLine.toCharArray();
// check for sequenceID or sequence line
		if( tempLine[0] == '>')
		{
		foo.sequenceID.add(new String(tempLine, 1, tempLine.length-1));
		System.out.println(tempLine[1]);
			if(lineCount>0)
			{
				foo.sequence.add(entireSequnce);
				lineCount = 0;
			}	
		}
		else 
		{
			if(lineCount == 0)
			{
			entireSequnce = null;
			entireSequnce = nextLine;
			lineCount++;
			}
			else
			{
			entireSequnce = entireSequnce+nextLine;
			}
		}
	}
// bug found and squashed. 
	foo.sequence.add(entireSequnce);
	foo.FASTAfile.close();
	FASTA2tsv(foo);
}
}
