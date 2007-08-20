package profilechecker.view;

import java.io.File;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

import junit.framework.TestCase;
import profilechecker.controller.ProfileCheckerController;
import profilechecker.model.Model;
import profilechecker.view.XMIParserGUI.ActionListenerImplementation;

/**
 * Test to XMIParserGUI. This test is used to guarantee that the interface keeps
 * the same across different versions.
 * 
 * @Author Matheus
 */
public class XMIParserGUITest extends TestCase {

	/**
	 * Tests the open file action response.
	 * @throws Exception If something fails. 
	 */
	public void testAction() throws Exception {
		ProfileCheckerController controller = new ProfileCheckerController();
		Model model = new Model();
		JEditorPane jep = new JEditorPane();
		JFrame jframe = new JFrame();
		ActionListenerImplementation acl = new ActionListenerImplementation(
				jep, jframe, controller, model);
		acl.parseFile(new File(
				"resources-test/profile_multi_stereotype_definition.xmi"));
		String expectedResult = "<html><body>Profile<ul><li><b>name</b> public<li><b>id</b> _12_5_1_12e803d1_1186331641532_79382_142<li><b>visibility</b> publicV<p><li>Stereotype<ul><li><b>name</b> SimpleStereotype<li><b>id</b> _12_5_1_12e803d1_1186332374248_452459_298<li><b>visibility</b> publicV<li><b>type</b> UML Standard Profile::UML2.0 Metamodel::Classes::Kernel::Class<li><b>type</b> UML Standard Profile::UML2.0 Metamodel::Classes::Kernel::Association</ul></ul><hr /></body></html>";
		assertEquals(expectedResult, jep.getText());
	}

	/**
	 * Tests if its possible to create a GUI without errors.
	 * @throws Exception If something fails.
	 */
	public void testCreation() throws Exception {
		ProfileCheckerController controller = new ProfileCheckerController();
		Model model = new Model();
		new XMIParserGUI(controller, model);
	}

}
