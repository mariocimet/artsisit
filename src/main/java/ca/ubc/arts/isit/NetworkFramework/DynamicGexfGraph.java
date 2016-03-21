package ca.ubc.arts.isit.NetworkFramework;

import it.uniroma1.dis.wsngroup.gexf4j.core.Edge;
import it.uniroma1.dis.wsngroup.gexf4j.core.EdgeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.Graph;
import it.uniroma1.dis.wsngroup.gexf4j.core.Mode;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.Attribute;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.dynamic.TimeFormat;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.SpellImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;



public class DynamicGexfGraph {

	private ArrayList<Pair> edges;

public static void newMain(String[] args){



	//Todo: Clean up/refactor this way of accessing the parsed data
	//Get access to parsed data
	CSVDataParser data = new CSVDataParser();

	try {
		//Use main() to be able to access threads, users, etc;
		data.main();
	} catch (IOException e) {
		e.printStackTrace();
	}




	//Todo: clean up this map/Collection nonsense - is there a reason to use a map in the parsing?
	Map<Integer, User> usersM = data.users;
	Map<String, Thread> threadsM =data.threads;
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

	AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);



	graph.getAttributeLists().add(attrList);

	Attribute name = attrList.createAttribute("0", AttributeType.STRING, "name");
	Attribute attIndegree = attrList.createAttribute("1", AttributeType.FLOAT, "indegree");
	Attribute grade = attrList.createAttribute("2", AttributeType.FLOAT, "grade");
	Attribute studentID = attrList.createAttribute("3", AttributeType.INTEGER, "id");

//TODO: Allow access to parsed information - threads, grades, users

	 // Build the Nodes:

	 for(int i = 0; i < users.size(); i++){

	 User u = users.get(i);

	 Node nodeUser = graph.createNode(u.getUserName());
	 nodeUser
	 	.setLabel(Integer.toString(u.getAuthorId()))
	 	.getAttributeValues();
	// Todo: add dynamic grade attribute like Cartel example
	// Todo: find library to direcly alter the xml configuration file

	 }

	 //Build the Edges

	 //Simple Chain Network Algorithm

	  // Todo: add name network features?
	//Todo: finish chain network algorighm - then work on dynamic attributes


	for(int n = 0; n < threads.size(); n++){
		 Thread thread = threads.get(n);

		 ArrayList<Comment> replies = thread.replies;

	   for(int i = 1; i < replies.size(); i++){
	      User iUser = replies.get(i).author;

	     for(int j = 0; i<j; i++){

	       User jUser = replies.get(i).author;

			 Node a = graph.getNode(iUser.getUserName());
			 Node b = graph.getNode(jUser.getUserName());

			 if(!(a.hasEdgeTo(b.getId()) ||  b.hasEdgeTo(a.getId()))) {
				 a.connectTo((a.getId() +"-"+ b.getId()), b);


				 for(int m =0; m < a.getEdges().size(); m++){
					 Edge e = a.getEdges().get(m);
					 if (e.getId().equalsIgnoreCase((a.getId() +"-"+ b.getId()))){
						 e.setWeight(calculateWeight(i,j));
						 //Todo: how to add dynamic spell to this?
						 // could be e.getSpells().add(new Spell(Date)));
					 }
				 }



			 }else{
				 for(int k = 0; k< a.getEdges().size();k++ ){
					 Edge e = a.getEdges().get(k);
					 if(e.getId().equalsIgnoreCase(a.getId() +"-"+ b.getId()) || e.getId().equalsIgnoreCase(b.getId() +"-"+ a.getId())){
						float currentWeight = e.getWeight();
						 e.setWeight(currentWeight + calculateWeight(i,j));
					 }
				 }
			 }


	         }

	     }



	 }






	StaxGraphWriter graphWriter = new StaxGraphWriter();
	File f = new File("dynamic_graph_sample.gexf");
	Writer out;
	try {
		out =  new FileWriter(f, false);
		graphWriter.writeToStream(gexf, out, "UTF-8");
		System.out.println(f.getAbsolutePath());
	} catch (IOException e) {
		e.printStackTrace();
	}
}


	public static int calculateWeight(int i, int j){


		return 0;
	}
}


//Example for canibalizing code from

