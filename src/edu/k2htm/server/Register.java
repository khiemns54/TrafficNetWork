package edu.k2htm.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.k2htm.datahelper.CheckUserHelper;
import edu.k2htm.datahelper.DataHelper;
import edu.k2htm.datahelper.User;
import edu.k2htm.log.Log;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String TAG = "Register";
	private String username;
	private String password;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log.d(TAG, "doGet");
		username = request.getParameter(User.DB_USER_USERNAME_COL);
		password = request.getParameter(User.DB_USER_PASSWORD_COL);
		
		PrintWriter printWriter = new PrintWriter(response.getOutputStream());
		if(username==null||password==null){
			Log.d(TAG,"null");
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
			return;
		}
		CheckUserHelper checkUserHelper = new DatabaseConnection(ServletListener.dbURL,ServletListener.username,ServletListener.password,ServletListener.dbName);
		try {
			
			checkUserHelper.init();
			boolean result = checkUserHelper.register(username, password);
			if (result == true)
				printWriter.println(DataHelper.STATUS_OK);
			else
				printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
			Log.d(TAG, "Finished registration:"+result);
		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
		} finally{
			checkUserHelper.close();
		}
		

	}

}
