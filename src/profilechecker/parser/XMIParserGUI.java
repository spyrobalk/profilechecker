package profilechecker.parser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import profilechecker.Profile;
import profilechecker.Stereotype;

/**
 * GUI to use the XMIParser. This is only a prototype and is needed to test and
 * verify any XMI parser using a graphical interface.
 * 
 * @author Matheus
 */
/**
 * @author matheusgr
 * 
 */
public class XMIParserGUI extends JFrame {

	/** Default serial version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Listener to be invoked when the open file button receives an action.
	 * 
	 * It will open a JFileChooser dialog and then invoke the XMIParser.
	 * 
	 * @author Matheus
	 */
	/**
	 * @author matheusgr
	 * 
	 */
	static final class ActionListenerImplementation implements ActionListener {

		/** JEditorPane to receive the text. */
		private JEditorPane resultPane;

		/** JFrame parent of the JFileChooser. */
		private JFrame parent;

		/**
		 * ActionListener implementation.
		 * 
		 * @param resultPane
		 *            Pane to receive the parser result.
		 * @param parent
		 *            Parent of the JFileChooser.
		 */
		public ActionListenerImplementation(JEditorPane resultPane,
				JFrame parent) {
			this.parent = parent;
			this.resultPane = resultPane;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(parent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				parseFile(file);
			} else {
				// TODO CANCELLED
			}
		}

		/**
		 * Parse the XMI file and print the result at the JEditorPane.
		 * 
		 * @param file
		 *            File to be parsed.
		 */
		void parseFile(File file) {
			try {
				XMIParser parser = new XMIParser(file);
				Map<String, Profile> profiles = parser.parse();
				StringBuilder sb = new StringBuilder();
				for (String profileName : profiles.keySet()) {
					Profile profile = profiles.get(profileName);
					sb.append("<html><body>");
					sb.append("Profile<ul>");
					sb.append("<li><b>name</b> " + profile.getName());
					sb.append("<li><b>id</b> " + profile.getId());
					sb.append("<li><b>visibility</b> "
							+ profile.getVisibility());
					sb.append("<p>");
					Map<String, Stereotype> stereotypes = profiles.get(
							profileName).getStereotypes();
					for (String stereotypeName : stereotypes.keySet()) {
						Stereotype stereotype = stereotypes.get(stereotypeName);
						sb.append("<li>Stereotype<ul>");
						sb.append("<li><b>name</b> " + stereotype.getName());
						sb.append("<li><b>id</b> " + stereotype.getId());
						sb.append("<li><b>visibility</b> "
								+ stereotype.getVisibility());
						for (String type : stereotype.getTypes()) {
							sb.append("<li><b>type</b> " + type);
						}
						sb.append("</ul>");
					} // End of 'stereotypes for-each'
					sb.append("</ul>");
					sb.append("<hr />");
				} // End of 'profiles for-each'
				sb.append("</body></html>");
				resultPane.setText(sb.toString());
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/** Main content pane. */
	private JPanel mainContentPane = null;

	/** Main pane of the GUI. */
	private JPanel mainPanel = null;

	/** Button that open the JFileChooser dialog. */
	private JButton openFileButton = null;

	/** JScrollPane to hold JEditorPane. */
	private JScrollPane editorScrollPane = null;

	/** JEditorPane to show the parse result. */
	private JEditorPane editorPane = null;

	/**
	 * Initialize the main JFrame of the XMIParserGUI.
	 */
	public XMIParserGUI() {
		super();
		initialize();
	}

	/**
	 * Initialize this JFrame.
	 */
	private void initialize() {
		setSize(new Dimension(467, 261));
		setContentPane(getMainContentPane());
	}

	/**
	 * This method initializes mainContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainContentPane() {
		if (mainContentPane == null) {
			mainContentPane = new JPanel();
			mainContentPane.setLayout(new BorderLayout());
			mainContentPane.add(getMainPanel(), BorderLayout.CENTER);
		}
		return mainContentPane;
	}

	/**
	 * This method initializes mainPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mainPanel.setLayout(null);
			mainPanel.add(getOpenFileButton(), null);
			mainPanel.add(getEditorScrollPane(), null);
		}
		return mainPanel;
	}

	/**
	 * This method initializes openFileButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getOpenFileButton() {
		if (openFileButton == null) {
			openFileButton = new JButton();
			openFileButton.setBounds(new Rectangle(17, 10, 428, 21));
			openFileButton.setText("Open File...");
			openFileButton.addActionListener(new ActionListenerImplementation(
					getEditorPane(), this));
		}
		return openFileButton;
	}

	/**
	 * This method initializes editorScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getEditorScrollPane() {
		if (editorScrollPane == null) {
			editorScrollPane = new JScrollPane();
			editorScrollPane.setBounds(new Rectangle(15, 42, 429, 180));
			editorScrollPane.setViewportView(getEditorPane());
		}
		return editorScrollPane;
	}

	/**
	 * This method initializes editorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getEditorPane() {
		if (editorPane == null) {
			editorPane = new JEditorPane();
			editorPane.setContentType("text/html");
			editorPane.setAutoscrolls(true);
			editorPane.setEditable(false);
		}
		return editorPane;
	}

}
