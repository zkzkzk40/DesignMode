package cn.edu.zjut.webserver.conrtoller;

import com.zjut.util.encrypt.Impl.EncryptTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

import static com.zjut.Application.decryptedFileWrite;
import static com.zjut.Application.readAndSave;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/28 14:54
 * @Description: 该类用于 文件上传下载
 */
@RestController
@RequestMapping("/file")
@Slf4j
@CrossOrigin
public class FileController {

    @Value("${filepath}")
    private String filepath;
    @Value("${encryptMode}")
    private EncryptTypeEnum encryptMode;
    /**
     * 处理文件上传
     */
    @PostMapping(value = "/upload")
    public String uploading(@RequestParam("file") MultipartFile file,HttpServletResponse response) throws UnsupportedEncodingException {
        //1. 上传文件写到暂存区
        File targetFile = new File(filepath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(filepath + file.getOriginalFilename());){
            out.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件上传失败!");
            return "uploading failure";
        }
        String path=filepath+file.getOriginalFilename();
        log.info("文件上传成功!,暂存在:{}",path);

        String newFilePath;
        //2. 解密文件
        if(file.getOriginalFilename().endsWith(".zk")){
            log.info("开始解密文件:{},加密模式为:{}",file.getOriginalFilename(),encryptMode);
            newFilePath=decryptedFileWrite(path);
        }
        //3. 加密文件
        else{
            log.info("开始加密文件:{},加密模式为:{}",file.getOriginalFilename(),encryptMode);
            newFilePath=readAndSave(path,encryptMode);
        }
        log.info("开始向客户端传输文件:{}",newFilePath);
        //4.向客户端传输文件
        File newfile = new File(newFilePath);
        if(newfile.exists()){
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(newfile.getName(),"utf8"));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            try(FileInputStream fis= new FileInputStream(newfile);
                BufferedInputStream bis = new BufferedInputStream(fis)) {
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("结束");
        return "uploading success!";
    }

}
