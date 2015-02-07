package tests;

import app.model.Location;
import app.model.Participant;
import app.model.Tournament;
import app.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import src.Factory;

/**
 *
 * @author Henk
 */
public class Matchmaking {
    
    ArrayList<User> users;
    ArrayList<Participant> participants;
    
    public Matchmaking(){
        
        users = new ArrayList();
        participants = new ArrayList();
        Location location = Factory.createLocation("Van Doornenplantsoen 31", 1200);
        Tournament tournament = Factory.createTournament(1, location, "Test toernooi", "Een hele mooie toernooi", "25/02/2015", 20, 120, 4);
        
        createUsers();  
        createParticipants(tournament);  
        
        // Shuffle alle users door elkaar
        long seed = System.nanoTime();
        Collections.shuffle(users, new Random(seed));
        Collections.shuffle(users, new Random(seed));
        
        src.rating.Matchmaking matchmake = new src.rating.Matchmaking(tournament, participants);
//       src.rating.Matchmaking.sort(users);
        
    }
      
    // <editor-fold defaultstate="collapsed" desc="Click om brackets te zien">
    private void createUsers(){
        
        // Beginner
        users.add(Factory.createUser(1, "Jeffrey1", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 0, 1000, 0, 0));
        users.add(Factory.createUser(1, "Jeffrey2", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 0, 1000, 0, 0));
        users.add(Factory.createUser(1, "Jeffrey3", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 0, 1000, 0, 0));
        
        // Noobs
        users.add(Factory.createUser(1, "Jeffrey4", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 8292, 509, 1, 9));
        users.add(Factory.createUser(1, "Jeffrey5", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 6112, 473, 0, 7));
        users.add(Factory.createUser(1, "Jeffrey6", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4563, 512, 0, 5));
        
        // Casuals
        users.add(Factory.createUser(1, "Jeffrey7", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 1000, 1400, 1, 0));
        users.add(Factory.createUser(1, "Jeffrey8", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 1000, 1400, 1, 0));
        users.add(Factory.createUser(1, "Jeffrey9", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 1000, 1400, 1, 0));
        
        // Semi goede mensen
        users.add(Factory.createUser(1, "Jeffrey10", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 2400, 1600, 2, 0));
        users.add(Factory.createUser(1, "Jeffrey11", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 2400, 1600, 2, 0));
        users.add(Factory.createUser(1, "Jeffrey12", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 2400, 1600, 2, 0));
        
        // Goede mensen
        users.add(Factory.createUser(1, "Jeffrey13", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4000, 1733, 3, 0));
        users.add(Factory.createUser(1, "Jeffrey14", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4000, 1733, 3, 0));
        users.add(Factory.createUser(1, "Jeffrey15", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4000, 1733, 3, 0));
        
        // Semi pro
        users.add(Factory.createUser(1, "Jeffrey16", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 7566, 2291, 4, 0));
        users.add(Factory.createUser(1, "Jeffrey17", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 7566, 2091, 3, 1));
        users.add(Factory.createUser(1, "Jeffrey18", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 7566, 2291, 4, 0));
        
        // Beginner kopie
        users.add(Factory.createUser(1, "Jeffrey19", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 0, 1000, 0, 0));
        users.add(Factory.createUser(1, "Jeffrey20", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 0, 1000, 0, 0));
        users.add(Factory.createUser(1, "Jeffrey21", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 0, 1000, 0, 0));
        
        // Noobs kopie
        users.add(Factory.createUser(1, "Jeffrey22", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 8292, 509, 1, 9));
        users.add(Factory.createUser(1, "Jeffrey23", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 6112, 473, 0, 7));
        users.add(Factory.createUser(1, "Jeffrey24", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4563, 512, 0, 5));
        
        // Casuals kopie
        users.add(Factory.createUser(1, "Jeffrey25", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 1000, 1400, 1, 0));
        users.add(Factory.createUser(1, "Jeffrey26", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 1000, 1400, 1, 0));
        users.add(Factory.createUser(1, "Jeffrey27", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 1000, 1400, 1, 0));
        
        // Semi goede mensen kopie
        users.add(Factory.createUser(1, "Jeffrey28", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 2400, 1600, 2, 0));
        users.add(Factory.createUser(1, "Jeffrey29", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 2400, 1600, 2, 0));
        users.add(Factory.createUser(1, "Jeffrey30", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 2400, 1600, 2, 0));
        
        // Goede mensen kopie
        users.add(Factory.createUser(1, "Jeffrey31", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4000, 1733, 3, 0));
        users.add(Factory.createUser(1, "Jeffrey32", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4000, 1733, 3, 0));
        users.add(Factory.createUser(1, "Jeffrey33", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4000, 1733, 3, 0));
        
        // Semi pro kopie
        users.add(Factory.createUser(1, "Jeffrey34", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 7566, 2291, 4, 0));
        users.add(Factory.createUser(1, "Jeffrey35", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 7566, 2091, 3, 1));
        users.add(Factory.createUser(1, "Jeffrey36", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 7566, 2291, 4, 0));
        
        // Patrick die goed is
        users.add(Factory.createUser(1, "Patrick1", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4000, 1733, 3, 0));
        users.add(Factory.createUser(1, "Patrick2", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4000, 1733, 3, 0));
        users.add(Factory.createUser(1, "Patrick3", "Teststraat 24", "9999ZJ", "Texel", "0697721421", "derp@live.nl", 4000, 1733, 3, 0));
        
    }
    // </editor-fold>
    
    private void createParticipants(Tournament tournament){
        
        for(User user : users) {
            participants.add(Factory.createParticipant(user, tournament, 1, "25/02/2015", "17/01/2015"));
        }
        
    }
    
    
}
