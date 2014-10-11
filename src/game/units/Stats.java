package game.units;

public interface Stats {

	void setBase(Stat stat, int value);
	void setBuff(Stat stat, int value);
	void setCurrent(Stat stat, int value);
	int getBase(Stat stat);
	int getBuff(Stat stat);
	int getCurrent(Stat stat);
	int getTotal(Stat stat);

}
