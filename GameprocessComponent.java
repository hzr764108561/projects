package xyz.chengzi.aeroplanechess.view;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameprocessComponent extends JComponent {

    private JComboBox<String> Process;

    public GameprocessComponent() {
        setLayout(null);
        setSize(1000, 25);
        Process = new JComboBox<>();
        List<String> pathname = getFileList("C://Users//Ld//Desktop//project1//src//xyz//chengzi//aeroplanechess//Gameprocess");
        for (int i = 0; i < pathname.size(); i++) {
            Process.addItem(pathname.get(i));
        }
        Process.setLocation(370, 0);
        Process.setSize(1000, 25);
        Process.setVisible(true);
        add(Process);
    }

    public List getFileList(String path) {
        List list = new ArrayList();
        try {
            File file = new File(path);
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                list.add(path+"\\"+filelist[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Object getSelectedprocess(){
        return Process.getSelectedItem();
    }
}
