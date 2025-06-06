package com.budgettracker.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    public JwtResponse(String token2, String string, Long id2, String username2, String email2) {
		// TODO Auto-generated constructor stub
	}
	private String token, type = "Bearer";
    private Long id;
    private String username;
    private String email;
}
