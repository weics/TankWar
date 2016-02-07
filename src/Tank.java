import javax.xml.xpath.XPath;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by WEI on 2016/2/6.
 */
public class Tank {


    public static final int XSPEED = 5; //x轴方向的速度
    public static final int YSPEED = 5; //x轴方向的速度
    int x; //设置坦克的位置距离左上角的水平距离
    int y; //设置坦克的位置距离左上角的垂直距离

    //定义四个布尔变量来判断方向的确定  初始化全部为false
    private boolean bL=false;
    private boolean bU=false;
    private boolean bR=false;
    private boolean bD=false;

    enum Dircetion  {L,LU,U,RU,R,RD,D,LD,STOP}

    Dircetion dir = Dircetion.STOP;//初始化方向为停止

    //构造函数
    public Tank(int x ,int y){
        this.x = x;
        this.y = y;
    }

    //使用画笔来创建坦克
    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.RED);      //设置坦克的颜色为红色
        g.fillOval(x,y,20,20);    //   坦克的大小为半径20
        g.setColor(c);

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
    }

    //监听键盘的记录   来控制坦克的移动方向
    public void  keyPressed(KeyEvent e) {
        int key = e.getKeyCode();//获取键盘的按键的值
        switch(key) {
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


}
