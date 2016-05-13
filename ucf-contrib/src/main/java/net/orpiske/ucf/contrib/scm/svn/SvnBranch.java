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
package net.orpiske.ucf.contrib.scm.svn;


import net.orpiske.ucf.contrib.scm.ScmBranch;

/**
 * SVN branch support implementation
 */
public class SvnBranch implements ScmBranch {
	private String url;

	/**
	 * Constructor
	 * @param url Branch URL
	 */
	public SvnBranch(final String url) {
		this.url = url;
	}


	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getUrl() {
		return url;
	}
}
