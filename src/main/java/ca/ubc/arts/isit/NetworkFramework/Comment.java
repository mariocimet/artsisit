package ca.ubc.arts.isit.NetworkFramework;

/**
 * Created by mario on 3/5/16.
 */
public class Comment {
	public final User author;
	public final String body;
	public final String thread;
	public final long date;
	public final boolean threadStarter;


	//Todo: add sentiment analysis of comment body?

	public Comment(User author, String body, String thread, long date, boolean threadStarter) {
		this.author = author;
		this.body = body;
		this.thread = thread;
		this.date = date;
		this.threadStarter = threadStarter;
	}





}
