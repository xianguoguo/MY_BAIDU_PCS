package sample;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

public class UploadRobot implements Runnable {
	private static String host = "bcs.duapp.com";
	private static String accessKey = "";
	private static String secretKey = "";
	private static String bucket = "";
	
	
	public BaiduBCS baiduBCS;
	
	// 线程池
	private static ExecutorService pool;
	
	private String rootPath;
	public UploadRobot(String rootPath){
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		baiduBCS = new BaiduBCS(credentials, host);
		baiduBCS.setDefaultEncoding("UTF-8");
		this.rootPath = rootPath;
	}
	public List<File> scanFile(File root) {
        List<File> fileInfo = new ArrayList<File>();

        File[] files = root.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.isDirectory() && pathname.isHidden()) { // 去掉隐藏文件夹
                    return false;
                }
                if (pathname.isFile() && pathname.isHidden()) {// 去掉隐藏文件
                    return false;
                }
                return true;
            }
        });
        for (File file : files) {// 逐个遍历文件
            if (file.isDirectory()) { // 如果是文件夹，则进一步遍历，属于递归算法
                List<File> ff = scanFile(file);
                fileInfo.addAll(ff);
            } else {
                fileInfo.add(file); // 如果不是文件夹，则添加入文件列表
            }
        }
        return fileInfo;
    }
	
	public void putObjectByFile(File file) {
		String object = file.getAbsolutePath().replace("\\", "/").replace("E:/rings/data", "");
		//System.out.println(object);
		
		PutObjectRequest request = new PutObjectRequest(bucket, object, file);
		ObjectMetadata metadata = new ObjectMetadata();
		// metadata.setContentType("text/html");
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		System.out.println(response.getRequestId());
	}
	@Override
	public void run() {
		List<File> files = scanFile(new File(rootPath));
		for (File file : files) {
			//System.out.println(file.getAbsolutePath());
			putObjectByFile(file);
		}
	}
}
