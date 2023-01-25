package com.moovex.util;

import android.content.Context;

import com.moovex.R;
import com.moovex.driver.utils.DriverCL;
import com.moovex.driver.utils.DriverSystems;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by developer on 21/2/17.
 */
public class ColorRestore {
    public static Context c;

    /**
     * Adding color files to Local hashmap
     */
    public static synchronized void getAndStoreColorValues(String result, Context c) {
        try {
            ColorRestore.c = c;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            Document doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("*");

            DriverSystems.out.println("lislength" + nList.getLength());
            int chhh = 0;
            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    chhh++;
                    Element element2 = (Element) node;
                    if (element2.getAttribute("name").equals("pressBack"))
                        DriverSystems.out.println("size" + chhh + "___" + element2.getTextContent());
                    DriverCL.nfields_byName.put(element2.getAttribute("name"), element2.getTextContent());
                }
            }
            getColorValueDetail();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * Getting Color values from local hash map
     */
    public static synchronized void getColorValueDetail() {
        Field[] fieldss = R.color.class.getDeclaredFields();
        // fields =new int[fieldss.length];
        for (Field field : fieldss) {
            int id = c.getResources().getIdentifier(field.getName(), "color", c.getPackageName());
            if (DriverCL.nfields_byName.containsKey(field.getName())) {
                DriverCL.fields.add(field.getName());
                DriverCL.fields_value.add(c.getResources().getString(id));
                DriverCL.fields_id.put(field.getName(), id);
            } else {
                DriverSystems.out.println("Imissedthepunchrefree" + field.getName());
            }
        }
        for (Map.Entry<String, String> entry : DriverCL.nfields_byName.entrySet()) {
            String h = entry.getKey();
            String value = entry.getValue();
            DriverCL.nfields_byID.put(DriverCL.fields_id.get(h), DriverCL.nfields_byName.get(h));
            // do stuff
        }
    }
}
