package ca.ubc.arts.isit.NetworkFramework;

import com.google.common.hash.HashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mario on 2/28/16.
 */
public class User {
	public final int authorId;
	public final String userName;
	public final Date firstPost;
	public static ArrayList<Comment> comments;
	public static HashMap<Date, Double> gradePercent;
	public static HashMap<Date, Double> gradeAbsolute;
	public static double gradeTotal;
	public static double gradeMax;

	//Todo: Add a representation of dynamic attributes

	public enum userType { INSTRUCTOR, TA, STUDENT}


	public User(int authorId, String userName, Date firstPost){
		this.authorId = authorId;
		this.userName = userName;
		this.firstPost = firstPost;
		gradePercent = new HashMap<Date, Double>();
		gradeAbsolute = new HashMap<Date,Double>();
		gradeTotal = 0;
		gradeMax = 0;

	}

	public String getUserName(){
		return this.userName;
	}




	public int getAuthorId(){
		return this.authorId;
	}

	public double averageGrade(){

 	return gradeTotal / gradeMax;

	}

	public double averageWordCount(){
	//Todo: implement average word Count
	return 0;
	}

}
