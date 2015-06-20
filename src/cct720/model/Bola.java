package cct720.model;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

public class Bola {

	private Sphere sphere;
	private TransformGroup tg = new TransformGroup();
	private BranchGroup branchGroup = new BranchGroup();
	private float peso;
	private float raio;

	public Bola(Vector3f posInicial, float peso, float raio) {
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f blue = new Color3f(0.3f, 0.3f, 0.8f);
		Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);

		Material blueMat = new Material(blue, black, blue, specular, 25.0f);
		blueMat.setLightingEnable(true);

		Appearance blueApp = new Appearance();
		blueApp.setMaterial(blueMat);

		this.raio = raio;
		this.sphere = new Sphere(raio, blueApp);
		this.sphere.setUserData("Shooting Ball");

		Transform3D td = new Transform3D();
		td.set(posInicial);

		this.tg = new TransformGroup(td);
		this.tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.tg.addChild(this.sphere); // set its radius and appearance
		
		this.branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
		this.branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		this.branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		this.branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		
		this.branchGroup.addChild(this.tg);
		
		this.peso = peso;
	}

	public Sphere getSphere() {
		return sphere;
	}

	public void setSphere(Sphere sphere) {
		this.sphere = sphere;
	}

	public TransformGroup getTg() {
		return tg;
	}

	public void setTg(TransformGroup tg) {
		this.tg = tg;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public BranchGroup getBranchGroup() {
		return branchGroup;
	}

	public void setBranchGroup(BranchGroup branchGroup) {
		this.branchGroup = branchGroup;
	}

	public float getRaio() {
		return raio;
	}

	public void setRaio(float raio) {
		this.raio = raio;
	}
}
