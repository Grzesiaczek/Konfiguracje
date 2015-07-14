package main;

public class Line extends Object {
	
	public Line(String value, int line) {
		this.value = value;
		this.line = line;
	}

	@Override
	public String print() {
		return printStart() + value + "</div>\n";
	}

	@Override
	public int getHash() {
		int hash = 0;
		
		if(value.trim().length() > 0)
			hash = (int)value.trim().charAt(0);
		
		return value.trim().length() + hash;
	}
}