/**


	/*public static void main(String[] args) {
		Gexf gexf = new GexfImpl();
		Calendar date = Calendar.getInstance();
		
		gexf.getMetadata()
			.setLastModified(date.getTime())
			.setCreator("Gephi.org")
			.setDescription("A Web network");


		Graph graph = gexf.getGraph();
		graph.
			setDefaultEdgeType(EdgeType.UNDIRECTED)
			.setMode(Mode.DYNAMIC)
			.setTimeType(TimeFormat.XSDDATETIME);
		
		AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
		graph.getAttributeLists().add(attrList);

		Attribute attUrl = attrList.createAttribute("0", AttributeType.STRING, "url");
		Attribute attIndegree = attrList.createAttribute("1", AttributeType.FLOAT, "indegree");
		Attribute attFrog = attrList.createAttribute("2", AttributeType.BOOLEAN, "frog")
			.setDefaultValue("true");

*//**
		*//* Node Gephi *//*
		Node gephi = graph.createNode("0");
		gephi
			.setLabel("Gephi")
			.getAttributeValues()
				.addValue(attUrl, "http://gephi.org")
				.addValue(attIndegree, "1");
		
		Spell spellGephi = new SpellImpl();
		date.set(2012, 3, 28, 16, 10, 0);
		date.set(Calendar.MILLISECOND, 0);
		spellGephi.setStartValue(date.getTime());
		gephi.getSpells().add(spellGephi);
		
		
		*//* Node Webatlas *//*
		Node webatlas = graph.createNode("1");
		webatlas
			.setLabel("Webatlas")
			.getAttributeValues()
				.addValue(attUrl, "http://webatlas.fr")
				.addValue(attIndegree, "2");
		
		Spell spellWebatlas1 = new SpellImpl();
		date.set(Calendar.MINUTE, 15);
		spellWebatlas1.setStartValue(date.getTime());
		date.set(2012, 3, 28, 18, 57, 2);
		spellWebatlas1.setEndValue(date.getTime());
		webatlas.getSpells().add(spellWebatlas1);
		
		Spell spellWebatlas2 = new SpellImpl();
		date.set(2012, 3, 28, 20, 31, 10);
		spellWebatlas2.setStartValue(date.getTime()).setStartIntervalType(IntervalType.OPEN);
		date.set(Calendar.MINUTE, 45);
		date.set(Calendar.SECOND, 21);
		spellWebatlas2.setEndValue(date.getTime());
		webatlas.getSpells().add(spellWebatlas2);
		
		Spell spellWebatlas3 = new SpellImpl();
		date.set(2012, 3, 28, 21, 0, 0);
		spellWebatlas3.setStartValue(date.getTime());
		date.set(2012, 4, 11, 10, 49, 27);
		spellWebatlas3.setEndValue(date.getTime()).setEndIntervalType(IntervalType.OPEN);
		webatlas.getSpells().add(spellWebatlas3);
		
		
		*//* Node RTGI *//*
		Node rtgi = graph.createNode("2");
		rtgi
			.setLabel("RTGI")
			.getAttributeValues()
				.addValue(attUrl, "http://rtgi.fr")
				.addValue(attIndegree, "1");
		
		Spell spellRtgi = new SpellImpl();
		date.set(2012, 3, 27, 6, 0, 0);
		spellRtgi.setStartValue(date.getTime());
		date.set(2012, 4, 19);
		spellRtgi.setEndValue(date.getTime());
		rtgi.getSpells().add(spellRtgi);
		
		
		*//* Node BarabasiLab *//*
		Node blab = graph.createNode("3");
		blab
			.setLabel("BarabasiLab")
			.getAttributeValues()
				.addValue(attUrl, "http://barabasilab.com")
				.addValue(attIndegree, "3")
				.addValue(attFrog, "false");
		
		
		*//* Node foobar *//*
		Node foobar = graph.createNode("4");
		foobar
			.setLabel("FooBar")
			.getAttributeValues()
				.addValue(attUrl, "http://foo.bar")
				.addValue(attIndegree, "1")
				.addValue(attFrog, "false");
		
		
		*//* Edge 0 [gephi, webatlas] *//*
		Edge edge0 = gephi.connectTo("0", webatlas);
		
		Spell spellEdge0 = new SpellImpl();
		date.set(2012, 3, 28, 16, 15, 36);
		spellEdge0.setStartValue(date.getTime());
		date.set(2012, 3, 28, 17, 41, 5);
		spellEdge0.setEndValue(date.getTime());
		edge0.getSpells().add(spellEdge0);
		
		
		*//* Edge 1 [gephi, rtgi] *//*
		Edge edge1 = gephi.connectTo("1", rtgi);
		
		Spell spellEdge1 = new SpellImpl();
		date.set(2012, 3, 30, 11, 16, 6);
		spellEdge1.setStartValue(date.getTime());
		date.set(2012, 4, 3, 11, 52, 6);
		spellEdge1.setEndValue(date.getTime());
		edge1.getSpells().add(spellEdge1);
		
		
		*//* Edge 2 [rtgi, webatlas] *//*
		Edge edge2 = rtgi.connectTo("2", webatlas);
		Spell spellEdge2 = new SpellImpl();
		date.set(2012, 4, 1, 11, 0, 0);
		spellEdge2.setStartValue(date.getTime()).setStartIntervalType(IntervalType.OPEN);
		date.set(2012, 4, 5, 11, 9, 44);
		spellEdge2.setEndValue(date.getTime());
		edge2.getSpells().add(spellEdge2);
		
		
		*//* Edge 3 [gephi, blab] *//*
		Edge edge3 = gephi.connectTo("3", blab);
		Spell spellEdge3 = new SpellImpl();
		date.set(2012, 3, 30, 12, 13, 22);
		spellEdge3.setStartValue(date.getTime());
		date.set(Calendar.MINUTE, 58);
		date.set(Calendar.SECOND, 24);
		spellEdge3.setEndValue(date.getTime());
		edge3.getSpells().add(spellEdge3);
		
		
		*//* Edge 4 [webatlas, blab] *//*
		Edge edge4 = webatlas.connectTo("4", blab);
		Spell spellEdge4 = new SpellImpl();
		date.set(2012, 3, 30, 21, 2, 37);
		spellEdge4.setStartValue(date.getTime());
		date.set(Calendar.MINUTE, 13);
		spellEdge4.setEndValue(date.getTime());
		edge4.getSpells().add(spellEdge4);
		
		
		*//* Edge 5 [foobar, blab] *//*
		foobar.connectTo("5", blab);
		
		
		StaxGraphWriter graphWriter = new StaxGraphWriter();
		File f = new File(coursetitle + ".gexf");
		Writer out;
		try {
			out =  new FileWriter(f, false);
			graphWriter.writeToStream(gexf, out, "UTF-8");
			System.out.println(f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}*/

