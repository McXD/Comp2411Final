package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.student.StudentLoginGUI;
import gui.teacher.TeacherLoginGUI;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class MainGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setLocationRelativeTo(null);
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
		setBounds(100, 100, 362, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel welcomeLabel =  new JLabel("Automated Examnination System");
		welcomeLabel.setBounds(40, 25, 270, 21);
		welcomeLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 14));
		contentPane.add(welcomeLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(85, 72, 180, 70);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(2, 0, 5, 5));
		
		JButton studentLoginButton = new JButton("Student Entrance");
		panel.add(studentLoginButton);
		studentLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							StudentLoginGUI frame = new StudentLoginGUI();
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		studentLoginButton.setFont(new Font("Arial", Font.PLAIN, 13));
		
		JButton teacherLoginButton = new JButton("Teacher Entrance");
		panel.add(teacherLoginButton);
		teacherLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							TeacherLoginGUI frame = new TeacherLoginGUI();
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		teacherLoginButton.setFont(new Font("Arial", Font.PLAIN, 13));
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exitButton.setBounds(10, 177, 69, 23);
		contentPane.add(exitButton);
	}
}