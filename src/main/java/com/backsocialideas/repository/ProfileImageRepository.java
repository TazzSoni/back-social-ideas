package com.backsocialideas.repository;

import com.backsocialideas.model.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity, Long>{
//    @Query(value = "select*from anexo where id IN  (select anexo_id from atividade_anexos where atividade_id =:atividadeId)", nativeQuery = true)
//    List<Anexo> findByCdAtividade(@Param("atividadeId") Long atividadeId);
    
    ProfileImageEntity findById(long id);
}
