package ie.gmit.sw;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Processor process the indegree and outDegree each class in a given Map
 * @author  Martin Coleman
 */
public class Processor {
	private Map<String,Metric> graph;
	private Map<String,Metric> processedGraph;
	private String jarLocation;

	public Processor(Map<String, Metric> graph,String jarLoc) {
		this.graph = graph;
		this.jarLocation = jarLoc;
		iterateOver(this.graph);
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
			
			//printClass(key.toString());
			processGraph(key.toString());  
		}//end while	
		
	}//end iterateOver
	
	/**
	 * processGraph Loads classes from Jar and passes requested class to be Processed
	 */
	private void processGraph(String clsName) {
		try {
			//Fixes error where class cannot load
			//Problem: http://stackoverflow.com/questions/194698/how-to-load-a-jar-file-at-runtime
			File file  = new File(jarLocation);
			
			URL url = file.toURI().toURL();  
			URL[] urls = new URL[]{url};
			
			//Loads classes From jar
			ClassLoader cl = new URLClassLoader(urls);
			//Loads class with class loader
			Class cls = cl.loadClass(clsName);
			
			//pass class to be Processed
			processClass(cls);
			//print metric to screen
			printMetric(cls);
				
		} catch (ClassNotFoundException e) {
			System.out.println("[ERROR]: Cannot find class");
			//e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("[ERROR]: Malformed Url Error");
			//e.printStackTrace();
		}catch (NoClassDefFoundError e) {
			System.out.println("[ERROR]: Class Definition Error");
			//e.printStackTrace();
		}
		
	}//end ProcessGraph
	
	/**
	 * processClass processes the indegree and outDegree of a given class 
	 */
	private void processClass(Class cls) {
		//Package pack = cls.getPackage(); //Get the package
		//boolean iface = cls.isInterface();//Is it an interface?
		
		Class[] interfaces = cls.getInterfaces();//gets list of interfaces class implements
		//loop over each interface
		for (int i = 0; i < interfaces.length ; i++) {		
			if(graph.containsKey(interfaces[i].getName())) {
	            //indegree++ for Interface
				incInDegree(interfaces[i].getName());
				
	            //outdegree++ for Class
	            incOutDegree(cls.getName());
			}//end if
		}//end for

		Constructor[] cons = cls.getConstructors();
		for (int i = 0; i < cons.length; i++) {
		     Constructor ct = cons[i];
		     
		     Class paramTypes[] = ct.getParameterTypes();
		     for (int j = 0; j < paramTypes.length; j++){
		    	 if(graph.containsKey(paramTypes[j].getName())) {
		    		 //indegree++ for param type
					 incInDegree(paramTypes[j].getName());
			        
		             //outdegree++ for Class
		             incOutDegree(cls.getName());
				 }//end if
		     }//end for 
		}//end for
	
		//A field is a class, interface, or enum with an associated value. 
		Field[] fieldlist = cls.getFields(); //Get the fields / attributes
		for (int i = 0; i < fieldlist.length; i++) {
		    Field fld = fieldlist[i];
	    	if(graph.containsKey(fld.getName())) {
	    		//indegree++ for Field
				incInDegree(fld.getName());
		        
	            //outdegree++ for Class
	            incOutDegree(cls.getName());
			}//end if	
		}//endFor
		
		Method[] methlist = cls.getMethods(); //Get the set of methods
		//for each method in list of methods
		for (int i = 0; i < methlist.length;i++) {
			Method method = methlist[i];
  		      	
	      	Class paramTypes[] = method.getParameterTypes();
	      	//loop all param types
	      	for (int j = 0; j < paramTypes.length; j++){
	      		if(graph.containsKey(paramTypes[j].getName())) {
		    		//indegree++ for param type
					incInDegree(paramTypes[j].getName());
			        
		            //outdegree++ for Class
		            incOutDegree(cls.getName());
				}//end if

	    	}//end for
	      	
	      	//if returnType is in graph
	      	if(graph.containsKey(method.getReturnType().getName())) {
	    		//indegree++ for param type
				incInDegree(method.getReturnType().getName());
		        
	            //outdegree++ for Class
	            incOutDegree(cls.getName());
			}//end if
		 }//end for
	}//end ProcessClass
	
	/**
	 * Print In/OutDegree for given class
	 */
	private void printMetric(Class cls) {
		System.out.println("Name: "+cls.getName());
		System.out.println("---------------------------------------------------------------");
		Metric m=graph.get(cls.getName());
		System.out.println("OutDegree: "+m.getOutDegree());
		System.out.println("InDegree: "+m.getInDegree());
		System.out.println("Stability: "+m.getStability());
		System.out.println("---------------------------------------------------------------");
	}//end printInOutDegree
	
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
	
	/**
	 * printClass prints class to Console 
	 */
	private void printClass(String clsName) {
		
		List<Class> listOfCls = new ArrayList();
		
		try {
			
			File file  = new File(jarLocation);
			
			URL url = file.toURL();  
			URL[] urls = new URL[]{url};
			
			ClassLoader cl = new URLClassLoader(urls);
			Class cls = cl.loadClass(clsName);
			
			System.out.println("Name: "+cls.getName());
			System.out.println("---------------------------------------------------------------");
			
			Package pack = cls.getPackage(); //Get the package
			
			boolean iface = cls.isInterface();//Is it an interface?
			
			if(iface){
				Class[] interfaces = cls.getInterfaces();
			}
			
			Constructor[] cons = cls.getConstructors();
			
			for (int i = 0; i < cons.length; i++) {
		         Constructor ct = cons[i];
		         System.out.println("\tname  = " + ct.getName());
		         System.out.println("\tdecl class = " + ct.getDeclaringClass());

		         Class pvec[] = ct.getParameterTypes();
		         for (int j = 0; j < pvec.length; j++){
		            System.out.println("\tparam #" + j + " " + pvec[j]);
		         }

		         Class evec[] = ct.getExceptionTypes();
		         for (int j = 0; j < evec.length; j++){
		            System.out.println("\texc #" + j + " " + evec[j]);
		         }
		         System.out.println("\t-----");
		      }
			
			//A field is a class, interface, or enum with an associated value. 
			Field[] fieldlist = cls.getFields(); //Get the fields / attributes
			
			for (int i = 0; i < fieldlist.length; i++) {
		         Field fld = fieldlist[i];
		         System.out.println("\tname = " + fld.getName());
		         System.out.println("\tdecl class = " + fld.getDeclaringClass());
		         System.out.println("\ttype = " + fld.getType());
		         int mod = fld.getModifiers();
		         System.out.println("\tmodifiers = " + Modifier.toString(mod));
		         System.out.println("-----");
		    }
			
			
			Method[] methlist = cls.getMethods(); //Get the set of methods
			
			//for each method in list of methods
			for (int i = 0; i < methlist.length;i++) {
			      	Method m = methlist[i];
			      	
			      	System.out.println("\tname = " + m.getName());
			      	System.out.println("\tdecl class = " + m.getDeclaringClass());
			      	
			      	Class pvec[] = m.getParameterTypes();
			      //loop all param types
			      	for (int j = 0; j < pvec.length; j++){
			         		System.out.println("\tParamater Type #" + j + " " + pvec[j]);
			    	}
			      	
			      	//loop all exceptions
			      	Class evec[] = m.getExceptionTypes();
			      	for (int j = 0; j < evec.length; j++){
			         		System.out.println("\tException #" + j + " " + evec[j]);
			      	}
			      	
			      	System.out.println("\treturn type = " + m.getReturnType());
			      	System.out.println("\t-----");
			 }
			
			System.out.println("---------------------------------------------------------------");	
			
		} catch (ClassNotFoundException e) {
			//System.out.println("Cannot find class");
			//e.printStackTrace();
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		}	
	}//end printClass

}//end Processor
