package edu.k2htm.datahelper;

public interface VoteHelper extends DataHelper{
	public int[] getVote(int cautionID) throws Exception;
	public void vote(int cautionID,String username,boolean bonus) throws Exception;
}
