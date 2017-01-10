package ie.gmit.sw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.query.*;
import com.db4o.ta.*;
import xtea_db4o.XTEA;
import xtea_db4o.XTeaEncryptionStorage;


public class SavedResults {
	private ObjectContainer db = null;
	//private List<Map> resultList = new ArrayList<Map>();
	private List<Map> listOfMaps = new ArrayList<Map>();
	
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
		
		/*//test Data
		Metric m = new Metric();
		
		Map<String,	Metric> map = new HashMap<String, Metric>();
		
		map.put("ie.gmit.sw", m);
		
		addGraph(map);*/
		
		

		//load stored jars list from DB
		load();	

	}//end SavedResults

	private void load(){
		//try to load from Database
		try {
			//List<Map> res = (List<Map>)db.query(Map.class);
			List<Map> maps = new ArrayList<Map>(db.query(Map.class));
			
			this.listOfMaps=maps;
		} catch (Exception e) {
			//handle error here
			System.out.println("Error Load");
			e.printStackTrace();
		}
	}//end load
	
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
	
	public void deleteGraph(){
		//index on List?
	}//end deleteGraph
	
	private void saveToDB(){	
		try {
			db.store(this.listOfMaps);
		} catch (Exception e) {
			//db.rollback(); //Rolls back the transaction
			System.out.println("Error Save");
			e.printStackTrace();
		}
	}//end saveToDB
	
	public List getResults(){
		System.out.println(listOfMaps.toString());
		return listOfMaps;
	}//end getResults
	
}
