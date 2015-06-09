package cct720.model;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;

public class Cubo {
	private Box box;
	private TransformGroup tg = new TransformGroup();
	private BranchGroup branchGroup = new BranchGroup();
	private float peso;

	public Cubo(Vector3f p1, float[] pontos , float peso) {
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f blue = new Color3f((float)Math.random(), (float)Math.random(), (float)Math.random());
		Color3f specular = new Color3f((float)Math.random(), (float)Math.random(), (float)Math.random());

		Material blueMat = new Material(blue, black, blue, specular, 25.0f);
		blueMat.setLightingEnable(true);

		Appearance blueApp = new Appearance();
		blueApp.setMaterial(blueMat);

		this.box = new Box(pontos[0],pontos[1],pontos[2],blueApp);

		Transform3D td = new Transform3D();
		td.set(p1);

		this.tg = new TransformGroup(td);
		this.tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.tg.addChild(this.box); // set its radius and appearance

		this.branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
		this.branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		this.branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		this.branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);

		this.branchGroup.addChild(this.tg);

		this.peso = peso;
	}
	
	public Cubo(Vector3f p1, float[] pontos , float peso, Color3f c1, Color3f c2, Color3f c3) {
		Material blueMat = new Material(c1, c2, c1, c3, 25.0f);
		blueMat.setLightingEnable(true);

		Appearance blueApp = new Appearance();
		blueApp.setMaterial(blueMat);

		this.box = new Box(pontos[0],pontos[1],pontos[2],blueApp);

		Transform3D td = new Transform3D();
		td.set(p1);

		this.tg = new TransformGroup(td);
		this.tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.tg.addChild(this.box); // set its radius and appearance

		this.branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
		this.branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		this.branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		this.branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);

		this.branchGroup.addChild(this.tg);

		this.peso = peso;
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

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

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}
	
	
}
