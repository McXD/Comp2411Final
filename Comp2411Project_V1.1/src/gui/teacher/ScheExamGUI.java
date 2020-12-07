package gui.teacher;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import connector.TeacherLoginSession;
import entity.Class0;
import entity.Exam;
import entity.ExamInfo;
import entity.Paper;
import entity.Semester;
import entity.Subject;
import util.TeacherUtil;

public class ScheExamGUI extends JFrame {
	private TeacherLoginSession tls;
	private Exam exam;
	
	private JPanel contentPane;
	private JTextField startText;
	private JTextField subjectText;
	private JTextField classText;
	private JTextField examIDText;
	private JTextField durationText;
	private JTextField paperIDText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScheExamGUI frame = new ScheExamGUI(null, null);
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
	public ScheExamGUI(JFrame parent, TeacherLoginSession tls) {
		this.tls = tls;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel examInfoLabel = new JLabel("Exam Information");
		examInfoLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 30));
		examInfoLabel.setBounds(126, 63, 320, 58);
		contentPane.add(examInfoLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(139, 186, 294, 152);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(6, 2, 2, 2));
		
		JLabel examIDLabel = new JLabel("Exam ID: ");
		examIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(examIDLabel);
		
		examIDText = new JTextField();
		panel.add(examIDText);
		examIDText.setColumns(10);
		
		JLabel classLabel = new JLabel("Class: ");
		classLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(classLabel);
		
		classText = new JTextField();
		panel.add(classText);
		classText.setColumns(10);
		
		JLabel subjectLabel = new JLabel("Subject: ");
		subjectLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(subjectLabel);
		
		subjectText = new JTextField();
		panel.add(subjectText);
		subjectText.setColumns(10);
		
		JLabel startLabel = new JLabel("Start: ");
		startLabel.setToolTipText("(Input start time should be like 01-Dec-2020 12:00:00 AM)");
		startLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(startLabel);
		
		startText = new JTextField();
		startText.setToolTipText("(Input start time should be like 01-Dec-2020 12:00:00 AM)");
		panel.add(startText);
		startText.setColumns(10);
		
		JLabel durationLabel = new JLabel("Duration(in minute): ");
		durationLabel.setToolTipText("");
		durationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(durationLabel);
		
		durationText = new JTextField();
		panel.add(durationText);
		durationText.setColumns(10);
		
		JLabel paperIDLabel = new JLabel("Paper ID: ");
		paperIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(paperIDLabel);
		
		paperIDText = new JTextField();
		panel.add(paperIDText);
		paperIDText.setColumns(10);
		
		JButton designPaperButton = new JButton("Design Paper");
		designPaperButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//retrieve the information
				//TODO
				
				try {
					//retrieve information
					exam = new entity.Exam(
							tls.getTeacher(),
							examIDText.getText(),
							new Class0(classText.getText()),
							new Subject(subjectText.getText()),
							new Paper(paperIDText.getText()),
							LocalDateTime.parse(startText.getText(), DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a")),
							Integer.parseInt(durationText.getText()));
					
					//check authority
					if (!TeacherUtil.checkAuthority(tls.getTeacher(), exam.forClass, exam.onSubject)) {
						throw new Exception("You do not have authority to set this exam\n");
					}
					
					//check duplicity
					ExamInfo toSet = new ExamInfo(tls.getTeacher().tid, classText.getText(),
										subjectText.getText(), LocalDateTime.parse(startText.getText(), DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a")),
												examIDText.getText());
					ArrayList<ExamInfo> already = tls.getExmInfo();
					int sig = TeacherUtil.checkDuplicity(already, toSet);
					if (sig == 1) {
						throw new Exception("Duplicated Exam ID");
					}else if (sig == 2) {
						throw new Exception(String.format("You have already set an exam\n for class %s on subject %s in sememster %s",
																classText.getText(), subjectText.getText(),
																Semester.inSemester(LocalDateTime.parse(startText.getText(), DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a")),
																	Integer.parseInt(classText.getText().strip().substring(0, 2)) + 2000).name()));
					}
					
										
					//call DesignPaperGUI
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								setVisible(false);
								DesignPaperGUI frame = new DesignPaperGUI(ScheExamGUI.this, tls, exam);
								frame.setLocationRelativeTo(null);
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					
				}catch(DateTimeParseException | ParseException pe) {
					JOptionPane.showMessageDialog(new JPanel(), "Wrong input format\n"+pe.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(new JPanel(), e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
		});
		designPaperButton.setBounds(445, 427, 118, 32);
		contentPane.add(designPaperButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				parent.setVisible(true);
			}
		});
		exitButton.setBounds(10, 427, 118, 32);
		contentPane.add(exitButton);
	}
}
