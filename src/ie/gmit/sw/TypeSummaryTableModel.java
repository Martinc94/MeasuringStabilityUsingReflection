package ie.gmit.sw;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.*;
import ie.gmit.sw.metric.Metric;

/**
 * TypeSummaryTableModel formats how data is displayed in Jtable 
 */
public class TypeSummaryTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 777L;
	private String[] cols = {"Class Name", "Out Degree", "In Degree", "Stability"};
	private Object[][] data = {
		{"Stuff 1", "Other Stuff 1", "Even More Stuff 1", "Even More Stuff"},
		{"Stuff 2", "Other Stuff 2", "Even More Stuff 2", "Even More Stuff"},
		{"Stuff 3", "Other Stuff 3", "Even More Stuff 3", "Even More Stuff"},
		{"Stuff 4", "Other Stuff 4", "Even More Stuff 4", "Even More Stuff"},
		{"Stuff 5", "Other Stuff 5", "Even More Stuff 5", "Even More Stuff"},
		{"Stuff 6", "Other Stuff 6", "Even More Stuff 6", "Even More Stuff"},
		{"Stuff 7", "Other Stuff 7", "Even More Stuff 7", "Even More Stuff"}
	};

	public int getColumnCount() {
        return cols.length;
    }
	
    public int getRowCount() {
        return data.length;
	}

    public String getColumnName(int col) {
    	return cols[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
	}
   
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
	}
    
    public void setData(Map <String,Metric>graph){
		Object[][] mapData = new Object[graph.size()][4];
		Iterator<?> classIterator = graph.entrySet().iterator();
		Entry<String, Metric> thisEntry;
		
		//Iterate Over Map
		int i=0;
		while (classIterator.hasNext()) {
			thisEntry = (Entry<String, Metric>) classIterator.next();
			Metric m = thisEntry.getValue();
			
			//set Name
			mapData[i][0] = thisEntry.getKey();
			//set outDegree
			mapData[i][1] = m.getOutDegree();
			//set inDegree
			mapData[i][2] = m.getInDegree();
			//set Stability
			mapData[i][3] = m.getStability();	
			i++;
		}//end while	
		
		//add data to table
		data=mapData;
	} //end setData
}