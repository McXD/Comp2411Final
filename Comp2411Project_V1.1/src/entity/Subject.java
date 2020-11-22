package entity;

public class Subject {
	public final String sub_id;
	
	public Subject(String sub_id) {
		this.sub_id = sub_id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Subject)) return false;
		Subject other = (Subject) obj;
		return sub_id.compareTo(other.sub_id) == 0;
	}
	
	@Override
	public String toString() {
		return sub_id;
	}
}
