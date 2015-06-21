package cct720.control;

import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Bounds;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOnCollisionMovement;
import javax.media.j3d.WakeupOr;

import cct720.model.Bola;
import cct720.model.Cubo;
import cct720.model.MainUniverse;

public class TargetCollisionControl extends Behavior {

	/** The shape that is being watched for collisions. */
	protected Shape3D collidingShape;
	/** The separate criteria that trigger this behaviour */
	protected WakeupCriterion[] theCriteria;
	/** The result of the 'OR' of the separate criteria */
	protected WakeupOr oredCriteria;

	private Bola shootingBall;
	private BeginGameControl bgc;
	private Cubo target;

	protected MainUniverse su;

	/**
	 * @param theShape
	 *            Shape3D that is to be watched for collisions.
	 * @param theBounds
	 *            Bounds that define the active region for this behaviour
	 */
	public TargetCollisionControl(Shape3D theShape, Bounds theBounds,
			MainUniverse su, BeginGameControl bgc, Cubo c) {
		this.su = su;
		this.bgc = bgc;
		this.target = c;
		collidingShape = theShape;
		setSchedulingBounds(theBounds);
	}

	/**
	 * This sets up the criteria for triggering the behaviour. It creates an
	 * entry, exit and movement trigger, OR's these together and then sets the
	 * OR'ed criterion as the wake up condition.
	 */
	public void initialize() {
		theCriteria = new WakeupCriterion[3];
		WakeupOnCollisionEntry startsCollision = new WakeupOnCollisionEntry(
				collidingShape);
		WakeupOnCollisionExit endsCollision = new WakeupOnCollisionExit(
				collidingShape);
		WakeupOnCollisionMovement moveCollision = new WakeupOnCollisionMovement(
				collidingShape);
		theCriteria[0] = startsCollision;
		theCriteria[1] = endsCollision;
		theCriteria[2] = moveCollision;
		oredCriteria = new WakeupOr(theCriteria);
		wakeupOn(oredCriteria);
	}

	/**
	 * This is where the work is done. This identifies the type of collision
	 * (entry, exit or movement) and prints a message stating that an object has
	 * collided with this object. The userData field of the shape associated
	 * with this collision detector # is used to identify the object. Finally,
	 * the wake up condition is set to be the OR'ed criterion again.
	 */
	public void processStimulus(Enumeration criteria) {
		wakeupOn(oredCriteria);
		while (criteria.hasMoreElements()) {
			WakeupCriterion theCriterion = (WakeupCriterion) criteria
					.nextElement();
			if (theCriterion instanceof WakeupOnCollisionEntry) {
				String colidido = ((WakeupOnCollisionEntry) theCriterion).getTriggeringPath().getObject().getParent().getName();
				if (bgc.getShootingBall() != null && colidido.equals("Bola")) {
					su.sceneBG.removeChild(bgc.getShootingBall()
							.getBranchGroup());
					su.sceneBG.removeChild(target.getBranchGroup());
					bgc.ganhaPto(10);
					bgc.showExplosion();
				}
			}
		}
	}
}
