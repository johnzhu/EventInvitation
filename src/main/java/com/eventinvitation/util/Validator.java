package com.eventinvitation.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.eventinvitation.domain.Address;

public class Validator {
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static Pattern pattern;
	private static Matcher matcher;
	
	static{
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public static boolean isValidEmail(String email){
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static boolean isValidPassword(String password){
		if(password == null || password.equals("") || password.length() < 8)
			return false;
		else
			return true;
	}

	public static boolean isValidAddress(Address address) {
		if(address.getCountry() != null && !address.getCountry().equals("")){
			return true;
		}
		if(address.getState() != null && !address.getState().equals("")){
			return true;
		}
		if(address.getStreet() != null && !address.getStreet().equals("")){
			return true;
		}
		return false;
	}
	
}
