package main;

import java.util.ArrayList;

public abstract class Object {
	public enum Status {Normal, Edited, Added, Removed};
	
	protected ArrayList<String> modifiers = new ArrayList<String>();
	protected ArrayList<Object> body = new ArrayList<Object>();
	
	protected String value;
	protected String name;
	
	protected int line;
	protected int tab;
	
	protected Status status = Status.Normal;
	
	public abstract String print();
	
	protected String printModifiers() {
		String result = "";
		
		for(int i = 0; i < tab; i++)
			result += "\t";
		
		for(int i = 0; i < modifiers.size(); i++)
			result += modifiers.get(i) + " ";
		
		return result;
	}
	
	protected String printStart() {
		
		String number = "<span style=\"background-color:#eeeaec\">" + line + ".&nbsp;</span>";
		
		switch(status) {
		case Normal:
			return "<div style=\"background-color:white\">" + number;
		case Added:
			return "<div style=\"background-color:#60f060\">" + number;
		case Removed:
			return "<div style=\"background-color:#f06060\">" + number;
		case Edited:
			return "<div style=\"background-color:#f0b020\">" + number;
		default:
			break;
		}
		
		return "<div>" + number;
	}
	
	protected String printEnd() {
		return "</div>";
	}
	
	protected String printChildren() {
		String result = "";
		
		for(int i = 0; i < body.size(); i++)
			result += body.get(i).print();
		
		return result;
	}
	
	protected String join(String s1, String s2) {
		return s1 + " " + s2;
	}
	
	public int getLine() {
		return line;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public int getHash() {
		int result = 0;
		
		for(Object line : body)
			result += line.getHash();
		
		return result;
	}
	
	public void setStatus(Status status) {
		this.status = status;
		
		for(Object data : body)
			data.setStatus(status);
	}
}