package entity;

import java.util.ArrayList;
import entity.question.*;

public class Paper {
	public final String pid;
	private ArrayList<McQuestion> mcs;
	private ArrayList<FbQuestion> fbs;
	private ArrayList<FlQuestion> fls;
	
	public Paper(String pid) {
		this.pid = pid;
		mcs = new ArrayList<McQuestion>();
		fbs = new ArrayList<FbQuestion>();
		fls = new ArrayList<FlQuestion>();
	}
	
	public Paper(String pid, ArrayList<McQuestion> mcs, ArrayList<FbQuestion> fbs, ArrayList<FlQuestion> fls) {
		this.pid = pid;
		this.mcs = mcs;
		this.fbs = fbs;
		this.fls = fls;
	}
	
	public void addMc(String text, String optionA, String optionB, String optionC, String optionD,
			String answer, int point, boolean flag) {
		mcs.add(QuestionFactory.genMc(text,optionA,optionB,optionC,optionD,answer,point,flag));
	}
	
	public void addMc(McQuestion q) {
		mcs.add(q);
	}
	
	public void addFb(String text, String answer, int point, boolean flag) {
		fbs.add(QuestionFactory.genFb(text, answer, point, flag));
	}
	
	public void addFb(FbQuestion q) {
		fbs.add(q);
	}
	
	public void addFl(String text, int point, boolean flag) {
		fls.add(QuestionFactory.genFl(text, point, flag));
	}
	
	public void addFl(FlQuestion q) {
		fls.add(q);
	}
	
	public ArrayList<McQuestion> getMcs(){
		return this.mcs;
	}
	
	public ArrayList<FlQuestion> getFls(){
		return this.fls;
	}
	
	public ArrayList<FbQuestion> getFbs(){
		return this.fbs;
	}
	
	public String toString(int foo) {
		//all the fl questions
		StringBuilder sb = new StringBuilder();
		int index = 1;
		for (FlQuestion q : fls) {
			sb.append(index++ + ": " + q.toString() + "\n\n");
		}
		
		return sb.toString();
	}
}
