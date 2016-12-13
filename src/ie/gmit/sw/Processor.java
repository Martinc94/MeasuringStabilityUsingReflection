package ie.gmit.sw;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Processor process the indegree and outDegree each class in a given Map
 * @author  Martin Coleman
 */
public class Processor {
	private Map<String,Metric> graph;
	private Map<String,Metric> processedGraph;

	public Processor(Map<String, Metric> graph) {
		this.graph = graph;
		iterateOver(graph);
	}//end constructor

	/**
	 * Iterate over map and process each class
	 */
	private void iterateOver(Map graph) {
		//Iterate over Map
		Iterator<?> classIterator = graph.entrySet().iterator();
		Entry<String, Metric> thisEntry;
		
		while (classIterator.hasNext()) {
			thisEntry = (Entry<String, Metric>) classIterator.next();
			Object key = thisEntry.getKey();
			//Object value = thisEntry.getValue();		  
			//System.out.println(key.toString());
			
			process(key.toString());
			  
		}//end while	
		
	}//end iterateOver
	
	/**
	 * Processor process the indegree and outDegree each class in a given Map
	 */
	private void process(String clsName) {
		try {
			Class cls = Class.forName(clsName);
			
			Package pack = cls.getPackage(); //Get the package
			
			boolean iface = cls.isInterface();//Is it an interface?
			
			if(iface){
				Class[] interfaces = cls.getInterfaces();
			}
			
			Constructor[] cons = cls.getConstructors();
			
			for(int i =0; i < cons.length;i++){
				Class[] params = cons[i].getParameterTypes();
				//System.out.println(params);
			}
			
			Field[] fields = cls.getFields(); //Get the fields / attributes
			
			Method[] methods = cls.getMethods(); //Get the set of methods
			
			for(int i =0; i < methods.length;i++){
				Class c = methods[i].getReturnType(); //Get a method return type
				//System.out.println("[Method return type] "+c);
				//incOutDegree(clsName);
			}	
			
			for(int i =0; i < methods.length;i++){
				Class[] params = methods[i].getParameterTypes(); //Get method parameters
				//System.out.println("[Method param types] "+ params);
			}	
			
		} catch (ClassNotFoundException e) {
			//System.out.println("Cannot find class");
			//e.printStackTrace();
		}		
		
		/*
		//test outDegree 
		Metric m=graph.get(clsName);
		System.out.println(m.getOutDegree());*/
		
	}//end Process
	
	/**
	 * Increase OutDegree for given class
	 */
	private void incOutDegree(String clsName){
		//find metric on map with key and increment outDegree
		Metric m;
		m=graph.get(clsName);
		
		//increase outDegree
		m.incrementOutDegree();
		
		//add back to graph
		graph.put(clsName, m);
	}//end incOutDegree
	
	/**
	 * Increase InDegree for given class
	 */
	private void incInDegree(String clsName){
		//find metric on map with key and increment inDegree
		Metric m;
		m=graph.get(clsName);
		
		//increase inDegree
		m.incrementInDegree();
		
		//add back to graph
		graph.put(clsName, m);
	}//end incInDegree

	/**
	 * Returns the processedGraph
	 */
	public Map<String, Metric> getProcessedGraph() {
		return this.processedGraph;
	}//end getProcessedGraph

}//end Processor
