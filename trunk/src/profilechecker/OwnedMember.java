package profilechecker;

import java.io.Serializable;

public class OwnedMember implements Serializable {

	private String name;
	private String id;
	private VisibilityType visibility;

	public OwnedMember() {

	}

	public OwnedMember(String name, String id, VisibilityType visibility) {
		setName(name);
		setId(id);
		setVisibility(visibility);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new NullPointerException("Name property must not be null");
		}
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id == null) {
			throw new NullPointerException("ID property must not be null");
		}
		this.id = id;
	}

	public VisibilityType getVisibility() {
		return visibility;
	}

	public void setVisibility(VisibilityType visibility) {
		if (visibility == null) {
			throw new NullPointerException(
					"Visibility property must not be null");
		}
		this.visibility = visibility;
	}

}