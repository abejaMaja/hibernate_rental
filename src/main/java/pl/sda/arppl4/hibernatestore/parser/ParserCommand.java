package pl.sda.arppl4.hibernatestore.parser;

import pl.sda.arppl4.hibernatestore.dao.SamochodDao;
import pl.sda.arppl4.hibernatestore.model.Samochod;
import pl.sda.arppl4.hibernatestore.model.TypNadwozia;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ParserCommand {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Scanner scanner;
    private final SamochodDao dao;

    public ParserCommand(Scanner scanner, SamochodDao dao) {
        this.scanner = scanner;
        this.dao = dao;
    }

    public void parse() {
        String command;
        do {
            System.out.println("Command: [dodaj, lista, update, usun, readById]");
            command = scanner.next();
            switch (command) {
                case "dodaj":
                    obslugaDodaj();
                    break;
                case "lista":
                    obslugaZwrocListe();
                    break;
                case "readById":
                    obslugaZwrocSamochod();
                    break;
                case "usun":
                    obslugaUsun();
                    break;
                case "update":
                    obslugaAktualizuj();
                    break;
            }
        } while (!command.equalsIgnoreCase("koniec"));
    }

    private void obslugaAktualizuj() {
        System.out.println("Podaj id produktu, który chcesz aktualizować");
        Long id = scanner.nextLong();
        Optional<Samochod> optionalSamochod = dao.zwrocSamochod(id);
        if (optionalSamochod.isPresent()) {
            System.out.println("Co zmieniamy, marke czy rokProdukcji");
            String output = scanner.next();
            Samochod samochodAktualizowany = optionalSamochod.get();
            switch (output) {
                case "marke":
                    System.out.println("Podaj marke " + optionalSamochod);
                    String marka = scanner.next();
                    samochodAktualizowany.setMarka(marka);
                    break;
                case "rokProdukcji":
                    LocalDate rokProdukcji = loadDataProdukcji();
                    samochodAktualizowany.setRokProdukcji(rokProdukcji);
                    break;
            }

            dao.updateSamochod(samochodAktualizowany);
            System.out.println("Dokonano aktualizacji danych dla samochodu " + samochodAktualizowany);
        }
    }

    private void obslugaUsun() {
        System.out.println("Podaj id samochodu");
        Long id = scanner.nextLong();
        Optional<Samochod> optionalSamochod = dao.zwrocSamochod(id);
        Samochod samochod = null;
        if (optionalSamochod.isPresent()) {
            samochod = optionalSamochod.get();
            dao.usunSamochod(samochod);
        }
        System.out.println("Usunięto samochod  " + samochod);
    }

    private void obslugaZwrocSamochod() {
        System.out.println("Podaj id samochodu");
        Long id = scanner.nextLong();
        Optional<Samochod> optionalSamochod = dao.zwrocSamochod(id);
        System.out.println("Szukany samochod to " + optionalSamochod);
    }


    private void obslugaZwrocListe() {
        List<Samochod> samochodList = dao.zwrocListeSamochod();
        for (Samochod samochod : samochodList) {
            System.out.println(samochod);
        }

        System.out.println();
    }

    private void obslugaDodaj() {
        System.out.println("Podaj nazwę:");
        String name = scanner.next();

        System.out.println("Podaj marke:");
        String marka = scanner.next();

        LocalDate rokProdukcji = loadDataProdukcji();

        TypNadwozia typNadwozia = loadTypNadwozia();


        System.out.println("Podaj ilość miejsc:");
        int iloscMiejsc = scanner.nextInt();



        Samochod samochod = new Samochod(null, name, marka, rokProdukcji, typNadwozia, iloscMiejsc);
        dao.dodajSamochod(samochod);

    }

    private TypNadwozia loadTypNadwozia() {
        TypNadwozia typNadwozia = null;
        do {
            try {
                System.out.println("Podaj typ nadwozia:");
                String unitString = scanner.next();

                typNadwozia = TypNadwozia.valueOf(unitString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Nie ma takiego typu nadwozia: ...");
            }
        } while (typNadwozia == null);
        return typNadwozia;
    }

    private LocalDate loadDataProdukcji() {
        LocalDate dataProdukcji = null;
        do {
            try {
                System.out.println("Podaj datę produkcji:");
                String dataProdukcjiString = scanner.next();

                dataProdukcji = LocalDate.parse(dataProdukcjiString, FORMATTER);

                LocalDate today = LocalDate.now();
                if (dataProdukcji.isAfter(today)) {
                    // błąd, expiry date jest przed dzisiejszym dniem
                    throw new IllegalArgumentException("Podana data jest z przyszłości");
                }

            } catch (IllegalArgumentException | DateTimeException iae) {
                dataProdukcji = null;
                System.err.println("Niewłaściwy format daty, podaj datę w formacie: yyyy-MM-dd");
            }
        } while (dataProdukcji == null);
        return dataProdukcji;
    }


    }



