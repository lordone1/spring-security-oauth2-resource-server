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
package sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Josh Cummings
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KeycloakAdapterApplicationTests extends AbstractKeycloakITests {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void performWhenProperAuthorizationHeaderThenAllow()
		throws Exception {

		String token = this.getTokenByResourceOwnerGrant("public-client", "isok", "password");

		this.mockMvc.perform(get("/ok")
				.header("Authorization", "Bearer " + token))
				.andExpect(content().string("ok"))
				.andExpect(status().isOk());
	}

	@Test
	public void performWhenProperAuthorizationHeaderThenNoSessionCreated()
			throws Exception {

		String token = this.getTokenByResourceOwnerGrant("public-client", "isok", "password");

		MvcResult result =
				this.mockMvc.perform(get("/ok")
					.header("Authorization", "Bearer " + token))
					.andReturn();

		assertThat(result.getRequest().getSession(false)).isNull();
	}

	@Test
	public void performWhenMissingAuthorizationHeaderThenUnauthorized()
		throws Exception {

		this.mockMvc.perform(get("/ok"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void performWhenInsufficientScopeThenForbidden()
		throws Exception {

		String token = this.getTokenByResourceOwnerGrant("public-client", "isnotok", "password");

		this.mockMvc.perform(get("/ok")
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isForbidden())
				.andExpect(header().string(HttpHeaders.WWW_AUTHENTICATE,
						"Bearer error=\"insufficient_scope\", " +
								"error_description=\"Resource requires any or all of these scopes [ok]\", " +
								"error_uri=\"https://tools.ietf.org/html/rfc6750#section-3.1\", " +
								"scope=\"ok\""));
	}


}
