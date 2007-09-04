package profilechecker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

public class ProfileTest extends TestCase {

	private Set<String> types2;

	private Stereotype stereotype2;

	private Stereotype stereotype1;

	private Set<String> types1;

	private Profile profile;

	private Map<String, Stereotype> stereotypes;

	protected void setUp() throws Exception {
		super.setUp();

		stereotype1 = new Stereotype("SimpleStereotype1",
				"_12_5_1_12e803d1_1186332378092_478735_301", VisibilityType
						.toValue("public"));
		List<String> stTypes = new ArrayList();
		types1 = new HashSet<String>();
		stTypes.add("Classes");
		stTypes.add("Kernel");
		stTypes.add("Class");
		types1.addAll(stTypes);

		stereotype1.setTypes(types1);

		stereotype2 = new Stereotype("SimpleStereotype2",
				"_12_5_1_12e803d1_1186332374248_452459_298", VisibilityType
						.toValue("public"));
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
						.toValue("public"));

	}

	protected void tearDown() throws Exception {
		super.tearDown();

		stereotype1 = null;
		stereotype2 = null;
		stereotypes = null;
		profile = null;
	}

	public void testGetName() {
		assertEquals("TesteProfile", profile.getName());

	}

	public void testGetId() {
		assertEquals("_12_5_1_12e803d1_1185146167886_761175_232", profile
				.getId());

	}

	public void testGetVisibility() {
		assertEquals(VisibilityType.toValue("public"), profile.getVisibility());
	}

	public void testGetStereotypes() {
		profile.setStereotypes(stereotypes);
		assertEquals(stereotypes,profile.getStereotypes());
	}
}
