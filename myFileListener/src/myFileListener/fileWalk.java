package myFileListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

@SuppressWarnings("unused")
public class fileWalk {
	List<String> pathName = new ArrayList<String>();
	public void iteratorPath(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles();   //listFiles是获取该目录下所有文件和目录的绝对路径  return file[]
		if(files != null) {
			for(File each_file : files) {
				if(each_file.isFile()) {
					pathName.add(each_file.getName());
				}else if(each_file.isDirectory()) {
					iteratorPath(file.getAbsolutePath());
				}
			}
		}
	}

	public static void main(String[] args) {
		fileWalk file_walk = new fileWalk();
		file_walk.iteratorPath("E:\\tmp\\");
		for(String list : file_walk.pathName) {
			System.out.println(list);
		}
	}
}
