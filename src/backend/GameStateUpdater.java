/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import entities.Base;
import entities.Building;
import entities.GameMap;
import entities.GameStep;
import entities.Participation;
import entities.ResourceAmount;
import entities.Square;
import entities.Troop;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 *
 * @author wi3s3r
 */
public class GameStateUpdater {

    private static GameStep currentGameStep;

    public static GameMap nextState(GameMap currentGameMap, GameStep cGameStep) {
        currentGameStep = cGameStep;
        GameMap nextGameMap = currentGameMap;
        
        //Changes for single User
        for (Participation p : nextGameMap.getParticipations()) {

            /*Creation*/
            //p.setBases(createBasesAndBuildings(p.getBases())); there is nothing to calculate
            //p.setTroops(createTroops(p.getTroops())); there is nothing to calculate
            //because the fontend shows everything that has an lower gamestep than the current gamestep
            p.setResources(createRessources(p.getResources(), p.getBases()));

            /*Upgrade*/
            p.setBases(upgradeBuildings(p.getBases()));
            p.setTroops(upgradeTroops(p.getTroops()));

            /*Move*/
            p.setTroops(moveTroops(p.getTroops()));

        }

        //Changes for more User
        //Every user has done his actions (upgrade...)

        //All troops on the map
        List<Troop> troops = new ArrayList<Troop>();
        for (Participation p : nextGameMap.getParticipations()) {
            troops.addAll(p.getTroops());
        }

        for (Participation p : nextGameMap.getParticipations()) {
            int myStrength, opponentStrength;
            List<Troop> attack = new ArrayList<Troop>();
            List<Troop> defense = new ArrayList<Troop>();

            //Attack Base
            for (Base b : p.getBases()) {
                myStrength = 100;
                opponentStrength = 0;
                for (Troop t : troops) {
                    if (b.getSquare().equals(t.getCurrentSquare())) {
                        if (t.getParticipation().equals(p)) {
                            myStrength += t.getUpgradeLevel().getStrength();
                            defense.add(t);
                        } else {
                            opponentStrength += t.getUpgradeLevel().getStrength();
                            attack.add(t);
                        }
                    }
                }
                if (opponentStrength > 0) {
                    if (myStrength >= opponentStrength) {
                        while (!attack.isEmpty()) {
                            Participation op = attack.get(0).getParticipation();
                            attack.get(0).getCurrentSquare().getTroops().remove(attack.get(0));
                            op.getTroops().remove(attack.get(0));
                            troops.remove(attack.get(0));
                            attack.get(0).setCreated(null);
                            attack.remove(0);
                        }
                    } else {
                        while (!defense.isEmpty()) {
                            p.getTroops().remove(defense.get(0));
                            defense.get(0).getCurrentSquare().getTroops().remove(defense.get(0));
                            troops.remove(defense.get(0));
                            defense.get(0).setCreated(null);
                            defense.remove(0);
                        }
                        if (!b.getStarterBase()) {
                            b.setDestroyed(currentGameStep);
                        } else {
                            List<Participation> plist = new ArrayList<Participation>();
                            for (Troop t : attack) {
                                if (!plist.contains(t.getParticipation())) {
                                    plist.add(t.getParticipation());
                                }
                            }
                            for (ResourceAmount r : p.getResources()) {
                                r.setAmount(r.getAmount() / plist.size() + 1);
                                for (Participation o : plist) {
                                    o.getResources().get(o.getResources().indexOf(r)).setAmount(o.getResources().get(o.getResources().indexOf(r)).getAmount() + r.getAmount());
                                }
                            }
                        }

                    }
                }
            }
            p.setBases(destroyBases(p.getBases()));


        }

        //Attack Troops vs Troops
        Map<Square, List<Troop>> fights = new HashMap<Square, List<Troop>>();
        for (Troop t : troops) {
        	//System.out.println(t.getCurrentSquare().getId());
            if (fights.containsKey(t.getCurrentSquare())) {
                fights.get(t.getCurrentSquare()).add(t);
            } else {
                ArrayList<Troop> list = new ArrayList<Troop>();
                list.add(t);
                fights.put(t.getCurrentSquare(), list);
            }
        }
        //System.out.println(fights.size());
        for (Square s : fights.keySet()) {
            Participation winner = getWinner(fights.get(s));
            if (winner != null) {
                //Just the winner troops stay alive
                for (Troop t : fights.get(s)) {
                    if (!t.getParticipation().equals(winner)) {
                        Participation p = t.getParticipation();
                        p.getTroops().remove(t);
                        t.getCurrentSquare().getTroops().remove(t);
                        t.setCreated(null);
                    }
                }
            } else {
                //All losers
                for (Troop t : fights.get(s)) {
                    Participation p = t.getParticipation();
                    p.getTroops().remove(t);
                    t.getCurrentSquare().getTroops().remove(t);
                    t.setCreated(null);
                }
            }
        }

        return nextGameMap;
    }

