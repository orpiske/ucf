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


import net.orpiske.ucf.contrib.exceptions.ContribException;
import net.orpiske.ucf.contrib.repository.exceptions.RepositorySetupException;
import net.orpiske.ucf.contrib.repository.utils.RepositoryUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
public class RepositorySettings {
	
	private static PropertiesConfiguration config;
	
	/**
	 * Initializes the configuration object
	 * 
	 * @throws ContribException if the configuration file is not found, if the configuration
	 * file is not parseable or if unable to create directories
	 */
	public static void initConfiguration() throws ContribException  {
		
		String repositoryPath = RepositoryUtils.getUserRepository();
		String path = repositoryPath + File.separator + "repositories.conf";
		
		if (config == null) {
			
			File file = new File(path);
			
			if (!file.exists()) { 
			
				try {
					if (!file.getParentFile().mkdirs()) {
						throw new ContribException("Unable to create parent directories");
					}
					
					if (!file.createNewFile()) {
						throw new ContribException("Unable to create repository settings file");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					config = new PropertiesConfiguration(path);
				} catch (ConfigurationException e) {
					throw new ContribException("Unable to load repository configuration: "
							+ e.getMessage(), e);
				}	
				
				try {
					config.save();
				} catch (ConfigurationException e) {
					throw new ContribException("Unable to save repository configuration: "
								+ e.getMessage(), e);
				}
			}
			else {
				try {
					config = new PropertiesConfiguration(path);
				} catch (ConfigurationException e) {
					throw new ContribException("Unable to load repository configuration: "
								+ e.getMessage(), e);
				}
			}
	
		}
	}
	
	
	/**
	 * Gets the configuration object
	 * 
	 * @return the instance of the configuration object
	 */
	public static PropertiesConfiguration getConfig() {
		return config;
	}
	
	
	private static void addUserConfig(final RepositoryInfo repositoryInfo) throws RepositorySetupException {
		String repositoryPath = RepositoryUtils.getUserRepository();
		
		String path = repositoryPath + File.separator + repositoryInfo.getName() 
				+ File.separator + "user.conf";
		
		File file = new File(path);
		
		if (!file.exists()) { 
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RepositorySetupException("Unable to create user configuration "
						+ "file", e);
			}
		}
		else {
			throw new RepositorySetupException("This user already exists");
		}
		
		PropertiesConfiguration userConfig;
		try {
			userConfig = new PropertiesConfiguration(path);
			
			userConfig.addProperty(repositoryInfo.getName() + ".auth.user", 
					repositoryInfo.getUserName());
			userConfig.addProperty(repositoryInfo.getName() + ".auth.password", 
					repositoryInfo.getPassword());
			userConfig.save();
		} catch (ConfigurationException e) {
			throw new RepositorySetupException("Unable to save user configuration to " 
					+ path + ":" + e.getMessage(), e);
		}
	
	}
	
	
	public static void addNewRepository(final RepositoryInfo repositoryInfo) throws RepositorySetupException {
		addUserConfig(repositoryInfo);
	}

}
