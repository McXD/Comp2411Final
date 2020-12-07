package gui.teacher;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import connector.TeacherLoginSession;
import entity.Exam;
import entity.ExamResultRecord;
import entity.SemesterRecord;
import util.CommonUtil;
import util.StudentUtil;
import util.TeacherUtil;

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
		
		JButton printButton = new JButton("Print");
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileWriter csvWriter = new FileWriter("Exam_" + exam.eid + ".csv");
					
					csvWriter.append(String.format("%s,%s,%s,%s\n", "Student ID", "Student Name", "Grade", "Letter Grade"));
					for (ExamResultRecord r : records) {
						csvWriter.append(String.format("%s,%s,%d,%s\n", r.student.sid, r.student.name, r.grade, CommonUtil.convertGrade(r.grade)));
					}
					csvWriter.append("\n\n\nHighest,Lowest,Average,Median\n");
					double[] temp = TeacherUtil.summarize0(records);
					for(int i=0;i<4;i++) {
						csvWriter.append(temp[i]+ (i == 3? "\n" : ","));
					}
					csvWriter.flush();
					csvWriter.close();
					
					JOptionPane.showMessageDialog(new JPanel(), "Record printed");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JPanel(), "Unexpected IO Error\n"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		printButton.setBounds(474, 436, 89, 23);
		contentPane.add(printButton);
	}
}
