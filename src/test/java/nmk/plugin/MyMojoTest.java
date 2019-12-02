package nmk.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Rule;
import org.junit.Test;

public class MyMojoTest {
	@Rule
	public MojoRule rule = new MojoRule() {
		@Override
		protected void before() throws Throwable {
		}

		@Override
		protected void after() {
		}
	};

	/**
	 * @throws Exception if any
	 */
	@Test
	public void testFilesExistance() throws Exception {
		File pom = new File("target/test-classes/project-to-test/");
		assertNotNull(pom);
		assertTrue(pom.exists());

		// 1. Test show goal processing
		ShowMojo showMojo = (ShowMojo) rule.lookupConfiguredMojo(pom, "show");
		assertNotNull(showMojo);
		showMojo.execute();

		// 2. Test backup goal processing
		BackupMojo backupMojo = (BackupMojo) rule.lookupConfiguredMojo(pom, "backup");
		assertNotNull(backupMojo);
		backupMojo.execute();				

		File outputDirectory = (File) rule.getVariableValueFromObject(backupMojo, "outputDirectory");
		assertNotNull(outputDirectory);
		assertTrue(outputDirectory.exists());

		final String fileMask = (String) rule.getVariableValueFromObject(backupMojo, "fileMask");
		final String artifactId = (String) rule.getVariableValueFromObject(backupMojo, "artifactId");
		assertNotNull(artifactId);
		
		final String backupFileName = Utils.getBackupFileName(artifactId, fileMask);
		File _backupedFile = new File(outputDirectory, backupFileName);
		assertNotNull(_backupedFile);
		assertTrue(_backupedFile.exists());
		
		// 3. Test restore goal processing
		RestoreMojo restoreMojo = (RestoreMojo) rule.lookupConfiguredMojo(pom, "restore");
		assertNotNull(restoreMojo);
		restoreMojo.execute();
		
		File _baseDir = (File) rule.getVariableValueFromObject(restoreMojo, "baseDir");		
		assertNotNull(fileMask);		
		File _originalFile = new File(_baseDir, fileMask);
		assertTrue(_originalFile.exists());
		assertTrue(FileUtils.contentEquals(_backupedFile, _originalFile));
	}

	/** Do not need the MojoRule. */
	@WithoutMojo
	@Test
	public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn() {
		assertTrue(true);
	}

}
