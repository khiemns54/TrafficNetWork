package edu.k2htm.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.k2htm.datahelper.Comment;
import edu.k2htm.datahelper.DataHelper;
import edu.k2htm.log.Log;

/**
 * Servlet implementation class SendComment
 */
public class SendComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String TAG="SendComment";
	private String commenter;
	private int cautionID=-1;
	private String commentString;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		commenter=request.getParameter(Comment.DB_COMMENT_COMMENTER_COL);
		cautionID=Integer.parseInt(request.getParameter(Comment.DB_COMMENT_CAUTION_COL));
		commentString=request.getParameter(Comment.DB_COMMENT_COMMENT_COL);
		PrintWriter printWriter=new PrintWriter(response.getOutputStream());
		if(cautionID<0 || commenter==null){
			Log.d(TAG, "Null");
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
			return;
		}
		Comment comment=new Comment(commenter, cautionID, commentString, new DatabaseConnection(ServletListener.dbURL,ServletListener.username,ServletListener.password,ServletListener.dbName));
		try {
			comment.sendComment();
			printWriter.println(DataHelper.STATUS_OK);
			printWriter.flush();
		} catch (Exception e) {
			Log.d(TAG, "EX:"+e.getMessage());
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
			
		}
		Log.d(TAG, "Finished");
	}

}
