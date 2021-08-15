package com.example.springrestapp.generic;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Set;

public abstract class GenericRestService<T extends BaseEntity, D extends BaseDTO> {

    public abstract Set<D> getAll();

    public abstract D getOne(Long id);

    public abstract T updateEntityData(T entity, D dto);

    public abstract JpaRepository<T, Long> getRepository();

    public abstract D convertToDTO(T entity);

    public abstract T getNewEntity();

    @Transactional
    public void delete(D dto) {
        T entity = getRepository().findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        getRepository().delete(entity);
    }

    @Transactional
    public D save(D dto) throws OptimisticLockException {
        if (dto.getId() !=  null) {
            T entity = getRepository().findById(dto.getId()).orElse(null);
            if (entity != null) {
                throw new EntityExistsException();
            }
        }
        T updatedEntity = updateEntityData(getNewEntity(), dto);
        return convertToDTO(getRepository().save(updatedEntity));
    }

    @Transactional
    public D update(D dto) throws OptimisticLockException {
        T entity = getRepository().getById(dto.getId());
        if (!Objects.equals(entity.getVersion(), dto.getVersion())) {
            throw new OptimisticLockException();
        }
        T updatedEntity = updateEntityData(entity, dto);
        return convertToDTO(getRepository().save(updatedEntity));
    }
}
