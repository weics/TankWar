import javax.xml.xpath.XPath;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

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
    private int oldX;//记录坦克撞到墙是的原始位置X轴
    private int oldY;//记录坦克撞到墙是的原始位置Y轴

    TankWarClient tc;

    //定义四个布尔变量来判断方向的确定  初始化全部为false
    private boolean bL=false;
    private boolean bU=false;
    private boolean bR=false;
    private boolean bD=false;

    public boolean isTankBeGood() {
        return tankbeGood;
    }

    private boolean tankbeGood ;    //控制坦克是否为友方坦克还是敌方坦克
    private boolean beLive = true;  //控制坦克生存状态  即坦克是否被子弹击中死亡
    private static Random r = new Random();

    private int step = r.nextInt(12)+3;//使用随机函数产生随机数随机数为0~11  不包括12  并且加上3

    //坦克的生存状态的get方法
    public boolean isBeLive() {
        return beLive;
    }

    //坦克的生存状态的set方法
    public void setBeLive(boolean beLive) {
        this.beLive = beLive;
    }



    enum Dircetion  {L,LU,U,RU,R,RD,D,LD,STOP}

    private Dircetion dir = Dircetion.STOP; //初始化坦克的方向为停止
    private Dircetion ptdir = Dircetion.D;  //设置炮筒的方向

    //构造函数
    public Tank(int x ,int y,boolean beGood){
        this.x = x;
        this.y = y;
        this.oldX = oldX;
        this.oldY = oldY;
        this.tankbeGood = beGood;
    }

    public Tank(int x ,int y,boolean beGood,Dircetion dir,TankWarClient tc){
        this(x,y,beGood);
        this.dir = dir ;
        this.tc = tc;
    }


    //使用画笔来创建坦克
    public void draw(Graphics g){
        if(!beLive){
            if(!tankbeGood){
                tc.tanks.remove(this);  //如果坦克为敌方坦克则将坦克从坦克对象的集合中删除

            }
            return ;
        }

        Color c = g.getColor();
        if(tankbeGood){
            g.setColor(Color.RED);      //设置友方坦克的颜色为红色
        } else {
            g.setColor(Color.BLUE);     //设置敌方的坦克颜色为红色
        }


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

        //记录原始的位置
        this.oldX = x;
        this.oldY = y;

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

        if(this.dir != Dircetion.STOP){
            this.ptdir = this.dir;
        }

        //使坦克在遇到游戏的边框的时候归位  使其不能越过边框
        if(x < 0 ){
            x = 0;
        }

        if(y < 25 ){
            y = 25;
        }

        if(x + Tank.WIDTH > TankWarClient.GAME_WIDTH ){
            x = TankWarClient.GAME_WIDTH - Tank.WIDTH;
        }

        if(y + Tank.HEIGHT > TankWarClient.GAME_LENGTH){
            y = TankWarClient.GAME_LENGTH - Tank.HEIGHT;
        }


        if(!tankbeGood){
            Dircetion[] dirs = Dircetion.values();//让方向dir中的对象变为数组

            //每次11+3步之后就重新改变方向
            if(step == 0){
                step = r.nextInt(12) + 3;
                int rn = r.nextInt(dirs.length);
                dir = dirs[rn];
            }
            step--;

            //使坦克的发射子弹的速度不是十分的快  是发射子弹的概率为38/40
            if(r.nextInt(40) > 38 ) {
                this.fire();
            }

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

    //监听按键的松开的的函数
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();//获取键盘的按键的值
        switch(key) {
            case KeyEvent.VK_CONTROL:   //当按下的control键松开的时候才开炮
                fire();
                break;
            case KeyEvent.VK_LEFT:      //键盘值   左
                bL = false;
                break;
            case KeyEvent.VK_UP:        //键盘值   上
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:     //键盘值  右
                bR = false;
                break;
            case KeyEvent.VK_DOWN:      //键盘值  下
                bD = false;
                break;
        }
        locationdir();                  //根据键盘记录的code码，获取坦克的方向
    }

    //子弹的发射
    public Missile fire(){
        if(!beLive){
            return null;
        }
        int x= this.x + Tank.WIDTH/2 - Missile.WIDTH/2;//使子弹射出的位置是坦克的中心
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        Missile m = new Missile(x,y,ptdir,tankbeGood,this.tc);
        tc.missiles.add(m);
        return m;
    }

    //定义坦克的getRect方法
    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

    //让坦克保持原来的位置
    public void stay(){
        this.x = oldX;
        this.y = oldY;
    }

    public void  collideWithWall(Wall w) {
        if(this.getRect().intersects(w.getRect())){
            this.stay();//当坦克撞到墙的时候就让坦克获取原来的位置  直到坦克的在一个画图的周期中坦克的位置离开了墙
        }
    }


}
