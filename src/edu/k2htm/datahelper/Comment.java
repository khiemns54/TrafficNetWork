package edu.k2htm.datahelper;


public class Comment {
	public static final String TAG = "Comment";
	public static final String DB_COMMENT_TABLENAME="comment";
	public static final String DB_COMMENT_ID_COL="ID";
	public static final String DB_COMMENT_CAUTION_COL="caution";
	public static final String DB_COMMENT_COMMENTER_COL="commenter";
	public static final String DB_COMMENT_COMMENT_COL="comment";
	public static final String DB_COMMENT_TIME_COL="time";
	
	private String username;
	private int cautionID;
	private String comment;
	private CommentHelper commentHelper;
	private long time;
	
	//TEST 
	public Comment(String user,int ID,String comment,long time) {
		this.setCommenter(user);
		this.setCautionID(ID);
		this.setComment(comment);
		this.setTime(time);
	}
	//endtest//
	public Comment(String user,int ID,String comment,CommentHelper helper) {
		this.setCommenter(user);
		this.setCautionID(ID);
		this.setComment(comment);
		this.setCommentHelper(helper);
	}
	
	public Comment() {
		
	}

	public void sendComment() throws Exception{
		commentHelper.init();
		commentHelper.send(username, cautionID, comment);
		commentHelper.close();
		//Log.i(TAG,"Send comment ok");
	}
	public static String getCreateTableQuery(){
		String query = "CREATE TABLE IF NOT EXISTS`" + DB_COMMENT_TABLENAME
				+ "` (" + "`"+DB_COMMENT_ID_COL+"` int(11) NOT NULL AUTO_INCREMENT," + "`"
				+ DB_COMMENT_COMMENTER_COL + "` varchar(50) NOT NULL," + "`"
				+ DB_COMMENT_CAUTION_COL + "` int NOT NULL," + "`"
				+ DB_COMMENT_COMMENT_COL + "` varchar(500) DEFAULT NULL,"+ "`"
				+ DB_COMMENT_TIME_COL + "` varchar(20) NOT NULL ,"
				+ "PRIMARY KEY (`ID`)," 
				+ "CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`"
				+ DB_COMMENT_COMMENTER_COL + "`) REFERENCES `"
				+ User.DB_USER_TABLENAME + "` (`" + User.DB_USER_USERNAME_COL
				+ "`) ON UPDATE CASCADE,"
				
				+ "CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`"
				+ DB_COMMENT_CAUTION_COL + "`) REFERENCES `"
				+ DB_COMMENT_CAUTION_COL + "` (`" + Caution.DB_CAUTION_ID_COL
				+ "`) ON UPDATE CASCADE"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		
		return query;
	}

	public String getCommenter() {
		return username;
	}

	public void setCommenter(String username) {
		this.username = username;
	}

	public int getCautionID() {
		return cautionID;
	}

	public void setCautionID(int cautionID) {
		this.cautionID = cautionID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	public CommentHelper getCommentHelper() {
		return commentHelper;
	}
	public void setCommentHelper(CommentHelper commentHelper) {
		this.commentHelper = commentHelper;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	@Override
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("----------\n");
		buffer.append("Commenter:"+this.username+"\n");
		buffer.append("Time	:"+this.time+"\n");
		buffer.append("Comment:"+this.comment+"\n");
		buffer.append("ID:"+this.cautionID+"\n");
		buffer.append("----------\n");
		return buffer.toString();
	}
}
