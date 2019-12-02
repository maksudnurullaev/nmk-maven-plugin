package nmk.plugin;

public class Utils {
	
	public static final String getBackupFileName(final String artifactId, final String fileMask) {
		final String destFileName = String.format("%s-%s", artifactId,fileMask);
		return destFileName;
	}	
}
