package itembank;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserInfoView extends JFrame {

	private JPanel contentPane;
	private JLabel lbUserName;
	private JButton btnTestStart;
	private JPanel panel;
	private JButton btnChangeInfo;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	
	private Users mUsr; 
	private MemberJoinView mModifyUsrView;	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UserInfoView frame = new UserInfoView();
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
	public UserInfoView() {
		setTitle("사용자 확인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 443);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLbUserName());
		contentPane.add(getBtnTestStart());
	}
	*/
	
	
	public UserInfoView(Users usr) {
		setTitle("사용자 확인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLbUserName());
		contentPane.add(getBtnTestStart());
		
		lbUserName.setText("'" + usr.getName()+"'님 반갑습니다!");
		contentPane.add(getPanel());
		contentPane.add(getBtnChangeInfo());
		
		this.mUsr = usr;
		
		setTitle("사용자 확인 - " + usr.getName() + "님");
		
		setVisible(true);
		
	}
	
	
	
	private JLabel getLbUserName() {
		if (lbUserName == null) {
			lbUserName = new JLabel("사용자님 반갑습니다!");
			lbUserName.setBorder(new LineBorder(SystemColor.controlShadow, 1, true));
			lbUserName.setHorizontalAlignment(SwingConstants.CENTER);
			lbUserName.setFont(new Font("굴림", Font.BOLD, 14));
			lbUserName.setBounds(22, 16, 427, 37);
		}
		return lbUserName;
	}
	private JButton getBtnTestStart() {
		if (btnTestStart == null) {
			btnTestStart = new JButton("시험 시작!");
			btnTestStart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					new TestPageView(mUsr);
					btnTestStart.setText("문제 생성 중...");
					dispose();
				}
			});

			btnTestStart.setBounds(238, 366, 97, 37);
		}
		return btnTestStart;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uC720\uC758 \uC0AC\uD56D", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, Color.RED));
			panel.setBounds(22, 63, 542, 295);
			panel.setLayout(null);
			panel.add(getLblNewLabel());
			panel.add(getLabel());
			panel.add(getLabel_1());
			panel.add(getLabel_2());
			panel.add(getLabel_3());
			panel.add(getLabel_4());
		}
		return panel;
	}
	private JButton getBtnChangeInfo() {
		if (btnChangeInfo == null) {
			btnChangeInfo = new JButton("정보 수정");
			btnChangeInfo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(mModifyUsrView==null)
						mModifyUsrView = new MemberJoinView(mUsr, null);
					else
						mModifyUsrView.setVisible(true);
				}
			});
			btnChangeInfo.setBounds(467, 17, 97, 37);
		}
		return btnChangeInfo;
	}
	
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("시험 시간은 각 과목당 30분으로 총 150분입니다.");
			lblNewLabel.setBounds(37, 243, 473, 21);
		}
		return lblNewLabel;
	}
	
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("평가할 과목은 5과목으로 데이터베이스, 전자계산기, 운영체제, 소프트웨어 공학");
			label.setBounds(37, 60, 473, 21);
		}
		return label;
	}
	
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("데이터 통신입니다.");
			label_1.setBounds(37, 88, 473, 21);
		}
		return label_1;
	}
	
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("각 과목당 문제를 5점씩 환산하여 100점 만점에 한 과목이라도 40점 미만일 경우 ");
			label_2.setBounds(37, 129, 473, 21);
		}
		return label_2;
	}
	
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("불합격 처리됩니다.");
			label_3.setBounds(37, 160, 473, 21);
		}
		return label_3;
	}
	
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("모든 과목 총 100문제를 1점으로 환산하여 60점 미만일 경우도 불합격 처리됩니다.");
			label_4.setBounds(37, 191, 473, 21);
		}
		return label_4;
	}
}
