package ie.gmit.sw;

/**
 * Runner Launches the GUI Window
 */
public class Runner {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AppWindow();
			}
		});
	}
}
