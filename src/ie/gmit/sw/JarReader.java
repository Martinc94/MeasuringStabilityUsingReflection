package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * JarReader is for reading all the classes in a given Jar File and inserting them into a Map.
 * @author  Martin Coleman
 */

public class JarReader {
	private JarInputStream in=null;
	private JarEntry next;
	private Map<String,Metric> graph = new HashMap<String,Metric>();
	
	public JarReader(String jarName){
		readJar(jarName);
	}//end JarReader Constructor
	
	/**
	 * Reads all the classes of a givenJar and adds to Map
	 */
	public void readJar(String jarName){
		try {
			in = new JarInputStream(new FileInputStream(new File(jarName)));
			next = in.getNextJarEntry();
			
			while (next != null) {
				if (next.getName().endsWith(".class")) {
					String name = next.getName().replaceAll("/", "\\.");
					name = name.replaceAll(".class", "");
					
					if (!name.contains("$")) name.substring(0, name.length() - ".class".length());{
						//System.out.println(name);
						//Add to class to map with new Metric
						graph.put(name, new Metric());
					}//end If
					
				}//END IF
				next = in.getNextJarEntry();
			}//end While
			
		} catch (IOException e) {
			System.out.println("Cannot Read Jar");
			e.printStackTrace();
		}//end Catch
		
	}//end ReadJar

	/**
	 * Returns map of classes and their Metrics
	 */
	public Map<String, Metric> getMap() {
		return this.graph;
	}//end getMap
	
}//end JarReader
