package profilechecker;

import java.util.LinkedHashSet;
import java.util.Set;


public class Stereotype extends OwnedMember {

	private Set<String> types;

	public Stereotype() {
		
	}

	public Stereotype(String name, String id, VisibilityType visibility) {
		super(name, id, visibility);
		setTypes(new LinkedHashSet<String>());
	}
	
	public void setTypes(Set<String> types) {
		this.types = types;
	}
	
	public Set<String> getTypes() {
		return this.types;
	}
	
	public void addType(String type) {
		this.types.add(type);
	}
	
}