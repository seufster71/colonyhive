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


public class Text extends TextBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String defaultText;
	private Set<LangText> langTexts;
	
	// Constructor
	public Text() {
		super();
	}

	// setter/getter
	public String getDefaultText() {
		return defaultText;
	}
	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}

	public Set<LangText> getLangTexts() {
		return langTexts;
	}
	public void setLangTexts(Set<LangText> langTexts) {
		this.langTexts = langTexts;
	}
	
	public void setLangTexts(Map<String,String> langMap){
		if (this.langTexts == null) {
			this.langTexts = new HashSet<LangText>();
		}
		for (String key : langMap.keySet()) {
			boolean exists = false;
			for (LangText l : this.langTexts){
				if (l.getLang().equals(key)) {
					l.setText(langMap.get(key));
					exists = true;
					break;
				}
			}
			// create new if it does not exist
			if (!exists) {
				LangText text = new LangText();
				text.setTextMeta(this);
				text.setLang(key);
				text.setText(langMap.get(key));
				this.langTexts.add(text);
			}
		}
	}
}
