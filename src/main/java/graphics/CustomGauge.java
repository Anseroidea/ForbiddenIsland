package graphics;

import eu.hansolo.medusa.Gauge;

public class CustomGauge extends Gauge {

    public double getValue(){
        return super.getValue()/10;
    }

    public double getCurrentValue(){
        return super.getCurrentValue()/10;
    }

}
