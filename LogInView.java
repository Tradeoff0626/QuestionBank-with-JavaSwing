package itembank;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.BevelBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.EtchedBorder;

public class LogInView extends JFrame {

	private JPanel contentPane;
	private JLabel lbID;
	private JLabel lbPW;
	private JTextField tfID;
	private JButton btnLogIn;
	private JPasswordField pfPW;
	private JLabel lbState;

	ItembankDBA ibDBA = new ItembankDBA();
	private JButton btnMemberJoin;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInView frame = new LogInView();
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
	public LogInView() {
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 225);
		contentPane = new JPanel();
		contentPane.setToolTipText("로그인 정보를 입력하세요.");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLbID());
		contentPane.add(getLbPW());
		contentPane.add(getTfID());
		contentPane.add(getBtnLogIn());
		contentPane.add(getPfPW());
		contentPane.add(getLbState());
		
		getRootPane().setDefaultButton(btnLogIn);
		contentPane.add(getBtnMemberJoin());
		
		setVisible(true);
	}
	private JLabel getLbID() {
		if (lbID == null) {
			lbID = new JLabel("ID :");
			lbID.setHorizontalAlignment(SwingConstants.RIGHT);
			lbID.setFont(new Font("굴림", Font.BOLD, 14));
			lbID.setBounds(12, 33, 98, 24);
		}
		return lbID;
	}
	private JLabel getLbPW() {
		if (lbPW == null) {
			lbPW = new JLabel("비밀번호 :");
			lbPW.setHorizontalAlignment(SwingConstants.RIGHT);
			lbPW.setFont(new Font("굴림", Font.BOLD, 14));
			lbPW.setBounds(12, 79, 98, 24);
		}
		return lbPW;
	}
	private JTextField getTfID() {
		if (tfID == null) {
			tfID = new JTextField();
			tfID.setBounds(122, 31, 141, 31);
			tfID.setColumns(10);
		}
		return tfID;
	}
	private JButton getBtnLogIn() {
		if (btnLogIn == null) {
			btnLogIn = new JButton("확인");
			btnLogIn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(tfID.getText().isEmpty() || pfPW.getPassword().length==0) {
						/*아디디 및 패스워드 입력 컨트롤이 비었을 경우 재입력 요구*/
						
						lbState.setText("아이디와 비밀번호를 입력해주세요.");
						return;
					}
					
					Users usr = ibDBA.getLogInInfo(tfID.getText(), String.valueOf(pfPW.getPassword()));
					
					if(usr==null) {
						lbState.setText("해당하는 아이디가 존재하지 않습니다.");
					}
					else if(usr.getPw().equals("pass")) {
						lbState.setText("로그인 성공");
						
						if(usr.getPriority().equals("ADMIN")) {
							//AdminMenuView amv = new AdminMenuView(usr);
							AdminModeView amv = new AdminModeView(usr);
						}
						else {
							UserInfoView uiv = new UserInfoView(usr);
						}
						dispose();
					}
					else {
						lbState.setText("비밀번호가 일치하지 않습니다.");
					}
				}
			});
			btnLogIn.setFont(new Font("굴림", Font.BOLD, 14));
			btnLogIn.setBounds(282, 29, 74, 79);
		}
		return btnLogIn;
	}
	private JPasswordField getPfPW() {
		if (pfPW == null) {
			pfPW = new JPasswordField();
			pfPW.setBounds(122, 77, 141, 31);
		}
		return pfPW;
	}
	private JLabel getLbState() {
		if (lbState == null) {
			lbState = new JLabel("");
			lbState.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			lbState.setHorizontalAlignment(SwingConstants.CENTER);
			lbState.setBounds(0, 163, 378, 24);
		}
		return lbState;
	}
	private JButton getBtnMemberJoin() {
		if (btnMemberJoin == null) {
			btnMemberJoin = new JButton("등  록");
			btnMemberJoin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//회원 등록 버튼
					MemberJoinView mjv = new MemberJoinView();
					dispose();
				}
			});
			btnMemberJoin.setBounds(282, 118, 74, 31);
		}
		return btnMemberJoin;
	}
}
