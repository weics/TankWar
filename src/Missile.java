import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Direction dir ;   //获取坦克的方向  以便是子弹的与坦克的方向一致
    private TankWarClient tc;
    private boolean good;//判断子弹的好坏

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Map<String , Image> imgs = new HashMap<String, Image>();
    private static Image[] missileImgs = null;

    static {
        missileImgs = new Image[] {
                tk.getImage(Explode.class.getClassLoader().getResource("images/missileL.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("images/missileLU.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("images/missileU.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("images/missileRU.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("images/missileR.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("images/missileRD.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("images/missileD.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("images/missileLD.gif"))
        };

        imgs.put("L",missileImgs[0]);
        imgs.put("LU",missileImgs[1]);
        imgs.put("U",missileImgs[2]);
        imgs.put("RU",missileImgs[3]);
        imgs.put("R",missileImgs[4]);
        imgs.put("RD",missileImgs[5]);
        imgs.put("D",missileImgs[6]);
        imgs.put("LD",missileImgs[7]);

    }


    public Missile(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y, Direction dir , boolean tankbeGood , TankWarClient tc){
        this(x,y,dir);
        this.good = tankbeGood;
        this.tc = tc;
    }


    //子弹的生存状态的get函数
    public boolean ismissileslive (){
        return missileslive;
    }

    //用画笔画出坦克
    public void draw(Graphics g){

        if(!missileslive){
            tc.missiles.remove(this);
            return ;
        }

        switch(dir) {
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
        move();
    }

    //定义子弹的getRect方法
    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }

    //判断坦克是否被子弹击中
    public boolean hiTank(Tank t){
        //使用getRect方法判断子弹和坦克是否在同一个矩形方框中
        if(this.missileslive && this.getRect().intersects(t.getRect()) && t.isBeLive() && this.good != t.isTankBeGood() ){
            if(t.isTankBeGood()){
                t.setLife(t.getLife() - 20);//当坦克是好坦克的时候  每次被击中就血量减少20
                if(t.getLife() <= 0){
                    t.setBeLive(false);//如果血量减少到0 坦克就消失
                }
            } else {
                t.setBeLive(false);//如果坏坦克被子弹击中  则设置坦克生存状态为false  则坦克的画出动作将不会执行
            }

            this.missileslive = false;//坦克被子弹击中  设置击中坦克的子弹的消失
            Explode e = new Explode(x,y,tc);
            tc.explodes.add(e);
            return true;
        }
        return false;
    }

    public boolean hitWall(Wall w){
        if(this.getRect().intersects(w.getRect())){
            this.missileslive = false;
            return true;
        }
        return false;
    }

    public boolean hitTanks(List<Tank> tanks) {
        for(int i = 0 ; i < tanks.size(); i++){
            if(hiTank(tanks.get(i))){
                return true;
            }
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
