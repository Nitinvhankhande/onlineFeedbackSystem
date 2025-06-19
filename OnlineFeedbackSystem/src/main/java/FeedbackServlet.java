import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeedbackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean status = false;

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int userId = feedBackDAO.getUserId(email, password);
		String userName = feedBackDAO.getUserName(email, password);
		
		AdminDAO dao = new AdminDAO();
		List<Question> questions = dao.getAllQuestions();

		for (Question q : questions) {

			String questionText = q.getText();
			int answer = Integer.parseInt(request.getParameter("answer_" + q.getId()));

			if (questionText != null && answer > 0) {
				status = feedBackDAO.insertFeedback(userId,userName, questionText, answer);
			}
		}
		out.println("<html><head><title>Feedback Status</title>");
		out.println("<style>");

		/* General Styling */
		out.println("body { font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #f0f2f5, #ffffff); display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");

		/* Container */
		out.println(".container { background: #ffffff; padding: 30px; border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.15); text-align: center; width: 350px; transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out; }");
		out.println(".container:hover { transform: scale(1.02); box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.2); }");

		/* Success Message */
		out.println(".success { color: #28a745; font-size: 22px; font-weight: 600; }");

		/* Error Message */
		out.println(".error { color: #dc3545; font-size: 22px; font-weight: 600; }");

		/* Button Styling */
		out.println(".btn { display: inline-block; background: #007bff; color: white; font-size: 16px; padding: 10px 20px; border-radius: 6px; text-decoration: none; margin-top: 20px; transition: 0.3s ease-in-out; }");
		out.println(".btn:hover { background: #0056b3; transform: scale(1.05); }");

		/* Animation */
		out.println("@keyframes fadeIn { from { opacity: 0; transform: translateY(-10px); } to { opacity: 1; transform: translateY(0); } }");
		out.println(".container { animation: fadeIn 0.8s ease-in-out; }");

		out.println("</style>");
		out.println("</head><body>");

		/* Content */
		out.println("<div class='container'>");
		if (status) {
		    out.println("<h2 class='success'>Feedback Successfully Submitted</h2>");
		} else {
		    out.println("<h2 class='error'> Feedback Submission Failed</h2>");
		}

		/* Button to go back to the Main Index Page */
		out.println("<a href='index.html' class='btn'>Go to Home</a>");

		out.println("</div>");
		out.println("</body></html>");

	}
}
