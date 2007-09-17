package profilechecker.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

public class ModelTest extends TestCase {

	private Model model1;

	private String profileName;

	private String packageName;

	private String stereotypeName;

	private String type;

	private String id;
	
	private Map<String, Profile> profiles;
	
	private Map<String, Package> packages;
	
	private Set<StereotypeApplication> applications;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		profiles = new HashMap<String, Profile>();
		profileName = "profile";
		stereotypeName = "stereotype";
		Profile profile = new Profile(profileName, "1", VisibilityType.publicV, 1);
		Stereotype stereotype = new Stereotype(stereotypeName, "1_1",
				VisibilityType.publicV, 2);
		type = "Class";
		stereotype.addType(type);
		profile.addStereotype(stereotypeName, stereotype);
		profiles.put(profileName, profile);

		packages = new HashMap<String, Package>();
		packageName = "package";
		Package package1 = new Package(packageName, "2", VisibilityType.publicV, 3);
		id = "2_1";
		package1.addMember(id, new Member("member", id, VisibilityType.publicV,
				type, 4));
		packages.put(packageName, package1);

		applications = new HashSet<StereotypeApplication>();

		applications.add(new StereotypeApplication("3", type, id,
				stereotypeName, profileName));

		this.model1 = new Model(profiles, packages, applications);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetProfile() {
		assertEquals(profiles,model1.getProfiles());
	}

	public void testGetPackages() {
		assertEquals(packages,model1.getPackages());
	}

	public void testGetApplications() {
		assertEquals(applications,model1.getApplications());
	}

	public void testGetValidationExceptions() {
		//Ne exception is expected becouse no exception was inserted in this model
		assertEquals(new HashSet<ValidationException>() , model1.getValidationExceptions());
	}

}
