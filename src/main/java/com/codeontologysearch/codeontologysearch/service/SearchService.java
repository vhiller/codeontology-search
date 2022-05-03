package com.codeontologysearch.codeontologysearch.service;

import com.codeontologysearch.codeontologysearch.dto.ResultDto;
import com.codeontologysearch.codeontologysearch.repository.OWLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final OWLRepository owlRepository;

    public List<ResultDto> findAllByTerm(String term) throws FileNotFoundException {
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                " " +
                "SELECT DISTINCT ?subject ?label ?supertype " +
                "WHERE { " +
                "{ ?subject a owl:Class . } UNION { ?individual a ?subject . } .  " +
                "OPTIONAL { ?subject rdfs:subClassOf ?supertype } . " +
                "OPTIONAL { ?subject rdfs:label ?label } " +
                "} ORDER BY ?subject";
        return owlRepository.executeQueryOneColumn(query);
    }
}
