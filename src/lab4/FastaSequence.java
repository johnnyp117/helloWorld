package lab4;
import java.io.*;
import java.util.*;

import lab3.ReadFASTA;
/**
 * @author John Patterson 
 * Date : 4.10.18
 * 
 * Read in FASTA file as an object
 * Will have categories of the file stored and callable by class methods
 */
public class FastaSequence {
	
	private final String fullFilePath = null;
	private ArrayList<String> sequenceID  = new ArrayList<String>();
	private ArrayList<String> sequence  = new ArrayList<String>();
	public int FASTALineIndex = 0;
	
	public FastaSequence()
	{
		// make part of class instance so multiple files could be read in during main
		this.sequenceID = null;
		this.sequence = null;
		this.FASTALineIndex = 0;
		//this.fullFilePath = filepath;
		
	}
	
	public static List<FastaSequence> readFastaFile(String filepath, String parseLength) throws Exception
	{

		System.out.println("Running FASTA file reader.");
		System.out.println(filepath);
		int lineIndex = FastaSequence.getNextFastaLine();
		BufferedReader FASTAfile = new BufferedReader(new FileReader(new File(filepath)));
		String nextLine = FASTAfile.readLine(lineIndex);
		 if( nextLine != null) 
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
		 return this.FASTALineIndex = FASTALineIndex;
		
	}
	
	public int getNextFastaLine() 
	{		
		return this.FASTALineIndex;
	}

	public static void printFastaToTsv(ReadFASTA foo) throws Exception
	{
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
	
	// returns the header of this sequence without the “>”
	public String getHeader(int seqNum)
	{
		return this.sequenceID.get(seqNum);
	}

	// returns the Dna sequence of this FastaSequence
	public String getSequence(int seqNum) 
	{ 
		return this.sequence.get(seqNum);
	}	
	// returns the number of G’s and C’s divided by the length of this sequence
	// make generic char arg of up to 4? or 2 char arg ? generalize it would be easier
	public float getGCRatio(char ref, char ref2, String seq)
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
		System.out.println(ref+ref2+" ratio is "+ );
		return refCount;
	}
	
	// extra credit, doesnt keep it in ram space. 
	//could print to file and read it in every 'sequence' and update output?
	// realistically file IO is $$, but balance between memory space and time,
	// so batches would be ideal and output at each step?
	//public FastaSequence getNextSequence()

	public static void main(String[] args) throws Exception
	{
		
		String fileOfInterest = 
				System.getProperty("user.dir") + "\\src\\lab4\\FASTAinput.txt";
		
		List<FastaSequence> fastaList = 
				FastaSequence.readFastaFile();
	
		for( FastaSequence fs : fastaList)
		{
		System.out.println(fs.getHeader());
		System.out.println(fs.getSequence());
		System.out.println(fs.getGCRatio());
		}
	}


}
