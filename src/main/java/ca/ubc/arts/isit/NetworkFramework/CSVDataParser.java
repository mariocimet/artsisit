package ca.ubc.arts.isit.NetworkFramework;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;

/**
 * @author MarioCimet
 *
 * 	This class handles parsing of the csv files which contain the relevant data

 */
public class CSVDataParser {
	public static Map<String, Thread> threads;
	public static Map<Integer, User> users;
	public static String filepathNetwork;
	public static String filepathUsers;
	public static String filepathGrades;



	public CSVDataParser(){}

	public void main() throws IOException, ParseException {

		Date endDate =  new Date(1423580832420L);

		//Big Todo: replace with Google BigQuery requests

		//Small Todo: Switch this to a file chooser GUI
		filepathUsers = "/Users.csv";
		filepathNetwork = "/China.csv";
		filepathGrades = "/Grades.csv";

		threads = new HashMap<String,Thread>();
		users = new HashMap<Integer, User>();

		//CSV Source for Users
		InputStream us = ISITMenu.class.getResourceAsStream(filepathUsers);
		BufferedReader usersInput = new BufferedReader(new InputStreamReader(us));
		Iterable<CSVRecord> userRecords = CSVFormat.EXCEL.withHeader().parse(usersInput);



		//CSV Source for Grades
		InputStream gs = ISITMenu.class.getResourceAsStream(filepathGrades);
		BufferedReader gradesInput = new BufferedReader(new InputStreamReader(gs));
		Iterable<CSVRecord> gradeRecords = CSVFormat.EXCEL.withHeader().parse(gradesInput);


		// CSV Source for the Discussion Forum
		InputStream discussion = ISITMenu.class.getResourceAsStream(filepathNetwork);
		BufferedReader discussionInput = new BufferedReader(new InputStreamReader(discussion));
		Iterable<CSVRecord> forumRecords = CSVFormat.EXCEL.withHeader().parse(discussionInput);


		/*
		Purpose: Extract data from discussion forum logs.
		Pre-Condition: forumRecords MUST be sorted by comment type (CommentThreads before Comments) and date (old to new)
		 */
 		for (CSVRecord record : forumRecords) {

			//Getting Comment parameters in appropriate types from String fields in data
			String body = record.get("body");
			String type = record.get("_type");
			String commentId = record.get("_id__$oid");
			String threadID = record.get("comment_thread_id__$oid");
			int authorID = Integer.parseInt(record.get("author_id"));
			String userName = record.get("author_username");
			long dateLong = Double.valueOf(record.get("created_at__$date")).longValue();
			Date date = new Date(dateLong);
			boolean isThreadStarter = type.equalsIgnoreCase("commentThread");

			//If user hasn't been seen yet, create user object and save date of the first post
			if(!users.containsKey(authorID)){
				User u = new User(authorID, userName, date);
				users.put(authorID, u);
			}

			Comment c = new Comment(users.get(authorID), body, threadID, date, isThreadStarter);

			//If a post is a new thread, create a thread object and initialize the list of users with the poster
			if (isThreadStarter) {
				Thread thread = new Thread(commentId, c);
				threads.put(commentId, thread);
			}

			//Otherwise, add the comment to the relevant thread
			if (type.equalsIgnoreCase("comment")) {
				threads.get(threadID).replies.add(c);
			}

		}
/*

		// Purpose: record any users who did not participate in discussion, who may have grades
		for(CSVRecord record:userRecords){
			if (!users.containsKey(Integer.parseInt(record.get("id")))){
				int id = Integer.parseInt(record.get("id"));
				String userName = record.get("username");
				User u = new User(id, userName, endDate);
			}
		}
*/


		/*
		Purpose: create longitudinal grade attribute for students
		Pre-Condition: gradeRecords MUST be cleaned of all non-graded rows (where maxGrade is NULL),
					   gradeRecords MUST be sorted by date (old to new)
		 */

		for(CSVRecord record:gradeRecords){
			if(record.get("student_id").isEmpty()) break;
			User user = users.get(Integer.parseInt(record.get("student_id")));
			Integer grade = Integer.parseInt(record.get("grade"));
			Integer maxGrade = Integer.parseInt(record.get("max_grade"));

			user.gradeTotal += grade;
			user.gradeMax += maxGrade;

			double percent = user.gradeTotal / user.gradeMax;



			DateFormat df = new SimpleDateFormat("MM/dd/yy");



				Date date = df.parse(record.get("created"));


		//	if(date.before(user.firstPost)) user.firstPost = date;
				HashMap<Date,Double> map = user.gradeAbsolute;
				HashMap<Date,Double> map2 = user.gradePercent;

			map2.put(date, percent);


			if(map.containsKey(date)){
					map.put(date, map.get(date) + grade);
				}else {

				map.put(date, grade.doubleValue());
			}





		}

	}

}
