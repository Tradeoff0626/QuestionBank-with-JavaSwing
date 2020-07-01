package itembank;

import java.sql.Date;

public class Scores {
	private int num;
	private String id;
	private int subject1;
	private int subject2;
	private int subject3;
	private int subject4;
	private int subject5;
	private Date date;
	
	public int getNum() {
		return num;
	}
	public String getId() {
		return id == null ? "" : id.trim();
	}
	public int getSubject1() {
		return subject1;
	}
	public int getSubject2() {
		return subject2;
	}
	public int getSubject3() {
		return subject3;
	}
	public int getSubject4() {
		return subject4;
	}
	public int getSubject5() {
		return subject5;
	}
	public Date getDate() {
		return date;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setSubject1(int subject1) {
		this.subject1 = subject1;
	}
	public void setSubject2(int subject2) {
		this.subject2 = subject2;
	}
	public void setSubject3(int subject3) {
		this.subject3 = subject3;
	}
	public void setSubject4(int subject4) {
		this.subject4 = subject4;
	}
	public void setSubject5(int subject5) {
		this.subject5 = subject5;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getTotalScore() {
		return this.subject1 + this.subject2 + this.subject3 + this.subject4 + this.subject5;
	}
	
	public String getEvaluate() {
		String str = "합격";
		
		if(getTotalScore()<60)
		str = "불합격";
		
		if(this.subject1<8 || this.subject2<8 || this.subject3<8 || this.subject4<8 || this.subject5<8)
			str = "과락";
		
		return str;
	}
	
}
