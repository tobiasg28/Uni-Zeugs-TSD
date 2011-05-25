/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.test;

import backend.GameState;
import entities.Base;
import entities.Building;
import entities.BuildingType;
import entities.GameMap;
import entities.GameStep;
import entities.Participation;
import entities.Resource;
import entities.ResourceAmount;
import entities.Square;
import entities.Troop;
import entities.TroopType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author wi3s3r
 */
public class GameStateTest {
    
    private GameStep before, current;
    private TroopType tt;
    private Resource rt1, rt2;
    private BuildingType bt1;
    private GameMap map;
    private Square s1,s2,s3,s4;
    private Troop t1,t2,t3,t4,t5,t6,t7,t8;
    private Participation p1, p2;
    private ResourceAmount r1, r2, r3, r4;
    private Base b1, b2;
    
    @Before
    public void setUp(){
        /*GameStep*/
        before = new GameStep();
        before.setId(new Long(1));
        before.setDate(new Date(new Date().getTime()));
        
        current = new GameStep();
        current.setId(new Long(2));
        current.setDate(new Date(new Date().getTime()));
        
        /*Types*/
        tt = new TroopType();
        tt.setId(new Long(1));
        tt.setStrength(1);
        
        rt1 = new Resource();
        rt1.setId(new Long(1));
        rt1.setName("r1");
        rt2 = new Resource();
        rt2.setId(new Long(2));
        rt2.setName("r2");
        
        bt1 = new BuildingType();
        bt1.setId(new Long(1));
        bt1.setProductionType(rt2);
        bt1.setProductionRate(20);
        
        /*MAP*/
        map = new GameMap();
        map.setId(new Long(1));
        map.setMaxUsers(3);
        map.setName("Welt");
        
        /*Participation*/
        p1 = new Participation();
        p1.setId(new Long(1));
        p2 = new Participation();
        p2.setId(new Long(2));
        
        s1 = new Square();
        s1.setId(new Long(1));
        s1.setPositionX(0);
        s1.setPositionY(0);
        s1.setPrivilegedFor(rt1);
        s2 = new Square();
        s2.setId(new Long(1));
        s2.setPositionX(1);
        s2.setPositionY(0);
        s3 = new Square();
        s3.setId(new Long(1));
        s3.setPositionX(0);
        s3.setPositionY(1);
        s4 = new Square();
        s4.setId(new Long(1));
        s4.setPositionX(1);
        s4.setPositionY(1);
        s4.setPrivilegedFor(rt2);
        
        /*Troops*/
        t1 = new Troop();
        t1.setId(new Long(1));
        t1.setUpgradeLevel(tt);
        t1.setCurrentSquare(s1);
        t1.setLevelUpgradeFinish(before);
        t1.setTargetSquare(s2);
        t1.setMovementFinish(before);
        t2 = new Troop();
        t2.setId(new Long(2));
        t2.setUpgradeLevel(tt);
        t2.setCurrentSquare(s1);
        t2.setLevelUpgradeFinish(null);
        t2.setTargetSquare(s2);
        t2.setMovementFinish(null);
        t3 = new Troop();
        t3.setId(new Long(3));
        t3.setUpgradeLevel(tt);
        t3.setCurrentSquare(s1);
        t3.setLevelUpgradeFinish(null);
        t3.setTargetSquare(s2);
        t3.setMovementFinish(before);
        t4 = new Troop();
        t4.setId(new Long(4));
        t4.setUpgradeLevel(tt);
        t4.setCurrentSquare(s1);
        t4.setLevelUpgradeFinish(before);
        t4.setTargetSquare(s2);
        t4.setMovementFinish(null);
        t5 = new Troop();
        t5.setId(new Long(5));
        t5.setUpgradeLevel(tt);
        t5.setCurrentSquare(s4);
        t5.setLevelUpgradeFinish(before);
        t5.setTargetSquare(s2);
        t5.setMovementFinish(before);
        t6 = new Troop();
        t6.setId(new Long(6));
        t6.setUpgradeLevel(tt);
        t6.setCurrentSquare(s4);
        t6.setLevelUpgradeFinish(null);
        t6.setTargetSquare(s2);
        t6.setMovementFinish(null);
        t7 = new Troop();
        t7.setId(new Long(7));
        t7.setUpgradeLevel(tt);
        t7.setCurrentSquare(s4);
        t7.setLevelUpgradeFinish(null);
        t7.setTargetSquare(s2);
        t7.setMovementFinish(null);
        t8 = new Troop();
        t8.setId(new Long(8));
        t8.setUpgradeLevel(tt);
        t8.setCurrentSquare(s4);
        t8.setLevelUpgradeFinish(before);
        t8.setTargetSquare(s2);
        t8.setMovementFinish(null);
        
        /*Ressources*/
        r1 = new ResourceAmount();
        r1.setId(new Long(1));
        r1.setAmount(0);
        r1.setResource(rt1);
        r2 = new ResourceAmount();
        r2.setId(new Long(2));
        r2.setAmount(10);
        r2.setResource(rt2); 
        r3 = new ResourceAmount();
        r3.setId(new Long(3));
        r3.setAmount(0);
        r3.setResource(rt1);
        r4 = new ResourceAmount();
        r4.setId(new Long(4));
        r4.setAmount(10);
        r4.setResource(rt2);
        
        
        /*Bases with Buidlings*/
        b1 = new Base();
        b1.setId(new Long(1));
        b1.setSquare(s1);
        b1.setDestroyed(null);
        b1.setStarterBase(true);
        List<Building> bus = new LinkedList<Building>();
        Building bu1 = new Building();
        bu1.setId(new Long(1));
        bu1.setUpgradeLevel(3);
        bu1.setLevelUpgradeFinish(before);
        bu1.setType(bt1);
        bus.add(bu1);
        b1.setBuildings(bus);
        b2 = new Base();
        b2.setId(new Long(2));
        b2.setSquare(s4);
        b2.setDestroyed(null);
        b2.setStarterBase(true);
        List<Building> bus2 = new LinkedList<Building>();
        Building bu2 = new Building();
        bu2.setId(new Long(2));
        bu2.setUpgradeLevel(3);
        bu2.setLevelUpgradeFinish(before);
        bu2.setType(bt1);
        bus2.add(bu2);
        b2.setBuildings(bus2);
    }
    
