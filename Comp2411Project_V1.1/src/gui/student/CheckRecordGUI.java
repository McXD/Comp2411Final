package gui.student;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import connector.StudentLoginSession;
import entity.StudentRecord;
import util.StudentUtil;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CheckRecordGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable recordTable;
	
	private StudentLoginSession sls;
	private JFrame parent;
	private JButton exitButton;

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
	public CheckRecordGUI(JFrame parent, StudentLoginSession sls) {
		this.parent = parent;
		this.sls = sls;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] colNames = {"Hosting Instructor", "Exam ID", "Grade", "Feedback"};
		Object[][] table = StudentUtil.getRecordTable(sls.checkRecord());
		
		recordTable = new JTable(table, colNames);
		recordTable.setEnabled(false);
		recordTable.setBounds(0, 0, 1, 1);
		contentPane.add(recordTable);
		
		JScrollPane scrollPane = new JScrollPane(recordTable);
		scrollPane.setBounds(80, 147, 413, 196);
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
