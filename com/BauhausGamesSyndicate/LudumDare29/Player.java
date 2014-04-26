/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.BauhausGamesSyndicate.LudumDare29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.BauhausGamesSyndicate.LudumDare29.overworld.Overworld;

/**
 *
 * @author Benedikt Vogler
 */
public class Player extends AbstractCharacter {

    public Player(float x, float y, String name) {
        super(x, y, "player");
    }
    
    /**
     *
     * @param delta
     */
    @Override
    public void update(float delta){
        setAcceleration(0);
        if (Gdx.input.isKeyPressed(Keys.D)){
            setAcceleration(getAcceleration() -1);
        }
        
        if (Gdx.input.isKeyPressed(Keys.A)){
            setAcceleration(getAcceleration() +1);
        }
        setAcceleration(getAcceleration()*getAccFactor());
        setVelocity(getVelocity() + getAcceleration());
        setX(getX() + getVelocity());
        setY(Overworld.getHeightmapValue((int) getX() ));
    }
}
