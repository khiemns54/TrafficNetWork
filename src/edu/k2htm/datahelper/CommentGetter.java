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

public class CommentGetter {
	private static final String TAG = "CommentGetter";
	private CommentHelper commentHelper;
	private int cautionID;
	private ArrayList<Comment> comments;
	public static final String COMMENT_ROOT_ELEMENT = "commentList";
	public static final String COMMENT_ELEMENT_TAG= "commentNode";
	/* test */
	public CommentGetter(ArrayList<Comment> comments) {
		// TODO Auto-generated constructor stub
		this.comments = comments;
	}

	public CommentGetter(String xmlStr) {
		// TODO Auto-generated constructor stub
		ArrayList<Comment> inputComList = parseXmlDocument(xmlStr);

		this.comments = inputComList;
	}

	// end test///////////
	public CommentGetter(int cautionID, CommentHelper commentHelper) {
		this.setCautionID(cautionID);
		this.setCommentHelper(commentHelper);

	}

	public ArrayList<Comment> getComments(int cautionID) throws Exception {
		commentHelper.init();
		this.comments = commentHelper.getComments(cautionID);
		commentHelper.close();
		return comments;
	}

	public int getCautionID() {
		return cautionID;
	}

	public void setCautionID(int cautionID) {
		this.cautionID = cautionID;
	}

	public String getCommentsAsXML(int cautionID) throws Exception {
		// COMENT OUT THIS FOR TEST //
		Log.d(TAG, "get Comments as xml");
		this.getComments(cautionID);
		String outputXmlString = "";
		try {
			// create doc object
			// initialize
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			// create root element
			Element rootElement = doc.createElement(COMMENT_ROOT_ELEMENT);
			doc.appendChild(rootElement);
			for (int i = 0; i < comments.size(); i++) {
				// XML structure
				// <commentList>
				// //<commentNode id="1">
				// ////<commenter>
				// ////<cautionId>
				// ////<time>
				// ////<comment>
				// //</commentNode>
				// </commentList>
				// root elements
				Comment curComment = comments.get(i);
				// staff elements
				Element comment = doc
						.createElement(COMMENT_ELEMENT_TAG);
				rootElement.appendChild(comment);
	
				// set attribute to report element
				Attr attr = doc.createAttribute("id");
				attr.setValue("1");
				comment.setAttributeNode(attr);
	
				// shorten way
				// staff.setAttribute("id", "1");
	
				// id elements
				Element id = doc.createElement(Comment.DB_COMMENT_CAUTION_COL);
				id.appendChild(doc.createTextNode(curComment.getCautionID()
						+ ""));
				comment.appendChild(id);
	
				// commenter elements
				Element commenter = doc
						.createElement(Comment.DB_COMMENT_COMMENTER_COL);
				commenter.appendChild(doc.createTextNode(curComment
						.getCommenter()));
				comment.appendChild(commenter);
	
				// comment_content elements
				Element comment_content = doc
						.createElement(Comment.DB_COMMENT_COMMENT_COL);
				comment_content.appendChild(doc.createTextNode(curComment
						.getComment()));
				comment.appendChild(comment_content);
				// time elements
				Element time = doc.createElement(Comment.DB_COMMENT_TIME_COL);
				time.appendChild(doc.createTextNode(curComment.getTime() + ""));
				comment.appendChild(time);
	
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
		return outputXmlString;
	}

	public CommentHelper getCommentHelper() {
		return commentHelper;
	}

	public void setCommentHelper(CommentHelper commentHelper) {
		this.commentHelper = commentHelper;
	}

	public static ArrayList<Comment> parseXmlDocument(String xmlStr) {
		ArrayList<Comment> inputComList = new ArrayList<Comment>();
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
			String comenter = "", comment_content = "", cautionID = "", time = "", type = "";
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();
			NodeList nList = doc
					.getElementsByTagName(COMMENT_ELEMENT_TAG);
			// browse each element tag
			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					cautionID = getTagValue(Comment.DB_COMMENT_CAUTION_COL,
							eElement);

					comenter = getTagValue(Comment.DB_COMMENT_COMMENTER_COL,
							eElement);
					comment_content = getTagValue(
							Comment.DB_COMMENT_COMMENT_COL, eElement);

					time = getTagValue(Comment.DB_COMMENT_TIME_COL, eElement);

				}
				Comment comment = new Comment(comenter,
						Integer.parseInt(cautionID), comment_content,
						Long.parseLong(time));

				inputComList.add(comment);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputComList;
	}

	@Override
	public String toString() {
		// Log.d(TAG,"toString");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < comments.size(); i++) {
			buffer.append(comments.get(i).toString() + "\n");
		}
		return buffer.toString();
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
}
