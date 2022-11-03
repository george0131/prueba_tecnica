package com.alianza.demo.config;


import com.alianza.demo.persistence.repository.base.QueryableReadRepository;
import com.alianza.demo.persistence.repository.base.QueryableReadRepositoryImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryFragment;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CustomRepositoryFactory extends JpaRepositoryFactory {

    private final EntityManager entityManager;

    public CustomRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @NotNull
    @Override
    protected RepositoryComposition.RepositoryFragments getRepositoryFragments(@NotNull RepositoryMetadata metadata) {
        RepositoryComposition.RepositoryFragments fragments = super.getRepositoryFragments(metadata);

        if (QueryableReadRepository.class.isAssignableFrom(
                metadata.getRepositoryInterface())) {

            JpaEntityInformation<?, Serializable> entityInformation =
                    getEntityInformation(metadata.getDomainType());

            Object queryableFragment = instantiateClass(
                    QueryableReadRepositoryImpl.class, entityInformation, entityManager);

            fragments = fragments.append(RepositoryFragment.implemented(queryableFragment));
        }

        return fragments;
    }
}
