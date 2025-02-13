package com.sangeng;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest
//@ConfigurationProperties(prefix = "oss")
public class OSSTest {

//    private String accessKey;
//    private String secretKey;
//    private String bucket;
//
//    public void setAccessKey(String accessKey) {
//        this.accessKey = accessKey;
//    }
//
//    public void setSecretKey(String secretKey) {
//        this.secretKey = secretKey;
//    }
//
//    public void setBucket(String bucket) {
//        this.bucket = bucket;
//    }

    @Test
    public void testOss(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "fsBR4Z4Q7wv9ab0J2S7GbXCN1om10PWU1UX2Pms2";
        String secretKey = "HXwx7vJ-sre7sFx253SI6DpOD3V7WwDI3gHuO9K7";
        String bucket = "ylcblog";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "2022/sg.png";

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);


            InputStream inputStream = new FileInputStream("/Users/yulong/Desktop/SGBlog/tuzi.gif");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                System.out.println("上传成功");
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

    }
}
