package entity;

import java.time.LocalDateTime;

public enum Semester {
	Y01S01,Y01S02,
	Y02S01,Y02S02,
	Y03S01,Y03S02,
	Y04S01,Y04S02;
	
	public static Semester inSemester(LocalDateTime time) {
			 if (time.isAfter(LocalDateTime.of(2019, 9, 1, 0, 0)) && time.isBefore(LocalDateTime.of(2019, 12, 31, 0, 0))) return Y01S01;
		else if (time.isAfter(LocalDateTime.of(2020, 1, 1, 0, 0)) && time.isBefore(LocalDateTime.of(2020, 6, 30, 0, 0))) return Y01S02;
		
		else if (time.isAfter(LocalDateTime.of(2020, 9, 1, 0, 0)) && time.isBefore(LocalDateTime.of(2020, 12, 31, 0, 0))) return Y02S01;
		else if (time.isAfter(LocalDateTime.of(2021, 1, 1, 0, 0)) && time.isBefore(LocalDateTime.of(2021, 6, 30, 0, 0))) return Y02S02;
		
		else if (time.isAfter(LocalDateTime.of(2021, 9, 1, 0, 0)) && time.isBefore(LocalDateTime.of(2021, 12, 31, 0, 0))) return Y03S01;
		else if (time.isAfter(LocalDateTime.of(2022, 1, 1, 0, 0)) && time.isBefore(LocalDateTime.of(2022, 6, 30, 0, 0))) return Y03S02;
		
		else if (time.isAfter(LocalDateTime.of(2022, 9, 1, 0, 0)) && time.isBefore(LocalDateTime.of(2022, 12, 31, 0, 0))) return Y04S01;
		else return Y04S02;
		
	}
	
	public boolean isInSemester(LocalDateTime time) {
		return inSemester(time) == this;
	}
	
	public static void main(String[] args) {
		for (Semester s:Semester.values()) {
			System.out.println(s.ordinal());
		}
	}
}
