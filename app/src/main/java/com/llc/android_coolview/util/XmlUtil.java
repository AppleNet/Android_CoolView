package com.llc.android_coolview.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.llc.android_coolview.kobe.bean.TrainCode;
import com.thoughtworks.xstream.XStream;

import android.util.Log;
import android.util.Xml;
/**
 * Xml解析有三种
 *  1.pull解析
 *  2.sax解析
 *  3.DOM解析
 * */
public class XmlUtil {

	/**
	 *  Pull解析
	 *
	 * */
	public static List<Map<Object,Object>> xmlPullParse(InputStream stream){
		int count=1;
		List<Map<Object,Object>> list=null;
		HashMap<Object, Object> map = null;
		if(stream!=null){
			try {
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(stream, "UTF-8");
				int eventType = xpp.getEventType();
				String tagName = "";
				String value = "";
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_DOCUMENT:
							list = new ArrayList<Map<Object,Object>>();
							break;
						case XmlPullParser.START_TAG:
							tagName = xpp.getName();
							if ("TimeTable".equals(tagName)) {
								map = new HashMap<Object, Object>();
								map.put("Count",count++);
							}
							if ("TrainCode".equals(tagName)) {
								value = xpp.nextText();
								String values = value.split(" ")[0];
								map.put("TrainCode", values);
							}
							if ("FirstStation".equals(tagName)) {
								value = xpp.nextText();
								map.put("FirstStation", value);
							}
							if ("LastStation".equals(tagName)) {
								value = xpp.nextText();
								map.put("LastStation", value);
							}
							if ("StartStaion".equals(tagName)) {
								value = xpp.nextText();
								map.put("StartStation", value);
							}
							if ("StartTime".equals(tagName)) {
								value = xpp.nextText();
								map.put("StartTime", value);
							}
							if ("ArriveStaion".equals(tagName)) {
								value = xpp.nextText();
								map.put("ArriveStaion", value);
							}
							if ("ArriveTime".equals(tagName)) {
								value = xpp.nextText();
								map.put("ArriveTime", value);
							}
							if ("KM".equals(tagName)) {
								value = xpp.nextText();
								map.put("KM", value);
							}
							if ("UseDate".equals(tagName)) {
								value = xpp.nextText();
								map.put("UseDate", value);
							}
							break;
						case XmlPullParser.END_TAG:
							tagName = xpp.getName();
							if ("TimeTable".equals(tagName)) {
								list.add(map);
							}
							break;
						case XmlPullParser.END_DOCUMENT:

							break;
					}
					eventType = xpp.next();
				}
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * SAX解析
	 *
	 * */
	public static List<Map<Object,Object>> xmlSaxParse(InputStream stream){
		//创建事件处理器
		SaxHandler saxHandler=new SaxHandler();
		//创建解析器的工厂对象
		SAXParserFactory factory=SAXParserFactory.newInstance();
		try {
			SAXParser saxparser=factory.newSAXParser();
			saxparser.parse(stream, saxHandler);
			List<Map<Object,Object>> list=saxHandler.getList();
			return list;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * DOM解析
	 *
	 * */
	public static List<Map<Object,Object>> xmlDomParser(InputStream stream){

		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String,TrainCode>> xmlJarParser(InputStream stream){
		XStream xStream=new XStream();
		List<Map<String,TrainCode>> list= (List<Map<String, TrainCode>>) xStream.fromXML(stream);
		return list;
	}
}

class SaxHandler extends DefaultHandler{

	private List<Map<Object,Object>> list;
	private Map<Object,Object> map;
	private String perTag;
	private int count=1;
	public SaxHandler() {
		super();
	}

	public List<Map<Object, Object>> getList() {
		return list;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		list=new ArrayList<Map<Object,Object>>();
		map=new HashMap<Object, Object>();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if("TimeTable".equals(localName)){
			for(int i=0;i<attributes.getLength();i++){

			}
			map.put("Count", count++);
		}
		perTag=localName;

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("TimeTable".equals(localName)){
			list.add(map);
		}
		perTag=null;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		String data=new String(ch,start,length);
		if(!"".equals(data.trim())){
			Log.d("XmlUtil--SaxPull", "content: "+data.trim());
		}
		if ("TrainCode".equals(perTag)) {
			map.put("TrainCode", data);
		}
		if ("FirstStation".equals(perTag)) {
			map.put("FirstStation", data);
		}
		if ("LastStation".equals(perTag)) {
			map.put("LastStation", data);
		}
		if ("StartStation".equals(perTag)) {
			map.put("StartStation", data);
		}
		if ("ArriveStation".equals(perTag)) {
			map.put("ArriveStation", data);
		}
		if ("StartTime".equals(perTag)) {
			map.put("StartTime", data);
		}
		if ("ArriveTime".equals(perTag)) {
			map.put("ArriveTime", data);
		}
		if ("KM".equals(perTag)) {
			map.put("KM", data);
		}
		if ("UseDate".equals(perTag)) {
			map.put("UseDate", data);
		}
	}
}
