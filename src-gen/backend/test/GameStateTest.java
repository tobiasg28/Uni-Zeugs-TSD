/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.test;

import backend.GameState;
import entities.Participation;
import entities.Square;
import entities.Troop;
import entities.TroopType;
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
}
