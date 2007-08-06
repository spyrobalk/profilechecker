package profilechecker;

import javax.swing.JFrame;

import profilechecker.parser.XMIParserGUI;

/**
 * ProfileChecker main class.
 * 
 * @author Matheus
 */
public class Main {

	/**
	 * Method to be executed when invoking the ProfileChecker as an application.
	 * 
	 * @param args
	 *            Arguments of the application.
	 */
	public static void main(String[] args) {
		XMIParserGUI xmiParserGUI = new XMIParserGUI();
	    xmiParserGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    xmiParserGUI.setVisible(true);
	}

}
