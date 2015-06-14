package cct720.control;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import cct720.model.Cubo;

public class CuboControl {
	
	public Cubo criarCuboAleatorio(boolean zSide){
		if (zSide)
			return new Cubo(new Vector3f((float)-Math.random()*15,(float)Math.random()*15,(float)Math.random()*15),
					new float[] {(float)Math.random()*2,(float)Math.random()*2,(float)Math.random()*2},(float)Math.random()*30);
		else
			return new Cubo(new Vector3f((float)-Math.random()*15,(float)Math.random()*15,(float)-Math.random()*15),
					new float[] {(float)Math.random()*2,(float)Math.random()*2,(float)Math.random()*2},(float)Math.random()*30);
	}
	
	public Cubo gerarParede(){
		return new Cubo(new Vector3f(-10.0f, 5.0f, 0),
				new float[] {0.0f,5.0f,10.0f},30.0f, 
				new Color3f(0.3f,0.3f,0.3f), new Color3f(0.0f,0.0f,0.0f), new Color3f(0.3f,0.3f,0.3f));
	}
	
	public Cubo gerarAlvos(boolean zSide){
		if (zSide)
			return new Cubo(new Vector3f(-10.0f,(float)Math.random()*10,(float)Math.random()*10),
					new float[] {(float)Math.random()*2,(float)Math.random()*2,(float)Math.random()*2},(float)Math.random()*30,
					new Color3f(0.9f,0.0f,0.0f), new Color3f(0.0f,0.0f,0.0f), new Color3f(0.3f,0.3f,0.3f));
		else
			return new Cubo(new Vector3f(-10.0f,(float)Math.random()*10,(float)-Math.random()*15),
					new float[] {(float)Math.random()*2,(float)Math.random()*2,(float)Math.random()*2},(float)Math.random()*30,
					new Color3f(0.9f,0.0f,0.0f), new Color3f(0.0f,0.0f,0.0f), new Color3f(0.3f,0.3f,0.3f));
	}
}