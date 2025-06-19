import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class userLoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		// Retrieve form data
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int userid = feedBackDAO.getUserId(email,password);
		
		if (email.equals("admin@gmail.com") && password.equals("123")) {
			
			AdminLoginServlet.quesitionFrom(out);

		} else {
			boolean isSaved = feedBackDAO.checkUser(email, password);
			// HTML response

			if (isSaved) {

			    AdminDAO dao = new AdminDAO();
			    List<Question> questions = dao.getAllQuestions();

			    out.println("<html><head><title>Feedback Form</title>");
			    out.println("<style>");

			    /* Import Google Fonts */
			    out.println("@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap');");

			    /* General Page Styling */
			    out.println("body { font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #74ebd5, #acb6e5); text-align: center; margin: 0; display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100vh; }");

			    /* Header */
			    out.println(".header { background: #007bff; color: white; padding: 20px; font-size: 24px; font-weight: 600; box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2); width: 100%; position: fixed; top: 0; left: 0; text-align: center; }");

			    /* Container */
			    out.println(".container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.2); width: 60%; margin-top: 80px; transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out; }");
			    out.println(".container:hover { transform: scale(1.02); box-shadow: 0px 10px 25px rgba(0, 0, 0, 0.3); }");

			    /* Heading */
			    out.println("h2 { font-size: 26px; font-weight: 600; color: #333; }");

			    /* Table */
			    out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
			    out.println("th, td { padding: 12px; border: 1px solid #ddd; text-align: left; }");
			    out.println("th { background-color: #007bff; color: white; font-weight: 600; text-align: center; }");
			    out.println("td { font-size: 16px; }");

			    /* Question Styling */
			    out.println(".question { font-weight: 600; color: #333; font-size: 18px; }");

			    /* Radio Buttons */
			    out.println("input[type='radio'] { margin: 5px; transform: scale(1.2); cursor: pointer; transition: 0.2s; }");
			    out.println("input[type='radio']:hover { transform: scale(1.3); }");

			    /* Submit Button */
			    out.println("button { background: #007bff; border: none; color: white; padding: 12px 20px; font-size: 16px; border-radius: 50px; cursor: pointer; transition: all 0.3s ease-in-out; font-weight: 600; margin-top: 20px; }");
			    out.println("button:hover { background: #0056b3; transform: translateY(-3px) scale(1.1); box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.3); }");

			    out.println("</style>");
			    out.println("</head><body>");

			    /* Header */
			    out.println("<div class='header'> Feedback Form</div>");

			    /* Main Container */
			    out.println("<div class='container'>");
			    out.println("<h2>We Value Your Feedback</h2>");
			    out.println("<p><strong>User ID : </strong> " + userid + "</p>");

			    /* Form Start */
			    out.println("<form action='submitFeedback' method='post'>");
			    out.println("<input type='hidden' name='email' value='" + email + "'>");
			    out.println("<input type='hidden' name='password' value='" + password + "'>");

			    /* Table Start */
			    out.println("<table>");
			    out.println("<tr><th>Question</th><th>Rating (1-5)</th></tr>");

			    for (Question q : questions) {
			        out.println("<tr>");
			        out.println("<td class='question'>" + q.getText() + "</td>");
			        out.println("<td style='text-align: center;'>");
			        out.println("<input type='hidden' name='question_" + q.getText() +"' value='" + q.getText() +"'>");
			        for (int i = 1; i <= 5; i++) {
			            out.println("<input type='radio' name='answer_" + q.getId() + "' value='" + i + "' required> " + i);
			        }
			        out.println("</td>");
			        out.println("</tr>");
			    }

			    out.println("</table>");

			    /* Submit Button */
			    out.println("<button type='submit'>Submit Feedback</button>");

			    out.println("</form>"); // Close Form
			    out.println("</div>");
			    out.println("</body></html>");

			} else {
				out.println("<html><head><title>Feedback Status</title>");
				out.println("<style>");

				/* General Styling */
				out.println("body { font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #dfe9f3, #ffffff); display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");

				/* Main Container */
				out.println(".container { background: #ffffff; padding: 30px; border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.15); text-align: center; width: 350px; transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out; }");
				out.println(".container:hover { transform: scale(1.02); box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.2); }");

				/* Heading */
				out.println("h2 { color: #e74c3c; font-size: 24px; margin-bottom: 15px; font-weight: 600; }");

				/* Button Styling */
				out.println("button { background: #007bff; border: none; color: white; padding: 12px 22px; font-size: 17px; border-radius: 8px; cursor: pointer; transition: 0.3s ease-in-out; width: 100%; margin-top: 15px; font-weight: 500; }");
				out.println("button:hover { background: #0056b3; transform: scale(1.05); box-shadow: 0px 4px 10px rgba(0, 91, 187, 0.3); }");
				out.println("button a { color: white; text-decoration: none; display: block; }");

				/* Animation */
				out.println("@keyframes fadeIn { from { opacity: 0; transform: translateY(-10px); } to { opacity: 1; transform: translateY(0); } }");
				out.println(".container { animation: fadeIn 0.8s ease-in-out; }");

				out.println("</style>");
				out.println("</head><body>");

				/* Content */
				out.println("<div class='container'>");
				out.println("<h2>Oops! You Need to Register First</h2>");
				out.println("<button><a href='register.html'>Register Now</a></button>");
				out.println("</div>");

				out.println("</body></html>");

			}

		}
		// Display feedback form again
	}

}
