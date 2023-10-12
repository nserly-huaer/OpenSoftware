package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Scanner;

public class Main {
    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final Runtime runtime = Runtime.getRuntime();
        Process process = null;

        String[] openPath = null;
        Reader r = null;
        boolean isOpen = false;
        if (Reader.Cache.length() != 0) {
            System.out.println("请输入是否沿用上次打开方法(true;false)");
            isOpen = sc.nextBoolean();
        }

        if (isOpen) {
            System.out.println("读取中...");
            logger.info("读取中...");
            r = new Reader(new File(""));
            openPath = r.Read();
        } else {
            r = new Reader(new File(""));
            r.CleanData();
            System.out.println("请输入文件路径($<返回)");
            for (; ; ) {
                String str = sc.nextLine();
                if (!(str.endsWith("\"") && str.startsWith("\""))) {
                    str = str.replace("\"", "");
                }
                str = str.trim();
                if (str.equals("$<"))
                    break;
                r.Write(str);
            }
            openPath = r.Read();
        }

        boolean exception = false;
        try {

            for (int i = 0; i < openPath.length; i++) {
                if (openPath[i].isEmpty())
                    continue;
                String cmd = openPath[i];
                ProcessBuilder processBuilder = new ProcessBuilder(cmd);
                process = processBuilder.start();
            }

        } catch (final Exception e) {
            exception = true;
            logger.error(e);
        } finally {
            if (exception) {
                logger.error("运行失败，请与管理员联系~");
            } else {
                System.out.println("运行成功！");
                logger.info("运行成功！");
            }
        }
    }

}