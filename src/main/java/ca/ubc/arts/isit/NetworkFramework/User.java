package ca.ubc.arts.isit.NetworkFramework;

/**
 * Created by mario on 2/28/16.
 */
public class User {
	public final int authorId;
	public final String userName;

	//Todo: Add a representation of dynamic attributes

	public enum userType { INSTRUCTOR, TA, STUDENT}


	public User(int authorId, String userName){
		this.authorId = authorId;
		this.userName = userName;

	}


}
