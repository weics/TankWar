import java.io.IOException;
import java.util.Properties;

/**
 * Created by WEI on 2016/2/12.
 */
public class Propertiesmanager {

    /**
     * 此函数将构造函数私有化，使得其他函数不能对其进行创建
     */
    private Propertiesmanager(){}

    public static String getProperty(String key){

        Properties props = new Properties();

        try {
            props.load(Propertiesmanager.class.getClassLoader().getResourceAsStream("config/tank.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }



        return props.getProperty(key);
    }

}

