/*
 * Author: Marion Veloria
 * Started: 4/9/18
 * Vision: Create a java GUI that mimics a basketball box score
 */

import javax.swing.SwingUtilities; 

public class RunProject implements Runnable {
	/*
	 * Swing is not thread safe due to the fact that there could be 
	 * multiple components and events associated with it.  To combat
	 * this problem we instantiate the class that contains all of these
	 * swing components in the main method and schedule it to invoke 
	 * the actual startup method run() in the event dispatching thread.
	 */
	public void run () {
		Project p = new Project();
	}
	
	public static void main (String[] args) {
		SwingUtilities.invokeLater(new RunProject());
	}
}