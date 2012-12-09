package edu.k2htm.datahelper;

import edu.k2htm.log.Log;


public class User {
	public static final String DB_USER_TABLENAME = "user";
	public static final String DB_USER_USERNAME_COL = "username";
	public static final String DB_USER_PASSWORD_COL = "password";
	public static final String TAG = "User";
	private String username;
	private String password;
	private CheckUserHelper userHelper;
	public static String getCreateTableQuery() {
		// TODO Auto-generated method stub
		return "CREATE TABLE IF NOT EXISTS`"
				+ DB_USER_TABLENAME + "` (" + "`" + DB_USER_USERNAME_COL
				+ "` varchar(50) NOT NULL," + "`" + DB_USER_PASSWORD_COL
				+ "` varchar(50) NOT NULL," + "PRIMARY KEY (`"+DB_USER_USERNAME_COL+"`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}
	public User(String username,String password,CheckUserHelper checkUserHelper) {
		// TODO Auto-generated constructor stub
		this.username=username;
		this.password=password;
		this.setUserHelper(checkUserHelper);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public boolean checkUser() throws Exception{
		this.userHelper.init();
		boolean result= userHelper.checkUser(username, password);
		this.userHelper.close();
		return result;
	}
	public void register() throws Exception{
		Log.d(TAG,"Register");
		this.userHelper.init();
		userHelper.register(username, password);
		this.userHelper.close();
	}
	public CheckUserHelper getUserHelper() {
		return userHelper;
	}
	public void setUserHelper(CheckUserHelper userHelper) {
		this.userHelper = userHelper;
	}
}
