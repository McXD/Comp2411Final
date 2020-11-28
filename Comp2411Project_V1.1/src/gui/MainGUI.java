package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.teacher.TeacherLoginGUI;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
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
	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton teacherLoginButton = new JButton("Teacher Entrance");
		teacherLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
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
		});
		teacherLoginButton.setFont(new Font("Arial", Font.PLAIN, 13));
		teacherLoginButton.setBounds(131, 125, 151, 23);
		contentPane.add(teacherLoginButton);
		
		JLabel welcomeLabel = new JLabel("Automated Examnination System");
		welcomeLabel.setBounds(81, 46, 270, 21);
		welcomeLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 14));
		contentPane.add(welcomeLabel);
		
		JButton studentLoginButton = new JButton("Student Entrance");
		studentLoginButton.setFont(new Font("Arial", Font.PLAIN, 13));
		studentLoginButton.setBounds(131, 170, 151, 23);
		contentPane.add(studentLoginButton);
	}
}