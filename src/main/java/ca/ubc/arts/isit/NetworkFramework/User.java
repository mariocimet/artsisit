package ca.ubc.arts.isit.NetworkFramework;

import com.google.common.hash.HashCode;

import java.util.ArrayList;

/**
 * Created by mario on 2/28/16.
 */
public class User {
	public final int authorId;
	public final String userName;
	public final String hash;
	public static ArrayList<Comment> comments;

	//Todo: Add a representation of dynamic attributes

	public enum userType { INSTRUCTOR, TA, STUDENT}


	public User(int authorId, String userName, String hash){
		this.authorId = authorId;
		this.userName = userName;
		this.hash = hash;

	}


}
