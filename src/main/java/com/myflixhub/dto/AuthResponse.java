package com.myflixhub.dto;

public class AuthResponse {

    private String token;
    private String tokenType;
    private String email;
    private String fullName;
    private String role;

    public AuthResponse() {}

    public AuthResponse(String token, String tokenType, String email, String fullName, String role) {
        this.token = token;
        this.tokenType = tokenType;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    // ─── Getters ────────────────────────────────────────────────────
    public String getToken()     { return token; }
    public String getTokenType() { return tokenType; }
    public String getEmail()     { return email; }
    public String getFullName()  { return fullName; }
    public String getRole()      { return role; }

    // ─── Setters ────────────────────────────────────────────────────
    public void setToken(String token)         { this.token = token; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public void setEmail(String email)         { this.email = email; }
    public void setFullName(String fullName)   { this.fullName = fullName; }
    public void setRole(String role)           { this.role = role; }

    // ─── Builder ────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String token;
        private String tokenType;
        private String email;
        private String fullName;
        private String role;

        public Builder token(String token)         { this.token = token; return this; }
        public Builder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
        public Builder email(String email)         { this.email = email; return this; }
        public Builder fullName(String fullName)   { this.fullName = fullName; return this; }
        public Builder role(String role)           { this.role = role; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, tokenType, email, fullName, role);
        }
    }
}
