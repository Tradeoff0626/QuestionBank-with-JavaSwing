package itembank;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.JTextPane;
import javax.swing.JCheckBox;

import org.omg.CORBA.INTF_REPOS;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegQuizView extends JFrame {

	private JPanel contentPane;
	private JSplitPane splitPane;
	private JPanel panel_1;
	private JSplitPane splitPane_1;
	private JSplitPane splitPane_2;
	private JSplitPane splitPane_3;
	private JSplitPane splitPane_4;
	private JSplitPane splitPane_5;
	private JLabel lbQuizPic;
	private JLabel lbExamPic;
	private JScrollPane scrollPane;
	private JSplitPane splitPane_6;
	private JSplitPane splitPane_7;
	private JTextArea taQuiz;
	private JSplitPane splitPane_8;
	private JPanel panel;
	private JSplitPane splitPane_9;
	private JScrollPane scrollPane_1;
	private JTextArea taSolution;
	private JButton btnQuizPicClear;
	private JButton btnRegQuizPic;
	private JButton btnExamPicClear;
	private JButton btnRegExamPic;
	private JTextField tfAnswer;
	private JLabel lbExam1;
	private JLabel lbExam2;
	private JLabel lbExam3;
	private JLabel lbExam4;
	private JTextField tfExam1;
	private JTextField tfExam2;
	private JTextField tfExam3;
	private JTextField tfExam4;

	private JButton btnOK;
	private JButton btnCancel;
	private JCheckBox cbEditMode;
	
	/*****************************/
	ImageIcon mQuizIcon, mExamIcon;
	JFileChooser mFileDlg;
	String mRecentPath;
	String mQuizPicFileName, mExamPicFileName;
	
	ItembankDBA ibDBA = new ItembankDBA();
	private JComboBox cbbGroup;
	private JLabel lblNewLabel;

	private int mModifingQuizNum;
	private byte[] mExamBin;
	private byte[] mQuizBin;
	private JLabel label;
	private JLabel lbQuizID;
	private JButton btnClear;
	
	private boolean mBoolEditMode;
	private Quizzes mQuiz;
	
	private AdminModeView mAdmView;
	
	//int tempNum=1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegQuizView frame = new RegQuizView();
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
	public RegQuizView() {
		setTitle("문제 및 해답");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getSplitPane(), BorderLayout.CENTER);
		
		cbbGroup.addItem("데이터베이스");
		cbbGroup.addItem("전자계산기구조");
		cbbGroup.addItem("운영체제");
		cbbGroup.addItem("소프트웨어공학");
		cbbGroup.addItem("데이터통신");
		
		ibDBA = new ItembankDBA();
		///////////// 초기에 비편집 모드  ////////////////////////////
		cbEditMode.setSelected(false);
		setEnableComponent(cbEditMode.isSelected());
	}
	
	public RegQuizView(Quizzes quiz, AdminModeView admView) {
		setTitle("문제 및 해답");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getSplitPane(), BorderLayout.CENTER);
		
		cbbGroup.addItem("데이터베이스");
		cbbGroup.addItem("전자계산기구조");
		cbbGroup.addItem("운영체제");
		cbbGroup.addItem("소프트웨어공학");
		cbbGroup.addItem("데이터통신");
		
		ibDBA = new ItembankDBA();
		mAdmView = admView;
		///////////// 초기에 비편집 모드  ////////////////////////////
			
		if(quiz!=null) {
			this.mBoolEditMode = true;
			this.mModifingQuizNum = quiz.getSeqNum();
			diplayQuiz(quiz);
		}
		else
			this.mBoolEditMode = false;
		//this.mQuiz = quiz;
		
		if(quiz==null)
			cbEditMode.setSelected(true);
		
		setEnableComponent(cbEditMode.isSelected());
		
		setVisible(true);
	}

	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setDividerSize(1);
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setRightComponent(getPanel_1());
			splitPane.setLeftComponent(getSplitPane_1());
			splitPane.setDividerLocation(500);
		}
		return splitPane;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.add(getBtnOK());
			panel_1.add(getBtnCancel());
			panel_1.add(getCbEditMode());
			panel_1.add(getCbbGroup());
			panel_1.add(getLblNewLabel());
			panel_1.add(getLabel());
			panel_1.add(getLbQuizID());
			panel_1.add(getBtnClear());
		}
		return panel_1;
	}
	private JSplitPane getSplitPane_1() {
		if (splitPane_1 == null) {
			splitPane_1 = new JSplitPane();
			splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_1.setDividerSize(1);
			splitPane_1.setLeftComponent(getSplitPane_2());
			splitPane_1.setRightComponent(getSplitPane_3());
			splitPane_1.setDividerLocation(250);
		}
		return splitPane_1;
	}
	private JSplitPane getSplitPane_2() {
		if (splitPane_2 == null) {
			splitPane_2 = new JSplitPane();
			splitPane_2.setDividerSize(1);
			splitPane_2.setRightComponent(getSplitPane_4());
			splitPane_2.setLeftComponent(getScrollPane());
			splitPane_2.setDividerLocation(450);
		}
		return splitPane_2;
	}
	private JSplitPane getSplitPane_3() {
		if (splitPane_3 == null) {
			splitPane_3 = new JSplitPane();
			splitPane_3.setDividerSize(1);
			splitPane_3.setRightComponent(getSplitPane_5());
			splitPane_3.setLeftComponent(getSplitPane_8());
			splitPane_3.setDividerLocation(450);
		}
		return splitPane_3;
	}
	private JSplitPane getSplitPane_4() {
		if (splitPane_4 == null) {
			splitPane_4 = new JSplitPane();
			splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_4.setDividerSize(1);
			splitPane_4.setLeftComponent(getLbQuizPic());
			splitPane_4.setRightComponent(getSplitPane_6());
			splitPane_4.setDividerLocation(220);
		}
		return splitPane_4;
	}
	private JSplitPane getSplitPane_5() {
		if (splitPane_5 == null) {
			splitPane_5 = new JSplitPane();
			splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_5.setDividerSize(1);
			splitPane_5.setLeftComponent(getLbExamPic());
			splitPane_5.setRightComponent(getSplitPane_7());
			splitPane_5.setDividerLocation(220);
		}
		return splitPane_5;
	}
	private JLabel getLbQuizPic() {
		if (lbQuizPic == null) {
			lbQuizPic = new JLabel("");
			lbQuizPic.setHorizontalAlignment(SwingConstants.CENTER);
			lbQuizPic.setBorder(new TitledBorder(null, "\uBB38\uC81C \uC774\uBBF8\uC9C0", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, Color.PINK));
		}
		return lbQuizPic;
	}
	private JLabel getLbExamPic() {
		if (lbExamPic == null) {
			lbExamPic = new JLabel("");
			lbExamPic.setHorizontalAlignment(SwingConstants.CENTER);
			lbExamPic.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uBCF4\uAE30 \uC774\uBBF8\uC9C0", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, Color.PINK));
		}
		return lbExamPic;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uBB38 \uC81C", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(255, 200, 0)));
			scrollPane.setViewportView(getTaQuiz());
		}
		return scrollPane;
	}
	private JSplitPane getSplitPane_6() {
		if (splitPane_6 == null) {
			splitPane_6 = new JSplitPane();
			splitPane_6.setDividerSize(1);
			splitPane_6.setLeftComponent(getBtnQuizPicClear());
			splitPane_6.setRightComponent(getBtnRegQuizPic());
		}
		return splitPane_6;
	}
	private JSplitPane getSplitPane_7() {
		if (splitPane_7 == null) {
			splitPane_7 = new JSplitPane();
			splitPane_7.setDividerSize(1);
			splitPane_7.setLeftComponent(getBtnExamPicClear());
			splitPane_7.setRightComponent(getBtnRegExamPic());
		}
		return splitPane_7;
	}
	private JTextArea getTaQuiz() {
		if (taQuiz == null) {
			taQuiz = new JTextArea();
			taQuiz.setLineWrap(true);
		}
		return taQuiz;
	}
	private JSplitPane getSplitPane_8() {
		if (splitPane_8 == null) {
			splitPane_8 = new JSplitPane();
			splitPane_8.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_8.setDividerSize(1);
			splitPane_8.setLeftComponent(getPanel_2());
			splitPane_8.setRightComponent(getSplitPane_9());
			splitPane_8.setDividerLocation(150);
		}
		return splitPane_8;
	}
	private JPanel getPanel_2() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uBCF4 \uAE30", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, Color.ORANGE));
			panel.setLayout(null);
			panel.add(getLbExam1());
			panel.add(getLbExam2());
			panel.add(getLbExam3());
			panel.add(getLbExam4());
			panel.add(getTfExam1());
			panel.add(getTfExam2());
			panel.add(getTfExam3());
			panel.add(getTfExam4());
		}
		return panel;
	}
	private JSplitPane getSplitPane_9() {
		if (splitPane_9 == null) {
			splitPane_9 = new JSplitPane();
			splitPane_9.setDividerSize(1);
			splitPane_9.setRightComponent(getScrollPane_1());
			splitPane_9.setLeftComponent(getTfAnswer());
			splitPane_9.setDividerLocation(50);
		}
		return splitPane_9;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTaSolution());
		}
		return scrollPane_1;
	}
	private JTextArea getTaSolution() {
		if (taSolution == null) {
			taSolution = new JTextArea();
			taSolution.setBorder(new TitledBorder(null, "\uD480 \uC774", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, Color.BLUE));
		}
		return taSolution;
	}
	private JButton getBtnQuizPicClear() {
		if (btnQuizPicClear == null) {
			btnQuizPicClear = new JButton("그림 삭제");
			btnQuizPicClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mQuizPicFileName = null;
					lbQuizPic.setIcon(new ImageIcon(mQuizPicFileName));
					mQuizBin = null;
				}
			});
		}
		return btnQuizPicClear;
	}
	private JButton getBtnRegQuizPic() {
		if (btnRegQuizPic == null) {
			btnRegQuizPic = new JButton("그림 찾기");
			btnRegQuizPic.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mQuizPicFileName = findImagePath();
					lbQuizPic.setIcon(new ImageIcon(mQuizPicFileName)); // 파일을 로딩하여 이미지 레이블에 출력한다.
					mQuizBin = binaryToBytes(mQuizPicFileName);
				}
			});
		}
		return btnRegQuizPic;
	}
	private JButton getBtnExamPicClear() {
		if (btnExamPicClear == null) {
			btnExamPicClear = new JButton("그림 삭제");
			btnExamPicClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mExamPicFileName = null;
					lbExamPic.setIcon(new ImageIcon(mExamPicFileName));
					mExamBin = null;
					
					//tempNum++;
				}
			});
		}
		return btnExamPicClear;
	}
	private JButton getBtnRegExamPic() {
		if (btnRegExamPic == null) {
			btnRegExamPic = new JButton("그림 찾기");
			btnRegExamPic.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mExamPicFileName = findImagePath();
				    lbExamPic.setIcon(new ImageIcon(mExamPicFileName)); // 파일을 로딩하여 이미지 레이블에 출력한다.
				    mExamBin = binaryToBytes(mExamPicFileName);
				}
			});
		}
		return btnRegExamPic;
	}
	
	
	private String findImagePath() {
		/********************************************
		 * 14.10.07					-K.C.Kang
		 * 
		 * JFilechooser 클래스를 이용하여 파일 다이얼로그로 파일을
		 * 찾는 일을 한다.
		 *******************************************/
		
		mFileDlg = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", 	// 파일 이름에 창에 출력될 문자열
                "jpg", "gif"); 			// 파일 필터로 사용되는 확장자. *.jpg. *.gif만 나열됨
		mFileDlg.setFileFilter(filter); // 파일 다이얼로그에 파일 필터 설정
		
		if(mRecentPath!=null)			//이전 작업 중인 디렉토리가 있는 경우
			mFileDlg.setCurrentDirectory(new File(mRecentPath));	//해당 데렉토리에서 시작한다.
		
		int ret = mFileDlg.showOpenDialog(null);
	    
		if(ret != JFileChooser.APPROVE_OPTION) { // 사용자가  창을 강제로 닫았거나 취소 버튼을 누른 경우
	                JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다", "경고", 
                    JOptionPane.WARNING_MESSAGE);
	                return null;
	            }
	     
	    // 사용자가 파일을 선택하고 "열기" 버튼을 누른 경우
	    String filePath = mFileDlg.getSelectedFile().getPath(); // 파일 경로명을 알아온다.
	    mRecentPath = filePath;
	    //pack(); // 이미지의 크기에 맞추어 프레임의 크기 조절
	    
		return filePath;
	}
	
	private JTextField getTfAnswer() {
		if (tfAnswer == null) {
			tfAnswer = new JTextField();
			tfAnswer.setHorizontalAlignment(SwingConstants.CENTER);
			tfAnswer.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uC815 \uB2F5", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(255, 0, 0)));
			tfAnswer.setColumns(10);
		}
		return tfAnswer;
	}
	private JLabel getLbExam1() {
		if (lbExam1 == null) {
			lbExam1 = new JLabel("1.");
			lbExam1.setHorizontalAlignment(SwingConstants.RIGHT);
			lbExam1.setBounds(12, 20, 31, 24);
		}
		return lbExam1;
	}
	private JLabel getLbExam2() {
		if (lbExam2 == null) {
			lbExam2 = new JLabel("2.");
			lbExam2.setHorizontalAlignment(SwingConstants.RIGHT);
			lbExam2.setBounds(12, 50, 31, 24);
		}
		return lbExam2;
	}
	private JLabel getLbExam3() {
		if (lbExam3 == null) {
			lbExam3 = new JLabel("3.");
			lbExam3.setHorizontalAlignment(SwingConstants.RIGHT);
			lbExam3.setBounds(12, 80, 31, 24);
		}
		return lbExam3;
	}
	private JLabel getLbExam4() {
		if (lbExam4 == null) {
			lbExam4 = new JLabel("4.");
			lbExam4.setHorizontalAlignment(SwingConstants.RIGHT);
			lbExam4.setBounds(12, 110, 31, 24);
		}
		return lbExam4;
	}
	private JTextField getTfExam1() {
		if (tfExam1 == null) {
			tfExam1 = new JTextField();
			tfExam1.setBounds(55, 21, 366, 24);
			tfExam1.setColumns(10);
		}
		return tfExam1;
	}
	private JTextField getTfExam2() {
		if (tfExam2 == null) {
			tfExam2 = new JTextField();
			tfExam2.setColumns(10);
			tfExam2.setBounds(55, 50, 366, 24);
		}
		return tfExam2;
	}
	private JTextField getTfExam3() {
		if (tfExam3 == null) {
			tfExam3 = new JTextField();
			tfExam3.setColumns(10);
			tfExam3.setBounds(55, 80, 366, 24);
		}
		return tfExam3;
	}
	private JTextField getTfExam4() {
		if (tfExam4 == null) {
			tfExam4 = new JTextField();
			tfExam4.setColumns(10);
			tfExam4.setBounds(55, 110, 366, 24);
		}
		return tfExam4;
	}
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton("저 장");
		
			btnOK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					/*********************************
					 * 
					 * 
					 * 구현
					 * 
					 * 
					 ********************************/
					
					if(0!=JOptionPane.showConfirmDialog(null, "저장하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION))
						return;
					
					//DB에 저장하는 루틴.
					if(taQuiz.getText().isEmpty() || tfAnswer.getText().isEmpty())
						JOptionPane.showMessageDialog(rootPane, "문제 및 정답란은 필수 입력 사항입니다.");
					
					
					Quizzes qz = new Quizzes();
					
					qz.setGroup(cbbGroup.getSelectedItem().toString());		//수정필요
					qz.setQuiz(taQuiz.getText());
					if(mQuizBin!=null)
						qz.setQuizImg(mQuizBin);
					qz.setExam1(tfExam1.getText());
					qz.setExam2(tfExam2.getText());
					qz.setExam3(tfExam3.getText());
					qz.setExam4(tfExam4.getText());
					if(mExamBin!=null)
						qz.setExamImg(mExamBin);
					qz.setAnswer(Integer.parseInt(tfAnswer.getText()));
					qz.setSolution(taSolution.getText());
								
					//String[] opt = {"저장", "수정", "취소"};
					//int select = JOptionPane.showOptionDialog(null, "저장/수정/취소", "확인", 0, 0, null, opt, null);
					
					//if(select==0) {
					//	ibDBA.addQuiz(qz);
					//	resetControls();
					//}
					//else if(select==1)
					//	ibDBA.modifyQuiz(qz, mModifingQuizNum);
					
					if(mBoolEditMode)
						ibDBA.modifyQuiz(qz, mModifingQuizNum);
					else 
						ibDBA.addQuiz(qz);
					
					mAdmView.refreshQuizList();
					
					if(mBoolEditMode)
						mAdmView.setStatusLabel(mModifingQuizNum + "번 문제가 수정되었습니다.");
					else 
						mAdmView.setStatusLabel("새로운 문제가 등록되었습니다.");
						
					setVisible(false);
				}
			});
	
			btnOK.setBounds(578, 10, 85, 30);
		}
		return btnOK;
	}
	
	
	public byte[] binaryToBytes(String fileName) {
		byte[] byteData = null;
		File file = new File(fileName);
		FileInputStream fis = null;
		
		byteData = new byte [(int)file.length()];
		
		try {
			fis = new FileInputStream(file);

			try {
				fis.read(byteData);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return byteData;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("취 소");
			btnCancel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					/*********************************
					 * 
					 * 구현
					 * 
					 ********************************/
					
					setVisible(false);
					//dispose();
					
					/*
					//DB에서 읽어오는 루틴
					lbQuizPic.setIcon(new ImageIcon());
					lbExamPic.setIcon(new ImageIcon());
					
					
					ArrayList<Quizzes> qz= ibDBA.getQuizList("SELECT * FROM itembank_quizzes WHERE quiz_num="+ tempNum);
				
					if(qz.size()==1)
					{
					
					
					cbbGroup.setSelectedItem(qz.get(0).getGroup());				
					taQuiz.setText(qz.get(0).getQuiz());
					if(qz.get(0).getQuizImg()!=null) {
						mQuizBin = qz.get(0).getQuizImg();
						lbQuizPic.setIcon(new ImageIcon(mQuizBin));
					}
					tfExam1.setText(qz.get(0).getExam1());
					tfExam2.setText(qz.get(0).getExam2());
					tfExam3.setText(qz.get(0).getExam3());
					tfExam4.setText(qz.get(0).getExam4());
					if(qz.get(0).getExamImg()!=null) {
						mExamBin = qz.get(0).getExamImg();
						lbExamPic.setIcon(new ImageIcon(mExamBin));
					}
					tfAnswer.setText(String.valueOf(qz.get(0).getAnswer()));
					taSolution.setText(qz.get(0).getSolution());
					
					mModifingQuizNum = qz.get(0).getSeqNum();
					lbQuizID.setText(String.valueOf(mModifingQuizNum));
					}
					
					//tempNum++;
					*/
				}
			});
			
			btnCancel.setBounds(675, 10, 85, 30);
		}
		return btnCancel;
	}
	
	public void diplayQuiz(Quizzes q) {
		
		this.mBoolEditMode = true;
		this.mModifingQuizNum = q.getSeqNum();
		cbEditMode.setSelected(false);
		setEnableComponent(false);
		
		cbbGroup.setSelectedItem(q.getGroup());				
		taQuiz.setText(q.getQuiz());
	
		if(q.getQuizImg()!=null) {
			mQuizBin = q.getQuizImg();
			lbQuizPic.setIcon(new ImageIcon(mQuizBin));
		}
		else
			lbQuizPic.setIcon(null);
		
		tfExam1.setText(q.getExam1());
		tfExam2.setText(q.getExam2());
		tfExam3.setText(q.getExam3());
		tfExam4.setText(q.getExam4());
		
		if(q.getExamImg()!=null) {
			mExamBin = q.getExamImg();
			lbExamPic.setIcon(new ImageIcon(mExamBin));
		}
		else
			lbExamPic.setIcon(null);
		
		tfAnswer.setText(String.valueOf(q.getAnswer()));
		taSolution.setText(q.getSolution());
		
		mModifingQuizNum = q.getSeqNum();
		lbQuizID.setText(String.valueOf(mModifingQuizNum));
	}
	
	private void setEnableComponent(boolean b) {
		
		cbbGroup.setEnabled(b);
		taQuiz.setEnabled(b);
		
		tfExam1.setEditable(b);
		tfExam2.setEditable(b);
		tfExam3.setEditable(b);
		tfExam4.setEditable(b);
		
		tfAnswer.setEditable(b);
		taSolution.setEditable(b);
		
		btnRegQuizPic.setEnabled(b);
		btnQuizPicClear.setEnabled(b);
		btnRegExamPic.setEnabled(b);
		btnExamPicClear.setEnabled(b);
	
		btnClear.setEnabled(b);
		btnOK.setEnabled(b);
	}
	private JCheckBox getCbEditMode() {
		if (cbEditMode == null) {
			cbEditMode = new JCheckBox("편집 모드");
			cbEditMode.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					setEnableComponent(cbEditMode.isSelected());
				}
			});
			cbEditMode.setBounds(8, 14, 85, 23);
		}
		return cbEditMode;
	}
	private JComboBox getCbbGroup() {
		if (cbbGroup == null) {
			cbbGroup = new JComboBox();
			cbbGroup.setBounds(182, 15, 127, 21);
		}
		return cbbGroup;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("과목 분류 :");
			lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel.setBounds(101, 12, 69, 27);
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("등록 번호 :");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setBounds(321, 12, 69, 27);
		}
		return label;
	}
	private JLabel getLbQuizID() {
		if (lbQuizID == null) {
			lbQuizID = new JLabel("");
			lbQuizID.setHorizontalAlignment(SwingConstants.LEFT);
			lbQuizID.setBounds(402, 13, 46, 27);
		}
		return lbQuizID;
	}
	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton("초기화");
			btnClear.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					resetControls();
					
				}
			});
			
			btnClear.setBounds(469, 10, 85, 30);
		}
		return btnClear;
	}
	
	public void ClearQuiz() {
		resetControls();
		
		mBoolEditMode = false;
		mModifingQuizNum = -1;		
	}
	
	public void resetControls() {
		//cbbGroup.setSelectedIndex(0);
		taQuiz.setText("");
		lbQuizPic.setIcon(null);
		mQuizPicFileName=null;
		mQuizBin=null;
		tfExam1.setText("");
		tfExam2.setText("");
		tfExam3.setText("");
		tfExam4.setText("");
		lbExamPic.setIcon(null);
		mExamPicFileName=null;
		mExamBin=null;
		tfAnswer.setText("");
		taSolution.setText("");
		lbQuizID.setText("New");
		
		cbEditMode.setSelected(true);
		setEnableComponent(true);
	}
}
