package itembank;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.border.TitledBorder;

import java.awt.Color;

import javax.swing.UIManager;
import java.awt.Font;

public class AdminModeView extends JFrame {

	private JPanel contentPane;
	private JSplitPane splitPane;
	private JTabbedPane tabbedPane;
	private JSplitPane splitPane_1;
	private JSplitPane splitPane_2;
	private JSplitPane splitPane_3;
	private JSplitPane splitPane_4;
	private JPanel panel;
	private JPanel panel_1;
	private JScrollPane scrollPane_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JTable tbQuizzes;
	
	private Users mUsr;
	private ItembankDBA ibDBA = new ItembankDBA();
	private JButton btnAddUsr;

	private MemberJoinView mMngMemView;				//멤버 추가/수정 클래스 
	private JComboBox cbbPrior;
	private JComboBox cbbPass;
	private JComboBox cbbUserSearch;
	private JTextField tfUserSearch;
	private JButton btnUserSearch;
	private JComboBox cbbGroup;
	private JComboBox cbbSort;
	private JTextField tfQuizSearch;
	private JButton btnQuizSearch;
	private JButton btnAddQuiz;
	private JButton btnEditQuiz;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JButton btnSimTest;
	private JLabel label_3;
	private JLabel label_4;
	
	private RegQuizView mRegQuizView;
	private int mSelUsrRow, mSelQuizRow;
	
	private ArrayList<Users> mUsrArr;
	private ArrayList<Quizzes> mQzArr;
	private ArrayList<Scores> mScrArr;
	private JButton btnDelQuiz;
	
	private AdminModeView mThisObj;
	private JButton btnQuizInit;
	private JButton btnUsrInit;
	private JSplitPane splitPane_5;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_2;
	private JTable tnUserss;
	private JTable tbUserScore;
	private JTable tbUsers;
	
