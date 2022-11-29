package com.minorproject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    String[] music = new String[30];

    public Sound() {
        music[0] = "res/sound/BlueBoyAdventure.wav";
        music[1] = "res/sound/coin.wav";
        music[2] = "res/sound/powerup.wav";
        music[3] = "res/sound/unlock.wav";
        music[4] = "res/sound/fanfare.wav";
    }
    public void setFile(int i) {
        try {
            File musicpath = new File(music[i]);
                AudioInputStream ais = AudioSystem.getAudioInputStream(musicpath);
                clip = AudioSystem.getClip();
                clip.open(ais);
        }catch (Exception e ) {
//            e.printStackTrace();
        }
    }

    public void play() {

        clip.start();

    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){

        clip.stop();
    }
}