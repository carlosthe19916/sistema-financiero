package org.ventura.facade;

import java.util.List;

import javax.ejb.Local;

import org.ventura.model.Personanatural;

@Local
public interface PersonaNaturalLocal {

    void create(Personanatural persnaNatural);

    void edit(Personanatural persnaNatural);

    void remove(Personanatural persnaNatural);

    Personanatural find(Object id);

    List<Personanatural> findAll();

    List<Personanatural> findRange(int[] range);

    int count();
    
}