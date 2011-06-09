/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import dao.GameMapDAO;
import dao.ResourceDAO;
import dao.SquareDAO;
import entities.GameMap;
import entities.Resource;
import entities.Square;
import java.util.ArrayList;
import java.util.List;
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
                s.setPositionX(x);
                s.setPositionY(y);
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
    
    public static Long newPlayer(Long GameMapId){
        //TODO
        return null;
    }
    
}
