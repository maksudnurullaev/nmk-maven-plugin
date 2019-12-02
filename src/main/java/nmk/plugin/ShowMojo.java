package nmk.plugin;


import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.sisu.Description;

/**
 * Show goals backup/restore directory and file name.
 */
@Mojo( name = "show", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
@Description("Show finded pom.xml files to backup/restore actions.")
public class ShowMojo
    extends AbstractMojo
{	

    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;
   
    @Parameter( defaultValue = "pom.xml", property = "fileMask", required = true )
    private String fileMask;

    @Parameter( defaultValue = "${project.artifactId}", readonly = true)
    private String artifactId;

	public void execute()
        throws MojoExecutionException
    {
    	getLog().info( "NMK:SHOW, file(s) will be saved:" + outputDirectory.getAbsolutePath() );
    	getLog().info( "NMK:SHOW, file(s) mask:" + String.format("%s-%s", artifactId,fileMask));
    }
}
