package dev.hudsonprojects.backend;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;

public class Experiments {

	public static void main(String[] args) {
		System.out.println(Instant.ofEpochSecond(1733601079).atZone(ZoneId.systemDefault()).toLocalDateTime());
	}

}
