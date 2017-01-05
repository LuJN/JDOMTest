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
			// 创建SAXBuilder对象
			SAXBuilder saxBuilder = new SAXBuilder();
		    // 2.创建一个输入流，将xml文件加载到输入流中
			InputStream in = new FileInputStream("books.xml");
			// 3.通过saxBuilder的build方法，将输入流加载到saxBuilder中
			Document document = saxBuilder.build(in);
			// 4.通过document对象获取xml文件的根节点
			Element rootElement = document.getRootElement();
			// 5.获取根节点下的子节点的List集合
			List<Element> bookList = rootElement.getChildren();
			for(Element book : bookList) {
				bookEntity = new Book();
				System.out.println("===========开始解析第" + (bookList.indexOf(book) + 1) + "本书============");
				// 解析book的属性集合
				List<Attribute> attrList = book.getAttributes();
				for(Attribute attr : attrList) {
					String attrName = attr.getName();
					String attrValue = attr.getValue();
					System.out.println("属性名：" + attrName + "----属性值：" + attrValue);
					if(attrName.equals("id")) {
						bookEntity.setId(Integer.valueOf(attrValue));
					}
				}
//				String attrValue = book.getAttributeValue("id");
//				System.out.println("属性名：id" + "----属性值：" + attrValue);
				List<Element> bookChildren = book.getChildren();
				for(Element bookChild : bookChildren) {
					String nodeName = bookChild.getName();
					String nodeValue = bookChild.getValue();
					System.out.println("节点名：" + nodeName + "----节点值：" + nodeValue);
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
				System.out.println("===========结束解析第" + (bookList.indexOf(book) + 1) + "本书============");
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
		title.setText("新闻");
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
