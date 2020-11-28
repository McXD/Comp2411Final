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
	
	public Teacher(String tid, String name) {
		this.tid = tid;
		this.name = name;
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
			result += c.toString() + ": " + teaches.get(c).toString() + "\n";
		}
		return result;
	}
	
	public static void main(String[] args) {
		HashMap<Class0, Subject> teaches = new HashMap<Class0, Subject>();
		Class0 c1 = new Class0("1901");
		Class0 c2 = new Class0("1901");
		teaches.put(c1, new Subject("AF2108"));
		System.out.println("teaches.containsKey(c1): " + teaches.containsKey(c1));
		System.out.println("c1.equals(c2): " + c1.equals(c2));
		System.out.println("teaches.containsKey(c2): " + teaches.containsKey(c2));
		
		Teacher cora = new Teacher("16096548d","Cora",teaches);
		System.out.println(cora.canSetExam(new Class0("1901"), new Subject("AF2108")));
	}
}
