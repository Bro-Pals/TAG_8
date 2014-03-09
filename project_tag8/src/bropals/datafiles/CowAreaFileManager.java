/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.datafiles;

import bropals.Main;
import bropals.debug.Debugger;
import static bropals.debug.Debugger.ERROR;
import static bropals.debug.Debugger.INFO;
import static bropals.debug.Debugger.print;
import bropals.gameobject.GameObject;
import bropals.gameobject.GrappleHookPoint;
import bropals.gameobject.Human;
import bropals.gameobject.Waypoint;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.HayBale;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.TeleportDoor;
import bropals.gameobject.block.Wall;
import bropals.level.Area;
import bropals.level.AreaFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class CowAreaFileManager {
    
    private final String BLOCK="BLOCK", WALL="WALL", HUMAN="HUMAN", NORMALDOOR="NORMALDOOR", TELEPORTDOOR="TELEPORTDOOR", AVACADO="AVACADO", AVACADOBIN="AVACADOBIN", GRAPPLEHOOKPOINT="GRAPPLEHOOKPOINT", HAYBALE="HAYBALE";
    
    private final String dataDirectory = "data";
    private String jarPath;
    
    private BlockMachine blockMachine;
    private WallMachine wallMachine;
    private HumanMachine humanMachine;
    private NormalDoorMachine normalDoorMachine;
    private TeleportDoorMachine teleportDoorMachine;
    private AvacadoMachine avacadoMachine;
    private AvacadoBinMachine avacadoBinMachine;
    private GrappleHookPointMachine grappleHookPointMachine;
    private HayBaleMachine hayBaleMachine;
    
    public CowAreaFileManager() {
        try {
            jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
            print("Jar Root: " + jarPath, INFO);
        } catch(URISyntaxException e) {
            print("Can't get jar root!", ERROR);
        }
        blockMachine = new BlockMachine();
        wallMachine = new WallMachine();
        humanMachine = new HumanMachine();
        normalDoorMachine = new NormalDoorMachine();
        teleportDoorMachine = new TeleportDoorMachine();
        avacadoMachine = new AvacadoMachine();
        avacadoBinMachine = new AvacadoBinMachine();
        grappleHookPointMachine = new GrappleHookPointMachine();
        hayBaleMachine = new HayBaleMachine();
    }
    
    public void export(Area area, File asFile) {
        //Export all objects in area to a text file
        try {
            //Open file output stream
            BufferedWriter writer = new BufferedWriter(new FileWriter(asFile));
            ArrayList<GameObject> objects = area.getObjects();
            humanMachine.giveWriter(writer); //Needs writer reference
            
            for (int i=0; i<objects.size(); i++) {
                writer.write(makeDataLineFor(objects.get(i)));
                writer.newLine();
            }
            
            writer.flush();
            writer.close();
            Debugger.print("Successfully saved Area with ID " + area.getAreaId() + " to " + asFile.getAbsolutePath() + "", Debugger.INFO);
        } catch(IOException exception) {
            Debugger.print("Unable to save file to " + asFile.getAbsolutePath(), Debugger.ERROR);
        }
    }
    
    public void loadArea(AreaFactory factory, File file) {
        try {
            factory.makeNewAreaOverOldArea();
            Area area = factory.getArea();
            //Read all of the lines and insert objects into the area as we go
            BufferedReader reader = new BufferedReader(new FileReader(file));
            humanMachine.giveReader(reader); //Needs reader for special case
            
            String line = "";
            line = reader.readLine();
            while(line!=null) {
                area.addObject(
                        readDataLine(line)
                );
                line = reader.readLine();
            }
            
            reader.close();
            Debugger.print("Successfully opened Area with ID " + area.getAreaId() + " from " + file.getAbsolutePath() + "", Debugger.INFO);
        } catch(Exception e) {
            Debugger.print("Unable to read file " + file.getAbsolutePath(), Debugger.ERROR);
        }
    }
    
    private String makeDataLineFor(GameObject forObject) {
        String dataLine = "";
        if (forObject instanceof Wall) {
            return wallMachine.makeDataLine((Wall)forObject);
        } else
        if (forObject instanceof GrappleHookPoint) {
            return grappleHookPointMachine.makeDataLine((GrappleHookPoint)forObject);
        } else
        if (forObject instanceof Avacado) {
            return avacadoMachine.makeDataLine((Avacado)forObject);
        } else
        if (forObject instanceof AvacadoBin) {
            return avacadoBinMachine.makeDataLine((AvacadoBin)forObject);
        } else
        if (forObject instanceof NormalDoor) {
            return normalDoorMachine.makeDataLine((NormalDoor)forObject);
        } else
        if (forObject instanceof TeleportDoor) {
            return teleportDoorMachine.makeDataLine((TeleportDoor)forObject);
        } else
        if (forObject instanceof Human) {
            return humanMachine.makeDataLine((Human)forObject);
        } else
        if (forObject instanceof HayBale) {
            return hayBaleMachine.makeDataLine((HayBale)forObject);
        } else
        if (forObject instanceof Block) {
            return blockMachine.makeDataLine((Block)forObject);
        }
        return dataLine;
    }
    
    private GameObject readDataLine(String line) {
        String[] tokenized = line.split(" ");
        if (tokenized[0].equals(WALL)) {
            return wallMachine.readDataLine(line);
        } else
        if (tokenized[0].equals(BLOCK)) {
            return blockMachine.readDataLine(line);
        } else
        if (tokenized[0].equals(AVACADO)) {
            return avacadoMachine.readDataLine(line);
        } else
        if (tokenized[0].equals(AVACADOBIN)) {
            return avacadoBinMachine.readDataLine(line);
        } else
        if (tokenized[0].equals(GRAPPLEHOOKPOINT)) {
            return grappleHookPointMachine.readDataLine(line);
        } else
        if (tokenized[0].equals(HAYBALE)) {
            return hayBaleMachine.readDataLine(line);
        } else
        if (tokenized[0].equals(NORMALDOOR)) {
            return normalDoorMachine.readDataLine(line);
        } else
        if (tokenized[0].equals(TELEPORTDOOR)) {
            return teleportDoorMachine.readDataLine(line);
        } else 
        if (tokenized[0].equals(HUMAN)) {
            return humanMachine.readDataLine(line); //Super special case
        } else {
            Debugger.print("No code for handling token type " + tokenized[0] + "!", Debugger.ERROR);
        }
        return null;
    }
    
    class BlockMachine extends GameObjectIOMachine<Block> {

        @Override
        public String makeDataLine(Block object) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Block readDataLine(String line) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class GrappleHookPointMachine extends GameObjectIOMachine<GrappleHookPoint> {

        @Override
        public String makeDataLine(GrappleHookPoint object) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public GrappleHookPoint readDataLine(String line) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        
    }
    class AvacadoMachine extends GameObjectIOMachine<Avacado> {

        @Override
        public String makeDataLine(Avacado object) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Avacado readDataLine(String line) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    class AvacadoBinMachine extends GameObjectIOMachine<AvacadoBin> {

        @Override
        public String makeDataLine(AvacadoBin object) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public AvacadoBin readDataLine(String line) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class NormalDoorMachine extends GameObjectIOMachine<NormalDoor> {

        @Override
        public String makeDataLine(NormalDoor object) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public NormalDoor readDataLine(String line) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class TeleportDoorMachine extends GameObjectIOMachine<TeleportDoor> {

        @Override
        public String makeDataLine(TeleportDoor object) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public TeleportDoor readDataLine(String line) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class HumanMachine extends GameObjectIOMachine<Human> {

        private BufferedReader reader;
        
        public void giveReader(BufferedReader reader) {
            this.reader=reader;
        }
        
        private BufferedWriter writer;
        
        public void giveWriter(BufferedWriter writer) {
            this.writer = writer;
        }
        
        @Override
        public String makeDataLine(Human object) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Human readDataLine(String line) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        class WaypointMachine extends GameObjectIOMachine<Waypoint> {

            @Override
            public String makeDataLine(Waypoint object) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Waypoint readDataLine(String line) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        }
    }
    
    class HayBaleMachine extends GameObjectIOMachine<HayBale> {

        @Override
        public String makeDataLine(HayBale object) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public HayBale readDataLine(String line) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class WallMachine extends GameObjectIOMachine<Wall> {

        @Override
        public String makeDataLine(Wall object) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Wall readDataLine(String line) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        
    }
}
    
