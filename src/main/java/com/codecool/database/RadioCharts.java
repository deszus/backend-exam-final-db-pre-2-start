package com.codecool.database;


import java.sql.*;

public class RadioCharts {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public RadioCharts(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    // adja vissza annak a számnak a címét a nevét, amelyiket az évben a legtöbbször játszottak a rádiók.
    public String getMostPlayedSong() {
        return getRequiredData("SELECT song, SUM (times_aired) FROM music_broadcast GROUP BY song ORDER BY SUM (times_aired) DESC LIMIT 1;",
                "song");
    }

    // adja vissza annak az előadónak a nevét, akinek a legtöbb száma szerepel az adatbázisban (ugyanaz a szám többször lejátszva egynek számít).
    public String getMostActiveArtist() {
        return getRequiredData("SELECT artist, COUNT (artist) FROM music_broadcast GROUP BY artist ORDER BY COUNT (artist) DESC LIMIT 1;",
                "artist");
    }

    public String getRequiredData(String sql, String fieldName) {
        String result = "";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result = rs.getString(fieldName);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}



