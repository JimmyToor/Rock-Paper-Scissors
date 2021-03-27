package com.example.jimmy.rockpaperscissors;

public class Robot extends Player {
    private WeaponStrategy weaponStrategy;
    private final WeaponStrategy DEFAULT_WEAPON_STRATEGY = new RandomStrategy();

    public Robot(){
        super("DEFAULT_ROBOT_NAME");
        setWeaponStrategy(DEFAULT_WEAPON_STRATEGY);
    }

    public Robot(String name)
    {
        super(name);
        setWeaponStrategy(DEFAULT_WEAPON_STRATEGY);
    }

    public Robot(String name, WeaponStrategy weaponStrategy)
    {
        super(name);
        setWeaponStrategy(weaponStrategy);
    }

    public WeaponStrategy getWeaponStrategy()
    {
        return weaponStrategy;
    }

    public void setWeaponStrategy(WeaponStrategy weaponStrategy)
    {
        this.weaponStrategy = weaponStrategy;
    }

    public void makeChoice(Match match)
    {
        setWeapon(weaponStrategy.choose(match));
    }
}
