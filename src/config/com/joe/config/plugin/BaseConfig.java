/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.joe.config.plugin;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.configuration.Configuration;

import com.joe.core.version.Named;

public abstract class BaseConfig extends AbstractConfig implements Cloneable {

	private String name;
	private String version;
	private Configuration conf;
	private Map store = new LinkedMap();

	protected void setPropertyDirect(String key, Object value) {
		store.put(key, value);
	}

	public Object getProperty(String key) {
		return store.get(key);
	}

	public boolean isEmpty() {
		return store.isEmpty();
	}

	public boolean containsKey(String key) {
		return store.containsKey(key);
	}

	protected void clearPropertyDirect(String key) {
		if (containsKey(key)) {
			store.remove(key);
		}
	}
	
	public void remove(String key){
		this.clearPropertyDirect(key);
	}

	public void clear() {
		fireEvent(EVENT_CLEAR, null, null, true);
		store.clear();
		fireEvent(EVENT_CLEAR, null, null, false);
	}

	public Iterator getKeys() {
		return store.keySet().iterator();
	}

	public Object clone() {
		try {
			BaseConfig copy = (BaseConfig) super.clone();
			copy.store = (Map) ConfigUtils.clone(store);
			return copy;
		} catch (CloneNotSupportedException cex) {
			throw new RuntimeException(cex);
		}
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getUniqueKey() {
		return Named.getKey(getName(), getVersion());
	}

	@Override
	public String getUniqueKey(String split) {
		return Named.getKey(getName(), getVersion(), split);
	}
}
