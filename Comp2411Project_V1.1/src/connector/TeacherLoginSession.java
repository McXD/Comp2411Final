package connector;

import entity.*;
import entity.question.*;
import exception.IdentityException;

import java.util.ArrayList;
import java.sql.*;
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
			String query = String.format("UPDATE SITS SET grade = grade + %d, feedback = '%s' WHERE e_id = ? and t_id = ? and s_id = ?",
					grade,feedback);
			PreparedStatement pst = con.prepareStatement("UPDATE SITS SET grade = grade + ?, feedback = ? WHERE e_id = ? and t_id = ? and s_id = ?");
			String eid = ash.ofExam.eid;
			String tid = ash.ofExam.eid;
			String sid = ash.owner.sid;
			pst.setInt(1, grade);
			pst.setString(2, feedback);
			pst.setString(3, eid);
			pst.setString(4, tid);
			pst.setString(5, sid);
			
			pst.execute();
			
			con.commit();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	
	}
	
	public ArrayList<AnswerSheet> getSheetForExam(Exam exam) throws SQLException{
		
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
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
}
