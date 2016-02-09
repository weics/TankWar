import java.awt.*;

/**
 * Created by WEI on 2016/2/7.
 */
public class Missile {

    public static final int XSPEED = 10;//子弹的x轴方向的速度
    public static final int YSPEED = 10;//子弹的y轴方向的速度
    public static final int WIDTH =10;  //子弹的宽度
    public static final int HEIGHT = 10;//子弹的高度
    int x ;//子弹的水平位置
    int y ;//子弹的垂直位置

    private boolean missileslive = true;//定义一枚炮弹是否生存着
    Tank.Dircetion dir ;   //获取坦克的方向  以便是子弹的与坦克的方向一致
    TankWarClient tc;

    public Missile(int x, int y, Tank.Dircetion dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y, Tank.Dircetion dir ,TankWarClient tc){
        this(x,y,dir);
        this.tc = tc;
    }

    public boolean ismissileslive (){
        return missileslive;
    }

    //用画笔画出坦克
    public void draw(Graphics g){

        if(!missileslive){
            tc.missiles.remove(this);
            return ;
        }

        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x,y,WIDTH,HEIGHT);
        g.setColor(c);
        move();
    }

    //定义子弹的getRect方法
    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

    //判断坦克是否被子弹击中
    public boolean hiTank(Tank t){
        //使用getRect方法判断子弹和坦克是否在同一个矩形方框中
        if(this.getRect().intersects(t.getRect()) && t.isBeLive()){
            t.setBeLive(false);//如果坦克被子弹击中  则设置坦克生存状态为false  则坦克的画出动作将不会执行
            this.missileslive = false;//坦克被子弹击中  设置击中坦克的子弹的消失
            Explode e = new Explode(x,y,tc);
            tc.explodes.add(e);
            return true;
        }
        return false;
    }


    //设置子弹移动的方向
    public void move(){
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
        }

        if (x < 0 || y < 0 || x > TankWarClient.GAME_WIDTH || y > TankWarClient.GAME_LENGTH){
            missileslive = false;
            tc.missiles.remove(this);
        }
    }

}
