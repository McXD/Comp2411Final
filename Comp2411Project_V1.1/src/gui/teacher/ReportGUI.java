package gui.teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connector.TeacherLoginSession;
import entity.Exam;
import entity.ExamResultRecord;
import util.TeacherUtil;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReportGUI extends JFrame {

	private JPanel contentPane;
	private JTable recordTable;
	private JButton exitButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportGUI frame = new ReportGUI(null,null,null);
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
	public ReportGUI(JFrame parent, TeacherLoginSession tls, Exam exam) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ArrayList<ExamResultRecord> records = tls.getRecordForExam(exam);
		
		String[] colNames = {"Student ID", "Student Name", "Grade", "Letter Grade"};
		Object[][] content = TeacherUtil.record2Table(records);
		
		recordTable = new JTable(content, colNames);
		recordTable.setBounds(116, 123, 341, 223);
		contentPane.add(recordTable);
		
		JScrollPane scrollPane = new JScrollPane(recordTable);
		scrollPane.setBounds(84, 123, 404, 229);
		contentPane.add(scrollPane);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				parent.setVisible(true);
			}
		});
		exitButton.setBounds(10, 436, 89, 23);
		contentPane.add(exitButton);
	}

}
