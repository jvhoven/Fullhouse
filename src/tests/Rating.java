/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import app.model.*;
import src.Factory;

/**
 *
 * @author Jeffrey
 */
public final class Rating {
    
  
   /* public Rating(){
        
        Location location = Factory.createLocation("Van Doornenplantsoen 31", 1200);
        Tournament tournament = Factory.createTournament(1, location, "Test toernooi", "Een hele mooie toernooi", "25/02/2015", 20, 120);
        
        User userOne = Factory.createUser(382, "Milan Fazzi", "Teststraat 10", "2000KK", "DIF", "00000000", "test@live.nl", 5733, 1833, 3, 0);
        User userTwo = Factory.createUser(383, "Henk de Vries", "Teststraat 10", "2000KK", "DIF", "00000000", "test@live.nl", 5733, 1833, 3, 0);
        User userThree = Factory.createUser(382, "Eric Johansen", "Teststraat 10", "2000KK", "DIF", "00000000", "test@live.nl", 5733, 1833, 3, 0);
       
        Participant[] participants = {
            Factory.createParticipant(userOne, tournament, 1, "25/02/2015", "12/01/2015"),
            Factory.createParticipant(userTwo, tournament, 1, "25/02/2015", "12/01/2015"),
            Factory.createParticipant(userThree, tournament, 1, "25/02/2015", "12/01/2015")
        };
        
        automateTest(10, participants, 3);
    }
        
    public void automateTest(int rounds, Participant[] participants, int tableSize){
        
        //Table table = Factory.createTable(tableSize);
        
        System.out.println(participants.length);
        
        for(int i = 0; i < rounds; i++) {      
            
            table.newSession();
        
            for(Participant participant : participants) {
                table.session.addParticipant(participant);
            }
            
            System.out.println("\n --- GAME NUMBER [" + i + "] ---- \n");
//            table.session.finish(randomWithRange(0, tableSize - 1));

            for(Participant participant : participants) {
                
                User user = participant.getUser();
                
                System.out.println("Naam\t\t Rating\t Totale score\t Gewonnen\t Verloren");
                System.out.println(user.get("name") + "\t " + user.get("rating") + "\t " + user.get("total_score") + "\t\t " + user.get("wins") + "\t\t " + user.get("losses"));
            }
            
            table.removeSession();
        }        
    }
    
    public int randomWithRange(int min, int max)
    {
       int range = (max - min) + 1;     
       return (int)(Math.random() * range) + min;
    }*/
    
}
