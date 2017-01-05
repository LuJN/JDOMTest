import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.w3c.dom.stylesheets.LinkStyle;

public class JDOM {
	public void parseXml() {
		List<Book> bookEntityList = new ArrayList<>();
		Book bookEntity = null;
		try {
			// ����SAXBuilder����
			SAXBuilder saxBuilder = new SAXBuilder();
		    // 2.����һ������������xml�ļ����ص���������
			InputStream in = new FileInputStream("books.xml");
			// 3.ͨ��saxBuilder��build�����������������ص�saxBuilder��
			Document document = saxBuilder.build(in);
			// 4.ͨ��document�����ȡxml�ļ��ĸ��ڵ�
			Element rootElement = document.getRootElement();
			// 5.��ȡ���ڵ��µ��ӽڵ��List����
			List<Element> bookList = rootElement.getChildren();
			for(Element book : bookList) {
				bookEntity = new Book();
				System.out.println("===========��ʼ������" + (bookList.indexOf(book) + 1) + "����============");
				// ����book�����Լ���
				List<Attribute> attrList = book.getAttributes();
				for(Attribute attr : attrList) {
					String attrName = attr.getName();
					String attrValue = attr.getValue();
					System.out.println("��������" + attrName + "----����ֵ��" + attrValue);
					if(attrName.equals("id")) {
						bookEntity.setId(Integer.valueOf(attrValue));
					}
				}
//				String attrValue = book.getAttributeValue("id");
//				System.out.println("��������id" + "----����ֵ��" + attrValue);
				List<Element> bookChildren = book.getChildren();
				for(Element bookChild : bookChildren) {
					String nodeName = bookChild.getName();
					String nodeValue = bookChild.getValue();
					System.out.println("�ڵ�����" + nodeName + "----�ڵ�ֵ��" + nodeValue);
					if(nodeName.equals("name")) {
						bookEntity.setName(nodeValue);
					} else if(nodeName.equals("author")) {
						bookEntity.setAuthor(nodeValue);
					} else if(nodeName.equals("year")) {
						bookEntity.setYear(nodeValue);
					} else if(nodeName.equals("price")) {
						bookEntity.setPrice(nodeValue);
					} else if(nodeName.equals("language")) {
						bookEntity.setLanguage(nodeValue);
					}
				}
				System.out.println("===========����������" + (bookList.indexOf(book) + 1) + "����============");
				bookEntityList.add(bookEntity);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bookEntityList);
	}
	
	public void createXml() {
		Element rss = new Element("rss");
		rss.setAttribute("version", "2.0");
		Document doc = new Document(rss);
		Element channel = new Element("channel");
		rss.addContent(channel);
		Element title = new Element("title");
		title.setText("����");
		channel.addContent(title);
		Format format = Format.getCompactFormat();
		format.setIndent("");
		format.setEncoding("utf-8");
		XMLOutputter outputter = new XMLOutputter(format);
		try {
			outputter.output(doc, new FileOutputStream(new File("rss.xml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		JDOM jdom = new JDOM();
		jdom.parseXml();
		jdom.createXml();
		
	}
}
