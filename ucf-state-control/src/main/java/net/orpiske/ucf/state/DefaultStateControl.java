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

    private Git git;
    private File repositoryPath;

    public void initTracking(final File repositoryPath) throws Exception {
        this.repositoryPath = repositoryPath;
        logger.debug("Initializing traker on {}", repositoryPath.getPath());
        InitCommand initCommand = Git.init();

        initCommand.setDirectory(repositoryPath);
        git = initCommand.call();
    }

    public void open(final File repositoryPath) throws Exception {
        this.repositoryPath = repositoryPath;
        // Repository repository = accessRepository(repositoryPath);

        git = Git.open(repositoryPath);
    }

    /**
     * Access the repository
     * @param file the repository directory or file
     * @return A repository pointer object
     * @throws IOException
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
    public void track(final File file, final ConfigurationUnit unit) throws Exception {
        logger.debug("Saving file {} in {}", file.getName(), repositoryPath.getPath());
        Repository repo = accessRepository(repositoryPath);

        logger.trace("Repo path: {}", repo.getDirectory().getPath());
        Status status = git.status().call();

        /**
         * The rationale is that we don't need to do anything if the files are already
         * tracked.
         */
        for (String s : status.getUntracked()) {
            Target target = unit.getUnitId().getTarget();

            String targetPath = target.getFullPath();
            logger.trace("Untracked file: {}", s);
            logger.trace("Target path: {}", targetPath);

            if (s.equals(targetPath)) {
                logger.debug("Adding untracked file {}", s);

                AddCommand ac = git.add();
                ac.addFilepattern(targetPath).call();
                break;
            }
        }
    }

    @Override
    public void save(final String comment) throws Exception {
        Status status = git.status().call();


        if (status.hasUncommittedChanges()) {
            logger.info("Saving configuration state for due to configuration changes");
            CommitCommand commitCommand = git.commit();

            commitCommand.setCommitter("ucf", "");
            commitCommand.setMessage(comment);
            commitCommand.setAll(true);
            commitCommand.call();
        }
        else {
            logger.info("There were no changes to the configuration files. Skipping save state");
        }
    }

    @Override
    public void restore(RestorePointer pointer) throws Exception {
        CheckoutCommand checkoutCommand = git.checkout();

        checkoutCommand.setAllPaths(true);
        checkoutCommand.setForce(true);
        checkoutCommand.setStartPoint(pointer.getId());
        checkoutCommand.call();
    }
}
