package gui.teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import connector.TeacherLoginSession;
import entity.Exam;
import entity.Paper;
import util.TeacherUtil;

public class DesignPaperGUI extends JFrame {

	Paper paper;
	JFrame parent;
	Exam exam;
	TeacherLoginSession tls;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesignPaperGUI frame = new DesignPaperGUI(null,null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel contentPane;
	private JPanel mcTabPanel;
	private String answer = "";
	private boolean mcOptional = false;
	private boolean fbOptional = false;
	private boolean flOptional = false;
	
	int mandtot = 0, optot = 0;
	JLabel totalPointLabel;

	/**
	 * Create the frame.
	 */
	public DesignPaperGUI(JFrame parent, TeacherLoginSession tls, Exam exam) {
		this.tls = tls;
		this.exam = exam;
		this.paper = exam.withPaper;
		this.parent = parent;
		
		/*Main Panel*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/*Tabbed Pane*/
		
		totalPointLabel = new JLabel("Mandatory: 0          Optional: 0");
		totalPointLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(totalPointLabel, BorderLayout.NORTH);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		/*Button Group*/
		ButtonGroup answerButtonGroup = new ButtonGroup();
		
		/*FB Panel*/
		mcTabPanel = new JPanel();
		mcTabPanel.setAutoscrolls(true);
		tabbedPane.addTab("Multiple Choice Questions",null,mcTabPanel,null);
		mcTabPanel.setLayout(null);
		
		JTextArea mcQuestionTextArea = new JTextArea("Question goes here...");
		mcQuestionTextArea.setLineWrap(true);
		mcQuestionTextArea.setBounds(10, 44, 156, 70);
		mcTabPanel.add(mcQuestionTextArea);
		
		JLabel inputQuestionLabel = new JLabel("Question:");
		inputQuestionLabel.setBounds(130, 19, 124, 14);
		mcTabPanel.add(inputQuestionLabel);
		
		JLabel optionALabel = new JLabel("A.");
		optionALabel.setHorizontalAlignment(SwingConstants.RIGHT);
		optionALabel.setBounds(72, 127, 48, 25);
		mcTabPanel.add(optionALabel);
		
		JTextArea optionAText = new JTextArea();
		optionAText.setText("Option A");
		optionAText.setBounds(130, 154, 297, 25);
		mcTabPanel.add(optionAText);
		
		JScrollPane optionAScrollPane = new JScrollPane(optionAText);
		optionAScrollPane.setBounds(130, 127, 297, 35);
		mcTabPanel.add(optionAScrollPane);
		
		JTextArea optionBText = new JTextArea();
		optionBText.setText("Option B");
		optionBText.setBounds(130, 190, 297, 25);
		mcTabPanel.add(optionBText);
		
		JScrollPane optionBScrollPane = new JScrollPane(optionBText);
		optionBScrollPane.setBounds(130, 173, 297, 35);
		mcTabPanel.add(optionBScrollPane);
		
		JLabel optionBLabel = new JLabel("B.");
		optionBLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		optionBLabel.setBounds(72, 173, 48, 25);
		mcTabPanel.add(optionBLabel);
		
		JTextArea optionCText = new JTextArea();
		optionCText.setText("Option C");
		optionCText.setBounds(130, 231, 297, 25);
		mcTabPanel.add(optionCText);
		
		JScrollPane optionCScrollPane = new JScrollPane(optionCText);
		optionCScrollPane.setBounds(130, 219, 297, 33);
		mcTabPanel.add(optionCScrollPane);

		JLabel optionCLabel = new JLabel("C.");
		optionCLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		optionCLabel.setBounds(72, 219, 48, 25);
		mcTabPanel.add(optionCLabel);
		
		JTextArea optionDText = new JTextArea();
		optionDText.setText("Option D");
		optionDText.setBounds(130, 267, 297, 25);
		mcTabPanel.add(optionDText);
		
		JScrollPane optionDScrollPane = new JScrollPane(optionDText);
		optionDScrollPane.setBounds(130, 263, 297, 33);
		mcTabPanel.add(optionDScrollPane);
		
		JLabel optionDLabel = new JLabel("D.");
		optionDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		optionDLabel.setBounds(72, 263, 48, 25);
		mcTabPanel.add(optionDLabel);
		
		JRadioButton mcOptionButton = new JRadioButton("Optional");
		mcOptionButton.setBounds(130, 365, 109, 23);
		mcTabPanel.add(mcOptionButton);
		mcOptionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mcOptional = true;
			}
			
		});
		
		JLabel pointLabel = new JLabel("Point:");
		pointLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pointLabel.setBounds(330, 19, 48, 14);
		mcTabPanel.add(pointLabel);
		
		JTextField pointTextField = new JTextField();
		pointTextField.setBounds(387, 16, 40, 20);
		mcTabPanel.add(pointTextField);
		pointTextField.setColumns(10);
		
		JLabel answerLabel2 = new JLabel("Answer:");
		answerLabel2.setBounds(130, 306, 48, 14);
		mcTabPanel.add(answerLabel2);
		
		JRadioButton answerAButton = new JRadioButton("A");
		answerAButton.setBounds(130, 327, 40, 23);
		mcTabPanel.add(answerAButton);
		answerAButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				answer = "A";
			}
			
		});
		
		JRadioButton answerBButton = new JRadioButton("B");
		answerBButton.setBounds(172, 327, 40, 23);
		mcTabPanel.add(answerBButton);
		answerBButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				answer = "B";
			}
			
		});
		
		JRadioButton answerCButton = new JRadioButton("C");
		answerCButton.setBounds(214, 327, 40, 23);
		mcTabPanel.add(answerCButton);
		answerCButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				answer = "C";
			}
			
		});
		
		JRadioButton answerDButton = new JRadioButton("D");
		answerDButton.setBounds(256, 327, 40, 23);
		mcTabPanel.add(answerDButton);
		answerDButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				answer = "D";
			}
			
		});
		
		answerButtonGroup.add(answerAButton);
		answerButtonGroup.add(answerBButton);
		answerButtonGroup.add(answerCButton);
		answerButtonGroup.add(answerDButton);
		
		JButton mcNextButton = new JButton("Design Next");
		mcNextButton.setBounds(440, 400, 110, 25);
		mcTabPanel.add(mcNextButton);
		mcNextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//check if all blanks are filled
				String tempQuestionText = mcQuestionTextArea.getText();
				String tempOptionAText = optionAText.getText();
				String tempOptionBText = optionBText.getText();
				String tempOptionCText = optionCText.getText();
				String tempOptionDText = optionDText.getText();
				String tempPointText = pointTextField.getText();
				String tempAnswer = answer;
				
				if (tempQuestionText.isBlank() || tempOptionAText.isBlank() ||
						tempOptionBText.isBlank() || tempOptionCText.isBlank() ||
						tempOptionDText.isBlank() || tempPointText.isBlank() || tempAnswer.isEmpty()) {
					JOptionPane.showMessageDialog(new JPanel(), "You haven't finshed the design of this question yet"
							, "Unfinisded Question",JOptionPane.WARNING_MESSAGE);
				}else if (!mcOptional && mandtot + Integer.parseInt(tempPointText) > 100){
					JOptionPane.showMessageDialog(new JPanel(), "total point of mandatory questions exceeds 100"
							, "Warning",JOptionPane.WARNING_MESSAGE);
				}	
				else {
					int point = Integer.parseInt(tempPointText);
					paper.addMc(tempQuestionText, tempOptionAText, tempOptionBText,
							tempOptionCText, tempOptionDText, tempAnswer, point, mcOptional);
					if (mcOptional) optot += point;
					else mandtot += point;
					totalPointLabel.setText("Mandatory: " + mandtot + "          Optional: " + optot);
					mcQuestionTextArea.setText("Question goes here...");
					optionAText.setText("Option A");
					optionBText.setText("Option B");
					optionCText.setText("Option C");
					optionDText.setText("Option D");
					answerButtonGroup.clearSelection();
					if (mcOptional) mcOptionButton.doClick();
					mcOptional = false;
					pointTextField.setText("");
					answer = "";
				}
				
			}
			
		});
		
		
		JButton mcFinish = new JButton("Finished!");
		mcFinish.setBounds(10, 400, 110, 25);
		mcTabPanel.add(mcFinish);
		mcFinish.addActionListener(new FinishButtonActionListener());
		
		JScrollPane mcQuestionScrollPane = new JScrollPane(mcQuestionTextArea);
		mcQuestionScrollPane.setBounds(130, 44, 297, 72);
		mcTabPanel.add(mcQuestionScrollPane);
				
		
		
		/*fb question tab*/
		JPanel fbTabPanel = new JPanel();
		tabbedPane.addTab("Fill-in-Blank Questions", null, fbTabPanel, null);
		fbTabPanel.setLayout(null);
		
		JTextArea fbTextArea = new JTextArea("Question goes here");
		fbTextArea.setLineWrap(true);
		fbTextArea.setBounds(130, 86, 297, 102);
		fbTabPanel.add(fbTextArea);
		
		JScrollPane fbQuestionTextScrollPane = new JScrollPane(fbTextArea);
		fbQuestionTextScrollPane.setBounds(130, 86, 297, 102);
		fbTabPanel.add(fbQuestionTextScrollPane);
		
		JLabel questLabel = new JLabel("Please use '__' (double underscore) to indicate a black");
		questLabel.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 11));
		questLabel.setBounds(115, 23, 329, 27);
		fbTabPanel.add(questLabel);
		
		JLabel inputQuestionLabel2 = new JLabel("Question:");
		inputQuestionLabel2.setBounds(130, 61, 60, 20);
		fbTabPanel.add(inputQuestionLabel2);
		
		JLabel pointLabel2 = new JLabel("Point:");
		pointLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		pointLabel2.setBounds(335, 61, 48, 14);
		fbTabPanel.add(pointLabel2);
		
		JTextField fbPointTextField = new JTextField();
		fbPointTextField.setBounds(387, 55, 40, 20);
		fbTabPanel.add(fbPointTextField);
		fbPointTextField.setColumns(10);
		
		JRadioButton fbOptionButton = new JRadioButton("Optional");
		fbOptionButton.setBounds(130, 347, 109, 23);
		fbTabPanel.add(fbOptionButton);
		fbOptionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fbOptional = true;
			}
			
		});
		
		JLabel answerLabel = new JLabel("Answer:");
		answerLabel.setBounds(130, 235, 48, 14);
		fbTabPanel.add(answerLabel);
		
		JTextArea fbAnswerTextArea = new JTextArea();
		fbAnswerTextArea.setText("Answer goes here...");
		fbAnswerTextArea.setLineWrap(true);
		fbAnswerTextArea.setBounds(130, 260, 297, 76);
		fbTabPanel.add(fbAnswerTextArea);
		
		JScrollPane fbAnswerScrollPane = new JScrollPane(fbAnswerTextArea);
		fbAnswerScrollPane.setBounds(130, 260, 297, 76);
		fbTabPanel.add(fbAnswerScrollPane);
		
		JLabel questLabel2 = new JLabel("Please use ',' (a comma) to seperate the answers");
		questLabel2.setFont(new Font("Arial Black", Font.BOLD | Font.ITALIC, 11));
		questLabel2.setBounds(125, 210, 307, 14);
		fbTabPanel.add(questLabel2);
		
		JButton fbNextButton = new JButton("Design Next");
		fbNextButton.setBounds(440, 400, 110, 25);
		fbTabPanel.add(fbNextButton);
		fbNextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String tempQuestionText = fbTextArea.getText();
				String tempAnswerText = fbAnswerTextArea.getText();
				String tempPointText = fbPointTextField.getText();
				if (tempQuestionText.isBlank() || tempAnswerText.isBlank() || tempPointText.isEmpty()) {
					JOptionPane.showMessageDialog(new JPanel(), "You haven't finshed the design of this question yet"
							, "Unfinisded Question",JOptionPane.WARNING_MESSAGE);
				}else if (!fbOptional && mandtot + Integer.parseInt(tempPointText) > 100){
					JOptionPane.showMessageDialog(new JPanel(), "total point exceeds 100"
							, "Warning",JOptionPane.WARNING_MESSAGE);
				}
				else {
					int point = Integer.parseInt(tempPointText);
					paper.addFb(tempQuestionText, tempAnswerText, point, fbOptional);
					
					if (fbOptional) optot += point;
					else mandtot += point;
					totalPointLabel.setText("Mandatory: " + mandtot + "          Optional: " + optot);
					
					fbTextArea.setText("Question goes here...");
					fbAnswerTextArea.setText("Answer goes here...");
					fbPointTextField.setText("");
					if (fbOptional) fbOptionButton.doClick();
					fbOptional = false;
				}
			}
		});
		
		JButton fbFinish = new JButton("Finished!");
		fbFinish.setBounds(10, 400, 110, 25);
		fbTabPanel.add(fbFinish);
		fbFinish.addActionListener(new FinishButtonActionListener());
		
		/*FL Tab*/
		JPanel flTabPanel = new JPanel();
		tabbedPane.addTab("Full Length Questions", null, flTabPanel, null);
		flTabPanel.setLayout(null);
		
		JTextArea flTextArea = new JTextArea("Question goes here...");
		flTextArea.setLineWrap(true);
		flTextArea.setBounds(130, 67, 297, 216);
		flTabPanel.add(flTextArea);
		
		JScrollPane scrollPane = new JScrollPane(flTextArea);
		scrollPane.setBounds(130, 67, 297, 216);
		flTabPanel.add(scrollPane);
		
		JLabel inputQuestionLabel3 = new JLabel("Question:");
		inputQuestionLabel3.setBounds(130, 39, 63, 27);
		flTabPanel.add(inputQuestionLabel3);
		
		JLabel pointLabel3 = new JLabel("Point:");
		pointLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		pointLabel3.setBounds(337, 45, 48, 14);
		flTabPanel.add(pointLabel3);
		
		JTextField flPointTextField = new JTextField();
		flPointTextField.setBounds(387, 39, 40, 20);
		flTabPanel.add(flPointTextField);
		flPointTextField.setColumns(10);
		
		JRadioButton flOptionButton = new JRadioButton("Optional");
		flOptionButton.setBounds(130, 312, 109, 23);
		flTabPanel.add(flOptionButton);
		flOptionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				flOptional = true;
				
			}
			
		});
		
		JButton flNextButton = new JButton("Design Next");
		flNextButton.setBounds(440, 400, 110, 25);
		flTabPanel.add(flNextButton);
		flNextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String tempQuestionText = flTextArea.getText();
				String tempPointText = flPointTextField.getText();
				if (tempQuestionText.isBlank() || tempPointText.isEmpty()) {
					JOptionPane.showMessageDialog(new JPanel(), "You haven't finshed the design of this question yet"
							, "Unfinisded Question",JOptionPane.WARNING_MESSAGE);
				}else if (!flOptional && mandtot + Integer.parseInt(tempPointText) > 100){
					JOptionPane.showMessageDialog(new JPanel(), "total point exceeds 100"
							, "Warning",JOptionPane.WARNING_MESSAGE);
				}else {
					int point = Integer.parseInt(tempPointText);
					paper.addFl(tempQuestionText, point, fbOptional);
					
					if (flOptional) optot += point;
					else mandtot += point;
					totalPointLabel.setText("Mandatory: " + mandtot + "          Optional: " + optot);
					
					
					flTextArea.setText("Question goes here...");
					flPointTextField.setText("");
					if (flOptional) flOptionButton.doClick();
					flOptional = false;
					
				}
			}	
		});
		
		JButton flFinish = new JButton("Finished!");
		flFinish.setBounds(10, 400, 110, 25);
		flTabPanel.add(flFinish);
		flFinish.addActionListener(new FinishButtonActionListener());
		
	}
	
	private class FinishButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if (mandtot != 100) {
				JOptionPane.showMessageDialog(new JPanel(), "total point of mandatory questions not reaches 100"
						, "Warning",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			Object[] options = {"Yes", "No"};
			
			int choice = JOptionPane.showOptionDialog(new JPanel(), TeacherUtil.summarizePaper(paper) + "\nSure to quit?",
					"Confirmation",
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);
			if (choice == JOptionPane.YES_OPTION) {
				//store the exam to database
				tls.setExam(exam);
				
				//Prompt the user
				JOptionPane.showMessageDialog(new JPanel(), "You have successfully\nscheduled an exam!");
				
				//Quit this session
				dispose();
				parent.setVisible(true);
			}else {
				//do nothing and return
			}
		}
		
	}
}
