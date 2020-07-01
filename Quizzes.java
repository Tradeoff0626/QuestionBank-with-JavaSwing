package itembank;

public class Quizzes {
	private int seqNum;
	private String group;
	private String quiz;
	private byte[] quizImg;
	private String exam1;
	private String exam2;
	private String exam3;
	private String exam4;
	private byte[] examImg;
	private int answer;
	private String solution;
	private int count;
	private int hit;
	
	
	
	/////////////////// Getter /////////////////////////////
	public int getSeqNum() {
		return seqNum;
	}
	public String getGroup() {
		return group == null ? "" : group.trim();
	}
	public String getQuiz() {
		return quiz == null ? "" : quiz.trim();
	}
	public byte[] getQuizImg() {
		return quizImg;
	}
	public String getExam1() {
		return exam1 == null ? "" : exam1.trim();
	}
	public String getExam2() {
		return exam2 == null ? "" : exam2.trim();
	}
	public String getExam3() {
		return exam3 == null ? "" : exam3.trim();
	}
	public String getExam4() {
		return exam4 == null ? "" : exam4.trim();
	}
	public byte[] getExamImg() {
		return examImg;
	}
	public int getAnswer() {
		return answer;
	}
	public String getSolution() {
		return solution == null ? "" : solution.trim();
	}
	public int getCount() {
		return count;
	}
	public int getHit() {
		return hit;
	}
	
	
/////////////////// Setter /////////////////////////////
	
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}
	public void setQuizImg(byte[] quizImg) {
		this.quizImg = quizImg;
	}
	public void setExam1(String exam1) {
		this.exam1 = exam1;
	}
	public void setExam2(String exam2) {
		this.exam2 = exam2;
	}
	public void setExam3(String exam3) {
		this.exam3 = exam3;
	}
	public void setExam4(String exam4) {
		this.exam4 = exam4;
	}
	public void setExamImg(byte[] examImg) {
		this.examImg = examImg;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
}
