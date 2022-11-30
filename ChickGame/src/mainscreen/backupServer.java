package mainscreen;

import java.io.*;
import java.sql.*;
import java.net.*;

public class backupServer {
	public static void main(String[] args) {
		// 통신
		BufferedReader in = null;
		BufferedWriter out = null;
		ServerSocket listener = null;
		Socket socket = null;

		// DB연결관련
		Connection conn = null;
		Statement stmt = null;
		
		String[] msgsl = null;
		

		try {
			// 수신관련
			listener = new ServerSocket(8080);
			System.out.println("연결을 기다립니다.");
			socket = listener.accept();
			System.out.println("연결되었습니다.");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			PreparedStatement Query = null;
			
			// DB관련
			Class.forName("com.mysql.jdbc.Driver");
			// DB 연결 conn 객체, chickData / root / 1234
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chickdata?serverTimezone=UTC", "root",
					"1234");
			System.out.println("DB 연결 완료");
			
			String inputMsg = in.readLine(); // 한 행 읽기
			msgsl = inputMsg.split(",");
			Query = conn.prepareStatement("insert data values('"+msgsl[0]+"','"+msgsl[1]+"',"+Integer.parseInt(msgsl[2])+","+Integer.parseInt(msgsl[3])+","+Integer.parseInt(msgsl[4])+","+Integer.parseInt(msgsl[5])+","+Integer.parseInt(msgsl[6])
					+","+Integer.parseInt(msgsl[7])+","+Integer.parseInt(msgsl[8])+","+Integer.parseInt(msgsl[9])+")");
			Query.execute();			

		} catch (IOException e1) {
			System.out.println("입출력 오류 발생!");
		} catch (ClassNotFoundException e2) {
			System.out.println("JDBC 드라이버 관련 오류입니다.");
		} catch (SQLException e3) {
			System.out.println("DB 연결 오류 혹은 SQL 연결 오류 " + e3);
		} finally {
			try {
				socket.close();
				listener.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
