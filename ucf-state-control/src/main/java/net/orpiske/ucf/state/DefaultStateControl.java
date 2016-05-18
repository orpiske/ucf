package net.orpiske.ucf.state;

import java.io.File;
import java.io.IOException;

import net.orpiske.ucf.types.ConfigurationUnit;
import net.orpiske.ucf.types.Target;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by opiske on 5/18/16.
 */
public class DefaultStateControl implements StateControl {
    private static final Logger logger = LoggerFactory.getLogger(DefaultStateControl.class);

    private File repository;
    private Git git;

    /**
     * Access the repository
     * @param file the repository directory or file
     * @return A repository pointer object
     * @throws ScmAccessException
     */
    private Repository accessRepository(final File file)
            throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository;


        if (file.isDirectory()) {
            repository = builder.setGitDir(file)
                    .readEnvironment()
                    .findGitDir()
                    .build();
        }
        else {
            repository = builder.setGitDir(file.getParentFile())
                    .readEnvironment()
                    .findGitDir()
                    .build();
        }

        return repository;
    }

    @Override
    public void track(File repository) throws Exception {
        this.repository = repository;
        logger.debug("Initializing traker on {}", repository.getPath());
        InitCommand initCommand = Git.init();

        initCommand.setDirectory(repository);
        git = initCommand.call();



    }

    @Override
    public void save(File file, final ConfigurationUnit unit) throws Exception {
        logger.debug("Saving file {} in {}", file.getName(), repository.getPath());
        Repository repo = accessRepository(repository);

        logger.debug("Repo path: {}", repo.getDirectory().getPath());

        Status status = git.status().call();

        for (String s : status.getUntracked()) {
            Target target = unit.getUnitId().getTarget();

            String targetPath = target.getFullPath();
            logger.trace("Untracked file: {}", s);
            logger.trace("Target path: {}", targetPath);

            if (s.equals(targetPath)) {
                logger.debug("Adding untracked file {}", s);

                AddCommand ac = git.add();
                ac.addFilepattern(targetPath).call();
            }
        }

    }

    @Override
    public void restore(File file) throws Exception {

    }
}
