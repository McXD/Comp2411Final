package gui.student;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import util.StudentUtil;
import entity.Exam;
import java.util.ArrayList;
import connector.StudentLoginSession;

public class SitExamGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable examTable;
	
	private JFrame parent;
	private StudentLoginSession sls;
	private boolean hasExam;
	private JScrollPane scrollPane;
	private JLabel infoLabel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SitExamGUI frame = new SitExamGUI(null,new StudentLoginSession("19084103d","00000000"));
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
	public SitExamGUI(JFrame parent, StudentLoginSession sls) {
		this.parent = parent;
		this.sls = sls;
		
		//All pending exams in order
		ArrayList<Exam> pending = StudentUtil.getUnsatExams(sls.getAllExams());
		hasExam = !pending.isEmpty();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		infoLabel = new JLabel("Pending Exams...");
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 28));
		infoLabel.setBounds(131, 89, 310, 70);
		contentPane.add(infoLabel);
		
		Object[] tableTitle = {"Examiner","Subject","Start","Duration"};
		Object[][] tableContent = StudentUtil.makeTableRocords(pending);
		examTable = new JTable(tableContent,tableTitle);
		examTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		examTable.setBounds(136, 184, 300, 167);
		contentPane.add(examTable);
		
		scrollPane = new JScrollPane(examTable);
		scrollPane.setBounds(77, 184, 419, 167);
		contentPane.add(scrollPane);
		
		JLabel hoorayLabel = new JLabel("Hooray! There's no exam!");
		hoorayLabel.setFont(new Font("Lucida Console", Font.ITALIC, 25));
		hoorayLabel.setBounds(97, 245, 378, 34);
		contentPane.add(hoorayLabel);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				parent.setVisible(true);
			}
		});
		backButton.setBounds(10, 436, 109, 23);
		contentPane.add(backButton);
		
		JButton sitButton = new JButton("Sit Next Exam");
		sitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!StudentUtil.canSitNow(pending.get(0).start)) {
					JOptionPane.showMessageDialog(new JPanel(), "This entrance shall only open within \n 5 mins of the start time of the first pending exam");
				}else {
					setVisible(false);
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								doPaperGUI frame = new doPaperGUI(SitExamGUI.this, sls, pending.get(0));
								frame.setLocationRelativeTo(null);
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		sitButton.setBounds(454, 436, 109, 23);
		contentPane.add(sitButton);
		
		//layout according to hasExam
		if (hasExam) {
			//display the exams in a table
			examTable.setVisible(true);
			hoorayLabel.setVisible(false);
			sitButton.setEnabled(true);
			
		}else {
			examTable.setVisible(false);
			scrollPane.setVisible(false);
			hoorayLabel.setVisible(true);
			sitButton.setEnabled(false);
		}
		
		
		
	}
}
