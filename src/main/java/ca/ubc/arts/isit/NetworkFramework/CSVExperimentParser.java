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
	public static String filepathUsers;




	public void main()throws IOException{
	                /*

	             Todo: Add parsers for each data source (do this elsewhere?)
               Code for parsing and loading CSV Files

                Having the .csv in main/resources allows for using the class-loader - need to
                change this to user selecting the file either with a GUI or the command-line
                 */

		filepathUsers = "/Users.csv";
		filepathNetwork = "/China.csv";



		InputStream users = ISITMenu.class.getResourceAsStream(filepathUsers);
		BufferedReader usersInput = new BufferedReader(new InputStreamReader(users));

		Iterable<CSVRecord> userRecords = CSVFormat.EXCEL.withHeader().parse(usersInput);


	InputStream discussion = ISITMenu.class.getResourceAsStream(filepathNetwork);
	BufferedReader discussionInput = new BufferedReader(new InputStreamReader(discussion));

	Iterable<CSVRecord> forumRecords = CSVFormat.EXCEL.withHeader().parse(discussionInput);

//Todo: create lists of nodes, and lists of threads
	for(CSVRecord record:forumRecords){

		if(record.get("_type").equalsIgnoreCase("commentThread")){

			Thread thread = new Thread(record.get("_id__$oid"),  new User( 1, "yo", "yo"));
			threads.add(thread);

		}



	}





}

}
