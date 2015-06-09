package cct720.control;

import javax.vecmath.Vector3f;

import cct720.model.Bola;

public class BolaControl {

	public Bola criarBolaAleatoria(Vector3f posInical){
		return new Bola(posInical,(float)Math.random()*30.0f,(float)Math.random()*1.0f);
	}
	
}
