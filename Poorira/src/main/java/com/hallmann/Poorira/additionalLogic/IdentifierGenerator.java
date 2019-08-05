package com.hallmann.Poorira.additionalLogic;

public class IdentifierGenerator {

	public String generateIdentifier(String projectName) {
		StringBuilder identifier = new StringBuilder();
		String[] words = projectName.toUpperCase().split("\\s+");
		if(words.length == 1) {
			if(words[0].length() > 4) {
				identifier.append(words[0].substring(0, 2));
				identifier.append(words[0].substring(words[0].length() - 2, words[0].length()));				
			}
			else
				identifier.append(words[0]);
		} else {
			identifier.append(words[0].substring(0, 2));
			identifier.append(words[words.length - 1].substring(0, 2));
		}
		
		return identifier.toString();
	}
	
}
