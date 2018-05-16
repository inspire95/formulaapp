package Formula1;

import Formula1.modele.*;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;

/**
 *
 * @author Dawid
 */
public class OntologyDataBase {

    private OWLObjectRenderer renderer;
    private OWLOntologyManager manager = null;
    private OWLOntology ontology = null;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;
    private OWLDataFactory factory;
    private PrefixOWLOntologyFormat pm;

    static File file = new File("Formula1.owl");
    public static File fileToReload = new File("fileToReload.owl");
    private String BASE_URL = "http://www.semanticweb.org/dawid/ontologies/2017/3/formula1";

    private OWLClass Formula1Class;
    private OWLClass DriverClass;
    private OWLClass RaceClass;
    private OWLClass TeamClass;

    private OWLDataProperty teamIDdata;
    private OWLDataProperty raceIDdata;
    private OWLDataProperty driverIDdata;
    private OWLDataProperty formula1IDdata;

    private OWLObjectProperty teamDriveraObject;
    private OWLObjectProperty partipObject;
    private OWLObjectProperty raceObject;
    private OWLObjectProperty winnerObject;

    private OWLDatatype integerDatatype;

    public OntologyDataBase() {

        open(OntologyDataBase.file);

        Formula1Class = factory.getOWLClass(":Formula1", pm);
        DriverClass = factory.getOWLClass(":Driver", pm);
        RaceClass = factory.getOWLClass(":Race", pm);
        TeamClass = factory.getOWLClass(":Team", pm);

        integerDatatype = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER
                .getIRI());

        teamDriveraObject = factory.getOWLObjectProperty(":Team", pm);
        raceObject = factory.getOWLObjectProperty(":Race", pm);
        winnerObject = factory.getOWLObjectProperty(":Winner", pm);
        partipObject = factory.getOWLObjectProperty(":Particitians", pm);

