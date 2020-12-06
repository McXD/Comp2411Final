package gui.teacher;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import connector.TeacherLoginSession;
import entity.Exam;
import util.TeacherUtil;

public class CheckRecordGUI extends JFrame {

	private JPanel contentPane;

	private JFrame parent;
	private TeacherLoginSession tls;
	private int selected = -1;
	private ArrayList<Exam> exams;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckRecordGUI frame = new CheckRecordGUI(null,null);
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
	public CheckRecordGUI(JFrame parent, TeacherLoginSession tls) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("Finished Exams");
		titleLabel.setBounds(158, 11, 257, 37);
		titleLabel.setFont(new Font("Arial Black", Font.BOLD, 26));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(titleLabel);
		
		JButton checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							setVisible(false);
							ReportGUI frame = new ReportGUI(CheckRecordGUI.this,tls,exams.get(selected));
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		checkButton.setBounds(474, 436, 89, 23);
		checkButton.setEnabled(false);
		contentPane.add(checkButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				parent.setVisible(true);
			}
		});
		exitButton.setBounds(10, 436, 89, 23);
		contentPane.add(exitButton);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		exams = tls.getAllExam();
		int count = 0;
		for (Exam e:exams) {
			listModel.add(count++, TeacherUtil.getExamInfoForTeacher(e));
		}
		
		JList<String> examList = new JList<String>(listModel);
		examList.setSelectedIndex(-1);
		examList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (examList.getSelectedIndex() == -1) {
					checkButton.setEnabled(false);
				}else {
					checkButton.setEnabled(true);
					selected = examList.getSelectedIndex();
				}
			}
		});
		examList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		examList.setBounds(0, 0, 1, 1);
		contentPane.add(examList);
		
		JScrollPane scrollPane = new JScrollPane(examList);
		scrollPane.setBounds(136, 59, 300, 350);
		contentPane.add(scrollPane);
	}
}
