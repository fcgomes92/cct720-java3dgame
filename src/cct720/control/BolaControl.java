package cct720.control;

import java.util.Random;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import cct720.model.Bola;

public class BolaControl {

	private Transform3D t3d = new Transform3D();
//	private Transform3D t3d2 = new Transform3D();
//	private float ajusteX = 0.0f;
//	private float ajusteY = 0.0f;
//	private float ajusteZ = 0.0f;
	
	public Bola criarBolaAleatoria(Vector3f posInical){
		Random rand = new Random();
		return new Bola(posInical,(float)(rand.nextInt((30 - 10) + 1) + 10),(float)((rand.nextInt((100 - 50) + 1) + 50))/100);
	}
	
	public boolean moveBola(float TIME_STEP, 
			float X0, float Y0,
			float x, float y, float z,
			float V0x, float gravity,
			Bola b, BeginGameControl bgc) throws InterruptedException {
//		ajusteX = 0.0f;
//		ajusteY = 0.0f;
//		ajusteZ = 0.0f;
		for (float i = TIME_STEP; (y-b.getRaio()) >= 0 ; i+=TIME_STEP){
			x = X0 + V0x*i;
			y = Y0 - (gravity*i*i)/2;
//			ajusteX = x;
//			ajusteY = y;
//			ajusteZ = z;
			b.getTg().getTransform(t3d);
			t3d.setTranslation(new Vector3d(x,y,z));
			b.getTg().setTransform(t3d);
			if(bgc.executarExplosao)
				break;
			Thread.sleep((long) (TIME_STEP*1000));
		}
		
		return true;
	}
	
	public void deformBola(float TIME_STEP, 
			float X0, float Y0,
			float x, float y, float z,
			float V0x, float gravity,
			Bola b) throws InterruptedException {
		// Deforma a bola
		b.getTg().getTransform(t3d);
		t3d.setScale(new Vector3d(1.3,0.7,1));
		b.getTg().setTransform(t3d);
		
		// Da uma um time para o café
		Thread.sleep((long) (TIME_STEP*1000));
			
		// Volta a bola ao normal
		b.getTg().getTransform(t3d);
		t3d.setScale(new Vector3d(1.0f,1.0f,1.0f));
		b.getTg().setTransform(t3d);
		
		// Move ela de leve
//		b.getTg().getTransform(t3d);
//		b.getTg().getLocalToVworld(t3d);
//		t3d2.setTranslation(new Vector3d(ajusteX,(0-ajusteY+b.getRaio()),ajusteZ));
//		t3d.mul(t3d2);
//		b.getTg().setTransform(t3d);
	}
	
}
