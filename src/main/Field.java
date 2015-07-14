package main;

public class Field extends Object {

	@Override
	public String print() {
		return printModifiers() + value + ";";
	}
}