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
package net.orpiske.ucf.actions;

import net.orpiske.ucf.contrib.repository.RepositoryInfo;
import net.orpiske.ucf.contrib.repository.RepositoryManager;
import net.orpiske.ucf.contrib.repository.exceptions.RepositorySetupException;
import net.orpiske.ucf.contrib.repository.exceptions.RepositoryUpdateException;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.copyOfRange;

/**
 * Implements add-repository action
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
public class RepositoryAction extends Action {
    private enum SubAction {
        ADD,
        UPDATE
    };
	private static final Logger logger = LoggerFactory.getLogger(RepositoryAction.class);

	private CommandLine cmdLine;
	private Options options;

	private boolean isHelp;
    private SubAction subAction;

	private RepositoryInfo repositoryInfo;

	public RepositoryAction(String[] args) {
		processCommand(args);
	}

	private void processAddOptions(String[] args) {
		CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("n", "name", true, "repository name");
        options.addOption("u", "url", true, "repository url");
        options.addOption(null, "username", true, "repository user name");
        options.addOption(null, "password", true, "repository password");
        options.addOption(null, "branch", true, "(git only) repository branch");

        try {
			cmdLine = parser.parse(options, args);
		} catch (ParseException e) {
			help(options, -1);
		}

		isHelp = cmdLine.hasOption("help");

		String name = cmdLine.getOptionValue('n');
		if (name == null) {
			help(options, -1);
		}

		repositoryInfo = new RepositoryInfo(name);


		String url = cmdLine.getOptionValue('u');
		if (url == null) {
			help(options, -1);
		}
		repositoryInfo.setUrl(url);

		String userName = cmdLine.getOptionValue("username");
		repositoryInfo.setUserName(userName);


		String password = cmdLine.getOptionValue("password");
		repositoryInfo.setPassword(password);

		String branch = cmdLine.getOptionValue("branch");
		repositoryInfo.setRepositoryVersion(branch);
	}

	private void processUpdateOptions(String[] args) {
		CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("n", "name", true, "repository name");

        try {
			cmdLine = parser.parse(options, args);
		} catch (ParseException e) {
			help(options, -1);
		}

		isHelp = cmdLine.hasOption("help");

		String name = cmdLine.getOptionValue('n');
		if (name == null) {
			help(options, -1);
		}

		repositoryInfo = new RepositoryInfo(name);
    }

	@Override
	protected void processCommand(String[] args) {


		if (args.length == 0) {
			System.err.println("Not enough parameters");

			return;
		}

		String first = args[0];
        String[] newArgs = copyOfRange(args, 1, args.length);

		if (first.equals("add")) {
            subAction = SubAction.ADD;
            processAddOptions(newArgs);
		}
		else {
            if (first.equals("update")) {
                subAction = SubAction.UPDATE;
                processUpdateOptions(newArgs);
            }
            else {
                System.err.println("Not enough parameters: please inform either add or update");

                return;
            }
		}
	}

	@Override
	public int run() {

		try {
			if (isHelp) {
				help(options, 1);
			}
			else {
                RepositoryManager repositoryManager = new RepositoryManager();

                if (subAction == SubAction.ADD) {
                    repositoryManager.add(repositoryInfo);
                }
                else {
                    repositoryManager.update();
                }
			}
			
			return 0;
		} catch (RepositoryUpdateException e) {
			System.err.println("Unable to update repository: " + e.getMessage());

			if (logger.isDebugEnabled()) {
				logger.error("Unable to install: " + e.getMessage(), e);
			}
			
			return 1;
		} catch (RepositorySetupException e) {
			System.err.println("Unable to setup new repository: " + e.getMessage());

			if (logger.isDebugEnabled()) {
				logger.error("Unable to setup new repository: " + e.getMessage(), e);
			}

			return 2;
		}
	}

}
