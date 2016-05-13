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
package net.orpiske.ucf.contrib.repository;

import net.orpiske.ucf.contrib.repository.utils.RepositoryUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Creates a new Provider based on the repository information
 * @author Otavio R. Piske <angusyoung@gmail.com>
 */
public class ProviderFactory {
	private static final Logger logger = Logger.getLogger(ProviderFactory.class);
	
	private static final String GIT_METADATA_DIR = ".git";
	private static final String SVN_METADATA_DIR = ".svn";
	
	
	/**
	 * Creates a new repository provider based on the repository information
	 * @param repositoryInfo repository information
	 * @return A repository provider object
	 */
	public static Provider newProvider(final RepositoryInfo repositoryInfo) {
		String url = repositoryInfo.getUrl();
		
		if (url.endsWith(".git") || url.startsWith("git://")) {
			return new GitProvider(repositoryInfo);
		}
		
		if (url.startsWith("svn://")) {
			return new SvnProvider(repositoryInfo);
		}
		
		// Defaults to SvnProvider because, well, most git repositories end with ".git"
		return new SvnProvider(repositoryInfo);
	}
	
	
	private static boolean isRepository(final File repositoryPath, final String metadataDir) {
		String metadataSubDir = repositoryPath.getPath() + File.separator + metadataDir;
		File dir = new File(metadataSubDir);
		
		if (dir.exists()) {
			return true;
		}
		
		return false;
	}
	
	
	private static boolean isGit(final File repositoryPath) {
		return isRepository(repositoryPath, GIT_METADATA_DIR);
	}
	
	
	private static boolean isSvn(final File repositoryPath) {
		return isRepository(repositoryPath, SVN_METADATA_DIR);
	}
	
	
	private static void setRepositoryCredentials(RepositoryInfo repositoryInfo) {
		String repositoryPath = RepositoryUtils.getUserRepository();

		String path = repositoryPath + File.separator + repositoryInfo.getName() 
				+ File.separator + "user.conf";

		try {
			PropertiesConfiguration userConfig = new PropertiesConfiguration(path);
			
			String userName = userConfig.getString(repositoryInfo.getName() 
					+ ".auth.user");
			repositoryInfo.setUserName(userName);
			
			String password = userConfig.getString(repositoryInfo.getName() 
					+ ".auth.password");
			repositoryInfo.setPassword(password);
			
		} catch (ConfigurationException e) {
			logger.warn("Unable to load user configuration settings for " + 
					repositoryInfo.getName() + "repository");
		}
	}
	
	
	/**
	 * Creates a new repository provider based on the information available in the 
	 * repository dir
	 * 
	 * @param repositoryPath the path to the repository
	 * @return A repository provider object
	 */
	public static Provider newProvider(final File repositoryPath) {
		RepositoryInfo repositoryInfo = new RepositoryInfo(repositoryPath.getName());
		
		repositoryInfo.setUrl("file://" + repositoryPath.getPath());
		setRepositoryCredentials(repositoryInfo);
		
		if (isGit(repositoryPath)) {
			return new GitProvider(repositoryInfo);
		}
		
		if (isSvn(repositoryPath)) {
			return new SvnProvider(repositoryInfo);
		}
			
		return null;
	} 
}
