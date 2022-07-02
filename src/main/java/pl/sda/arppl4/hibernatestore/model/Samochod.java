package pl.sda.arppl4.hibernatestore.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.TypNadwozia;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Samochod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String marka;
    private LocalDate rokProdukcji;
    @Enumerated(EnumType.STRING)
    private TypNadwozia typNadwozia;
    private int iloscMiejsc;




}
