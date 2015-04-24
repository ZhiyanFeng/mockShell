package a2;

import java.net.*;
import java.io.*;

/**
 * Class to get a file from a URL.
 * 
 * @author Laura
 *
 */
public class GetFileFromURL extends Command {

	private static String documentation = "Enter get URL in the shell, where "
			+ "URL is a web address, to retrieve the file \n at that URL and "
			+ "add it to the current working directory.";

	/**
	 * Returns string documentation for GetFileFromURL command.
	 * 
	 * @return documentation string
	 */
	public static String getDocumentation() {
		return documentation;
	}

	/**
	 * Take in URL WebAddress and return a filename String.
	 * 
	 * @param WebAddress
	 * @return filename
	 */
	public static String getFileName(String WebAddress) {
		String[] splitAddress = WebAddress.split("/");
		String filename = splitAddress[splitAddress.length - 1];
		return filename;
	}

	/**
	 * Retrieves a file from a specified URL and adds it to the current working
	 * directory.
	 * 
	 * @param WebAddress
	 * @param current
	 *            directory
	 * @throws IOException
	 */
	public static void getter(String WebAddress, Directory current)
			throws IOException {

		URL fileAddress = new URL(WebAddress);
		URLConnection yc = fileAddress.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));

		String filename = getFileName(WebAddress);
		Boolean fileExists = FileGetter.checkfile(current, filename);
		if (fileExists) {
			File f = FileGetter.getFile(current, filename);
			String inputLine;
			inputLine = in.readLine();
			f.overwriteContent(inputLine + "\n");
			while ((inputLine = in.readLine()) != null)
				f.addContent(inputLine + "\n");
			in.close();
		} else {
			File f = new File(filename);
			current.addFile(f);
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				f.addContent(inputLine + "\n");
			in.close();
		}
	}
}
