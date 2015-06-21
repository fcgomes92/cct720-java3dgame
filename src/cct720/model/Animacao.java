package cct720.model;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

public class Animacao extends Shape3D {

	private static final int DELAY = 100; // ms delay between frames
	private static final int NUM_VERTS = 4;

	private TransformGroup tg = new TransformGroup();
	private BranchGroup branchGroup = new BranchGroup();

	private ImageComponent2D[] ims;
	private Texture2D texture;

	public Animacao(Point3f center, float screenSize, ImageComponent2D[] ims,
			int dir) {
		this.ims = ims;
		switch (dir) {
		case 1:
			createGeometry1(center, screenSize);
			break;
		case 2:
			createGeometry2(center, screenSize);
			break;
		case 3:
			createGeometry3(center, screenSize);
			break;
			
		case 4:
			createGeometry4(center, screenSize);
			break;
		}
		createAppearance();
	} // end of ImageCsSeries()

	private void createGeometry1(Point3f c, float sz) {
		QuadArray plane = new QuadArray(NUM_VERTS, GeometryArray.COORDINATES
				| GeometryArray.TEXTURE_COORDINATE_2);

		// the screen is centered at the center, size screenSize, facing +z axis
		// Point3f p1 = new Point3f(c.x - sz / 2, c.y - sz / 2, c.z);
		// Point3f p2 = new Point3f(c.x + sz / 2, c.y - sz / 2, c.z);
		// Point3f p3 = new Point3f(c.x + sz / 2, c.y + sz / 2, c.z);
		// Point3f p4 = new Point3f(c.x - sz / 2, c.y + sz / 2, c.z);
		//
		// Point3f p1 = new Point3f(c.x, c.y - sz / 2, c.z - sz / 2);
		// Point3f p2 = new Point3f(c.x, c.y + sz / 2, c.z - sz / 2);
		// Point3f p3 = new Point3f(c.x, c.y - sz / 2, c.z - sz / 2);
		// Point3f p4 = new Point3f(c.x, c.y + sz / 2, c.z - sz / 2);

		Point3f p1 = new Point3f(c.x - sz / 2, c.y, c.z - sz / 2);
		Point3f p2 = new Point3f(c.x + sz / 2, c.y, c.z - sz / 2);
		Point3f p3 = new Point3f(c.x + sz / 2, c.y, c.z + sz / 2);
		Point3f p4 = new Point3f(c.x - sz / 2, c.y, c.z + sz / 2);

		// anti-clockwise from bottom left
		plane.setCoordinate(3, p1);
		plane.setCoordinate(2, p2);
		plane.setCoordinate(1, p3);
		plane.setCoordinate(0, p4);

		TexCoord2f q = new TexCoord2f();
		q.set(0.0f, 0.0f);
		plane.setTextureCoordinate(0, 0, q);
		q.set(1.0f, 0.0f);
		plane.setTextureCoordinate(0, 1, q);
		q.set(1.0f, 1.0f);
		plane.setTextureCoordinate(0, 2, q);
		q.set(0.0f, 1.0f);
		plane.setTextureCoordinate(0, 3, q);

		setGeometry(plane);

		// this.branchGroup.addChild(this.tg);
	}

	private void createGeometry2(Point3f c, float sz) {
		QuadArray plane = new QuadArray(NUM_VERTS, GeometryArray.COORDINATES
				| GeometryArray.TEXTURE_COORDINATE_2);

		// the screen is centered at the center, size screenSize, facing +z axis
		// Point3f p1 = new Point3f(c.x - sz / 2, c.y - sz / 2, c.z);
		// Point3f p2 = new Point3f(c.x + sz / 2, c.y - sz / 2, c.z);
		// Point3f p3 = new Point3f(c.x + sz / 2, c.y + sz / 2, c.z);
		// Point3f p4 = new Point3f(c.x - sz / 2, c.y + sz / 2, c.z);
		//
		Point3f p1 = new Point3f(c.x - sz / 2, c.y - sz / 2, -c.z);
		Point3f p2 = new Point3f(c.x + sz / 2, c.y - sz / 2, -c.z);
		Point3f p3 = new Point3f(c.x + sz / 2, c.y + sz / 2, -c.z);
		Point3f p4 = new Point3f(c.x - sz / 2, c.y + sz / 2, -c.z);
		// Point3f p1 = new Point3f(c.x - sz / 2, c.y, c.z - sz / 2);
		// Point3f p2 = new Point3f(c.x + sz / 2, c.y, c.z - sz / 2);
		// Point3f p3 = new Point3f(c.x + sz / 2, c.y, c.z + sz / 2);
		// Point3f p4 = new Point3f(c.x - sz / 2, c.y, c.z + sz / 2);

		// anti-clockwise from bottom left
		plane.setCoordinate(3, p1);
		plane.setCoordinate(2, p2);
		plane.setCoordinate(1, p3);
		plane.setCoordinate(0, p4);

		TexCoord2f q = new TexCoord2f();
		q.set(0.0f, 0.0f);
		plane.setTextureCoordinate(0, 0, q);
		q.set(1.0f, 0.0f);
		plane.setTextureCoordinate(0, 1, q);
		q.set(1.0f, 1.0f);
		plane.setTextureCoordinate(0, 2, q);
		q.set(0.0f, 1.0f);
		plane.setTextureCoordinate(0, 3, q);

		setGeometry(plane);

		// this.branchGroup.addChild(this.tg);
	}

