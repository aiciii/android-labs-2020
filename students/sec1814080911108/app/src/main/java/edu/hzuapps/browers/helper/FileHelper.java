package edu.hzuapps.browers.helper;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.hzuapps.browers.bookmarks.Bookmark;

/**
 * Created by lanying on 2020/11/30
 */
public class FileHelper {
    private Context mContext;

    public FileHelper() {
    }

    public FileHelper(Context mContext) {
        super();
        this.mContext = mContext;
    }

    /*
     *  文件保存
     * */
    public void add(String fileName, String fileContent) throws Exception {
        //私有模式,创建出来的文件只能被本应用访问,覆盖原文件
        FileOutputStream output = mContext.openFileOutput(fileName, Context.MODE_APPEND);
        output.write(fileContent.getBytes());  //字节流写入
        output.close();         //关闭输出流
    }

    public void save(String fileName, String fileContent) throws Exception {
        //私有模式,创建出来的文件只能被本应用访问,覆盖原文件
        FileOutputStream output = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
        output.write(fileContent.getBytes());  //字节流写入
        output.close();         //关闭输出流
    }

    /*
     * 文件读取
     * */
    public String read(String fileName) throws IOException {
        // 打开文件输入流
        FileInputStream input = mContext.openFileInput(fileName);
        byte[] temp = new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        // 读取文件内容:
        while ((len = input.read(temp)) > 0) {
            sb.append(new String(temp, 0, len));
        }
        // 关闭输入流
        input.close();
        return sb.toString();
    }

    /*
     * 文件删除
     * */
    public boolean delete(String fileName){
        File file = new File(fileName);
        return file.delete();
    }

}
