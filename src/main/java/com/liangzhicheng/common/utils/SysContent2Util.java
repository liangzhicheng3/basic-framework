package com.liangzhicheng.common.utils;

import com.liangzhicheng.common.constant.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description 屏蔽敏感词工具类
 * @author liangzhicheng
 * @since 2021-03-05
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SysContent2Util {

    /**
     * @description 初始化敏感字库
     * @return Map
     */
    public Map initKeyWord() {
        //读取敏感词库 ,存入Set中
        Set<String> wordSet = readSensitiveWordFile();
        //将敏感词库加入到HashMap中//确定有穷自动机DFA
        return addSensitiveWordToHashMap(wordSet);
    }

    /**
     * @description 读取敏感词库 ,存入HashMap中
     * @return Set<String>
     */
    private Set<String> readSensitiveWordFile() {
        Set<String> wordSet = null;
        //敏感词库
        File file = new File(Constants.CONTENT_FILTER_FILEPATH);
        try {
            //读取文件输入流
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), Constants.INPUT_CHARSET);
            //文件是否是文件和是否存在
            if (file.isFile() && file.exists()) {
                wordSet = new HashSet<String>();
                //BufferedReader是包装类，先把字符读到缓存里，到缓存满了，再读入内存，提高了读效率
                BufferedReader br = new BufferedReader(read);
                String txt = null;
                //读取文件，将文件内容放入到set中
                while ((txt = br.readLine()) != null) {
                    wordSet.add(txt);
                }
                br.close();
            }
            //关闭文件流
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordSet;
    }

    /**
     * @description 将HashSet中的敏感词，存入HashMap中
     * @param wordSet
     * @return Map
     */
    private Map addSensitiveWordToHashMap(Set<String> wordSet) {
        //初始化敏感词容器，减少扩容操作
        Map wordMap = new HashMap(wordSet.size());
        for (String word : wordSet) {
            Map nowMap = wordMap;
            for (int i = 0; i < word.length(); i++) {
                //转换成char型
                char keyChar = word.charAt(i);
                //获取
                Object tempMap = nowMap.get(keyChar);
                //如果存在该key，直接赋值
                if (tempMap != null) {
                    nowMap = (Map) tempMap;
                } else { //不存在则，则构建一个map，同时将isEnd设置为0
                    //设置标志位
                    Map<String, String> newMap = new HashMap<String, String>();
                    newMap.put("isEnd", "0");
                    //添加到集合
                    nowMap.put(keyChar, newMap);
                    nowMap = newMap;
                }
                //最后一个
                if (i == word.length() - 1) {
                    nowMap.put("isEnd", "1");
                }
            }
        }
        return wordMap;
    }

}