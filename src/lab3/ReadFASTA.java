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
	public String[] sequenceID  = new String[0];
	public String[] sequence  = new String[0];
	int numA = 0;
	int numC = 0;
	int numT = 0;
	int numG = 0;
	public static void main(String[] args) throws Exception
	{
		System.out.println("Please enter the name of the FASTA file.");
		String inString = System.console().readLine();
		String fullFilePath = System.getProperty("user.dir") +inString;
		BufferedReader FASTAfile = new BufferedReader(new FileReader(new File(fullFilePath)));
		
		int lineCount = 0;
		for(String nextLine = FASTAfile.readLine(); nextLine != null; nextLine = FASTAfile.readLine())
		{
			if( nextLine.split(">")[1] == ">")
			{
				
			}
			else 
			{
				continue;
			}
			
			
		}
		FASTAfile.close();	
	}
	
	
}
