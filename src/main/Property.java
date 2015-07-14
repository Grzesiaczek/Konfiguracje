package main;

import java.util.ArrayList;

public class Property extends Object {

	public Property(ArrayList<String> data, int line) {
		value = data.get(0);
		this.line = line;
		
		String[] text = value.split(" ");
		name = text[text.length - 1];
		
		for(int i = 1; i < data.size(); i++)
			body.add(new Line(data.get(i), line + i));
	}
	
	@Override
	public String print() {
		String result = printStart() + value + "<br>\n";
		
		for(Object s : body)
			result += s.print();
		
		return result + printEnd();
	}
}