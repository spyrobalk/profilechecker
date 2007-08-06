package profilechecker;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;
import profilechecker.parser.XMIParserFacade;
import util.ParsingException;
import util.VariablesImpl;
import easyaccept.EasyAccept;
import easyaccept.QuitSignalException;

public class AcceptanceTest extends TestCase {

	public void testParser() throws FileNotFoundException, QuitSignalException,
			IOException, ParsingException {
		boolean failed = false;
		EasyAccept tester = new EasyAccept();
		if (!tester.runAcceptanceTest(new XMIParserFacade(),
				"resources-test/acceptance/parser.txt", new VariablesImpl())) {
			failed = true;
		}
		assertFalse(failed);
	}

}