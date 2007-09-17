package profilechecker.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import profilechecker.model.Member;
import profilechecker.model.Model;
import profilechecker.model.Package;
import profilechecker.model.Profile;
import profilechecker.model.Stereotype;
import profilechecker.model.StereotypeApplication;
import profilechecker.model.ValidationException;
import profilechecker.model.VisibilityType;

import junit.framework.TestCase;

/**
 * Class to test the StereotypeApplicationValidation.
 * 
 * @author Matheus
 */
public class StereotypeApplicationValidationTest extends TestCase {

	private Model model;

	private StereotypeApplicationValidation sav;

	private String profileName;

	private String stereotypeName;

	private String type;

	private String packageName;

	private String id;

	/**
	 * Constructor to create the StereotypeApplicationValidation to be used.
	 */
	public StereotypeApplicationValidationTest() {
		sav = new StereotypeApplicationValidation();
	}

	@Override
	public void setUp() {
		Map<String, Profile> profiles = new HashMap<String, Profile>();
		profileName = "profile";
		stereotypeName = "stereotype";
		Profile profile = new Profile(profileName, "1", VisibilityType.publicV, 0);
		Stereotype stereotype = new Stereotype(stereotypeName, "1_1",
				VisibilityType.publicV, 0);
		type = "Class";
		stereotype.addType(type);
		profile.addStereotype(stereotypeName, stereotype);
		profiles.put(profileName, profile);

		Map<String, Package> packages = new HashMap<String, Package>();
		packageName = "package";
		Package package1 = new Package(packageName, "2", VisibilityType.publicV, 0);
		id = "2_1";
		package1.addMember(id, new Member("member", id, VisibilityType.publicV,
				type, 0));
		packages.put(packageName, package1);

		Set<StereotypeApplication> applications = new HashSet<StereotypeApplication>();

		applications.add(new StereotypeApplication("3", type, id,
				stereotypeName, profileName));

		this.model = new Model(profiles, packages, applications);
	}

	/**
	 * Test a successfully case of validation.
	 */
	public void testValidationOk() {
		Set<ValidationException> validate = sav.validate(model);
		assertTrue("No validation exception should occurr", validate.isEmpty());
	}

	/**
	 * Test a case where the applied profile could not be found.
	 */
	public void testProfileNotFound() {
		this.model.setProfiles(new HashMap<String, Profile>());
		Set<ValidationException> validate = sav.validate(model);
		assertEquals(1, validate.size());
		for (ValidationException ve : validate) {
			assertEquals("Profile not found: profile", ve.getMessage());
		}
	}

	/**
	 * Test a case where the applied stereotype could not be found.
	 */
	public void testStereotypeNotFound() {
		this.model.getProfiles().get(profileName).setStereotypes(
				new HashMap<String, Stereotype>());
		Set<ValidationException> validate = sav.validate(model);
		assertEquals(1, validate.size());
		for (ValidationException ve : validate) {
			assertEquals("Stereotype not found: stereotype", ve.getMessage());
		}
	}

	/**
	 * Test a case where the applied stereotype type is not valid (stereotype
	 * has no association in the meta-model).
	 */
	public void testTypeOfApplicationNotValid() {
		this.model.getProfiles().get(profileName).getStereotypes().get(
				stereotypeName).setTypes(new HashSet<String>());
		Set<ValidationException> validate = sav.validate(model);
		assertEquals(1, validate.size());
		for (ValidationException ve : validate) {
			assertEquals("Type not valid: Class", ve.getMessage());
		}
	}

	/**
	 * Test a case where the class member is null. (Defensive programming)
	 */
	public void testMemberTypeNull() {
		this.model.getPackages().get(packageName).getMembers().get(id).setType(
				null);
		Set<ValidationException> validate = sav.validate(model);
		assertEquals(1, validate.size());
		for (ValidationException ve : validate) {
			assertEquals("Member type cannot be null.", ve.getMessage());
		}
	}

	/**
	 * Test a case where the applied stereotype differ from the valid type and
	 * applied class type.
	 */
	public void testMemberTypeDifferent() {
		this.model.getPackages().get(packageName).getMembers().get(id).setType(
				"Something_Complete_Different");
		Set<ValidationException> validate = sav.validate(model);
		assertEquals(1, validate.size());
		for (ValidationException ve : validate) {
			assertEquals("Specified type Class cannot be different from application Something_Complete_Different", ve
					.getMessage());
		}
	}

}