	private String mQuizOrderOpt;
	private JSplitPane splitPane_6;
	private JScrollPane scrollPane_3;
	private JPanel panel_5;
	private JTable tbScores;
	private JButton btnRefresh;
	private JScrollPane scrollPane_4;
	private JTable tbUserCount;
	private JScrollPane scrollPane_5;
	private JTable tbQuizCount;
	private JLabel lbStatus;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminModeView frame = new AdminModeView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminModeView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getSplitPane(), BorderLayout.CENTER);
	}
	
	public AdminModeView(Users usr) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getSplitPane(), BorderLayout.CENTER);
		
		comboBoxInit();		//콤보박스 초기화
			
		mUsr = usr;			//로그인한 유저의 정보 저장
		mThisObj = this;	//멤버 등록 및 문제 등록 클래스에서 데이터 갱신 후 이 클래스의 테이블을 갱신(멤버 메소드 접근)하기 위해 전달할 객체 주소 백업 
		
		tableInit();		//출려되는 테이블 초기화
		
		setTitle("관리자 모드 - " + mUsr.getName() + "님");
		lbStatus.setText("관리자 모드로 접속하였습니다.");
		
		setVisible(true);
	}
	
	private void comboBoxInit() {
		/*******************************
		 * 옵션이 될 콤보박스 값 초기화  
		 ******************************/
		
		cbbPrior.addItem("전체");
		cbbPrior.addItem("응시자");
		cbbPrior.addItem("관리자");
		
		cbbPass.addItem("전체");
		cbbPass.addItem("합격");
		cbbPass.addItem("불합격");
		cbbPass.addItem("미응시");
		
		cbbUserSearch.addItem("아이디");
		cbbUserSearch.addItem("이름");
		cbbUserSearch.addItem("이메일");
		
		
		cbbGroup.addItem("전체");
		cbbGroup.addItem("데이터베이스");
		cbbGroup.addItem("전자계산기구조");
		cbbGroup.addItem("운영체제");
		cbbGroup.addItem("소프트웨어공학");
		cbbGroup.addItem("데이터통신");
		
		cbbSort.addItem("등록순");
		cbbSort.addItem("문제 사전순");
		cbbSort.addItem("유형별");
	}
	
	private void tableInit() {
		/************************************
		 * 데이터를 출력할 테이블에 필요한 쿼리문 처리 
		 * 결과 값을 테이블에 출력
		 *************************************/
		
		String sql = "SELECT * FROM itembank_users";
		mUsrArr = ibDBA.getUserList(sql);
		
		mQuizOrderOpt = " ORDER BY quiz_num";
		sql = "SELECT * FROM itembank_quizzes" + mQuizOrderOpt;
		mQzArr = ibDBA.getQuizList(sql);
		
		sql = "SELECT * FROM itembank_scores ORDER BY score_num";
		mScrArr = ibDBA.getScoreList(sql);
		
		printUserTable(mUsrArr);
		printQuizTable(mQzArr);			
		
		printScoreTable(mScrArr);
		printUserCountTable();
		printQuizCountTable();
	}
	
	private void alignCenterColumnValue(JTable tbl) {
		/*****************************************************
		 * 테이블의 객체를 받아서 해당 테이블의 요소 값을 가운데 정렬시키는 메소드
		 *****************************************************/
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = tbl.getColumnModel();
		
		for(int i=0; i<tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
	}
	
	public void printUserTable(ArrayList<Users> arr) {
		/*********************************************************
		 * 사용자 정보를 테이블에 출력
		 *********************************************************/
		
		String[] tblUsrCols = {"ID", "이름", "e-Mail", /*"응시 횟수",*/ "지난 결과", "권한"};
		DefaultTableModel dtUsr = new DefaultTableModel(tblUsrCols, arr.size());
		tbUsers.setModel(dtUsr);
		
		for(int i=0; i<arr.size(); i++) {
			dtUsr.setValueAt(arr.get(i).getId(), i, 0);
			dtUsr.setValueAt(arr.get(i).getName(), i, 1);
			dtUsr.setValueAt(arr.get(i).getEmail(), i, 2);
			//dtUsr.setValueAt(arr.get(i).getapplyCount(), i, 3);
			//String sql = "SELECT COUNT(*) FROM itembank_scores WHERE score_id='" + arr.get(i).getId() + "'";
			//dtUsr.setValueAt(ibDBA.getSingleValueFromDB(sql), i, 3);
			dtUsr.setValueAt(arr.get(i).getPass(), i, 3);
			dtUsr.setValueAt(arr.get(i).getPriority().equals("ADMIN") ? "관리자" : "응시자", i, 4);
			
		}
		
		alignCenterColumnValue(tbUsers);	
	}
	
	public void printUserScoreTable(ArrayList<Scores> arr) {
		/*********************************************************
		 * 시험 점수에 관련된 정보를 테이블에 출력
		 *********************************************************/
		
		String[] tblQuestCols = {"응시 차수", "응시 날짜", "데이터베이스", "전자계산기구조", "운영체제", "소프트웨어공학", "데이터통신"};
		DefaultTableModel dtUsrScr = new DefaultTableModel(tblQuestCols, arr.size());
		tbUserScore.setModel(dtUsrScr);
		
		for(int i=0; i<arr.size(); i++) {
			dtUsrScr.setValueAt(i+1, i, 0);
			dtUsrScr.setValueAt(arr.get(i).getDate(), i, 1);
			dtUsrScr.setValueAt(arr.get(i).getSubject1(), i, 2);
			dtUsrScr.setValueAt(arr.get(i).getSubject2(), i, 3);
			dtUsrScr.setValueAt(arr.get(i).getSubject3(), i, 4);
			dtUsrScr.setValueAt(arr.get(i).getSubject4(), i, 5);
			dtUsrScr.setValueAt(arr.get(i).getSubject5(), i, 6);
		}
		
		alignCenterColumnValue(tbUserScore);
	}
	
	
	public void printQuizTable(ArrayList<Quizzes> arr) {
		/*********************************************************
		 * 문제 정보를 테이블에 출력
		 *********************************************************/
		
		String[] tblQuestCols = {"번호", "상세 과목", "문제", "문제 이미지", "보기1", "보기2", "보기3", "보기4", "보기 이미지", "정답", "풀이", "출제 횟수", "정답 횟수", "정답률"};
		DefaultTableModel dtQuest = new DefaultTableModel(tblQuestCols, arr.size());
		tbQuizzes.setModel(dtQuest);
		
		for(int i=0; i<arr.size(); i++) {
			dtQuest.setValueAt(arr.get(i).getSeqNum(), i, 0);
			dtQuest.setValueAt(arr.get(i).getGroup(), i, 1);
			dtQuest.setValueAt(arr.get(i).getQuiz(), i, 2);
			dtQuest.setValueAt(arr.get(i).getQuizImg()!=null ? "O" : "X", i, 3);
			dtQuest.setValueAt(arr.get(i).getExam1(), i, 4);
			dtQuest.setValueAt(arr.get(i).getExam2(), i, 5);
			dtQuest.setValueAt(arr.get(i).getExam3(), i, 6);
			dtQuest.setValueAt(arr.get(i).getExam4(), i, 7);
			dtQuest.setValueAt(arr.get(i).getExamImg()!=null ? "O" : "X", i, 8);
			dtQuest.setValueAt(arr.get(i).getAnswer(), i, 9);
			dtQuest.setValueAt(arr.get(i).getSolution(), i, 10);
			dtQuest.setValueAt(arr.get(i).getCount(), i, 11);
			dtQuest.setValueAt(arr.get(i).getHit(), i, 12);
			double hits = (double)arr.get(i).getHit()/arr.get(i).getCount()*100;
			String strHits = String.format("%.2f", hits);
			dtQuest.setValueAt(strHits+"%", i, 13);
		}
		
		alignCenterColumnValue(tbQuizzes);
	}
	
	public void printScoreTable(ArrayList<Scores> arr) {
		/*********************************************************
		 * 점수에 대한 상세 정보를 테이블에 출력
		 *********************************************************/
		
		String[] tblScoreCols = {"번호", "아이디", "날짜", "데이터베이스", "전자계산기구조", "운영체제", "소프트웨어공학", "데이터통신", "총점", "평가"};
		DefaultTableModel dtScore = new DefaultTableModel(tblScoreCols, arr.size());
		tbScores.setModel(dtScore);

		for(int i=0; i<arr.size(); i++) {
			dtScore.setValueAt(arr.get(i).getNum(), i, 0);
			dtScore.setValueAt(arr.get(i).getId(), i, 1);
			dtScore.setValueAt(arr.get(i).getDate(), i, 2);
			dtScore.setValueAt(arr.get(i).getSubject1(), i, 3);
			dtScore.setValueAt(arr.get(i).getSubject2(), i, 4);
			dtScore.setValueAt(arr.get(i).getSubject3(), i, 5);
			dtScore.setValueAt(arr.get(i).getSubject4(), i, 6);
			dtScore.setValueAt(arr.get(i).getSubject5(), i, 7);			
			dtScore.setValueAt(arr.get(i).getTotalScore(), i, 8);
			dtScore.setValueAt(arr.get(i).getEvaluate(), i, 9);
			}
		
		alignCenterColumnValue(tbScores);
	}
	
	public void printUserCountTable() {
		/*********************************************************
		 * 합격률에 대한 정보를 테이블에 출력 
		 *********************************************************/
		
		String[] tblUsrCntCols = {"응시 횟수", "합격 횟수","합격율", "응시자 수", "합격자 수", "합격자 비율"};
		DefaultTableModel dtUsrCnt = new DefaultTableModel(tblUsrCntCols, 1);
		tbUserCount.setModel(dtUsrCnt);
		
		
		int applyCnt = ibDBA.getSingleValueFromDB("SELECT COUNT(*) FROM itembank_scores");
		dtUsrCnt.setValueAt(applyCnt, 0, 0);
		int passCnt = ibDBA.getSingleValueFromDB("SELECT COUNT(*) FROM itembank_scores WHERE (score_subject1+score_subject2+score_subject3+score_subject4+score_subject5)>=60");
		dtUsrCnt.setValueAt(passCnt, 0, 1);
		String passRate = String.format("%.2f", (double)passCnt/applyCnt*100);
		dtUsrCnt.setValueAt(passRate+"%", 0, 2);
		int applierCnt = ibDBA.getSingleValueFromDB("SELECT COUNT(DISTINCT score_id) FROM itembank_scores");
		dtUsrCnt.setValueAt(applierCnt, 0, 3);
		int passerCnt = ibDBA.getSingleValueFromDB("SELECT COUNT(DISTINCT score_id) FROM itembank_scores WHERE (score_subject1+score_subject2+score_subject3+score_subject4+score_subject5)>=60");
		dtUsrCnt.setValueAt(passerCnt, 0, 4);
		String passerRate = String.format("%.2f", (double)passerCnt/applierCnt*100);
		dtUsrCnt.setValueAt(passerRate+"%", 0, 5);
		
		
		alignCenterColumnValue(tbUserCount);
	}
	
	public void printQuizCountTable() {
		/*********************************************************
		 * 점수에 대한 통계 수치를 테이블에 출력
		 *********************************************************/
		
		String[] tblQuizCntCols = {"데이터베이스", "전자계산기구조","운영체제", "소프트웨어공학", "데이터통신", "총점"};
		DefaultTableModel dtQuizCnt = new DefaultTableModel(tblQuizCntCols, 1);
		tbQuizCount.setModel(dtQuizCnt);
		
		
		int dbAvg = ibDBA.getSingleValueFromDB("SELECT AVG(score_subject1) FROM itembank_scores");
		dtQuizCnt.setValueAt(dbAvg, 0, 0);
		int calcAvg = ibDBA.getSingleValueFromDB("SELECT AVG(score_subject2) FROM itembank_scores");
		dtQuizCnt.setValueAt(calcAvg, 0, 1);
		int osAvg = ibDBA.getSingleValueFromDB("SELECT AVG(score_subject3) FROM itembank_scores");
		dtQuizCnt.setValueAt(osAvg, 0, 2);
		int swAvg = ibDBA.getSingleValueFromDB("SELECT AVG(score_subject4) FROM itembank_scores");
		dtQuizCnt.setValueAt(swAvg, 0, 3);
		int netAvg = ibDBA.getSingleValueFromDB("SELECT AVG(score_subject5) FROM itembank_scores");
		dtQuizCnt.setValueAt(netAvg, 0, 4);
		int totAvg = ibDBA.getSingleValueFromDB("SELECT AVG(score_subject1+score_subject2+score_subject3+score_subject4+score_subject5) FROM itembank_scores");
		dtQuizCnt.setValueAt(totAvg, 0, 5);
		
		alignCenterColumnValue(tbQuizCount);
	}
	
	private void searchUserMethod() {
		/********************************************************************
		 * 콤보박스 및 텡스트필드 값을 활용하여 사용자 검색 버튼을 눌렀을 때 검색을 위한 쿼리를 작성, 테이블 출력을 하는 메소드
		 *********************************************************************/
		
		String sql=null, strPrior=null, strPass=null, strKeyword=null;
		boolean opt = false;
		
		
		if(cbbPrior.getSelectedIndex()==1)
			strPrior = " user_prior='USER'";
		else if(cbbPrior.getSelectedIndex()==2)
			strPrior = " user_prior='ADMIN'";
		
		if(cbbPass.getSelectedIndex()!=0) 
			strPass = " user_pass='" + cbbPass.getSelectedItem() + "'";
		
		if(!tfUserSearch.getText().isEmpty()) {
			if(cbbUserSearch.getSelectedIndex()==0)
				strKeyword = " user_id LIKE '%" + tfUserSearch.getText() + "%'";
			else if(cbbUserSearch.getSelectedIndex()==1)
				strKeyword = " user_name LIKE '%" + tfUserSearch.getText() + "%'";
			else if(cbbUserSearch.getSelectedIndex()==2)
				strKeyword = " user_email LIKE '%" + tfUserSearch.getText() + "%'";
		}
		
		sql = "SELECT * FROM itembank_users";
		
		if(strPrior!=null || strPass!=null || strKeyword!=null) {
			sql += " WHERE";
		}
		
		if(strPrior!=null) {
			sql += strPrior;
			opt = true;
		}
		
		if(strPass!=null) {
			if(opt)
				sql += " AND";
			sql += strPass;
			opt = true;
		}
		
		if(strKeyword!=null) {
			if(opt)
				sql += " AND";
			sql += strKeyword;
		}
		
		System.out.println(sql);
		
		mUsrArr = ibDBA.getUserList(sql);
		printUserTable(mUsrArr);
		
		String status = "사용자 검색 - 권한:" + cbbPrior.getSelectedItem() + ", 합격여부:" 
						+ cbbPass.getSelectedItem() + ", 키워드:" + cbbUserSearch.getSelectedItem() 
						+ "-" + tfUserSearch.getText() + " 조건으로 검색되었습니다.";
		
		lbStatus.setText(status);
		
	}
	
	private void searchQuizMethod() {
		/********************************************************************
		 * 콤보박스 및 텡스트필드 값을 활용하여 문제 검색 버튼을 눌렀을 때 검색을 위한 쿼리를 작성, 테이블 출력을 하는 메소드
		 *********************************************************************/
		
		String sql = "SELECT * FROM itembank_quizzes";
		String groupOpt = null;
		String searchOpt = null;
		boolean opt = false;
		
		if(cbbGroup.getSelectedIndex()!=0) {
			groupOpt = " WHERE quiz_group='" + cbbGroup.getSelectedItem() + "'";
			opt = true;
			sql += groupOpt;
		}
			
		
		if(!tfQuizSearch.getText().isEmpty()) {
			if(!opt)
				sql += " WHERE";
			else
				sql += " AND";
			
			searchOpt = " quiz_quiz LIKE '%" + tfQuizSearch.getText() + "%'";
			sql += searchOpt;
		}
			
		sql += mQuizOrderOpt;
		
		System.out.println(sql);
		
		mQzArr = ibDBA.getQuizList(sql);
		printQuizTable(mQzArr);
		
		String str = "정렬:" + cbbSort.getSelectedItem() + ", 문제 유형:" + cbbGroup.getSelectedItem() 
					+ ", 키워드-" + tfUserSearch.getText() + " 조건으로 검색되었습니다.";
		
		lbStatus.setText(str);
	}
	
	private void setOrderOptStr(int opt) {
		/*************************************
		 * 옵션 번호에 따라 추가될 정렬 쿼리를 성정
		 *************************************/
		
		switch (opt) {
		case 0:	mQuizOrderOpt = " ORDER BY quiz_num"; break;
		case 1: mQuizOrderOpt = " ORDER BY quiz_quiz"; break;
		case 2: mQuizOrderOpt = " ORDER BY quiz_group";break;
		}	
	}
	

	public void refreshQuizList(){
		//리스트를 테이블로 출력
		searchQuizMethod();	
		//printQuizTable(mQzArr);
	}
	
	
	private JButton getBtnDelQuiz() {
		if (btnDelQuiz == null) {
			btnDelQuiz = new JButton("삭 제");
			btnDelQuiz.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {	
					
					if(2==JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.OK_CANCEL_OPTION))
						return;
					
					int delNum = mQzArr.get(mSelQuizRow).getSeqNum();
					//시퀀스 값으로 삭제
					int result = ibDBA.deleteQuiz(delNum);
					//리스트 재구성
					refreshQuizList();
					
					lbStatus.setText(delNum + "번 문제가 삭제되었습니다.");
						
					/////////////////////////////////////
				}
			});
			btnDelQuiz.setBounds(764, 10, 89, 38);
		}
		return btnDelQuiz;
	}
	
	private JButton getBtnQuizInit() {
		if (btnQuizInit == null) {
			btnQuizInit = new JButton("초기화");
			btnQuizInit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					cbbSort.setSelectedIndex(0);
					setOrderOptStr(cbbSort.getSelectedIndex());
					
					String sql = "SELECT * FROM itembank_quizzes" + mQuizOrderOpt;
					
					cbbGroup.setSelectedIndex(0);
					tfQuizSearch.setText("");
					
					System.out.println(sql);	
					mQzArr = ibDBA.getQuizList(sql);
					printQuizTable(mQzArr);
					
					lbStatus.setText("문제 검색 조건이 초기화되었습니다.");
				}
			});
			btnQuizInit.setBounds(777, 21, 74, 24);
		}
		return btnQuizInit;
	}
	
	private JButton getBtnUsrInit() {
		if (btnUsrInit == null) {
			btnUsrInit = new JButton("초기화");
			btnUsrInit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cbbPrior.setSelectedIndex(0);
					cbbPass.setSelectedIndex(0);
					tfUserSearch.setText("");
					
					mUsrArr = ibDBA.getUserList("SELECT * FROM itembank_users");
					printUserTable(mUsrArr);
					
					lbStatus.setText("사용자 검색 조건이 초기화되었습니다.");
				}
			});
			btnUsrInit.setBounds(777, 21, 74, 24);
		}
		return btnUsrInit;
	}
	
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setDividerSize(1);
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setLeftComponent(getTabbedPane());
			splitPane.setRightComponent(getPanel_3());
			splitPane.setDividerLocation(680);
		}
		return splitPane;
	}
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("사용자 관리", null, getSplitPane_1(), null);
			tabbedPane.addTab("문제 관리", null, getSplitPane_2(), null);
			tabbedPane.addTab(" 통  계 ", null, getSplitPane_6(), null);
		}
		return tabbedPane;
	}
	private JSplitPane getSplitPane_1() {
		if (splitPane_1 == null) {
			splitPane_1 = new JSplitPane();
			splitPane_1.setDividerSize(1);
			splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_1.setLeftComponent(getSplitPane_3());
			splitPane_1.setRightComponent(getPanel_2());
			splitPane_1.setDividerLocation(590);
		}
		return splitPane_1;
	}
	private JSplitPane getSplitPane_2() {
		if (splitPane_2 == null) {
			splitPane_2 = new JSplitPane();
			splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_2.setDividerSize(1);
			splitPane_2.setLeftComponent(getSplitPane_4());
			splitPane_2.setRightComponent(getPanel_4());
			splitPane_2.setDividerLocation(590);
		}
		return splitPane_2;
	}
	private JSplitPane getSplitPane_3() {
		if (splitPane_3 == null) {
			splitPane_3 = new JSplitPane();
			splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_3.setDividerSize(1);
			splitPane_3.setLeftComponent(getPanel_1());
			splitPane_3.setRightComponent(getSplitPane_5());
			splitPane_3.setDividerLocation(70);
		}
		return splitPane_3;
	}
	private JSplitPane getSplitPane_4() {
		if (splitPane_4 == null) {
			splitPane_4 = new JSplitPane();
			splitPane_4.setDividerSize(1);
			splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_4.setLeftComponent(getPanel());
			splitPane_4.setRightComponent(getScrollPane_1());
			splitPane_4.setDividerLocation(70);
		}
		return splitPane_4;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getCbbGroup());
			panel.add(getCbbSort());
			panel.add(getTfQuizSearch());
			panel.add(getBtnQuizSearch());
			panel.add(getLabel_1());
			panel.add(getLabel_2());
			panel.add(getLabel_4());
			panel.add(getBtnQuizInit());
		}
		return panel;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.add(getCbbPrior());
			panel_1.add(getCbbPass());
			panel_1.add(getCbbUserSearch());
			panel_1.add(getTfUserSearch());
			panel_1.add(getBtnUserSearch());
			panel_1.add(getLblNewLabel());
			panel_1.add(getLabel());
			panel_1.add(getLabel_3());
			panel_1.add(getBtnUsrInit());
		}
		return panel_1;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTbQuizzes());
		}
		return scrollPane_1;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.add(getBtnAddUsr());
		}
		return panel_2;
	}
	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.setFont(new Font("굴림", Font.ITALIC, 12));
			panel_3.setLayout(null);
			panel_3.add(getLbStatus());
		}
		return panel_3;
	}
	private JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.setLayout(null);
			panel_4.add(getBtnAddQuiz());
			panel_4.add(getBtnEditQuiz());
			panel_4.add(getBtnSimTest());
			panel_4.add(getBtnDelQuiz());
		}
		return panel_4;
	}
	private JTable getTbQuizzes() {
		if (tbQuizzes == null) {
			tbQuizzes = new JTable();
			tbQuizzes.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					mSelQuizRow = tbQuizzes.getSelectedRow();
					//테이블을 선택하면 해당 인덱스 값을 저장하여 문제를 편집하거나 삭제할때 사용.
				}
			});
			tbQuizzes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return tbQuizzes;
	}
	private JButton getBtnAddUsr() {
		if (btnAddUsr == null) {
			btnAddUsr = new JButton("추 가");
			btnAddUsr.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(mMngMemView==null)
						mMngMemView = new MemberJoinView(mUsr, mThisObj);
					//유저를 등록할때 호출. 인자에 권한을 위해 유저 클래스 및 등록 완료 후 테이블 갱신을 위해 해당 클래스를 전달.
					else
						mMngMemView.setVisible(true);
				}
			});
			btnAddUsr.setBounds(764, 10, 89, 38);
		}
		return btnAddUsr;
	}
	private JComboBox getCbbPrior() {
		if (cbbPrior == null) {
			cbbPrior = new JComboBox();
			cbbPrior.setBounds(52, 20, 120, 26);
		}
		return cbbPrior;
	}
	private JComboBox getCbbPass() {
		if (cbbPass == null) {
			cbbPass = new JComboBox();
			cbbPass.setBounds(226, 20, 111, 26);
		}
		return cbbPass;
	}
	private JComboBox getCbbUserSearch() {
		if (cbbUserSearch == null) {
			cbbUserSearch = new JComboBox();
			cbbUserSearch.setBounds(459, 20, 93, 26);
		}
		return cbbUserSearch;
	}
	private JTextField getTfUserSearch() {
		if (tfUserSearch == null) {
			tfUserSearch = new JTextField();
			tfUserSearch.setBounds(569, 21, 116, 26);
			tfUserSearch.setColumns(10);
		}
		return tfUserSearch;
	}
		
	private JButton getBtnUserSearch() {
		if (btnUserSearch == null) {
			btnUserSearch = new JButton("검 색");
			btnUserSearch.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					searchUserMethod();
					//검색 쿼리 작성 및 출력을 위한 메소드 호출
				}
				
			});
			btnUserSearch.setBounds(697, 21, 68, 24);
		}
		return btnUserSearch;
	}
	private JComboBox getCbbGroup() {
		if (cbbGroup == null) {
			cbbGroup = new JComboBox();
			cbbGroup.setBounds(344, 19, 120, 26);
		}
		return cbbGroup;
	}
	private JComboBox getCbbSort() {
		if (cbbSort == null) {
			cbbSort = new JComboBox();
			cbbSort.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					////////////////////////////////////////////
					//정렬값 변경되었을 때....
					//정렬 콤보박스인 경우 상태 변경시 바로 정렬하도록 구현.
					setOrderOptStr(cbbSort.getSelectedIndex());
					
					String sql = "SELECT * FROM itembank_quizzes" + mQuizOrderOpt;
					mQzArr = ibDBA.getQuizList(sql);
					printQuizTable(mQzArr);
					
					lbStatus.setText(cbbSort.getSelectedItem() + " 조건으로 정렬되었습니다.");
					////////////////////////////////////////////
				
				}
			});
			cbbSort.setBounds(51, 20, 111, 26);
		}
		return cbbSort;
	}
	
	
	
	private JTextField getTfQuizSearch() {
		if (tfQuizSearch == null) {
			tfQuizSearch = new JTextField();
			tfQuizSearch.setColumns(10);
			tfQuizSearch.setBounds(569, 21, 116, 26);
		}
		return tfQuizSearch;
	}
	private JButton getBtnQuizSearch() {
		if (btnQuizSearch == null) {
			btnQuizSearch = new JButton("검 색");
			btnQuizSearch.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					////////////////////////////////////////
					searchQuizMethod();
					///////////////////////////////////////
				}
			});
			btnQuizSearch.setBounds(697, 21, 68, 24);
		}
		return btnQuizSearch;
	}
	private JButton getBtnAddQuiz() {
		if (btnAddQuiz == null) {
			btnAddQuiz = new JButton("추 가");
			btnAddQuiz.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(mRegQuizView==null)		//단일 객체 생성
						mRegQuizView = new RegQuizView(null, mThisObj);
					else {
						mRegQuizView.ClearQuiz();
						mRegQuizView.setVisible(true);
					}
				}
			});
			btnAddQuiz.setBounds(663, 10, 89, 38);
		}
		return btnAddQuiz;
	}
	private JButton getBtnEditQuiz() {
		if (btnEditQuiz == null) {
			btnEditQuiz = new JButton("편 집");
			btnEditQuiz.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					/////////////////////////////////
					if(mRegQuizView==null)
						mRegQuizView = new RegQuizView(mQzArr.get(mSelQuizRow), mThisObj);
					else {
						mRegQuizView.diplayQuiz(mQzArr.get(mSelQuizRow));			//확인 필요
						mRegQuizView.setVisible(true);
					}
					
					////////////////////////////////
				}
			});
			btnEditQuiz.setBounds(562, 10, 89, 38);
		}
		return btnEditQuiz;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("권한 :");
			lblNewLabel.setBounds(12, 26, 44, 15);
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("합격 :");
			label.setBounds(184, 26, 44, 15);
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("유형 :");
			label_1.setBounds(298, 25, 44, 15);
		}
		return label_1;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("정렬 :");
			label_2.setBounds(12, 26, 44, 15);
		}
		return label_2;
	}
	private JButton getBtnSimTest() {
		if (btnSimTest == null) {
			btnSimTest = new JButton("모의 테스트");
			btnSimTest.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					/////////////////////////////////////
					////////////////////////////////////	
					new TestPageView(mUsr);
					lbStatus.setText("모의 테스트가 시작되었습니다.");
				}
			});
			btnSimTest.setBounds(12, 10, 115, 38);
		}
		return btnSimTest;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("키워드 :");
			label_3.setHorizontalAlignment(SwingConstants.RIGHT);
			label_3.setBounds(388, 26, 59, 15);
		}
		return label_3;
	}
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("문제 키워드 :");
			label_4.setHorizontalAlignment(SwingConstants.RIGHT);
			label_4.setBounds(483, 25, 74, 15);
		}
		return label_4;
	}
	
	private JSplitPane getSplitPane_5() {
		if (splitPane_5 == null) {
			splitPane_5 = new JSplitPane();
			splitPane_5.setDividerSize(1);
			splitPane_5.setLeftComponent(getScrollPane_2());
			splitPane_5.setRightComponent(getScrollPane_2_1());
			splitPane_5.setDividerLocation(350);
		}
		return splitPane_5;
	}
	private JScrollPane getScrollPane_2() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			//scrollPane.setViewportView(getTnUserss());
			scrollPane.setViewportView(getTbUsers());
		}
		return scrollPane;
	}
	private JScrollPane getScrollPane_2_1() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setViewportView(getTbUserScore());
		}
		return scrollPane_2;
	}
	private JTable getTnUsers() {
		if (tnUserss == null) {
			tnUserss = new JTable();
			tnUserss.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					mSelUsrRow = tbUsers.getSelectedRow();
				}
			});
		}
		return tnUserss;
	}
	private JTable getTbUserScore() {
		if (tbUserScore == null) {
			tbUserScore = new JTable();
		}
		return tbUserScore;
	}
	private JTable getTbUsers() {
		if (tbUsers == null) {
			tbUsers = new JTable();
			tbUsers.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					mSelUsrRow = tbUsers.getSelectedRow();
					String sql = "SELECT * FROM itembank_scores WHERE score_id='" + mUsrArr.get(mSelUsrRow).getId() + "'"; 
					ArrayList<Scores> scr = ibDBA.getUserScoreList(sql);
					printUserScoreTable(scr);
				}
			});
		}
		return tbUsers;
	}

	public void refreshUserList() {
			//리스트를 테이블로 출력
			searchUserMethod();
		
			//printUserTable(mUsrArr);
	}
	private JSplitPane getSplitPane_6() {
		if (splitPane_6 == null) {
			splitPane_6 = new JSplitPane();
			splitPane_6.setDividerSize(1);
			splitPane_6.setLeftComponent(getScrollPane_3());
			splitPane_6.setRightComponent(getPanel_5());
			splitPane_6.setDividerLocation(500);
		}
		return splitPane_6;
	}
	private JScrollPane getScrollPane_3() {
		if (scrollPane_3 == null) {
			scrollPane_3 = new JScrollPane();
			scrollPane_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uC810\uC218 \uD14C\uC774\uBE14", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(128, 0, 0)));
			scrollPane_3.setViewportView(getTbScores());
		}
		return scrollPane_3;
	}
	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.setLayout(null);
			panel_5.add(getBtnRefresh());
			panel_5.add(getScrollPane_4());
			panel_5.add(getScrollPane_5());
		}
		return panel_5;
	}
	private JTable getTbScores() {
		if (tbScores == null) {
			tbScores = new JTable();
		}
		return tbScores;
	}
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton("갱 신");
			btnRefresh.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//////////////////////////////////////////////////////////////
					
					String sql = "SELECT * FROM itembank_scores ORDER BY score_num";
					mScrArr = ibDBA.getScoreList(sql);
					
					printScoreTable(mScrArr);
					
					printUserCountTable();
					printQuizCountTable();
					
					lbStatus.setText("시험 결과를 최신 정보로 갱신하였습니다.");
					////////////////////////////////////////////////////////////
					
					////////////////////////////////////////////////////////////
					
				}
			});
			btnRefresh.setBounds(274, 603, 79, 35);
		}
		return btnRefresh;
	}
	private JScrollPane getScrollPane_4() {
		if (scrollPane_4 == null) {
			scrollPane_4 = new JScrollPane();
			scrollPane_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uD569\uACA9\uB960", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 128, 128)));
			scrollPane_4.setBounds(12, 36, 341, 63);
			scrollPane_4.setViewportView(getTbUserCount());
		}
		return scrollPane_4;
	}
	private JTable getTbUserCount() {
		if (tbUserCount == null) {
			tbUserCount = new JTable();
		}
		return tbUserCount;
	}
	private JScrollPane getScrollPane_5() {
		if (scrollPane_5 == null) {
			scrollPane_5 = new JScrollPane();
			scrollPane_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uD3C9\uADE0 \uC810\uC218", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 128, 0)));
			scrollPane_5.setBounds(12, 137, 341, 63);
			scrollPane_5.setViewportView(getTbQuizCount());
		}
		return scrollPane_5;
	}
	private JTable getTbQuizCount() {
		if (tbQuizCount == null) {
			tbQuizCount = new JTable();
		}
		return tbQuizCount;
	}
	private JLabel getLbStatus() {
		if (lbStatus == null) {
			lbStatus = new JLabel("");
			lbStatus.setFont(new Font("굴림체", Font.BOLD | Font.ITALIC, 13));
			lbStatus.setBounds(0, 0, 872, 20);
		}
		return lbStatus;
	}
	
	public void setStatusLabel(String msg) {
		lbStatus.setText(msg);
	}
}
