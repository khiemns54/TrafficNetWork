package edu.k2htm.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.k2htm.datahelper.DataHelper;
import edu.k2htm.datahelper.VoteHelper;
import edu.k2htm.datahelper.VoteSetGetter;
import edu.k2htm.log.Log;

/**
 * Servlet implementation class GetVote
 */
public class GetVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String TAG = "GetVote";
	private int cautionID=0;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tmop=request.getParameter(VoteSetGetter.DB_VOTE_CAUTION);
		PrintWriter printWriter=new PrintWriter(response.getOutputStream());
		if(tmop==null){
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
			return;
		}
		cautionID=Integer.parseInt(tmop);
		VoteHelper voteHelper=new DatabaseConnection(ServletListener.dbURL,ServletListener.username,ServletListener.password,ServletListener.dbName);
		VoteSetGetter voteSetGetter=new VoteSetGetter(voteHelper);
		try {
			int[] result=voteSetGetter.getVote(cautionID);
			printWriter.println(result[0]);
			printWriter.println(result[1]);
			printWriter.flush();
		} catch (Exception e) {
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
		}
		Log.i(TAG,"Finished");
	}

}
