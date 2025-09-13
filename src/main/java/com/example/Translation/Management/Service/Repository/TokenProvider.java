package com.example.Translation.Management.Service.Repository;

import java.util.List;

public interface TokenProvider {
	  String generateToken(String username, List<String> roles);
	    boolean validateToken(String token);
	    String extractUsername(String token);
	    List<String> extractRoles(String token);
}
