package org.freerealm.command;

import java.util.Random;

import org.apache.log4j.Logger;
import org.freerealm.Realm;
import org.freerealm.Utility;
import org.freerealm.unit.Unit;

/**
 * Command class to execute an attack to a hostile unit by the given attacking
 * unit.
 * <p>
 * TODO : More explanation when the command is finalized.
 * 
 * @author Deniz ARIKAN
 */
public class AttackUnitCommand extends FreeRealmAbstractCommand {

	private final Unit attacker;
	private final Unit defender;
	private final int attackBonusPercentage = 0;
	private int defenceBonusPercentage = 0;

	/**
	 * Constructs an AttackUnitCommand using attacker, defender.
	 * 
	 * @param attacker
	 *            Unit making the attack
	 * @param defender
	 *            Defending unit
	 */
	public AttackUnitCommand(Realm realm, Unit attacker, Unit defender) {
		super(realm);
		this.attacker = attacker;
		this.defender = defender;
	}

	/**
	 * Executes command to make unit attack given defending unit.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (getAttacker().canAttack()) {
			Unit winner = null;
			Unit loser = null;
			int attackPoints = getAttacker().getAttackPoints();
			int defencePoints = getDefender().getDefencePoints();
			String log = getAttacker().getPlayer().getNation().getAdjective() + " " + getAttacker().getName() + " is attacking " + getDefender().getPlayer().getNation().getAdjective() + " "
					+ getDefender().getName() + ".";
			Logger.getLogger(AttackUnitCommand.class).info(log);
			Logger.getLogger(AttackUnitCommand.class).info("Attack points : " + attackPoints + " Defense points : " + defencePoints);
			attackPoints = (int) Utility.modifyByPercent(attackPoints, attackBonusPercentage);
			defenceBonusPercentage = Utility.getCoordinateDefenceBonus(getRealm(), getDefender().getCoordinate());
			defencePoints = (int) Utility.modifyByPercent(defencePoints, defenceBonusPercentage);
			Logger.getLogger(AttackUnitCommand.class).info("Actual attack points : " + attackPoints + " Actual defense points : " + defencePoints);
			int total = attackPoints + defencePoints;
			Random random = new Random(System.currentTimeMillis());
			int randomInt = random.nextInt(total);
			log = "Total : " + total + " Random : " + randomInt;
			if (randomInt < attackPoints) {
				loser = getDefender();
				winner = getAttacker();
				log = log + " Attacker won!";
			} else {
				loser = getAttacker();
				winner = getDefender();
				log = log + " Defender won!";
			}
			Logger.getLogger(AttackUnitCommand.class).info(log);
			getExecutor().execute(new UnitSetMovementPointsCommand(winner, 0));
			getExecutor().execute(new RemoveUnitCommand(getRealm(), loser.getPlayer(), loser));
			putParameter("attacker", getAttacker());
			putParameter("defender", getDefender());
			putParameter("winner", winner);
			putParameter("coordinate", getDefender().getCoordinate());
			setState(SUCCEEDED);
		} else {
			setState(FAILED);
		}
	}

	public Unit getAttacker() {
		return attacker;
	}

	public Unit getDefender() {
		return defender;
	}
}
