
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Retrieve form data
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Save data to database

		try {
			boolean isemail = feedBackDAO.checkEmail(email);
			if (isemail) {
				out.println("<html><head><title>Registration Status</title>");
				out.println("<style>");
				out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 50px; }");
				out.println(".container { background: #fff; padding: 20px; border-radius: 10px; display: inline-block; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 350px; padding: 30px; }");
				out.println("h2 { color: #dc3545; font-size: 24px; }"); // Red color for "Already Registered"
				out.println("p { font-size: 16px; color: #333; }");
				out.println("button { background-color: #007bff; border: none; color: white; padding: 12px 20px; text-align: center; font-size: 16px; border-radius: 5px; cursor: pointer; transition: 0.3s; }");
				out.println("button:hover { background-color: #0056b3; }");
				out.println("button a { color: white; text-decoration: none; display: block; }");
				out.println("</style>");
				out.println("</head><body>");

				out.println("<div class='container' style='border: 2px solid #dc3545;'>"); // Red border to indicate existing registration
				out.println("<h2> Already Registered</h2>");
				out.println("<p>You have already registered. Please log in to continue.</p>");
				out.println("<button><a href='user_login.html'>Go to Login</a></button>");
				out.println("</div>");

				out.println("</body></html>");

			} else {
				boolean isSaved = feedBackDAO.saveData( name, email, password);
				// HTML response
				out.println("<html><head><title>Registration Status</title>");
				out.println("<style>");

				/* Import Google Fonts */
				out.println("@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap');");

				/* Background Styling */
				out.println("body { font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #74ebd5, #acb6e5); text-align: center; padding: 50px; color: #333; margin: 0; display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100vh; }");

				/* Message Box */
				out.println(".message { font-size: 28px; font-weight: 600; margin-bottom: 20px; padding: 20px; border-radius: 10px; background: white; box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.2); transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out; }");
				out.println(".message:hover { transform: scale(1.05); box-shadow: 0px 10px 25px rgba(0, 0, 0, 0.3); }");
				out.println(".success { color: #28a745; animation: fadeIn 1s ease-in-out; }");
				out.println(".error { color: #dc3545; animation: shake 0.5s ease-in-out; }");

				/* Buttons */
				out.println("button { background: #007bff; border: none; color: white; padding: 12px 20px; font-size: 16px; border-radius: 50px; cursor: pointer; transition: all 0.3s ease-in-out; font-weight: 600; margin: 10px; display: inline-block; }");
				out.println("button:hover { background: #0056b3; transform: translateY(-3px) scale(1.1); box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.3); }");
				out.println("button a { color: white; text-decoration: none; font-weight: bold; display: block; }");

				/* Error Button */
				out.println(".error button { background: #dc3545; }");
				out.println(".error button:hover { background: #c82333; transform: translateY(-3px) scale(1.1); }");

				/* Animations */
				out.println("@keyframes fadeIn { from { opacity: 0; transform: translateY(-20px); } to { opacity: 1; transform: translateY(0); } }");
				out.println("@keyframes shake { 0%, 100% { transform: translateX(0); } 25%, 75% { transform: translateX(-5px); } 50% { transform: translateX(5px); } }");

				out.println("</style>");
				out.println("</head><body>");

				if (isSaved) {
				    out.println("<div class='message success'>");
				    out.println("<h2> Registration Successful</h2>");
				    out.println("<button><a href='user_login.html'>click to Login</a></button>");
				    out.println("</div>");
				} else {
				    out.println("<div class='message error'>");
				    out.println("<h2>❌ Registration Failed</h2>");
				    out.println("<button><a href='register.html'> Try Again</a></button>");
				    out.println("</div>");
				}

				out.println("</body></html>");


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
