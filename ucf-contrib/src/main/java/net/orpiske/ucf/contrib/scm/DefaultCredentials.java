/**
 Copyright 2013 Otavio Rodolfo Piske

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package net.orpiske.ucf.contrib.scm;

/**
 * Default SCM credentials
 */
public class DefaultCredentials implements ScmCredentials {
	private String userName;
	private String password;


	/**
	 * Constructor
	 * @param userName the user name as a String
	 * @param password the password as a String
	 */
	public DefaultCredentials(final String userName, final String password) {
		this.userName = userName;
		this.password = password;
	}


	/**
	 * Gets the user name
	 * @return the user name as a String
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * Gets the password
	 * @return the password as a String object
	 */
	public String getPassword() {
		return password;
	}
}
