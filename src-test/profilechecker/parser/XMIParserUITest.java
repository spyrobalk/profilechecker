package profilechecker.parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class XMIParserUITest extends TestCase {

	public void testParseFile() throws ParserConfigurationException, SAXException, IOException {
		XMIParserUI xmiParserUI = new XMIParserUI();
		String expectedOutput = "Profile" + XMIParserUI.LINE_SEPARATOR + "   name       : public" + XMIParserUI.LINE_SEPARATOR + "   id         : _12_5_1_12e803d1_1186331641532_79382_142" + XMIParserUI.LINE_SEPARATOR + "   visibility : publicV" + XMIParserUI.LINE_SEPARATOR + XMIParserUI.LINE_SEPARATOR + "   Stereotype" + XMIParserUI.LINE_SEPARATOR + "      name       : SimpleStereotype" + XMIParserUI.LINE_SEPARATOR + "      id         : _12_5_1_12e803d1_1186332374248_452459_298" + XMIParserUI.LINE_SEPARATOR + "      visibility : publicV" + XMIParserUI.LINE_SEPARATOR + "      type       : UML Standard Profile::UML2.0 Metamodel::Classes::Kernel::Class" + XMIParserUI.LINE_SEPARATOR + "      type       : UML Standard Profile::UML2.0 Metamodel::Classes::Kernel::Association" + XMIParserUI.LINE_SEPARATOR + XMIParserUI.LINE_SEPARATOR + XMIParserUI.LINE_SEPARATOR + XMIParserUI.LINE_SEPARATOR;
		assertEquals(expectedOutput, xmiParserUI.parse(new File("resources-test/profile_multi_stereotype_definition.xmi")));
	}

}
