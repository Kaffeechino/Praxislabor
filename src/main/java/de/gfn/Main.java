package de.gfn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static Connection conn;
    static {
        try {
            conn = DBManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean checkDuplicates(Connection conn, String s) {
        boolean result = false;

        return result;
    }
    public static void main(String[] args) throws Exception {
        String line;
        String wholeFile = "";

        String filePath = "C:\\Users\\Student\\IdeaProjects\\Praxislabor\\src\\main\\resources\\ww-german-postal-codes.txt";

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("VALUES")) {
                    continue;
                }
                wholeFile += line;

            }
            wholeFile = wholeFile.substring(1, wholeFile.length() - 2);

            String[] data = wholeFile.split("\\),\\(");
            String insertQuery = "INSERT INTO german_postal_codes (zipcode, city, state, community, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)";
            String selectQuery = "SELECT * FROM german_postal_codes " +
                    "WHERE (zipcode = ? AND city = ?);";
            int progressCounter = 0;
            int duplicates = 0;
            for (String row : data
            ) {
                row = row.substring(1, row.length() - 1);
                String[] rowArr = row.split("','");

                //Check
                PreparedStatement preparedStatementCheck = conn.prepareStatement(selectQuery);
                preparedStatementCheck.setString(1,rowArr[1]);
                preparedStatementCheck.setString(2,rowArr[2]);

                ResultSet resultSet = preparedStatementCheck.executeQuery();
                if (resultSet.next()) {
                    duplicates++;
                    System.out.print("Duplicates: " + duplicates + "\r");
                    continue;
                }
                PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
                preparedStatement.setString(1, rowArr[1]);
                preparedStatement.setString(2, rowArr[2]);
                preparedStatement.setString(3, rowArr[3]);
                preparedStatement.setString(4, rowArr[4]);
                preparedStatement.setDouble(5, Double.parseDouble(rowArr[5]));
                preparedStatement.setDouble(6, Double.parseDouble(rowArr[6]));
                preparedStatement.executeUpdate();
                progressCounter++;
                System.out.print(progressCounter + "/" + data.length + ": done!\r");

            }
            // Close the BufferedReader and FileReader when done
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }
    }
}