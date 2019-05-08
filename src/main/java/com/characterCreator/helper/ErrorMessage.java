package com.characterCreator.helper;

public class ErrorMessage {
	
	
	public static String getStringInputErrorMessage() {
		return "A character's %s can only contain letters, numbers, and one space";
	}
	
	public static String getLevelInputErrorMessage() {
		return "A character's level must be between 1 and 30";
	}
	
	public static String getStatInputErrorMessage() {
		return "All of a character's stats must be between 1 and 28";
	}
}