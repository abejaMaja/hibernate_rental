package pl.sda.arppl4.hibernatestore.dao;

import pl.sda.arppl4.hibernatestore.model.Samochod;

import java.util.List;
import java.util.Optional;

public interface ISamochodDao {
    public void dodajSamochod(Samochod samochod);

    // Delete
    public void usunSamochod(Samochod samochod);

    // Read
    public Optional<Samochod> zwrocSamochod(Long id);

    public List<Samochod> zwrocListeSamochod();

    // Update
    public void updateSamochod(Samochod samochod);
}
