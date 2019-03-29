package shotchart.domain;

// @deemus
import java.util.Date;

// Yksittäistä laukaisukarttaa kuvaava luokka.
public class ShotChart {

    private int id;
    private String date;
    private String homeTeam;
    private String awayTeam;
    private User user;
    private int[][] shoots;

    public ShotChart(int id, String date, String homeTeam, String awayTeam, User user, int[][] shoots) {
        this.id = id;
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.shoots = shoots;
        this.user = user;
    }

    public ShotChart(User user) {
        this.user = user;
        this.shoots = new int[200][400];
    }

    public String getShootsAsString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < shoots.length; x++) {
            for (int y = 0; y < shoots[0].length; y++) {
                sb.append(";").append(shoots[x][y]).append(";").append(x).append(";").append(y);
            }
        }
        return sb.toString();
    }
    
    public void addGoal(int x, int y) {
        // maalin merkki on 1 
        this.shoots[x][y] = 1;
    }

    public void addSavedShot(int x, int y) {
        // torjutun laukauksen merkki on 2
        this.shoots[x][y] = 2;
    }

    public void addMissedShot(int x, int y) {
        // ohilaukauksen merkki on 3
        this.shoots[x][y] = 3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int[][] getShoots() {
        return shoots;
    }

    public void setShoots(int[][] shoots) {
        this.shoots = shoots;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ShotChart)) {
            return false;
        }
        ShotChart other = (ShotChart) obj;
        return id == other.id;
    }
}