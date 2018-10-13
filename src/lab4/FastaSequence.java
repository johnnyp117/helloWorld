package lab4;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @author John Patterson 
 * Date : 12.10.18
 * 
 * Class designed to functionalize a FASTA file contents
 * Constructor holds sequence header, seq, and the location in the file from beginning
 * Class methods return obj specific info or operate over a FASTA file to populate obj
 */
public class FastaSequence 
{
	public String sequenceHeader  = new String();
	public String sequence  = new String();
	public int fastaLineIndex;
// constructor holds the relevant information for a particular fasta sequence
	public FastaSequence()
	{
		this.sequenceHeader = null;
		this.sequence = "";
		this.fastaLineIndex = 0;
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
					char[] tempLine = nextLine.toCharArray();
			// check for sequenceHeader or sequence line
					if( tempLine[0] == '>')
					{
						i++;
			// keeps this tempFastaObj in scope until the next header is found.
						FastaSequence tempFastaObj = new FastaSequence();
						tempFastaObj.sequenceHeader = new String(tempLine, 1, tempLine.length-1);
						foo.add(tempFastaObj);
					}
			// Assumes if not a header then is a sequence,combines seq two one string
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
	// Brings in fasta sequences and creates a unique set, which then is then turned into map of repetitions and FastaSequence objects
	public static void writeUnique(String inFile, String outFile) throws Exception
	{	
	// a list of unique FastaSequences is made by checking against a set of sequence strings
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(inFile);
		List<FastaSequence> fastaSequenceList =  new LinkedList<FastaSequence>();
		Set<String> fastaSequenceSet = new HashSet<String>();
		for( FastaSequence fs : fastaList)
		{
			if(!fastaSequenceSet.contains(fs.getSequence()))
			{
				fastaSequenceSet.add(fs.getSequence());
				fastaSequenceList.add(fs);
			}
		}
		Map<FastaSequence, Integer> mapOfFastaSequences = 
			fastaSequenceList.stream().
			collect(Collectors.toMap(e->e, e->e.getKey(fastaList)));
	// after creating the map of FastaSequences to number(int) of times they appear
	// they are sorted into a new map for printing
		LinkedHashMap<FastaSequence, Integer> sortedMap = new LinkedHashMap<>();
		mapOfFastaSequences.entrySet().stream().sorted(Map.Entry.comparingByValue())
	    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
	// Print this Key value pair to a file		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)));
		writer.write("repitions\tsequenceID\tratioAT\tratioCG\tsequence\n");
		for (Map.Entry<FastaSequence, Integer> entry : sortedMap.entrySet())
		{
		writer.write(entry.getValue() +"\t"+ entry.getKey().getHeader() +"\t"+entry.getKey().getNucleotideRatio('A','T') +"\t"+ 
				entry.getKey().getNucleotideRatio('C','G') + "\t"+ entry.getKey().getSequence() +"\n");	
		}
		writer.close();
	}
	// returns the number of times a FastaSeqence object's sequence appears in another List of FastaSequence objects
	public Integer getKey(List<FastaSequence> fastaInput)
	{
		String ref = this.getSequence();
		int numberOfRefSeq = 0;
		for( FastaSequence fs : fastaInput)
		{
			if(ref.equals(fs.getSequence()))
			{
			numberOfRefSeq++;
			}
		}
		return numberOfRefSeq;
	}
	// Takes a list of FastSequence objects and prints the list of obj's variables to file 
	public static void printFastaToTsv(List<FastaSequence> fastaList, String fullFilePath) throws Exception
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fullFilePath)));
		writer.write("sequenceID\tratioAT\tratioCG\tsequence\n");
		for( FastaSequence fs : fastaList)
		{
		writer.write(fs.getHeader() +"\t"+ fs.getNucleotideRatio('A','T') +"\t"+ 
				fs.getNucleotideRatio('C','G') + "\t"+ fs.getSequence() +"\n");	
		}
		writer.close();
		System.out.println("It has been printed to "+fullFilePath);
	}
	
	// returns the header of this sequence without the �>�
	public String getHeader()
	{
		return this.sequenceHeader;
	}

	// returns the raw sequence of a given FastaSequence
	public String getSequence() 
	{ 
		return this.sequence;
	}	
	// returns the number of chars (up to two) input as 'ref' divided by the length of this sequence
	public float getNucleotideRatio(char ref)
	{
		return this.getNucleotideRatio(' ', ref);
	}
	public float getNucleotideRatio(char ref, char ref2)
	{
		String seq = this.sequence;
		float refCount = 0;
		float allCount = 0;
		char[] charSeq = seq.toCharArray();
		// operates over char array and counts ref and not-ref chars
		for(int i=0;i <= charSeq.length-1;i++)
		{
			if(charSeq[i] == ref || charSeq[i] == ref2)
			{
			refCount++;
			}
			else
			{
			allCount++;
			}
		}
		float refRatio = refCount/(allCount+refCount);
		//System.out.println(ref+" "+ref2+" ratio is "+refRatio);
		return refRatio;
	}
	// main method to demo code for FastSequence class
	public static void main(String[] args) throws Exception
	{
		String fileOfInterest = System.getProperty("user.dir") + "\\src\\lab4\\FASTAinput";
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(fileOfInterest);
		for( FastaSequence fs : fastaList)
		{
		System.out.println(fs.getHeader());
		System.out.println(fs.getSequence());
		System.out.println("The GC ratio for this sequence is "+fs.getNucleotideRatio('G','C'));
		System.out.println("The ratio of just A is "+fs.getNucleotideRatio('A'));
		}
		String fileOutput = System.getProperty("user.dir") + "\\src\\lab4\\FastaOutput.txt";
		printFastaToTsv(fastaList ,fileOutput);
		String fileOutputForUnique = System.getProperty("user.dir") + "\\src\\lab4\\FastaUniqueoutput.txt";
		writeUnique(fileOfInterest, fileOutputForUnique);
	}
}


