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
		return cid.compareTo(other.cid) == 0;
	}
	
	public String toString() {
		return cid;
	}
}
