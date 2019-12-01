package nmk.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;
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

		ShowMojo showMojo = (ShowMojo) rule.lookupConfiguredMojo(pom, "show");
		assertNotNull(showMojo);
		showMojo.execute();

		BackupMojo backupMojo = (BackupMojo) rule.lookupConfiguredMojo(pom, "backup");
		assertNotNull(backupMojo);
		backupMojo.execute();

		File outputDirectory = (File) rule.getVariableValueFromObject(backupMojo, "outputDirectory");
		assertNotNull(outputDirectory);
		assertTrue(outputDirectory.exists());

		String fileMask = (String) rule.getVariableValueFromObject(backupMojo, "fileMask");
		File _fileMask = new File(outputDirectory, fileMask);
		assertNotNull(_fileMask);
		assertTrue(_fileMask.exists());
		

//		MavenProject project = myMojo.getProject();
//		assertNotNull(project);
//		List<String> modules = project.getModules();
//
//		if (myMojo.isMultiProjectMode()) {
//			myMojo.getLog().info("Modules:");
//			for (String string : modules) {
//				myMojo.getLog().info("-> Module name: " + string);
//			}
//		}
//        File outputDirectory = ( File ) rule.getVariableValueFromObject( myMojo, "outputDirectory" );
//        assertNotNull( outputDirectory );
//        assertTrue( outputDirectory.exists() );

//        File touch = new File( outputDirectory, "touch2.txt" );
//        assertTrue( touch.exists() );

	}

	/** Do not need the MojoRule. */
	@WithoutMojo
	@Test
	public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn() {
		assertTrue(true);
	}

}
