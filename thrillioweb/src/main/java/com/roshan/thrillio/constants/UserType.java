package com.roshan.thrillio.constants;

public enum UserType {
	USER("user"),
	EDITOR("cheifeditor"),
	CHEIF_EDITOR("cheifeditor");
	private UserType(String name){
		this.name=name;
	}
    private String name;
    public String getName(){
    	return name;
    }
}
