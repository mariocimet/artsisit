package ca.ubc.arts.isit.NetworkFramework;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.*;

/**
 * Created by mario on 2/28/16.
 */
public class CSVDataParser {
	public static Map<String, Thread> threads;
	public static Map<Integer, User> users;
	public static String filepathNetwork;
	public static String filepathUsers;
	public static String filepathGrades;


	public CSVDataParser(){

	}

	public void main() throws IOException {


		// Paths for CSV Files Todo: Switch this to a file chooser GUI
		filepathUsers = "/Users.csv";
		filepathNetwork = "/China.csv";
		filepathGrades = "/Grades.csv";

		threads = new HashMap<String,Thread>();
		users = new HashMap<Integer, User>();



		// CSV Source for the Users
		InputStream us = ISITMenu.class.getResourceAsStream(filepathUsers);
		BufferedReader usersInput = new BufferedReader(new InputStreamReader(us));
		Iterable<CSVRecord> userRecords = CSVFormat.EXCEL.withHeader().parse(usersInput);

		//Todo: add in grade parsing
/*
		//CSV Source for Grades
		InputStream gs = ISITMenu.class.getResourceAsStream(filepathUsers);
		BufferedReader gradesInput = new BufferedReader(new InputStreamReader(gs));
		Iterable<CSVRecord> gradeRecords = CSVFormat.EXCEL.withHeader().parse(gradesInput);
*/

		// CSV Source for the Discussion Forum
		InputStream discussion = ISITMenu.class.getResourceAsStream(filepathNetwork);
		BufferedReader discussionInput = new BufferedReader(new InputStreamReader(discussion));
		Iterable<CSVRecord> forumRecords = CSVFormat.EXCEL.withHeader().parse(discussionInput);

		//Populate list of Users, using integer Id's as keys
		for(CSVRecord record : userRecords){

		User u = new User( Integer.parseInt(record.get("id")), record.get("username"), record.get("hash_id"));

			users.put(Integer.parseInt(record.get("id")), u);
		}


		//Create dynamic attribute lists for Users
		/*
		for(CSVRecord record : gradeRecords){
			User u = users.get(Integer.parseInt(record.get("id")));

			//TODO: Parse the Date
		if(!record.get("max_grade").equalsIgnoreCase("NULL")) u.gradeAttribute.put(parseDate(record.get("created")), Float.parseFloat(record.get("grade")));

		}
		*/

		//Create list of threads with ordered lists of comments
 		for (CSVRecord record : forumRecords) {

			//CSV column names for comment fields
			String body = record.get("body");
			String type = record.get("_type");
			String commentId = record.get("_id__$oid");
			String threadID = record.get("comment_thread_id__$oid");
			int authorID = Integer.parseInt(record.get("author_id"));


			//Todo: fix, Currently erroring
			// long date = Long.parseLong(record.get("created_at__$date"));
			long date = 0;
			boolean isThreadStarter = type.equalsIgnoreCase("commentThread");


			Comment c = new Comment(users.get(authorID), body, threadID, date, isThreadStarter);



			//If a post is a new thread, create a thread object and initialize the list of users with the poster
			if (isThreadStarter) {
				Thread thread = new Thread(commentId, c);
				threads.put(commentId, thread);
			}

			if (type.equalsIgnoreCase("comment")) {
				threads.get(threadID).replies.add(c);
			}

		}

	}

	//Todo: Implement this method
	private Date parseDate(String date){
		return null;

	}

}
