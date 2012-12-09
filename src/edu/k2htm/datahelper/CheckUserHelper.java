package edu.k2htm.datahelper;

public interface CheckUserHelper extends DataHelper {
	public boolean checkUser(String username,String password) throws Exception;
	public boolean register(String username,String password) throws Exception;
}
