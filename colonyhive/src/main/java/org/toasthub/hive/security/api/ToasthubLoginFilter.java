package org.toasthub.hive.security.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.toasthub.hive.utils.Request;
import org.toasthub.hive.utils.Response;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;


import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ToasthubLoginFilter extends AbstractAuthenticationProcessingFilter {

	private RememberMeServices rememberMeServices = new NullRememberMeServices();
	
	public static final String TOASTHUB_SECURITY_FORM_USERNAME_KEY = "LOGIN_FORM_USERNAME";
	public static final String TOASTHUB_SECURITY_FORM_PASSWORD_KEY = "LOGIN_FORM_PASSWORD";

	private String usernameParameter = TOASTHUB_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = TOASTHUB_SECURITY_FORM_PASSWORD_KEY;
	private boolean postOnly = true;
	
	protected UserManagerSvc userManagerSvc;
	
	public ToasthubLoginFilter() {
		super(new AntPathRequestMatcher("/api/login/authenticate", "POST"));
	}

	public ToasthubLoginFilter(UserManagerSvc userManagerSvc) {
		super(new AntPathRequestMatcher("/api/login/authenticate", "POST"));
		this.userManagerSvc = userManagerSvc;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}
		//String tenant = TenantContext.getURLDomain();
		Authentication authResult = null;
		Response restResponse = new Response();
		try {
			if (postOnly && !request.getMethod().equals("POST")) {
				throw new AuthenticationServiceException(
						"Authentication method not supported: " + request.getMethod());
			}
			
			ObjectMapper mapper = new ObjectMapper();
			Request input = mapper.readValue(request.getInputStream(), Request.class);

			if (input.containsParam("action") && "LOGINAUTHENTICATE".equals(input.getParam("action"))) {
				
				if (input.containsParam("inputFields")) {
					// get user from db
					userManagerSvc.authenticate(input,restResponse);
					User user = (User) restResponse.getParam("user");
					if (user != null) {
						authResult = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), (List<GrantedAuthority>) restResponse.getParam("authorities"));
						restResponse.getParams().remove("user");
						restResponse.getParams().remove("authorities");
					}
				} else {
					authResult = new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());
				}
			} else {
				authResult = null;
			}

			//authResult = attemptAuthentication(request, response);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed
				// authentication
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				ObjectMapper mapperOut = new ObjectMapper();
				mapperOut.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
				//mapperOut.writerWithView(View.Member.class);
				//mapperOut.writerWithView(View.Admin.class);
				out.println(mapperOut.writeValueAsString(restResponse));
				out.close();
				return;
			}
			
		} catch (InternalAuthenticationServiceException failed) {
			logger.error( "An internal error occurred while trying to authenticate the user.", failed);
			unsuccessfulAuthentication(request, response, failed);

			return;
		} catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);

		rememberMeServices.loginSuccess(request, response, authResult);

		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
					authResult, this.getClass()));
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ObjectMapper mapperOut = new ObjectMapper();
		mapperOut.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		//mapperOut.writerWithView(View.Member.class);
		//mapperOut.writerWithView(View.Admin.class);
		out.println(mapperOut.writeValueAsString(restResponse));
		out.close();
		
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		
		return null;
	}

	
	protected String obtainPassword(HttpServletRequest request) {
		
		return request.getParameter(passwordParameter);
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}
	
	protected void setDetails(Response restResponse, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(restResponse);
	}
	
	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}
	
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}
	
}
