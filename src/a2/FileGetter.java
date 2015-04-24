package a2;

import java.nio.file.NoSuchFileException;

/**
 * file getter class is a helper class to check if file exist and to get the
 * file if so
 * 
 * @author zhiyan
 *
 */
public class FileGetter extends Command {
	/**
	 * check if file exist for a given directory
	 * 
	 * @param d
	 * @param file
	 * @return a boolean value
	 */
	public static boolean checkfile(Directory d, String filename) {
		if (!(d.getFileListing().isEmpty())) {
			for (File f : d.getFileListing()) {
				if (f.getName().equals(filename))
					return true;
			}
		}
		return false;
	}

	/**
	 * ruturn the file object if exist, otherwise throw an NoSuchFileException
	 * 
	 * @param d
	 * @param file
	 * @return
	 * @throws NoSuchFileException
	 */
	public static File getFile(Directory d, String filename)
			throws NoSuchFileException {
		if (FileGetter.checkfile(d, filename)) {
			for (File f : d.getFileListing()) {
				if (f.getName().equals(filename))
					return f;
			}
		}
		throw new NoSuchFileException(filename + " " + "does not exist");
	}
}