        teamIDdata = factory.getOWLDataProperty(":TeamID", pm);
        raceIDdata = factory.getOWLDataProperty(":RaceID", pm);
        driverIDdata = factory.getOWLDataProperty(":DriverID", pm);
        formula1IDdata = factory.getOWLDataProperty(":FormulaOneID", pm);

    }

    public void open(File file) {
        try {
            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(IRI.create(file));
            pm = (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology);
            pm.setDefaultPrefix(BASE_URL + "#");
            reasonerFactory = new StructuralReasonerFactory();
            reasoner = reasonerFactory.createReasoner(ontology);
            factory = manager.getOWLDataFactory();
            renderer = new DLSyntaxObjectRenderer();
        } catch (OWLOntologyCreationException ex) {
        }
    }

    public void delete(String name) {
        OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(ontology));
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(":" + name, pm);
        individual.accept(remover);
        manager.applyChanges(remover.getChanges());
        reload();
    }

    public void reload() {
        fileToReload = new File("fileToReload.owl");
        save(fileToReload);
        open(fileToReload);
    }

    public void save(File file) {
        IRI documentIRI2;
        if (file == null) {
            documentIRI2 = IRI.create(file);
        } else {
            documentIRI2 = IRI.create(file);
        }
        try {
            manager.saveOntology(ontology, new OWLXMLOntologyFormat(), documentIRI2);
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(OntologyDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Formula1> loadFormula1() {
        ObservableList<Formula1> list = FXCollections.observableArrayList();

        for (OWLNamedIndividual a : reasoner.getInstances(Formula1Class, false).getFlattened()) {

            Formula1 f = new Formula1();

            f.setYear(renderer.render(a).substring(2));

            OWLNamedIndividual formula = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);

            reasoner.getObjectPropertyValues(formula, raceObject).getFlattened().forEach(n -> {
                f.setRace(renderer.render(n));
            });
            reasoner.getObjectPropertyValues(formula, winnerObject).getFlattened().forEach(n -> {
                f.setWinner(renderer.render(n));
            });
            reasoner.getObjectPropertyValues(formula, partipObject).getFlattened().forEach(n -> {
                f.addParticipant(renderer.render(n));
            });
            list.add(f);
        }
        return list;
    }

    public ObservableList<Driver> loadDrivers() {
        ObservableList<Driver> list = FXCollections.observableArrayList();

        for (OWLNamedIndividual a : reasoner.getInstances(DriverClass, false).getFlattened()) {

            Driver d = new Driver();

            d.setName(renderer.render(a));

            OWLNamedIndividual driver = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);

            reasoner.getDataPropertyValues(driver, driverIDdata).iterator().forEachRemaining(n -> {
                d.setId(Integer.parseInt(n.getLiteral()));
            });

            reasoner.getObjectPropertyValues(driver, teamDriveraObject).getFlattened().forEach(n -> {
                d.setTeam(renderer.render(n));
            });

            list.add(d);
        }
        return list;
    }

    public ObservableList<Race> loadRaces() {
        ObservableList<Race> list = FXCollections.observableArrayList();

        for (OWLNamedIndividual a : reasoner.getInstances(RaceClass, false).getFlattened()) {

            Race r = new Race();

            r.setName(renderer.render(a));

            OWLNamedIndividual race = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);

            reasoner.getDataPropertyValues(race, raceIDdata).iterator().forEachRemaining(n -> {
                r.setId(Integer.parseInt(n.getLiteral()));
            });

            list.add(r);
        }
        return list;
    }

    public ObservableList<Team> loadTeams() {
        ObservableList<Team> list = FXCollections.observableArrayList();

        for (OWLNamedIndividual a : reasoner.getInstances(TeamClass, false).getFlattened()) {

            Team t = new Team();

            t.setName(renderer.render(a));

            OWLNamedIndividual pracownik = factory.getOWLNamedIndividual(":" + renderer.render(a), pm);

            reasoner.getDataPropertyValues(pracownik, teamIDdata).iterator().forEachRemaining(n -> {
                t.setId(Integer.parseInt(n.getLiteral()));
            });

            list.add(t);
        }
        return list;
    }

    public void addFormula1(Formula1 f) {
        OWLNamedIndividual formula1 = new OWLNamedIndividualImpl(IRI.create("#" + "F1" + f.getYear()));
        OWLClassAssertionAxiom ax0 = factory.getOWLClassAssertionAxiom(Formula1Class, formula1);
        manager.addAxiom(ontology, ax0);
        OWLNamedIndividual race = factory.getOWLNamedIndividual(":" + f.getRace(), pm);
        OWLNamedIndividual winner = factory.getOWLNamedIndividual(":" + f.getWinner(), pm);
        OWLObjectPropertyAssertionAxiom ax2 = factory
                .getOWLObjectPropertyAssertionAxiom(raceObject, formula1, race);
        manager.addAxiom(ontology, ax2);
        OWLObjectPropertyAssertionAxiom ax3 = factory
                .getOWLObjectPropertyAssertionAxiom(winnerObject, formula1, winner);
        manager.addAxiom(ontology, ax3);

        boolean isFirst = true;
        for (String particitian : f.getParticitians()) {
            if (!isFirst) {
                particitian = particitian.substring(1);

            } else {
                isFirst = false;
            }
            OWLNamedIndividual partic = factory.getOWLNamedIndividual(":" + particitian, pm);
            OWLObjectPropertyAssertionAxiom ax4 = factory
                    .getOWLObjectPropertyAssertionAxiom(partipObject, formula1, partic);
            manager.addAxiom(ontology, ax4);
        }

        reload();
    }

    public void addTeam(Team t) {

        OWLNamedIndividual team = new OWLNamedIndividualImpl(IRI.create("#" + t.getName()));
        OWLClassAssertionAxiom ax0 = factory.getOWLClassAssertionAxiom(TeamClass, team);

        manager.addAxiom(ontology, ax0);

        OWLLiteral mocLiteral = factory.getOWLLiteral(String.valueOf(t.getId()), integerDatatype);
        OWLAxiom ax1 = factory.getOWLDataPropertyAssertionAxiom(teamIDdata, team, mocLiteral);
        manager.addAxiom(ontology, ax1);

        reload();
    }

    public void addDriver(Driver d) {

        OWLNamedIndividual driver = new OWLNamedIndividualImpl(IRI.create("#" + d.getName()));
        OWLClassAssertionAxiom ax0 = factory.getOWLClassAssertionAxiom(DriverClass, driver);
        manager.addAxiom(ontology, ax0);

        OWLLiteral idLiteral = factory.getOWLLiteral(String.valueOf(d.getId()), integerDatatype);
        OWLAxiom ax1 = factory.getOWLDataPropertyAssertionAxiom(driverIDdata, driver, idLiteral);
        manager.addAxiom(ontology, ax1);

        OWLNamedIndividual team = factory.getOWLNamedIndividual(":" + d.getTeam(), pm);

        OWLObjectPropertyAssertionAxiom ax2 = factory
                .getOWLObjectPropertyAssertionAxiom(teamDriveraObject, driver, team);
        manager.addAxiom(ontology, ax2);

        reload();
    }

    public void addRace(Race r) {

        OWLNamedIndividual race = new OWLNamedIndividualImpl(IRI.create("#" + r.getName()));
        OWLClassAssertionAxiom ax0 = factory.getOWLClassAssertionAxiom(RaceClass, race);
        manager.addAxiom(ontology, ax0);

        OWLLiteral idLiteral = factory.getOWLLiteral(String.valueOf(r.getId()), integerDatatype);
        OWLAxiom ax1 = factory.getOWLDataPropertyAssertionAxiom(raceIDdata, race, idLiteral);
        manager.addAxiom(ontology, ax1);

        reload();
    }
}
