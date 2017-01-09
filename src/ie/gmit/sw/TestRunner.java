package ie.gmit.sw;

/**
 * TestRunner is the class used for testing the JarReader and Processing Stability
 * @author  Martin Coleman
 */

public class TestRunner {
	//private static String jarLocation="C:\\Users\\User\\Desktop\\jar.jar";//Enter Url Here
	private static String jarLocation="CustomerJar.jar";

	public static void main(String[] args) {
		//Jar reader parses Jar to map
		JarReader jr = new JarReader(jarLocation);
		
		//Processer process Metric for each Class in a given map
		Processor p = new Processor(jr.getMap(),jarLocation);
	}//end main

}//end TestRunner
