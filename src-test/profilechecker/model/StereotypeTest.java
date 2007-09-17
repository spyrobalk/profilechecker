package profilechecker.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

/**
 * Test Stereotype model.
 *
 * @author matheusgr
 */
public class StereotypeTest extends TestCase {

	private Stereotype stereotype1;

	private Stereotype stereotype2;

	private Set<String> types1;

	private Set<String> types2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// <ownedMember xmi:type='uml:Stereotype'
		// xmi:id='_12_5_1_12e803d1_1186332374248_452459_298'
		// name='SimpleStereotype'
		// visibility='public'>
		// <ownedAttribute xmi:type='uml:Property'
		// xmi:id='_12_5_1_12e803d1_1186332378092_478735_301' name='base_Class'
		// visibility='private'
		// association='_12_5_1_12e803d1_1186332378092_114764_300'>
		// <type xmi:type='uml:Class'
		// href='http://schema.omg.org/spec/UML/2.0/uml.xml#Class'>
		// <xmi:Extension extender='MagicDraw UML 12.5' extenderID='MagicDraw
		// UML 12.5'>
		// <referenceExtension referentPath='UML Standard Profile::UML2.0
		// Metamodel::Classes::Kernel::Class' referentType='Class'/>
		// </xmi:Extension>

		stereotype1 = new Stereotype("SimpleStereotype1",
				"_12_5_1_12e803d1_1186332378092_478735_301", VisibilityType
						.toValue("public"), 1);
		List<String> stTypes = new ArrayList<String>();
		types1 = new HashSet<String>();
		stTypes.add("Classes");
		stTypes.add("Kernel");
		stTypes.add("Class");
		types1.addAll(stTypes);

		stereotype1.setTypes(types1);

		// <ownedMember xmi:type='uml:Stereotype'
		// xmi:id='_12_5_1_12e803d1_1186332374248_452459_298'
		// name='SimpleStereotype' visibility='public'>
		// <ownedAttribute xmi:type='uml:Property'
		// xmi:id='_12_5_1_12e803d1_1186332378092_478735_301' name='base_Class'
		// visibility='private'
		// association='_12_5_1_12e803d1_1186332378092_114764_300'>
		// <type xmi:type='uml:Class'
		// href='http://schema.omg.org/spec/UML/2.0/uml.xml#Class'>
		// <xmi:Extension extender='MagicDraw UML 12.5' extenderID='MagicDraw
		// UML 12.5'>
		// <referenceExtension referentPath='UML Standard Profile::UML2.0
		// Metamodel::Classes::Class' referentType='Class'/>
		// </xmi:Extension>
		// </type>
		// </ownedAttribute>
		// </ownedMember>

		stereotype2 = new Stereotype("SimpleStereotype2",
				"_12_5_1_12e803d1_1186332374248_452459_298", VisibilityType
						.toValue("public"), 2);
		stTypes = new ArrayList<String>();
		types2 = new HashSet<String>();
		stTypes.add("Classes");
		stTypes.add("Class");
		types2.addAll(stTypes);

		stereotype2.setTypes(types2);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test getName at Stereotype.
	 */
	public void testGetName() {
		assertEquals("SimpleStereotype1", stereotype1.getName());
		assertEquals("SimpleStereotype2", stereotype2.getName());
	}

	/**
	 * Test getId at Stereotype.
	 */
	public void testGetId() {
		assertEquals("_12_5_1_12e803d1_1186332378092_478735_301", stereotype1
				.getId());
		assertEquals("_12_5_1_12e803d1_1186332374248_452459_298", stereotype2
				.getId());
	}

	/**
	 * Test getVisibility at Stereotype.
	 */
	public void testGetVisibility() {
		assertEquals(VisibilityType.toValue("public"), stereotype1
				.getVisibility());
		assertEquals(VisibilityType.toValue("public"), stereotype2
				.getVisibility());
	}

	/**
	 * Test getTypes at Stereotype.
	 */
	public void testGetTypes() {
		assertEquals(types1, stereotype1.getTypes());
		assertEquals(types2, stereotype2.getTypes());
	}

}
