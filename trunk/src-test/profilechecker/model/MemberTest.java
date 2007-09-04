package profilechecker.model;

import junit.framework.TestCase;

/**
 * Tests for the <code> Member </code> bean.
 * @author moises
 *
 */
public class MemberTest extends TestCase {

	private Member ownedMember1;

	private Member ownedMember2;

	/**
	 * Initializes two simple members.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		// <ownedMember xmi:type='uml:Class'
		// xmi:id='_12_5_1_12e803d1_1185146568325_708968_547' name='Classe1'
		// visibility='public'/>
		ownedMember1 = new Member("Classe1",
				"_12_5_1_12e803d1_1185146568325_708968_547", VisibilityType
						.toValue("public"),"uml:Class");
		// <ownedMember xmi:type='uml:Class'
		// xmi:id='_12_5_1_12e803d1_1185146761643_148816_618' name='Classe2'
		// visibility='public'/>
		ownedMember2 = new Member("Classe2",
				"_12_5_1_12e803d1_1185146761643_148816_618", VisibilityType
						.toValue("public"),"uml:Class");

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		ownedMember1 = null;
		ownedMember2 = null;
	}
	
	public void testGetName(){
		assertEquals("Classe1",ownedMember1.getName());
		assertEquals("Classe2",ownedMember2.getName());
	}
	
	public void testGetId(){
		assertEquals("_12_5_1_12e803d1_1185146568325_708968_547",ownedMember1.getId());
		assertEquals("_12_5_1_12e803d1_1185146761643_148816_618",ownedMember2.getId());
	}
	
	public void testGetVisibility(){
		assertEquals(VisibilityType
				.toValue("public"),ownedMember1.getVisibility());
		assertEquals(VisibilityType
				.toValue("public"),ownedMember2.getVisibility());
	}
	
	public void testGetType(){
		assertEquals("uml:Class",ownedMember1.getType());
		assertEquals("uml:Class",ownedMember2.getType());
	}

}
