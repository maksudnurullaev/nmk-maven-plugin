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
 * Backup goal for file (pom.xml file, by default).
 */
@Mojo( name = "backup", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
@Description("Backup action.")
public class BackupMojo
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
    	
    	getLog().info( "NMK:BACKUP, file(s) will be backuped from:" + baseDir.getAbsolutePath() );
    	getLog().info( "NMK:BACKUP, to:" + outputDirectory.getAbsolutePath() );
    	getLog().info( "NMK:BACKUP, file name:" + destFileName);
    	
    	File _sourceFileMask = new File(baseDir, fileMask);
    	File _destFileMask = new File(outputDirectory, destFileName);

    	if(!_sourceFileMask.exists()) {
    		throw new MojoExecutionException("Could not find source directory: " + _sourceFileMask.getAbsolutePath());
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
