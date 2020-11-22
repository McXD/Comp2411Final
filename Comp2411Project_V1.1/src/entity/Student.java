package entity;

public class Student {
	public final String sid;
	public final String name;
	public final Class0 memberOf;
	
	public Student(String sid, String name, Class0 memberOf) {
		this.sid = sid;
		this.name = name;
		this.memberOf = memberOf;
	}
	
	public Student(String sid) {
		this.sid = sid;
		this.name = null;
		this.memberOf = null;
	}
	
	@Override
	public String toString() {
		return "Student ID: " + sid + "\nStudent Name: " + name + "\nClass: " + memberOf.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Student)) return false;
		Student other = (Student) obj;
		return sid.compareTo(other.sid) == 0;
	}
}