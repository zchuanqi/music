package cn.itcast.music.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ByteUtil {
    public static void inputStream_to_file(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //解析通过FormData传送的request参数
    public static Map parseParams(HttpServletRequest req){
        DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        Map params=new HashMap();
        try {
            List items=upload.parseRequest(req);
            for(Object object:items){
                FileItem fileItem=(FileItem) object;
                if(fileItem.isFormField()){
                    params.put(fileItem.getFieldName(),fileItem.getString("utf-8"));
                }
            }
        } catch (FileUploadException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return params;
    }

    //获取FormData中的文件流
    public static Map<String,InputStream> parseFileStream(HttpServletRequest req){
        Map<String,InputStream> resultMap=new HashMap<>();
        try {
            ServletFileUpload upload=new ServletFileUpload();
            upload.setFileSizeMax(1024*1024*100);
            upload.setSizeMax(1024*1024*100*2);
            FileItemIterator iterator=upload.getItemIterator(req);
            while ((iterator.hasNext())){
                FileItemStream item=iterator.next();
                if (!item.isFormField()) {
                    resultMap.put(item.getFieldName(),item.openStream());
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static byte[] readByte(InputStream inputStream)throws IOException{
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[100000];   //设置缓冲区
        int len=0;
        while((len=inputStream.read(buffer,0,buffer.length))!=-1){
            outputStream.write(buffer,0,len);   //缓冲区写入到byte输出流
        }
        inputStream.close();
        return outputStream.toByteArray();
    }
}
