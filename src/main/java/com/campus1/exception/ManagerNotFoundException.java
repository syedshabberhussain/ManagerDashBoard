package com.campus1.exception;


public class ManagerNotFoundException extends RuntimeException{
	
	public ManagerNotFoundException(String message){
		super(message);
	}
}
