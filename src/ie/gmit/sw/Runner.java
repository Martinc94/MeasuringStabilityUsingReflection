package ie.gmit.sw;

/**
 * Runner is the class used for running the application
 * @author  Martin Coleman
 */

public class Runner {

	public static void main(String[] args) {
		//Jar reader parses Jar
		JarReader jr = new JarReader("CustomerJar.jar");
		
		//Processer process Metric for each Class
		Processor p = new Processor(jr.getMap());
	}//end main

}//end Runner
