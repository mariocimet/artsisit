package ca.ubc.arts.isit.NetworkFramework;

import com.google.common.hash.HashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by mario on 2/28/16.
 */
public class User {
	public final int authorId;
	public final String userName;
	public final String hash;
	public static ArrayList<Comment> comments;
	public static Map<Date, Float> gradeAttribute;

	//Todo: Add a representation of dynamic attributes

	public enum userType { INSTRUCTOR, TA, STUDENT}


	public User(int authorId, String userName, String hash){
		this.authorId = authorId;
		this.userName = userName;
		this.hash = hash;

	}

	public String getUserName(){
		return this.userName;
	}



	public int getAuthorId(){
		return this.authorId;
	}

	public double averageGrade(){

	//Todo: implement average grade calculation
	return 0;

	}

	public double averageWordCount(){
	//Todo: implement average word Count
	return 0;
	}

}
