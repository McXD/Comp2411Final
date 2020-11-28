package entity;

public class Class0 {
	public final String cid;
	
	public Class0(String cid) {
		this.cid = cid;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Class0)) return false;
		
		Class0 other = (Class0) obj;
		return cid.trim().compareTo(other.cid.trim()) == 0;
	}
	
	@Override
	public int hashCode() {
		return cid.hashCode();
	}
	
	public String toString() {
		return cid;
	}
	
	public static void main(String[] args) {
		Class0 c1,c2;
		c1 = new Class0("1901");
		c2 = new Class0("1901");
		System.out.println(c1.equals(c2));
	}
}
