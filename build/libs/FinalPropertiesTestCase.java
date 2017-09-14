import java.awt.*;
import javax.swing.*;
import com.thinking.machines.pm.tool.ui.*;
public class FinalPropertiesTestCase extends JFrame
{
private FinalPropertiesPanel panel;
private Container container;
public FinalPropertiesTestCase()
{
panel=new FinalPropertiesPanel();
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(panel);
setLocation(10,10);
setSize(300,400);
setVisible(true);
}
public static void main(String gg[])
{
FinalPropertiesTestCase fp=new FinalPropertiesTestCase();
}
}
