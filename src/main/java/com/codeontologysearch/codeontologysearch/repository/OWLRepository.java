package com.codeontologysearch.codeontologysearch.repository;

import com.codeontologysearch.codeontologysearch.dto.ResultDto;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OWLRepository {


    public List<ResultDto> executeQueryOneColumn(String queryString) throws FileNotFoundException {
        List<ResultDto> values = new ArrayList<>();
        Model model = FileManager.get().loadModel(ResourceUtils.getFile("classpath:CodeOntology.owl").getAbsolutePath());

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();

            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();

                ResultDto resultDto = new ResultDto();

                Resource subject = solution.getResource("?subject");
                if (subject != null) {
                    if (subject.isLiteral()) {
                        Literal literal = subject.asLiteral();
                        resultDto.setSubject(literal.getString());
                    } else {
                        resultDto.setSubject(subject.getLocalName());
                    }
                }
                RDFNode label = solution.get("?label");
                if (label != null) {
                    if (label.isLiteral()) {
                        Literal literal = label.asLiteral();
                        resultDto.setLabel(literal.getString());
                    }
                }
                Resource supertype = solution.getResource("?supertype");
                if (supertype != null) {
                    if (supertype.isLiteral()) {
                        Literal literal = supertype.asLiteral();
                        resultDto.setSupertype(literal.getString());
                    } else {
                        resultDto.setSupertype(supertype.getLocalName());
                    }
                }

                values.add(resultDto);
            }
        } finally {
            qexec.close();
        }
        return values;

    }

}
