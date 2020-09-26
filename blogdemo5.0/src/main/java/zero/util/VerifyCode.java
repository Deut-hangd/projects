package zero.util;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerifyCode {

    // 随机生成二维码内容
    public static String drawRandomText(int width, int height, BufferedImage verifyImg) {
        Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
        // 将画笔颜色设为白色
        graphics.setColor(Color.WHITE);
        // 填充画板背景色
        graphics.fillRect(0, 0, width, height);
        // 设置字体格式
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 35));
        // 字符和数字组合,验证码生成模板
        String baseNumLetter = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        // 存储验证码的字符串
        StringBuffer verifyImgChars = new StringBuffer();
        Random random = new Random();
        // 旋转原点的坐标
        int x = 20;
        String ch = "";
        // 生成四个随机字符或数字
        for (int i = 0; i < 4; i++){
            // 重置画笔颜色
            graphics.setColor(getRandomColor());
            // 设置字体旋转角度
            int degree = random.nextInt() % 30;
            // 产生随机字符
            int index = random.nextInt(baseNumLetter.length());
            ch = baseNumLetter.charAt(index) + "";
            verifyImgChars.append(ch);
            // 正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 40);
            graphics.drawString(ch, x, 40);
            // 反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 40);
            x += 30;
        }
        // 画干扰线
        for (int i = 0; i < 6; i++){
            // 重置画笔
            graphics.setColor(getRandomColor());
            // 在画板上添加干扰线
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }
        // 画干扰点
        for (int i = 0; i < 60; i++){
            // 生成两个画板范围内的随机数
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            // 重置画笔颜色
            graphics.setColor(getRandomColor());
            // 填充干扰点
            graphics.fillRect(x1, y1, 2, 2);
        }
        return verifyImgChars.toString();
    }

    // 随机产生颜色
    private static Color getRandomColor(){
        Random random = new Random();
        // 随机产生颜色的 RGB 值
        Color color = new Color(random.nextInt(256),
                random.nextInt(256), random.nextInt(256));
        return color;
    }
}
