/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.test;

import backend.GameState;
import entities.Base;
import entities.Building;
import entities.BuildingType;
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
    public void nextStateTest(){
        //TODO nextStateTest
    }
}
