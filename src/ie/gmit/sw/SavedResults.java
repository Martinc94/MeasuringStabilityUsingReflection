package ie.gmit.sw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ta.*;
import xtea_db4o.XTEA;
import xtea_db4o.XTeaEncryptionStorage;

/**
 *SavedResults Manages DB4o. It Loads and Saves Lists of Maps
 */
public class SavedResults {
	private ObjectContainer db = null;
	private List<Map> listOfMaps = new ArrayList<Map>();
	
	/**
	 *Constructor for class
	 */
	public SavedResults(){
		//db40 Configuration
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().add(new TransparentActivationSupport()); //Real lazy. Saves all the config commented out below
		config.common().add(new TransparentPersistenceSupport()); //Lazier still. Saves all the config commented out below
		config.common().updateDepth(7); //Propagate updates
		
		//Use the XTea lib for encryption.
		config.file().storage(new XTeaEncryptionStorage("password", XTEA.ITERATIONS64));
	
		//Open a local database. Use Db4o.openServer(config, server, port) for full client / server
		db = Db4oEmbedded.openFile(config, "stability.data");

		//load stored jars list from DB
		load();	

	}//end SavedResults

	/**
	 * Loads a list of graphs from Db4o
	 */
	private void load(){
		//try to load from Database
		try {
			List<Map> maps = new ArrayList<Map>(db.query(Map.class));
			
			this.listOfMaps=maps;
		} catch (Exception e) {
			System.out.println("Error Loading from DB");
			e.printStackTrace();
		}
	}//end load
	
	/**
	 * Adds a new graph to list 
	 * 
	 * @param graph
	 * Map of String,Metric
	 */
	public void addGraph(Map<String,Metric>graph){
		//try add to list 
		try {
			this.listOfMaps.add(graph);
			//save to db
			saveToDB();
		} catch (Exception e) {
			System.out.println("Error add");
			e.printStackTrace();
		}
	}//end addGraph
	
	/**
	 * Delete a graph from list 
	 */
	public void deleteGraph(int index){
		if(index<listOfMaps.size()){
			 listOfMaps.remove(index);
		}
	}//end deleteGraph
	
	/**
	 * Save List of maps to Db4o
	 */
	private void saveToDB(){	
		try {
			db.store(this.listOfMaps);
		}catch (Exception e) {
			//db.rollback(); //Rolls back the transaction
			System.out.println("Error Saving");
			e.printStackTrace();
		}
	}//end saveToDB
	
	/**
	 * Returns a list of maps
	 * 
	 * @return
	 * List of maps
	 */
	public List getResults(){
		return listOfMaps;
	}//end getResults
	
	/**
	 * Returns a Map at a given index from list of maps
	 * 
	 * @return
	 * Returns a map
	 */
	public Map getResultsAtIndex(int index){
		
		try {
			if(index<listOfMaps.size()){
				return listOfMaps.get(index);
			}
			else{
				return new HashMap<String,Metric>();
			}
		} catch (Exception e) {
			return new HashMap<String,Metric>();
		}
	}//end getResultsAtIndex
}//end SavedResults
