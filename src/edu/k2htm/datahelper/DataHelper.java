package edu.k2htm.datahelper;

public interface DataHelper {
	public static final String STATUS_OK="OK";
	public static final String STATUS_FAIL="FAIL";
	public void init() throws Exception;
	
	public void close();

}
