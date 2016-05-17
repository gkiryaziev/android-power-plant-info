package ua.kiryaziev.PowerPlantInfo;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLParser {

	public String getXmlFromUrl(String url, String userName, String userPass) {
		try {
			
			return new AsyncHttp().execute(url, userName, userPass).get();	// упрощено

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// public String getXmlFromUrl(String url) {
	// String xml = null;
	//
	// try {
	// HttpClient httpClient = new DefaultHttpClient();
	// HttpPost httpPost = new HttpPost(url);
	//
	// HttpResponse httpResponse = httpClient.execute(httpPost);
	// HttpEntity httpEntity = httpResponse.getEntity();
	// xml = EntityUtils.toString(httpEntity);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return xml;
	// }

	public Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			is.setEncoding("UTF-8");
			doc = db.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return doc;
	}

	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}
}
