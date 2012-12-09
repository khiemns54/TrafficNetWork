package edu.k2htm.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.k2htm.datahelper.DataHelper;
import edu.k2htm.datahelper.Report;
import edu.k2htm.datahelper.ReportGetHelper;
import edu.k2htm.datahelper.ReportGetter;
import edu.k2htm.log.Log;

/**
 * Servlet implementation class GetInfo
 */
public class GetInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String TAG = "GetInfo";
	private int period=0;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String tmp=request.getParameter(Report.PERIOD);
		PrintWriter printWriter=new PrintWriter(response.getOutputStream());
		if(tmp==null){
			Log.d(TAG,"null");
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
			return;
		}
		period=Integer.parseInt(tmp);
		ReportGetHelper reportGetHelper=new DatabaseConnection(ServletListener.dbURL,ServletListener.username,ServletListener.password,ServletListener.dbName);
		String result;
		try {
			Log.d(TAG,"Period:"+period);
			result = new ReportGetter(reportGetHelper).getReportAsXML(period);
			printWriter.println(result);
			printWriter.flush();
		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
		}
		
		Log.d(TAG, "Finished");
	}

}
