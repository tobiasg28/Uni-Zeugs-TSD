/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import dao.BaseDAO;
import dao.GameMapDAO;
import dao.ParticipationDAO;
import dao.ResourceAmountDAO;
import dao.ResourceDAO;
import dao.SquareDAO;
import entities.Base;
import entities.GameMap;
import entities.Participation;
import entities.Resource;
import entities.ResourceAmount;
import entities.Square;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import storage.DAOException;

/**
 *
 * @author wi3s3r
 */
public class GameStart {
    
    //Precondition: Types for Troops, Ressources, Buildings already exist
    public static Long newGame(String name, int maxPlayer){
        ResourceDAO rDao = new ResourceDAO();
        List<Resource> resources = null;
        try {
            resources = rDao.getAll();
        } catch (DAOException ex) {
            return null;
        }
        if(resources == null){
            //ERROR: No Ressources
            return null;
        }
        
        GameMapDAO mDao = new GameMapDAO();
        SquareDAO sDAO = new SquareDAO();
        
        GameMap map = new GameMap();
        map.setMaxUsers(maxPlayer);
        map.setName(name);
        List<Square> squares = new ArrayList<Square>();
        for(int x = 0; x < (maxPlayer * maxPlayer);x++){
            for(int y = 0; y < (maxPlayer * maxPlayer);y++){
                Square s = new Square();
                s.setPositionX(y);
                s.setPositionY(x);
                if((x + y == maxPlayer || x == y) && (x/2)*2==x){
                    if(x > resources.size()){
                        s.setPrivilegedFor(resources.get(x-resources.size()));
                    }else{
                        s.setPrivilegedFor(resources.get(resources.size()-x));
                    }
                }
                if((x == maxPlayer/2 && (y/2)*2!=y) || (y == maxPlayer/2 && (x/2)*2!=x)){
                    if(x > resources.size()){
                        s.setPrivilegedFor(resources.get(x-resources.size()));
                    }else{
                        s.setPrivilegedFor(resources.get(resources.size()-x));
                    }
                }
                try {
                    if(sDAO.create(s)){
                        squares.add(s);
                    }
                    else{
                        return null;
                    }
                } catch (DAOException ex) {
                    return null;
                }
            }
        }
        map.setSquares(squares);
        try {
            if(mDao.create(map) && map.getId()!=null){
                return map.getId();
            }
        } catch (DAOException ex) {
            return null;
        }
        return null;
    }
    
    //Precondition: Map exists + Types exist
    public static boolean newPlayer(User user, Long GameMapId){
        GameMapDAO mDao = new GameMapDAO();
        GameMap map = null;
        try {
            map = mDao.get(GameMapId);
        } catch (DAOException ex) {
            return false;
        }
        for(Participation p : map.getParticipations()){
            if(user.equals(p)){
                return false;
            }
        }
        Participation player = new Participation();
        player.setParticipant(user);
        player.setMap(map);
        ResourceDAO rDao = new ResourceDAO();
        List<Resource> resources = null;
        try {
            resources = rDao.getAll();
        } catch (DAOException ex) {
            return false;
        }
        if(resources == null){
            //ERROR: No Ressources
            return false;
        }
        ResourceAmountDAO raDao = new ResourceAmountDAO();
        List<ResourceAmount> resourceAmounts = new ArrayList<ResourceAmount>();
        for(Resource r : resources){
            ResourceAmount ra = new ResourceAmount();
            ra.setAmount(100);
            ra.setResource(r);
            try {
                if(raDao.create(ra)){
                    resourceAmounts.add(ra);
                }else{
                    return false;
                }
            } catch (DAOException ex) {
                return false;
            }
        }
        player.setResources(resourceAmounts);
        
        List<Base> bases = new ArrayList<Base>();
        BaseDAO bDao = new BaseDAO();
        Base start = new Base();
        //TODO: Cycle Conflict
        //start.setParticipation(player);
        start.setStarterBase(true);
        Square s = null;
        //TODO check for free place for a base
        if(s==null){
            return false;
        }
        start.setSquare(s);
        try {
            //TODO Question GameStep???
            if(bDao.create(start)){
                bases.add(start);
            }
        } catch (DAOException ex) {
            return false;
        }
        player.setBases(bases);
        //TODO Troops
        player.setTroops(null);
        ParticipationDAO pDao = new ParticipationDAO();
        try {
            if(pDao.create(player)){
                return true;
            }
        } catch (DAOException ex) {
            return false;
        }
        return false;
    }
    
}
