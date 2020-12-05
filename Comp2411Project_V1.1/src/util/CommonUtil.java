package util;

public class CommonUtil {
	public static String convertGrade(int grade) {
		if (grade >= 95) return "A+";
		else if (grade >= 90) return "A";
		else if (grade >= 85) return "A-";
		else if (grade >= 80) return "B+";
		else if (grade >= 75) return "B+";
		else if (grade >= 70) return "B-";
		else if (grade >= 65) return "C+";
		else if (grade >= 60) return "C";
		else if (grade >= 55) return "C-";
		else if (grade >= 50) return "D+";
		else if (grade >= 45) return "D";
		else if (grade >= 40) return "D-";
		else return "F";
	}
}
