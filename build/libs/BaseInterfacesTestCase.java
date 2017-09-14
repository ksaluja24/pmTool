import java.awt.*;
import javax.swing.*;
import com.thinking.machines.pm.tool.ui.*;
public class BaseInterfacesTestCase extends JFrame
{
private BaseInterfacesPanel panel;
private Container container;
public BaseInterfacesTestCase()
{
panel=new BaseInterfacesPanel();
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(panel);
setLocation(10,10);
setSize(300,400);
setVisible(true);
}
public static void main(String gg[])
{
BaseInterfacesTestCase tc=new BaseInterfacesTestCase();
}
}