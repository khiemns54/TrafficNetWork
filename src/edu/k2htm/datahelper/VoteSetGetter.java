package edu.k2htm.datahelper;

public class VoteSetGetter {
	public static final String DB_VOTE_TABLENAME="vote";
	public static final String DB_VOTE_VOTER_COL="voter";
	public static final String DB_VOTE_TYPE_COL="bonus";
	public static final String DB_VOTE_CAUTION="caution";
	private VoteHelper helper;
	public VoteSetGetter(VoteHelper helper) {
		this.setHelper(helper);
	}
	public static String getCreateTableQuery(){
		String query = "CREATE TABLE IF NOT EXISTS`" + DB_VOTE_TABLENAME+"`(`"
				
				+ DB_VOTE_VOTER_COL + "` varchar(50) NOT NULL," + "`"
				+ DB_VOTE_CAUTION + "` int NOT NULL," + "`"
				+ DB_VOTE_TYPE_COL + "` boolean NOT NULL DEFAULT TRUE," 
				
				+ "PRIMARY KEY (`"+DB_VOTE_VOTER_COL+"`,`"+DB_VOTE_CAUTION+"`)," 
				+ "CONSTRAINT `vote_ibfk_1` FOREIGN KEY (`"
				+ DB_VOTE_VOTER_COL + "`) REFERENCES `"
				+ User.DB_USER_TABLENAME + "` (`" + User.DB_USER_USERNAME_COL
				+ "`) ON UPDATE CASCADE,"
				
				+ "CONSTRAINT `vote_ibfk_2` FOREIGN KEY (`"
				+ DB_VOTE_CAUTION + "`) REFERENCES `"
				+ Caution.DB_CAUTION_TABLENAME + "` (`" + Caution.DB_CAUTION_ID_COL
				+ "`) ON UPDATE CASCADE"
				
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		return query;

	}
	
	public int[] getVote(int cautionID) throws Exception{
		int[] tmp;
		this.helper.init();
		tmp=this.helper.getVote(cautionID);
		this.helper.close();
		return tmp;
	}
	
	public VoteHelper getHelper() {
		return helper;
	}
	public void setHelper(VoteHelper helper) {
		this.helper = helper;
	}
	public void vote(String username, int cautionID, boolean bonus) throws Exception {
		this.helper.init();
		this.helper.vote(cautionID, username, bonus);
		this.helper.close();
	}
}
