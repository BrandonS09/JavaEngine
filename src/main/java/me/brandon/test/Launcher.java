package me.brandon.test;

import me.brandon.core.EngineManager;
import me.brandon.core.WindowManager;
import me.brandon.core.util.Constants;
import org.lwjgl.Version;

public class Launcher {
    private static WindowManager window;
    private static TestGame game;
    private static EngineManager engine;

    public static TestGame getGame() {
        return game;
    }

    public static void main(String[] args){
        System.out.println(Version.getVersion());
        window = new WindowManager(Constants.TITLE, 1600, 900, false);
        engine = new EngineManager();
        game = new TestGame();
        try{
            engine.start();
        } catch(Exception e){
            e.printStackTrace();
        }

        while(!window.windowShouldClose()){
            window.update();
        }

        window.cleanup();
    }

    public static WindowManager getWindow() {
        return window;
    }
}
