package itembank;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.border.EtchedBorder;

public class MemberJoinView extends JFrame {

	private JPanel contentPane;
	private JLabel lbID;
	private JTextField tfID;
	private JLabel lbPW;
	private JLabel lbRePW;
	private JLabel lbEmail;
	private JTextField tfEmail;
	private JLabel lbPrior;
	private JComboBox cbbPrior;
	private JPasswordField pfPW;
	private JPasswordField pfRePW;
	private JButton btnOK;
	private JButton btnCancel;
	private JLabel lbState;
	private ItembankDBA ibDBA;
	private JLabel lbName;
	private JTextField tfName;

	private Users mUsr;
	private AdminModeView mAdmView;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberJoinView frame = new MemberJoinView();
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
	public MemberJoinView() {
		setTitle("회원 등록/관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 464);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLbID());
		contentPane.add(getTfID());
		contentPane.add(getLbPW());
		contentPane.add(getLbRePW());
		contentPane.add(getLbEmail());
		contentPane.add(getTfEmail());
		contentPane.add(getLabel_3());
		contentPane.add(getCbbPrior());
		contentPane.add(getPfPW());
		contentPane.add(getPfRePW());
		contentPane.add(getBtnOK());
		contentPane.add(getBtnCancel());
		contentPane.add(getLbState());
		
		ibDBA = new ItembankDBA();
		
		cbbPrior.addItem("사용자");
		cbbPrior.addItem("관리자");
		cbbPrior.setSelectedIndex(0);
		
		contentPane.add(getLbName());
		contentPane.add(getTfName());
		
		setVisible(true);
		
	}
	
	
	public MemberJoinView(Users usr, AdminModeView admView) {
		setTitle("회원 등록/관리");
		
		if(usr.getPriority().equals("ADMIN"))
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		else
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 440, 464);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLbID());
		contentPane.add(getTfID());
		contentPane.add(getLbPW());
		contentPane.add(getLbRePW());
		contentPane.add(getLbEmail());
		contentPane.add(getTfEmail());
		contentPane.add(getLabel_3());
		contentPane.add(getCbbPrior());
		contentPane.add(getPfPW());
		contentPane.add(getPfRePW());
		contentPane.add(getBtnOK());
		contentPane.add(getBtnCancel());
		contentPane.add(getLbState());
		
		contentPane.add(getLbName());
		contentPane.add(getTfName());
		
		ibDBA = new ItembankDBA();
		this.mUsr = usr;
		this.mAdmView = admView;
		
		if(usr.getPriority().equals("ADMIN")) {
			cbbPrior.addItem("사용자");
			cbbPrior.addItem("관리자");
			cbbPrior.setVisible(true);
			lbPrior.setVisible(true);
		}
		else {
			setDefaultValues();
		}
		
