package myFileListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import myFileListener.FileMd5;

/**
 * @author jyroo
 * myfilelistener
 */
@SuppressWarnings("unused")
public class myFileListener {
	HashMap<String, String> hashmap = new HashMap<>(); //存放hash键值对
	List<String> file_in = new ArrayList<String>();
	List<String> file_ex = new ArrayList<String>();
	@SuppressWarnings("static-access")
	public void iteratorPath(String dir, List<String> file_in, List<String> file_ex) {
		while(true) {
			List<String> pathName = new ArrayList<String>();  //存放文件名
			File file = new File(dir);
			File[] files = file.listFiles();   //返回某个目录下所有文件和目录的绝对路径  return file[]
			if(files != null) {
				for(File each_file : files) {
					if(each_file.isFile()) {      // 如果是文件
						int jui=2, juj=2;
						if(file_in.size()!=0) {
							jui = 0;
							for(String strin : file_in) {
								if(each_file.getName().indexOf(strin)==-1) {
									jui = 0;
								}
								if(each_file.getName().indexOf(strin)!=-1) {
									jui = 1;
								}
							}
						}
						if(file_ex.size()!=0) {
							juj = 0;
							for(String strex : file_ex) {
								if(each_file.getName().indexOf(strex)!=-1) {
									juj = 1;								
							}
						}
						if(juj==1||jui==0) {
							continue;
						}
						pathName.add(each_file.getName());       //存储文件名   
						
						String file_path = each_file.getAbsolutePath();    //获取文件的绝对路径
						
						try {
							FileMd5 mymd5 = new FileMd5();
							String md5_value = mymd5.fileMd5(file_path);    //生成文件对应的hash值
							if(hashmap.get(each_file.getName())==null) {
								System.out.println("文件夹：" + dir + "中的文件：" + each_file.getName() + "为新建文件！时间为：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
								hashmap.put(each_file.getName(), md5_value);    //以文件名作为key，hash值作为value存储到hashmap中
							}
							if(!hashmap.get(each_file.getName()).equals(md5_value)) {
								System.out.println("文件夹：" + dir + "中的文件：" + each_file.getName() + "被更新！时间为：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
								hashmap.put(each_file.getName(), md5_value);
							}
						} catch (Exception e) {
							System.out.println("发生 "+e+" 的错误！！时间为" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						}
					}
	//					}else if(each_file.isDirectory()) {       //如果是文件夹
	//						//iteratorPath(each_file.getAbsolutePath());   //递归遍历
	//					}
					}
				}
				try {
					int juk;
					for(String key : hashmap.keySet()) {
						if(!pathName.contains(key)) {
							System.out.println("文件夹：" + dir + "中的文件：" + key + "的文件已被删除！时间为：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
							hashmap.remove(key);
						}
					}
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		myFileListener file_walk = new myFileListener();
		List<String> file_ex = new ArrayList<String>();
		List<String> file_in = new ArrayList<String>();
		file_ex.add(".rec");
		//file_in.add("hi");
		file_walk.iteratorPath("E:\\tmp\\", file_in, file_ex);
		for(String key:file_walk.hashmap.keySet()){
			System.out.println("Key: "+key+" Value: "+file_walk.hashmap.get(key));
        }
	}	
	
}
