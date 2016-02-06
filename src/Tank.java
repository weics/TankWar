import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by WEI on 2016/2/6.
 */
public class Tank {
    int x; //设置坦克的位置距离左上角的水平距离
    int y; //设置坦克的位置距离左上角的垂直距离

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
    }

    //监听键盘的记录   来控制坦克的移动方向
    public void  keyPressed(KeyEvent e) {
        int key = e.getKeyCode();//获取键盘的按键的值
        switch(key) {
            case KeyEvent.VK_UP:  //键盘值   上
                y -= 5;
                break;
            case KeyEvent.VK_LEFT: //键盘值   左
                x -= 5;
                break;
            case KeyEvent.VK_RIGHT: //键盘值  右
                x += 5;
                break;
            case KeyEvent.VK_DOWN:  //键盘值  下
                y += 5;
                break;
        }
    }
}
