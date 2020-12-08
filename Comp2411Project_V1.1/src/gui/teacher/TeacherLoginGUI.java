package gui.teacher;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import connector.TeacherLoginSession;
import exception.IdentityException;


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
		setBounds(100, 100, 421, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(-87, 90, 400, 80);
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
		
		JLabel loginLabel = new JLabel("Welcome, Teacher");
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 23));
		loginLabel.setBounds(75, 21, 258, 82);
		contentPane.add(loginLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(310, 201, 89, 23);
		loginButton.setMnemonic(KeyEvent.VK_ENTER);
		contentPane.add(loginButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exitButton.setBounds(10, 201, 89, 23);
		contentPane.add(exitButton);
		
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//fetch the ID and PW
				String inputID = IDTextField.getText();
				@SuppressWarnings("deprecation")
				String inputPW = PWTextField.getText();
				
				try {
					if (!checkIndentity(inputID, inputPW)) {
						JOptionPane.showMessageDialog(new JFrame(), "Invalid ID or Password!", "Warning",JOptionPane.WARNING_MESSAGE);
					}else {
						//call new frame
						setVisible(false);
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									TeacherEntranceGUI frame = new TeacherEntranceGUI(tls);
									frame.setLocationRelativeTo(null);
									frame.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(new JFrame(), "No Database Connection!", "Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
	}
	
	private boolean checkIndentity(String id, String pw) throws SQLException {
		try {
			tls = new TeacherLoginSession(id, pw);
			return true;
		}catch(IdentityException ie) {
			return false;
		}
	}
}
