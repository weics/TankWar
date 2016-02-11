import java.awt.*;

/**
 * Created by WEI on 2016/2/11.
 */
public class Wall {
    int x ;//墙的位置X轴
    int y ;//墙的位置Y轴
    int w ;//墙的宽度
    int h ;//墙的高度

    TankWarClient tc ;

    //构造函数
    public Wall(int x, int y, int w, int h, TankWarClient tc) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tc = tc;
    }

    public void draw (Graphics g){
        g.fillRect(x,y,w,h);//画出墙的画笔
    }

    public Rectangle getRect(){
        return new Rectangle(x,y,w,h);//获取墙与子弹和坦克的碰撞检测
    }
}
