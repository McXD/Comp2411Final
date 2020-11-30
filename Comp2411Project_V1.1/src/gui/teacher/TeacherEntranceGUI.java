package gui.teacher;
import connector.TeacherLoginSession;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TeacherEntranceGUI extends JFrame {
	private TeacherLoginSession tls;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherEntranceGUI frame = new TeacherEntranceGUI(null);
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
	public TeacherEntranceGUI(TeacherLoginSession tls) {
		this.tls = tls;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(152, 159, 268, 151);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(3, 1, 2, 2));
		
		JButton newExamButton = new JButton("Schedule An Exam");
		newExamButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				setVisible(false);
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ScheExamGUI frame = new ScheExamGUI(TeacherEntranceGUI.this, tls);
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		panel.add(newExamButton);
		
		JButton markPaperButton = new JButton("Mark Papers");
		markPaperButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							MarkPaperGUI frame = new MarkPaperGUI(TeacherEntranceGUI.this, tls);
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		panel.add(markPaperButton);
		
		JButton checkRecordButton = new JButton("Check Records");
		checkRecordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(new JPanel(), "This functionality will be added soon...");
			}
		});
		panel.add(checkRecordButton);
		
		JLabel queryLabel = new JLabel("What would you like to do?");
		queryLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 25));
		queryLabel.setBounds(84, 53, 405, 39);
		contentPane.add(queryLabel);
	}
}
