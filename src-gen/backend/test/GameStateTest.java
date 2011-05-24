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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author wi3s3r
 */
public class GameStateTest {
    
    @Before
    public void setUp(){
    
    }
    
    @After
    public void shutDown(){
    
    }
    
    @Test
    public void sortMapByValueTest(){
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(3, 3);
        map.put(8, 8);
        map.put(7, 7);
        map.put(1, 1);
        map.put(5, 5);
        map.put(4, 4);
        map.put(6, 6);
        map.put(10, 10);
        map.put(2, 2);
        map.put(9, 9);
        map = GameState.sortMapByValue(map);
        List list = new LinkedList(map.entrySet());
        Integer before = 11;
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            Assert.assertEquals(entry.getKey(), entry.getValue());
            Assert.assertTrue((Integer)entry.getValue() < before);
            before = (Integer) entry.getValue();
        }
    }
    
    @Test
    public void getWinnerTest_ShouldReturnWinner(){
        TroopType type = new TroopType();
        type.setStrength(1);
        
        Participation p1 = new Participation();
        p1.setId(new Long(1));
        Participation p2 = new Participation();
        p2.setId(new Long(2));
        Participation p3 = new Participation();
        p3.setId(new Long(3));
        
        List<Troop> troops = new LinkedList<Troop>();
        Troop t1 = new Troop();
        t1.setUpgradeLevel(type);
        t1.setParticipation(p1);
        troops.add(t1);
        Troop t2 = new Troop();
        t2.setUpgradeLevel(type);
        t2.setParticipation(p1);
        troops.add(t2);
        Troop t3 = new Troop();
        t3.setUpgradeLevel(type);
        t3.setParticipation(p1);
        troops.add(t3);
        Troop t4 = new Troop();
        t4.setUpgradeLevel(type);
        t4.setParticipation(p2);
        troops.add(t4);
        Troop t5 = new Troop();
        t5.setUpgradeLevel(type);
        t5.setParticipation(p3);
        troops.add(t5);
        
        Assert.assertEquals(p1, GameState.getWinner(troops));
    }
    
    @Test
    public void getWinnerTest_ShouldReturnNull(){
        TroopType type = new TroopType();
        type.setStrength(1);
        
        Participation p1 = new Participation();
        p1.setId(new Long(1));
        Participation p2 = new Participation();
        p2.setId(new Long(2));
       
        
        List<Troop> troops = new LinkedList<Troop>();
        Troop t1 = new Troop();
        t1.setUpgradeLevel(type);
        t1.setParticipation(p1);
        troops.add(t1);
        Troop t2 = new Troop();
        t2.setUpgradeLevel(type);
        t2.setParticipation(p1);
        troops.add(t2);
        Troop t3 = new Troop();
        t3.setUpgradeLevel(type);
        t3.setParticipation(p2);
        troops.add(t3);
        Troop t4 = new Troop();
        t4.setUpgradeLevel(type);
        t4.setParticipation(p2);
        troops.add(t4);
        
        
        Assert.assertEquals(null, GameState.getWinner(troops));
    }
    
    @Test
    public void getWinnerTest_ShouldReturnParticipation(){
        TroopType type = new TroopType();
        type.setStrength(1);
        
        Participation p1 = new Participation();
        p1.setId(new Long(1));
       
        
        List<Troop> troops = new LinkedList<Troop>();
        Troop t1 = new Troop();
        t1.setUpgradeLevel(type);
        t1.setParticipation(p1);
        troops.add(t1);
        Troop t2 = new Troop();
        t2.setUpgradeLevel(type);
        t2.setParticipation(p1);
        troops.add(t2);
        Troop t3 = new Troop();
        t3.setUpgradeLevel(type);
        t3.setParticipation(p1);
        troops.add(t3);
        Troop t4 = new Troop();
        t4.setUpgradeLevel(type);
        t4.setParticipation(p1);
        troops.add(t4);
        
        
        Assert.assertEquals(p1, GameState.getWinner(troops));
    }
    
    @Test
    public void createRessourcesTest(){
        List<Base> bases = new LinkedList<Base>();
        List<ResourceAmount> resources = new LinkedList<ResourceAmount>();
        
        Resource type1 = new Resource();
        type1.setId(new Long(1));
        type1.setName("Type1");
        Resource type2 = new Resource();
        type2.setId(new Long(2));
        type2.setName("Type2");
        Resource type3 = new Resource();
        type3.setId(new Long(3));
        type3.setName("Type3");
        
        ResourceAmount r1 = new ResourceAmount();
        r1.setAmount(0);
        r1.setResource(type1);
        resources.add(r1);
        ResourceAmount r2 = new ResourceAmount();
        r2.setAmount(10);
        r2.setResource(type2);
        resources.add(r2);
        ResourceAmount r3 = new ResourceAmount();
        r3.setAmount(100);
        r3.setResource(type3);
        resources.add(r3);
        
        Base b1 = new Base();
        Square s1 = new Square();
        s1.setId(new Long(1));
        s1.setPrivilegedFor(type2);
        b1.setSquare(s1);
        bases.add(b1);
        
        Base b2 = new Base();
        Square s2 = new Square();
        s2.setId(new Long(2));
        b2.setSquare(s2);
        List<Building> bus = new LinkedList<Building>();
        Building bu1 = new Building();
        bu1.setId(new Long(1));
        bu1.setUpgradeLevel(2);
        BuildingType bt1 = new BuildingType();
        bt1.setId(new Long(1));
        bt1.setProductionType(type3);
        bt1.setProductionRate(20);
        bu1.setType(bt1);
        bus.add(bu1);
        b2.setBuildings(bus);
        bases.add(b2);
        
        resources = GameState.createRessources(resources, bases);
        
        Assert.assertEquals(100, resources.get(0).getAmount());
        Assert.assertEquals(160, resources.get(1).getAmount());
        Assert.assertEquals(240, resources.get(2).getAmount());
    }
    
    @Test
    public void nextStateTest_ForOneParticipation(){
        /*GameStep*/
        GameStep before = new GameStep();
        before.setId(new Long(1));
        before.setDate(new Date(new Date().getTime()));
        
        GameStep current = new GameStep();
        current.setId(new Long(2));
        current.setDate(new Date(new Date().getTime()));
        
        
        /*Types*/
        TroopType tt = new TroopType();
        tt.setId(new Long(1));
        tt.setStrength(1);
        
        Resource rt1 = new Resource();
        rt1.setId(new Long(1));
        rt1.setName("r1");
        Resource rt2 = new Resource();
        rt2.setId(new Long(2));
        rt2.setName("r2");
        
        BuildingType bt1 = new BuildingType();
        bt1.setId(new Long(1));
        bt1.setProductionType(rt2);
        bt1.setProductionRate(20);
        
        /*MAP*/
        GameMap map = new GameMap();
        map.setId(new Long(1));
        map.setMaxUsers(3);
        map.setName("Welt");
        
        /*Squares*/
        List<Square> squares = new LinkedList<Square>();
        Square s1 = new Square();
        s1.setId(new Long(1));
        s1.setPositionX(0);
        s1.setPositionY(0);
        s1.setPrivilegedFor(rt1);
        Square s2 = new Square();
        s2.setId(new Long(1));
        s2.setPositionX(1);
        s2.setPositionY(0);
        Square s3 = new Square();
        s3.setId(new Long(1));
        s3.setPositionX(0);
        s3.setPositionY(1);
        Square s4 = new Square();
        s4.setId(new Long(1));
        s4.setPositionX(1);
        s4.setPositionY(1);
        s4.setPrivilegedFor(rt2);
        
        map.setSquares(squares);
        
        //Participations
        List<Participation> parts = new LinkedList<Participation>();
        Participation p1 = new Participation();
        p1.setId(new Long(1));
        
        
        
        List<Troop> troops = new LinkedList<Troop>();
        Troop t1 = new Troop();
        t1.setId(new Long(1));
        t1.setUpgradeLevel(tt);
        t1.setParticipation(p1);
        t1.setCurrentSquare(s1);
        t1.setLevelUpgradeFinish(before);
        t1.setTargetSquare(s2);
        t1.setMovementFinish(before);
        troops.add(t1);
        Troop t2 = new Troop();
        t2.setId(new Long(2));
        t2.setUpgradeLevel(tt);
        t2.setParticipation(p1);
        t2.setCurrentSquare(s1);
        t2.setLevelUpgradeFinish(null);
        t2.setTargetSquare(s2);
        t2.setMovementFinish(null);
        troops.add(t2);
        Troop t3 = new Troop();
        t3.setId(new Long(3));
        t3.setUpgradeLevel(tt);
        t3.setParticipation(p1);
        t3.setCurrentSquare(s1);
        t3.setLevelUpgradeFinish(null);
        t3.setTargetSquare(s2);
        t3.setMovementFinish(before);
        troops.add(t3);
        Troop t4 = new Troop();
        t4.setId(new Long(4));
        t4.setUpgradeLevel(tt);
        t4.setParticipation(p1);
        t4.setCurrentSquare(s1);
        t4.setLevelUpgradeFinish(before);
        t4.setTargetSquare(s2);
        t4.setMovementFinish(null);
        troops.add(t4);
        p1.setTroops(troops);
        
        List<ResourceAmount> resources = new LinkedList<ResourceAmount>();
        
        ResourceAmount r1 = new ResourceAmount();
        r1.setId(new Long(1));
        r1.setAmount(0);
        r1.setResource(rt1);
        resources.add(r1);
        ResourceAmount r2 = new ResourceAmount();
        r2.setId(new Long(2));
        r2.setAmount(10);
        r2.setResource(rt2);
        resources.add(r2);
        
        p1.setResources(resources);
        
        List<Base> bases = new LinkedList<Base>();
        Base b1 = new Base();
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
        bases.add(b1);
         
        p1.setBases(bases);
        
        parts.add(p1);
        
        map.setParticipations(parts);
        
        map = GameState.nextState(map, current);
        
        Assert.assertNotNull(map);
        Assert.assertEquals(s2, map.getParticipations().get(0).getTroops().get(0).getCurrentSquare());
        Assert.assertEquals(s1, map.getParticipations().get(0).getTroops().get(1).getCurrentSquare());
        Assert.assertEquals(s2, map.getParticipations().get(0).getTroops().get(2).getCurrentSquare());
        Assert.assertEquals(s1, map.getParticipations().get(0).getTroops().get(3).getCurrentSquare());
        
        //TODO Troops UpgradeLevel
        
        Assert.assertEquals(4, map.getParticipations().get(0).getBases().get(0).getBuildings().get(0).getUpgradeLevel());
        
        Assert.assertEquals(150, map.getParticipations().get(0).getResources().get(0).getAmount());
        Assert.assertEquals(170, map.getParticipations().get(0).getResources().get(1).getAmount());
    }
}
