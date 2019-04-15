package org.lgraf.mailing;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;


@Service
class MailingService {
    private final Map<Long, Mailing> repository = new HashMap<>();


    @PostConstruct
    void initialize() {
        repository.put( 1L, new Mailing( 1L, "mailing" ) );
    }


    Collection<Mailing> findAll() {
        return repository.values();
    }


    Mailing find( Long id ) {
        Mailing mailing = repository.get( id );
        if( null == mailing )
            throw new IllegalArgumentException( "mailing for id '" + id + "' does not exist!" );

        return mailing;
    }


    Mailing create( String name ) {
        Mailing mailing;

        synchronized( repository ) {
            long nextID = repository.size() + 1;

            mailing = new Mailing( nextID, name );
            repository.put( nextID, mailing );
        }

        return mailing;
    }


}
