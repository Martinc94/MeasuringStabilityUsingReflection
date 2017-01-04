package ie.gmit.sw;

/**
 * Metric class is for measuring the stability of a class from its inDegree and outDegree
 * @author  Martin Coleman
 */

public class Metric {
	private int inDegree;
	private int outDegree;
	
	public Metric(){
	}//default Constructor
	
	/**
	 * Calculates and returns Stability of a class
	 */
	public float getStability(){
		float stability;
		
		//Calculate Stability
		try {
            if(getOutDegree() != 0) {
            	//stability can be calculated
                stability = ((float) getOutDegree() / ((float) getInDegree() + (float) getOutDegree()));
            } else {
            	//not dependant
                stability = 0;
            }//end ifElse
        } catch (ArithmeticException e){
        	//Prevent NaN being Calculated return 0 instead
        	//http://stackoverflow.com/questions/10817706/exception-in-thread-main-java-lang-arithmeticexception-by-zero
        	System.out.println("[ERROR]: Error calculating Stability");
            stability = 0;
        }//end tryCatch
		
		return stability;
	}//end getStability

	/**
	 * Returns the inDegree of a class
	 */
	public int getInDegree() {
		return inDegree;
	}//end getInDegree

	/**
	 * Returns the outDegree of a class
	 */
	public int getOutDegree() {
		return outDegree;
	}//end getOutDegree

	/**
	 * Increase the inDegree of a class
	 */
	public void incrementInDegree() {
		this.inDegree++;
	}//end incrementInDegree

	/**
	 * Increase the outDegree of a class
	 */
	public void incrementOutDegree() {
		this.outDegree++;
	}//end incrementOutDegree
	
}//end Metric
