package entity;

import java.time.LocalDateTime;

public class ExamInfo {
	public final String tid;
	public final String cid;
	public final String subid;
	public final LocalDateTime start;
	public final String eid;
	
	public ExamInfo(String tid, String cid, String subid, LocalDateTime start, String eid) {
		this.tid = tid;
		this.cid = cid;
		this.subid = subid;
		this.start = start;
		this.eid = eid;
	}
}
