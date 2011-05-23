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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wi3s3r
 */
class GameState {

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
            //TODO losing Resources
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
                            op.getTroops().remove(attack.get(0));
                            troops.remove(attack.get(0));
                            attack.remove(0);
                        }
                    } else {
                        while (!defense.isEmpty()) {
                            p.getTroops().remove(defense.get(0));
                            troops.remove(defense.get(0));
                            defense.remove(0);
                        }
                        if (!b.getStarterBase()) {
                            b.setDestroyed(currentGameStep);
                        }
                    }
                }
            }
            p.setBases(destroyBases(p.getBases()));


        }

        //Attack Troops vs Troops
        Map<Square, List<Troop>> fights = new HashMap<Square, List<Troop>>();
        for (Troop t : troops) {
            if (fights.containsKey(t.getCurrentSquare())) {
                fights.get(t.getCurrentSquare()).add(t);
            } else {
                ArrayList<Troop> list = new ArrayList<Troop>();
                list.add(t);
                fights.put(t.getCurrentSquare(), list);
            }
        }
        for (Square s : fights.keySet()) {
            Participation winner = getWinner(fights.get(s));
            if (winner != null) {
                //Just the winner troops stay alive
                for (Troop t : fights.get(s)) {
                    if (!t.getParticipation().equals(winner)) {
                        Participation p = t.getParticipation();
                        p.getTroops().remove(t);
                    }
                }
            } else {
                //All losers
                for (Troop t : fights.get(s)) {
                    Participation p = t.getParticipation();
                    p.getTroops().remove(t);
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
    private static List<ResourceAmount> createRessources(List<ResourceAmount> resources, List<Base> bases) {
        List<ResourceAmount> nextResources = resources;

        for (ResourceAmount r : nextResources) {
            //Standard
            r.setAmount(r.getAmount() + 100);
            for (Base b : bases) {
                //ProductionBuildings
                for (Building bu : b.getBuildings()) {
                    if (bu.getType().getProductionType().equals(r.getResource())) {
                        r.setAmount(r.getAmount() + (bu.getType().getProductionRate() * bu.getUpgradeLevel()));
                    }
                }
                //Privileged Squares
                if (b.getSquare().getPrivilegedFor().equals(r.getResource())) {
                    r.setAmount(r.getAmount() + 50);
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
     * TODO upgrade == next Type???
     * @param troops
     * @return 
     */
    private static List<Troop> upgradeTroops(List<Troop> troops) {
        List<Troop> nextTroops = troops;

        for (Troop t : nextTroops) {
            if (t.getLevelUpgradeFinish() != null && t.getLevelUpgradeFinish().getDate().getTime() <= currentGameStep.getDate().getTime()) {
                //TODO What should the function do here???
                //t.setUpgradeLevel(t.getUpgradeLevel());
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
            if (t.getMovementFinish() != null && t.getMovementFinish().getDate().getTime() <= currentGameStep.getDate().getTime()) {
                t.setCurrentSquare(t.getTargetSquare());
                t.setLevelUpgradeFinish(null);
            }
        }

        return nextTroops;
    }

    private static List<Base> destroyBases(List<Base> bases) {
        List<Base> nextBases = bases;

        for (int i = 0; i < nextBases.size(); i++) {
            if (nextBases.get(i).getDestroyed() != null && nextBases.get(i).getDestroyed().getDate().getTime() <= currentGameStep.getDate().getTime()) {
                nextBases.remove(i);
            }
        }

        return nextBases;
    }

    private static Participation getWinner(List<Troop> fighters) {
        Map<Participation, Integer> strengths = new HashMap<Participation, Integer>();
        for (Troop t : fighters) {
            if (strengths.containsKey(t.getParticipation())) {
                strengths.put(t.getParticipation(), strengths.get(t.getParticipation()) + t.getUpgradeLevel().getStrength());
            } else {
                strengths.put(t.getParticipation(), t.getUpgradeLevel().getStrength());
            }
        }
        Participation winner = null;
        if(strengths.size() < 2){
            return strengths.keySet().iterator().next();
        }
        strengths = sortMapByValue(strengths);
        //TODO
        
        return winner;
    }

    private static Map sortMapByValue(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {

            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
