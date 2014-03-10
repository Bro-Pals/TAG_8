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
import bropals.gameobject.HumanState;
import bropals.gameobject.HumanType;
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
import bropals.util.Vector2;
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
    private final String TRUE="TRUE", FALSE="FALSE";
    private final String WAYPOINTENDER = "ENDWAYPOINTS";
    private final String SEPARATOR = " ";
    
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
    private boolean ignoringAvacados;
    
    public CowAreaFileManager() {
        try {
            jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
            print("Jar Root: " + jarPath, INFO);
        } catch(URISyntaxException e) {
            print("Can't get jar root!", ERROR);
        }
        ignoringAvacados = false;
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
    
    public void loadAllTheAvacados() {
        // Go through each file and all the lines in each file and see if there is an avacado
        // If there is an avacado, then load it, otherwise ignore it
        File[] allFiles = new File(jarPath + "\\" + dataDirectory).listFiles();
        BufferedReader reader;
        int avacadoCount = 0;
        for (int f=0; f<allFiles.length; f++) {
            Debugger.print("Checking " + allFiles.length + " files for Avacados", Debugger.INFO);
            //In each file
            try {
                reader = new BufferedReader(new FileReader(allFiles[f]));
                String line = reader.readLine();
                String[] tokens = line.split(SEPARATOR);
                while(line!=null) {
                    if (tokens[0].equals(AVACADO)) {
                        readDataLine(line);
                        avacadoCount++;
                    }//Don't load if it isn't an avacado
                    line = reader.readLine();
                }
                humanMachine.reset();
                reader.close();
                Debugger.print("Found a total of " + avacadoCount + " avacadoes from " + allFiles.length + " files", Debugger.INFO);
            } catch(Exception e) {
                Debugger.print("Could not load file for avacado checking " + allFiles[f].getName(), Debugger.ERROR);
            }
        }
        ignoringAvacados = true;
    }
    
    private String makeFirstLine(Area area) {
        return area.getAreaId() + SEPARATOR + area.getNorthTargetId() + SEPARATOR + area.getSouthTargetId() + SEPARATOR + area.getEastTargetId() + SEPARATOR + area.getWestTargetId();
    }
    
    private void readAndSetFirstLine(Area area, String firstLine) {
        String[] block = firstLine.split(SEPARATOR);
        area.setRoomID( Integer.parseInt(block[0]) );
        area.setNorthTargetId( Integer.parseInt(block[1]) );
        area.setSouthTargetId( Integer.parseInt(block[2]));
        area.setEastTargetId( Integer.parseInt(block[3]));
        area.setWestTargetId( Integer.parseInt(block[4]));
    }
    
    public void export(Area area, File asFile) {
        //Export all objects in area to a text file
        try {
            //Open file output stream
            BufferedWriter writer = new BufferedWriter(new FileWriter(asFile));
            ArrayList<GameObject> objects = area.getObjects();
            humanMachine.giveWriter(writer); //Needs writer reference
            writer.write(makeFirstLine(area));
            writer.newLine();
            for (int i=0; i<objects.size(); i++) {
                writer.write(makeDataLineFor(objects.get(i)));
                if (i<(objects.size()-1)) {
                    writer.newLine();
                }
            }
            humanMachine.reset();
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
            readAndSetFirstLine(area, line);
            
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
           //e.printStackTrace();
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
            if (!ignoringAvacados) {
                return avacadoMachine.readDataLine(line);
            }
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
            return BLOCK + SEPARATOR + object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getWidth() + SEPARATOR + object.getHeight() + SEPARATOR + object.getTextureString() + SEPARATOR + object.getTextureIndex();
        }

        @Override
        public Block readDataLine(String line) {
            String[] block = line.split(SEPARATOR);
            Block object = new Block(0, 0, 10, 10);
            object.setX( Float.parseFloat(block[1]) );
            object.setY( Float.parseFloat(block[2]) );
            object.setWidth( Float.parseFloat(block[3]) );
            object.setHeight( Float.parseFloat(block[4]) );
            object.setTextureString(block[5]);
            object.setTextureIndex(Integer.parseInt(block[6]));
            return object;
        }
        
    }
    
    class GrappleHookPointMachine extends GameObjectIOMachine<GrappleHookPoint> {

        @Override
        public String makeDataLine(GrappleHookPoint object) {
            return GRAPPLEHOOKPOINT + SEPARATOR + object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getInteractDistance() + SEPARATOR + object.getTextureString() + SEPARATOR + object.getTextureIndex();
        }

        @Override
        public GrappleHookPoint readDataLine(String line) {
            String[] block = line.split(SEPARATOR);
            GrappleHookPoint object = new GrappleHookPoint(0, 0);
            object.setX( Float.parseFloat(block[1]) );
            object.setY( Float.parseFloat(block[2]) );
            object.setInteractDistance( Float.parseFloat(block[3]) );
            object.setTextureString(block[4]);
            object.setTextureIndex(Integer.parseInt(block[5]));
            return object;
        }
        
        
    }
    class AvacadoMachine extends GameObjectIOMachine<Avacado> {

        @Override
        public String makeDataLine(Avacado object) {
            return AVACADO + SEPARATOR + object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getWidth() + SEPARATOR + object.getHeight() + SEPARATOR + object.getRoomId() + SEPARATOR + object.getInteractDistance() + SEPARATOR + object.getTextureString() + SEPARATOR + object.getTextureIndex();
        }

        @Override
        public Avacado readDataLine(String line) {
            String[] block = line.split(SEPARATOR);
            Avacado object = new Avacado(0, 0, 0, 10, 10);
            object.setX( Float.parseFloat(block[1]) );
            object.setY( Float.parseFloat(block[2]) );
            object.setWidth( Float.parseFloat(block[3]) );
            object.setHeight( Float.parseFloat(block[4]) );
            object.setRoomId( Integer.parseInt(block[5]) );
            object.setInteractDistance( Float.parseFloat(block[6]));
            object.setTextureString(block[7]);
            object.setTextureIndex(Integer.parseInt(block[8]));
            return object;
        }
        
    }
    class AvacadoBinMachine extends GameObjectIOMachine<AvacadoBin> {

        @Override
        public String makeDataLine(AvacadoBin object) {
            return AVACADOBIN + SEPARATOR + object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getWidth() + SEPARATOR + object.getHeight() + SEPARATOR + object.getInteractDistance() + SEPARATOR + object.getTextureString() + SEPARATOR + object.getTextureIndex();
        }

        @Override
        public AvacadoBin readDataLine(String line) {
            String[] block = line.split(SEPARATOR);
            AvacadoBin object = new AvacadoBin(0, 0, 10, 10);
            object.setX( Float.parseFloat(block[1]) );
            object.setY( Float.parseFloat(block[2]) );
            object.setWidth( Float.parseFloat(block[3]) );
            object.setHeight( Float.parseFloat(block[4]) );
            object.setInteractDistance( Float.parseFloat(block[5]) );
            object.setTextureString(block[6]);
            object.setTextureIndex(Integer.parseInt(block[7]));
            return object;        
        }
        
    }
    
    class NormalDoorMachine extends GameObjectIOMachine<NormalDoor> {

        @Override
        public String makeDataLine(NormalDoor object) {
            return NORMALDOOR + SEPARATOR + object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getWidth() + SEPARATOR + object.getHeight() + SEPARATOR + object.getInteractDistance() + SEPARATOR + toBoolString(object.isOpen()) + SEPARATOR + object.getTextureString() + SEPARATOR + object.getTextureIndex();
        }

        @Override
        public NormalDoor readDataLine(String line) {
            String[] block = line.split(SEPARATOR);
            NormalDoor object = new NormalDoor(0, 0, 10, 10);
            object.setX( Float.parseFloat(block[1]) );
            object.setY( Float.parseFloat(block[2]) );
            object.setWidth( Float.parseFloat(block[3]) );
            object.setHeight( Float.parseFloat(block[4]) );
            object.setInteractDistance( Float.parseFloat(block[5]) );
            object.setOpened( toBoolFromString(block[6]) );
            object.setTextureString(block[7]);
            object.setTextureIndex(Integer.parseInt(block[8]));
            return object;         
        }
        
    }
    
    class TeleportDoorMachine extends GameObjectIOMachine<TeleportDoor> {

        @Override
        public String makeDataLine(TeleportDoor object) {
            return TELEPORTDOOR + SEPARATOR + object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getWidth() + SEPARATOR + object.getHeight() + SEPARATOR + object.getInteractDistance() + SEPARATOR + object.getTargetAreaID() + SEPARATOR + object.getXPlayerPos() + SEPARATOR + object.getYPlayerPos() + SEPARATOR + object.getTextureString() + SEPARATOR + object.getTextureIndex();
        }

        @Override
        public TeleportDoor readDataLine(String line) {
            String[] block = line.split(SEPARATOR);
            TeleportDoor object = new TeleportDoor(0, 0, 10, 10, 0, 0, 0);
            object.setX( Float.parseFloat(block[1]) );
            object.setY( Float.parseFloat(block[2]) );
            object.setWidth( Float.parseFloat(block[3]) );
            object.setHeight( Float.parseFloat(block[4]) );
            object.setInteractDistance( Float.parseFloat(block[5]) );
            object.setTargetAreaID( Integer.parseInt(block[6]) );
            object.setXPlayerPos( Float.parseFloat(block[7]) );
            object.setYPlayerPos( Float.parseFloat(block[8]) );
            object.setTextureString(block[9]);
            object.setTextureIndex(Integer.parseInt(block[10]));
            return object;         
        }
        
    }
    
    class HumanMachine extends GameObjectIOMachine<Human> {

        private BufferedReader reader;
        
        public void giveReader(BufferedReader reader) {
            this.reader=reader;
        }
        
        private void reset() {
            reader = null;
            writer = null;
        }
        
        private BufferedWriter writer;
        
        public void giveWriter(BufferedWriter writer) {
            this.writer = writer;
        }
        
        private WaypointMachine waypointMachine;
        
        public HumanMachine() {
            waypointMachine = new WaypointMachine();
        }
        
        @Override
        public String makeDataLine(Human object) {
            String firstLine = 
            HUMAN + SEPARATOR + object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getWidth() + SEPARATOR + object.getHeight() + SEPARATOR + object.getType().toString() + SEPARATOR + object.getState().toString() + SEPARATOR + object.getFaceDirection().getX() + SEPARATOR + object.getFaceDirection().getY() + SEPARATOR + object.getSpeed() + SEPARATOR + object.getFieldOfView() + SEPARATOR + object.getAlertTimer() + SEPARATOR + object.getSightRange() + SEPARATOR + object.getAttackDistance() + SEPARATOR + object.getTextureString() + SEPARATOR + object.getTextureIndex();
            firstLine = firstLine + "\n" + constructWayPointStrings(object);
            return firstLine;
        }

        @Override
        public Human readDataLine(String line) {
            String[] block = line.split(SEPARATOR);
            Human human = new Human(0, 0, 10, 10, Vector2.UNIT_X);
            
            human.setX( Float.parseFloat(block[1]) );
            human.setY( Float.parseFloat(block[2]) );
            human.setWidth( Float.parseFloat(block[3]) );
            human.setHeight( Float.parseFloat(block[4]) );
            human.setType( HumanType.fromString(block[5]) );
            human.setState( HumanState.fromString(block[6]) );
            
            Vector2 faceDir = new Vector2(
                    Float.parseFloat(block[7]),
                    Float.parseFloat(block[8])
            );
            human.setFaceDirection(faceDir);
            
            human.setSpeed( Float.parseFloat(block[9]) );
            human.setFieldOfView( Float.parseFloat(block[10] ));
            human.setAlertTimer( Integer.parseInt(block[11]) );
            human.setSightRange( Float.parseFloat( block[12] ));
            human.setAttackDistance( Float.parseFloat( block[13] ));
            human.setTextureString( block[14] );
            human.setTextureIndex( Integer.parseInt( block[15] ));
            
            //Now we have to start reading the way point lines following all that
            try {
                String currentLine = reader.readLine();
                ArrayList<Waypoint> patrolPath = new ArrayList<>();
                while(!currentLine.equals(WAYPOINTENDER)) {
                    //Keep reading lines and appending more waypoints
                    patrolPath.add(waypointMachine.readDataLine(currentLine));
                    currentLine = reader.readLine();
                }
                //When the end of the line is reached, put the path into the human
                Waypoint[] path = (Waypoint[])patrolPath.toArray(new Waypoint[patrolPath.size()]);
                human.setPatrolPath(path);
            } catch(Exception e) {
                Debugger.print("Error while reading a Waypoint list!", ERROR);
            }
            return human;
        }

        private String constructWayPointStrings(Human forHuman) {
            String str = "";
            Waypoint[] waypoints = forHuman.getPatrolPath();
            for (int i=0; i<waypoints.length; i++) {
                str = str + waypointMachine.makeDataLine(waypoints[i]) + "\n";
            }
            //Ending line
            str = str + WAYPOINTENDER; //"Way point ender"
            return str;
        }
        
        class WaypointMachine extends GameObjectIOMachine<Waypoint> {

            @Override
            public String makeDataLine(Waypoint object) {
                return object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getDelay();
            }

            @Override
            public Waypoint readDataLine(String line) {
                String[] block = line.split(SEPARATOR);
                Waypoint object = new Waypoint(0, 0, 0);
                object.setX( Float.parseFloat(block[0]) );
                object.setY( Float.parseFloat(block[1]) );
                object.setDelay( Integer.parseInt(block[2]) );
                return object;
            }
        }
    }
    
    class HayBaleMachine extends GameObjectIOMachine<HayBale> {

        @Override
        public String makeDataLine(HayBale object) {
            return HAYBALE + SEPARATOR + object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getWidth() + SEPARATOR + object.getHeight() + SEPARATOR + object.getInteractDistance() + SEPARATOR + object.getTextureString() + SEPARATOR + object.getTextureIndex();
        }

        @Override
        public HayBale readDataLine(String line) {
            String[] block = line.split(SEPARATOR);
            HayBale object = new HayBale(0, 0,10, 10);
            object.setX( Float.parseFloat(block[1]) );
            object.setY( Float.parseFloat(block[2]) );
            object.setWidth( Float.parseFloat(block[3]) );
            object.setHeight( Float.parseFloat(block[4]) );
            object.setInteractDistance( Float.parseFloat(block[5]) );
            object.setTextureString(block[6]);
            object.setTextureIndex(Integer.parseInt(block[7]));
            return object;          
        }
        
    }
    
    class WallMachine extends GameObjectIOMachine<Wall> {

        @Override
        public String makeDataLine(Wall object) {
            return WALL + SEPARATOR + object.getX() + SEPARATOR + object.getY() + SEPARATOR + object.getWidth() + SEPARATOR + object.getHeight() + SEPARATOR + object.getTextureString() + SEPARATOR + object.getTextureIndex();
        }

        @Override
        public Wall readDataLine(String line) {
            String[] block = line.split(SEPARATOR);
            Wall object = new Wall(0, 0, 10, 10);
            object.setX( Float.parseFloat(block[1]) );
            object.setY( Float.parseFloat(block[2]) );
            object.setWidth( Float.parseFloat(block[3]) );
            object.setHeight( Float.parseFloat(block[4]) );
            object.setTextureString(block[5]);
            object.setTextureIndex(Integer.parseInt(block[6]));
            return object;        
        }

    }
    
    private String toBoolString(boolean b) {
        if (true) {
            return TRUE;
        } else {
            return FALSE;
        }
    }
    
    private boolean toBoolFromString(String str) {
        if (str.equals(TRUE)) {
            return true;
        } else if (str.equals(FALSE)) {
            return false;
        } else {
            Debugger.print("Boolean Error in reading .cowarea file!", ERROR);
            return false;
        }
    }
}
    
