package ie.gmit.sw;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Map;

/**
 * AppWindow manages Swing GUI
 */
public class AppWindow {
	private JFrame frame;
	private String JarLocation;
	private Map<String,Metric>graph;
	private JButton btnDialog;
	
	/**
	 * Constructor for class
	 */
	public AppWindow(){
		//Create a window for the application
		frame = new JFrame();
		frame.setTitle("B.Sc. in Software Development - GMIT");
		frame.setSize(550, 500);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		
        //The file panel will contain the file chooser
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
        top.setBorder(new javax.swing.border.TitledBorder("Select Jar"));
        top.setPreferredSize(new java.awt.Dimension(500, 100));
        top.setMaximumSize(new java.awt.Dimension(500, 100));
        top.setMinimumSize(new java.awt.Dimension(500, 100));
        
        final JTextField txtFileName =  new JTextField(20);
		txtFileName.setPreferredSize(new java.awt.Dimension(100, 30));
		txtFileName.setMaximumSize(new java.awt.Dimension(100, 30));
		txtFileName.setMargin(new java.awt.Insets(2, 2, 2, 2));
		txtFileName.setMinimumSize(new java.awt.Dimension(100, 30));
		txtFileName.setEditable(false);
		
		JButton btnChooseFile = new JButton("Browse...");
		btnChooseFile.setToolTipText("Select Jar");
        btnChooseFile.setPreferredSize(new java.awt.Dimension(90, 30));
        btnChooseFile.setMaximumSize(new java.awt.Dimension(90, 30));
        btnChooseFile.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnChooseFile.setMinimumSize(new java.awt.Dimension(90, 30));
		btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
        		JFileChooser fc = new JFileChooser("./");
        		int returnVal = fc.showOpenDialog(frame);
            	if (returnVal == JFileChooser.APPROVE_OPTION) {
                	File file = fc.getSelectedFile().getAbsoluteFile();
                	String name = file.getAbsolutePath(); 
                	txtFileName.setText(name);
                	System.out.println("You selected the following file: " + name);
                	
                	JarLocation = name;
                	
            	}
			}
        });
		
		JButton btnStability = new JButton("Calculate Stability");
		btnStability.setToolTipText("Do Something");
		btnStability.setPreferredSize(new java.awt.Dimension(150, 30));
		btnStability.setMaximumSize(new java.awt.Dimension(150, 30));
		btnStability.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnStability.setMinimumSize(new java.awt.Dimension(150, 30));
		btnStability.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("Attempting to read Jar at Location: "+JarLocation);
            	
            	try {
            		if(JarLocation!=null){
	            		JarReader jr = new JarReader(JarLocation);
	            		//Processer process Metric for each Class in a given map
	            		Processor p = new Processor(jr.getMap(),JarLocation);
	            		//set Graph
	            		graph=p.getGraph();
	            		
	            		btnDialog.setVisible(true);
            		}//end if
            		else{
            			//alert user
            			JOptionPane.showMessageDialog(null, "Please select a valid Jar!");
            		}//end else
            		
					
				} catch (Exception e) {
					//Error
				}
  
			}//end actionPerformed
        });
		
        top.add(txtFileName);
        top.add(btnChooseFile);
        top.add(btnStability);
        frame.getContentPane().add(top); //Add the panel to the window
        
        
        //A separate panel for the programme output
        JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEADING));
        mid.setBorder(new BevelBorder(BevelBorder.RAISED));
        mid.setPreferredSize(new java.awt.Dimension(500, 300));
        mid.setMaximumSize(new java.awt.Dimension(500, 300));
        mid.setMinimumSize(new java.awt.Dimension(500, 300));
        
        /*CustomControl cc = new CustomControl(new java.awt.Dimension(500, 300));
        cc.setBackground(Color.WHITE);
        cc.setPreferredSize(new java.awt.Dimension(300, 300));
        cc.setMaximumSize(new java.awt.Dimension(300, 300));
        cc.setMinimumSize(new java.awt.Dimension(300, 300));
        mid.add(cc);
		frame.getContentPane().add(mid);*/
        
        
        
		
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setPreferredSize(new java.awt.Dimension(500, 50));
        bottom.setMaximumSize(new java.awt.Dimension(500, 50));
        bottom.setMinimumSize(new java.awt.Dimension(500, 50));
        
        btnDialog = new JButton("Show Stability Table"); //Create Quit button
        btnDialog.setVisible(false);
        btnDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//check if graph is available
            	try {
					if(graph!=null){
						AppSummary as =  new AppSummary(frame, true, graph);
		            	as.show();
					}
					else{
						JOptionPane.showMessageDialog(null, "Error please select Jar or try a different Jar");
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error");
				}
            	
			}
        });
        
        JButton btnQuit = new JButton("Quit"); //Create Quit button
        btnQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.exit(0);
			}
        });
        bottom.add(btnDialog);
        bottom.add(btnQuit);

        frame.getContentPane().add(bottom);       
		frame.setVisible(true);
	}
	
	/**
	 * Main Launches AppWindow creating GUI
	 */
	public static void main(String[] args) {
		new AppWindow();
	}
}