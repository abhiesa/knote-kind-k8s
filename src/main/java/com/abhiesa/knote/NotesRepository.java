package com.abhiesa.knote;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author abhiesa
 * */
interface NotesRepository extends MongoRepository<Note, String> {}