/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;


import dao.GameMapDAO;
import dao.ParticipationDAO;
import entities.Base;
import entities.GameMap;
import entities.Participation;
import entities.ResourceAmount;
import entities.Square;
import entities.Troop;
import entities.TroopType;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import storage.DAOException;


/**
 *
 * @author wi3s3r
 */
public class Acceptance {
    
    public static boolean enoughResource(List<ResourceAmount> request, Long participationId){
        ParticipationDAO dao = new ParticipationDAO();
        Participation p = null;
        try {
            p = dao.get(participationId);
        } catch (DAOException ex) {
            return false;
        }
        if(p == null){
            return false;
        }
        for(ResourceAmount r : request){
            if(p.getResources().contains(r)){
                if(r.getAmount() > p.getResources().get(p.getResources().indexOf(r)).getAmount()){
                    return false;
                }
            }else{
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean existsTroopUpgradeLevel(TroopType type){
        if(type.getNextLevel() == null){
            return false;
        }
        return true;
    }
    
    public static boolean isSquareFree(Square square, Long GameMapId){
        GameMapDAO dao = new GameMapDAO();
        GameMap map = null;
        try {
            map = dao.get(GameMapId);
        } catch (DAOException ex) {
            return false;
        }
        if(map == null){
            return false;
        }
        for(Participation p : map.getParticipations()){
            for(Base b : p.getBases()){
                if(b.getSquare().equals(square)){
                    return false;
                }
            }
            for(Troop t : p.getTroops()){
                if(t.getCurrentSquare().equals(square)){
                    return false;
                }
            }
        }
        return true;
    }
    
}
