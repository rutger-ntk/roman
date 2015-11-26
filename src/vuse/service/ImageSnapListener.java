package vuse.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;

public class ImageSnapListener extends MediaListenerAdapter {

	// Interval between capturing frames/screenshots
	public static final double SECONDS_BETWEEN_FRAMES = 1;

	// The location for the screenshots to be saved and named
	private static final String outputFilePrefix = "/home/rnikolov/LunaWorkProjects/VUSETool/WebContent/screenshot-";

	// The video stream index, used to ensure we display 1 frame from one and
	// only one video stream from the media container.
	private static int mVideoStreamIndex = -1;

	// Time of last frame write
	private static long mLastPtsWrite = Global.NO_PTS;

	public static final long MICRO_SECONDS_BETWEEN_FRAMES = (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);

	// List of all captured screenshots
	private static List<String> screenshotsList = new ArrayList<String>();

	/**
	 * Handles video events only
	 */
	public void onVideoPicture(IVideoPictureEvent event) {		
		if (event.getStreamIndex() != mVideoStreamIndex) {
			// if the selected video stream id is not yet set, go ahead an
			// select this lucky video stream
			if (mVideoStreamIndex == -1)
				mVideoStreamIndex = event.getStreamIndex();
			// no need to show frames from this video stream
			else
				return;
		}
		
		// if uninitialized, back date mLastPtsWrite to get the very first frame
		if (mLastPtsWrite == Global.NO_PTS)
			mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;

		// if it's time to write the next frame
		if (event.getTimeStamp() - mLastPtsWrite >= MICRO_SECONDS_BETWEEN_FRAMES) {

			String outputFilename = dumpImageToFile(event.getImage());

			// extracting the frame image name from the path location
			int index = outputFilename.lastIndexOf("/");

			// adds all screenshots to a list for later preview
			screenshotsList.add(outputFilename.substring(index + 1));

			// indicate file written
			double seconds = ((double) event.getTimeStamp())
					/ Global.DEFAULT_PTS_PER_SECOND;
			System.out.printf("at elapsed time of %6.3f seconds wrote: %s\n",
					seconds, outputFilename);

			// update last write time
			mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
		}

	}

	/**
	 * Saves extracted screenshots to .png files
	 * 
	 * @param image
	 * @return String that represents the saved files' path/location
	 */
	private String dumpImageToFile(BufferedImage image) {
		try {
			String outputFilename = outputFilePrefix + System.currentTimeMillis()
					+ ".png";

			ImageIO.write(image, "png", new File(outputFilename));
			return outputFilename;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get screenshots method
	 * 
	 * @return
	 */
	public static List<String> getScreenshotsList() {
		return screenshotsList;
	}

	/**
	 * Set screenshots method 
	 */
	public static void setScreenshotsList() {
		screenshotsList = new ArrayList<String>();
	}
	
	/**
	 * Set the mLastPtsWrite for a new file
	 */
	public static void setmLastPtsWrite() {
		mLastPtsWrite = Global.NO_PTS;
	}

}