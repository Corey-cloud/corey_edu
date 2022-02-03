package com.corey.aliyun.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * Created by corey on 2021/7/22
 **/

@Service
public class OssServiceImpl implements OssService {

    // 上传文件到阿里云oss
    @Override
    public String upload(MultipartFile file) {

        // //工具类获取值
        // String endpoint = ConstantPropertiesUtil.END_POINT;
        // String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        // String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        //工具类获取值
        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI5tRxycDSE7PqAM7U2uXh";
        String accessKeySecret = "mppCivtML5Y9EyFxJaeuX8rblFQtGS";
        String bucketName = "corey-edu";

        try {

            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件名称
            String fileName = file.getOriginalFilename();

            // 1 在文件名称中添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //gaga255wlf1.jpg
            fileName = uuid+fileName;
            System.out.println("uuid+fileName:\t" + fileName);

            // 2 把文件按照日期进行分类
            //获取当前日期
            // 2020/12/21
            String datePath = new DateTime().toString("yyyy-MM-dd");
            // 拼接
            // 2020/12/21/gaga255wlf1.jpg
            fileName = datePath+"/"+fileName;
            System.out.println("datePath+\"/\"+fileName:\t" + fileName);

            /**
             * 调用oss方法实现上传
             * @param 1 Bucket名称    2 上传到oss的文件路径和文件名称      3 上传文件输入流
             * @return
             */
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient
            ossClient.shutdown();

            // 返回上传成功后的文件路径
            // https://corey-edu.oss-cn-shenzhen.aliyuncs.com/4adf0004b076304639ba.jfif
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
