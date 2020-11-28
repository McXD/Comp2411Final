package gui.teacher;

import util.TeacherUtil;
import connector.TeacherLoginSession;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;


public class TeacherLoginGUI extends JFrame {

	private TeacherLoginSession tls;
	
	private JPanel contentPane;
	private JTextField IDTextField;
	private JPasswordField PWTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherLoginGUI frame = new TeacherLoginGUI();
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
	public TeacherLoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 200, 400, 80);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel IDLabel = new JLabel("ID: ");
		IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(IDLabel);
		
		IDTextField = new JTextField();
		panel.add(IDTextField);
		IDTextField.setColumns(10);
		
		JLabel PWLabel = new JLabel("Password: ");
		PWLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(PWLabel);
		
		PWTextField = new JPasswordField();
		panel.add(PWTextField);
		
		JLabel loginLabel = new JLabel("Welcome Teacher");
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 23));
		loginLabel.setBounds(151, 107, 258, 82);
		contentPane.add(loginLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(355, 316, 89, 23);
		loginButton.setMnemonic(KeyEvent.VK_ENTER);
		contentPane.add(loginButton);
		
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//fetch the ID and PW
				String inputID = IDTextField.getText();
				String inputPW = PWTextField.getText();
				
				if (!checkIndentity(inputID, inputPW)) {
					JOptionPane.showMessageDialog(new JFrame(), "Invalid ID or Password!", "Warning",JOptionPane.WARNING_MESSAGE);
				}else {
					//call new frame
					setVisible(false);
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								TeacherEntranceGUI frame = new TeacherEntranceGUI(tls);
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
				
			}
			
		});
	}
	
	private boolean checkIndentity(String id, String pw) {
		try {
			tls = new TeacherLoginSession(id, pw);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
