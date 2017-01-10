package ie.gmit.sw;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.*;
import com.db4o.ext.DatabaseFileLockedException;

/**
 * ResultsWindow Manages saved results from Db4o
 */
public class ResultsWindow extends JDialog{
	private static final long serialVersionUID = 777L;	
	private ResultsTableModel tm = null;
	private JTable table = null;
	private JScrollPane tableScroller = null;
	private JButton btnClose = null;
	private JPanel tablePanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private Container c;
	private List<Map> listOfMaps = new ArrayList<Map>();
	private JFrame frame;
	
	/**
	 * Constructor For Class
	 */
	public ResultsWindow(JFrame parent, boolean modal,List<Map> maps){
        super(parent, modal);
        super.setTitle("Summary");
        super.setResizable(true);
        
        this.setSize(new Dimension(500, 400));
        
		c = getContentPane();
		c.setLayout(new FlowLayout());	
		
		listOfMaps=maps;

		createTable(maps);
        configureButtonPanel();
        
        c.add(tablePanel);
        c.add(buttonPanel);
	}
	
	/**
	 * Create Table from given list of Maps
	 * 
	 * @param maps
	 * List of Maps containing Sting key and Metric value
	 */
	private void createTable(List<Map> maps){
		//this.currentGraph=graph;
		tm = new ResultsTableModel();
		tm.setData(maps);
		table = new JTable(tm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++){
			column = table.getColumnModel().getColumn(i);
			if (i == 0){
				column.setPreferredWidth(300);
				column.setMaxWidth(500);
				column.setMinWidth(200);
			}else{
				column.setPreferredWidth(100);
				column.setMaxWidth(70);
				column.setMinWidth(50);
			}
		}

		tableScroller = new JScrollPane(table);
		tableScroller.setPreferredSize(new java.awt.Dimension(485, 235));
		tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		tablePanel.add(tableScroller, FlowLayout.LEFT);
	}//end createTable
	
	/**
	 * Configures ButtonPanel
	 */
	private void configureButtonPanel(){
    	buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		//Configure the Cancel button
		btnClose = new JButton("Close");		
		btnClose.setToolTipText("Close this Window");
		btnClose.setPreferredSize(new java.awt.Dimension(100, 40));
		btnClose.setMaximumSize(new java.awt.Dimension(100, 40));
		btnClose.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnClose.setMinimumSize(new java.awt.Dimension(100, 40));
		btnClose.setIcon(new ImageIcon("images/close.gif"));
		btnClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});
		
		buttonPanel.add(btnClose);
		
		//handles clicks for Jtable
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	//pass to appSummary to view 
		            launchSummary(row);
		        }
		    }
		});
	}//end configureButtonPanel
	
	/**
	 * launches new AppSummary to view stability of map at selected index
	 */
	private void launchSummary(int index){
		try {	
				AppSummary as =  new AppSummary(frame, true, listOfMaps.get(index),false);
            	as.show();	
			
		}catch (DatabaseFileLockedException e) {
			//JOptionPane.showMessageDialog(null, "Error loading database is locked(Try again)");
			e.printStackTrace();
			AppSummary as =  new AppSummary(frame, true, listOfMaps.get(index),false);
        	as.show();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error loading results");
			e.printStackTrace();
		}
		
	}//end launchSummary

}//end ResultsWindow
