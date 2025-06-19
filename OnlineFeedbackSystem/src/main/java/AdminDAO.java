import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/feedback_db";
	private static final String USER = "root";
	private static final String PASSWORD = "Archer@1234";

	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public List<Question> getAllQuestions() {

		List<Question> questionList = new ArrayList<>();
		String query = "SELECT question_id, question_text FROM questions";

		try (Connection connection = getConnection();
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				int id = rs.getInt("question_id");
				String text = rs.getString("question_text");

				questionList.add(new Question(id, text));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questionList;
	}

	public static void insertQuesition(String question_text) {
		String sql = "INSERT INTO questions (question_text) VALUES (?)";
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, question_text);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteQuesition(int id) {
		String sql = "DELETE FROM questions WHERE question_id=?";
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateQuesition(int question_id, String question_text) {
		String sql = "UPDATE questions SET question_text = ? WHERE question_id = ?";

		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, question_text); // Set new question text
			ps.setInt(2, question_id); // Set question ID for WHERE condition

			int rowsUpdated = ps.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("Question updated successfully.");
			} else {
				System.out.println("No question found with ID: " + question_id);
			}

		} catch (SQLException e) {
			System.err.println("Error updating question: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void getAllFeedback(PrintWriter out) {

		out.println("<html><head><title>Feedback List</title>");
		out.println("<style>");
		out.println("body { font-family: Arial, sans-serif; background-color: #f8f9fa; text-align: center; margin: 0; }");
		out.println(".header { background-color: #28a745; color: white; padding: 15px; font-size: 22px; font-weight: bold; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); position: fixed; width: 100%; top: 0; left: 0; text-align: center; }");
		out.println("table { width: 80%; margin: 80px auto 20px; border-collapse: collapse; background: white; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.2); border-radius: 10px; overflow: hidden; }");
		out.println("th, td { padding: 12px; border: 1px solid #ddd; text-align: center; transition: background 0.3s ease-in-out; }");
		out.println("th { background-color: #28a745; color: white; }");
		out.println("tr:hover { background-color: #d4edda; transform: scale(1.02); transition: all 0.3s ease-in-out; }");
		out.println("@keyframes fadeIn { from { opacity: 0; transform: translateY(-10px); } to { opacity: 1; transform: translateY(0); } }");
		out.println("table { animation: fadeIn 0.8s ease-in-out; }");
		out.println("</style>");
		out.println("</head><body>");
		out.println("<div class='header'>Feedback List</div>");
		out.println("<table>");
		out.println("<tr><th>ID</th><th>User ID</th><th>Name</th><th>Question</th><th>Rating</th></tr>");

		String sql = "SELECT id, user_id, user_name, Quesition, Rating FROM feedback";

		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getInt("id") + "</td>");
				out.println("<td>" + rs.getInt("user_id") + "</td>");
				out.println("<td>" + rs.getString("user_name") + "</td>");
				out.println("<td>" + rs.getString("Quesition") + "</td>");
				out.println("<td>" + rs.getInt("Rating") + "</td>");
				out.println("</tr>");
			}

		} catch (SQLException e) {
			out.println("<tr><td colspan='5' style='color: red;'>Error fetching data</td></tr>");
			e.printStackTrace();
		}
		out.println("</table>");
		out.println("</body></html>");

	}

}
