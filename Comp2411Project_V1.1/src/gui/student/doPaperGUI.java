package gui.student;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import connector.StudentLoginSession;
import entity.AnswerSheet;
import entity.Exam;
import entity.Paper;
import util.StudentUtil;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import entity.question.*;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.beans.PropertyChangeEvent;

public class doPaperGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel mcTabPanel;
	
	private int mcCount;
	private int mcCurrent; //index of the question list
	private int fbCount;
	private int fbCurrent;
	private int flCount;
	private int flCurrent;
	private String[] mcAnswer;
	private String[] fbAnswer;
	private String[] flAnswer;
	private Duration dura;
	private Timer timer;
	
	private JButton mcNextButton, fbNextButton, flNextButton;
	private JButton mcLastButton, fbLastButton, flLastButton;
	private JLabel mcIndexLabel, fbIndexLabel, flIndexLabel;
	
	private JFrame parent;
	private StudentLoginSession sls;
	private Exam exam;
	private Paper paper;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					doPaperGUI frame = new doPaperGUI(null,null,null);
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
	public doPaperGUI(JFrame parent, StudentLoginSession sls, Exam sitting) {
		this.parent = parent;
		this.sls = sls;
		this.exam = sitting;
		this.paper = sitting.withPaper;
		this.dura = Duration.ofMinutes(exam.duration);
		
		ArrayList<McQuestion> mcs = paper.getMcs();
		mcCount = mcs.size();
		mcAnswer = new String[mcCount];
		ArrayList<FbQuestion> fbs = paper.getFbs();
		fbCount = fbs.size();
		fbAnswer = new String[fbCount];
		ArrayList<FlQuestion> fls = paper.getFls();
		flCount = fls.size();
		flAnswer = new String[flCount];
		
		Arrays.fill(mcAnswer, " ");
		Arrays.fill(fbAnswer, " ");
		Arrays.fill(flAnswer, " ");
		
		/*Main Panel*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/*Tab Pane*/
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		/*Button Group*/
		ButtonGroup answerButtonGroup = new ButtonGroup();
		
		/*FB Panel*/
		mcTabPanel = new JPanel();
		mcTabPanel.setAutoscrolls(true);
		tabbedPane.addTab("Multiple Choice Questions",null,mcTabPanel,null);
		mcTabPanel.setLayout(null);
		
		JTextArea mcQuestionTextArea = new JTextArea();
		mcQuestionTextArea.setEditable(false);
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
		optionAText.setEditable(false);
		optionAText.setText("Option A");
		optionAText.setBounds(130, 154, 297, 25);
		mcTabPanel.add(optionAText);
		
		JScrollPane optionAScrollPane = new JScrollPane(optionAText);
		optionAScrollPane.setBounds(130, 127, 297, 35);
		mcTabPanel.add(optionAScrollPane);
		
		JTextArea optionBText = new JTextArea();
		optionBText.setEditable(false);
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
		optionCText.setEditable(false);
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
		optionDText.setEditable(false);
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
		mcOptionButton.setEnabled(false);
		mcOptionButton.setSelected(true);
		mcOptionButton.setBounds(130, 365, 109, 23);
		mcTabPanel.add(mcOptionButton);
		
		JLabel pointLabel = new JLabel("Point:");
		pointLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pointLabel.setBounds(330, 19, 48, 14);
		mcTabPanel.add(pointLabel);
		
		JTextField mcPointTextField = new JTextField();
		mcPointTextField.setEditable(false);
		mcPointTextField.setBounds(387, 16, 40, 20);
		mcTabPanel.add(mcPointTextField);
		mcPointTextField.setColumns(10);
		
		JLabel answerLabel2 = new JLabel("Answer:");
		answerLabel2.setBounds(130, 306, 48, 14);
		mcTabPanel.add(answerLabel2);
		
		JRadioButton answerAButton = new JRadioButton("A");
		answerAButton.setBounds(130, 327, 40, 23);
		mcTabPanel.add(answerAButton);

		
		JRadioButton answerBButton = new JRadioButton("B");
		answerBButton.setBounds(172, 327, 40, 23);
		mcTabPanel.add(answerBButton);

		
		JRadioButton answerCButton = new JRadioButton("C");
		answerCButton.setBounds(214, 327, 40, 23);
		mcTabPanel.add(answerCButton);

		
		JRadioButton answerDButton = new JRadioButton("D");
		answerDButton.setBounds(256, 327, 40, 23);
		mcTabPanel.add(answerDButton);
		
		answerButtonGroup.add(answerAButton);
		answerButtonGroup.add(answerBButton);
		answerButtonGroup.add(answerCButton);
		answerButtonGroup.add(answerDButton);
		
		mcNextButton = new JButton("Next");
		mcNextButton.setBounds(440, 400, 110, 25);
		mcTabPanel.add(mcNextButton);
		mcNextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//save this answer
				//refill with next question
				//caller has ensured there's a question
				if (answerAButton.isSelected()) mcAnswer[mcCurrent] = "A";
				else if (answerBButton.isSelected()) mcAnswer[mcCurrent] = "B";
				else if (answerCButton.isSelected()) mcAnswer[mcCurrent] = "C";
				else if (answerDButton.isSelected()) mcAnswer[mcCurrent] = "D";
				
				int next = ++mcCurrent;
				
				mcIndexLabel.setText((next+1) + "/" + mcCount);
				mcQuestionTextArea.setText(mcs.get(next).text);
				optionAText.setText(mcs.get(next).optionA);
				optionBText.setText(mcs.get(next).optionB);
				optionCText.setText(mcs.get(next).optionC);
				optionDText.setText(mcs.get(next).optionD);
				mcPointTextField.setText(mcs.get(next).point + "");
				mcOptionButton.setSelected(mcs.get(next).flag);
				//handle the answer
				String answer = mcAnswer[next];
				if (answer.compareTo("A") == 0) answerAButton.setSelected(true);
				else if (answer.compareTo("B") == 0) answerBButton.setSelected(true);
				else if (answer.compareTo("C") == 0) answerCButton.setSelected(true);
				else if (answer.compareTo("D") == 0) answerDButton.setSelected(true);
				else answerButtonGroup.clearSelection();
				
				mcNextButton.setEnabled(mcCurrent!=mcCount-1);
				mcLastButton.setEnabled(true);
			}
			
		});
		
		
		JButton mcFinish = new JButton("Finished!");
		mcFinish.setBounds(10, 400, 110, 25);
		mcTabPanel.add(mcFinish);
		mcFinish.addActionListener(new FinishButtonActionListener());
		
		JScrollPane mcQuestionScrollPane = new JScrollPane(mcQuestionTextArea);
		mcQuestionScrollPane.setBounds(130, 44, 297, 72);
		mcTabPanel.add(mcQuestionScrollPane);
		
		mcLastButton = new JButton("Last");
		mcLastButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//save this answer
				//refill with last question
				//caller has to ensure that there's a question
				if (answerAButton.isSelected()) mcAnswer[mcCurrent] = "A";
				else if (answerBButton.isSelected()) mcAnswer[mcCurrent] = "B";
				else if (answerCButton.isSelected()) mcAnswer[mcCurrent] = "C";
				else if (answerDButton.isSelected()) mcAnswer[mcCurrent] = "D";
				
				int last = --mcCurrent;
				mcIndexLabel.setText((last+1) + "/" + mcCount);
				mcQuestionTextArea.setText(mcs.get(last).text);
				optionAText.setText(mcs.get(last).optionA);
				optionBText.setText(mcs.get(last).optionB);
				optionCText.setText(mcs.get(last).optionC);
				optionDText.setText(mcs.get(last).optionD);
				mcPointTextField.setText(mcs.get(last).point + "");
				mcOptionButton.setSelected(mcs.get(last).flag);
				
				String answer = mcs.get(last).answer;
				if (answer.compareTo("A") == 0) answerAButton.setSelected(true);
				else if (answer.compareTo("B") == 0) answerBButton.setSelected(true);
				else if (answer.compareTo("C") == 0) answerCButton.setSelected(true);
				else if (answer.compareTo("D") == 0) answerDButton.setSelected(true);
				else answerButtonGroup.clearSelection();
				
				mcNextButton.setEnabled(true);
				mcLastButton.setEnabled(mcCurrent != 0);
			}
		});
		mcLastButton.setBounds(318, 400, 109, 24);
		mcTabPanel.add(mcLastButton);
		
		mcIndexLabel = new JLabel();
		mcIndexLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mcIndexLabel.setBounds(72, 44, 48, 14);
		mcTabPanel.add(mcIndexLabel);
		
		JButton mcConfirmButton = new JButton("Confirm");
		mcConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (answerAButton.isSelected()) mcAnswer[mcCurrent] = "A";
				else if (answerBButton.isSelected()) mcAnswer[mcCurrent] = "B";
				else if (answerCButton.isSelected()) mcAnswer[mcCurrent] = "C";
				else if (answerDButton.isSelected()) mcAnswer[mcCurrent] = "D";
			}
		});
		mcConfirmButton.setBounds(387, 327, 89, 23);
		mcTabPanel.add(mcConfirmButton);
		
		if (mcCount == 0) {
			//cannot do anything
			mcIndexLabel.setText("0/0");
			mcQuestionTextArea.setText("");
			optionAText.setText("");
			optionBText.setText("");
			optionCText.setText("");
			optionDText.setText("");
			mcPointTextField.setText("0");
			mcOptionButton.setSelected(false);
			answerAButton.setEnabled(false);
			answerBButton.setEnabled(false);
			answerCButton.setEnabled(false);
			answerDButton.setEnabled(false);
			mcNextButton.setEnabled(false);
			mcLastButton.setEnabled(false);
			
		}else {
			//initialize
			mcCurrent = 0;
			mcIndexLabel.setText("1/" + mcCount);
			mcQuestionTextArea.setText(mcs.get(0).text);
			optionAText.setText(mcs.get(0).optionA);
			optionBText.setText(mcs.get(0).optionB);
			optionCText.setText(mcs.get(0).optionC);
			optionDText.setText(mcs.get(0).optionD);
			mcPointTextField.setText(mcs.get(0).point + "");
			mcOptionButton.setSelected(mcs.get(0).flag);
			mcNextButton.setEnabled(mcCount!=1);
			mcLastButton.setEnabled(false);
		}
		
		
		/*fb question tab*/
		JPanel fbTabPanel = new JPanel();
		tabbedPane.addTab("Fill-in-Blank Questions", null, fbTabPanel, null);
		fbTabPanel.setLayout(null);
		
		JTextArea fbTextArea = new JTextArea("Question goes here");
		fbTextArea.setEditable(false);
		fbTextArea.setLineWrap(true);
		fbTextArea.setBounds(30, 61, 297, 102);
		fbTabPanel.add(fbTextArea);
		
		JScrollPane fbQuestionTextScrollPane = new JScrollPane(fbTextArea);
		fbQuestionTextScrollPane.setBounds(130, 86, 297, 102);
		fbTabPanel.add(fbQuestionTextScrollPane);
		
		JLabel inputQuestionLabel2 = new JLabel("Question:");
		inputQuestionLabel2.setBounds(130, 61, 60, 20);
		fbTabPanel.add(inputQuestionLabel2);
		
		JLabel pointLabel2 = new JLabel("Point:");
		pointLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		pointLabel2.setBounds(335, 61, 48, 14);
		fbTabPanel.add(pointLabel2);
		
		JTextField fbPointTextField = new JTextField();
		fbPointTextField.setEditable(false);
		fbPointTextField.setBounds(387, 55, 40, 20);
		fbTabPanel.add(fbPointTextField);
		fbPointTextField.setColumns(10);
		
		JRadioButton fbOptionButton = new JRadioButton("Optional");
		fbOptionButton.setEnabled(false);
		fbOptionButton.setBounds(130, 347, 109, 23);
		fbTabPanel.add(fbOptionButton);
		
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
		
		fbNextButton = new JButton("Next");
		fbNextButton.setBounds(440, 400, 110, 25);
		fbTabPanel.add(fbNextButton);
		fbNextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//save this answer
				//refill with next question
				//caller has ensured there's a question
				fbAnswer[fbCurrent] = fbAnswerTextArea.getText();
				
				int next = ++fbCurrent;
				
				fbIndexLabel.setText((next+1) + "/" + fbCount);
				fbTextArea.setText(fbs.get(next).text);
				fbPointTextField.setText(fbs.get(next).point + "");
				fbOptionButton.setSelected(fbs.get(next).flag);
				fbAnswerTextArea.setText(fbAnswer[next]);
				
				fbNextButton.setEnabled(fbCurrent!=fbCount-1);
				fbLastButton.setEnabled(true);
			}
		});
		
		JButton fbFinish = new JButton("Finished!");
		fbFinish.setBounds(10, 400, 110, 25);
		fbTabPanel.add(fbFinish);
		
		fbLastButton = new JButton("Last");
		fbLastButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//save this answer
				//refill with last question
				//caller has ensured there's a question
				fbAnswer[fbCurrent] = fbAnswerTextArea.getText();
				
				int last = --fbCurrent;
				
				fbIndexLabel.setText((last+1) + "/" + fbCount);
				fbTextArea.setText(fbs.get(last).text);
				fbPointTextField.setText(fbs.get(last).point + "");
				fbOptionButton.setSelected(fbs.get(last).flag);
				fbAnswerTextArea.setText(fbAnswer[last]);
				
				fbNextButton.setEnabled(true);
				fbLastButton.setEnabled(fbCurrent != 0);
			}
		});
		fbLastButton.setBounds(318, 400, 109, 24);
		fbTabPanel.add(fbLastButton);
		
		fbIndexLabel = new JLabel();
		fbIndexLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		fbIndexLabel.setBounds(72, 86, 48, 14);
		fbTabPanel.add(fbIndexLabel);
		
		JButton fbConfirmButton = new JButton("Confirm");
		fbConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fbAnswer[fbCurrent] = fbAnswerTextArea.getText();
			}
		});
		fbConfirmButton.setBounds(338, 347, 89, 23);
		fbTabPanel.add(fbConfirmButton);
		
		if (fbCount == 0) {
			//cannot do anything
			fbIndexLabel.setText("0/0");
			fbTextArea.setText("");
			fbPointTextField.setText("0");
			fbOptionButton.setSelected(false);
			fbNextButton.setEnabled(false);
			fbLastButton.setEnabled(false);
			
		}else {
			//initialize
			fbCurrent = 0;
			fbIndexLabel.setText("1/" + fbCount);
			fbTextArea.setText(fbs.get(0).text);
			fbPointTextField.setText(fbs.get(0).point + "");
			fbOptionButton.setSelected(fbs.get(0).flag);
			fbNextButton.setEnabled(fbCount!=1);
			fbLastButton.setEnabled(false);
		}
		
		
		fbFinish.addActionListener(new FinishButtonActionListener());
		
		/*FL Tab*/
		JPanel flTabPanel = new JPanel();
		tabbedPane.addTab("Full Length Questions", null, flTabPanel, null);
		flTabPanel.setLayout(null);
		
		JTextArea flTextArea = new JTextArea("Question goes here...");
		flTextArea.setEditable(false);
		flTextArea.setLineWrap(true);
		flTextArea.setBounds(130, 67, 297, 216);
		flTabPanel.add(flTextArea);
		
		JScrollPane scrollPane = new JScrollPane(flTextArea);
		scrollPane.setBounds(130, 67, 297, 112);
		flTabPanel.add(scrollPane);
		
		JLabel inputQuestionLabel3 = new JLabel("Question:");
		inputQuestionLabel3.setBounds(130, 39, 63, 27);
		flTabPanel.add(inputQuestionLabel3);
		
		JLabel pointLabel3 = new JLabel("Point:");
		pointLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		pointLabel3.setBounds(337, 45, 48, 14);
		flTabPanel.add(pointLabel3);
		
		JTextField flPointTextField = new JTextField();
		flPointTextField.setEditable(false);
		flPointTextField.setBounds(387, 39, 40, 20);
		flTabPanel.add(flPointTextField);
		flPointTextField.setColumns(10);
		
		
		JTextArea flAnswerArea = new JTextArea();
		flAnswerArea.setBounds(0, 0, 5, 15);
		flTabPanel.add(flAnswerArea);
		
		JScrollPane flAnswerScrollPane = new JScrollPane(flAnswerArea);
		flAnswerScrollPane.setBounds(130, 223, 297, 100);
		flTabPanel.add(flAnswerScrollPane);
		
		
		JRadioButton flOptionButton = new JRadioButton("Optional");
		flOptionButton.setEnabled(false);
		flOptionButton.setBounds(130, 330, 109, 23);
		flTabPanel.add(flOptionButton);
		
		flNextButton = new JButton("Next");
		flNextButton.setBounds(440, 400, 110, 25);
		flTabPanel.add(flNextButton);
		flNextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//save this answer
				//refill with next question
				//caller has ensured there's a question
				flAnswer[flCurrent] = flAnswerArea.getText();
				
				int next = ++flCurrent;
				
				flIndexLabel.setText((next+1) + "/" + flCount);
				flTextArea.setText(fls.get(next).text);
				flPointTextField.setText(fls.get(next).point + "");
				flOptionButton.setSelected(fls.get(next).flag);
				flAnswerArea.setText(flAnswer[next]);
				
				flNextButton.setEnabled(flCurrent!=flCount-1);
				flLastButton.setEnabled(true);
			}	
		});
		
		JButton flFinish = new JButton("Finished!");
		flFinish.setBounds(10, 400, 110, 25);
		flTabPanel.add(flFinish);
		
		flLastButton = new JButton("Last");
		flLastButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//save this answer
				//refill with last question
				//caller has ensured there's a question
				flAnswer[flCurrent] = flAnswerArea.getText();
				
				int last = --flCurrent;
				
				flIndexLabel.setText((last+1) + "/" + flCount);
				flTextArea.setText(fls.get(last).text);
				flPointTextField.setText(fls.get(last).point + "");
				flOptionButton.setSelected(fls.get(last).flag);
				flAnswerArea.setText(flAnswer[last]);
				flNextButton.setEnabled(true);
				flLastButton.setEnabled(flCurrent != 0);
			}
		});
		flLastButton.setBounds(318, 400, 109, 24);
		flTabPanel.add(flLastButton);
		
		flIndexLabel = new JLabel();
		flIndexLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		flIndexLabel.setBounds(72, 67, 48, 14);
		flTabPanel.add(flIndexLabel);

		if (flCount == 0) {
			//cannot do anything
			flIndexLabel.setText("0/0");
			flTextArea.setText("");
			flPointTextField.setText("0");
			flOptionButton.setSelected(false);
			flNextButton.setEnabled(false);
			flLastButton.setEnabled(false);
			
		}else {
			//initialize
			flCurrent = 0;
			flIndexLabel.setText("1/" + flCount);
			flTextArea.setText(fls.get(0).text);
			flPointTextField.setText(fls.get(0).point + "");
			flOptionButton.setSelected(fls.get(0).flag);
			flNextButton.setEnabled(flCount!=1);
			flLastButton.setEnabled(false);
		}
		
		JLabel flAnswerLabel = new JLabel("Answer:");
		flAnswerLabel.setBounds(130, 205, 48, 14);
		flTabPanel.add(flAnswerLabel);
		
		JButton flConfirmButton = new JButton("Confirm");
		flConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				flAnswer[flCurrent] = flAnswerArea.getText();
			}
		});
		flConfirmButton.setBounds(337, 334, 89, 23);
		flTabPanel.add(flConfirmButton);

		JLabel timeLabel = new JLabel(dura.toString());
		timeLabel.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				
			}
		});
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(timeLabel, BorderLayout.NORTH);
		
		ActionListener countdown = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							dura = dura.minusSeconds(1);
							timeLabel.setText(dura.toString());
							//time out
							if (dura.isNegative() || dura.isZero()) {
								JOptionPane.showMessageDialog(new JPanel(), "Time Up!");
								timer.stop();
								storePaper();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			
		};
		
		timer = new Timer(1000, countdown);
		
		flFinish.addActionListener(new FinishButtonActionListener());
		
		timer.start();
	}
	
	private class FinishButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object[] options = {"Yes", "No"};
			
			int choice = JOptionPane.showOptionDialog(new JPanel(), "\nSure to quit?",
					"Confirmation",
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);
			if (choice == JOptionPane.YES_OPTION) {
				//store the paper to database and quit;
				storePaper();
			}else {
				//do nothing and return
			}
		}
		
	}
	
	private void storePaper() {
		timer.stop();
		StringBuilder mcAnswerStringB = new StringBuilder(), fbAnswerStringB = new StringBuilder(),
				flAnswerStringB = new StringBuilder();
		
		for (String s : mcAnswer) mcAnswerStringB.append(s + "/");
		for (String s : fbAnswer) fbAnswerStringB.append(s + "/");
		for (String s : flAnswer) flAnswerStringB.append(s + "/");
		
		String mcAnswerString = mcAnswerStringB.toString();
		String fbAnswerString = fbAnswerStringB.toString();
		String flAnswerString = flAnswerStringB.toString();
		
		AnswerSheet sheet = new AnswerSheet(sls.getStudent(), exam, mcAnswerString, fbAnswerString, flAnswerString);
		
		int grade = StudentUtil.autoGrade(sheet, paper);
		
		sls.storeAnswerAndAntoGrade(sheet, grade);
		//Prompt the user
		JOptionPane.showMessageDialog(new JPanel(), "Paper Submitted!\n" + "Currrent Grade: " + grade);
		dispose();
		parent.setVisible(true);
	}
}
