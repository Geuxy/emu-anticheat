package me.geuxy.emu;

import org.bukkit.plugin.java.JavaPlugin;

public class EmuPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Emu.INSTANCE.init(this);
        this.saveDefaultConfig();
    }

    /*@Override
    public void saveDefaultConfig() {
        super.saveDefaultConfig();
    }*/

}
