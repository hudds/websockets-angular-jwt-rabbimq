package dev.hudsonprojects.backend.common.lib;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dev.hudsonprojects.backend.common.lib.util.StringUtils;

public class PersonUsernameGenerator {

	
	private final String personName;
	
	private boolean generateWithTimestamps;


	public PersonUsernameGenerator(String personName) {
		this.personName = StringUtils.stripAccents(personName).toLowerCase();
	}
	
	public PersonUsernameGenerator generatingWithTimestamps() {
		generateWithTimestamps = true;
		return this;
	}
	
	public PersonUsernameGenerator generatingWithoutTimestamps() {
		generateWithTimestamps = false;
		return this;
	}

	
	public Set<String> getUsernames(){
		Set<String> usernames = new LinkedHashSet<>();
		var personNameParts = personName.split(" ");
		usernames.add(personNameParts[0]);
		List<String> dividers = List.of(".", "-", "_");
		for (String divider : dividers) {
			getFirstLastName(personNameParts, divider).ifPresent(usernames::add);
			getFirstMiddleNameLastName(personNameParts, divider).ifPresent(usernames::add);
			getFirstMiddleNameAbreviatedLastName(personNameParts, divider).ifPresent(usernames::add);
			usernames.addAll(getMiddleNamesCombinations(personNameParts, divider));
		}
		
		if(generateWithTimestamps) {
			usernames.addAll(getWithTimestamps(usernames));
		}
		return usernames;
	}
	
	
	private Set<String> getWithTimestamps(Collection<String> usernames){
		Set<String> withTimestamps = new LinkedHashSet<>();
		String uuid = UUID.randomUUID().toString();
		for(var username : usernames) {
			LocalDateTime now = LocalDateTime.now();
			Integer year = now.getYear()%100;
			Integer month = now.getMonth().getValue();
			Integer day = now.getDayOfMonth();
			Integer hour = now.getHour();
			Integer minute = now.getMinute();
			Integer second = now.getSecond();
			Long nanoTime = System.nanoTime() % 1000;
			Character monthChar = now.getMonth().toString().toLowerCase().charAt(0);
			Character dayOfWeekChar = now.getDayOfWeek().toString().toLowerCase().charAt(0);

			String nameWithTimestamp = username + year.toString(); 
			withTimestamps.add(nameWithTimestamp);
			nameWithTimestamp += month;
			withTimestamps.add(nameWithTimestamp);
			nameWithTimestamp += day;
			withTimestamps.add(nameWithTimestamp);
			nameWithTimestamp += hour;
			withTimestamps.add(nameWithTimestamp);
			nameWithTimestamp += minute;
			withTimestamps.add(nameWithTimestamp);
			nameWithTimestamp += second;
			withTimestamps.add(nameWithTimestamp);
			
			withTimestamps.add(username + nanoTime + monthChar);
			withTimestamps.add(username + nanoTime + monthChar + dayOfWeekChar);
			
			withTimestamps.add(username + "-" + now.getMonth().toString().toLowerCase());
			withTimestamps.add(username + "-" + now.getDayOfWeek().toString().toLowerCase());
			withTimestamps.add(username + "-" + now.getMonth().toString().toLowerCase() +"-"+now.getDayOfWeek().toString().toLowerCase());
			
			
			withTimestamps.add(username + "-" + uuid);
		}
		
		return withTimestamps;
	}
	
	private Optional<String> getFirstLastName(String[] nameParts, String divider) {
		if(nameParts.length > 1) {
			return Optional.of(nameParts[0]+divider+nameParts[nameParts.length-1]);
		}
		return Optional.empty();
	}
	
	private Optional<String> getFirstMiddleNameLastName(String[] nameParts, String divider) {
		if(nameParts.length > 2) {
			int middle = nameParts.length / 2;
			return Optional.of(String.join(divider, nameParts[0], nameParts[middle], nameParts[nameParts.length-1]));
		}
		return Optional.empty();
	}
	
	
	private Optional<String> getFirstMiddleNameAbreviatedLastName(String[] nameParts, String divider) {
		if(nameParts.length > 2) {
			int middle = nameParts.length / 2;
			return Optional.of(String.join(divider, nameParts[0], String.valueOf(nameParts[middle].charAt(0)), nameParts[nameParts.length-1]));
		}
		return Optional.empty();
	}
	
	private Optional<String> getFirstAllMiddleNamesAbreviatedLastName(String[] nameParts, String divider) {
		if(nameParts.length > 2) {
			var firstName = nameParts[0];
			var lastName = nameParts[nameParts.length-1];
			var middleNames =  Stream.of(Arrays.copyOfRange(nameParts, 1, nameParts.length-1))
					.map(name -> String.valueOf(name.charAt(0)))
					.collect(Collectors.joining(divider));
			return Optional.of(String.join(divider, firstName, middleNames, lastName));
		}
		return Optional.empty();
	}
	
	
	private List<String> getMiddleNamesCombinations(String[] nameParts, String divider) {
		if(nameParts.length > 2) {
			var firstName = nameParts[0];
			var lastName = nameParts[nameParts.length-1];
			var middleNames =  Arrays.asList(Arrays.copyOfRange(nameParts, 1, nameParts.length-1));
			var names =  new ArrayList<String>();
			for(var middleName : middleNames) {
				names.add(String.join(divider, firstName, middleName, lastName));
			}
			for(var middleName : middleNames) {
				names.add(String.join(divider, firstName, String.valueOf(middleName.charAt(0)), lastName));
			}
			return names;
		}
		return new ArrayList<>();
	}
	
	private Optional<String> getAllNames(String[] nameParts, String divider) {
		return Optional.of(String.join(divider, nameParts));
	}

}
