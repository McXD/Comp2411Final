package entity.question;

public class FlQuestion {
	public final String text;
	public final int point;
	public final boolean flag;
	
	FlQuestion(String text, int point, boolean flag) {
		this.text = text;
		this.point = point;
		this.flag = flag;
	}
	
	@Override
	public String toString() {
		return text + "(" + (flag? "Madatory,  " : "Optional,  ") + point + " points)" ;
	}
}
