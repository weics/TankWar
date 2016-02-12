import java.awt.*;

/**
 * Created by WEI on 2016/2/9.
 */
public class Explode {

    int x;      //定义爆炸产生的位置
    int y;
    private boolean live = true ;//定义爆炸的生存状态
    private TankWarClient tc ;
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private static Image[] imgs = {
            tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
    };



    int step = 0;//定义爆炸产生阶段的步骤

    private static boolean init = false;

    public Explode(int x , int y , TankWarClient tc){
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw (Graphics g){
        if(false == init) {
            for(int i = 0 ; i < imgs.length ;i++){
                g.drawImage(imgs[i],x,y,null);
                init = true;
            }
        }


        //判断爆炸的生存状态
        if(!live){
            tc.explodes.remove(this);
            return;//如果爆炸的生存状态为false  则爆炸不产生
        }

        //爆炸的图像变化的从大到小变化  让爆炸到达数组的最后就不产生爆炸图像
        if(step == imgs.length){
            live =false;
            step = 0;
            return;
        }

        g.drawImage(imgs[step],x,y,null);
        step++;
    }
}
