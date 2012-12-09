package edu.k2htm.datahelper;

import edu.k2htm.log.Log;

public class Report {
	public static final String TAG = "Report";
	public static final String PERIOD = "Period";
	
	private String username;
	private long time;
	private int lat;
	private int lng;
	private String image;
	private String description;
	private CautionHelper cautionHelper;
	private short type;
	private int cautionID;
	
	//
	private int voteUp;
	private int voteDown;
	
	//
	public Report(int cautionID,String username, long time, int lat, int lng,
			String des, short type, String image,int up,int down) {
		setUsername(username);
		setTime(time);
		setLat(lat);
		setLng(lng);
		setDescription(des);
		setType(type);
		setImage(image);
		setCautionID(cautionID);
		this.setVoteUp(up);
		this.setVoteDown(down);
	}

	public Report() {
		// TODO Auto-generated constructor stub
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CautionHelper getCautionHelper() {
		return cautionHelper;
	}

	public void setCautionHelper(CautionHelper cautionHelper) {
		this.cautionHelper = cautionHelper;
	}

	public short getType() {
		Log.i(TAG, "get Type"+type);
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.username + "\n");
		buffer.append(this.image + "\n");
		buffer.append(this.getDescription() + "\n");
		buffer.append(this.lat + " " + this.lng + "\n");
		buffer.append(this.time + "\n");
		buffer.append(this.type + "\n");
		return buffer.toString();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public int getCautionID() {
		return cautionID;
	}
	public void setCautionID(int cautionID) {
		this.cautionID = cautionID;
	}

	public int getVoteUp() {
		return voteUp;
	}

	public void setVoteUp(int voteUp) {
		this.voteUp = voteUp;
	}

	public int getVoteDown() {
		return voteDown;
	}

	public void setVoteDown(int voteDown) {
		this.voteDown = voteDown;
	}
}
