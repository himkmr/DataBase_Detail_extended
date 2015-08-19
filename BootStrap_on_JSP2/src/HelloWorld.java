import java.io.IOException;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String message = "";
	static String url;
	static Connection conn = null;
	Properties props;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public void init() throws ServletException {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		url = "jdbc:oracle:thin:testuser/password@localhost";
		// properties for creating connection to Oracle database
		props = new Properties();
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");

		// creating connection to Oracle database using JDBC
		try {
			conn = DriverManager.getConnection(url, props);
			if (conn == null)
				throw new SQLException();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		message ="";
		ResultSet result = null;
		String query = "select CUST_FIRST_NAME from DEMO_CUSTOMERS where CUSTOMER_ID=2";
		// System.out.println(query);
		PreparedStatement preStatement;
		try {
			preStatement = conn.prepareStatement(query);
			result = preStatement.executeQuery();
			while (result.next()) {
				message = message + result.getString("CUST_FIRST_NAME") + "\n";

			}
			//message = result.getString("CUST_FIRST_NAME");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Set response content type
		response.setContentType("text/html");
		// Actual logic goes here.
		request.setAttribute("message", message);

		getServletContext().getRequestDispatcher("/output.jsp").forward(
				request, response);

		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
