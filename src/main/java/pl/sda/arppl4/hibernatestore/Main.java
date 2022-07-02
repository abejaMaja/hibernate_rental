package pl.sda.arppl4.hibernatestore;

import pl.sda.arppl4.hibernatestore.dao.SamochodDao;
import pl.sda.arppl4.hibernatestore.parser.ParserCommand;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SamochodDao samochodDao = new SamochodDao();

        ParserCommand parser = new ParserCommand(scanner, samochodDao);
        parser.parse();
    }
}
