package pt.technic.apps.minesfinder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Massadas
 */
public class SettingTable implements Serializable {

   private int menuWidth;
   private int menuHeight;
   private int inGameWidth;
   private int inGameHeight;
   

    private transient ArrayList<SettingTableListener> listeners;

    public SettingTable() {
        menuWidth = 600;
        menuHeight = 450;
        inGameWidth = 800;
        inGameHeight = 600;
        listeners = new ArrayList<>();
    }

    public int getMenuWidth() {
       return menuWidth;
    }
    
    public int getMenuHeight() {
       return menuHeight;
    }
    
    public int getInGameWidth() {
       return inGameWidth;
    }
    
    public int getInGameHeight() {
       return inGameHeight;
    }

    public void setMenuWidthHeight(int menuWidth, int menuHeight) {
        this.menuWidth = menuWidth;
        this.menuHeight = menuHeight;
        notifySettingTableUpdated();
    }
    
    public void setInGameWidthHeight(int inGameWidth, int inGameHeight) {
        this.inGameWidth = inGameWidth;
        this.inGameHeight = inGameHeight;
        notifySettingTableUpdated();
    }

    public void addSettingTableListener(SettingTableListener list) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(list);
    }

    public void removeSettingTableListener(SettingTableListener list) {
        if (listeners != null) {
            listeners.remove(list);
        }
    }

    private void notifySettingTableUpdated() {
        if (listeners != null) {
            for (SettingTableListener list : listeners) {
                list.settingUpdated(this);
            }
        }
    }
}