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
 * Goal to show found pom.xml files to backup/restore actions.
 */
@Mojo( name = "backup", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
@Description("Backup actions.")
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
    private File basedir;    

	public void execute()
        throws MojoExecutionException
    {
    	getLog().info( "NMK:BACKUP, file(s) will be saved:" + outputDirectory.getAbsolutePath() );
    	getLog().info( "NMK:BACKUP, file(s) mask:" + fileMask);
    	
    	File _fileMask = new File(basedir, fileMask);
    	if(!_fileMask.exists()) {
    		throw new MojoExecutionException("Could not find sorce file maks: " + fileMask);
    	}
    	
    	File _destFileMask = new File(outputDirectory, fileMask);

    	if ( !outputDirectory.exists() )
        {
    		outputDirectory.mkdirs();
        }
    	
    	try {
    	    FileUtils.copyFile(_fileMask, _destFileMask);
    	} catch (IOException e) {
    		throw new MojoExecutionException(e.getMessage());
    	}
    }
}
