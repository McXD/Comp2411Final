package entity.question;

public class McQuestion  {
	public final String text;
	public final String optionA;
	public final String optionB;
	public final String optionC;
	public final String optionD;
	public final String answer;
	public final int point;
	public final boolean flag;
	
	McQuestion(String text, String optionA, String optionB, String optionC, String optionD,
			String answer, int point, boolean flag)
	{
		this.text = text;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.answer = answer;
		this.point = point;
		this.flag = flag;
	}

	public int check(String answer) {
		if (this.answer.toLowerCase().compareTo(answer.toLowerCase()) == 0) return point;
		else return 0;
	}
	
	public String[] getOptions() {
		return new String[] {this.optionA, this.optionB, this.optionC, this.optionD};
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(text + "(" + (flag? "Madatory,  " : "Optional,  ") + point + " points)" + ":\n");
		sb.append("A." + optionA + "\n");
		sb.append("B." + optionB + "\n");
		sb.append("C." + optionC + "\n");
		sb.append("D." + optionD + "\n");
		
		return sb.toString();
	}
}
