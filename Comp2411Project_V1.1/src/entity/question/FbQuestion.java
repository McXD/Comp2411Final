package entity.question;

public class FbQuestion {
	public final  String text;
	public final String answer; //answer in this format:"answer1, answer2, answer3"
	public final int point;
	public final boolean flag;

	FbQuestion(String text, String answer, int point, boolean flag) {
		this.text = text;
		this.answer = answer;
		this.point = point;
		this.flag = flag;
	}
	
	public int check(String answer) {
		int rightCount = 0;
		
		String[] toCheck = answer.split(",");
		String[] rightAnswer = this.answer.split(",");
		
		for (int i=0; i<toCheck.length; i++) {
			if (i < rightAnswer.length) {
				System.out.println("toCheck: " + toCheck[i]);
				System.out.println("Answer: " + rightAnswer[i]);
				if (rightAnswer[i].strip().compareTo(toCheck[i].strip()) == 0) rightCount += 1;
			}
		}
		System.out.println("Rightcount: " + rightCount);
		return point * rightCount / rightAnswer.length;
	}
	
	@Override
	public String toString() {
		return text + "(" + (flag? "Madatory,  " : "Optional,  ") + point + " points)" ;
	}
	
	public static void main(String[] args) {
		FbQuestion fb = new FbQuestion("1+1=__?", "2", 5, false);
		System.out.println(fb.check("2"));
	}
}
