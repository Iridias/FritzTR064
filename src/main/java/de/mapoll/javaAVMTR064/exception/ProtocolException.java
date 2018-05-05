/*
 * *********************************************************************************************************************
 *
 * javaAVMTR064 - open source Java TR-064 API
 *===========================================
 *
 * Copyright 2018 Iridias (https://github.com/Iridias)
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 **********************************************************************************************************************
 */
package de.mapoll.javaAVMTR064.exception;

public class ProtocolException extends Exception {
	
	private String errorCode;
	
	public ProtocolException(final String message) {
		super(message);
	}
	
	public ProtocolException(final String message, final String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ProtocolException(final Throwable cause) {
		super(cause);
	}
	
	public ProtocolException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public ProtocolException(final String message, final String errorCode, final Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
}
