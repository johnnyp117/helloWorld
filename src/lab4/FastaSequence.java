package lab4;
import java.io.*;
import java.util.*;

import lab3.ReadFASTA;
/**
 * @author John Patterson 
 * Date : 12.10.18
 * 
 * Class designed to functionalize a FASTA file contents
 * Will have categories of the file stored and callable by class methods
 */
public class FastaSequence 
{
	public String sequenceHeader  = new String();
	public String sequence  = new String();
	public int FASTALineIndex;
// constructor holds the relevant information for a particular fasta sequence
	public FastaSequence()
	{
		this.sequenceHeader = null;
		this.sequence = "";
		this.FASTALineIndex = 0;
	}
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception
	{
		List<FastaSequence> foo = readFastaFile(filepath, 0);
		return foo;
	}
	public static List<FastaSequence> readFastaFile(String filepath, int parseLength) throws Exception
	{
		if (parseLength == 0)
		{
			System.out.print("Running FASTA file reader in ");
			System.out.println(filepath);
			List<FastaSequence> foo = new LinkedList<FastaSequence>();
			BufferedReader FASTAfile = new BufferedReader(new FileReader(new File(filepath)));
			//String nextLine = FASTAfile.readLine();
			//int countOfLine = 0;
			int i = -1;
			for(String nextLine = FASTAfile.readLine(); nextLine != null; nextLine = FASTAfile.readLine())
			{
			// Breaks nextLine into charArray for checking
					System.out.println(nextLine);
					char[] tempLine = nextLine.toCharArray();
			// check for sequenceHeader or sequence line
					if( tempLine[0] == '>')
					{
						i++;
						FastaSequence tempFastaObj = new FastaSequence();
						tempFastaObj.sequenceHeader = new String(tempLine, 1, tempLine.length-1);
						foo.add(tempFastaObj);
						System.out.println(tempLine[1]);						
					}
					else 
					{
						String tempSeq = foo.get(i).sequence;
						foo.get(i).sequence = tempSeq + nextLine;
					}
					//i++;
			}
			FASTAfile.close();
			System.out.println("The file has been parsed");
			return foo;
		}
		else
		{
			List<FastaSequence> foo = new LinkedList<FastaSequence>();
			return foo;
		}
	}
	public static void writeUnique(File inFile, File outFile ) 
			throws Exception
		{	
			
		}

//	public static List<FastaSequence> printFastaToTsv(FastaSequence foo, String fullFilePath) throws Exception
//	{
//		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fullFilePath)));
//		writer.write("repitions\tsequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\n");
//		for(int i =0;i <= foo.sequenceID.size()-1;i++)
//		{
//		writer.write(foo.sequenceID.get(i) +"\t"+ countLetter('A',foo.sequence.get(i)) +"\t"+ 
//		countLetter('C',foo.sequence.get(i)) +"\t"+ countLetter('T',foo.sequence.get(i)) +"\t"+ 
//		countLetter('G',foo.sequence.get(i)) +"\t"+ foo.sequence.get(i) +"\n");	
//		}
//		writer.close();
//		System.out.println("It has been printed to "+fullFilePath);
//	}
	
	// returns the header of this sequence without the “>”
	public String getHeader()
	{
		return this.sequenceHeader;
	}

	// returns the raw sequence of a given FastaSequence
	public String getSequence() 
	{ 
		return this.sequence;
	}	
	// returns the number of G’s and C’s divided by the length of this sequence
	// make generic char arg of up to 4? or 2 char arg ? generalize it would be easier
	public float getNucleotideRatio(char ref, char ref2)
	{
		String seq = this.sequence;
		int refCount = 0;
		int allCount = 0;
		char[] charSeq = seq.toCharArray();
		for(int i=0;i <= charSeq.length-1;i++)
		{
			if(charSeq[i] == (ref | ref2))
			{
			refCount++;
			}
			else
			{
			allCount++;
			}
		}
		float refRatio = refCount/allCount;
		System.out.println(ref+ref2+" ratio is "+ refRatio);
		return refRatio;
	}
	// extra credit, doesnt keep it in ram space. 
	// so batches would be ideal and output at each step?
	// public FastaSequence getNextSequence()
	public static void main(String[] args) throws Exception
	{
		String fileOfInterest = System.getProperty("user.dir") + "\\src\\lab4\\FASTAinput";
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(fileOfInterest);
		for( FastaSequence fs : fastaList)
		{
		System.out.println(fs.getHeader());
		System.out.println(fs.getSequence());
		System.out.println(fs.getNucleotideRatio('G','C'));
		}
	}
}


