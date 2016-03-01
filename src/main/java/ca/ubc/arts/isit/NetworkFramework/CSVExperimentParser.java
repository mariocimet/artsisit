package ca.ubc.arts.isit.NetworkFramework;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by mario on 2/28/16.
 */
public class CSVExperimentParser {
	public static List<Thread> threads;
	public static List<User> users;
	public static String filepathNetwork;




	public void main()throws IOException{
	                /*
          Todo: Add parsers for each data source (do this elsewhere?)
               Code for parsing and loading CSV Files

                Having the .csv in main/resources allows for using the class-loader - need to
                change this to user selecting the file either with a GUI or the command-line
                 */

	filepathNetwork = "/China.csv";


	InputStream is = ISITMenu.class.getResourceAsStream("/China.csv");
	BufferedReader in = new BufferedReader(new InputStreamReader(is));

	Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);

//Todo: create lists of nodes, and lists of threads
	for(CSVRecord record:records){



	}





}

}