    @After
    public void shutDown(){
    
    }
    
    
    @Test
    public void nextStateTest_ForOneParticipation(){ 
        
        List<Troop> troops = new LinkedList<Troop>();
        troops.add(t1);
        t1.setParticipation(p1);
        troops.add(t2);
        t2.setParticipation(p1);
        troops.add(t3);
        t3.setParticipation(p1);
        troops.add(t4);
        t4.setParticipation(p1);
        p1.setTroops(troops);
        
        //Ressources
        List<ResourceAmount> resources = new LinkedList<ResourceAmount>();
        resources.add(r1);
        resources.add(r2);
        p1.setResources(resources);
        
        //Bases
        List<Base> bases = new LinkedList<Base>();
        bases.add(b1); 
        p1.setBases(bases);
        
        //Squares
        List<Square> squares = new LinkedList<Square>();
        squares.add(s1);
        squares.add(s2);
        squares.add(s3);
        squares.add(s4);
        map.setSquares(squares);
        
        //Participations
        List<Participation> parts = new LinkedList<Participation>();
        parts.add(p1);
        map.setParticipations(parts);
        
        map = GameState.nextState(map, current);
        
        Assert.assertNotNull(map);
        Assert.assertEquals(s2, map.getParticipations().get(0).getTroops().get(0).getCurrentSquare());
        Assert.assertEquals(s1, map.getParticipations().get(0).getTroops().get(1).getCurrentSquare());
        Assert.assertEquals(s2, map.getParticipations().get(0).getTroops().get(2).getCurrentSquare());
        Assert.assertEquals(s1, map.getParticipations().get(0).getTroops().get(3).getCurrentSquare());
        
        //TODO Troops UpgradeLevel Assertions
        
        Assert.assertEquals(4, map.getParticipations().get(0).getBases().get(0).getBuildings().get(0).getUpgradeLevel());
        
        Assert.assertEquals(150, map.getParticipations().get(0).getResources().get(0).getAmount());
        Assert.assertEquals(170, map.getParticipations().get(0).getResources().get(1).getAmount());
    }
    
