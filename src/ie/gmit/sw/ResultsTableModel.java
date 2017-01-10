package ie.gmit.sw;

import java.util.List;
import java.util.Map;
import javax.swing.table.*;

/**
 * ResultsTableModel formats how data is displayed in Jtable 
 */
public class ResultsTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 777L;
	private String[] cols = {"Name"};
	private Object[][] data = {
		{"Name"}
	};

	/**
	 * Returns the length of columns
	 * 
	 * @return
	 * Returns the columns length as an int
	 */
	public int getColumnCount() {
        return cols.length;
    }//end getColumnCount
	
	/**
	 * Returns the length of rows
	 * 
	 * @return
	 * Returns the rows length as an int
	 */
    public int getRowCount() {
        return data.length;
	}//end getRowCount

    /**
	 * Returns the name of Column
	 * 
	 * @return
	 * Returns the name of Column as an String
	 */
    public String getColumnName(int col) {
    	return cols[col];
    }//end getColumnName

    /**
   	 * Returns the Object at given Column
   	 * 
   	 * @return
   	 * Returns Object
   	 */
    public Object getValueAt(int row, int col) {
        return data[row][col];
	}//end getValueAt
   
    /**
   	 * Returns the Class at given Column
   	 * 
   	 * @return
   	 * Returns a Class
   	 */
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
	}//end getColumnClass
    
    /**
   	 * Sets the data for Table from given Maps
   	 * 
   	 * @param maps
   	 * List of Maps
   	 */
    public void setData(List<Map> maps){
		Object[][] mapData = new Object[maps.size()][1];
		
		//maps.get(1);
		int i=0;
		for (Map map : maps) {
			mapData[i][0] = map.toString();
			i++;
		}//end for
		
		data=mapData;

	}//end setData
}