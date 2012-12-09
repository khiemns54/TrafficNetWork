package edu.k2htm.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import edu.k2htm.datahelper.Caution;
import edu.k2htm.datahelper.CautionHelper;
import edu.k2htm.datahelper.DataHelper;
import edu.k2htm.log.Log;

/**
 * Servlet implementation class SendInfo
 */
public class SendInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "SendInfo";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int REQUEST_SIZE = 1024 * 1024 * 50;
	private ServletContext context;
	private String username;
	private int lat;
	private int lng;
	private long time;
	private String comment;
	private short type;
	private FileItem uploadFileItem = null;
	private File fileUpload;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// checks if the request actually contains upload file
		Log.i(TAG, "doPost");
		PrintWriter printWriter = new PrintWriter(response.getOutputStream());
		context = getServletContext();
		if (!ServletFileUpload.isMultipartContent(request)) {
			return;
		}

		// configures some settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(REQUEST_SIZE);

		// constructs the directory path to store upload file

		// creates the directory if it does not exist
		File uploadDir = (File) context
				.getAttribute(ServletListener.IMAGES_FOLDER);

		try {
			Log.i(TAG, "get parameter");
			this.time = new Date().getTime();
			Iterator iter = upload.parseRequest(request).iterator();
			
			// iterates over form's fields
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					uploadFileItem = item;
					Log.i(TAG, "Get filed:" + uploadFileItem.getFieldName());
					// String fileName = new File(item.getName()).getName();
					//
					// File storeFile = new File(uploadDir,fileName);
					//
					// // saves the file on disk
					// Log.i(storeFile.getAbsolutePath());
					// item.write(storeFile);
				} else {
					String tmpFieldName = item.getFieldName();
					Log.i(TAG, "Get filed:" + tmpFieldName+":values="+item.getString());
					
					if (tmpFieldName.equals(Caution.DB_CAUTION_USERNAME_COL))
						this.username = item.getString();
					if (tmpFieldName.equals(Caution.DB_CAUTION_TYPE_COL))
						this.type = Short.parseShort(item.getString());
					if (tmpFieldName.equals(Caution.DB_CAUTION_LAT_COL))
						this.lat = Integer.parseInt(item.getString());
					if (tmpFieldName.equals(Caution.DB_CAUTION_LNG_COL))
						this.lng = Integer.parseInt(item.getString());
					if (tmpFieldName.equals(Caution.DB_CAUTION_DESCRIPTION_COL))
						this.comment = item.getString();

				}
			}
			if (uploadFileItem != null) {
				Log.i(TAG,"Has image");
				fileUpload = new File(uploadDir, this.username + "_"
						+ this.time + "_"
						+ (new File(uploadFileItem.getName()).getName()));
				uploadFileItem.write(fileUpload);
				Log.i(TAG, "Saved " + fileUpload.getAbsolutePath());

			}

			CautionHelper cautionHelper = new DatabaseConnection(ServletListener.dbURL,ServletListener.username,ServletListener.password,ServletListener.dbName);
			cautionHelper.init();
			cautionHelper.report(username, type, time, lat, lng, fileUpload,
					comment);
			cautionHelper.close();
			Log.i(TAG, "upload success!");
			printWriter.println(DataHelper.STATUS_OK);
			printWriter.flush();
		} catch (Exception ex) {
			Log.i(TAG,ex.getMessage());
			ex.printStackTrace();
			printWriter.println(DataHelper.STATUS_FAIL);
			printWriter.flush();
			Log.i(TAG, "upload fail");

		}
		Log.i(TAG, "finished.");

	}
}