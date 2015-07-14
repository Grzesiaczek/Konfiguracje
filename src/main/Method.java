package main;

import java.util.ArrayList;

public class Method extends Object {
	
	public Method(ArrayList<String> data, int line) {
		value = data.get(0);
		this.line = line;
		
		for(int i = 1; i < data.size(); i++)
			body.add(new Line(data.get(i), line + i));
	}
	
	@Override
	public String print() {
		String result = printStart() + value +  "<br>\n";
		
		for(Object s : body)
			result += s.print();
		
		return result + printEnd();
	}
}