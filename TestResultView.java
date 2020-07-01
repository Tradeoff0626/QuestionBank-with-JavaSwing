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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestResultView extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tbSubEval;
	private JScrollPane scrollPane_1;
	private JTable tbTotalEval;
	private JLabel lbName;
	private JButton btnOK;
	
	private Users mUsr;
	private ItembankDBA ibDBA;

	private ArrayList<Quizzes> quizList;
	private Integer[] answerList;
	private JButton btnRecvSol;
	
	private boolean mSolutionRecv;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	TestResultView frame = new TestResultView();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	/*
	public TestResultView() {
		setTitle("테스트 결과");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 527);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		contentPane.add(getScrollPane());
		contentPane.add(getScrollPane_1());
	}
	*/
	
	public TestResultView(Users usr, ArrayList<Integer> score, ArrayList<Quizzes> qList, Integer[] rAnswerList) {
		setTitle("테스트 결과");

		if(usr.getPriority().equals("ADMIN"))
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		else
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 485, 432);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getScrollPane());
		contentPane.add(getScrollPane_1());
		contentPane.add(getLbName());

		mSolutionRecv=false;
		
		quizList = qList;
		answerList = rAnswerList;
		
		
		this.mUsr = usr;
		ibDBA = new ItembankDBA();
		
		if(usr==null) {
			usr = new Users();
			usr.setName("테스터");
		}
			
		
		lbName.setText("'" + usr.getName() + "'님의 시험 결과입니다.");
		
		int[] subjectsScore = new int[5];
		int totScore=0;
		
		for(int i=0; i<5; i++) {
			subjectsScore[i] = score.get(i).intValue();
			totScore += subjectsScore[i];
		}
		
		String subState = "합격";
		String totState = "합격";
		
		for(int i=0; i<score.size(); i++) {
			if(score.get(i).intValue()<8)
				subState="과락";
		}
		if(totScore<60)
			totState="불합격";
		
		if(mUsr.getPriority().equals("USER"))
			ibDBA.UpdateUserInfo(usr, totState);
		
		
		String subjects[] = {"데이터베이스", "전자계산기구조", "운영체제", "소프트웨어공학", "데이터통신"};
		String subEvalCols[] = {"과목명", "정답/문제수", "점수", "합격 여부"};
		String totalEvalCols[] = {"총점", "평가", "합격 여부"};
		
		DefaultTableModel dtSubEval = new DefaultTableModel(subEvalCols, 5);
		DefaultTableModel dtTotalEval = new DefaultTableModel(totalEvalCols, 1);
		
		
		tbSubEval.setModel(dtSubEval);
		tbTotalEval.setModel(dtTotalEval);
		contentPane.add(getLbName());
		contentPane.add(getBtnOK());
		contentPane.add(getBtnRecvSol());
		
		for(int i=0; i<5; i++) {
			dtSubEval.setValueAt(subjects[i], i, 0);
			dtSubEval.setValueAt(subjectsScore[i]+"/20", i, 1);
			dtSubEval.setValueAt(subjectsScore[i]*5, i, 2);
			dtSubEval.setValueAt(subjectsScore[i]>7 ? "합격" : "과락", i, 3);
		}
		
		dtTotalEval.setValueAt(totScore, 0, 0);
		dtTotalEval.setValueAt(subState, 0, 1);
		dtTotalEval.setValueAt(totState, 0, 2);
		
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = tbSubEval.getColumnModel();
		
		for(int i=0; i<tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
		
		tcm = tbTotalEval.getColumnModel();

		for(int i=0; i<tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
		
		
		setVisible(true);
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
			scrollPane.setBounds(37, 85, 407, 108);
			scrollPane.setViewportView(getTbSubEval());
		}
		return scrollPane;
	}
	private JTable getTbSubEval() {
		if (tbSubEval == null) {
			tbSubEval = new JTable();
			tbSubEval.setRequestFocusEnabled(false);
			tbSubEval.setEnabled(false);
			tbSubEval.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			tbSubEval.setAutoscrolls(false);
			tbSubEval.setRowSelectionAllowed(false);
		}
		return tbSubEval;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBorder(new EmptyBorder(0, 0, 0, 0));
			scrollPane_1.setBounds(37, 235, 407, 44);
			scrollPane_1.setViewportView(getTbTotalEval());
		}
		return scrollPane_1;
	}
	private JTable getTbTotalEval() {
		if (tbTotalEval == null) {
			tbTotalEval = new JTable();
			tbTotalEval.setRequestFocusEnabled(false);
			tbTotalEval.setEnabled(false);
			tbTotalEval.setBorder(new EmptyBorder(0, 0, 0, 0));
			tbTotalEval.setAutoscrolls(false);
		}
		return tbTotalEval;
	}
	private JLabel getLbName() {
		if (lbName == null) {
			lbName = new JLabel("님의 테스트 결과입니다.");
			lbName.setBounds(37, 31, 234, 23);
		}
		return lbName;
	}
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton("확 인");
			btnOK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					
					if(mUsr.getPriority().equals("ADMIN"))
						dispose();
					else {
						if(!mSolutionRecv) {
							if(2==JOptionPane.showConfirmDialog(null, "풀이집을 다운로드하지 않았습니다. 종료하시겠습니까?", "종료 확인", JOptionPane.OK_CANCEL_OPTION))
								return;		//종료 취소한 경우.
						}
						System.exit(0);
					}	
					
					
				}
			});
			btnOK.setBounds(188, 354, 97, 30);
		}
		return btnOK;
	}
	private JButton getBtnRecvSol() {
		if (btnRecvSol == null) {
			btnRecvSol = new JButton("풀이 받기");
			btnRecvSol.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					new MakeSolvePage(quizList, answerList);
					
					JOptionPane.showConfirmDialog(null, "다운로드 완료되었습니다.", "풀이집 다운로드", JOptionPane.DEFAULT_OPTION);
					mSolutionRecv = true;
				}
			});
			btnRecvSol.setBounds(349, 356, 97, 27);
		}
		return btnRecvSol;
	}
}
