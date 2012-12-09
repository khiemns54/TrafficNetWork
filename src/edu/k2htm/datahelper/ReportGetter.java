package edu.k2htm.datahelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.k2htm.log.Log;

public class ReportGetter {
	private ArrayList<Report> reports;
	private ReportGetHelper reportGetHelper;
	public static final String REPORT_ROOT_TAG = "reportList";
	public static final String REPORT_ELEMENT_TAG = "report";
	public static final String USERNAME_TAG = "username";
	public static final String TIME_TAG = "time";
	public static final String LAT_TAG = "lat";
	public static final String LNG_TAG = "lng";
	public static final String DESCRIPTION_TAG = "des";
	public static final String TYPE_TAG = "type";
	public static final String IMAGE_TAG = "image";
	public static final String TAG = "ReportGetter";
	public static final String ID_TAG = "id";
	public static final String UPVOTE_TAG = "upvote";
	public static final String DOWNVOTE_TAG = "downvote";

	public ReportGetter(ReportGetHelper reportGetHelper) {
		// TODO Auto-generated constructor stub
		reports = new ArrayList<Report>();
		this.setReportGetHelper(reportGetHelper);
	}

	/* test */
	public ReportGetter(ArrayList<Report> reports) {
		// TODO Auto-generated constructor stub
		this.reports = reports;
	}

	public ReportGetter(String xmlStr) {
		// TODO Auto-generated constructor stub
		ArrayList<Report> inputRepList = parseReportXml(xmlStr);

		this.reports = inputRepList;
	}

	// end test///////////
	public static ArrayList<Report> parseReportXml(String xmlStr) {
		Log.d(TAG, "XML:" + xmlStr);
		ArrayList<Report> inputRepList = new ArrayList<Report>();
		try {
			// xmlStr need to be convert to avoid premature end of file
			String xmlStrConverted = "";
			// xmlStr String to inputStream
			InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
			// read it with BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				xmlStrConverted += line + "\n";
			}
			br.close();
			// new InputStream from xmlStrConverted
			is = new ByteArrayInputStream(xmlStrConverted.getBytes());
			// pasrse (inputStream)XMLString
			String username = "", image = "", des = "", lat = "", lng = "", time = "", type = "", id = "";

			// TODO lamf cais nayf
			int up = 0;
			int down = 0;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(REPORT_ELEMENT_TAG);
			// browse each element tag
			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					username = getTagValue(USERNAME_TAG, eElement);
					time = getTagValue(TIME_TAG, eElement);
					lat = getTagValue(LAT_TAG, eElement);
					lng = getTagValue(LNG_TAG, eElement);
					des = getTagValue(DESCRIPTION_TAG, eElement);
					type = getTagValue(TYPE_TAG, eElement);
					image = getTagValue(IMAGE_TAG, eElement);
					id = getTagValue(ID_TAG, eElement);
					up = Integer.parseInt(getTagValue(UPVOTE_TAG, eElement)) ;
					down = Integer.parseInt(getTagValue(DOWNVOTE_TAG, eElement));
				}

				Report report = new Report(Integer.parseInt(id), username,
						Long.parseLong(time), Integer.parseInt(lat),
						Integer.parseInt(lng), des, Short.parseShort(type),
						image, up, down);
				inputRepList.add(report);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(inputRepList.toString());
		return inputRepList;
	}

	public ArrayList<Report> getReports(int periodMin) throws Exception {
		Log.d(TAG, "getReports:" + periodMin);
		this.reportGetHelper.init();
		reports = getReportGetHelper().getReport(periodMin);
		this.reportGetHelper.close();
//		Log.d(TAG, "Get Result:\n" + toString());
		return reports;
	}

	public String getReportAsXML(int periodMin) throws Exception {
		// COMENT OUT THIS FOR TEST //
		this.getReports(periodMin);

		String outputXmlString = "";
		try {
			// create doc object
			// initialize
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			// create root element
			Element rootElement = doc.createElement(REPORT_ROOT_TAG);
			doc.appendChild(rootElement);
			for (int i = 0; i < reports.size(); i++) {
				// XML structure
				// <reportList>
				// //<report id="1">
				// ////<username>
				// ////<time>
				// ////<lat>
				// ////<lng>
				// ////<des>
				// ////<type>
				// ////<image>
				// //</report>
				// </reportList>
				// root elements
				Report curReport = reports.get(i);
				// staff elements
				Element report = doc.createElement(REPORT_ELEMENT_TAG);
				rootElement.appendChild(report);

				// set attribute to report element
				Attr attr = doc.createAttribute("id");
				attr.setValue("1");
				report.setAttributeNode(attr);

				// shorten way
				// staff.setAttribute("id", "1");

				// username elements
				Element username = doc.createElement(USERNAME_TAG);
				username.appendChild(doc.createTextNode(curReport.getUsername()));
				report.appendChild(username);

				// time elements
				Element time = doc.createElement(TIME_TAG);
				time.appendChild(doc.createTextNode(curReport.getTime() + ""));
				report.appendChild(time);

				// latitude elements
				Element lat = doc.createElement(LAT_TAG);
				lat.appendChild(doc.createTextNode(curReport.getLat() + ""));
				report.appendChild(lat);
				// longitude elements
				Element lng = doc.createElement(LNG_TAG);
				lng.appendChild(doc.createTextNode(curReport.getLng() + ""));
				report.appendChild(lng);
				// longitude elements
				Element des = doc.createElement(DESCRIPTION_TAG);
				des.appendChild(doc.createTextNode(curReport.getDescription()));
				report.appendChild(des);
				// longitude elements
				Element type = doc.createElement(TYPE_TAG);
				type.appendChild(doc.createTextNode(curReport.getType() + ""));
				report.appendChild(type);
				// longitude elements
				Element image = doc.createElement(IMAGE_TAG);
				image.appendChild(doc.createTextNode(curReport.getImage()));
				report.appendChild(image);
				// id elements
				Element id = doc.createElement(ID_TAG);
				id.appendChild(doc.createTextNode(curReport.getCautionID() + ""));
				report.appendChild(id);
				// upvote elements
				Element upvote = doc.createElement(UPVOTE_TAG);
				upvote.appendChild(doc.createTextNode(curReport.getVoteUp()
						+ ""));
				report.appendChild(upvote);
				// downvote elements
				Element downvote = doc.createElement(DOWNVOTE_TAG);
				downvote.appendChild(doc.createTextNode(curReport.getVoteUp()
						+ ""));
				report.appendChild(downvote);
				// // write the content into xml file
				// TransformerFactory transformerFactory = TransformerFactory
				// .newInstance();
				// Transformer transformer =
				// transformerFactory.newTransformer();
				// DOMSource source = new DOMSource(doc);
				// StreamResult result = new StreamResult(new
				// File("C:\\file.xml"));

				// Output to String
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);

				OutputStream output = new ByteArrayOutputStream();
				StreamResult result = new StreamResult(output);

				transformer.transform(source, result);
				outputXmlString = output.toString();
			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		Log.d(TAG, "xml result:" + outputXmlString);
		return outputXmlString;
	}

	public ReportGetHelper getReportGetHelper() {
		return reportGetHelper;
	}

	public void setReportGetHelper(ReportGetHelper reportGetHelper) {
		this.reportGetHelper = reportGetHelper;
	}

	protected static String getTagValue(String sTag, Element eElement) {

		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue != null) {
			return nValue.getNodeValue();
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Report:" + this.reports.size() + "\n");
		for (int i = 0; i < reports.size(); i++) {
			buffer.append(reports.get(i).toString() + "\n");
		}

		return buffer.toString();
	}
}
