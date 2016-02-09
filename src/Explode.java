import java.awt.*;

/**
 * Created by WEI on 2016/2/9.
 */
public class Explode {

    int x;      //定义爆炸产生的位置
    int y;
    private boolean live = true ;//定义爆炸的生存状态
    private TankWarClient tc ;

    int[] diameter = {10,15,25,35,45,55,65,40,10,0};//爆炸变化过程的圆框大小
    int step = 0;//定义爆炸产生阶段的步骤

    public Explode(int x , int y , TankWarClient tc){
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw (Graphics g){
        //判断爆炸的生存状态
        if(!live){
            tc.explodes.remove(this);
            return;//如果爆炸的生存状态为false  则爆炸不产生
        }

        //爆炸的图像变化的从大到小变化  让爆炸到达数组的最后就不产生爆炸图像
        if(step == diameter.length){
            live =false;
            step = 0;
            return;
        }

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillOval(x,y,diameter[step],diameter[step]);
        g.setColor(c);

        step++;
    }
}
