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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BearerTokenError}.
 *
 * @author Vedran Pavic
 */
public class BearerTokenErrorTests {

	private static final String TEST_ERROR_CODE = "test-code";

	private static final HttpStatus TEST_HTTP_STATUS = HttpStatus.UNAUTHORIZED;

	private static final String TEST_DESCRIPTION = "test-description";

	private static final String TEST_URI = "http://example.com";

	private static final String TEST_SCOPE = "test-scope";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void constructorWithErrorCodeWhenErrorCodeIsValidThenCreated() {
		BearerTokenError error = new BearerTokenError(TEST_ERROR_CODE, TEST_HTTP_STATUS);

		assertThat(error.getErrorCode()).isEqualTo(TEST_ERROR_CODE);
		assertThat(error.getHttpStatus()).isEqualTo(TEST_HTTP_STATUS);
		assertThat(error.getDescription()).isNull();
		assertThat(error.getUri()).isNull();
		assertThat(error.getScope()).isNull();
	}

	@Test
	public void constructorWithErrorCodeAndHttpStatusWhenErrorCodeIsNullThenThrowIllegalArgumentException() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("errorCode cannot be empty");

		new BearerTokenError(null, TEST_HTTP_STATUS);
	}

	@Test
	public void constructorWithErrorCodeAndHttpStatusWhenErrorCodeIsEmptyThenThrowIllegalArgumentException() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("errorCode cannot be empty");

		new BearerTokenError("", TEST_HTTP_STATUS);
	}

	@Test
	public void constructorWithErrorCodeAndHttpStatusWhenHttpStatusIsNullThenThrowIllegalArgumentException() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("httpStatus must not be null");

		new BearerTokenError(TEST_ERROR_CODE, null);
	}

	@Test
	public void constructorWithAllParametersWhenAllParametersAreValidThenCreated() {
		BearerTokenError error = new BearerTokenError(TEST_ERROR_CODE, TEST_HTTP_STATUS, TEST_DESCRIPTION, TEST_URI,
				TEST_SCOPE);

		assertThat(error.getErrorCode()).isEqualTo(TEST_ERROR_CODE);
		assertThat(error.getHttpStatus()).isEqualTo(TEST_HTTP_STATUS);
		assertThat(error.getDescription()).isEqualTo(TEST_DESCRIPTION);
		assertThat(error.getUri()).isEqualTo(TEST_URI);
		assertThat(error.getScope()).isEqualTo(TEST_SCOPE);
	}

	@Test
	public void constructorWithAllParametersWhenErrorCodeIsNullThenThrowIllegalArgumentException() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("errorCode cannot be empty");

		new BearerTokenError(null, TEST_HTTP_STATUS, TEST_DESCRIPTION, TEST_URI, TEST_SCOPE);
	}

	@Test
	public void constructorWithAllParametersWhenErrorCodeIsEmptyThenThrowIllegalArgumentException() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("errorCode cannot be empty");

		new BearerTokenError("", TEST_HTTP_STATUS, TEST_DESCRIPTION, TEST_URI, TEST_SCOPE);
	}

	@Test
	public void constructorWithAllParametersWhenHttpStatusIsNullThenThrowIllegalArgumentException() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("httpStatus must not be null");

		new BearerTokenError(TEST_ERROR_CODE, null, TEST_DESCRIPTION, TEST_URI, TEST_SCOPE);
	}

}