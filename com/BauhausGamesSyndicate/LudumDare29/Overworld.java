package com.BauhausGamesSyndicate.LudumDare29;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

/**
 *
 * @author Benedikt Vogler
 */
public class Overworld {
    private final int[] heightmap;
    private final ArrayList<AbstractEntity> entityList = new ArrayList<>();
    private int cameraPos = 0;
    private int anzMinions = 10;
    private final int height =1024;
    private final int width = 4096;//2^14
    private final Texture[] chunkgraphic; 
    private final Texture parallax;

    public Overworld() {        
        chunkgraphic = new Texture[3];//max 3 backgroudn tiles
        chunkgraphic[0] = new Texture(Gdx.files.internal("com/BauhausGamesSyndicate/LudumDare29/map0.png"));
        chunkgraphic[1] = new Texture(Gdx.files.internal("com/BauhausGamesSyndicate/LudumDare29/map1.png"));
        chunkgraphic[2] = new Texture(Gdx.files.internal("com/BauhausGamesSyndicate/LudumDare29/map0.png"));
        
        parallax = new Texture(Gdx.files.internal("com/BauhausGamesSyndicate/LudumDare29/bg.png"));
         
        //heightmap generieren
        this.heightmap = new int[200];
        for (int x = 0; x < heightmap.length; x++) {
            heightmap[x] = (int) (Math.random()*height);
        }
        
        for (int i = 0; i <= anzMinions; i++){
            entityList.add(new Minion(width/2f, height/2f ) );
        }
    }
    
    public void update(float delta){
        for( AbstractEntity m: entityList){
            m.update(m.getX()+delta, m.getY()+delta);
        }
        cameraPos+=delta;
        cameraPos = cameraPos % (width*chunkgraphic.length);
    }
    
    public void render(GameScreen gs){
       // sh.begin(ShapeRenderer.ShapeType.Filled);
        gs.getBatch().begin();
        
        //background
         int y = Gdx.graphics.getHeight()-height; 
         for (int i = 0; i < getMapWidth()/parallax.getWidth(); i++) {
            int x = i*parallax.getWidth()-cameraPos/2;
//            int m=getMapWidth();
//            if (x < -m)
//                x += m;
//            else
//                x = x % m;
            
            if (x<Gdx.graphics.getWidth() && x+width > 0)
               gs.getBatch().draw(parallax, x, y);
        }
         
         //render check left side
        y = Gdx.graphics.getHeight()-height; 
        for (int i = 0; i < chunkgraphic.length; i++) {
            int x = i*width-cameraPos;
            int m=getMapWidth();
            if (x < -m)
                x += m;
            else
                x = x % m;
            
            if (x<Gdx.graphics.getWidth() && x+width > 0)
               gs.getBatch().draw(chunkgraphic[i], x, y);
        }
         
        for( AbstractEntity m: entityList){
            m.render(gs);
        }
     
        
        
        
        
        
        //gs.getFont().draw(gs.getBatch(), Integer.toString(cameraPos), 20, 20);
        gs.getBatch().end();
        
        
        ShapeRenderer sh = gs.getShapeRenderer();
        sh.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < heightmap.length; i++) {
            sh.line(i*resolution()-cameraPos, getHeightmapValue(i), (i+1)*resolution()-cameraPos, getHeightmapValue(i+1));
        }
        sh.end();
        

        
        
        //sh.end();
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
    public int getHeightmapValue(int sample){
        return heightmap[sample % heightmap.length];
    }
    
    public int getMapWidth(){
        return width*chunkgraphic.length;
    }
    
}
