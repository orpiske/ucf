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
package net.orpiske.ucf.provider.net.orpiske.ucf.provider.walker;

import net.orpiske.ucf.types.ConfigurationSource;
import org.apache.commons.io.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Walks through the repository
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
@SuppressWarnings("rawtypes")
public class RepositoryWalker extends DirectoryWalker {
	
	private static final Logger logger = LoggerFactory.getLogger(RepositoryWalker.class);
	
	private ArrayList<ConfigurationSource> fileList = new ArrayList<>();
	private String repositoryName;

    public RepositoryWalker(final String repositoryName) {
        this.repositoryName = repositoryName;
    }

	
	@Override
	protected void handleFile(File file, int depth, Collection results)
			throws IOException 
			
	{
		if (file.isFile()) {
			ConfigurationSource tmp = new ConfigurationSource();

			tmp.setPath(file.getPath());
			tmp.setFile(file);

			// TODO: bad, bad, bad ... read this from a settings file
			File cbDir = new File(file.getParentFile().getParentFile().getParentFile(), "lib/handler");
			tmp.setCbDir(cbDir);

			fileList.add(tmp);

		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ConfigurationSource> load(final File repository) {

		if (logger.isDebugEnabled()) { 
			logger.debug("Looking up repository " + repository.getName());
		}
		
		try {
			if (repository.exists()) {
				walk(repository, new ArrayList());
			}
			else {
				logger.error("The packages directory does not exist in the repository: " 
						+ repository.getPath());
			}
		} catch (IOException e) {
			logger.error("Unable to walk the whole directory: " + e.getMessage(), e);
			logger.error("Returning a partial list of all the repository data due to errors");
		}
		
		return fileList;
	}

}
