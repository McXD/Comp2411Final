package connector;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import entity.AnswerSheet;
import entity.Class0;
import entity.Exam;
import entity.Paper;
import entity.Semester;
import entity.SemesterRecord;
import entity.Student;
import entity.StudentRecord;
import entity.Subject;
import entity.Teacher;
import exception.IdentityException;
import oracle.jdbc.driver.OracleConnection;

public class StudentLoginSession
{
	private Student student;
	private OracleConnection con;

	public Student getStudent() {
		return student;
	}
	
	public StudentLoginSession(String sid, String pw) throws IdentityException, SQLException{
		try {
			//Connect to Oracle DB
			File file = new File("config.txt");
			Scanner sc = new Scanner(file);
			
			String[] info = new String[5];
			
			for (int i=0; i<5; i++) {
				info[i] = sc.nextLine().split(":")[1].trim();
			}
			
			sc.close();
			
			String username, password;
			username = info[3];			// Your Oracle Account ID
			password = info[4]; 		// Password of Oracle Account

			// Connection
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			OracleConnection conn = 
				(OracleConnection)DriverManager.getConnection(
				 "jdbc:oracle:thin:@" + info[0] + ":" + info[1] + ":" + info[2],
				 username, password);
			
			//Fetch student id and pw
			String query = "SELECT s_name, s_pw FROM student where s_id = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, sid);

			ResultSet rs = pst.executeQuery();
			if (!rs.next()) {
				throw new IdentityException("StudentID not found");
			}else {
				String realPw = rs.getString("s_pw");
				if (realPw.compareTo(pw) != 0) throw new IdentityException("Invalid Password!");
				
				//Now everything goes right, construct
				
				//read s_name
				String name = rs.getString("s_name");
				
				//read the class the student belongs to
				query = "SELECT c_id FROM student NATURAL JOIN MEMBER_OF where s_id = ?";
				pst = conn.prepareStatement(query);
				pst.setString(1, sid);
				rs = pst.executeQuery();
				rs.next();
				String cid = rs.getString("c_id");
				Class0 clas = new Class0(cid);
				student = new Student(sid, name, clas);
				con = conn;
				
				con.setAutoCommit(false);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public void storeAnswerAndAntoGrade(AnswerSheet answer, int grade){
		
		try {			
			PreparedStatement pst = con.prepareStatement("CALL STUDENT_SITS_EXAM(?, ?, ?, ?, ?, ?)");
			
			PreparedStatement pst2 = con.prepareStatement(String.format("UPDATE SITS SET grade = %d WHERE s_id = ? and e_id = ? and t_id = ?",grade));
			pst.setString(1, student.sid);
			pst.setString(2, answer.ofExam.creator.tid);
			pst.setString(3, answer.ofExam.eid);
			pst.setString(4, answer.mcAnswer);
			pst.setString(5, answer.fbAnswer);
			pst.setString(6, answer.flAnswer);
			pst.execute();
			
			pst2.setString(1, student.sid);
			pst2.setString(2, answer.ofExam.eid);
			pst2.setString(3, answer.ofExam.creator.tid);
			
			pst2.execute();
			
			con.commit();
		} catch (SQLException e) {
			//This exception should not be triggered
			e.printStackTrace();
		} 
	}

	public ArrayList<StudentRecord> checkRecord(){
		try {
			PreparedStatement pst = con.prepareStatement("SELECT t_id, e_id, grade, feedback FROM SITS WHERE "
					+ "s_id = ?");
			
			pst.setString(1, student.sid);
			
			ResultSet rs = pst.executeQuery();
			
			ArrayList<StudentRecord> result = new ArrayList<StudentRecord>();
			
			while(rs.next()) {
				result.add(new StudentRecord(rs.getString(1),rs.getString(2), rs.getString(4), rs.getInt(3)));
			}
			
			return result;
		} catch (SQLException e) {
			//This exception should not be triggered
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Exam> getAllExams(){
		try {
			ArrayList<Exam> result = new ArrayList<Exam>();
			
			PreparedStatement pst = con.prepareStatement(
					"SELECT t_id, e_id, c_id, sub_id, p_id, e_start, e_dura, t_name" +
					" FROM sets NATURAL JOIN exam_sche NATURAL JOIN paper NATURAL JOIN teacher" +
					" WHERE c_id = ?" );
			pst.setString(1, student.memberOf.cid);
			
			ResultSet rs = pst.executeQuery();
			
			Teacher teacher;
			String eid;
			Class0 forClass;
			Subject onSubject;
			Paper paper;
			String pid;
			LocalDateTime start;
			int dura;
			
			while(rs.next()) {
				
				teacher = new Teacher(rs.getString(1),rs.getString(8));
				eid = rs.getString(2);
				forClass = new Class0(rs.getString(3));
				onSubject = new Subject(rs.getString(4));
				pid = rs.getString(5);
				start = LocalDateTime.parse(rs.getTimestamp(6).toString(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
				dura = rs.getInt(7);
				
				//Create paper
				paper = new Paper(pid);
				
				//Multi-choice questions
				PreparedStatement pstMc = con.prepareStatement("SELECT * FROM TABLE(SELECT p_mc FROM paper WHERE t_id = ? AND e_id = ? AND p_id = ?)");
				pstMc.setString(1, teacher.tid);
				pstMc.setString(2, eid);
				pstMc.setString(3, pid);
				
				ResultSet rsMc = pstMc.executeQuery();
				while(rsMc.next()) {
					paper.addMc(rsMc.getString(1),rsMc.getString(2),rsMc.getString(3),rsMc.getString(4),rsMc.getString(5),
							rsMc.getString(6),rsMc.getInt(7), rsMc.getInt(8) == 1);
				}
				
				PreparedStatement pstFb = con.prepareStatement("SELECT * FROM TABLE(SELECT p_fb FROM paper WHERE t_id = ? AND e_id = ? AND p_id = ?)");
				pstFb.setString(1, teacher.tid);
				pstFb.setString(2, eid);
				pstFb.setString(3, pid);
				
				ResultSet rsFb = pstFb.executeQuery();
				while(rsFb.next()) {
					paper.addFb(rsFb.getString(1),rsFb.getString(2),rsFb.getInt(3), rsFb.getInt(4) == 1);
				}
				
				PreparedStatement pstFl = con.prepareStatement("SELECT * FROM TABLE(SELECT p_fl FROM paper WHERE t_id = ? AND e_id = ? AND p_id = ?)");
				pstFl.setString(1, teacher.tid);
				pstFl.setString(2, eid);
				pstFl.setString(3, pid);
				
				ResultSet rsFl = pstFl.executeQuery();
				while(rsFl.next()) {
					paper.addFl(rsFl.getString(1), rsFl.getInt(2), rsFl.getInt(3) == 1);
				}
				
				result.add(new Exam(teacher, eid, forClass, onSubject, paper, start, dura));
			}
			
			return result;
		}catch(SQLException e) {
			//This exception should not be triggered
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<SemesterRecord> getSemesterRecords(Semester sem){
		try {
			ArrayList<SemesterRecord> result = new ArrayList<SemesterRecord>();
			
			String query = "select s_name, s_id, sub_id, grade, e_start, feedback from sits natural join student natural join exam_sche natural join sets where s_id = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, this.student.sid);

			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				LocalDateTime start = LocalDateTime.parse(rs.getTimestamp(5).toString(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
				String cid = this.student.memberOf.cid;
				int startYear = Integer.parseInt(cid.substring(0, 2)) + 2000;
				if (sem.isInSemester(start, startYear)) result.add(new SemesterRecord(new Student(rs.getString(2), rs.getString(1),null),
						new Subject(rs.getString(3)),
						rs.getInt(4), rs.getString(6)));
				else continue;
			}
			
			return result;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