		setVisible(true);
		
	}
	
	public void setDefaultValues() {
		ArrayList<Users> arr = ibDBA.getUserList("SELECT * FROM itembank_users WHERE user_id='" + mUsr.getId() + "'");
		mUsr.setEmail(arr.get(0).getEmail());
		
		tfID.setEditable(false);
		tfID.setText(mUsr.getId());
		tfName.setEditable(false);
		tfName.setText(mUsr.getName());
		tfEmail.setText(mUsr.getEmail());
	}
	
	private void clearValues() {
		tfID.setText("");
		pfPW.setText("");
		pfRePW.setText("");
		tfName.setText("");
		tfEmail.setText("");
		
		if(mUsr!=null && mUsr.getPriority().equals("ADMIN"))
			cbbPrior.setSelectedIndex(0);
	}
	
	private JLabel getLbID() {
		if (lbID == null) {
			lbID = new JLabel("아이디 :");
			lbID.setHorizontalAlignment(SwingConstants.RIGHT);
			lbID.setBounds(48, 28, 88, 32);
		}
		return lbID;
	}
	private JTextField getTfID() {
		if (tfID == null) {
			tfID = new JTextField();
			tfID.setBounds(154, 29, 200, 31);
			tfID.setColumns(10);
		}
		return tfID;
	}
	private JLabel getLbPW() {
		if (lbPW == null) {
			lbPW = new JLabel("비밀번호 :");
			lbPW.setHorizontalAlignment(SwingConstants.RIGHT);
			lbPW.setBounds(48, 83, 88, 32);
		}
		return lbPW;
	}
	private JLabel getLbRePW() {
		if (lbRePW == null) {
			lbRePW = new JLabel("비밀번호 확인 :");
			lbRePW.setHorizontalAlignment(SwingConstants.RIGHT);
			lbRePW.setBounds(48, 133, 88, 32);
		}
		return lbRePW;
	}
	private JLabel getLbEmail() {
		if (lbEmail == null) {
			lbEmail = new JLabel("이메일 :");
			lbEmail.setHorizontalAlignment(SwingConstants.RIGHT);
			lbEmail.setBounds(48, 238, 88, 32);
		}
		return lbEmail;
	}
	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
			tfEmail.setColumns(10);
			tfEmail.setBounds(154, 239, 200, 31);
		}
		return tfEmail;
	}
	private JLabel getLabel_3() {
		if (lbPrior == null) {
			lbPrior = new JLabel("권한 설정 :");
			lbPrior.setVisible(false);
			lbPrior.setHorizontalAlignment(SwingConstants.RIGHT);
			lbPrior.setBounds(49, 299, 88, 32);
		}
		return lbPrior;
	}
	private JComboBox getCbbPrior() {
		if (cbbPrior == null) {
			cbbPrior = new JComboBox();
			cbbPrior.setVisible(false);
			cbbPrior.setBounds(155, 305, 88, 21);
		}
		return cbbPrior;
	}
	private JPasswordField getPfPW() {
		if (pfPW == null) {
			pfPW = new JPasswordField();
			pfPW.setBounds(154, 83, 200, 32);
		}
		return pfPW;
	}
	private JPasswordField getPfRePW() {
		if (pfRePW == null) {
			pfRePW = new JPasswordField();
			pfRePW.setBounds(154, 133, 200, 32);
		}
		return pfRePW;
	}
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton("확  인");
			btnOK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					if(tfID.getText().isEmpty()) {
						lbState.setText("ID를 입력해주세요.");
						return;
					}
					
					if(pfPW.getPassword().length==0 || pfRePW.getPassword().length==0 ) {
						lbState.setText("비밀번호를 입력해주세요.");
						return;
					}
					
					if(tfName.getText().isEmpty()) {
						lbState.setText("이름을 입력해주세요.");
						return;
					}
					
					if(tfEmail.getText().isEmpty()) {
						lbState.setText("이메일을 입력해주세요.");
						return;
					}
					
					if(mUsr==null || mUsr.getPriority().equals("ADMIN")) {
						if(ibDBA.isExistID(tfID.getText())) {
							lbState.setText("이미 존재하는 아이디 입니다.");
							tfID.setText("");
							return;
						}	
					}
					
					
					if(!String.valueOf(pfPW.getPassword()).equals(String.valueOf(pfRePW.getPassword()))) {
						lbState.setText("비밀번호가 일치하지 않습니다.");
						pfPW.setText("");
						pfRePW.setText("");
						return;
					}
					
					Users usr = new Users();
					
					usr.setId(tfID.getText());
					usr.setName(tfName.getText());
					usr.setPw(String.valueOf(pfPW.getPassword()));
					usr.setEmail(tfEmail.getText());
					
					String prior=null;
					if(cbbPrior.getSelectedIndex()==0)
						prior = "USER";
					else 
						prior = "ADMIN";
					
					usr.setPriority(prior);	//확인 필요.
			//		usr.setapplyCount(0);
					usr.setPass("미응시");
					
					int result = 0; 
					if(mUsr!=null && mUsr.getPriority().equals("USER"))
						result = ibDBA.modifyUser(usr);
					else
						result = ibDBA.addUser(usr);
					
					if(result==0) {
						lbState.setText("서버에 문제가 발생하여 정상적으로 가입이 이루어지지 않았습니다.");
						return;
					}
						
					if(mUsr!=null && mUsr.getPriority().equals("ADMIN")) {
						mAdmView.refreshUserList();
						mAdmView.setStatusLabel("사용자가 추가되었습니다.");
					}
					else if(mUsr==null)
						new LogInView();
					
					clearValues();
					
					if(mUsr!=null && mUsr.getPriority().equals("USER"))
						setDefaultValues();
					
					dispose();
				}
			});
			btnOK.setBounds(217, 350, 88, 37);
		}
		return btnOK;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("취  소");
			btnCancel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					
					if(mUsr==null) {			//로그인 가입에서 실행된 경우
						new LogInView();
						dispose();
					}
					else {
						clearValues();
						setVisible(false);
					}
					
					if(mUsr!=null && mUsr.getPriority().equals("USER"))
						setDefaultValues();
					
				}
			});
			btnCancel.setBounds(317, 350, 88, 37);
		}
		return btnCancel;
	}
	private JLabel getLbState() {
		if (lbState == null) {
			lbState = new JLabel("");
			lbState.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			lbState.setBounds(0, 405, 424, 21);
		}
		return lbState;
	}
	private JLabel getLbName() {
		if (lbName == null) {
			lbName = new JLabel("이름 :");
			lbName.setHorizontalAlignment(SwingConstants.RIGHT);
			lbName.setBounds(48, 185, 88, 32);
		}
		return lbName;
	}
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setColumns(10);
			tfName.setBounds(154, 186, 200, 31);
		}
		return tfName;
	}
}
