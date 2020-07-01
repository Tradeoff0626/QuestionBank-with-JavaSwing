package itembank;

import itembank.ItembankDBA;
import itembank.Quizzes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSeparator;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

import java.awt.SystemColor;

import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.ListSelectionModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
//import javax.swing.Timer;

public class TestPageView extends JFrame {

	private JPanel contentPane;
	private JSplitPane splitPane;
	private JSplitPane splitPane_1;
	private JSplitPane splitPane_2;
	private JSplitPane splitPane_3;
	private JSplitPane splitPane_4;
	private JSplitPane splitPane_5;
	private JSplitPane splitPane_6;
	private JPanel panel;
	private JLabel lbTime;
	private JLabel lbRemainTimeMin;

	private Timer mRemainTimer;
	private int mReaminTime;
	private TimerEvent task;
	private JLabel lbName;
	private JLabel lbSubject;
	private JButton btnPrevQuest;
	private JButton btnNextQuest;
	private JPanel panel_1;
	private JButton btnTestConfirm;
	private JSplitPane splitPane_7;
	private JSplitPane splitPane_12;
	private JSplitPane splitPane_13;
	private JLabel lbQuizPic;
	private JLabel lbExamPic;
	private JScrollPane answerScrollPane;
	private JTable tbProgress;
	private JLabel lblNewLabel;
	private JPanel panel_2;
	private JRadioButton rdbtnExam1;
	private JRadioButton rdbtnExam2;
	private JRadioButton rdbtnExam3;
	private JRadioButton rdbtnExam4;
	private JScrollPane scrollPane_1;
	private JTextArea taQuestion;
	private JTextArea taExam1;
	private JTextArea taExam2;
	private JTextArea taExam3;
	private JTextArea taExam4;
	private ButtonGroup examGroup;
	
	private ArrayList<Quizzes> quizList;
	private Integer[] answerList;
	private ItembankDBA ibDBA;
	private int mQuizIndex;
	
	private DefaultTableModel dtQuest;
	private JLabel lbRemainTimeSec;
	private JLabel lbGroup;
	private JLabel lbRightAnswer;
	private JLabel label;
	
	private Users mUsr;
	private JLabel lbUsrName;
	private JLabel lbPic;
	
