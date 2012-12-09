package edu.k2htm.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.k2htm.datahelper.Comment;
import edu.k2htm.datahelper.CommentGetter;
import edu.k2htm.datahelper.DataHelper;
import edu.k2htm.log.Log;

/**
 * Servlet implementation class GetComment
 */
public class GetComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String TAG = "GetComment";
	private int cautionID=-1;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log.d(TAG,"Doget");
		response.setCharacterEncoding("utf-8");
		String tmp=request.getParameter(Comment.DB_COMMENT_CAUTION_COL);
		PrintWriter printWriter=new PrintWriter(response.getOutputStream());
		if(tmp==null){
			Log.d(TAG, "Null");
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
			return ;
		}
		cautionID=Integer.parseInt(tmp);
		CommentGetter commentGetter=new CommentGetter(cautionID, new DatabaseConnection(ServletListener.dbURL,ServletListener.username,ServletListener.password,ServletListener.dbName));
		try {
			printWriter.println(commentGetter.getCommentsAsXML(cautionID));
			printWriter.flush();
		} catch (Exception e) {
			Log.d(TAG,"ex:"+e.getMessage());
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
		}
		Log.i(TAG,"Finished");
	}

}
