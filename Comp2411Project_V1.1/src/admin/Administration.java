package admin;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	public Administration() throws FileNotFoundException {
		try {
			File file = new File("config.txt");
			Scanner sc = new Scanner(file);
			String[] info = new String[5];
			
			for (int i=0; i<5; i++) {
				info[i] = sc.nextLine().split(":")[1].trim();
			}
			
			sc.close();
			
			String username, password;
			username = info[3];
			password = info[4];

			// Connection
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			con = (OracleConnection)DriverManager.getConnection(
				 "jdbc:oracle:thin:@" + info[0] + ":" + info[1] + ":" + info[2],
				 username, password);
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
	
	
	public static void configure() throws IOException {
		Scanner in = new Scanner(System.in);
		FileWriter out = new FileWriter("config.txt");
		
		System.out.print("hostname: ");
		out.write("hostname: " + in.nextLine() + "\n");
		System.out.print("port: " );
		out.write("port: " + in.nextLine() + "\n");
		System.out.print("sid: " );
		out.write("sid: " + in.nextLine() + "\n");
		System.out.print("username: " );
		out.write("username: " + in.nextLine() + "\n");
		System.out.print("password: " );
		out.write("password: " + in.nextLine() + "\n");
		
		in.close();
		out.close();
		
		System.out.println("Configuration succeeded");
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		System.out.println("Option: 1 to configure, 2 to archive");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Input: ");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			Administration.configure();
			break;
		case 2:
			Administration ad = new Administration();
			ad.archive();
			break;
		default:
			System.err.println("Invalid input");
		}
	}
	
}
