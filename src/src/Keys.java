package src;

/**
 *
 * @author Project group 9
 */
public interface Keys {
    
    // User data
    public final String[] user = {
        "id",
        "name",
        "address",
        "postal",
        "city",
        "phone",
        "email",
        "total_score",
        "rating",
        "wins",
        "losses"
    };
    
    // Tournament data
    public final String[] tournament = {
        "id",
        "location",
        "name",
        "description",
        "start_date",
        "payment",
        "min_participants",
        "players_per_table"
    };
    
    // Participant data
    public final String[] participant = {
        "user",
        "tournament",
        "has_paid",
        "start_date",
        "date_approved"
    };
    
    // Masterclass participant data
    public final String[] master_participant = {
        "user",
        "masterclass",
        "has_paid",
        "start_date",
        "date_approved"
    };
    
    /*
    * Masterclass is a class given by one of the top players 
    * defined by the elo system
    */
    public final String[] masterclass = {
        "id",
        "name",
        "teacher", //user that teaches the masterclass * STILL NEED TO ADD / RIM needs updating
        "min_rating",
        "price",
        "location",
        "start_date"
    };
    
    /*
    * Location where the masterclass or tournament will be held
    */
    public final String[] location = {
        "postal",
        "streetnr",
        "address",
        "city",
        "max_capacity"
    };
    
    /*
    * Table the different types of tables
    * Table will also hold the participants that are playing at said table
    */
    public final String[] table = {
        "allowed_participants"
    };
    
    /*
    * Session will be the so called bracket in which participants have been 
    * divided in.
    */
    public final String[] session = {
    };
    
    public final String[] position = {
        "participant",
        "session"
    };
}
