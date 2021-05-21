package com.university.itis.services.impl;

import com.university.itis.dto.TripleDto;
import com.university.itis.services.ClassesRequestsService;
import com.university.itis.services.PredicatesRequestsService;
import com.university.itis.services.SparqlService;
import com.university.itis.services.answers.AlternativeAnswersHandler;
import com.university.itis.services.answers.AnswerClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SparqlServiceImpl implements SparqlService {

    private final ClassesRequestsService findOntologyClassService;
    private final PredicatesRequestsService triplesService;
    private final AlternativeAnswersHandler alternativeAnswersHandler;

    private final Random random = new Random();

    @Override
    public String selectEntityForQuestion(String type) {
        int countOfClasses = findOntologyClassService.getCountOfInstancesForClass(type);
        if (countOfClasses == 0) {
            return "None";
        }
        return findOntologyClassService.selectEntity(type, random.nextInt(countOfClasses));
    }

    @Override
    public String selectPlaceInRegion(String[] region) {
        return findOntologyClassService.selectPlaceInRegion(region);
    }

    @Override
    public LinkedHashMap<String, String> selectPlacesInRegion(String[] region) {
        return findOntologyClassService.selectPlacesInRegion(region);
    }

    @Override
    public List<String> selectEntitiesForQuestion(String type) {
        int countOfClasses = findOntologyClassService.getCountOfInstancesForClass(type);
        if (countOfClasses == 0) {
            return Collections.singletonList("None");
        }
        return findOntologyClassService.selectEntities(type, random.nextInt(countOfClasses));
    }

    @Override
    public LinkedHashMap<String, String> findEntities(String type, String query) {
        return findOntologyClassService.findEntities(type, query);
    }

    @Override
    public List<TripleDto> getSuitableTriples(String entityUri) {
        List<TripleDto> results = new ArrayList<>();
        results.addAll(triplesService.getSuitableTriplesStepOne(entityUri));
        results.addAll(triplesService.getSuitableTriplesStepTwo(entityUri));
        return results;
    }

    @Override
    public List<String> getAlternativeAnswers(String predicateUri, String correctAnswer) {
        String typeUri = triplesService.getRangeOfPredicate(predicateUri);
        if(typeUri == null) {
            return null;
        }
        AnswerClass answerClass = alternativeAnswersHandler.extractAnswerClass(typeUri, correctAnswer);
        if(answerClass.equals(AnswerClass.OTHER)) {
            int countOfClasses = findOntologyClassService.getCountOfInstancesForClass(typeUri);
            List<String> result = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                String label = findOntologyClassService.selectEntity(typeUri, random.nextInt(countOfClasses));
                result.add(label);
            }
            return result;
        } else {
            return alternativeAnswersHandler.getAlternativeAnswersForClass(answerClass);
        }
    }
}
