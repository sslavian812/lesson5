package ru.ifmo.mobdev.shalamov;

import android.widget.ArrayAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 14.10.13
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class XMLParser {


    static final String KEY_LINK = "link"; // parent node
    static final String KEY_TITLE = "title";
    static final String KEY_DATE = "pubDate";
    static final String KEY_DESC = "description";
    static final String KEY_ITEM = "item";


    private Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setCoalescing(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            try {
                is.setCharacterStream(new StringReader(xml));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            return null;
        } catch (SAXException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

        return doc;
    }

    private String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    private final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    //if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    //}
                }
            }
        }
        return "no data in xml";
    }

    public ArrayList<FeedItem> getFeed(String xml)
    {
        XMLParser parser = new XMLParser();

        Document doc = parser.getDomElement(xml); // getting DOM element
        NodeList nl = null;

        try {
            nl = doc.getElementsByTagName(KEY_ITEM);  // getting a list of nodes
        }
        catch (Exception exc)
        {
            return null;
        }

        ArrayList<FeedItem> feed = new ArrayList<FeedItem>();


        for (int i = 0; i < nl.getLength(); i++) {
            String link = parser.getValue((Element)nl.item(i), KEY_LINK);
            String description = parser.getValue((Element)nl.item(i), KEY_DESC);
            String title = parser.getValue((Element)nl.item(i), KEY_TITLE);
            String pubDate = parser.getValue((Element) nl.item(i), KEY_DATE);


            if(description == null)
                description = "fuck DOM XMLParser";

            if (description.indexOf("&amp;") != -1) {
                description = description.replaceAll("&amp;", "&");
            }
            if (description.indexOf("&quot;") != -1) {
                description = description.replaceAll("&quot;", "\"");
            }
            if (description.indexOf("<br>") != -1) {
                description = description.replaceAll("<br>", "\n");
            }
            if (description.indexOf("&gt;") != -1) {
                description = description.replaceAll("&gt;", "^");
            }

            feed.add(new FeedItem(link, title, pubDate, description, 0));
        }

        return feed;
    }
}
