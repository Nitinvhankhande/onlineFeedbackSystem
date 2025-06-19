import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class feedBackDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/feedback_db";
	private static final String USER = "root";
	private static final String PASSWORD = "Archer@1234";

	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static boolean saveData( String name, String email, String password) {
		boolean status = false;
		String query = "INSERT INTO users(name,email,password) VALUES ( ?, ?, ?)";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, password);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				status = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return status;
	}

	public static boolean checkUser(String email, String password) {
		boolean status = false;
		String query = "SELECT * FROM users WHERE email = ? AND password = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, email);
			stmt.setString(2, password);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					status = true; // If a row is found, email & password match
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return status;
	}
	
	protected static int getUserId(String email, String password) {
        String sql = "SELECT id FROM users WHERE email = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");  // Return user_id
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if user not found
    }
	
	public static String getUserName(String email, String password) {
		String sql = "SELECT name FROM users WHERE email = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name"); // Return user_name
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	protected static boolean insertFeedback(int user, String name, String question ,int rating) {
		boolean status = false;
		 String query = "INSERT INTO feedback (user_id, user_name, Quesition, Rating) VALUES (?, ?, ?, ?)";
		 
		 try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

				stmt.setInt(1, user);
				stmt.setString(2, name);
				stmt.setString(3, question);
				stmt.setInt(4, rating);
				

				int rowsInserted = stmt.executeUpdate();
				if (rowsInserted > 0) {
					status = true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		 
			return status;
	}

	public static boolean checkEmail(String email) throws SQLException {
		// TODO Auto-generated method stub
		
		String query = "SELECT * FROM users WHERE email = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)){
			stmt.setString(1, email);
			
			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
					return true;
				}
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return false;
	}
	
}
