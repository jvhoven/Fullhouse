/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.model.Participant;
import app.model.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import src.controller.BaseController;
import src.rating.Rating;

/**
 *
 * @author milan
 */
public class MatchmakingController extends BaseController  {
    
    public final static String table = "";
    
    public MatchmakingController() {
        super(table);
    }
    
    public void calculateScores(Participant winner, ArrayList<Participant> losers) {
       
        ArrayList<Participant> participants = new ArrayList<>(losers);
        participants.add(winner);
        
        int winnerPos = participants.size() - 1;
        int[][] newScores = new int[participants.size()][3];
        
        newScores[0] = Rating.updateScore(winner, losers, true);   
        for(int i = 0; i < participants.size(); i++) {
            
            ArrayList<Participant> otherParticipants = new ArrayList<>(participants);
            otherParticipants.remove(i);
            
            if(i == winnerPos) {
                newScores[i] = Rating.updateScore(participants.get(i), otherParticipants, true);            
            }else{
                newScores[i] = Rating.updateScore(participants.get(i), otherParticipants, false);
            }
        }
        
        /*
        * Since we cannot update the score after calculating it, as it will mess up
        * the other calculations, we need to set the rating, total_score and win/loss after 
        * the calculation is done.
        */
        for(int i = 0; i < participants.size(); i++) {
            
            Participant participant = participants.get(i);
            
            participant.getUser().set("total_score", newScores[i][0]);
            participant.getUser().set("rating", newScores[i][2]);
            
            if(i == winnerPos) {
                participant.getUser().set("wins", newScores[i][1]);
            } else {
                participant.getUser().set("losses", newScores[i][1]);
            }
            
            syncScore(participant.getUser());
        }
        
        
    }
    
    private void syncScore(User user) {
       
        // Update users in database
      
        String query = 
                   "UPDATE user"+
                   " SET"
                       + " total_score = ?," 
                       + " rating = ?,"
                       + " wins = ?,"
                       + " losses = ?" +
                   " WHERE id = ?";
        
        try {
            PreparedStatement update = conn.prepareStatement(query);

            update.setInt(1, user.getInt("total_score"));
            update.setInt(2, user.getInt("rating"));
            update.setInt(3, user.getInt("wins"));
            update.setInt(4, user.getInt("losses"));
            update.setInt(5, user.getInt("id"));
            
            super.update(update);
          
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
