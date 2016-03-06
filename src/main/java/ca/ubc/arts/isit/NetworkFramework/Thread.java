package ca.ubc.arts.isit.NetworkFramework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 2/28/16.
 */
public class Thread {
	public final String threadId;
	public static ArrayList<Integer> userkeys;

	public Thread(String id, int op){
		threadId = id;
		userkeys.add(op);
	}
}
