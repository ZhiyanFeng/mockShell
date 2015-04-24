package a2;

import java.util.*;

import Exceptions.NoSuchDirectoryException;

public class DirectoryGetter extends Command {
	public DirectoryGetter(Directory r, String name) {
	}

	/**
	 * parse the string to a list
	 * 
	 * @param D
	 *            is file string name
	 * @param L
	 *            is list stored the splitted D
	 */
	public static List<String> splitDir(String D) {
		List<String> L = new ArrayList<>();
		if (D.charAt(0) == '/') {
			L.add("/");
			if (D.length() != 1) {
				L.addAll(Arrays.asList(D.substring(1).split("/")));
			}
		} else {
			L.addAll(Arrays.asList(D.split("/")));
		}
		return L;
	}

	/**
	 * after given a string name find the directory object begin in the root and
	 * return the targeted directory object
	 * 
	 * @param DirName
	 *            is a string name of directory
	 * @return target directory
	 * @throws NoSuchDirectoryException
	 */
	public static Directory getDir(Directory CurrentDir, List<String> DirName)
			throws NoSuchDirectoryException {
		// create a root for later use
		Directory root = FileSystem.createFileSystemInstance().getRoot();
		int size = DirName.size();
		// check the first element of the List<String> DirName if it fall into
		// certain scenarios, execute the according code
		switch (DirName.get(0)) {
		case "/":
			// go to the root
			if (size == 1) {
				return root;
			} else {
				return getDir(root, DirName.subList(1, size));
			}
		case ".":
			// stay in current directory
			if (size == 1) {
				return CurrentDir;
			} else {
				return getDir(CurrentDir, DirName.subList(1, size));
			}
		case "..":
			// go to parent directory
			if (size == 1) {
				if (CurrentDir.getParent() != null)
					return CurrentDir.getParent();
				else
					return CurrentDir;
			} else {
				if (CurrentDir.getParent() != null) {
					CurrentDir = CurrentDir.getParent();
					return getDir(CurrentDir, DirName.subList(1, size));
				} else {
					return root;
				}

			}
		default:
			// after successfully enter the target directory, search the
			// directory
			// in target dir
			for (Directory d : CurrentDir.getDirectoryListing()) {
				if (d.getName().equals(DirName.get(0)) && size == 1) {
					return d;
				} else if (d.getName().equals(DirName.get(0))) {
					CurrentDir = d;
					return getDir(CurrentDir, DirName.subList(1, size));
				}
			}
			// System.out.println(CurrentDir.getPath() + DirName.get(0) + "
			// does not exist");
			throw new NoSuchDirectoryException();
			// return CurrentDir;

		}
	}

	public static String getDocumentation() {
		return "This class has two static function, splitDir take a file name "
				+ "as arguement, return a list of file names."
				+ "getDir takes a List<String> and current directory as "
				+ "arguements, grab the directory object and return it";
	}

	public static Directory getDir(Directory d, String Path)
			throws NoSuchDirectoryException {
		List<String> splittedPath = splitDir(Path);
		return (getDir(d, splittedPath));
	}
}
