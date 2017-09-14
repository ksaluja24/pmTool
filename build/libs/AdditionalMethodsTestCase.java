import java.awt.*;
import javax.swing.*;
import com.thinking.machines.pm.tool.ui.*;
public class AdditionalMethodsTestCase extends JFrame
{
private AdditionalMethodsPanel panel;
private Container container;
public AdditionalMethodsTestCase()
{
panel=new AdditionalMethodsPanel();
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(panel);
setLocation(10,10);
setSize(300,400);
setVisible(true);
}
public static void main(String gg[])
{
AdditionalMethodsTestCase tc=new AdditionalMethodsTestCase();
}
}