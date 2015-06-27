package org.scystl.operations;

/**
 * Created by Andrea on 25/06/2015.
 */
public class AditionOperation implements Operation{
    @Override
    public float calculate(float firstValue, float secondValue) {
        return firstValue + secondValue;
    }
}
