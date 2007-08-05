package profilechecker;

import java.util.HashMap;
import java.util.Map;

public class Profile extends OwnedMember {

	private Map<String, Stereotype> stereotypes;

	public Profile() {

	}

	public Profile(String name, String id, VisibilityType visibility) {
		super(name, id, visibility);
		setStereotypes(new HashMap<String, Stereotype>());
	}

	public Map<String, Stereotype> getStereotypes() {
		return stereotypes;
	}

	public void setStereotypes(Map<String, Stereotype> stereotypes) {
		this.stereotypes = stereotypes;
	}

	public void addStereotype(String id, Stereotype stereotype) {
		this.stereotypes.put(id, stereotype);
	}

}