	private final int TESTTIME= 150;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestPageView frame = new TestPageView();
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
	public TestPageView() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 969, 763);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getSplitPane(), BorderLayout.CENTER);
	
		mRemainTimer = new Timer();
		mReaminTime = 60 * 150;					//시험 시간 설정
		task = new TimerEvent();
		mRemainTimer.schedule(task, 1000);
		mRemainTimer.schedule(task, 1000, 1000);
		
		mQuizIndex=0;
		
		examGroup = new ButtonGroup();
		examGroup.add(rdbtnExam1);
		examGroup.add(rdbtnExam2);
		examGroup.add(rdbtnExam3);
		examGroup.add(rdbtnExam4);
		
		quizList = new ArrayList<Quizzes>();
		ibDBA = new ItembankDBA();
		answerList = new Integer[100];
		
		for(int i=0; i<100; i++)
			answerList[i]=0;
		
		ProgressTable();
		
		lbRightAnswer.setVisible(true);
		
		makeQuizList();
		displayQuiz(mQuizIndex);
		
		setVisible(true);
	}
	
	public TestPageView(Users usr) {
		setAlwaysOnTop(true);
			
		if(usr.getPriority().equals("ADMIN"))
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		else
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 969, 763);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getSplitPane(), BorderLayout.CENTER);
	
		this.mUsr = usr;
		lbUsrName.setText(mUsr.getName());
		
		lbPic.setIcon(new ImageIcon("img03.jpg"));
		
		mRemainTimer = new Timer();
		mReaminTime = 60 * TESTTIME;					//시험 시간 설정
		task = new TimerEvent();
		mRemainTimer.schedule(task, 1000);
		
		mQuizIndex=0;
		
		examGroup = new ButtonGroup();
		examGroup.add(rdbtnExam1);
		examGroup.add(rdbtnExam2);
		examGroup.add(rdbtnExam3);
		examGroup.add(rdbtnExam4);
		
		quizList = new ArrayList<Quizzes>();
		ibDBA = new ItembankDBA();
		answerList = new Integer[100];
		
		for(int i=0; i<100; i++)
			answerList[i]=0;
		
		ProgressTable();
		
		if(mUsr.getPriority().equals("ADMIN"))
			lbRightAnswer.setVisible(true);
		
		makeQuizList();
		displayQuiz(mQuizIndex);
		
		setVisible(true);
	}
	
	public void makeQuizList() {
		//quizList = ibDBA.mkQuizList()
		quizList = ibDBA.getTestQuizList();
		//quizList = ibDBA.getQuizList("SELECT * FROM itembank_quizzes");
	}
	
	public void displayQuiz(int i) {
		lbQuizPic.setIcon(null);
		lbExamPic.setIcon(null);
		
		taQuestion.setText(mQuizIndex+1 + ". " + quizList.get(i).getQuiz());
		if(quizList.get(i).getQuizImg()!=null)
			lbQuizPic.setIcon(new ImageIcon(quizList.get(i).getQuizImg()));
		taExam1.setText(quizList.get(i).getExam1());
		taExam2.setText(quizList.get(i).getExam2());
		taExam3.setText(quizList.get(i).getExam3());
		taExam4.setText(quizList.get(i).getExam4());
		
		lbGroup.setText(quizList.get(i).getGroup());
		//lbRightAnswer.setText(String.valueOf(quizList.get(i).getAnswer()));
		
		if(quizList.get(i).getExamImg()!=null)
			lbExamPic.setIcon(new ImageIcon(quizList.get(i).getExamImg()));
	}
	
	public void ProgressTable() {
		String[] tblProgressCols = {"번호", "풀이"};
		dtQuest = new DefaultTableModel(tblProgressCols, 100);
		tbProgress.setModel(dtQuest);
		
		
		for(int i=0; i<100; i++) {
			dtQuest.setValueAt(i+1, i, 0);
			if(answerList[i] > 0)
				//dtQuest.setValueAt(answerList[i], i, 1);
				dtQuest.setValueAt("●", i, 1);
		}
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = tbProgress.getColumnModel();
		
		for(int i=0; i<tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
	}

	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setDividerSize(1);
			splitPane.setRightComponent(getSplitPane_1());
			splitPane.setLeftComponent(getSplitPane_3());
			splitPane.setDividerLocation(850);
		}
		return splitPane;
	}
	private JSplitPane getSplitPane_1() {
		if (splitPane_1 == null) {
			splitPane_1 = new JSplitPane();
			splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_1.setDividerSize(1);
			splitPane_1.setLeftComponent(getSplitPane_2());
			splitPane_1.setRightComponent(getBtnTestConfirm());
			splitPane_1.setDividerLocation(660);
		}
		return splitPane_1;
	}
	private JSplitPane getSplitPane_2() {
		if (splitPane_2 == null) {
			splitPane_2 = new JSplitPane();
			splitPane_2.setDividerSize(1);
			splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_2.setRightComponent(getScrollPane_2());
			splitPane_2.setLeftComponent(getLblNewLabel());
			splitPane_2.setDividerLocation(50);
		}
		return splitPane_2;
	}
	private JSplitPane getSplitPane_3() {
		if (splitPane_3 == null) {
			splitPane_3 = new JSplitPane();
			splitPane_3.setDividerSize(1);
			splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_3.setLeftComponent(getSplitPane_4());
			splitPane_3.setRightComponent(getPanel_1());
			splitPane_3.setDividerLocation(660);
		}
		return splitPane_3;
	}
	private JSplitPane getSplitPane_4() {
		if (splitPane_4 == null) {
			splitPane_4 = new JSplitPane();
			splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_4.setDividerSize(1);
			splitPane_4.setRightComponent(getSplitPane_5());
			splitPane_4.setLeftComponent(getPanel());
			splitPane_4.setDividerLocation(100);
		}
		return splitPane_4;
	}
	private JSplitPane getSplitPane_5() {
		if (splitPane_5 == null) {
			splitPane_5 = new JSplitPane();
			splitPane_5.setDividerSize(1);
			splitPane_5.setRightComponent(getSplitPane_6());
			splitPane_5.setLeftComponent(getBtnPrevQuest());
			splitPane_5.setDividerLocation(50);
		}
		return splitPane_5;
	}
	private JSplitPane getSplitPane_6() {
		if (splitPane_6 == null) {
			splitPane_6 = new JSplitPane();
			splitPane_6.setDividerSize(1);
			splitPane_6.setRightComponent(getBtnNextQuest());
			splitPane_6.setLeftComponent(getSplitPane_7());
			splitPane_6.setDividerLocation(740);
		}
		return splitPane_6;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getLbTime());
			panel.add(getLbRemainTimeMin());
			panel.add(getLbName());
			panel.add(getLbSubject());
			panel.add(getLbRemainTimeSec());
			panel.add(getLabel());
			panel.add(getLbUsrName());
			panel.add(getLbPic());
		}
		return panel;
	}
	private JLabel getLbTime() {
		if (lbTime == null) {
			lbTime = new JLabel("남은 시간 :");
			lbTime.setHorizontalAlignment(SwingConstants.RIGHT);
			lbTime.setBounds(24, 69, 69, 15);
		}
		return lbTime;
	}
	private JLabel getLbRemainTimeMin() {
		if (lbRemainTimeMin == null) {
			lbRemainTimeMin = new JLabel("분");
			lbRemainTimeMin.setForeground(new Color(255, 0, 0));
			lbRemainTimeMin.setHorizontalAlignment(SwingConstants.RIGHT);
			lbRemainTimeMin.setBounds(105, 69, 53, 15);
		}
		return lbRemainTimeMin;
	}
	
	class TimerEvent extends TimerTask {
		@Override
		public void run() {
			mReaminTime--;
			
			String fmtMin = mReaminTime/60 + "분 ";
			String fmtSec = mReaminTime%60 + "초";
			
			lbRemainTimeMin.setText(fmtMin);
			lbRemainTimeSec.setText(fmtSec);
			
			mRemainTimer = new Timer();
			task = new TimerEvent();
			mRemainTimer.schedule(task, 1000);
			
			if(mReaminTime==0)
				testComplete();	//시험 종료 처리 루틴 실행
		}
	}
	private JLabel getLbName() {
		if (lbName == null) {
			lbName = new JLabel("이 름 :");
			lbName.setHorizontalAlignment(SwingConstants.RIGHT);
			lbName.setBounds(24, 13, 69, 15);
		}
		return lbName;
	}
	private JLabel getLbSubject() {
		if (lbSubject == null) {
			lbSubject = new JLabel("응시 유형 :");
			lbSubject.setHorizontalAlignment(SwingConstants.RIGHT);
			lbSubject.setBounds(24, 41, 69, 15);
		}
		return lbSubject;
	}
	private JButton getBtnPrevQuest() {
		if (btnPrevQuest == null) {
			btnPrevQuest = new JButton("<<");
			btnPrevQuest.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(mQuizIndex==0)
						return;
					
					//answerList[mQuizIndex] =  getAnswerByButton();
					mQuizIndex--;
					
					displayQuiz(mQuizIndex);
					ProgressTable();
					
					if(mQuizIndex>17 && mQuizIndex<83)
						answerScrollPane.getVerticalScrollBar().setValue(answerScrollPane.getVerticalScrollBar().getMaximum()/quizList.size()*(mQuizIndex-18));
					
					
					if(answerList[mQuizIndex] > 0)
						setAnswerButton(answerList[mQuizIndex]);
					else
						examGroup.clearSelection();
				}
			});
			
		}
		return btnPrevQuest;
	}
	private JButton getBtnNextQuest() {
		if (btnNextQuest == null) {
			btnNextQuest = new JButton(">>");
			
			btnNextQuest.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(mQuizIndex==99)
						return;
					
					//answerList[mQuizIndex] =  getAnswerByButton();
					mQuizIndex++;
					
					displayQuiz(mQuizIndex);	
					ProgressTable();
					if(mQuizIndex>17 && mQuizIndex<83)
						answerScrollPane.getVerticalScrollBar().setValue(answerScrollPane.getVerticalScrollBar().getMaximum()/quizList.size()*(mQuizIndex-18));
					
					
					if(answerList[mQuizIndex] > 0)
						setAnswerButton(answerList[mQuizIndex]);
					else 
						examGroup.clearSelection();
				}
			});
		}
		return btnNextQuest;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.add(getLbGroup());
			panel_1.add(getLbRightAnswer());
		}
		return panel_1;
	}
	private JButton getBtnTestConfirm() {
		if (btnTestConfirm == null) {
			btnTestConfirm = new JButton("시험 완료");
			btnTestConfirm.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String msg = "";
					
					for(int i=0; i<answerList.length; i++) {
						if(answerList[i]==0) {
							msg = "작성하지 않은 답안이 있습니다. ";
							break;
						}
					}
					msg += "시험을 종료하시겠습니까?";
					
					int choice = JOptionPane.showConfirmDialog(getPanel(), msg, "시험 종료 학인", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

					if(choice==0) {
						
						testComplete();
					}
					//////////////////////////////////////////////
					///////////////////////////////////////////////
					//////////////////////////////////////////////
				}
			});
		}
		return btnTestConfirm;
	}
	
	private void testComplete() {
		ArrayList<Integer> score = new ArrayList<Integer>();
		
		int quizCnt = 20;
		
		for(int i=0, right=0; i<quizList.size(); i+=quizCnt) {
			for(int j=0; j<quizCnt; j++) {
				if(quizList.get(i+j).getAnswer()==answerList[i+j])
					right++;
			}
			score.add(right);
			right=0;
		}
		
		//사용자일 경우만 카운트하도록 변경
		if(mUsr.getPriority().equals("USER")) {
			ibDBA.updateQuizCount(quizList, answerList);
			ibDBA.addScore(mUsr.getId(), score);
		}
		
		new TestResultView(mUsr, score, quizList, answerList);
		
		dispose();
		
		return;
	}
	
	private JSplitPane getSplitPane_7() {
		if (splitPane_7 == null) {
			splitPane_7 = new JSplitPane();
			splitPane_7.setDividerSize(1);
			splitPane_7.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_7.setLeftComponent(getSplitPane_12());
			splitPane_7.setRightComponent(getSplitPane_13());
			splitPane_7.setDividerLocation(250);
		}
		return splitPane_7;
	}
	private JSplitPane getSplitPane_12() {
		if (splitPane_12 == null) {
			splitPane_12 = new JSplitPane();
			splitPane_12.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uBB38\uC81C", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 128, 0)));
			splitPane_12.setDividerSize(1);
			splitPane_12.setRightComponent(getLbQuizPic());
			splitPane_12.setLeftComponent(getScrollPane_1());
			splitPane_12.setDividerLocation(450);
		}
		return splitPane_12;
	}
	private JSplitPane getSplitPane_13() {
		if (splitPane_13 == null) {
			splitPane_13 = new JSplitPane();
			splitPane_13.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uBCF4\uAE30", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 255)));
			splitPane_13.setDividerSize(1);
			splitPane_13.setRightComponent(getLbExamPic());
			splitPane_13.setLeftComponent(getPanel_2());
			splitPane_13.setDividerLocation(450);
		}
		return splitPane_13;
	}
	private JLabel getLbQuizPic() {
		if (lbQuizPic == null) {
			lbQuizPic = new JLabel("");
			lbQuizPic.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbQuizPic;
	}
	private JLabel getLbExamPic() {
		if (lbExamPic == null) {
			lbExamPic = new JLabel("");
			lbExamPic.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbExamPic;
	}
	private JScrollPane getScrollPane_2() {
		if (answerScrollPane == null) {
			answerScrollPane = new JScrollPane();
			answerScrollPane.setViewportView(getTable_2());
		}
		return answerScrollPane;
	}
	private JTable getTable_2() {
		if (tbProgress == null) {
			tbProgress = new JTable();
			tbProgress.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tbProgress.setRequestFocusEnabled(false);
			tbProgress.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//System.out.println(tbProgress.getSelectedRow());

					mQuizIndex=tbProgress.getSelectedRow();
					
					displayQuiz(mQuizIndex);	
					ProgressTable();
					
					if(answerList[mQuizIndex] > 0)
						setAnswerButton(answerList[mQuizIndex]);
					else 
						examGroup.clearSelection();
					
				}
			});
		}
		return tbProgress;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("진행 상황");
			lblNewLabel.setForeground(Color.RED);
			lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabel;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.add(getRdbtnExam1());
			panel_2.add(getRdbtnExam2());
			panel_2.add(getRdbtnExam3());
			panel_2.add(getRdbtnExam4());
			panel_2.add(getTaExam1());
			panel_2.add(getTaExam2());
			panel_2.add(getTaExam3());
			panel_2.add(getTaExam4());
		}
		return panel_2;
	}
	private JRadioButton getRdbtnExam1() {
		if (rdbtnExam1 == null) {
			rdbtnExam1 = new JRadioButton("가.");
			rdbtnExam1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					answerList[mQuizIndex] =  getAnswerByButton();
					ProgressTable();
				}
			});
			rdbtnExam1.setBounds(8, 14, 47, 23);
		}
		return rdbtnExam1;
	}
	private JRadioButton getRdbtnExam2() {
		if (rdbtnExam2 == null) {
			rdbtnExam2 = new JRadioButton("나.");
			rdbtnExam2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					answerList[mQuizIndex] =  getAnswerByButton();
					ProgressTable();
				}
			});
			rdbtnExam2.setBounds(8, 82, 47, 23);
		}
		return rdbtnExam2;
	}
	private JRadioButton getRdbtnExam3() {
		if (rdbtnExam3 == null) {
			rdbtnExam3 = new JRadioButton("다.");
			rdbtnExam3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					answerList[mQuizIndex] =  getAnswerByButton();
					ProgressTable();
				}
			});
			rdbtnExam3.setBounds(8, 150, 47, 23);
		}
		return rdbtnExam3;
	}
	private JRadioButton getRdbtnExam4() {
		if (rdbtnExam4 == null) {
			rdbtnExam4 = new JRadioButton("라.");
			rdbtnExam4.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					answerList[mQuizIndex] =  getAnswerByButton();
					ProgressTable();
				}
			});
			rdbtnExam4.setActionCommand("라.");
			rdbtnExam4.setBounds(8, 218, 47, 23);
		}
		return rdbtnExam4;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTaQuestion());
		}
		return scrollPane_1;
	}
	private JTextArea getTaQuestion() {
		if (taQuestion == null) {
			taQuestion = new JTextArea();
			taQuestion.setFont(new Font("Monospaced", Font.BOLD, 13));
			taQuestion.setEditable(false);
			taQuestion.setSelectionColor(SystemColor.textHighlight);
			taQuestion.setBackground(SystemColor.control);
			taQuestion.setLineWrap(true);
		}
		return taQuestion;
	}
	private JTextArea getTaExam1() {
		if (taExam1 == null) {
			taExam1 = new JTextArea();
			taExam1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					rdbtnExam1.setSelected(true);
					answerList[mQuizIndex] =  getAnswerByButton();
					ProgressTable();
				}
			});
			taExam1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			taExam1.setEditable(false);
			taExam1.setLineWrap(true);
			taExam1.setBackground(SystemColor.control);
			taExam1.setBounds(55, 14, 382, 54);
		}
		return taExam1;
	}
	private JTextArea getTaExam2() {
		if (taExam2 == null) {
			taExam2 = new JTextArea();
			taExam2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					rdbtnExam2.setSelected(true);
					answerList[mQuizIndex] =  getAnswerByButton();
					ProgressTable();
				}
			});
			taExam2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			taExam2.setEditable(false);
			taExam2.setLineWrap(true);
			taExam2.setBackground(SystemColor.control);
			taExam2.setBounds(55, 82, 382, 54);
		}
		return taExam2;
	}
	private JTextArea getTaExam3() {
		if (taExam3 == null) {
			taExam3 = new JTextArea();
			taExam3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					rdbtnExam3.setSelected(true);
					answerList[mQuizIndex] =  getAnswerByButton();
					ProgressTable();
				}
			});
			taExam3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			taExam3.setEditable(false);
			taExam3.setLineWrap(true);
			taExam3.setBackground(SystemColor.control);
			taExam3.setBounds(55, 150, 382, 54);
		}
		return taExam3;
	}
	private JTextArea getTaExam4() {
		if (taExam4 == null) {
			taExam4 = new JTextArea();
			taExam4.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					rdbtnExam4.setSelected(true);
					answerList[mQuizIndex] =  getAnswerByButton();
					ProgressTable();
				}
			});
			taExam4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			taExam4.setEditable(false);
			taExam4.setLineWrap(true);
			taExam4.setBackground(SystemColor.control);
			taExam4.setBounds(55, 218, 382, 54);
		}
		return taExam4;
	}
	
	private int getAnswerByButton() {
		if(rdbtnExam1.isSelected())
			return 1;
		if(rdbtnExam2.isSelected())
			return 2;
		if(rdbtnExam3.isSelected())
			return 3;
		if(rdbtnExam4.isSelected())
			return 4;
		
		return 0;
	}
	
	private void setAnswerButton(int s) {
		switch (s){
			case 1: rdbtnExam1.setSelected(true); break;
			case 2: rdbtnExam2.setSelected(true); break;
			case 3: rdbtnExam3.setSelected(true); break;
			case 4: rdbtnExam4.setSelected(true); break;
		}
		
		System.out.println(s);
	}
	private JLabel getLbRemainTimeSec() {
		if (lbRemainTimeSec == null) {
			lbRemainTimeSec = new JLabel("초");
			lbRemainTimeSec.setForeground(new Color(255, 0, 0));
			lbRemainTimeSec.setHorizontalAlignment(SwingConstants.RIGHT);
			lbRemainTimeSec.setBounds(158, 69, 40, 15);
		}
		return lbRemainTimeSec;
	}
	private JLabel getLbGroup() {
		if (lbGroup == null) {
			lbGroup = new JLabel("");
			lbGroup.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			lbGroup.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 12));
			lbGroup.setHorizontalAlignment(SwingConstants.CENTER);
			lbGroup.setBounds(61, 10, 445, 31);
		}
		return lbGroup;
	}
	private JLabel getLbRightAnswer() {
		if (lbRightAnswer == null) {
			lbRightAnswer = new JLabel("");
			lbRightAnswer.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					///////////////////////////////////////////////////
					lbRightAnswer.setText(String.valueOf(quizList.get(mQuizIndex).getAnswer()));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lbRightAnswer.setText("");
				}
			});
			lbRightAnswer.setVisible(false);
			lbRightAnswer.setHorizontalAlignment(SwingConstants.CENTER);
			lbRightAnswer.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lbRightAnswer.setBounds(782, 10, 53, 31);
		}
		return lbRightAnswer;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("정보처리기사 필기");
			label.setFont(new Font("굴림", Font.BOLD, 12));
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setBounds(105, 41, 120, 15);
		}
		return label;
	}
	private JLabel getLbUsrName() {
		if (lbUsrName == null) {
			lbUsrName = new JLabel("");
			lbUsrName.setHorizontalAlignment(SwingConstants.LEFT);
			lbUsrName.setBounds(116, 13, 109, 15);
		}
		return lbUsrName;
	}
	private JLabel getLbPic() {
		if (lbPic == null) {
			lbPic = new JLabel("");
			lbPic.setHorizontalTextPosition(SwingConstants.CENTER);
			lbPic.setHorizontalAlignment(SwingConstants.CENTER);
			lbPic.setBounds(396, 0, 449, 99);
		}
		return lbPic;
	}
}
