package profilechecker;

import java.util.HashMap;
import java.util.Map;

public class Class extends OwnedMember{
	
	private Map<String, Stereotype> stereorypes;

	public Class (String name, String id, VisibilityType visibility){
		super(name, id, visibility);
		stereorypes = new HashMap<String, Stereotype>();
	}
	
	public Map<String, Stereotype> getStereotypes(){
		return stereorypes;
	}
	
	public void addStereotypes(String id, Stereotype s){
		stereorypes.put(id,s);
	}
}
