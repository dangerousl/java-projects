package spacetiles.gameEngine;

import java.io.*;
import javax.sound.sampled.*;


public class SoundEngine extends Thread
{
    File soundFile;
    
    public SoundEngine(File file)
    {
        soundFile = file;
        //FloatControl.Type.VOLUME
        //FloatControl.Type.PAN
    }

    public void run()
    {
        playSound(soundFile);
    }
    
public void playSound(File file)
{
   while(true){
   try
   {
       // Get an AudioInputStream from the
       // specified file (which must be a
       // valid audio file, otherwise an
       // UnsupportedAudioFileException
       // object is thrown).

       AudioInputStream ais = AudioSystem.getAudioInputStream (file);

       // Get the AudioFormat for the sound data
       // in the AudioInputStream.
       AudioFormat af = ais.getFormat ();
        //System.out.println("af = " + af);
   
              
       // Create a DataLine.Info object that
       // describes the format for data to be
       // output over a Line.

       DataLine.Info dli = new DataLine.Info (SourceDataLine.class, af);
                          
       //System.out.println("DataLine.Info = " + dli);

       // Do any installed Mixers support the
       // Line? If not, we cannot play a sound
       // file.

       if (AudioSystem.isLineSupported (dli))
       {
           // Obtain matching Line as a
           // SourceDataLine (a Line to which
           // sound data may be written).

           SourceDataLine sdl = (SourceDataLine)AudioSystem.getLine(dli);
           ///System.out.println("sdl = " + sdl.getFormat());

           // Acquire system resources and make
           // the SourceDataLine operational.

                   
           sdl.open(af);

           // Initiate output over the
           // SourceDataLine.

           sdl.start();

           // Size and create buffer for holding
           // bytes read and written.
/*
           int frameSize = af.getFrameSize ();
           int bufferLenInFrames =
                         sdl.getBufferSize () / 8;
           int bufferLenInBytes =
                    bufferLenInFrames * frameSize;
 */
            int bufferLenInBytes = (int) af.getSampleRate() * af.getFrameSize();
            byte [] buffer = new byte [bufferLenInBytes];

           // Read data from the AudioInputStream
           // into the buffer and then copy that
           // buffer's contents to the
           // SourceDataLine.
           
           int numBytesRead;
           while ((numBytesRead = ais.read (buffer, 0, buffer.length)) != -1) {
              if (numBytesRead > 0)
                sdl.write (buffer, 0, numBytesRead);
            }
            
            sdl.drain(); //clear buffer
            sdl.close(); //close line
       }
   }
   catch (LineUnavailableException e)
   {
   }
   catch (UnsupportedAudioFileException e)
   {
   }
   catch (IOException e)
   {
   }
   }
}

}
