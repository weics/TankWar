
import javax.xml.xpath.XPath;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.Image;

/**
 * 这个类是介绍坦克的各个方法和性质的类
 * Created by WEI on 2016/2/6.
 */
public class Tank {

    /**
     * 坦克沿X轴方向运动的速度
     */
    public static final int XSPEED = 5;

    /**
     * 坦克沿Y轴方向运动的速度
     */
    public static final int YSPEED = 5;

    /**
     * 坦克在终端显示的宽度
     */
    public static final int WIDTH = 20;

    /**
     * 坦克在终端显示的高度
     */
    public static final int HEIGHT = 20;

    /**
     * 坦克在终端显示位置距离游戏框的左上角的水平距离(X轴)
     */
    int x;

    /**
     * 坦克在终端显示位置距离游戏框的左上角的垂直距离(Y轴)
     */
    int y;

    /**
     * 当坦克撞到墙的时候，记录坦克当时的位置水平距离(X轴)
     */
    private int oldX;

    /**
     * 当坦克撞到墙的时候，记录坦克当时的位置水平距离(X轴)
     */
    private int oldY;

    /**
     * 初始化坦克的生命值为100
     */
    private int life = 100;

    //新建一个血量的对象
    private BloodBar bb = new BloodBar();

    //导入一个坦克游戏的控制台指针
    TankWarClient tc;

    //定义四个布尔变量来判断方向的确定  初始化全部为false
    private boolean bL=false;//方向 左
    private boolean bU=false;//方向 上
    private boolean bR=false;//方向 右
    private boolean bD=false;//方向 下

    /**
     *
     */
    private boolean tankbeGood ;    //控制坦克是否为友方坦克还是敌方坦克
    private boolean tankbeLive = true;  //控制坦克生存状态  即坦克是否被子弹击中死亡
    private static Random r = new Random();

    private int step = r.nextInt(12)+3;//使用随机函数产生随机数随机数为0~11  不包括12  并且加上3

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Map<String , Image> imgs = new HashMap<String, Image>();
    private static Image[] tankImgs = null;
    public boolean isTankBeGood() {
        return tankbeGood;
    }


    static {
        tankImgs = new Image[] {
            tk.getImage(Explode.class.getClassLoader().getResource("images/tankL.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/tankLU.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/tankU.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/tankRU.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/tankR.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/tankRD.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/tankD.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/tankLD.gif"))
        };

        imgs.put("L",tankImgs[0]);
        imgs.put("LU",tankImgs[1]);
        imgs.put("U",tankImgs[2]);
        imgs.put("RU",tankImgs[3]);
        imgs.put("R",tankImgs[4]);
        imgs.put("RD",tankImgs[5]);
        imgs.put("D",tankImgs[6]);
        imgs.put("LD",tankImgs[7]);

    }
    //坦克的生存状态的get方法
    public boolean istankbeLive() {
        return tankbeLive;
    }

    //坦克的生存状态的set方法
    public void settankbeLive(boolean tankbeLive) {
        this.tankbeLive = tankbeLive;
    }

    public int getLife() {
        return life;
    }

    public void  setLife(int life) {
        this.life = life ;
    }


    private Direction dir = Direction.STOP; //初始化坦克的方向为停止
    private Direction ptdir = Direction.D;  //设置炮筒的方向

    //构造函数
    public Tank(int x ,int y,boolean beGood){
        this.x = x;
        this.y = y;
        this.oldX = oldX;
        this.oldY = oldY;
        this.tankbeGood = beGood;
    }

    public Tank(int x ,int y,boolean beGood,Direction dir,TankWarClient tc){
        this(x,y,beGood);
        this.dir = dir ;
        this.tc = tc;
    }


