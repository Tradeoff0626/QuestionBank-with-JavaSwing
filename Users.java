package itembank;

public class Users {
	private String id;
	private String pw;
	private String name;
	private String priority;
	private String email;
	//private int applyCount;
	private String pass;
	
	
	///////////// GETTER	//////////////////////////////////////////////
	
	public String getId() {
		return id == null ? "" : id.trim();
	}
	public String getPw() {
		return pw == null ? "" : pw.trim();
	}
	public String getName() {
		return name == null ? "" : name.trim();
	}
	public String getPriority() {
		return priority == null ? "" : priority.trim();
	}
	public String getEmail() {
		return email == null ? "" : email.trim();
	}
	//public int getapplyCount() {
	//	return applyCount;
	//}
	public String getPass() {
		return pass == null ? "" : pass.trim();
	}
	
	///////////// SETTER	//////////////////////////////////////////////

	public void setId(String id) {
		this.id = id;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	//public void setapplyCount(int applyCount) {
	//	this.applyCount = applyCount;
	//}
	public void setPass(String pass) {
		this.pass = pass;
	}	
	
	
	
}
