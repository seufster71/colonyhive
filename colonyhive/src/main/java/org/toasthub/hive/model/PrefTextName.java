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

package org.toasthub.hive.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PrefTextName extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private PrefName prefName;
	private String name;
	private Text title;
	private String optionalParams;
	private Set<PrefTextValue> values;
	
	// Constructor
	public PrefTextName() {
		super();
	}
	
	public PrefTextName (PrefName prefName, String name, Text title) {
		super();
		this.setPrefName(prefName);
		this.setName(name);
		this.setTitle(title);
	}
	
	// Setters/Getters
	@JsonIgnore
	public PrefName getPrefName() {
		return prefName;
	}
	public void setPrefName(PrefName prefName) {
		this.prefName = prefName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Text getTitle() {
		return title;
	}
	public void setTitle(Text title) {
		this.title = title;
	}
	
	public String getOptionalParams() {
		return optionalParams;
	}
	public void setOptionalParams(String optionalParams) {
		this.optionalParams = optionalParams;
	}

	public Set<PrefTextValue> getValues() {
		return values;
	}
	public void setValues(Set<PrefTextValue> values) {
		this.values = values;
	}

	public void setTitleDefaultText(String defaultText){
		if (this.title != null) {
			this.title.setDefaultText(defaultText);
		} else {
			Text text = new Text();
			text.setDefaultText(defaultText);
			this.setTitle(text);
		}
	}

	public void setTitleMtext(Map<String,String> langMap){
		if (this.title != null) {
			this.title.setLangTexts(langMap);
		} else {
			Text text = new Text();
			text.setLangTexts(langMap);
			this.setTitle(text);
		}
	}

	public void addToValues(Object value) {
		PrefTextValue val = (PrefTextValue) value;
		val.setPrefTextName(this);
		if (values == null) {
			values = new HashSet<PrefTextValue>();
			values.add(val);
		} else {
			boolean exists = false;
			for (PrefTextValue v : values) {
				if (v.getLang().equals(val.getLang())) {
					v.setValue(val.getValue());
					v.setRendered(val.getRendered());
					exists = true;
					break;
				}
			}
			if (exists == false) {
				values.add(val);
			}
		}
	}
}
