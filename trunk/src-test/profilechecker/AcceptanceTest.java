package profilechecker;

import junit.framework.TestCase;
import profilechecker.controller.XMIParserFacade;
import util.VariablesImpl;
import easyaccept.EasyAccept;

/**
 * Run the acceptance tests made by the client. Each method should be associated
 * with a EasyAccept script that represents an US.
 * 
 * @author Matheus
 */
public class AcceptanceTest extends TestCase {

	/**
	 * Tests the XMIParser.
	 * 
	 * @throws Exception
	 *             If something fails.
	 */
	public void testParser() throws Exception {
		boolean failed = false;
		EasyAccept tester = new EasyAccept();
		if (!tester.runAcceptanceTest(new XMIParserFacade(),
				"resources-test/acceptance/parser.txt", new VariablesImpl())) {
			failed = true;
		}
		assertFalse(failed);
	}

	/**
	 * Tests the XMIParser with stereotype applications.
	 * 
	 * @throws Exception
	 *             If something fails.
	 */
	public void testParser2() throws Exception {
		boolean failed = false;
		EasyAccept tester = new EasyAccept();
		if (!tester.runAcceptanceTest(new XMIParserFacade(),
				"resources-test/acceptance/parser2.txt", new VariablesImpl())) {
			failed = true;
		}
		assertFalse(failed);
	}
	
}