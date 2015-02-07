/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import app.model.*;
/**
 *
 * @author Henk
 */
public class Factory {
    
    
    public Factory() {
        
        // Alleenstaande models
        Location testLocation = src.Factory.createLocation("Van Doornenplantsoen 31", 1200);
        User testUser = src.Factory.createUser(1, "Jeffrey van Hoven", "Rehobothplantsoen 14", "2751BK", "Moerkapelle", "0697721421", "derp@live.nl", 2100, 29102, 10, 2);
        Tournament testTournament = src.Factory.createTournament(1, testLocation, "Test toernooi", "Een hele mooie toernooi", "25/02/2015", 20, 120);
        Masterclass testMasterclass = src.Factory.createMasterclass(1, 1200, 200, "27/02/2015");
        
        // Association models
        Participant testParticipant = src.Factory.createParticipant(testUser, testTournament, 1, "25/02/1995", "27/02/1995");
        Masterclass_participant testMasterparticipant = src.Factory.createMasterclassParticipant(testUser, testMasterclass, 1, "27/02/2015", "29/02/2015");
        
        // Debug test data
        System.out.println(testParticipant.getTournament().get("name"));
        System.out.println(testMasterparticipant.getMasterclass().get("start_date"));
        
    }
     
    
}
