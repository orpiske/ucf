/**
   Copyright 2012 Otavio Rodolfo Piske

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
package net.orpiske.ucf.contrib.repository.utils;


import net.orpiske.ucf.contrib.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Repository utilities
 * @author Otavio R. Piske <angusyoung@gmail.com>
 */
public class RepositoryUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(RepositoryUtils.class);
	
	private static String REPOSITORIES = "repositories";
	private static String DEFAULT_REPOSITORY_NAME = "default";
	
	

	/**
	 * Gets the path to the user repository (by default $HOME/.sdm/repositories)
	 * @return the path to the user repository
	 */
	public static String getUserRepository() {
		return Utils.getAppDirectoryPath() + File.separator + REPOSITORIES;
	}
	
	
	/**
	 * Gets a File object pointing to the user repository (by default $HOME/.sdm/repositories)
	 * @return a File object that points to the user repository
	 */
	public static File getUserRepositoryFile() {
		String userRepositoryPath = getUserRepository();
		
		return new File(userRepositoryPath);
	}
	
	
	/**
	 * Gets the path to the user repository (by default $HOME/.sdm/repositories)
	 * @return the path to the user repository
	 */
	public static String getUserDefaultRepository() {
		return Utils.getAppDirectoryPath() + File.separator + REPOSITORIES + File.separator
				+ DEFAULT_REPOSITORY_NAME;
	}
}
