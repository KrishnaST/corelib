package org.kst.corelib.iso8583;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class ISOFormatReader{
	
	private static HashMap<String, ISOFormat> isoFormats = new HashMap<String, ISOFormat>();
	private static JAXBContext jaxbContext;
	private static Unmarshaller jaxbUnmarshaller;
	
	static
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(XMLFormat.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static ISOFormat readFormat(String isoformat)
	{
		ISOFormat format = isoFormats.get(isoformat);
		if(format == null)
		{
			try
			{
				//File file = new File(ISOFormatReader.class.getResource("/").getPath()+"ini/"+isoformat+".xml");
				File file = new File(ISOFormatReader.class.getResource("/").getPath()+isoformat+".xml");
				System.out.println("Path : "+file.getAbsolutePath());
				XMLFormat xmlformat = (XMLFormat) jaxbUnmarshaller.unmarshal(file);
				if(xmlformat != null)
				{
					Field[] fields 	= xmlformat.getFields();
					int[] length 	= new int[129];
					String[] type 	= new String[129];
					String[] name 	= new String[129];
					
					Arrays.fill(length, 0);
					Arrays.fill(type, "");
					Arrays.fill(name, "");
					
					for(Field field : fields)
					{
						length[field.getId()] 	= field.getLength();
						type[field.getId()] 	= field.getType();
						name[field.getId()] 	= field.getName();
					}
					
					format = new ISOFormat(length, type, name);
					isoFormats.put(isoformat, format);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return format;
	}
	
	@XmlRootElement(name="fields")
	public static class XMLFormat {
		private Field[] fields = new Field[129];

		public Field[] getFields() {
			return fields;
		}

		@XmlElement(name="field")
		public void setFields(Field[] fields) {
			this.fields = fields;
		}


		@Override
		public String toString() {
			return "XMLFormat [fields=" + Arrays.toString(fields) + "]";
		}		
	}
	
	
	public static void main(String[] args) {
		readFormat("iso87");
		readFormat("iso87");
		readFormat("iso87");
	}
}
