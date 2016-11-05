package pack;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FirstSaxExample {
	public static void main(String[] args) {
		try {
			SAXParserFactory fact = SAXParserFactory.newInstance();
			SAXParser pars = fact.newSAXParser();
			DefaultHandler dh = new DefaultHandler(){
				private boolean insideNemzetiseg;
				private boolean insideCsapatErtek;
				private int angolCsapatertek = 0;
				private String aktualisCsapatNemzetisege;

				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					if(qName.equals("nemzetiseg")){
						insideNemzetiseg = true;
					}else if(qName.equals("csapatertek")){
						insideCsapatErtek = true;
					}
				}
				
				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if(qName.equals("nemzetiseg")){
						insideNemzetiseg = false;
					}else if(qName.equals("csapatertek")){
						insideCsapatErtek = false;
					}
				}
				
				@Override
				public void characters(char[] ch, int start, int length) throws SAXException {
					StringBuilder sb = new StringBuilder();
					
					for(int i=start;i<start + length;i++){
						sb.append(ch[i]);
					}
					
					if(insideNemzetiseg){
						aktualisCsapatNemzetisege = sb.toString();
					}
					
					if(insideCsapatErtek){
						if(aktualisCsapatNemzetisege.equals("Angol")){
							angolCsapatertek += Integer.parseInt(sb.toString());
						}
					}
				}
				
				@Override
				public void endDocument() throws SAXException {
					System.out.println("Össz angol csapatérték:  " + angolCsapatertek);
				}
			};
			pars.parse("focicsapatok.xml", dh);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
