package nmk.plugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import org.eclipse.sisu.Description;
import java.io.File;
import java.io.IOException;

/**
 * Restore goal for for previously saved file (pom.xml, by default).
 */
@Mojo( name = "restore", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
@Description("Restore action.")
public class RestoreMojo
    extends AbstractMojo
{
	
	/**
	 * Get modes of project, Single or Multi-Project mode
	 * 
	 * @author m.nurullayev
	 */
	public enum Modes { Single, Multiple};
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;
   
    @Parameter( defaultValue = "pom.xml", property = "fileMask", required = true )
    private String fileMask;

    @Parameter( defaultValue = "${project.basedir}", readonly = true )
    private File baseDir;    

    @Parameter( defaultValue = "${project.artifactId}", readonly = true)
    private String artifactId;

	public void execute()
        throws MojoExecutionException
    {
    	final String destFileName = Utils.getBackupFileName(artifactId, fileMask);
    	
    	getLog().info( "NMK:RESTORE, file(s) will be restored from:" + outputDirectory.getAbsolutePath() );
    	getLog().info( "NMK:BACKUP, to:" + baseDir.getAbsolutePath() );
    	getLog().info( "NMK:RESTORE, file name:" + destFileName);

    	File _sourceFileMask = new File(outputDirectory, destFileName);
    	File _destFileMask = new File(baseDir, fileMask);

    	if(!baseDir.exists()) {
    		throw new MojoExecutionException("Could not find destination derictory: " + baseDir.getAbsolutePath());
    	}
    	
    	if ( !outputDirectory.exists() )
        {
    		outputDirectory.mkdirs();
        }
    	
    	try {
    	    FileUtils.copyFile(_sourceFileMask, _destFileMask);
    	} catch (IOException e) {
    		throw new MojoExecutionException(e.getMessage());
    	}
    }
}
