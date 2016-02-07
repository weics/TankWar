import java.awt.*;

/**
 * Created by WEI on 2016/2/7.
 */
public class Missile {

    public static final int XSPEED = 10;//子弹的x轴方向的速度
    public static final int YSPEED = 10;//子弹的y轴方向的速度

    int x ;
    int y ;
    Tank.Dircetion dir ;   //获取坦克的方向  以便是子弹的与坦克的方向一致

    public Missile(int x, int y, Tank.Dircetion dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    //用画笔画出坦克
    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x,y,10,10);
        g.setColor(c);
        move();
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
    }

}
