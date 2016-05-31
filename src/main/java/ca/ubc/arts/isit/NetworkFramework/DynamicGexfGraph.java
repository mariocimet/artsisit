package ca.ubc.arts.isit.NetworkFramework;

import it.uniroma1.dis.wsngroup.gexf4j.core.*;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.*;
import it.uniroma1.dis.wsngroup.gexf4j.core.dynamic.Spell;
import it.uniroma1.dis.wsngroup.gexf4j.core.dynamic.TimeFormat;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.SpellImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeValueImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.*;

/**
@author MarioCimet

 This class performs the network discovery and produces the output of this
 program, a .gexf file representing a longitudinal network.

 */



public class DynamicGexfGraph {

	private static HashMap<String, Edge> edges;

public static void main(String[] args) throws ParseException {
	//Todo: Clean up/refactor this way of accessing the parsed data
	CSVDataParser data = new CSVDataParser();
	Date endDate =  new Date(1423580832420L);

	try {
		//Use main() to be able to access threads, users, etc; is this super ugly?
		data.main();
	} catch (IOException e) {
		e.printStackTrace();
	}



	//Todo: clean up this map/arrayList nonsense - is there a reason to use a map in the parsing? Why did i do this?
	Map<Integer, User> usersM = data.users;
	Map<String, Thread> threadsM =data.threads;
	edges = new HashMap<String, Edge>();
	ArrayList<User> users = new ArrayList<User>(usersM.values());
	ArrayList<Thread> threads = new ArrayList<Thread>(threadsM.values());

	//Make Course Title change according to file being parsed
	String coursetitle = "ChinaMOOC20143T";
	Gexf gexf = new GexfImpl();
	Calendar date = Calendar.getInstance();


	gexf.getMetadata()
			.setLastModified(date.getTime())
			.setCreator("ArtsISIT")
			.setDescription("A Dynamic Network of Students in " + coursetitle);

	Graph graph = gexf.getGraph();
	graph.
			setDefaultEdgeType(EdgeType.UNDIRECTED)
			.setMode(Mode.DYNAMIC)
			.setTimeType(TimeFormat.XSDDATETIME);


	//TODO: split up attributes into static (ie: user type, nationality, credit vs. honor code) and dynamic AttributeLists (ie: post length, grade)

	AttributeList attrList = new AttributeListImpl(AttributeClass.NODE).setMode(Mode.DYNAMIC);



	graph.getAttributeLists().add(attrList);

	Attribute name = attrList.createAttribute("0", AttributeType.STRING, "name");
	Attribute attIndegree = attrList.createAttribute("1", AttributeType.FLOAT, "indegree");
	Attribute grade = attrList.createAttribute("2", AttributeType.DOUBLE, "grade");
	Attribute studentID = attrList.createAttribute("3", AttributeType.INTEGER, "id");
	AttributeValue gradeValue;
//TODO: Allow access to parsed information - threads, grades, users

	 // Build the Nodes:

	 for(int i = 0; i < users.size(); i++){

	 User u = users.get(i);

	 Node nodeUser = graph.createNode(u.getUserName());
	 nodeUser
	 	.setLabel(Integer.toString(u.getAuthorId()))
	 	.getAttributeValues();

		 Spell start = new SpellImpl();
		 start.setStartValue(u.firstPost);
		 start.setEndValue(endDate);
		 nodeUser.getSpells().add(start);

		 Map map = u.gradePercent;

		 for(Date d:u.gradePercent.keySet()){

			  long e = d.getTime() + 100000;
			 Date end = new Date(e);

			 gradeValue = new AttributeValueImpl(grade);
			 gradeValue.setValue((map.get(d)).toString());
			 gradeValue.setStartValue(d);
			 gradeValue.setEndValue(end);
			 nodeUser.getAttributeValues().add(gradeValue);
		 }



	 }

	 //Build the Edges

	 //Simple Chain Network Algorithm

//For each thread
	for(int n = 0; n < threads.size(); n++){

		 Thread thread = threads.get(n);
		 ArrayList<Comment> replies = thread.replies;

//For each comment in the thread
	   for(int i = 1; i < replies.size(); i++) {


		   User sourceUser  = replies.get(i).author;
		   User targetUser = replies.get(i-1).author;

		   Node target = graph.getNode(targetUser.getUserName());
		   Node source = graph.getNode(sourceUser.getUserName());


			   if (!edges.containsKey(source.getId() + "-" + target.getId())) {

				   Spell edgespell = new SpellImpl();
				   edgespell.setStartValue(replies.get(i).date);
				   edgespell.setEndValue(endDate);
				   Edge e =  source.connectTo(target).setWeight((float).5);
				   e.getSpells().add(edgespell);
				   edges.put(source.getId() + "-" + target.getId(), e);

			   }else{
				  float weight = edges.get(source.getId() + "-" + target.getId()).getWeight();
				   edges.get(source.getId() + "-" + target.getId()).setWeight(weight + ((float).5));
			   }
		   }
	   }





	//Outputting the .gexf File

	StaxGraphWriter graphWriter = new StaxGraphWriter();
	File f = new File(coursetitle + " - dynamicGrades2" + ".gexf");
	Writer out;
	try {
		out =  new FileWriter(f, false);
		graphWriter.writeToStream(gexf, out, "UTF-8");
		System.out.println(f.getAbsolutePath());
	} catch (IOException e) {
		e.printStackTrace();
	}
}



	//
	public static float calculateWeight(int i, int j){

		Double w = 2 * Math.pow(.5, Math.abs(i-j));

		return new Float(w);

	}
}