package profilechecker;

public class Member extends OwnedMember{
	
	private String id;
	private String type;
	
	public Member(){
		
	}
	
	public Member(String name, String id, VisibilityType visibility, String type){
		super(name,id,visibility);
		this.id = id;
		this.type = type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
