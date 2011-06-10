package frontend.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import entities.Base;
import entities.GameMap;
import entities.Participation;
import entities.Square;
import frontend.Acceptance;

public class AcceptanceTest {
	
	private GameMap map;
	private Participation p1,p2;
	
	@Before
    public void setUp(){
		/*Map*/
        map = new GameMap();
        map.setId(new Long(1));
        map.setMaxUsers(2);
        map.setName("Welt");
        
        /*Participation*/
        List<Participation> par = new ArrayList<Participation>();
        p1 = new Participation();
        p1.setId(new Long(1));
        par.add(p1);
        p2 = new Participation();
        p2.setId(new Long(2));
        par.add(p2);
        map.setParticipations(par);
        
        
        List<Square> squares = new ArrayList<Square>();
        for (int y = 0; y < 2*map.getMaxUsers(); y++) {
            for (int x = 0; x < 2*map.getMaxUsers(); x++) {
                Square s = new Square();
                s.setPositionX(x);
                s.setPositionY(y);
                squares.add(s);
            }
        }
        map.setSquares(squares);
	}
	
	@Test
	public void getSquareForBaseTest_shouldReturnSquare(){
		Assert.assertEquals(map.getSquares().get(4), Acceptance.getSquareForBase(map));
	}
	
	@Test
	public void getSquareForBaseTest_withOneBase_shouldReturnSquare(){
		List<Base> bases = new ArrayList<Base>();
		Base b = new Base();
		b.setSquare(map.getSquares().get(0));
		bases.add(b);
		map.getParticipations().get(0).setBases(bases);
		Assert.assertEquals(map.getSquares().get(13), Acceptance.getSquareForBase(map));
	}
	
	@Test
	public void getSquareForBaseTest_withTwoBase_shouldReturnSquare(){
		List<Base> bases = new ArrayList<Base>();
		Base b = new Base();
		b.setSquare(map.getSquares().get(0));
		bases.add(b);
		map.getParticipations().get(0).setBases(bases);
		bases = new ArrayList<Base>();
		b = new Base();
		b.setSquare(map.getSquares().get(15));
		bases.add(b);
		map.getParticipations().get(1).setBases(bases);
		Assert.assertEquals(map.getSquares().get(3), Acceptance.getSquareForBase(map));
	}
	
	@Test
	public void getSquareForBaseTest_withThreeBase_shouldReturnSquare(){
		List<Base> bases = new ArrayList<Base>();
		Base b = new Base();
		b.setSquare(map.getSquares().get(0));
		bases.add(b);
		map.getParticipations().get(0).setBases(bases);
		bases = new ArrayList<Base>();
		b = new Base();
		b.setSquare(map.getSquares().get(15));
		bases.add(b);
		b = new Base();
		b.setSquare(map.getSquares().get(3));
		bases.add(b);
		map.getParticipations().get(1).setBases(bases);
		Assert.assertEquals(map.getSquares().get(12), Acceptance.getSquareForBase(map));
	}

	@Test
	public void getSquareForBaseTest_withFourBase_shouldReturnNull(){
		List<Base> bases = new ArrayList<Base>();
		Base b = new Base();
		b.setSquare(map.getSquares().get(0));
		bases.add(b);
		map.getParticipations().get(0).setBases(bases);
		bases = new ArrayList<Base>();
		b = new Base();
		b.setSquare(map.getSquares().get(15));
		bases.add(b);
		b = new Base();
		b.setSquare(map.getSquares().get(3));
		bases.add(b);
		b = new Base();
		b.setSquare(map.getSquares().get(12));
		bases.add(b);
		map.getParticipations().get(1).setBases(bases);
		Assert.assertNull(Acceptance.getSquareForBase(map));
	}
}
