package com.liangzhicheng.config.io;

import com.liangzhicheng.common.utils.SysToolUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @description 输入流，输出流
 * @author liangzhicheng
 * @since 2021-03-05
 */
public class IOStreamReader extends Thread {

    private InputStream is;

    public IOStreamReader(InputStream is) {
        this.is = is;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                SysToolUtil.info(line); //输出内容
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}