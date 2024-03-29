package profilechecker.model;

import junit.framework.TestCase;

/**
 * Test Package model.
 *
 * @author matheusgr
 */
public class PackageTest extends TestCase {

	private Package package1;

	private Package package2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// <ownedMember xmi:type='uml:Package'
		// xmi:id='_12_5_1_12e803d1_1185146560514_240773_520' name='TestePacote'
		// visibility='public'>

		package1 = new Package("TestePacote",
				"_12_5_1_12e803d1_1185146560514_240773_520", VisibilityType
						.toValue("public"), 1);
		// <ownedMember xmi:type='uml:Package'
		// xmi:id='_9_0_62a020a_1105705064323_957155_11049' name='Kernel'
		// visibility='public'>

		package2 = new Package("Kernel",
				"_9_0_62a020a_1105705064323_957155_11049", VisibilityType
						.toValue("public"), 2);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test getName at Package.
	 */
	public void testGetName() {
		assertEquals("TestePacote", package1.getName());
		assertEquals("Kernel", package2.getName());
	}

	/**
	 * Test getId at Package.
	 */
	public void testGetId() {
		assertEquals("_12_5_1_12e803d1_1185146560514_240773_520", package1
				.getId());
		assertEquals("_9_0_62a020a_1105705064323_957155_11049", package2
				.getId());
	}

	/**
	 * Test getVisibility at Package.
	 */
	public void testGetVisibility() {
		assertEquals(VisibilityType.toValue("public"), package1.getVisibility());
		assertEquals(VisibilityType.toValue("public"), package2.getVisibility());
	}
}
