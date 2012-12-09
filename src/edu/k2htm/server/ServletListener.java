package edu.k2htm.server;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import edu.k2htm.log.Log;

/**
 * Application Lifecycle Listener implementation class ServletListener
 * 
 */
@WebListener
public class ServletListener implements ServletContextListener {
	public static final String TAG = "ServletListener";
	public static final String IMAGES_FOLDER = "Images";
	public static final String CONFIG="config";
	private ServletContext context;
	public static String dbURL;
	public static String username;
	public static String password;
	public static String dbName;

	/**
	 * Default constructor.
	 */
	public ServletListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG , "Init");
		context = arg0.getServletContext();
		loadConfig();
		initImagesFolder();
		initDatabases();
		Log.i(TAG , "Init ok");
	}

	private void loadConfig() {
		Log.d(TAG, "Load config");
		File configFile=new File(context.getRealPath("/")+File.separator+CONFIG);
		if(configFile.exists()){
			try {
				Scanner s=new Scanner(configFile);
				dbURL=s.nextLine();
				username=s.nextLine();
				password=s.nextLine();
				dbName=s.nextLine();
				Log.d(TAG,"Read config finished");
			} catch (FileNotFoundException e) {
				Log.d(TAG,e.getMessage());
			}
		}
			
		
	}

	private void initDatabases()  {
		// TODO Auto-generated method stub
		DatabaseConnection dataHelper=new DatabaseConnection(dbURL,username,password,dbName);
		try {
			dataHelper.init();
			dataHelper.initTable();
			dataHelper.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void initImagesFolder() {
		// TODO Auto-generated method stub
		Log.i(TAG,"Init folder");
		File imagesFolder = new File(context.getRealPath("/") + File.separator
				+ IMAGES_FOLDER);
		if (!imagesFolder.exists()) {
			imagesFolder.mkdir();
		}
		context.setAttribute(IMAGES_FOLDER, imagesFolder);
		Log.i(TAG,"Init folder OK");
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
