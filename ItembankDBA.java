package itembank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.sql.BLOB;



 /*******************************************************
 * 날짜 : 14.10.01 							- K.C.Kang
 * 
 * 
 * info : 문제은행의 데이터베이스 접근 및 자료 처리 관련 클래스
 *******************************************************/

public class ItembankDBA {
	private String mUrl;
	private String mUser;
	private String mPassword;
	
	private Users mUsr;
	
	public ItembankDBA() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Oracle Driver 등록
			
			mUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
			mUser = "scott";
			mPassword = "tiger";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Users getLogInInfo(String id, String pw) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		mUsr = null;
		
		try {
				con = DriverManager.getConnection(mUrl, mUser, mPassword);
				
				String sql = "SELECT * FROM itembank_users WHERE user_id='" + id + "'";
				System.out.println(sql);
				
				st = con.createStatement();
				rs = st.executeQuery(sql);		
				
				if(rs.next()) {
					mUsr = new Users();
					
					System.out.println(id + " : exist");
					
					mUsr.setId(rs.getString("user_id"));
					
					if(pw.equals(rs.getString("user_pw")))
						mUsr.setPw("pass");
					else
						mUsr.setPw("fail");
					
					mUsr.setName(rs.getString("user_name"));
					mUsr.setEmail(rs.getString("user_email"));
					mUsr.setPriority(rs.getString("user_prior"));
					//mUsr.setapplyCount(rs.getInt("user_applycount"));
					mUsr.setPass(rs.getString("user_pass"));
					
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
					st.close();
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return mUsr;
	}
	
	public Boolean isExistID(String id) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		boolean b = false;
		
		try {
				con = DriverManager.getConnection(mUrl, mUser, mPassword);
				
				String sql = "SELECT * FROM itembank_users WHERE user_id='" + id + "'";
				System.out.println(sql);
				
				st = con.createStatement();
				rs = st.executeQuery(sql);		
				
				if(rs.next()) {
					b = true;
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
					st.close();
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return b;
	}

	
	public ArrayList<Users> getUserList(String sql) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		ArrayList<Users> arr = new ArrayList<Users>();
		System.out.println(sql);
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			st = con.createStatement();
			rs = st.executeQuery(sql);

			while(rs.next())
			{
				Users usr = new Users();
				
				usr.setId(rs.getString("user_id"));
				usr.setPw("null");			
				usr.setName(rs.getString("user_name"));
				usr.setEmail(rs.getString("user_email"));
				usr.setPriority(rs.getString("user_prior"));
			//	usr.setapplyCount(rs.getInt("user_applycount"));
				usr.setPass(rs.getString("user_pass"));
				
				arr.add(usr);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return arr;
	}
	
	public ArrayList<Scores> getUserScoreList(String sql) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		ArrayList<Scores> arr = new ArrayList<Scores>();
		System.out.println(sql);
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			st = con.createStatement();
			rs = st.executeQuery(sql);

			while(rs.next())
			{
				Scores scr = new Scores();
				
				scr.setId(rs.getString("score_id"));
				scr.setNum(rs.getInt("score_num"));
				scr.setDate(rs.getDate("score_date"));
				scr.setSubject1(rs.getInt("score_subject1"));
				scr.setSubject2(rs.getInt("score_subject2"));
				scr.setSubject3(rs.getInt("score_subject3"));
				scr.setSubject4(rs.getInt("score_subject4"));
				scr.setSubject5(rs.getInt("score_subject5"));
				
				arr.add(scr);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return arr;
	}
	
	public int addUser(Users usr) {
		Connection con = null;
		PreparedStatement ps = null;
		
		int result = 0;
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			
			String sql = "INSERT INTO itembank_users(user_id, user_pw, user_name, user_email, "
					+ "user_prior, user_pass) VALUES(?,?,?,?,?,?)";
			
			ps = con.prepareStatement(sql);
			
			
			ps.setString(1, usr.getId());
			ps.setString(2, usr.getPw());
			ps.setString(3, usr.getName());
			ps.setString(4, usr.getEmail());
			ps.setString(5, usr.getPriority());
		//	ps.setInt(6, usr.getapplyCount());
			ps.setString(6, usr.getPass());
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	/////////////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////////
	
	// 점수 결과 저장하는 메소드 /////
	
	public int addScore(String id, ArrayList<Integer> scores) {
		Connection con = null;
		PreparedStatement ps = null;
		
		int result = 0;
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			
			String sql = "INSERT INTO itembank_scores(score_num, score_id, score_subject1, score_subject2, "
					+ "score_subject3, score_subject4, score_subject5) "
					+ "VALUES(quiz_score_seq.nextval,?,?,?,?,?,?)";
			
			ps = con.prepareStatement(sql);
			
			
			ps.setString(1, id);
			ps.setInt(2, scores.get(0));
			ps.setInt(3, scores.get(1));
			ps.setInt(4, scores.get(2));
			ps.setInt(5, scores.get(3));
			ps.setInt(6, scores.get(4));
	
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public int modifyUser(Users usr) {
		Connection con = null;
		PreparedStatement ps = null;
		
		int result = 0;
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			
			String sql = "UPDATE itembank_users "
					+ "SET user_pw=?, user_email=?"
					+ " where user_id='" + usr.getId() + "'";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, usr.getPw());
			ps.setString(2, usr.getEmail());
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int UpdateUserInfo(Users usr, String pass) {
		Connection con = null;
		PreparedStatement ps = null;
		
		int result = 0;
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			
			String sql = "UPDATE itembank_users"
					+ " SET user_pass=?"
					+ " where user_id='" + usr.getId() + "'";
			
			ps = con.prepareStatement(sql);
			
		//	ps.setInt(1, usr.getapplyCount()+1);
		//	ps.setString(2, pass);
			ps.setString(1, pass);
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int setUserPrior(Users usr) {
		Connection con = null;
		PreparedStatement ps = null;
		
		int result = 0;
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			
			String sql = "UPDATE itembank_users "
					+ "SET user_email=prior"
					+ " where user_id='" + usr.getId() + "'";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, usr.getPriority());
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public int addQuiz(Quizzes qz) {
		Connection con = null;
		PreparedStatement ps = null;
		
		int result = 0;
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			
			String sql = "INSERT INTO itembank_quizzes(quiz_num, quiz_group, quiz_quiz, quiz_quiz_img,"
					+ " quiz_exam1, quiz_exam2, quiz_exam3, quiz_exam4, quiz_exam_img, quiz_answer,"
					+ " quiz_solution, quiz_count, quiz_hit) VALUES(quiz_test_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, qz.getGroup());
			ps.setString(2, qz.getQuiz());
			ps.setBytes(3, qz.getQuizImg());
			ps.setString(4, qz.getExam1());
			ps.setString(5, qz.getExam2());
			ps.setString(6, qz.getExam3());
			ps.setString(7, qz.getExam4());
			ps.setBytes(8, qz.getExamImg());
			ps.setInt(9, qz.getAnswer());
			ps.setString(10, qz.getSolution());
			ps.setInt(11, 0);
			ps.setInt(12, 0);
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int deleteQuiz(int seqNum) {
		Connection con = null;
		Statement st = null;
		int result = 0;
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			
			String sql = "DELETE FROM itembank_quizzes WHERE quiz_num=" + seqNum;
			System.out.println(sql);
			
			st = con.createStatement();
			result = st.executeUpdate(sql);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private ArrayList<Integer> getQuizNumByRandNum(ResultSet rs, int count) {
		/**********************************************
		 * 유형별로 저장된 rs와 생성할 문제수(count)를 받아 랜덤으로 정해진 문제 번호를 반환.
		 **********************************************/
		ResultSetMetaData rsmd = null;
		int qCount = 0;
		
		QuizRandomGen qrg = new QuizRandomGen();
		ArrayList<Integer> quizNums = new ArrayList<Integer>();
		ArrayList<Integer> seqIndex = new ArrayList<Integer>(); //문제들의 seq 번호 리스트 저장용
		
		try {
			while(rs.next()) {
				seqIndex.add(rs.getInt("quiz_num"));
				qCount++;
			}
			
			ArrayList<Integer> index = qrg.RandGenList(count, qCount/*rs.getMetaData().getColumnCount()*/);	//유형별  20개 랜덤 추출.
									
			
			System.out.println(index.size());
			for(int i=0; i<index.size(); i++) {
				for(int j=0; j<seqIndex.size(); j++) {
					if(index.get(i) == j)
						quizNums.add(seqIndex.get(j));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quizNums;
	}
	
	public ArrayList<Quizzes> getTestQuizList() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		ArrayList<Integer> seqList = new ArrayList<Integer>();	//랜덤으로 정해진 100개 문제의 '문제 번호' 저장용
		ArrayList<Quizzes> quizList = new ArrayList<Quizzes>();	//100개의 '문제' 저장용. 리턴값.
			
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			st = con.createStatement();
		
			//데이터베이스 유형 문제 랟덤 리스드 생성 
			String sql = "SELECT * FROM itembank_quizzes WHERE quiz_group='데이터베이스'";
			rs = st.executeQuery(sql);
			seqList.addAll(getQuizNumByRandNum(rs, 20));	//데이터베이스 문제 중 20 문제 인덱스 등록.
			
			//데이터베이스 유형 문제 랟덤 리스드 생성 
			sql = "SELECT * FROM itembank_quizzes WHERE quiz_group='전자계산기구조'";
			rs = st.executeQuery(sql);
			seqList.addAll(getQuizNumByRandNum(rs, 20));	//전자계산기구조  문제 중 20 문제 인덱스 등록.
			
			//데이터베이스 유형 문제 랟덤 리스드 생성 
			sql = "SELECT * FROM itembank_quizzes WHERE quiz_group='운영체제'";
			rs = st.executeQuery(sql);
			seqList.addAll(getQuizNumByRandNum(rs, 20));	//운영체제 문제 중 20 문제 인덱스 등록.
			
			//데이터베이스 유형 문제 랟덤 리스드 생성 
			sql = "SELECT * FROM itembank_quizzes WHERE quiz_group='소프트웨어공학'";
			rs = st.executeQuery(sql);
			seqList.addAll(getQuizNumByRandNum(rs, 20));	//소프트웨어공학 문제 중 20 문제 인덱스 등록.
			
			//데이터베이스 유형 문제 랟덤 리스드 생성 
			sql = "SELECT * FROM itembank_quizzes WHERE quiz_group='데이터통신'";
			rs = st.executeQuery(sql);
			seqList.addAll(getQuizNumByRandNum(rs, 20));	//데이터통신 문제 중 20 문제 인덱스 등록.
			
			
			for(int i=0; i<seqList.size(); i++) {
				sql = "SELECT * FROM itembank_quizzes WHERE quiz_num=" + seqList.get(i);
				rs = st.executeQuery(sql);
			
				if(rs.next()) {
					Quizzes qz = new Quizzes();
					
					qz.setSeqNum(rs.getInt("quiz_num"));
					qz.setGroup(rs.getString("quiz_group"));
					qz.setQuiz(rs.getString("quiz_quiz"));
					qz.setQuizImg(rs.getBytes("quiz_quiz_img"));
					qz.setExam1(rs.getString("quiz_exam1"));
					qz.setExam2(rs.getString("quiz_exam2"));
					qz.setExam3(rs.getString("quiz_exam3"));
					qz.setExam4(rs.getString("quiz_exam4"));
					qz.setExamImg(rs.getBytes("quiz_exam_img"));
					qz.setAnswer(rs.getInt("quiz_answer"));
					qz.setSolution(rs.getString("quiz_solution"));
					qz.setCount(rs.getInt("quiz_count"));
					qz.setHit(rs.getInt("quiz_hit"));
				
					quizList.add(qz);
				}
				
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		return quizList;
	}
	
	public void updateQuizCount(ArrayList<Quizzes> quizList, Integer[] answerList) {
		
		//문제 출제수 갱신
		//(문제 적중률 갱신 구현 필요)
		Connection con = null;
		PreparedStatement ps = null;
	
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			
			for(int i=0; i<quizList.size(); i++) {
	
				String sql = "UPDATE itembank_quizzes"
						+ " SET quiz_count=?, quiz_hit=?"
						+ " WHERE quiz_num=" + quizList.get(i).getSeqNum();
			
				ps = con.prepareStatement(sql);
				ps.setInt(1, quizList.get(i).getCount()+1);			//
				
				if(quizList.get(i).getAnswer()==answerList[i]) {		//문제가 맞았을 때
					ps.setInt(2, quizList.get(i).getHit()+1);
					System.out.println("hits : " + quizList.get(i).getSeqNum() + " : " +quizList.get(i).getHit()+1);
				}
				else {
					ps.setInt(2, quizList.get(i).getHit());
					System.out.println("hits : " + quizList.get(i).getSeqNum() + " : X ");
				}
				
				ps.executeUpdate();	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public ArrayList<Quizzes> getQuizList(String sql) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		ArrayList<Quizzes> arr = new ArrayList<Quizzes>();
		System.out.println(sql);
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			st = con.createStatement();
			rs = st.executeQuery(sql);

			while(rs.next())
			{
				Quizzes qz = new Quizzes();
					
				qz.setSeqNum(rs.getInt("quiz_num"));
				qz.setGroup(rs.getString("quiz_group"));
				qz.setQuiz(rs.getString("quiz_quiz"));
				qz.setQuizImg(rs.getBytes("quiz_quiz_img"));
				qz.setExam1(rs.getString("quiz_exam1"));
				qz.setExam2(rs.getString("quiz_exam2"));
				qz.setExam3(rs.getString("quiz_exam3"));
				qz.setExam4(rs.getString("quiz_exam4"));
				qz.setExamImg(rs.getBytes("quiz_exam_img"));
				qz.setAnswer(rs.getInt("quiz_answer"));
				qz.setSolution(rs.getString("quiz_solution"));
				qz.setCount(rs.getInt("quiz_count"));
				qz.setHit(rs.getInt("quiz_hit"));
				
				arr.add(qz);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return arr;
	}
	
	
	public int modifyQuiz(Quizzes qz, int quizNum) {
		Connection con = null;
		PreparedStatement ps = null;
		
		int result = 0;
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			
			String sql = "UPDATE itembank_quizzes "
					+ "SET quiz_group=?, quiz_quiz=?, quiz_quiz_img=?, quiz_exam1=?, quiz_exam2=?, quiz_exam3=?, "
					+ "quiz_exam4=?, quiz_exam_img=?, quiz_answer=?, quiz_solution=? where quiz_num=" + quizNum;
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, qz.getGroup());
			ps.setString(2, qz.getQuiz());
			ps.setBytes(3, qz.getQuizImg());
			ps.setString(4, qz.getExam1());
			ps.setString(5, qz.getExam2());
			ps.setString(6, qz.getExam3());
			ps.setString(7, qz.getExam4());
			ps.setBytes(8, qz.getExamImg());
			ps.setInt(9, qz.getAnswer());
			ps.setString(10, qz.getSolution());
			
			result = ps.executeUpdate();
			
			System.out.println(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return result;
	}

	public ArrayList<Scores> getScoreList(String sql) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		ArrayList<Scores> arr = new ArrayList<Scores>();
		System.out.println(sql);
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			st = con.createStatement();
			rs = st.executeQuery(sql);

			while(rs.next())
			{
				Scores scr = new Scores();
					
				scr.setNum(rs.getInt("score_num"));
				scr.setId(rs.getString("score_id"));
				scr.setSubject1(rs.getInt("score_subject1"));
				scr.setSubject2(rs.getInt("score_subject2"));
				scr.setSubject3(rs.getInt("score_subject3"));
				scr.setSubject4(rs.getInt("score_subject4"));
				scr.setSubject5(rs.getInt("score_subject5"));
				scr.setDate(rs.getDate("score_date"));
				
				arr.add(scr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return arr;
	}
	
	public int getSingleValueFromDB(String sql) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		int result = -1;
		
		System.out.println(sql);
		
		try {
			con = DriverManager.getConnection(mUrl, mUser, mPassword);
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if(rs.next())
				result = rs.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				st.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return result;
	}
}
