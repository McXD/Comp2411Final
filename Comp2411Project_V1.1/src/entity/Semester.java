package entity;

import java.time.LocalDateTime;

public enum Semester {
	Y01S01,Y01S02,
	Y02S01,Y02S02,
	Y03S01,Y03S02,
	Y04S01,Y04S02;
	
	public static Semester inSemester(LocalDateTime time, int beginYear) {
			 if (time.isAfter(LocalDateTime.of(beginYear, 9, 1, 0, 0)) && time.isBefore(LocalDateTime.of(beginYear, 12, 31, 0, 0))) return Y01S01;
		else if (time.isAfter(LocalDateTime.of(beginYear+1, 1, 1, 0, 0)) && time.isBefore(LocalDateTime.of(beginYear+1, 6, 30, 0, 0))) return Y01S02;
		
		else if (time.isAfter(LocalDateTime.of(beginYear+1, 9, 1, 0, 0)) && time.isBefore(LocalDateTime.of(beginYear+1, 12, 31, 0, 0))) return Y02S01;
		else if (time.isAfter(LocalDateTime.of(beginYear+2, 1, 1, 0, 0)) && time.isBefore(LocalDateTime.of(beginYear+2, 6, 30, 0, 0))) return Y02S02;
		
		else if (time.isAfter(LocalDateTime.of(beginYear+2, 9, 1, 0, 0)) && time.isBefore(LocalDateTime.of(beginYear+2, 12, 31, 0, 0))) return Y03S01;
		else if (time.isAfter(LocalDateTime.of(beginYear+3, 1, 1, 0, 0)) && time.isBefore(LocalDateTime.of(beginYear+3, 6, 30, 0, 0))) return Y03S02;
		
		else if (time.isAfter(LocalDateTime.of(beginYear+3, 9, 1, 0, 0)) && time.isBefore(LocalDateTime.of(beginYear+3, 12, 31, 0, 0))) return Y04S01;
		else return Y04S02;
		
	}
	
	public boolean isInSemester(LocalDateTime time, int beginYear) {
		return inSemester(time, beginYear) == this;
	}
	
	public static void main(String[] args) {
		for (Semester s:Semester.values()) {
			System.out.println(s.ordinal());
		}
	}
}
