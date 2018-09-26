package lab3;
import java.io.*;
import java.util.*;

/**
 * @author John Patterson 
 * Date : 24.9.18
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
		//System.out.println("Please enter the name of the FASTA file.");
		//String inString = System.console().readLine();
		System.out.println("Running FASTA file reader.");
		String fullFilePath = System.getProperty("user.dir") + "\\src\\lab3\\FASTAinput";
		System.out.println(fullFilePath);
		this.FASTAfile = new BufferedReader(new FileReader(new File(fullFilePath)));
	}
	
	public static int countLetter(char ref, String seq) 
	{
		int refCount = 0;
		char[] charSeq = seq.toCharArray();
		for(int i=0;i <= charSeq.length;i++)
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
		writer.write("sequenceID\tnumA\tnumC\\tnumG\\tnumT\\tsequence\n");
		for(int i =0;i <= foo.sequenceID.size();i++)
		{
		writer.write(foo.sequenceID.get(i)+ countLetter('A',foo.sequence.get(i))+"\t"+ countLetter('C',foo.sequence.get(i))+"\t"
		+ countLetter('T',foo.sequence.get(i))+"\t"+ countLetter('G',foo.sequence.get(i))+"\t"
		+foo.sequence.get(i)+"\n");	
		}
		writer.close();
		System.out.println("It has been printed to "+fullFilePath);
	}
	
// main that operates the logic flow
public static void main(String[] args) throws Exception
{
	ReadFASTA foo = new ReadFASTA();
	for(String nextLine = foo.FASTAfile.readLine(); nextLine != null; 
			nextLine = foo.FASTAfile.readLine())
	{
		System.out.println(nextLine);
		String[] tempLine = nextLine.split(">",2);
		if( tempLine[0].equals('>'))
		{
		foo.sequenceID.add(tempLine[1]);
		System.out.println(tempLine[1]);
		}
		else 
		{
		String entireSequnce = new String();
		int lineCount = 0;
		while(nextLine.split(">",2)[0] != ">")
		{
			if(lineCount == 0)
			{
			entireSequnce = nextLine;
			}
			else
			{
			entireSequnce = entireSequnce+nextLine;
			}
		}
		foo.sequenceID.add(entireSequnce);
		}
	}
	foo.FASTAfile.close();	
	FASTA2tsv(foo);
}
}
