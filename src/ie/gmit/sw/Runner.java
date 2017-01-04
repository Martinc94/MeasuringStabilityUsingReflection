package ie.gmit.sw;

/**
 * Runner is the class used for running the application
 * @author  Martin Coleman
 */

public class Runner {
	//private static String jarLocation="C:\\Users\\MartinColeman\\Desktop\\Jars\\CustomerJar.jar";
	//private static String jarLocation="C:\\Users\\MartinColeman\\Desktop\\Jars\\junit.jar";
	private static String jarLocation="CustomerJar.jar";

	public static void main(String[] args) {
		//Jar reader parses Jar to map
		JarReader jr = new JarReader(jarLocation);
		
		//Processer process Metric for each Class in a given map
		Processor p = new Processor(jr.getMap(),jarLocation);
	}//end main

}//end Runner
