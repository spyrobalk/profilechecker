package profilechecker.view;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicButtonUI;

import org.xml.sax.SAXException;

import profilechecker.controller.ProfileCheckerController;
import profilechecker.model.Model;
import profilechecker.model.Profile;
import profilechecker.model.Stereotype;
import profilechecker.model.StereotypeApplication;
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
    
    private ProfileCheckerController controller;
    
    /**
     * 
     */
    private Model model;
    
    /**
     * 
     */
    private final JTabbedPane tab = new JTabbedPane();
    
    private final String line = System.getProperty("line.separator");

    /**
     * 
     */
    Map<String,File> openedFiles;
    
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
       
        tab.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        add(tab);
        openedFiles = new HashMap<String, File>();
        initMenu();
       
        //get default screen size to make the application window
        setSize( Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
       
    }
    
    /**
     * 
     */
    public void initMenu() {
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
    }
    
    /**
     * @param model
     * @param controller
     * @param fileName
     * @return a string with a parsed file
     */
    public String parse(Model model,ProfileCheckerController controller,File fileName) {

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
            } // End of 'profiles for-each'
            
            sb.append("<hr />");
            
            Set<StereotypeApplication> applications = model.getApplications();
            
            for(StereotypeApplication stereotypeApplication : applications) {
            	sb.append("Application<ul>");
            	sb.append("<li><b>id</b> " + stereotypeApplication.getId());
                sb.append("<li><b>base</b> " + stereotypeApplication.getBase());
                sb.append("<li><b>baseId</b> " + stereotypeApplication.getBaseId());
                sb.append("<li><b>stereotype</b> " + stereotypeApplication.getStereotype());
                sb.append("<li><b>profile</b> " + stereotypeApplication.getProfile());
                sb.append("</ul>");
            }
            sb.append("</body></html>");
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }
    
    /**
     * @return a file
     */
    public File openFile() {
    	JFileChooser fileChooser =  new JFileChooser();
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML and XMI files","xmi","xml");
        fileChooser.setFileFilter(filter);
       
        int result = fileChooser.showOpenDialog(getContentPane());
       
        if(result==JFileChooser.CANCEL_OPTION) {
            return null;
        }
        
        return fileChooser.getSelectedFile();
    }
    
    /**
     * @return  a file
     * 
     */
    public boolean open() {
    	
       File fileName = openFile();
       
       if(fileName == null) {
    	   return false;
       }
       
       openedFiles.put(fileName.getName(), fileName);
       
       JEditorPane panel = new JEditorPane();
       
       panel.setEditable(false);
       panel.setContentType("text/html");
       panel.setAutoscrolls(true);
       panel.setText (parse(model,controller,fileName));
       
       //There are xmi with information that doesn't fit on the screen
       
       JScrollPane scrollPanel = new JScrollPane(panel);
       tab.add(scrollPanel);
       tab.setTitleAt(tab.getTabCount()-1, fileName.getName());
       ButtonTabComponent buttonTab = new ButtonTabComponent(tab);
       buttonTab.setName(fileName.getName());
       tab.setTabComponentAt(tab.getTabCount()-1,buttonTab);
       tab.setSelectedIndex(tab.getTabCount()-1);
       
       return true;
    }
    
    private class OpenListener implements ActionListener {

        /**
         *
         */
        private static final long serialVersionUID = 4742678140174816897L;

        public void actionPerformed(ActionEvent e) {
            
        	boolean opened = open();
            
            if(!opened) {
            	return;
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
            
        	StringBuilder sb = new StringBuilder();
            sb.append("ProfileChecker"+line);
            sb.append("Visit: www.lcc.ufcg.edu.br/moises/les"+line);
            sb.append(line);
            sb.append("Developers:" + line);
            sb.append("Clerton Ribeiro - clerton@dsc.ufcg.edu.br" + line);
            sb.append("Matheus Gaudencio - matheusgr@lcc.ufcg.edu.br" + line);
            sb.append ("Moises Rodrigues - moises@lcc.ufcg.edu.br" + line);
            sb.append(line);
            sb.append("Client:"+line);
            sb.append("Franklin de Souza Ramalho");
           
            JOptionPane.showMessageDialog(null, sb.toString(),"About ProfileChecker",JOptionPane.PLAIN_MESSAGE);
        }
    }
    private class ProfileCheckListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if(tab.getTabCount() == 0) {
				JOptionPane.showMessageDialog(null, "You must open a XMI file to run profile check","Warning",JOptionPane.WARNING_MESSAGE);
				return;
			}
		
			File selectedFile = openedFiles.get(tab.getTitleAt(tab.getSelectedIndex()));
			try {
				controller.parser(model,selectedFile);
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			controller.validate(model);
			
			StringBuilder message = new StringBuilder();
			message.append("File: " + selectedFile.getName()+line);
			message.append(line);
			Set<ValidationException> validationExceptions = model.getValidationExceptions();
			if(validationExceptions.size() == 0) {
				message.append("Profile Check is OK");
			} else {
				for(ValidationException ve : validationExceptions) {
					message.append(ve.getMessage() + line);
				}
			}
			JOptionPane.showMessageDialog(null, message.toString(), "ProfileChecker Result",JOptionPane.PLAIN_MESSAGE);
		}
    }
    
    /**
     * Component to be used as tabComponent;
     * Contains a JLabel to show the text and 
     * a JButton to close the tab it belongs to 
     */ 
    private class ButtonTabComponent extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final JTabbedPane pane;

        /**
         * @param pane
         */
        public ButtonTabComponent(final JTabbedPane pane) {
            //unset default FlowLayout' gaps
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            if (pane == null) {
                throw new NullPointerException("TabbedPane is null");
            }
            this.pane = pane;
            setOpaque(false);
            
            //make JLabel read titles from JTabbedPane
            JLabel label = new JLabel() {
                /**
				 * 
				 */
				private static final long serialVersionUID = -7085072637932096254L;

				public String getText() {
                    int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                    if (i != -1) {
                        return pane.getTitleAt(i);
                    }
                    return null;
                }
            };
            
            add(label);
            //add more space between the label and the button
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            //tab button
            JButton button = new TabButton();
            add(button);
            //add more space to the top of the component
            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        }

        private class TabButton extends JButton implements ActionListener {
            /**
			 * 
			 */
			private static final long serialVersionUID = -4295957985144684259L;

			/**
             * 
             */
            public TabButton() {
                int size = 17;
                setPreferredSize(new Dimension(size, size));
                setToolTipText("close this tab");
                //Make the button looks the same for all Laf's
                setUI(new BasicButtonUI());
                //Make it transparent
                setContentAreaFilled(false);
                //No need to be focusable
                setFocusable(false);
                setBorder(BorderFactory.createEtchedBorder());
                setBorderPainted(false);
                //Making nice rollover effect
                //we use the same listener for all buttons
                addMouseListener(buttonMouseListener);
                setRolloverEnabled(true);
                //Close the proper tab by clicking the button
                addActionListener(this);
            }

            public void actionPerformed(ActionEvent e) {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                openedFiles.remove(pane.getTitleAt(i));
                if (i != -1) {
                    pane.remove(i);
                }
            }

            //we don't want to update UI for this button
            //public void updateUI() {}

            //paint the cross
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                //shift the image for pressed buttons
                if (getModel().isPressed()) {
                    g2.translate(1, 1);
                }
                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.RED);
                if (getModel().isRollover()) {
                    g2.setColor(Color.MAGENTA);
                }
                int delta = 6;
                g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
                g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
                g2.dispose();
            }
        }

        private final MouseListener buttonMouseListener = new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(true);
                }
            }

            public void mouseExited(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(false);
                }
            }
        };
    }
  }