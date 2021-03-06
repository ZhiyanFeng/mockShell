package a2;

import java.nio.file.NoSuchFileException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.naming.directory.DirContext;

import Exceptions.NoSuchDirectoryException;
/**
 * this class is for remove the file or directory given by a command
 * @author yan
 *
 */
public class Remover extends Command{
//	this is the document to return
	private static String doc = "Confirm with the user that they "
			+ "want to delete PATH, and if so,"
			+ " remove it from the JShell file system. If PATH is a directory,"
			+ "recursively remove all files and directories in it, "
			+ "prompting for confirmation for each one.";
//	private static File f=null;
//	private static Directory Dir=null;
	
	/**
	 * default constructor
	 */
	public Remover(){
	};
	
	/**
	 * remove file give the file object with the confirmation of user
	 * @param d
	 * @param f
	 * @param console
	 */
	public static void deleteFile(Directory d, File f,Scanner console){
		String input = console.nextLine();
		if(input.equals("yes")){
			d.getFileListing().remove(f);
		}
	}
	
	/**
	 * delete the direcotry with confirmation from user
	 * @param d
	 * @param console
	 */
	public static void deleteDir(Directory d, Scanner console){
		System.out.println("do you want to delete(yes or no) " 
				+ d.getName());
		String input = console.nextLine();
		if(input.equals("yes")){
			Directory Parent = null;
			HashSet<File> fl = d.getFileListing();
			HashSet<Directory> dir = d.getDirectoryListing();
			int size = dir.size();
			Directory[] Array = dir.toArray(new Directory[size]);
			if(Array.length!=0){
				for(Directory subDir:Array){
					Remover.deleteDir(subDir, console);
				}
			}
			if(!fl.isEmpty()){
				Iterator<File> itr = fl.iterator();
				while(itr.hasNext()){
					File file = itr.next();
					System.out.println("do you want to delete (yes or no) " 
							+ file.getName() );
					String in = console.nextLine();
					if(in.equals("yes")){
						itr.remove();
					}

				}
			}
			Parent  = d.getParent();
			Parent.getDirectoryListing().remove(d);
		}
	}
	
	/**
	 * remove whole directory given the path
	 * @param currentDir
	 * @param path
	 */
	public static void rm(Directory currentDir, String path, Scanner console){
//		check the parameter validity
//		question here
		File f=null;
		Directory Dir=null, targetDir = null,ParentDir = null;
		List<String> sl = null;
		int size;
		
		if(currentDir == null)
			throw new NullPointerException(currentDir + "is null");
		if(path == null)
			throw new NullPointerException("dir or file should not be null");
		
		try{
			f = FileGetter.getFile(currentDir, path);
			sl = DirectoryGetter.splitDir(path);
			size = sl.size();
			try {
				targetDir = DirectoryGetter.getDir(currentDir, sl.subList(0, 
						size-1));
				Remover.deleteFile(targetDir, f, console);
			} catch (NoSuchDirectoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(NoSuchFileException e){
			try{
				Dir = DirectoryGetter.getDir(currentDir, path);
				Remover.deleteDir(Dir, console);
			}catch(NoSuchDirectoryException ex){
				System.out.println("path does not exist");
			}
		}
	}
/**
 * 
 * @return documentation
 */
	public static String getDocumentation() {
		return doc;
	}
}
