package com.jpmorgan.onlinetest.others;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
Implement a function folderNames, which accepts a string containing an XML file that specifies folder structure and returns all folder names that start with startingLetter. The XML format is given in the example below.

For example, for the letter 'u' and an XML file:

<?xml version="1.0" encoding="UTF-8"?>
<folder name="c">
    <folder name="program files">
        <folder name="uninstall information" />
    </folder>
    <folder name="users" />
</folder>
the function should return a collection with items "uninstall information" and "users" (in any order).
 */
public class Folders {
    public static Collection<String> folderNames(String xml, char startingLetter) throws Exception {

        List<String> retstr = new ArrayList<>();
        //throw new UnsupportedOperationException("Waiting to be implemented.");
        // create a new DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // use the factory to create a documentbuilder
        DocumentBuilder builder = factory.newDocumentBuilder();

        // create a new document from input source
        InputStream fis = new ByteArrayInputStream(xml.getBytes());
        InputSource is = new InputSource(fis);
        Document doc = builder.parse(is);


        NodeList nodeList = doc.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == nodeList.item(i).ELEMENT_NODE) {
                // do something with the current element

                String nodeName = nodeList.item(i).getNodeName();
                //System.out.println(nodeName);
                if (nodeName.equalsIgnoreCase("folder")) {


                    String nodeValue = nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue();
                    //System.out.println(nodeValue);
                    if (nodeValue != null && nodeValue.startsWith("" + startingLetter))
                        retstr.add(nodeValue);

                }
            }
        }


        return retstr;
    }


    public static void main(String[] args) throws Exception {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<folder name=\"c\">" +
                        "<folder name=\"program files\">" +
                        "<folder name=\"uninstall information\" />" +
                        "</folder>" +
                        "<folder name=\"users\" />" +
                        "</folder>";

        Collection<String> names = folderNames(xml, 'u');
        for (String name : names)
            System.out.println(name);
    }
}
