package sk.feri.awesomegame;

import com.badlogic.gdx.Input;

public class UsernameInputListener implements Input.TextInputListener {
    @Override
    public void input (String text) {
        if (text.matches("[A-Za-z0-9]+") && text.length() > 0 && text.length() < 7 && text != Settings.username){
            Settings.resetSettings();
            Settings.setUsername(text);
            Settings.save();
        }
    }

    @Override
    public void canceled () {
    }
}