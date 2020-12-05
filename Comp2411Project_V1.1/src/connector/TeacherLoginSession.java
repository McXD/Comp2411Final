package connector;

import entity.*;
import entity.question.*;
import exception.IdentityException;

import java.util.ArrayList;
import java.io.PipedInputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import oracle.jdbc.driver.*;

public class TeacherLoginSession {
	private Teacher teacher;
	private OracleConnection con;
	
	
	public TeacherLoginSession(String tid, String pw) throws IdentityException{
		try {
			//Connect to Oracle DataBase
			String username, password;
			username = "C##feng";
			password = "fyl200165";
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			OracleConnection conn = (OracleConnection)DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", username, password);
			
			//check teacher id and pw
			Statement st = conn.createStatement();
			String query = "SELECT t_name, t_pw FROM teacher where t_id = '" + tid + "'";

			ResultSet rs = st.executeQuery(query);
			
			if (!rs.next()) {
				//teacher id does not exist
				throw new IdentityException("TeacherID not found");
			}else {
				
				//teacher id exists
				//check pw
				String realPw = rs.getString("t_pw");
				if (realPw.compareTo(pw) != 0) throw new IdentityException("Invalid Password!");
				
				//Now everything goes right, construct the instance
				
				//read t_name
				String name = rs.getString("t_name");
				
				//read the class the teacher teaches
				query = "SELECT c_id, sub_id FROM teacher NATURAL JOIN teaches where t_id = '" + tid + "'";
				rs = st.executeQuery(query);
				
				HashMap<Class0,Subject> teaches = new HashMap<Class0, Subject>();
				while(rs.next()) {
					teaches.put(new Class0(rs.getString("c_id")), new Subject(rs.getString("sub_id").trim()));
				}
				
				
				teacher = new Teacher(tid,name,teaches);
				con = conn;
				
				//No Auto commit
				con.setAutoCommit(false);
			}
		} catch (SQLException e) {
			//This region should not be reached
			
			//For debugging
			System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!");
			System.err.println("WARNING");
			System.err.println("Transparent Region is reached!");
			e.printStackTrace();
		}
	}

	
	public void setExam(Exam e){
		//Read information into database
		System.out.println("in setExam");
		try {
			PreparedStatement pst1 = con.prepareStatement("CALL make_sche(?,?,?,?)");
			PreparedStatement pst2 = con.prepareStatement("CALL make_empty_paper(?,?,?)");
			PreparedStatement pst3 = con.prepareStatement("CALL set_exam(?,?,?,?)");

			String eid = e.eid;
			Timestamp start = Timestamp.valueOf(e.start);
			int dura = e.duration;
			Paper paper = e.withPaper;
			String pid = paper.pid;
			String cid = e.forClass.cid;
			String sub_id = e.onSubject.sub_id;
			String tid = teacher.tid;
			
			pst1.setString(1,teacher.tid);
			pst1.setString(2, eid);
			pst1.setTimestamp(3, start);
			pst1.setInt(4, dura);
			
			pst2.setString(1, pid);
			pst2.setString(2, eid);
			pst2.setString(3, teacher.tid);
			
			pst3.setString(1, teacher.tid);
			pst3.setString(2, eid);
			pst3.setString(3, cid);
			pst3.setString(4, sub_id);
			
			pst1.execute();
			pst2.execute();
			pst3.execute();
			
			System.out.println("Preparing statement");
			PreparedStatement mcpst = con.prepareStatement("CALL ADD_MC_TO_PAPER(?,?,?,QUESTION_MC_T(?,?,?,?,?,?,?,?))");
			PreparedStatement fbpst = con.prepareStatement("CALL ADD_FB_TO_PAPER(?,?,?,QUESTION_FB_T(?,?,?,?))");
			PreparedStatement flpst = con.prepareStatement("CALL ADD_FL_TO_PAPER(?,?,?,QUESTION_FL_T(?,?,?))");
			
			ArrayList<McQuestion> mcs = paper.getMcs();
			
			for(McQuestion m : mcs) {
				String[] ops = m.getOptions();
				mcpst.setString(1, pid);
				mcpst.setString(2, tid);
				mcpst.setString(3, eid);
				mcpst.setString(4, m.text);
				mcpst.setString(5, ops[0]);
				mcpst.setString(6, ops[1]);
				mcpst.setString(7, ops[2]);
				mcpst.setString(8, ops[3]);
				mcpst.setString(9, m.answer);
				mcpst.setInt(10, m.point);
				mcpst.setInt(11, m.flag?1:0);
				
				mcpst.execute();
			}
			
			ArrayList<FbQuestion> fbs = paper.getFbs();
			for(FbQuestion f : fbs) {
				fbpst.setString(1, pid);
				fbpst.setString(2, tid);
				fbpst.setString(3, eid);
				fbpst.setString(4, f.text);
				fbpst.setString(5, f.answer);
				fbpst.setInt(6, f.point);
				fbpst.setInt(7, f.flag?1:0);
				
				fbpst.execute();
			}
			
			ArrayList<FlQuestion> fls = paper.getFls();
			for(FlQuestion f : fls) {
				flpst.setString(1, pid);
				flpst.setString(2, tid);
				flpst.setString(3, eid);
				flpst.setString(4, f.text);
				flpst.setInt(5, f.point);
				flpst.setInt(6, f.flag?1:0);
				
				flpst.execute();
			}
			
			System.out.println("Before commit");
			con.commit();
			
		} catch (SQLException e1) {
			String msg = e1.getMessage();
			if (msg.contains("ORA-02291")) {
				System.err.println("Permission denied!");
			}
			System.err.println(e1.getMessage());
			e1.printStackTrace();
		}
	}

	
	public void grade(AnswerSheet ash, int grade, String feedback){
		try {
			System.out.println("In grade()");
			String eid = ash.ofExam.eid;
			String tid = ash.ofExam.creator.tid;
			String sid = ash.owner.sid;
			
			PreparedStatement pst = con.prepareStatement("UPDATE SITS SET grade = ?, feedback = ? WHERE e_id = ? and t_id = ? and s_id = ?");
			
			PreparedStatement pst0 = con.prepareStatement("Select grade from sits where e_id = ? and t_id = ? and s_id = ?");
			pst0.setString(1, eid);
			pst0.setString(2, tid);
			pst0.setString(3, sid);
			
			ResultSet rs0 = pst0.executeQuery();
			rs0.next();
			int grade0 = rs0.getInt(1);
			
			System.out.println(eid + " " + tid + " " + sid);
			pst.setInt(1, grade0 + grade > 100 ? 100 : grade0 + grade);
			pst.setString(2, feedback);
			pst.setString(3, eid);
			pst.setString(4, tid);
			pst.setString(5, sid);
			
			pst.execute();
			
			con.commit();
			System.out.println("Commited");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public ArrayList<AnswerSheet> getSheetForExam(Exam exam){
		try {
			ArrayList<AnswerSheet> result = new ArrayList<AnswerSheet>();
			String query = "SELECT s.s_id, s.answer_sheet.answer_mc, s.answer_sheet.answer_fb, s.answer_sheet.answer_fl FROM sits s"
					+ " WHERE e_id = ? AND t_id = ?";
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, exam.eid);
			pst.setString(2, exam.creator.tid);
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				AnswerSheet as = new AnswerSheet(new Student(rs.getString(1)), exam,
						rs.getString(2), rs.getString(3), rs.getString(4));
				result.add(as);
			}
			
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public ArrayList<Exam> getAllExam(){
		try {
			ArrayList<Exam> result = new ArrayList<Exam>();
			
			PreparedStatement pst = con.prepareStatement(
					"SELECT t_id, e_id, c_id, sub_id, p_id, e_start, e_dura, t_name" +
					" FROM sets NATURAL JOIN exam_sche NATURAL JOIN paper NATURAL JOIN teacher" +
					" WHERE t_id = ?" );
			pst.setString(1, teacher.tid);
			
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
			//This region should not be reached
			
			//For debugging
			System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!");
			System.err.println("WARNING");
			System.err.println("Transparent Region is reached!");
			e.printStackTrace();
			
			return null;
		}
	}
	
	public ArrayList<ExamResultRecord> getRecordForExam(Exam exam){
		try {
			String query = "select e_id,s_id, s_name, c_id,grade from sits natural join student natural join member_of where t_id = ? and e_id = ?";
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, exam.creator.tid);
			pst.setString(2, exam.eid);
			ResultSet rs = pst.executeQuery();
			
			ArrayList<ExamResultRecord> result = new ArrayList<ExamResultRecord>();
			while(rs.next()) {
				result.add(new ExamResultRecord(rs.getString(1),new Student(rs.getString(2),rs.getString(3),new Class0(rs.getString(4))),rs.getInt(5)));
			}
			
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
