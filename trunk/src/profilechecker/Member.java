package profilechecker;

public class Member extends OwnedMember{
	
	private String id;
	private String xmiType;
	
	public Member(){
		
	}
	
	public Member(String name, String id, VisibilityType visibility, String xmiType){
		super(name,id,visibility);
		this.id = id;
		this.xmiType = xmiType;
	}
	
	

}
