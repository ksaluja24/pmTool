import java.awt.*;
import java.awt.event.*;
import com.thinking.machines.pm.tool.main.*;
public class SplashDemo {
    public SplashDemo() {
        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        for(int i=0; i<100; i++) {
            splash.update();
            try {
                Thread.sleep(20);
            }
            catch(InterruptedException e) {
            }
        }

        splash.close();

    }
 
    public static void main (String args[]) {

	SplashDemo sp=new SplashDemo();
    Main k=new Main();  



    }
}
