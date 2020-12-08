package gui.teacher;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import connector.TeacherLoginSession;
import entity.AnswerSheet;
import entity.Exam;

public class TrulyMarkPaperGUI extends JFrame {

	private JPanel contentPane;
	private JTextField markTextField;
	private int ptr;
	private int number;
	private JFrame parent;
	private TeacherLoginSession tls;
	private Exam exam;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrulyMarkPaperGUI frame = new TrulyMarkPaperGUI(null,null,null);
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
	public TrulyMarkPaperGUI(JFrame parent, TeacherLoginSession tls, Exam exam) {
		this.parent = parent;
		this.tls = tls;
		this.exam = exam;
		
		ArrayList<AnswerSheet> sheets = tls.getSheetForExam(exam);
		number = sheets.size();
		ptr = 0;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel indexLabel = new JLabel("1/10: ");
		indexLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		indexLabel.setBounds(41, 11, 48, 14);
		contentPane.add(indexLabel);
		
		
		JTextArea sheetTextArea = new JTextArea();
		sheetTextArea.setEditable(false);
		sheetTextArea.setBounds(10, 0, 5, 15);
		contentPane.add(sheetTextArea);
		
		JScrollPane scrollPane = new JScrollPane(sheetTextArea);
		scrollPane.setBounds(99, 177, 374, 123);
		contentPane.add(scrollPane);
		
		markTextField = new JTextField("");
		markTextField.setBounds(99, 314, 59, 20);
		contentPane.add(markTextField);
		markTextField.setColumns(10);
		
		JLabel markLabel = new JLabel("Mark:");
		markLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		markLabel.setBounds(41, 314, 48, 14);
		contentPane.add(markLabel);
		
		JLabel feedbackLabel = new JLabel("Feedback:");
		feedbackLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		feedbackLabel.setBounds(30, 353, 59, 14);
		contentPane.add(feedbackLabel);
		
		JTextArea feedbackText = new JTextArea("The teacher was lazy and wrote nothing");
		feedbackText.setBounds(0, 0, 5, 15);
		contentPane.add(feedbackText);
		
		JScrollPane scrollPane_1 = new JScrollPane(feedbackText);
		scrollPane_1.setBounds(99, 353, 374, 54);
		contentPane.add(scrollPane_1);
		
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (markTextField.getText().isBlank() || markTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JPanel(), "You haven't mark this sheet yet!");
				}else {
					tls.grade(sheets.get(ptr), Integer.parseInt(markTextField.getText()),
							feedbackText.getText()==null ? "This teacher was lazy and wrote nothing" : feedbackText.getText());
					markTextField.setText("");
					feedbackText.setText("This teacher was lazy and wrote nothing");
				}
			}
		});
		confirmButton.setBounds(242, 436, 89, 23);
		contentPane.add(confirmButton);
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (markTextField.getText().isBlank() || markTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JPanel(), "You haven't mark this sheet yet!");
				}else {
					tls.grade(sheets.get(ptr), Integer.parseInt(markTextField.getText()),
							feedbackText.getText()==null ? "This teacher was lazy and wrote nothing" : feedbackText.getText());
					markTextField.setText("");
					feedbackText.setText("This teacher was lazy and wrote nothing");
				}
				
				//forward to the next question
				ptr++;
				indexLabel.setText(ptr+1 + "/" + number);
				sheetTextArea.setText(sheets.get(ptr).toString(1));
				
				//this sheet is the last one
				if (ptr == number-1) nextButton.setEnabled(false);
			}
		});
		nextButton.setBounds(474, 436, 89, 23);
		contentPane.add(nextButton);

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showConfirmDialog(new JPanel(), "Sure to quit?");
				dispose();
				parent.setVisible(true);
			}
		});
		exitButton.setBounds(10, 436, 89, 23);
		contentPane.add(exitButton);
		
		JLabel questionLabel = new JLabel("Question:");
		questionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		questionLabel.setBounds(30, 36, 59, 14);
		contentPane.add(questionLabel);
		
		JTextArea questionText = new JTextArea();
		questionText.setBounds(0, 0, 5, 15);
		contentPane.add(questionText);
		
		JScrollPane questionPane = new JScrollPane(questionText);
		questionPane.setBounds(99, 36, 374, 130);
		contentPane.add(questionPane);
		
		JLabel answerLabel = new JLabel("Answer:");
		answerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		answerLabel.setBounds(41, 177, 48, 14);
		contentPane.add(answerLabel);
		
		

		/*Initialization*/
		if (number == 0) {
			indexLabel.setText("0/0");
			confirmButton.setEnabled(false);
			nextButton.setEnabled(false);
		}else {
			indexLabel.setText(1 + "/" + number);
			
			sheetTextArea.setText(sheets.get(0).toString(1));
			questionText.setText(exam.withPaper.toString(1));
			
			if (number == 1) nextButton.setEnabled(false);
		}
	}
}
