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
package net.orpiske.ucf.contrib.scm.git;

import net.orpiske.ucf.contrib.scm.ScmBranch;

/**
 * Git Branch Implementation
 */
public class GitBranch implements ScmBranch {
	private String name;
	private String url;

	/**
	 * Constructor
	 * @param name Branch name
	 * @param url Branch URL
	 */
	public GitBranch(final String name, final String url) {
		this.name = name;
		this.url = url;
	}
	
	
	@Override
	public String getName() {
		return name;  
	}

	@Override
	public String getUrl() {
		return url;
	}
}
