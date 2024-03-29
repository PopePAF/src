package com.BauhausGamesSyndicate.LudumDare29.overworld;


import com.BauhausGamesSyndicate.LudumDare29.AbstractEntity;
import com.BauhausGamesSyndicate.LudumDare29.GameScreen;
import com.BauhausGamesSyndicate.LudumDare29.Minion;
import com.badlogic.gdx.Gdx;
import com.BauhausGamesSyndicate.LudumDare29.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

/**
 *
 * @author Benedikt Vogler
 */
public class Overworld {
    private static int[] heightmap;
    private static final ArrayList<AbstractEntity> entityList = new ArrayList<>();
    private static Player player;
    private static int cameraPos = 0;
    private static int anzMinions = 10;
    private static Chunk[] chunks; 
    private static Texture background;
    private static Texture overlay;

    public Overworld() {        
        chunks = new Chunk[3];//max 3 backgroudn tiles
        chunks[0] = new Chunk(0);
        chunks[1] = new Chunk(1);
        chunks[2] = new Chunk(0);
        
        background = new Texture(Gdx.files.internal("com/BauhausGamesSyndicate/LudumDare29/assets/bg.png"));
        overlay = new Texture(Gdx.files.internal("com/BauhausGamesSyndicate/LudumDare29/assets/overlay.png")); 
        
        //heightmap generieren
        Overworld.heightmap = new int[200];
        for (int x = 0; x < heightmap.length; x++) {
            heightmap[x] = (int) (Math.random()*Chunk.HEIGHT/2);
        }
        
        // make Player
        player = new Player(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, "player");
        
        //minnions in liste füllen
        for (int i = 0; i <= anzMinions; i++){
            entityList.add(new Minion(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f ) );
        }
    }
    
    public void update(float delta){
        cameraPos+=delta;
        cameraPos = cameraPos % (Chunk.WIDTH*chunks.length);
        
        player.update(delta);
        /*
        for( AbstractEntity m: entityList){
            m.update(delta);
        }
        */
    }
    
    public void render(GameScreen gs){
        gs.getBatch().begin();
        
        //background
         int y = Gdx.graphics.getHeight()-Chunk.HEIGHT; 
         for (int i = 0; i < getMapWidth()/background.getWidth(); i++) {
            int x = i*background.getWidth()-cameraPos/2;
//            int m=getMapWidth();
//            if (x < -m)
//                x += m;
//            else
//                x = x % m;
            
            if (x<Gdx.graphics.getWidth() && x+Chunk.WIDTH > 0)
               gs.getBatch().draw(background, x, y);
        }
         
        //middleground
        for (Chunk chunk : chunks) {
            chunk.render(gs);
        }
        player.render(gs);
        /*
        for( AbstractEntity m: entityList){
            m.render(gs);
        }
        */
        
        //overlay
        gs.getBatch().draw(overlay, 0, 0);
     
        gs.getBatch().end();
        
        
        ShapeRenderer sh = gs.getShapeRenderer();
        sh.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < heightmap.length; i++) {
            sh.line(i*resolution()-cameraPos, getHeightmapValue(i), (i+1)*resolution()-cameraPos, getHeightmapValue(i+1));
        }
        sh.end();
        
    }
    
/**
 * The resolution of the heightmap
     * @return 
 */
    public int resolution(){
        return getMapWidth()/heightmap.length;
    }
    
   /**
    * Round
    * @param sample
    * @return 
    */
    public static int getHeightmapValue(int sample){
        int m = heightmap.length;
        int i = (sample < 0) ? (m - (Math.abs(sample) % m) ) %m : (sample % m);
        return heightmap[i];
    }
    
    public int getMapWidth(){
        return Chunk.WIDTH*chunks.length;
    }
    
}
