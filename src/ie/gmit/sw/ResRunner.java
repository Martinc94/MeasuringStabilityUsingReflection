package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;

public class ResRunner {

	public static void main(String[] args) {
		SavedResults sr = new SavedResults();
		//sr.getResults();
		
		Map<String,	Metric> temp = new HashMap<String, Metric>();
		
		Metric m = new Metric();	
		temp.put("ie.gmit.sw", m);
		
		sr.addGraph(temp);
		
		sr.getResults();
		
	}

}
