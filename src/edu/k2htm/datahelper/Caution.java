package edu.k2htm.datahelper;

import java.io.File;
import java.util.Date;

import edu.k2htm.log.Log;



public class Caution {
	public static final String TAG = "Caution";
	public static final short JAM = 1;
	public static final String DB_CAUTION_USERNAME_COL = "username";
	public static final String DB_CAUTION_TABLENAME = "caution";
	public static final String DB_CAUTION_TIME_COL = "time";
	public static final String DB_CAUTION_LAT_COL = "lat";
	public static final String DB_CAUTION_LNG_COL = "lng";
	public static final String DB_CAUTION_IMAGE_COL = "image";
	public static final String DB_CAUTION_DESCRIPTION_COL = "description";
	public static final String DB_CAUTION_TYPE_COL="type";
	public static final String DB_CAUTION_ID_COL = "ID";
	
	private String username;
	private long time;
	private int lat;
	private int lng;
	private File image;
	private String comment;
	private CautionHelper cautionHelper;
	private short type;
	public Caution(String username,long time,short type,int lat,int lng,File image,String comment,CautionHelper cautionHelper) {
		// TODO Auto-generated constructor stub
		this.setUsername(username);
		this.setTime(time);
		this.setLat(lat);
		this.setLng(lng);
		this.setImage(image);
		this.setComment(comment);
		this.setType(type);
		this.setCautionHelper(cautionHelper);
	}

	public Caution(String username,short type,int lat,int lng,File image,String comment,CautionHelper cautionHelper) {
		// TODO Auto-generated constructor stub
		this.setUsername(username);
		this.setLat(lat);
		this.setLng(lng);
		this.setImage(image);
		this.setComment(comment);
		this.setTime(new Date().getTime());
		this.setType(type);
		this.setCautionHelper(cautionHelper);
	}
	
	public void report() throws Exception{

		Log.i(TAG,"Caution.report() ");
		cautionHelper.init();
		cautionHelper.report(username,type, time, lat, lng, image, comment);
		
		Log.i(TAG, "Report finished");
		cautionHelper.close();
	}
	
	public static String getCreateTableQuery() {
		// TODO Auto-generated method stub
		String query = "CREATE TABLE IF NOT EXISTS`" + DB_CAUTION_TABLENAME
				+ "` (" + "`"+DB_CAUTION_ID_COL+"` int(11) NOT NULL AUTO_INCREMENT," + "`"
				+ DB_CAUTION_USERNAME_COL + "` varchar(50) NOT NULL," + "`"
				+ DB_CAUTION_TIME_COL + "` BIGINT NOT NULL," + "`"
				+ DB_CAUTION_LAT_COL + "` int NOT NULL," + "`"
				+ DB_CAUTION_LNG_COL + "` int NOT NULL," + "`"
				+ DB_CAUTION_TYPE_COL + "` smallint NOT NULL," + "`"
				+ DB_CAUTION_IMAGE_COL + "` varchar(500) DEFAULT NULL," + "`"
				+ DB_CAUTION_DESCRIPTION_COL + "` varchar(500) DEFAULT NULL,"
				+ "PRIMARY KEY (`ID`)," + "KEY `" + DB_CAUTION_USERNAME_COL
				+ "` (`" + DB_CAUTION_USERNAME_COL + "`),"
				+ "CONSTRAINT `caution_ibfk_1` FOREIGN KEY (`"
				+ DB_CAUTION_USERNAME_COL + "`) REFERENCES `"
				+ User.DB_USER_TABLENAME + "` (`" + User.DB_USER_USERNAME_COL
				+ "`) ON UPDATE CASCADE"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		
		return query;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public int getLng() {
		return lng;
	}

	public void setLng(int lng) {
		this.lng = lng;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public CautionHelper getCautionHelper() {
		return cautionHelper;
	}

	public void setCautionHelper(CautionHelper helper) {
		this.cautionHelper = helper;
	}
}