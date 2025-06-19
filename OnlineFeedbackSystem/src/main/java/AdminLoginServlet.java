import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static void quesitionFrom(PrintWriter out) throws IOException {

		AdminDAO dao = new AdminDAO();
		List<Question> questions = dao.getAllQuestions();

		out.println("<html><head><title>Manage Questions</title>");
		out.println("<style>");

		/* General Body Styling */
		out.println(
				"body { font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #e0f7fa, #ffffff); margin: 0; padding: 0; text-align: center; }");

		/* Header Styling */
		out.println(
				".header { background: blue; color: white; padding: 15px; font-size: 22px; font-weight: bold; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); display: flex; justify-content: space-between; align-items: center; }");

		/* Container Styling */
		out.println(
				".container { background: white; padding: 25px; border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.2); text-align: center; width: 60%; margin: 40px auto; transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out; }");
		out.println(".container:hover { transform: scale(1.02); box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.3); }");

		/* Table Styling */
		out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
		out.println("th, td { padding: 12px; border: 1px solid #ddd; text-align: center; font-size: 16px; }");
		out.println("th { background-color: #007bff; color: white; }");

		/* Input Fields */
		out.println(
				"input[type='text'] { padding: 8px; width: 65%; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; }");

		/* Button Styles */
		out.println(
				".btn { display: inline-block; padding: 10px 15px; border: none; cursor: pointer; border-radius: 6px; font-size: 16px; transition: 0.3s ease-in-out; }");
		out.println(".add-btn { background-color: #28a745; color: white; }");
		out.println(".add-btn:hover { background-color: #218838; transform: scale(1.05); }");
		out.println(".update-btn { background-color: #ffc107; color: black; }");
		out.println(".update-btn:hover { background-color: #e0a800; transform: scale(1.05); }");
		out.println(".delete-btn { background-color: red; color: white; }");
		out.println(".delete-btn:hover { background-color: darkred; transform: scale(1.05); }");

		/* View Feedback Button */
		out.println(
				".view-feedback-btn { background-color: #000fff; color: white; padding: 10px 18px; border-radius: 8px; font-size: 16px; font-weight: bold; border: none; cursor: pointer; transition: all 0.3s ease-in-out; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2); margin-right: 15px; }");
		out.println(
				".view-feedback-btn:hover { background-color: blue; transform: scale(1.08); box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.3); }");

		/* Fade-in Animation */
		out.println(
				"@keyframes fadeIn { from { opacity: 0; transform: translateY(-10px); } to { opacity: 1; transform: translateY(0); } }");
		out.println(".container { animation: fadeIn 0.8s ease-in-out; }");
		out.println("</style>");
		out.println("</head><body>");

		/* Header Section */
		out.println("<div class='header'>");
		out.println("<span>Manage Questions</span>");
		out.println("<form action='adminLogin' method='post' style='margin:0;'>");
		out.println("<input type='hidden' name='action' value='allFedback'>");
		out.println("<button type='submit' class='view-feedback-btn'>View All Feedback</button>");
		out.println("</form>");
		out.println("</div>");

		/* Container Start */
		out.println("<div class='container'>");
		out.println("<h2>Question Management</h2>");
		out.println("<p>Total Questions: " + questions.size() + "</p>");

		/* Add Question Form */
		out.println("<form action='adminLogin' method='post'>");
		out.println("<input type='text' name='question_text' placeholder='Enter a new question' required>");
		out.println("<input type='hidden' name='action' value='add'>");
		out.println("<button type='submit' class='btn add-btn'>Add Question</button>");
		out.println("</form>");

		/* Questions Table */
		out.println("<table>");
		out.println("<tr><th>ID</th><th>Question</th><th>Actions</th></tr>");

		/* Display Questions */
		for (Question q : questions) {
			out.println("<tr>");
			out.println("<td>" + q.getId() + "</td>");
			out.println("<td><input type='text' value='" + q.getText() + "' name='question_" + q.getId() + "'></td>");
			out.println("<td>");

			/* Update Form */
			out.println("<form action='adminLogin' method='post' style='display:inline;'>");
			out.println("<input type='hidden' name='question_id' value='" + q.getId() + "'>");
			out.println("<input type='hidden' name='action' value='update'>");
			out.println("<input type='text' name='question_text' value='" + q.getText() + "' required>");
			out.println("<button type='submit' class='btn update-btn'>Update</button>");
			out.println("</form>");

			/* Delete Form */
			out.println("<form action='adminLogin' method='post' style='display:inline;'>");
			out.println("<input type='hidden' name='question_id' value='" + q.getId() + "'>");
			out.println("<input type='hidden' name='action' value='delete'>");
			out.println("<button type='submit' class='btn delete-btn'>Delete</button>");
			out.println("</form>");
			out.println("</td>");
			out.println("</tr>");
		}

		out.println("</table>");
		out.println("</div>"); // End Container
		out.println("</body></html>");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");

		if ("update".equals(action)) {
			int questionId = Integer.parseInt(request.getParameter("question_id"));
			out.println("<h3>Updating Question ID: " + questionId + "</h3>");

			String question_text = request.getParameter("question_text");

			AdminDAO.updateQuesition(questionId, question_text);
			response.sendRedirect("adminLogin");

		} else if ("delete".equals(action)) {

			int questionId = Integer.parseInt(request.getParameter("question_id"));
			AdminDAO.deleteQuesition(questionId);
			response.sendRedirect("adminLogin");

			// Call DAO to delete the question
		} else if ("add".equals(action)) {

			String question_text = request.getParameter("question_text");
			AdminDAO.insertQuesition(question_text);
			response.sendRedirect("adminLogin");

		} else if ("allFedback".equals(action)) {

			AdminDAO.getAllFeedback(out);
		} else {
			out.println("<h3>Invalid action!</h3>");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		quesitionFrom(out); // Call the method to display the
	}

}
