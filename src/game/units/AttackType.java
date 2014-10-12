package game.units;

public enum AttackType {
	
	// can hit one target in the front row
	MELEE,
	
	// can hit one target anywhere
	RANGED,
	
	// hits every enemy
	AOE,
	
	// hits all enemies in a line
	LINE,
	
	// hits everyone in the row
	ROW,
	
	// hit a random enemy
	RANDOM;
	
	@Override
	public String toString(){
		if (this == MELEE) return "melee";
		else if (this == RANGED) return "ranged";
		else if (this == AOE) return "aoe";
		else if (this == LINE) return "line";
		else if (this == ROW) return "row";
		else return "random";
	}
	
	public static AttackType fromString(String str){
		if (str.equals("melee")) return MELEE;
		else if (str.equals("ranged")) return RANGED;
		else if (str.equals("aoe")) return AOE;
		else if (str.equals("line")) return LINE;
		else if (str.equals("row")) return ROW;
		else if (str.equals("random")) return RANDOM;
		else throw new RuntimeException("unrecognised string for attack type");
	}
	
}
