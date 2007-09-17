package profilechecker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

public class StereotypeAplicationTest extends TestCase {

	private StereotypeApplication stereotypeApplication1;

	private StereotypeApplication stereotypeApplication2;

	private Set<String> types2;

	private Stereotype stereotype2;

	private Stereotype stereotype1;

	private Set<String> types1;

	private Profile profile;

	private Map<String, Stereotype> stereotypes;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		stereotype1 = new Stereotype("SimpleStereotype1",
				"_12_5_1_12e803d1_1186332378092_478735_301", VisibilityType
						.toValue("public"), 1);
		List<String> stTypes = new ArrayList();
		types1 = new HashSet<String>();
		stTypes.add("Classes");
		stTypes.add("Kernel");
		stTypes.add("Class");
		types1.addAll(stTypes);

		stereotype1.setTypes(types1);

		stereotype2 = new Stereotype("SimpleStereotype2",
				"_12_5_1_12e803d1_1186332374248_452459_298", VisibilityType
						.toValue("public"), 2);
		stTypes = new ArrayList();
		types2 = new HashSet<String>();
		stTypes.add("Classes");
		stTypes.add("Class");
		types2.addAll(stTypes);

		stereotype2.setTypes(types2);

		stereotypes = new HashMap<String, Stereotype>();
		stereotypes.put(stereotype1.getId(), stereotype1);
		stereotypes.put(stereotype2.getId(), stereotype2);

		// <ownedMember xmi:type='uml:Profile'
		// xmi:id='_12_5_1_12e803d1_1185146167886_761175_232'
		// name='TesteProfile' visibility='public'>

		profile = new Profile("TesteProfile",
				"_12_5_1_12e803d1_1185146167886_761175_232", VisibilityType
						.toValue("public"), 3);

		// <TesteProfile:SimpleStereotype1
		// xmi:id='_12_5_1_12e803d1_1185146581395_917930_569'
		// base_Class='_12_5_1_12e803d1_1185146568325_708968_547'/>

		stereotypeApplication1 = new StereotypeApplication(
				"_12_5_1_12e803d1_1185146581395_917930_569", "uml:Class",
				"_12_5_1_12e803d1_1185146568325_708968_547", stereotype1
						.getName(), profile.getName(), 1);

		// <TesteProfile:SimpleStereotype2
		// xmi:id='_12_5_1_12e803d1_1185146581395_917930_777'
		// base_Class='_12_5_1_12e803d1_1185146568325_708968_111'/>

		stereotypeApplication2 = new StereotypeApplication(
				"_12_5_1_12e803d1_1185146581395_917930_777", "uml:Class",
				"_12_5_1_12e803d1_1185146568325_708968_111", stereotype2
						.getName(), profile.getName(), 2);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetId() {
		assertEquals("_12_5_1_12e803d1_1185146581395_917930_569",
				stereotypeApplication1.getId());
		assertEquals("_12_5_1_12e803d1_1185146581395_917930_777",
				stereotypeApplication2.getId());

	}

	public void testGetStereotypes() {
		assertEquals(stereotype1.getName(), stereotypeApplication1
				.getStereotype());
		assertEquals(stereotype2.getName(), stereotypeApplication2
				.getStereotype());
	}

	public void testGetBase() {
		assertEquals("uml:Class", stereotypeApplication1.getBase());
		assertEquals("uml:Class", stereotypeApplication2.getBase());
	}

	public void testGetBaseId() {
		assertEquals("_12_5_1_12e803d1_1185146568325_708968_547", stereotypeApplication1.getBaseId());
		assertEquals("_12_5_1_12e803d1_1185146568325_708968_111", stereotypeApplication2.getBaseId());

	}

	public void testGetBaseProfile() {
		assertEquals(profile.getName(), stereotypeApplication1.getProfile());
		assertEquals(profile.getName(), stereotypeApplication2.getProfile());
	}
}
