package ca.ubc.arts.isit.NetworkFramework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 2/28/16.
 */
public class Thread {
	public final String threadId;
	public static ArrayList<Comment> replies;

	public Thread(String id, Comment op){
		threadId = id;
		replies = new ArrayList<Comment>();
		replies.add(op);
	}
}
