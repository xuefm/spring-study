package com.expect.spring;

import org.junit.jupiter.api.Test;

import java.io.*;

public class Assign {
    public static void main(String args[]) {
        int x, y;
        float z = 3.414f;
        double w = 3.1415;
        boolean truth = true;
        char c;
        String str;
        String str1 = "bye";
        c = 'A';
        str = "Hi out there";
        x = 6;
        y = 1000;

        System.out.println("x=" + x);
        System.out.println("y=" + y);
        System.out.println("z=" + z);
        System.out.println("w=" + w);
        System.out.println("truth=" + truth);
        System.out.println("c=" + c);
        System.out.println("str=" + str);
        System.out.println("str1=" + str1);
    }

    @Test
    public  void t1() throws Exception {
        long start = System.currentTimeMillis();
        File sourceFile = new File("F:/temp/elasticsearch-8.7.0-windows-x86_64.zip");
        File destFile = new File("F:/temp/elasticsearch-8.7.0-windows-x86_641.zip");

        InputStream in = new FileInputStream(sourceFile);
        OutputStream out = new FileOutputStream(destFile);
        BufferedInputStream bin = new BufferedInputStream(in);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = bin.read(bytes)) > 0) {
            bout.write(bytes, 0, length);
        }
        bin.close();
        bout.close();
        in.close();
        out.close();
        System.out.println(System.currentTimeMillis()-start);
    }

    @Test
    public  void t2() throws Exception {
        long start = System.currentTimeMillis();
        File sourceFile = new File("F:/temp/elasticsearch-8.7.0-windows-x86_64.zip");
        File destFile = new File("F:/temp/elasticsearch-8.7.0-windows-x86_641.zip");

        InputStream in = new FileInputStream(sourceFile);
        OutputStream out = new FileOutputStream(destFile);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = in.read(bytes)) > 0) {
            out.write(bytes, 0, length);
        }
        in.close();
        out.close();
        System.out.println(System.currentTimeMillis()-start);
    }

}
