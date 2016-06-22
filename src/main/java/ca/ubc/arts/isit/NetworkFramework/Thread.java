package ca.ubc.arts.isit.NetworkFramework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 2/28/16.
 */
public class Thread {
	public final String threadId;
	public static ArrayList<Comment> replies;
	public final User op;

	public Thread(String id, Comment op){
		threadId = id;
		replies = new ArrayList<Comment>();
		replies.add(op);
		this.op = op.author;
	}

	public User getOp(){
		return op;
	}
}
