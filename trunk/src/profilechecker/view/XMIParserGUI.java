package profilechecker.view;


import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.xml.sax.SAXException;

import profilechecker.controller.ProfileCheckerController;
import profilechecker.model.Model;
import profilechecker.model.Profile;
import profilechecker.model.Stereotype;
import profilechecker.model.ValidationException;

/**
 * @author Clerton Ribeiro de Araujo Filho
 *
 */
public class XMIParserGUI extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 4921629556785764895L;

    /**
     *
     */
    
    ProfileCheckerController controller;
    
    /**
     * 
     */
    Model model;
    
    /**
     * 
     */
    JDesktopPane theDesktop;
    
    /**
     * @param controller
     * @param model
     */
    public XMIParserGUI(ProfileCheckerController controller, Model model) {
       
        super("Profile Checker");
       
        this.controller = controller;
        this.model = model;
       
        //initialize the default GUI Look and Feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final UnsupportedLookAndFeelException e) {
            e.printStackTrace ();
        }
       
        theDesktop = new JDesktopPane();
        getContentPane().add(theDesktop);
       
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
       
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        menubar.add(fileMenu);
       
        JMenuItem openItem = new JMenuItem("Open File...");
        openItem.setMnemonic('O');
        OpenListener openListener = new OpenListener();
        openItem.addActionListener(openListener);
        fileMenu.add(openItem);
       
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('E');
        ExitListener exitListener = new ExitListener();
        exitItem.addActionListener(exitListener);
        fileMenu.add(exitItem);
        
        JMenu runMenu = new JMenu("Run");
        runMenu.setMnemonic('R');
        menubar.add(runMenu);
        
        JMenuItem profilecheck = new JMenuItem("Profile Check");
        profilecheck.setMnemonic('P');
        runMenu.add(profilecheck);
        ProfileCheckListener profileCheckListener = new ProfileCheckListener();
        profilecheck.addActionListener(profileCheckListener);
       
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        menubar.add(helpMenu);
       
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setMnemonic('A');
        helpMenu.add (aboutItem);
        AboutListener aboutListener = new AboutListener();
        aboutItem.addActionListener(aboutListener);        
       
        //get default screen size to make the application window
        setSize( Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
       
    }
    
    private class OpenListener implements ActionListener {

        /**
         *
         */
        private static final long serialVersionUID = 4742678140174816897L;

        public void actionPerformed(ActionEvent e) {
            
        	JFileChooser fileChooser =  new JFileChooser();
           
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML and XMI files","xmi","xml");
            fileChooser.setFileFilter(filter);
           
            int result = fileChooser.showOpenDialog(getContentPane());
           
            if(result==JFileChooser.CANCEL_OPTION) {
                return;
            }
           
            File fileName = fileChooser.getSelectedFile();
           
           
            JInternalFrame frame = new JInternalFrame(fileName.getName(),true,true,true,true);
            Container container = frame.getContentPane();
            JEditorPane panel = new JEditorPane();
            StringBuilder sb = new StringBuilder();
            try {
                controller.parser(model, fileName);
                Map<String, Profile> profiles = model.getProfiles();
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
                    //sb.append("<hr />");
                } // End of 'profiles for-each'
                sb.append("</body></html>");
            } catch (SAXException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            panel.setEditable(false);
            panel.setContentType("text/html");
            panel.setAutoscrolls(true);
            panel.setText (sb.toString());
            container.add(panel);
            frame.pack();
            theDesktop.add(frame);
            frame.setVisible(true);
            try {
                frame.setMaximum(true);
            } catch (PropertyVetoException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    private class ExitListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    private class AboutListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String lineSeparator = System.getProperty("line.separator");
            StringBuilder sb = new StringBuilder();
            sb.append("ProfileChecker"+lineSeparator);
            sb.append("Visit: www.lcc.ufcg.edu.br/moises/les"+lineSeparator);
            sb.append(lineSeparator);
            sb.append("Developers:" + lineSeparator);
            sb.append("Clerton Ribeiro - clerton@dsc.ufcg.edu.br" + lineSeparator);
            sb.append("Matheus Gaudencio - matheusgr@lcc.ufcg.edu.br" + lineSeparator);
            sb.append ("Moises Rodrigues - moises@lcc.ufcg.edu.br" + lineSeparator);
            sb.append(lineSeparator);
            sb.append("Client:"+lineSeparator);
            sb.append("Franklin de Souza Ramalho");
           
            JOptionPane.showMessageDialog(null, sb.toString(),"About ProfileChecker",JOptionPane.PLAIN_MESSAGE);
        }
    }
    private class ProfileCheckListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser =  new JFileChooser();  
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML and XMI files","xmi","xml");
            fileChooser.setFileFilter(filter);
           
            int result = fileChooser.showOpenDialog(getContentPane());
           
            if(result==JFileChooser.CANCEL_OPTION) {
                return;
            }
           
            File fileName = fileChooser.getSelectedFile();
            JInternalFrame frame = new JInternalFrame(fileName.getName(),true,true,true,true);
            Container container = frame.getContentPane();
            JEditorPane panel = new JEditorPane();
            StringBuilder sb = new StringBuilder();
            try {
				controller.parser(model, fileName);
				Map<String, Profile> profiles = model.getProfiles();
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
                    //sb.append("<hr />");
                } // End of 'profiles for-each'
                sb.append("</body></html>");
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 panel.setEditable(false);
	            panel.setContentType("text/html");
	            panel.setAutoscrolls(true);
	            panel.setText (sb.toString());
	            container.add(panel);
	            frame.pack();
	            theDesktop.add(frame);
	            frame.setVisible(true);
	            try {
	                frame.setMaximum(true);
	            } catch (PropertyVetoException e1) {
	                e1.printStackTrace();
	            }
			controller.validate(model);
			StringBuilder message = new StringBuilder();
			message.append("File: "+fileName.getName()+System.getProperty("line.separator"));
			message.append(System.getProperty("line.separator"));
			Set<ValidationException> validationExceptions = model.getValidationExceptions();
			if(validationExceptions.size() == 0) {
				message.append("OK");
			} else {
				for(ValidationException ve : validationExceptions) {
					message.append(ve.getMessage() + System.getProperty("line.separator"));
				}
			}
			JOptionPane.showMessageDialog(null, message.toString(), "ProfileChecker Result",JOptionPane.PLAIN_MESSAGE);
		}
    	
    }
}