    @Test
    public void nextStateTest_ForTwoParticipation(){
         
        List<Troop> troops = new LinkedList<Troop>();
        troops.add(t1);
        t1.setParticipation(p1);
        troops.add(t2);
        t2.setParticipation(p1);
        troops.add(t3);
        t3.setParticipation(p1);
        troops.add(t4);
        t4.setParticipation(p1);
        p1.setTroops(troops);
        
        //Ressources
        List<ResourceAmount> resources = new LinkedList<ResourceAmount>();
        resources.add(r1);
        resources.add(r2);
        p1.setResources(resources);
        
        //Bases
        List<Base> bases = new LinkedList<Base>();
        bases.add(b1); 
        p1.setBases(bases);
        
        
          
        List <Troop> troops2 = new LinkedList<Troop>();
        troops2.add(t5);
        t5.setParticipation(p2);
        troops2.add(t6);
        t6.setParticipation(p2);
        troops2.add(t7);
        t7.setParticipation(p2);
        troops2.add(t8);
        t8.setParticipation(p2);
        p2.setTroops(troops2);
        
        List<ResourceAmount> resources2 = new LinkedList<ResourceAmount>();
        resources2.add(r3);
        resources2.add(r4);
        p2.setResources(resources2);
        
        List<Base> bases2 = new LinkedList<Base>();
        bases2.add(b2); 
        p2.setBases(bases2);
        
        //Squares
        List<Square> squares = new LinkedList<Square>();
        squares.add(s1);
        squares.add(s2);
        squares.add(s3);
        squares.add(s4);
        map.setSquares(squares);
        
        //Participations
        List<Participation> parts = new LinkedList<Participation>();
        parts.add(p1);
        parts.add(p2);
        map.setParticipations(parts);
        
        map = GameState.nextState(map, current);
        
        Assert.assertNotNull(map);
        
        //TODO Troops UpgradeLevel Assertions
        
        Assert.assertEquals(4, map.getParticipations().get(0).getBases().get(0).getBuildings().get(0).getUpgradeLevel());
        
        Assert.assertEquals(150, map.getParticipations().get(0).getResources().get(0).getAmount());
        Assert.assertEquals(170, map.getParticipations().get(0).getResources().get(1).getAmount());
        
        //TODO Troops UpgradeLevel Assertions
        
        Assert.assertEquals(4, map.getParticipations().get(1).getBases().get(0).getBuildings().get(0).getUpgradeLevel());
        
        Assert.assertEquals(100, map.getParticipations().get(1).getResources().get(0).getAmount());
        Assert.assertEquals(220, map.getParticipations().get(1).getResources().get(1).getAmount());
        
        
        //After the war
        Assert.assertEquals(4, map.getParticipations().get(0).getTroops().size());
        Assert.assertEquals(s2, map.getParticipations().get(0).getTroops().get(0).getCurrentSquare());
        Assert.assertEquals(s1, map.getParticipations().get(0).getTroops().get(1).getCurrentSquare());
        Assert.assertEquals(s2, map.getParticipations().get(0).getTroops().get(2).getCurrentSquare());
        Assert.assertEquals(s1, map.getParticipations().get(0).getTroops().get(3).getCurrentSquare());
        
        Assert.assertEquals(3, map.getParticipations().get(1).getTroops().size());
        Assert.assertEquals(s4, map.getParticipations().get(1).getTroops().get(0).getCurrentSquare());
        Assert.assertEquals(s4, map.getParticipations().get(1).getTroops().get(1).getCurrentSquare());
        Assert.assertEquals(s4, map.getParticipations().get(1).getTroops().get(2).getCurrentSquare());
        
    }
}
