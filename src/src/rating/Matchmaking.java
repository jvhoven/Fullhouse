package src.rating;

import app.model.*;
import app.view.matchmaking.Overview;
import java.util.*;
import src.Factory;
import src.builder.Builder;

/**
 *
 * @author Henky
 */
public class Matchmaking {
    
    Tournament tournament;
    ArrayList<Participant> participants;
    ArrayList<Session> sessions;
    LinkedHashMap<Session, ArrayList<Position>> positions;
    ArrayList<ArrayList<ArrayList<Participant>>> allParticipants;
    
    int participantsLeft;
    int ronde = 0;
    int maxRondes = 3;
    int sessionSize;
    int participantCount;
    double numberSessions;
    int evenNumberSessions;
    
    final static int AINT_GOT_NO_REST = 0;
    
     //toernooi aanmaken
     public Matchmaking(Tournament tournament, ArrayList<Participant> participants) {
         
        this.tournament = tournament;
        this.participants = participants;
        this.sessions = new ArrayList<>();
        this.positions = new LinkedHashMap<>();
        this.allParticipants = Builder.newArrayList();
        
        this.sessionSize = tournament.getInt("players_per_table");
        this.participantCount = participants.size();
        this.evenNumberSessions = participantCount / sessionSize;
        this.numberSessions =  Math.ceil((double)participantCount / (double)sessionSize);        
        
        shuffle();
        divide();
     }
    
    private void divide() {
        
        Overview matchView = new app.view.matchmaking.Overview(participants, sessionSize, numberSessions, ronde);
   
    }
    
    private void shuffle(){
        
        long seed = System.nanoTime();
        Collections.shuffle(participants, new Random(seed));
        Collections.shuffle(participants, new Random(seed));

    }
}
