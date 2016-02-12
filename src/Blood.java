import java.awt.*;

/**
 * Created by WEI on 2016/2/12.
 */
public class Blood {

    int x;
    int y;
    int w;//血块的宽度
    int h;//血块的高度

    TankWarClient tc;

    private int step = 0;

    private boolean bloodLive = true;//设置血块的生存状态

    //使用二位数组存储血块移动的位置
    int[][] pos = {
            {300,200},
            {400,250},
            {600,200},
            {350,300},
            {250,360}
    };


    public boolean isBloodLive() {
        return bloodLive;
    }

    public void setBloodLive(boolean bloodLive) {
        this.bloodLive = bloodLive;
    }

    //构造函数
    public Blood() {
        this.x = pos[0][0];
        this.y = pos[0][1];
        this.w = 18;
        this.h = 18;
    }

    public void draw (Graphics g){
        if(!isBloodLive()) return;//如果血块的被吃掉了  则血块将不存在

        Color c = g.getColor();
        g.setColor(Color.MAGENTA);

        g.fillRect(x,y,w,h);
        g.setColor(c);
        move();//修改血块的下一个位置
    }


    public void move(){
        step++;
        if(step == pos.length){
            step = 0;
        }
        this.x = pos[step][0];
        this.y = pos[step][1];
    }

    public Rectangle getRect(){

        return new Rectangle(x,y,w,h);
    }
}


