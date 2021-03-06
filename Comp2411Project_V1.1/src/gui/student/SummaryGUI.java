package gui.student;

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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import connector.StudentLoginSession;
import entity.Semester;
import util.StudentUtil;

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
		setBounds(100, 100, 391, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel infoLabel = new JLabel("Choose A Sememster");
		infoLabel.setFont(new Font("Arial Black", Font.PLAIN, 26));
		infoLabel.setBounds(36, 30, 306, 58);
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
		checkButton.setBounds(280, 265, 89, 23);
		checkButton.setEnabled(false);
		contentPane.add(checkButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				parent.setVisible(true);
			}
		});
		exitButton.setBounds(10, 265, 89, 23);
		contentPane.add(exitButton);
		
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		int ptr = 0;
		
		int startYear = Integer.parseInt(sls.getStudent().memberOf.cid.substring(0, 2)) + 2000;
		sems = StudentUtil.getAvailableSemesters(startYear);
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
		scrollPane.setBounds(105, 99, 169, 137);
		contentPane.add(scrollPane);
	
	}

}
