import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by WEI on 2016/2/5.
 */



public class TankWarClient extends Frame {

    public static final int  GAME_WIDTH = 800;
    public static final int  GAME_LENGTH = 600;

    int x = 50;     //设置坦克的位置距离左上角为：50 50
    int y = 50;
    Image offScreenImage = null;

    //画出坦克的原型
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);      //设置坦克的颜色为红色
        g.fillOval(x,y,20,20);    //   坦克的大小为半径20
        g.setColor(c);
    }

    //使用双缓冲解决图像在显示的时候的不连贯的问题
    @Override
    //使用一个虚拟的图片接收操作，然后一次性的复写到顶层图片(即显示给用户看的图片)
    public void update(Graphics g) {

        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH,GAME_LENGTH);     //设置的虚拟图片必须与原始的图片的大小一致
        }

        Graphics goffScreenImage = offScreenImage.getGraphics();//新建虚拟图片的画笔
        Color c = goffScreenImage.getColor();   //获取虚拟画笔的颜色
        goffScreenImage.setColor(Color.BLUE);   //设置虚拟画笔的颜色为原始图片的颜色，实际的作用是清空上次的画面
        goffScreenImage.fillRect(0,0,GAME_WIDTH,GAME_LENGTH);  //(0,0)表示虚拟画笔的离左上角的位置  可以与原始图片的位置不一致，只要大小一致就可以
        goffScreenImage.setColor(c);            //将虚拟画笔的颜色改为原始的颜色
        paint(goffScreenImage);                 //将虚拟的图片复写到顶层图片上
        g.drawImage(offScreenImage,0,0,null);

    }

    //游戏窗口的函数
    public void lanchFrame(){
        this.setBackground(Color.BLUE);
        this.setLocation(300,100);  //定义游戏框离电脑左上角的距离  距离电脑左边：300   距离电脑上边：100
        this.setSize(GAME_WIDTH,GAME_LENGTH);      //定义游戏框的大小
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
        this.addKeyListener(new KeyMonitor());

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
                    Thread.sleep(50);   //进程睡眠的时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //使用一个私有的键盘监听方法来监听键盘的记录   来控制坦克的移动方向
    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();//获取键盘的按键的值
            switch(key) {
                case KeyEvent.VK_UP:  //键盘值   上
                    y -= 5;
                    break;
                case KeyEvent.VK_LEFT: //键盘值   左
                    x -= 5;
                    break;
                case KeyEvent.VK_RIGHT: //键盘值  右
                    x += 5;
                    break;
                case KeyEvent.VK_DOWN:  //键盘值  下
                    y += 5;
                    break;
            }
        }
    }
}
