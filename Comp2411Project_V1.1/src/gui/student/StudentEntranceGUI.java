package gui.student;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connector.StudentLoginSession;


public class StudentEntranceGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private StudentLoginSession sls;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentEntranceGUI frame = new StudentEntranceGUI(new StudentLoginSession("19084103d","00000000"));
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
	public StudentEntranceGUI(StudentLoginSession sls) {
		this.sls = sls;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(152, 159, 268, 151);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(3, 0, 2, 2));
		
		JButton sitExamButton = new JButton("Sit An Exam");
		sitExamButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				setVisible(false);
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SitExamGUI frame = new SitExamGUI(StudentEntranceGUI.this, sls);
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		panel.add(sitExamButton);
		
		JButton checkRecordButton = new JButton("Check My Record");
		checkRecordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							CheckRecordGUI frame = new CheckRecordGUI(StudentEntranceGUI.this, sls);
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		panel.add(checkRecordButton);
		
		JButton summaryButton = new JButton("Summary");
		summaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SummaryGUI frame = new SummaryGUI(StudentEntranceGUI.this, sls);
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		panel.add(summaryButton);
		
		JLabel queryLabel = new JLabel("What would you like to do?");
		queryLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 25));
		queryLabel.setBounds(84, 53, 405, 39);
		contentPane.add(queryLabel);
	}

}
