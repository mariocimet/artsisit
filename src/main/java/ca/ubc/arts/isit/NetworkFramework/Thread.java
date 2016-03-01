package ca.ubc.arts.isit.NetworkFramework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 2/28/16.
 */
public class Thread {
	public static ArrayList<User> users;
	public final int threadId;

	public Thread(int id, User op){
		threadId = id;
		users.add(op);
	}
}
