package cct720.control;

import javax.media.j3d.ImageComponent2D;

import com.sun.j3d.utils.image.TextureLoader;

public class AnimacaoControl {
	public ImageComponent2D[] loadImages(String fNms, int numIms) {
		String filename;
		TextureLoader loader;
		ImageComponent2D[] ims = new ImageComponent2D[numIms];
		System.out.println("Loading " + numIms + " textures from " + fNms);
		for (int i = 0; i < numIms; i++) {
			filename = new String(fNms + i + ".gif");
			System.out.println("Loading: " + filename);
			loader = new TextureLoader(filename, null);
			ims[i] = loader.getImage();
			if (ims[i] == null)
				System.out.println("Load failed for texture in : " + filename);
		}
		return ims;
	} // end of loadImages()
	
}
