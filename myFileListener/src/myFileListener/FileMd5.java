package myFileListener;

import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileInputStream;
import java.io.IOException;

public class FileMd5 {
	public static String fileMd5(String inputFile) throws IOException{
		
		int bufferSize = 1024*1024;  //缓冲区大小
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;
		
		try {
			//获取MD5的实例
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			
			fileInputStream = new FileInputStream(inputFile);
			
			digestInputStream = new DigestInputStream(fileInputStream, messageDigest);   //Creates a digest input stream, using the specified input stream and message digest.
			
			byte[] buffer = new byte[bufferSize];   //设置缓冲区，辅助读取文件，避免文件过大，导致的IO开销
			while(digestInputStream.read(buffer)>0);  //read: updates the message digest    return int
			// 获取最终的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();
			// 拿到结果 return字节数组byte[] 包含16个元素
			byte[] resultByteArray = messageDigest.digest();
			
			return byteArrayToHex(resultByteArray);	   //转换byte 为 string 类型
			
		}catch(NoSuchAlgorithmException e) {
			return null;
		}finally {
			try {
				digestInputStream.close();
				fileInputStream.close();
			}catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public static String byteArrayToHex(byte[] byteArray){
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        //一个字节是八位二进制 也就是2位十六进制字符
        char[] resultCharArray = new char[byteArray.length*2];

        int index = 0;
        for(byte b : byteArray){
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        return new String(resultCharArray);
    }
}
