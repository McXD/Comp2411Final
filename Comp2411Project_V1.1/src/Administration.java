import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleConnection;

class Triple{
	public final String sid;
	public final String tid;
	public final String eid;
	
	public Triple(String sid, String tid, String eid) {
		this.sid = sid;
		this.tid = tid;
		this.eid = eid;
	}
}
public class Administration {
	private OracleConnection con;
	
	public Administration() {
		String username, password;
		username = "C##feng";
		password = "fyl200165";
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			con = (OracleConnection)DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", username, password);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void archive() throws SQLException, IOException{
		FileWriter csvWriter = new FileWriter("archive.csv",true);
		
		PreparedStatement loop1 = con.prepareStatement("select s_id, t_id, e_id, e_start "
				+ "from sits s natural join exam_sche");
		ResultSet rs = loop1.executeQuery();
		ArrayList<Triple> firstPass = new ArrayList<Triple>();
		ArrayList<Triple> toBeDeleted = new ArrayList<Triple>();
		while (rs.next()) {
			if (rs.getTimestamp("e_start").toLocalDateTime().compareTo(LocalDateTime.now().minusYears(10)) < 0) {
				firstPass.add(new Triple(rs.getString(1),rs.getString(2),rs.getString(3)));
			}else continue;
		}
		
		PreparedStatement loop2 = con.prepareStatement("select s_flag from student where s_id = ?");
		for (int i = 0; i<firstPass.size(); i++) {
			Triple t = firstPass.get(i);
			loop2.setString(1, t.sid);
			ResultSet rs0 = loop2.executeQuery();
			rs0.next();
			if (rs0.getInt(1) == 0) toBeDeleted.add(firstPass.get(i));
		}
		
		
		String archiveQuery = "select s_id, t_id, e_id, s.answer_sheet.answer_mc as mc, s.answer_sheet.answer_fb as fb, s.answer_sheet.answer_fl as fl, grade, feedback"
				+ " from sits s where s_id = ? and t_id = ? and e_id = ?";
		String cleanupQuery = "delete from sits where s_id = ? and t_id = ? and e_id = ?";
		PreparedStatement archive = con.prepareStatement(archiveQuery);
		PreparedStatement cleanup = con.prepareStatement(cleanupQuery);
		
		
		for (Triple t : toBeDeleted) {
			archive.setString(1, t.sid);
			archive.setString(2, t.tid);
			archive.setString(3, t.eid);
			cleanup.setString(1, t.sid);
			cleanup.setString(2, t.tid);
			cleanup.setString(3, t.eid);
			
			ResultSet rs1 = archive.executeQuery();
			while (rs1.next()) {
				csvWriter.append(String.format("%s,%s,%s,%s,%s,%s,%d,%s\n",
						rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),
						rs1.getString(5),rs1.getString(6),rs1.getInt(7),rs1.getString(8)));
			}
			
			cleanup.execute();
		}
		
		csvWriter.flush();
		csvWriter.close();
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		Administration foo = new Administration();
		foo.archive();
	}
}
