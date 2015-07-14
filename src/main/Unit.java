package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextPane;

public class Unit {
	JTextPane pane;
	ArrayList<Object> data;
	Class content;
	
	@SuppressWarnings("resource")
	public Unit(File file, JTextPane pane) {
		this.pane = pane;
		pane.setContentType("text/html");
		
		data = new ArrayList<Object>();
		ArrayList<String> text = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			text.add(reader.readLine().substring(3));
			
			while(reader.ready())
				text.add(reader.readLine());
			
			scan(text);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		print();
	}
	
	public void print() {
		String text = "<html>";
		//"Text color: <font color='red'>red</font>"
		
		for(int i = 0; i < data.size(); i++)
			text = addLine(text, data.get(i).print());
		
		text = addLine(text, "</html>");
		text.replace(" ", "&nbsp;");

		pane.setText(text);
	}
	
	String addLine(String s1, String s2)
	{
		return s1 + '\n' + s2;
	}
	
	void scan(ArrayList<String> text) {
		int start = 0;
		int finish = 0;
		
		for(int i = 0; i < text.size(); i++) {
			String[] names = text.get(i).trim().split(" ");
			
			for(String s : names)
				if(s.equals("class")) {
					start = i;
					break;
				}
			
			if(start != 0) {
				int count = 0;
				boolean end = false;
				
				for(int j = 0; j < start; j++)
					data.add(new Line(text.get(j), j + 1));
				
				for(int j = 0; j < text.size(); j++) {
					String s = text.get(j).trim();
					
					for(int k = 0; k < s.length(); k++) {
						if(s.charAt(k) == '{')
							count++;
						if(s.charAt(k) == '}' && --count == 0) {
							end = true;
							break;
						}
					}
					
					finish = j;
					
					if(end)
						break;
				}
				
				ArrayList<String> list = new ArrayList<String>();
				
				for(int j = start; j <= finish; j++)
					list.add(text.get(j));
				
				content = new Class(list, start);
				data.add(content);
				
				if(end)
					break;
			}
		}
		
		for(int i = finish + 1; i < text.size(); i++)
			data.add(new Line(text.get(i), i + 1));
	}
	
	public Class getContent() {
		return content;
	}
}