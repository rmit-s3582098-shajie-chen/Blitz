package Command;

import java.io.InputStream;

/**
 *  A class just for getting the documents path location.
 * @author Liam
  */
public class GetDocPath {

	private static final String REG_QUERY = "reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal";

	/**
	 * @return Path to the windows users documents folder.
	 */
	public static String getDocumentsPath() {
		String myDocuments = null;
		try {
		    Process p =  Runtime.getRuntime().exec(REG_QUERY);
		    p.waitFor();

		    InputStream in = p.getInputStream();
		    byte[] b = new byte[in.available()];
		    in.read(b);
		    in.close();

		    myDocuments = new String(b);
		    myDocuments = myDocuments.split("\\s\\s+")[4];

		} catch(Throwable t) {
		    t.printStackTrace();
		    return "";
		}
		return myDocuments;
	}
	
}
