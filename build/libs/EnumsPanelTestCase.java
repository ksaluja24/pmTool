import java.awt.*;
import javax.swing.*;
import com.thinking.machines.pm.tool.ui.*;
public class EnumsPanelTestCase extends JFrame
{
private EnumsPanel panel;
private Container container;
public EnumsPanelTestCase()
{
panel=new EnumsPanel();
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(panel);
setLocation(10,10);
setSize(300,400);
setVisible(true);
}
public static void main(String gg[])
{
EnumsPanelTestCase tc=new EnumsPanelTestCase();
}
}