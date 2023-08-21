package TankImplementation.entity;

import TankSimulation.BlocoCenario;
import javaengine.CSprite;

public class Tank {

    private BlocoCenario[][] matrizBlocos = null;
    public CSprite tank = null;

    public Tank(CSprite sprite, BlocoCenario[][] matrizBlocos) {
        this.matrizBlocos = matrizBlocos;
        this.tank = sprite;
    }
}
