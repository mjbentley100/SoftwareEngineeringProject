package window;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundRecorder {

    AudioFormat format;
    DataLine.Info info;



    public SoundRecorder() {
        this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

        this.info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Error, Not supported");
            System.exit(999);

        }
    }




    public void recordAudio() {
        try {

            final TargetDataLine dataLine = (TargetDataLine) AudioSystem.getLine(info);
            dataLine.open();


            //Recording starts
            dataLine.start();

            Thread thread = new Thread() {
                @Override public void run() {
                    AudioInputStream stream = new AudioInputStream(dataLine);
                    File audioFile = new File("current.wav");
                    try {
                        AudioSystem.write(stream, AudioFileFormat.Type.WAVE, audioFile);
                    }
                    catch(IOException io) {io.printStackTrace();}
                }
            };
            thread.start();
            thread.sleep(100);
            dataLine.stop();
            dataLine.close();

        }
        catch(LineUnavailableException i){
            i.printStackTrace();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }


    }



}
