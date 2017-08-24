package org.kst.corelib.iso8583;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="field")
@XmlType (propOrder={"id","length","type","name"})
public class Field {
	private int id;
	private int length;
	private String type;
	private String name;
	
	public int getId() {
		return id;
	}
	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}
	
	public int getLength() {
		return length;
	}
	@XmlAttribute
	public void setLength(int length) {
		this.length = length;
	}
	
	public String getType() {
		return type;
	}	
	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getName() {
		return name;
	}
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "Field [id=" + id + ", length=" + length + ", type=" + type + ", name=" + name + "]";
	}
	
	
}
