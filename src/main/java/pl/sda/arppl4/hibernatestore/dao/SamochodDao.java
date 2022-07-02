package pl.sda.arppl4.hibernatestore.dao;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.arppl4.hibernatestore.model.Samochod;
import pl.sda.arppl4.hibernatestore.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SamochodDao implements ISamochodDao {

    @Override
    public void dodajSamochod(Samochod samochod) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {

            Transaction transaction = session.beginTransaction();

            session.save(samochod);

            transaction.commit();

        } catch (SessionException sessionException) {
            System.out.println("Tutaj miejsce na wyjatek");

        }

    }

    @Override
    public void usunSamochod(Samochod samochod) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {

            Transaction transaction = session.beginTransaction();

            session.remove(samochod);

            transaction.commit();

        }

    }

    @Override
    public Optional<Samochod> zwrocSamochod(Long id) {
            SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
            try (Session session = fabrykaPolaczen.openSession()) {
                Samochod obiektProduct = session.get(Samochod.class, id);
                return Optional.ofNullable(obiektProduct);
            }
    }

    @Override
    public List<Samochod> zwrocListeSamochod() {
        List<Samochod> samochodList = new ArrayList<>();
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {
            TypedQuery<Samochod> zapytanie = session.createQuery("from Samochod", Samochod.class);
            List<Samochod> wynikZapytania = zapytanie.getResultList();
            samochodList.addAll(wynikZapytania);


        } catch (SessionException sessionException) {
            System.out.println("Błąd wczytywania danych");


        }
        return samochodList;
    }

    @Override
    public void updateSamochod(Samochod samochod) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(samochod);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null){
                transaction.rollback();
            }
        }

    }
}
