package profilechecker.parser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
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

public class XMIParserGUI extends JFrame {

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	private JEditorPane jEditorPane = null;
	public XMIParserGUI() {
		super();
		initialize();
	}
	
	private void initialize() {
		setSize(new Dimension(467, 261));
		setContentPane(getJContentPane());
	}

	private Component getJFrame() {
		return this;
	}
	
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJScrollPane(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setBounds(new Rectangle(0, 0, 0, 0));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(17, 10, 428, 21));
			jButton.setText("Open File...");
			jButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					int returnVal = fc.showOpenDialog(getJFrame());
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
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
								sb.append("<li><b>visibility</b> " + profile.getVisibility());
								sb.append("<p>");
								Map<String, Stereotype> stereotypes = profiles.get(profileName).getStereotypes();
								for (String stereotypeName : stereotypes.keySet()) {
									Stereotype stereotype = stereotypes.get(stereotypeName);
									sb.append("<li>Stereotype<ul>");
									sb.append("<li><b>name</b> " + stereotype.getName());
									sb.append("<li><b>id</b> " + stereotype.getId());
									sb.append("<li><b>visibility</b> " + stereotype.getVisibility());
									for (String type : stereotype.getTypes()) {
										sb.append("<li><b>type</b> " + type);
									}
									sb.append("</ul>");
								}
								sb.append("</ul>");
								sb.append("<hr />");
							}
							sb.append("</body></html>");
							getJEditorPane().setText(sb.toString());
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
			        } else {
			        	// TODO CANCELLED
			        }
				}
				
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(15, 42, 429, 180));
			jScrollPane.setViewportView(getJEditorPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setContentType("text/html");
			jEditorPane.setAutoscrolls(true);
			jEditorPane.setEditable(false);
		}
		return jEditorPane;
	}

}
