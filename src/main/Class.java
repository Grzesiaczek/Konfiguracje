package main;

import java.util.ArrayList;

public class Class extends Object {
	
	ArrayList<Constructor> constructors;
	ArrayList<Method> methods;
	ArrayList<Property> properties;

	public Class(ArrayList<String> data, int line) {
		
		int start = 0;
		int last = 0;
		
		constructors = new ArrayList<Constructor>();
		methods = new ArrayList<Method>();
		properties = new ArrayList<Property>();
		
		value = data.get(0);
		this.line = line;
		
		String[] array = value.trim().split(" ");
		String name = array[array.length - 1];
		
		for(int i = 2; i < data.size(); i++) {
			String s = data.get(i).trim();
			
			if(s.indexOf('{') == 0) {
				start = i - 1;
				
				for(int j = last + 1; j < start; j++)
					body.add(new Line(data.get(j), line + j + 1));
				
				int count = 1;
				boolean end = false;
				
				for(int j = i + 1; j < data.size(); j++) {
					for(int k = 0; k < data.get(j).length(); k++) {
						char c = data.get(j).charAt(k);
						
						if(c == '{')
							count++;
						
						if(c == '}' && --count == 0) {
							end = true;
							break;
						}
					}
					
					last = j;
					
					if(end)
						break;
				}
				
				ArrayList<String> list = new ArrayList<String>();
				
				for(int j = start; j <= last; j++)
					list.add(data.get(j));
				
				String[] cs = null;
				String origin = data.get(start).trim();
				boolean prop = false;
				
				if(origin.contains("(")) {				
					array = origin.split("\\(");
					cs = array[0].split(" ");
				}
				else
					prop = true;
				
				if(prop) {
					Property property = new Property(list, line + i);
					properties.add(property);
					body.add(property);
				}
				else if(cs[cs.length - 1].trim().equals(name)) {
					Constructor constructor = new Constructor(list, line + i);
					constructors.add(constructor);
					body.add(constructor);
				}
				else {
					Method method = new Method(list, line + i);
					methods.add(method);
					body.add(method);
				}
				
				i = last + 1;
			}
		}
		
		for(int i = last + 1; i < data.size(); i++)
			body.add(new Line(data.get(i), line + i));
	}

	@Override
	public String print() {
		String result = printStart() + value + "<br>";
		
		for(int i = 0; i < body.size(); i++)
			result += body.get(i).print();
		
		return result + "<br>";
	}
	
	public ArrayList<Constructor> getConstructors() {
		return constructors;
	}
	
	public ArrayList<Method> getMethods() {
		return methods;
	}
	
	public ArrayList<Property> getProperties() {
		return properties;
	}
}