	private void createGeometry3(Point3f c, float sz) {
		QuadArray plane = new QuadArray(NUM_VERTS, GeometryArray.COORDINATES
				| GeometryArray.TEXTURE_COORDINATE_2);

		// the screen is centered at the center, size screenSize, facing +z axis
		Point3f p1 = new Point3f(c.x - sz / 2, c.y - sz / 2, c.z);
		Point3f p2 = new Point3f(c.x + sz / 2, c.y - sz / 2, c.z);
		Point3f p3 = new Point3f(c.x + sz / 2, c.y + sz / 2, c.z);
		Point3f p4 = new Point3f(c.x - sz / 2, c.y + sz / 2, c.z);
		//
		// Point3f p1 = new Point3f(c.x, c.y - sz / 2, c.z - sz / 2);
		// Point3f p2 = new Point3f(c.x, c.y + sz / 2, c.z - sz / 2);
		// Point3f p3 = new Point3f(c.x, c.y - sz / 2, c.z - sz / 2);
		// Point3f p4 = new Point3f(c.x, c.y + sz / 2, c.z - sz / 2);

		// Point3f p1 = new Point3f(c.x - sz / 2, c.y, c.z - sz / 2);
		// Point3f p2 = new Point3f(c.x + sz / 2, c.y, c.z - sz / 2);
		// Point3f p3 = new Point3f(c.x + sz / 2, c.y, c.z + sz / 2);
		// Point3f p4 = new Point3f(c.x - sz / 2, c.y, c.z + sz / 2);

		// anti-clockwise from bottom left
		plane.setCoordinate(3, p1);
		plane.setCoordinate(2, p2);
		plane.setCoordinate(1, p3);
		plane.setCoordinate(0, p4);

		TexCoord2f q = new TexCoord2f();
		q.set(0.0f, 0.0f);
		plane.setTextureCoordinate(0, 0, q);
		q.set(1.0f, 0.0f);
		plane.setTextureCoordinate(0, 1, q);
		q.set(1.0f, 1.0f);
		plane.setTextureCoordinate(0, 2, q);
		q.set(0.0f, 1.0f);
		plane.setTextureCoordinate(0, 3, q);

		setGeometry(plane);

		// this.branchGroup.addChild(this.tg);
	}
	
	private void createGeometry4(Point3f c, float sz) {
		QuadArray plane = new QuadArray(NUM_VERTS, GeometryArray.COORDINATES
				| GeometryArray.TEXTURE_COORDINATE_2);

		// the screen is centered at the center, size screenSize, facing +z axis
		// Point3f p1 = new Point3f(c.x - sz / 2, c.y - sz / 2, c.z);
		// Point3f p2 = new Point3f(c.x + sz / 2, c.y - sz / 2, c.z);
		// Point3f p3 = new Point3f(c.x + sz / 2, c.y + sz / 2, c.z);
		// Point3f p4 = new Point3f(c.x - sz / 2, c.y + sz / 2, c.z);
		//
		Point3f p1 = new Point3f(-c.x , c.y - sz / 2, c.z - sz / 2);
		Point3f p2 = new Point3f(-c.x , c.y - sz / 2, c.z + sz / 2);	
		Point3f p3 = new Point3f(-c.x , c.y + sz / 2, c.z - sz / 2);
		Point3f p4 = new Point3f(-c.x , c.y + sz / 2, c.z + sz / 2);

		// anti-clockwise from bottom left
		plane.setCoordinate(3, p1);
		plane.setCoordinate(2, p2);
		plane.setCoordinate(0, p3);
		plane.setCoordinate(1, p4);

		TexCoord2f q = new TexCoord2f();
		q.set(0.0f, 0.0f);
		plane.setTextureCoordinate(0, 0, q);
		q.set(1.0f, 0.0f);
		plane.setTextureCoordinate(0, 1, q);
		q.set(1.0f, 1.0f);
		plane.setTextureCoordinate(0, 2, q);
		q.set(0.0f, 1.0f);
		plane.setTextureCoordinate(0, 3, q);

		setGeometry(plane);

		// this.branchGroup.addChild(this.tg);
	}

	private void createAppearance() {
		Appearance app = new Appearance();
		app.setCapability(Appearance.ALLOW_TEXTURE_READ);
		app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);

		// blended transparency so texture can be irregular
		TransparencyAttributes tra = new TransparencyAttributes();
		tra.setTransparencyMode(TransparencyAttributes.BLENDED);
		app.setTransparencyAttributes(tra);

		// Create a two dimensional texture with magnification filtering
		// Set the texture from the first loaded image
		texture = new Texture2D(Texture2D.BASE_LEVEL, Texture.RGBA,
				ims[0].getWidth(), ims[0].getHeight());
		texture.setMagFilter(Texture2D.BASE_LEVEL_LINEAR); // NICEST
		texture.setImage(0, ims[0]);
		texture.setCapability(Texture.ALLOW_IMAGE_WRITE); // texture can change
		texture.setCapability(Texture.ALLOW_IMAGE_READ); // texture can change
		app.setTexture(texture);

		setAppearance(app);
	} // end of createAppearance()

	public void showSeries() {
		for (int i = 0; i < ims.length; i++) {
			try {
				texture.setImage(0, ims[i]);
				Thread.sleep(100); // wait a while
			} catch (Exception ex) {
			}
		}
	} // end of showSeries()

	public TransformGroup getTg() {
		return tg;
	}

	public void setTg(TransformGroup tg) {
		this.tg = tg;
	}

	public BranchGroup getBranchGroup() {
		return branchGroup;
	}

	public void setBranchGroup(BranchGroup branchGroup) {
		this.branchGroup = branchGroup;
	}
}