    //使用画笔来创建坦克
    public void draw(Graphics g){
        if(!tankbeLive){
            if(!tankbeGood){
                tc.tanks.remove(this);  //如果坦克为敌方坦克则将坦克从坦克对象的集合中删除

            }
            return ;
        }


        if(isTankBeGood()){
            bb.draw(g);
        }


        switch(ptdir) {         //选择炮筒的方向  使得炮筒的方向与坦克的方向一致
            case L:
                g.drawImage(imgs.get("L"),x,y,null);
                break;
            case LU:
                g.drawImage(imgs.get("LU"),x,y,null);
                break;
            case U:
                g.drawImage(imgs.get("U"),x,y,null);
                break;
            case RU:
                g.drawImage(imgs.get("RU"),x,y,null);
                break;
            case R:
                g.drawImage(imgs.get("R"),x,y,null);
                break;
            case RD:
                g.drawImage(imgs.get("RD"),x,y,null);
                break;
            case D:
                g.drawImage(imgs.get("D"),x,y,null);
                break;
            case LD:
                g.drawImage(imgs.get("LD"),x,y,null);
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

        if(this.dir != Direction.STOP){
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
            Direction[] dirs = Direction.values();//让方向dir中的对象变为数组

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
            dir = Direction.L;
        } else if(bL && bU && !bR && !bD){
            dir = Direction.LU;
        } else if(!bL && bU && !bR && !bD){
            dir = Direction.U;
        } else if(!bL && bU && bR && !bD){
            dir = Direction.RU;
        } else if(!bL && !bU && bR && !bD){
            dir = Direction.R;
        } else if(!bL && !bU && bR && bD){
            dir = Direction.RD;
        } else if(!bL && !bU && !bR && bD){
            dir = Direction.D;
        } else if(bL && !bU && !bR && bD){
            dir = Direction.LD;
        } else if(!bL && !bU && !bR && !bD){
            dir = Direction.STOP;
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
            case KeyEvent.VK_F2://当按下F2的时候 就重启
                if(!this.tankbeLive){
                    this.settankbeLive(true);
                    this.setLife(100);
                }
                break;
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
            case KeyEvent.VK_A:         //超级炮弹的按键
                superfire();
                break;
        }
        locationdir();                  //根据键盘记录的code码，获取坦克的方向
    }

    //子弹的发射
    public Missile fire(){
        if(!tankbeLive){
            return null;
        }
        int x= this.x + Tank.WIDTH/2 - Missile.WIDTH/2;//使子弹射出的位置是坦克的中心
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        Missile m = new Missile(x,y,ptdir,tankbeGood,this.tc);
        tc.missiles.add(m);
        return m;
    }


    public Missile fire(Direction dir ){
        if(!tankbeLive){
            return null;
        }
        int x= this.x + Tank.WIDTH/2 - Missile.WIDTH/2;//使子弹射出的位置是坦克的中心
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        Missile m = new Missile(x,y,dir,tankbeGood,this.tc);
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


    //判断坦克是否与坦克相撞的函数
    public boolean collideWithTank(java.util.List<Tank> tanks) {
        for(int i=0 ; i < tanks.size() ; i++){
            Tank t = tanks.get(i);
            if(this != t){
                if(this.tankbeLive && t.istankbeLive() && this.getRect().intersects(t.getRect())){
                    this.stay();
                    return true;
                }
            }
        }
        return false;
    }


    //超级开火的功能
    public void superfire (){
        Direction[] dirs = Direction.values();
        for(int i = 0 ; i < 8 ; i++){
            fire(dirs[i]);
        }
    }


    //创建一个血量的内部类
    private class BloodBar {
        public void draw (Graphics g){
            Color c = g.getColor();
            g.setColor(Color.BLACK);//设置血量的颜色
            g.drawRect(x,y-10,WIDTH,10);
            int w = WIDTH * life /100; //显示血量的变化
            g.fillRect(x,y-10,w,10);
            g.setColor(c);
        }
    }

    //坦克吃掉血块的函数
    public boolean eatBlood(Blood b){
        if( this.tankbeLive && b.isBloodLive() && this.getRect().intersects(b.getRect())){
            this.setLife(100);
            b.setBloodLive(false);
            return true;
        }
        return false;
    }


}
