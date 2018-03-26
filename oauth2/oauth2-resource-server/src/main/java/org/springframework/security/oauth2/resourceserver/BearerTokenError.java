/*
 * Copyright 2002-2018 the original author or authors.
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

package org.springframework.security.oauth2.resourceserver;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.util.Assert;

/**
 * A representation of an Bearer Token Error.
 *
 * @author Vedran Pavic
 * @since 5.1
 * @see BearerTokenErrorCodes
 * @see <a href="https://tools.ietf.org/html/rfc6750#section-3" target="_blank">RFC 6750 Section 3: The WWW-Authenticate Response Header Field</a>
 */
public final class BearerTokenError extends OAuth2Error {

	private final HttpStatus httpStatus;

	private final String scope;

	/**
	 * Create a {@code BearerTokenError} using the provided parameters.
	 * @param errorCode the error code
	 * @param httpStatus the HTTP status
	 */
	public BearerTokenError(String errorCode, HttpStatus httpStatus) {
		this(errorCode, httpStatus, null, null, null);
	}

	/**
	 * Create a {@code BearerTokenError} using the provided parameters.
	 * @param errorCode the error code
	 * @param httpStatus the HTTP status
	 * @param description the description
	 * @param uri the URI
	 * @param scope the scope
	 */
	public BearerTokenError(String errorCode, HttpStatus httpStatus, String description, String uri, String scope) {
		super(errorCode, description, uri);
		Assert.notNull(httpStatus, "httpStatus must not be null");
		this.httpStatus = httpStatus;
		this.scope = scope;
	}

	/**
	 * Return the HTTP status.
	 * @return the HTTP status
	 */
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	/**
	 * Return the scope.
	 * @return the scope
	 */
	public String getScope() {
		return this.scope;
	}

}