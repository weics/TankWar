import javax.xml.xpath.XPath;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by WEI on 2016/2/6.
 */
public class Tank {


    public static final int XSPEED = 5; //x轴方向的速度
    public static final int YSPEED = 5; //x轴方向的速度
    public static final int WIDTH = 20; //坦克的宽度
    public static final int HEIGHT = 20;//坦克的高度
    int x; //设置坦克的位置距离左上角的水平距离
    int y; //设置坦克的位置距离左上角的垂直距离

    TankWarClient tc;

    //定义四个布尔变量来判断方向的确定  初始化全部为false
    private boolean bL=false;
    private boolean bU=false;
    private boolean bR=false;
    private boolean bD=false;

    enum Dircetion  {L,LU,U,RU,R,RD,D,LD,STOP}

    private Dircetion dir = Dircetion.STOP;//初始化坦克的方向为停止
    private Dircetion ptdir = Dircetion.D;//设置炮筒的方向

    //构造函数
    public Tank(int x ,int y){
        this.x = x;
        this.y = y;
    }

    public Tank(int x ,int y,TankWarClient tc){
        this(x,y);
        this.tc = tc;
    }


    //使用画笔来创建坦克
    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.RED);      //设置坦克的颜色为红色
        g.fillOval(x,y,WIDTH,HEIGHT);
        g.setColor(c);
        switch(ptdir) {         //选择炮筒的方向  使得炮筒的方向与坦克的方向一致
            case L:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT/2);
                break;
            case LU:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y);
                break;
            case U:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y);
                break;
            case RU:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y);
                break;
            case R:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT/2);
                break;
            case RD:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT);
                break;
            case D:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y + Tank.HEIGHT);
                break;
            case LD:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x , y + Tank.HEIGHT);
                break;
        }
        move();     //坦克移动的函数
    }

    //坦克移动的八个方向   每个方向对应的距离变化
    public void  move(){
        switch(dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }
    }

    //从键盘的记录上获取坦克该往哪个方向变化
    public void locationdir(){
        if(bL && !bU && !bR && !bD){
            dir = Dircetion.L;
        } else if(bL && bU && !bR && !bD){
            dir = Dircetion.LU;
        } else if(!bL && bU && !bR && !bD){
            dir = Dircetion.U;
        } else if(!bL && bU && bR && !bD){
            dir = Dircetion.RU;
        } else if(!bL && !bU && bR && !bD){
            dir = Dircetion.R;
        } else if(!bL && !bU && bR && bD){
            dir = Dircetion.RD;
        } else if(!bL && !bU && !bR && bD){
            dir = Dircetion.D;
        } else if(bL && !bU && !bR && bD){
            dir = Dircetion.LD;
        } else if(!bL && !bU && !bR && !bD){
            dir = Dircetion.STOP;
        }

        if(this.dir != Dircetion.STOP){
            this.ptdir = this.dir;
        }
    }

    //监听键盘的记录   来控制坦克的移动方向
    public void  keyPressed(KeyEvent e) {
        int key = e.getKeyCode();//获取键盘的按键的值
        switch(key) {
            case KeyEvent.VK_CONTROL:
               tc.m = fire();
                break;
            case KeyEvent.VK_LEFT: //键盘值   左
                bL = true;
                break;
            case KeyEvent.VK_UP:  //键盘值   上
                bU = true;
                break;
            case KeyEvent.VK_RIGHT: //键盘值  右
                bR = true;
                break;
            case KeyEvent.VK_DOWN:  //键盘值  下
                bD = true;
                break;
        }
        locationdir();//根据键盘记录的code码，获取坦克的方向
    }


    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();//获取键盘的按键的值
        switch(key) {
            case KeyEvent.VK_LEFT: //键盘值   左
                bL = false;
                break;
            case KeyEvent.VK_UP:  //键盘值   上
                bU = false;
                break;
            case KeyEvent.VK_RIGHT: //键盘值  右
                bR = false;
                break;
            case KeyEvent.VK_DOWN:  //键盘值  下
                bD = false;
                break;
        }
        locationdir();//根据键盘记录的code码，获取坦克的方向
    }

    //子弹的发射
    public Missile fire(){
        int x= this.x + Tank.WIDTH/2 - Missile.WIDTH/2;//使子弹射出的位置是坦克的中心
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        Missile m = new Missile(x,y,ptdir);
        return m;
    }

}
