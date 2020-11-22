package entity;

import java.util.HashMap;

public class Teacher {
	public final String tid;
	public final String name;
	public final HashMap<Class0, Subject> teaches;
	
	public Teacher(String tid, String name, HashMap<Class0, Subject> teaches) {
		this.tid= tid;
		this.name = name;
		this.teaches = teaches;
	}
	
	public Teacher(String tid) {
		this.tid = tid;
		this.name = null;
		this.teaches = null;
	}
	
	public boolean canSetExam(Class0 forclass, Subject onSubject) {
		Subject possible = teaches.get(forclass);
		if (possible == null) return false;
		return possible.equals(onSubject);
	}
	
	public String toString() {
		String result = "Teacher ID: " + tid + "\nTeacher Name: " + name + "\nTeaches:\n";
		for (Class0 c:teaches.keySet()) {
			result += c.toString() + ": " + teaches.get(c).toString();
		}
		return result;
	}
}
