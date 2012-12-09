package edu.k2htm.datahelper;

import java.util.ArrayList;

public interface ReportGetHelper extends DataHelper {
	public ArrayList<Report> getReport(int periodMin) throws Exception;
}
