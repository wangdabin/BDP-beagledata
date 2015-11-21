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
package com.joe.config.plugin.event;

public class ConfigErrorEvent extends ConfigEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable cause;

	/**
	 * 
	 * @param source
	 * @param type
	 * @param propertyName
	 * @param propertyValue
	 * @param cause
	 */
	public ConfigErrorEvent(Object source, int type, String propertyName,
			Object propertyValue, Throwable cause) {
		super(source, type, propertyName, propertyValue, true);
		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}
}
