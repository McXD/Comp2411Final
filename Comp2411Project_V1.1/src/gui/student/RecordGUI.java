package gui.student;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connector.StudentLoginSession;
import entity.Semester;
import entity.SemesterRecord;
import util.StudentUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class RecordGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordGUI frame = new RecordGUI(null,null,null);
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
	public RecordGUI(JFrame parent, StudentLoginSession sls, Semester sem) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585,505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("In " + sem.name());
		titleLabel.setBounds(206, 49, 160, 37);
		titleLabel.setFont(new Font("Arial Black", Font.BOLD, 26));
		contentPane.add(titleLabel);
		
		String[] colNames = {"Subject", "Grade", "Letter Grade"};
		ArrayList<SemesterRecord> records = sls.getSemeterRecords(sem);
		Object[][] content = StudentUtil.semeterRecord2Table(records);
		
		table = new JTable(content, colNames);
		table.setBounds(0, 0, 1, 1);
		contentPane.add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(138, 98, 296, 274);
		contentPane.add(scrollPane);
		
		JButton summaryButton = new JButton("View Summary");
		summaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(new JPanel(), StudentUtil.summarize(records));
			}
		});
		summaryButton.setBounds(380, 394, 115, 23);
		contentPane.add(summaryButton);
		
		JButton exitButton = new JButton("Exit");
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
