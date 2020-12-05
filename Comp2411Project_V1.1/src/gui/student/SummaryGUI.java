package gui.student;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connector.StudentLoginSession;
import entity.Semester;
import util.StudentUtil;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class SummaryGUI extends JFrame {

	private JPanel contentPane;
	
	private JFrame parent;
	private StudentLoginSession sls;
	private int selected = -1;
	private ArrayList<Semester> sems;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SummaryGUI frame = new SummaryGUI(null, null);
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
	public SummaryGUI(JFrame parent, StudentLoginSession sls) {
		this.parent = parent;
		this.sls = sls;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel infoLabel = new JLabel("Choose A Sememster");
		infoLabel.setFont(new Font("Arial Black", Font.PLAIN, 26));
		infoLabel.setBounds(133, 69, 306, 58);
		contentPane.add(infoLabel);
		
		JButton checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							RecordGUI frame = new RecordGUI(SummaryGUI.this, sls, sems.get(selected));
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
		int ptr = 0;
		sems = StudentUtil.getAvailableSememsters();
		for (Semester s: sems) {
			listModel.add(ptr++, s.name());
		}
			
		JList<String> semesterList = new JList<String>(listModel);
		semesterList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (semesterList.getSelectedIndex() == -1) {
					checkButton.setEnabled(false);
				}else {
					checkButton.setEnabled(true);
					selected = semesterList.getSelectedIndex();
				}
			}
		});
		semesterList.setBounds(0, 0, 1, 1);
		contentPane.add(semesterList);
		
		JScrollPane scrollPane = new JScrollPane(semesterList);
		scrollPane.setBounds(192, 147, 188, 250);
		contentPane.add(scrollPane);
	
	}

}
