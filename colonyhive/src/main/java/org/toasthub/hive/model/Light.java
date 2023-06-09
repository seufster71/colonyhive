package org.toasthub.hive.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class Light implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Long id;
	protected String name;
	protected String code;
	protected Boolean enabled;
	protected Boolean on;
	protected GpioPinDigitalOutput gpio;
	
	// Constructor
	public Light() {
	}
	
	public Light(Long id, String name) {
		this.setId(id);
		this.setName(name);
		this.setCode(name);
		this.setEnabled(true);
		this.setOn(false);
	}
	
	// Methods
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Boolean getOn() {
		return on;
	}
	public void setOn(Boolean on) {
		this.on = on;
	}
	
	@JsonIgnore
	public GpioPinDigitalOutput getGpio() {
		return gpio;
	}
	public void setGpio(GpioPinDigitalOutput gpio) {
		this.gpio = gpio;
	}
	
	
}
