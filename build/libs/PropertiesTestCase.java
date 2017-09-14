import java.awt.*;
import javax.swing.*;
import com.thinking.machines.pm.tool.ui.*;
public class PropertiesTestCase extends JFrame
{
private PropertiesPanel panel;
private Container container;
public PropertiesTestCase()
{
panel=new PropertiesPanel();
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(panel);
setLocation(10,10);
setSize(300,400);
setVisible(true);
}
public static void main(String gg[])
{
PropertiesTestCase fp=new PropertiesTestCase();
}
}