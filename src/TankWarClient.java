
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * ━━━━━━萌萌哒━━━━━━
 *Code is far away from bug with the animal protecting　　　　　　　　　　
 * 　　　　　　　　　神兽保佑,代码无bug
 * Created by WEI on 2016/2/5.
 */



public class TankWarClient extends Frame {

    /**
     * 此变量是表示整个游戏的屏幕的宽度
     */
    public static final int  GAME_WIDTH = 800;
    /**
     * 此变量是表示整个游戏的屏幕的高度
     */
    public static final int  GAME_LENGTH = 600;



    Image offScreenImage = null;

    Tank myTank = new Tank(50,50,true, Direction.STOP,this);//创建一个坦克的对象

    Wall w1 = new Wall(300,200,100,30,this);    //创建墙1的对象
    Wall w2 = new Wall(200,300,30,100,this);    //创建墙2的对象

    Blood b = new Blood();

    List<Explode> explodes = new ArrayList<Explode>();
    List<Missile> missiles = new ArrayList<Missile>();//创建多个子弹的对象的集合
    List<Tank> tanks = new ArrayList<Tank>();


    /**
     *
     * @param g  此变量为画笔
     */
    public void paint(Graphics g) {

        g.drawString("missiles counts:"+missiles.size(),50,100);        //在屏幕上显示子弹发射的数量
        g.drawString("explodes counts:"+explodes.size(),50,120);        //在屏幕上显示爆炸的的数量
        g.drawString("tanks    counts:"+tanks.size()   ,50,140);        //在屏幕上显示敌方坦克的数量
        g.drawString("tanks      life:"+myTank.getLife(),50,160);       //在屏幕上显示敌方坦克的数量

        //判断敌方坦克是否死了   如果死了  就重新生成坦克

        if(tanks.size() <= 0){
            for(int i = 0 ; i < Integer.parseInt(Propertiesmanager.getProperty("retankcount")); i++) {
                tanks.add(new Tank(50 + 40 *(i+1),50 , false, Direction.D,this));
            }
        }

        for(int i= 0 ; i < missiles.size();i++){
            Missile m = missiles.get(i);    //得到每个子弹的对象
            m.hitTanks(tanks);              //敌方坦克击中会消失
            m.hiTank(myTank);               //如果自己的坦克被敌方坦克击中  会消失
            m.hitWall(w1);                  //判断子弹是否与墙1相撞
            m.hitWall(w2);                  //判断子弹是否与墙2相撞
            m.draw(g);                      //将每个子弹对象画出来
        }

        for(int i = 0 ; i < explodes.size() ; i++){
            Explode e = explodes.get(i);
            e.draw(g);
        }

        for (int i = 0 ; i < tanks.size() ; i++){
            Tank t = tanks.get(i);
            t.collideWithWall(w1);//判断坦克是否与墙相撞
            t.collideWithWall(w2);//判断坦克是否与墙相撞
            t.collideWithTank(tanks);//判断坦克是否与坦克相撞
            t.draw(g);
        }

        myTank.eatBlood(b);//判断坦克是否将血块吃掉
        myTank.draw(g); //画出坦克的原型
        w1.draw(g);//画出墙1
        w2.draw(g);//画出墙2
        b.draw(g);  //画出血块

    }


    /**
     *使用双缓冲解决图像在显示的时候的不连贯的问题
     *使用一个虚拟的图片接收操作，然后一次性的复写到顶层图片(即显示给用户看的图片)
     * @param g  画笔变量
     */
    public void update(Graphics g) {

        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH,GAME_LENGTH);     //设置的虚拟图片必须与原始的图片的大小一致
        }

        Graphics goffScreenImage = offScreenImage.getGraphics();//新建虚拟图片的画笔
        Color c = goffScreenImage.getColor();   //获取虚拟画笔的颜色
        goffScreenImage.setColor(Color.GREEN);   //设置虚拟画笔的颜色为原始图片的颜色，实际的作用是清空上次的画面
        goffScreenImage.fillRect(0,0,GAME_WIDTH,GAME_LENGTH);  //(0,0)表示虚拟画笔的离左上角的位置  可以与原始图片的位置不一致，只要大小一致就可以
        goffScreenImage.setColor(c);            //将虚拟画笔的颜色改为原始的颜色
        paint(goffScreenImage);                 //将虚拟的图片复写到顶层图片上
        g.drawImage(offScreenImage,0,0,null);

    }

    /**
     * 游戏窗口框运行的函数
     */
    public void lanchFrame(){



        int inittankcount = Integer.parseInt(Propertiesmanager.getProperty("inittankcount"));

        for(int i = 0 ; i < inittankcount ; i++) {
            tanks.add(new Tank(50 + 40 *(i+1),50 , false, Direction.D,this));
        }

        this.setBackground(Color.GREEN);
        this.setLocation(300,100);  //定义游戏框离电脑左上角的距离  距离电脑左边：300   距离电脑上边：100
        this.setSize(GAME_WIDTH,GAME_LENGTH);      //定义游戏框的大小
        this.setTitle("TankWar");   //定义标题栏的名字为：TankWar

        //设置一个监听器  监听窗口关闭的操作
        this.addWindowListener(new WindowAdapter() {
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
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}
