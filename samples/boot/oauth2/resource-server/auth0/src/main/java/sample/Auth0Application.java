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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.resourceserver.ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

@SpringBootApplication
public class Auth0Application {
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		PublicKey verify;

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			resourceServer(http)
					.jwt(jwtDecoder());
		}

		private ResourceServerConfigurer<HttpSecurity> resourceServer(HttpSecurity http) throws Exception {
			return http.apply(new ResourceServerConfigurer<>());
		}

		@Bean
		JwtDecoder jwtDecoder() {
			JWTVerifier verifier = JWT.require(Algorithm.RSA256((RSAPublicKey) this.verify, null))
					.withIssuer("rob").build();

			return new Auth0JwtDecoder(verifier);
		}
	}



	public static void main(String[] args) {
		SpringApplication.run(Auth0Application.class, args);
	}
}