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


import net.orpiske.ucf.contrib.repository.exceptions.RepositoryUpdateException;
import net.orpiske.ucf.contrib.scm.Scm;
import net.orpiske.ucf.contrib.scm.exceptions.DuplicateCheckoutException;
import net.orpiske.ucf.contrib.scm.exceptions.ScmCheckoutException;
import net.orpiske.ucf.contrib.scm.exceptions.ScmUpdateException;
import net.orpiske.ucf.contrib.scm.git.GitSCM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;



/**
 * Git repository provider
 * 
 * @author Otavio R. Piske <angusyoung@gmail.com>
 */
public class GitProvider implements Provider {
	
	private static final Logger logger = LoggerFactory.getLogger(GitProvider.class);
	
	private RepositoryInfo repositoryInfo;
	
	/**
	 * Default constructor
	 * @param repositoryInfo repository information
	 */
	public GitProvider(final RepositoryInfo repositoryInfo) {
		this.repositoryInfo = repositoryInfo;
	}
		
		
	/*
	 * (non-Javadoc)
	 * @see net.orpiske.ssps.common.repository.Provider#create()
	 */
	public void create() throws RepositoryUpdateException {
		Scm scm = new GitSCM();

		String repositoryPath = repositoryInfo.getLocalPath();
		File repositoryDir = new File(repositoryPath);

		try {
			scm.checkout(repositoryInfo.getUrl(), repositoryDir);
		} catch (ScmCheckoutException e) {
			throw new RepositoryUpdateException("Unable to checkout repository", e);
		} catch (DuplicateCheckoutException e) {
			logger.warn("The local repository already exists and will not be recreated", e);
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see net.orpiske.ssps.common.repository.Provider#update()
	 */
	public void update() throws RepositoryUpdateException {
		Scm scm = new GitSCM();

		try {
			scm.update(repositoryInfo.getLocalPath());
		} catch (ScmUpdateException e) {
			throw new RepositoryUpdateException("Unable to update repository", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.orpiske.ssps.common.repository.Provider#initialize()
	 */
	public void initialize() throws RepositoryUpdateException {
		File repositoryDir = new File(repositoryInfo.getLocalPath());
		
		if (!repositoryDir.exists()) {
			create();
		}
		else {
			update();
		}
	}
	
}
