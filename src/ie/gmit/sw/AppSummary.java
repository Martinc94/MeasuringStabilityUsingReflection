package ie.gmit.sw;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.table.*;

/**
 * AppSummary manages Jtable and other GUI dependancies for displaying results
 */
public class AppSummary extends JDialog{
	private static final long serialVersionUID = 777L;	
	private TypeSummaryTableModel tm = null;
	private JTable table = null;
	private JScrollPane tableScroller = null;
	private JButton btnClose = null;
	private JButton btnSave = null;
	private JPanel tablePanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private Container c;
	private SavedResults results = new SavedResults();
	private Map<String, Metric> currentGraph;
	
	/**
	 * Constructor For Class
	 */
	
	public AppSummary(JFrame parent, boolean modal,Map<String,Metric>graph, boolean enableSave){
        super(parent, modal);
        super.setTitle("Summary");
        super.setResizable(true);
        
        this.setSize(new Dimension(500, 400));
        
		c = getContentPane();
		c.setLayout(new FlowLayout());	

		createTable(graph);
        configureButtonPanel();
        
        c.add(tablePanel);
        c.add(buttonPanel);

        //allows User to save //used for viewing results
        btnSave.setEnabled(enableSave);
	}
	
	/**
	 * Create Table from given Map
	 * 
	 * @param graph
	 * Map Of String keys paired to Metric Values
	 */
	private void createTable(Map<String,Metric>graph){
		this.currentGraph=graph;
		tm = new TypeSummaryTableModel();
		tm.setData(graph);
		table = new JTable(tm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionBackground(Color.YELLOW);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++){
			column = table.getColumnModel().getColumn(i);
			if (i == 0){
				column.setPreferredWidth(60);
				column.setMaxWidth(300);
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
	}
	
	/**
	 * Configures ButtonPanel
	 */
	private void configureButtonPanel(){
    	buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    	
    	btnSave = new JButton("Save");		
    	btnSave.setToolTipText("Save results");
    	btnSave.setPreferredSize(new java.awt.Dimension(100, 40));
    	btnSave.setMaximumSize(new java.awt.Dimension(100, 40));
    	btnSave.setMargin(new java.awt.Insets(2, 2, 2, 2));
    	btnSave.setMinimumSize(new java.awt.Dimension(100, 40));
    	btnSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					saveResults();
					btnSave.setVisible(false);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error saving results");
				}
				
			}
		});

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

		buttonPanel.add(btnSave);
		buttonPanel.add(btnClose);
	}//end configureButtonPanel
	
	/**
	 * Passes graph to SavedResults to be saved by Db4o
	 */
	private void saveResults(){
		results.addGraph(currentGraph);
	}//saveResults

}//end AppSummary
