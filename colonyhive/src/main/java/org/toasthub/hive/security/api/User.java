/*
 * Copyright (C) 2016 The ToastHub Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.toasthub.hive.security.api;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

import org.toasthub.hive.utils.Request;
import org.toasthub.hive.utils.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String[] columns = {"firstname","middlename","lastname","active","username","created"};
	public static String[] dataTypes = {"string","string","string","boolean","string","date"};
	
	private String username;
	private String password;
	private String verifyPassword;
	private String salt;
	private String firstname;
	private String middlename;
	private String lastname;
	private String email;
	private String zipcode;
	private String challengePhase1;
	private String challengePhaseAnswer1;
	private String challengePhase2;
	private String challengePhaseAnswer2;
	private String challengePhase3;
	private String challengePhaseAnswer3;
	private String alternateEmail;
	private String sessionToken;
	private String emailToken;
	private boolean useToken;
	private boolean emailConfirm;
	private boolean forceReset;
	private String lang;
	private String logLevel;
	private Instant lastPassChange; // last password change this will be use to force password reset after x days
	private Map<String,RolePermission> permissions;
	
	private boolean connected;
	
	// Constructors
	public User() {
	}
	public User(String username, String password, String firstname, String lastname, String middlename, String zipcode, String lang){
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
		this.setLastPassChange(Instant.now());
		this.setUsername(username);
		this.setPassword(password);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setMiddlename(middlename);
		this.setZipcode(zipcode);
		this.setLang(lang);
	}
	// Constructor for list
	public User(Long id, String firstname, String middlename, String lastname, boolean active, String username, Instant created){
		this.setId(id);
		this.setFirstname(firstname);
		this.setMiddlename(middlename);
		this.setLastname(lastname);
		this.setActive(active);
		this.setUsername(username);
		this.setCreated(created);
	}
	// Constructor for ajax
	public User(Request request, Response response, String formName){
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
	}
	// Setters/Getters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getVerifyPassword() {
		return verifyPassword;
	}
	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getChallengePhase1() {
		return challengePhase1;
	}
	public void setChallengePhase1(String challengePhase1) {
		this.challengePhase1 = challengePhase1;
	}

	public String getChallengePhaseAnswer1() {
		return challengePhaseAnswer1;
	}
	public void setChallengePhaseAnswer1(String challengePhaseAnswer1) {
		this.challengePhaseAnswer1 = challengePhaseAnswer1;
	}
	
	public String getChallengePhase2() {
		return challengePhase2;
	}
	public void setChallengePhase2(String challengePhase2) {
		this.challengePhase2 = challengePhase2;
	}
	
	public String getChallengePhaseAnswer2() {
		return challengePhaseAnswer2;
	}
	public void setChallengePhaseAnswer2(String challengePhaseAnswer2) {
		this.challengePhaseAnswer2 = challengePhaseAnswer2;
	}
	
	public String getChallengePhase3() {
		return challengePhase3;
	}
	public void setChallengePhase3(String challengePhase3) {
		this.challengePhase3 = challengePhase3;
	}
	
	public String getChallengePhaseAnswer3() {
		return challengePhaseAnswer3;
	}
	public void setChallengePhaseAnswer3(String challengePhaseAnswer3) {
		this.challengePhaseAnswer3 = challengePhaseAnswer3;
	}
	
	public String getAlternateEmail() {
		return alternateEmail;
	}
	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}
	
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getEmailToken() {
		return emailToken;
	}
	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}
	
	public boolean isUseToken() {
		return useToken;
	}
	public void setUseToken(boolean useToken) {
		this.useToken = useToken;
	}

	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public boolean isEmailConfirm() {
		return emailConfirm;
	}
	public void setEmailConfirm(boolean emailConfirm) {
		this.emailConfirm = emailConfirm;
	}
	
	public boolean isForceReset() {
		return forceReset;
	}
	public void setForceReset(boolean forceReset) {
		this.forceReset = forceReset;
	}
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
	public Instant getLastPassChange() {
		return lastPassChange;
	}
	public void setLastPassChange(Instant lastPassChange) {
		this.lastPassChange = lastPassChange;
	}
	
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public Map<String,RolePermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Map<String,RolePermission> permissions) {
		this.permissions = permissions;
	}
}
