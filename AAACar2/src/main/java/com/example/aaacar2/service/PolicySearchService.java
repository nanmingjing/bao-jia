package com.example.aaacar2.service;

import com.example.aaacar2.entity.InsurerPolicy;
import com.example.aaacar2.repository.InsurerPolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicySearchService {

    private final InsurerPolicyRepository repository;

    public PolicySearchService(
            InsurerPolicyRepository repository
    ) {
        this.repository = repository;
    }

    public List<InsurerPolicy> search(
            String brand,
            String model,
            String region
    ) {

        List<InsurerPolicy> result =
                repository.exactMatch(
                        brand,
                        model,
                        region
                );

        if (!result.isEmpty()) {
            System.out.println("一级匹配");
            return result;
        }

        result =
                repository.keywordMatch(
                        brand,
                        region
                );

        if (!result.isEmpty()) {
            System.out.println("二级匹配");
            return result;
        }

        result =
                repository.regexMatch(
                        model
                );

        if (!result.isEmpty()) {
            System.out.println("三级匹配");
            return result;
        }

        result =
                repository.fuzzyMatch(
                        model
                );

        if (!result.isEmpty()) {
            System.out.println("四级匹配");
        }

        return result;
    }
}