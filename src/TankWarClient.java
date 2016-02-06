import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by WEI on 2016/2/5.
 */



public class TankWarClient extends Frame {

    int x = 50;
    int y = 50;

    //画出坦克的原型
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);      //设置坦克的颜色为红色
        g.fillOval(x,y,20,20);    //设置坦克的位置距离左上角为：50 50    坦克的大小为半径20
        g.setColor(c);
        y = y +5;
    }

    //游戏窗口的函数
    public void lanchFrame(){
        this.setBackground(Color.BLUE);
        this.setLocation(300,100);  //定义游戏框离电脑左上角的距离  距离电脑左边：300   距离电脑上边：100
        this.setSize(800,600);      //定义游戏框的大小    宽：800  高：600
        this.setTitle("TankWar");   //定义标题栏的名字为：TankWar

        //设置一个监听器  监听窗口关闭的操作
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);     //关闭游戏
            }
        });
        this.setResizable(false);   //让游戏框的大小不可变
        this.setVisible(true);      //让游戏框可见

        new Thread( new PaintThread()).start();
    }


    //主函数的入口
    public static void main(String[] args) {
       TankWarClient tw = new TankWarClient();
        tw.lanchFrame();
    }

    private class PaintThread implements Runnable {
        public void run(){
            while (true) {
                try {
                    repaint();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
