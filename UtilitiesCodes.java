package sdfcTestNGPackage;

import org.apache.commons.lang3.RandomStringUtils;

public class UtilitiesCodes {
	public void randomProgram() {
	System.out.println("-------Mobile Number ------"); 
    System.out.println(RandomStringUtils.randomNumeric(10)); 

    System.out.println("--------Name -----------"); 
    System.out.println(RandomStringUtils.randomAlphabetic(10)); 

    System.out.println("Email Address"); 
System.out.println(RandomStringUtils.randomAlphanumeric(10)+"@gmail.com");
	
}
}
