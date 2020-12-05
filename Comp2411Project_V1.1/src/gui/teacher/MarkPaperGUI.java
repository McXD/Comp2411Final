package gui.teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import connector.TeacherLoginSession;
import entity.Exam;
import util.TeacherUtil;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MarkPaperGUI extends JFrame {

	private JPanel contentPane;
	private JFrame parent;
	TeacherLoginSession tls;
	Exam selected;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MarkPaperGUI frame = new MarkPaperGUI(null, null);
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
	public MarkPaperGUI(JFrame parent, TeacherLoginSession tls) {
		this.parent = parent;
		this.tls = tls;
		
		ArrayList<Exam> finishedExams = TeacherUtil.getAllFinishedExams(tls.getAllExam());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		int ptr = 0;
		for (Exam e : finishedExams) {
			model.add(ptr++, TeacherUtil.getExamInfoForTeacher(e));
		}
		
		JList<String> examList = new JList<String>(model);
		examList.setBounds(121, 24, 561, 128);
		examList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(examList, BorderLayout.CENTER);
		examList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				selected = finishedExams.get(examList.getSelectedIndex());
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(examList);
		scrollPane.setBounds(135, 201, 303, 140);
		contentPane.add(scrollPane);
		
		JLabel infoLabel = new JLabel("Finished Exams...");
		infoLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 26));
		infoLabel.setBounds(147, 114, 278, 48);
		contentPane.add(infoLabel);
		
		JButton goButton = new JButton("Go");
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							TrulyMarkPaperGUI frame = new TrulyMarkPaperGUI(MarkPaperGUI.this,tls,selected);
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		goButton.setBounds(474, 436, 89, 23);
		contentPane.add(goButton);
		
		JLabel horrayLabel = new JLabel("Horray! No paper to be graded!");
		horrayLabel.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 26));
		horrayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		horrayLabel.setBounds(19, 197, 534, 76);
		contentPane.add(horrayLabel);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				parent.setVisible(true);
			}
		});
		
		exitButton.setBounds(10, 436, 89, 23);
		contentPane.add(exitButton);
		
		JLabel titleLabel = new JLabel(String.format("%8s %8s %15s", "Exam ID", "Class", "Subject"));
		titleLabel.setBounds(135, 185, 303, 14);
		contentPane.add(titleLabel);
		
		if (ptr == 0) {
			titleLabel.setVisible(false);
			horrayLabel.setVisible(true);
			examList.setVisible(false);
			scrollPane.setVisible(false);
		}else {
			horrayLabel.setVisible(false);
			examList.setSelectedIndex(0);
			selected = finishedExams.get(0);
			examList.setVisible(true);
		}
	}
}