    /**
     * Ressources: +100 Standard
     *             + XX for every Building that produces that Resource
     *             + 50 for every privileged Square for that Resource
     * @param resources
     * @param bases
     * @return 
     */
    public static List<ResourceAmount> createRessources(List<ResourceAmount> resources, List<Base> bases) {
        List<ResourceAmount> nextResources = resources;

        for (ResourceAmount r : nextResources) {
            //Standard
            r.setAmount(r.getAmount() + 1);
            for (Base b : bases) {
                //ProductionBuildings
                if (b.getBuildings() != null) {
                    for (Building bu : b.getBuildings()) {
                        if (bu.getType().getProductionType().equals(r.getResource())) {
                            r.setAmount(r.getAmount() + (bu.getType().getProductionRate() * bu.getUpgradeLevel()));
                        }
                    }
                }
                //Privileged Squares
                if (b.getSquare().getPrivilegedFor() != null && b.getSquare().getPrivilegedFor().equals(r.getResource())) {
                    r.setAmount(r.getAmount() + 5);
                }
            }
        }

        return nextResources;
    }

    /**
     * Upgrade: if LevelUpgradeFinish == null then no upgrade
     *          if LevelUpgradeFinish <= gamestep then upgradelevel + 1
     * @param bases
     * @return 
     */
    private static List<Base> upgradeBuildings(List<Base> bases) {
        List<Base> nextBases = bases;

        for (Base b : nextBases) {
            for (Building bu : b.getBuildings()) {
                if (bu.getLevelUpgradeFinish() != null && bu.getLevelUpgradeFinish().getDate().getTime() <= currentGameStep.getDate().getTime()) {
                    bu.setUpgradeLevel(bu.getUpgradeLevel() + 1);
                    bu.setLevelUpgradeFinish(null);
                }
            }
        }

        return nextBases;
    }

    /**
     * Upgrade: if LevelUpgradeFinish == null then no upgrade
     *          if LevelUpgradeFinish <= gamestep then upgradelevel = upgradelevel.nextLevel
     * @param troops
     * @return 
     */
    private static List<Troop> upgradeTroops(List<Troop> troops) {
        List<Troop> nextTroops = troops;
        System.out.println("Current GameStep: "+currentGameStep.getDate().getTime());
        //System.out.println("Troops Size: "+troops.size());
        for (Troop t : nextTroops) {
        	if(t.getLevelUpgradeFinish()!=null)
        		System.out.println("Troop"+t.getId() +" Upgrade: "+t.getLevelUpgradeFinish().getDate().getTime());
            if (t.getLevelUpgradeFinish() != null && t.getLevelUpgradeFinish().getDate().getTime() <= currentGameStep.getDate().getTime()) {
            	t.setUpgradeLevel(t.getUpgradeLevel().getNextLevel());
                t.setLevelUpgradeFinish(null);
            }
        }

        return nextTroops;
    }

    /**
     * Troops move to targetsquare if getMovementFinish != null and <= currentGameStep
     * @param troops
     * @return 
     */
    private static List<Troop> moveTroops(List<Troop> troops) {
        List<Troop> nextTroops = troops;
        
        for (Troop t : nextTroops) {
        	if(t.getMovementFinish()!=null)
        		System.out.println("Troop"+t.getId() +" Movement: "+t.getMovementFinish().getDate().getTime());
            if (t.getMovementFinish() != null && t.getMovementFinish().getDate().getTime() <= currentGameStep.getDate().getTime()) {
            	t.getCurrentSquare().getTroops().remove(t);
                t.setCurrentSquare(t.getTargetSquare());
                if(t.getCurrentSquare().getTroops() == null){
                	t.getCurrentSquare().setTroops(new ArrayList<Troop>());
                }
                t.getCurrentSquare().getTroops().add(t);
                t.setMovementFinish(null);
            }
        }

        return nextTroops;
    }

    private static List<Base> destroyBases(List<Base> bases) {
        List<Base> nextBases = bases;
        for (int i = 0; i < nextBases.size(); i++) {
            if (nextBases.get(i).getDestroyed() != null && nextBases.get(i).getDestroyed().getDate().getTime() <= currentGameStep.getDate().getTime()) {
            	nextBases.get(i).getSquare().setBase(null);
            	nextBases.get(i).setCreated(null);
            	nextBases.remove(i);
            }
        }

        return nextBases;
    }

    public static Participation getWinner(List<Troop> fighters) {
        Map<Participation, Integer> strengths = new HashMap<Participation, Integer>();
        for (Troop t : fighters) {
            if (strengths.containsKey(t.getParticipation())) {
                strengths.put(t.getParticipation(), strengths.get(t.getParticipation()) + t.getUpgradeLevel().getStrength());
            } else {
                strengths.put(t.getParticipation(), t.getUpgradeLevel().getStrength());
            }
        }
        //Just troops of one participation
        if (strengths.size() < 2) {
            return strengths.keySet().iterator().next();
        }
        strengths = Util.sortMapByValue(strengths);
        Iterator<Participation> it = strengths.keySet().iterator();
        Participation winner = (Participation) it.next();
        //The winner

        if (strengths.get(winner) > strengths.get(it.next())) {
            return winner;
        }
        //two troops have the same strength
        return null;
    }
}
