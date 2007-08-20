package profilechecker;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import profilechecker.controller.ProfileCheckerController;
import profilechecker.model.Model;
import profilechecker.view.XMIParserGUI;
import profilechecker.view.XMIParserUI;

/**
 * ProfileChecker main class.
 * 
 * @author Matheus
 */
public class Main {

	/** XMIParserGUI option. */
	private static final String PARSER_GUI_OPTION = "parsergui";

	/** XMIParserUI option. */
	private static final String PARSER_UI_OPTION = "parserui";

	/** Help option. */
	private static final String HELP_OPTION = "help";

	/**
	 * Method to be executed when invoking the ProfileChecker as an application.
	 * 
	 * @param args
	 *            Arguments of the application.
	 */
	public static void main(String[] args) {
		Option parserGUIOption = new Option(PARSER_GUI_OPTION,
				"Starts XMIParserGUI");
		Option parserUIOption = new Option(PARSER_UI_OPTION, true,
				"Starts XMIParserUI");
		Option help = new Option(HELP_OPTION, "This help message");

		Options options = new Options();

		options.addOption(parserGUIOption);
		options.addOption(parserUIOption);
		options.addOption(help);

		CommandLineParser parser = new GnuParser();
		
		ProfileCheckerController controller;
		
		try {
			controller = new ProfileCheckerController();
		} catch (ParserConfigurationException e1) {
			System.err.println("Failed to create parser.");
			e1.printStackTrace();
			return;
		} catch (SAXException e1) {
			System.err.println("Failed to create parser.");
			e1.printStackTrace();
			return;
		}
		
		Model model = new Model();
		
		try {
			CommandLine line = parser.parse(options, args);
			if (line.hasOption(PARSER_UI_OPTION)) {
				XMIParserUI xmiParserUI = new XMIParserUI(controller, model);
				File file = null;
				try {
					file = new File(line.getOptionValue(PARSER_UI_OPTION));
					System.out.println(xmiParserUI.parse(file));
				} catch (SAXException e) {
					System.err.println("Failed to parse file: " + file);
					e.printStackTrace();
				} catch (IOException e) {
					System.err.println("Failed to parse file: " + file);
					e.printStackTrace();
				}
			} else if (line.hasOption(PARSER_GUI_OPTION)) {
				XMIParserGUI xmiParserGUI = new XMIParserGUI(controller, model);
				xmiParserGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				xmiParserGUI.setVisible(true);
			} else {
				// automatically generate the help statement
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("java -jar profilechecker.jar", options);
			}
		} catch (ParseException exp) {
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
		}

	}

}