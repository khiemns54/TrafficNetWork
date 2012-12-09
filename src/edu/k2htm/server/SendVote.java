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
 * Servlet implementation class SendVote
 */
public class SendVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "SendVote";
    private String username;
    private int cautionID=-1;
    private boolean bonus=false;
    private VoteHelper helper;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log.d(TAG, "doGet");
		username=request.getParameter(VoteSetGetter.DB_VOTE_VOTER_COL);
		cautionID=Integer.parseInt(request.getParameter(VoteSetGetter.DB_VOTE_CAUTION));
		bonus=Boolean.parseBoolean(request.getParameter(VoteSetGetter.DB_VOTE_TYPE_COL));
		PrintWriter printWriter=new PrintWriter(response.getOutputStream());
		if(username==null||cautionID==-1){
			Log.d(TAG, "Null");
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
			return ;
		}
		VoteHelper helper=new DatabaseConnection(ServletListener.dbURL,ServletListener.username,ServletListener.password,ServletListener.dbName);
		VoteSetGetter voteSetGetter=new VoteSetGetter(helper);
		try {
			voteSetGetter.vote(username,cautionID,bonus);
			printWriter.println(DataHelper.STATUS_OK);
			printWriter.flush();
		} catch (Exception e) {
			Log.d(TAG, "Ex:"+e.getMessage());
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
		}
		Log.d(TAG,"Finished");
	}

}
